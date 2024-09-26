package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.*;
import de.tudortmund.cs.iltis.utils.graph.EmptyEdgeLabel;
import de.tudortmund.cs.iltis.utils.graph.Vertex;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.HashGraph;
import de.tudortmund.cs.iltis.utils.weblib.WebLib;
import de.tudortmund.cs.iltis.utils.weblib.WebLibManager;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

public class Z3Solver extends SmtlibSolver {

    // to avoid the establishment of multiple connections, this is handled as a singleton

    private static Z3Solver instance = new Z3Solver();

    public static Z3Solver getInstance() {
        return instance;
    }

    private Z3Solver() {
        super(false);
    }

    @Override
    protected void initConnection() {
        sort = "CustomSort";
        writer = new FOSmtlibFormulaWriter(sort);

        InputStream inputStream =
                Z3Solver.class.getResourceAsStream(
                        "/de/tudortmund/cs/iltis/logiclib/predicate/satisfiability/solvers/z3Config.xml");

        WebLibManager libs = WebLibManager.initFromConfig(inputStream).get();

        WebLib z3 = libs.getLibrary("z3").get();

        function = z3.getFunction("checkSmtlib").get();
    }

    // Z3 models are often sophisticated and hard to parse, so we use the RandomModelSolver's or
    // VampireModelSolver's
    // models instead
    @Override
    protected Answer parseModel(String modelOutput) {
        return new InvalidFormula();
    }

    protected String getConstantDefinitionPrefix() {
        return "{\n\"options\" : [\"-inputFormat=pri\", \"-quiet\", \"-timeout=1000\"],\n \"formula\" : \"";
    }

    public <V> Optional<List<String>> getHamiltonCycle(HashGraph<V, EmptyEdgeLabel> graph) {
        return getHamiltonCycle(graph, new IdentityFunction<>());
    }

    /**
     * Checks, if a graph has a hamilton cycle. If one exists it is returned as an ordered list of
     * vertex names
     *
     * @param graph graph to be checked for hamilton cycle
     * @param naming function to map vertices to strings
     * @return names of vertices of hamilton cycle if one exists
     * @param <V> vertex type of graph
     */
    public <V> Optional<List<String>> getHamiltonCycle(
            HashGraph<V, EmptyEdgeLabel> graph, Function<V, String> naming) {
        HashSet<Vertex<V, EmptyEdgeLabel>> vertices = graph.getVertices();

        HashMap<V, List<String>> stringConstants = new HashMap<>();

        int n = vertices.size();

        Set<V> unorderedVertices = stringConstants.keySet();

        for (Vertex<V, EmptyEdgeLabel> vertex : vertices) {
            List<String> numberedDuplicates = new ArrayList<>();

            String namedVertex = naming.apply(vertex.get());

            for (int i = 0; i < n; i++) {
                numberedDuplicates.add(namedVertex + "_" + i);
            }

            stringConstants.put(vertex.get(), numberedDuplicates);
        }

        StringBuilder smtlibInput = new StringBuilder();

        smtlibInput.append("{\n\"options\" : [\"-inputFormat=pri\", \"-quiet\"],\n");

        smtlibInput.append("\"formula\" : \"");

        // define boolean constants
        for (V vertex : unorderedVertices) {
            for (String namedVertex : stringConstants.get(vertex)) {
                smtlibInput.append("(declare-const ");
                smtlibInput.append(namedVertex);
                smtlibInput.append(" Bool)\\n");
            }
        }

        // every vertex must occur at least once in a hamilton cycle
        for (V vertex : unorderedVertices) {
            smtlibInput.append("(assert (or");
            for (String namedVertex : stringConstants.get(vertex)) {
                smtlibInput.append(" ");
                smtlibInput.append(namedVertex);
            }
            smtlibInput.append("))\\n");
        }

        // every position of a hamilton cycle must be occupied at least by one vertex
        for (int i = 0; i < n; i++) {
            smtlibInput.append("(assert (or");
            for (V vertex : unorderedVertices) {
                smtlibInput.append(" ");
                smtlibInput.append(stringConstants.get(vertex).get(i));
            }
            smtlibInput.append("))\\n");
        }

        // every vertex may only occur once in the hamilton cycle (except the start vertex)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!(i == 0 && j == n - 1)) {
                    for (V vertex : unorderedVertices) {
                        List<String> numberedDuplicates = stringConstants.get(vertex);
                        smtlibInput.append("(assert (or (not ");
                        smtlibInput.append(numberedDuplicates.get(i));
                        smtlibInput.append(") (not ");
                        smtlibInput.append(numberedDuplicates.get(j));
                        smtlibInput.append(")))\\n");
                    }
                }
            }
        }

        // every position of the cycle may only be used by one vertex
        List<V> orderedVertices = new ArrayList<>();
        orderedVertices.addAll(unorderedVertices);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    smtlibInput.append("(assert (or (not ");
                    smtlibInput.append(stringConstants.get(orderedVertices.get(j)).get(i));
                    smtlibInput.append(") (not ");
                    smtlibInput.append(stringConstants.get(orderedVertices.get(k)).get(i));
                    smtlibInput.append(")))\\n");
                }
            }
        }

        // if two vertices are not adjacent in the graph then they must not occur next to one
        // another in a hamilton cycle
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!graph.hasEdge(orderedVertices.get(i), orderedVertices.get(j))) {
                    for (int k = 0; k < n - 1; k++) {
                        smtlibInput.append("(assert (or (not ");
                        smtlibInput.append(stringConstants.get(orderedVertices.get(i)).get(k));
                        smtlibInput.append(") (not ");
                        smtlibInput.append(stringConstants.get(orderedVertices.get(j)).get(k + 1));
                        smtlibInput.append(")))\\n");
                    }

                    // since we are looking for a circle
                    smtlibInput.append("(assert (or (not ");
                    smtlibInput.append(stringConstants.get(orderedVertices.get(i)).get(n - 1));
                    smtlibInput.append(") (not ");
                    smtlibInput.append(stringConstants.get(orderedVertices.get(j)).get(0));
                    smtlibInput.append(")))\\n");
                }
            }
        }

        if (useExtraZero) {
            // vampire needs the explicit call to check satisfiability
            smtlibInput.append("(check-sat)\\n");
        }

        smtlibInput.append(getConstantPostfix());

        String finalInput = smtlibInput.toString();

        function.call(finalInput, handler);

        if (!answer.isPresent()) {
            throw new RuntimeException("Something went wrong in the solving process");
        }

        String answerString = answer.get();

        // did not find hamilton cycle
        if (answerString.equals("{\"result\":\"UNSATISFIABLE\"}")) {
            return Optional.empty();
        }

        // extracting a hamilton cycle from model
        String workingString =
                answerString.substring(
                        "{\"result\":\"SATISFIABLE\n".length() + 1, answerString.length() - 2);
        workingString = workingString.replaceAll("\\(define-fun ", "");
        workingString = workingString.replaceAll(" \\(\\) Bool", "");
        workingString = workingString.replaceAll("\\)", "");
        workingString = workingString.replaceAll("\\\\n", " ");
        workingString = workingString.replaceAll("\\s{2,}", " ").trim();

        String[] parts = workingString.split(" ");
        String[] cycleComponents = new String[n];
        for (int i = 1; i < parts.length; i = i + 2) {
            if (parts[i].equals("true")) {
                String variable = parts[i - 1];
                String[] partsVariable = variable.split("_");
                int position = Integer.parseInt(partsVariable[partsVariable.length - 1]);
                cycleComponents[position] =
                        variable.substring(0, partsVariable[partsVariable.length - 1].length() + 1);
            }
        }

        return Optional.of(Arrays.asList(cycleComponents));
    }

    private class IdentityFunction<V> implements Function<V, String> {
        @Override
        public String apply(V value) {
            return value.toString();
        }
    }
}
