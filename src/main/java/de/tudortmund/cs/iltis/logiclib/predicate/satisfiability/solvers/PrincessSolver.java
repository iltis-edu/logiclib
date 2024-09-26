package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.FailedSolving;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.InvalidFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.ValidFormula;
import de.tudortmund.cs.iltis.utils.weblib.WebLib;
import de.tudortmund.cs.iltis.utils.weblib.WebLibManager;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

public class PrincessSolver extends FOSolver {

    // to avoid the establishment of multiple connections, this is handled as a singleton
    private static PrincessSolver instance = new PrincessSolver();

    public static PrincessSolver getInstance() {
        return instance;
    }

    private FOPrincessFormulaWriter writer;

    private PrincessSolver() {
        super();
        sort = "int";
        writer = new FOPrincessFormulaWriter(sort);

        InputStream inputStream =
                PrincessSolver.class.getResourceAsStream(
                        "/de/tudortmund/cs/iltis/logiclib/predicate/satisfiability/solvers/princessConfig.xml");

        WebLibManager libs = WebLibManager.initFromConfig(inputStream).get();

        WebLib princess = libs.getLibrary("princess").get();

        function = princess.getFunction("checkFormula").get();
    }

    @Override
    public Answer solve(final Formula formula) {
        String translatedFormula = formatInput(formula);

        function.call(translatedFormula, handler);

        if (!answer.isPresent()) {
            return new FailedSolving();
        }

        String answerString = answer.get();

        if (answerString.equals("{\"result\":\"INVALID\\n\"}")) {
            return new InvalidFormula();
        }

        if (answerString.equals("{\"result\":\"VALID\\n\"}")) {
            return new ValidFormula();
        }

        // TODO: Constraint extraction
        return new InvalidFormula();
    }

    private String formatInput(final Formula formula) {
        StringBuilder formattedInput = new StringBuilder();

        formattedInput.append("{\n\"options\" : [\"-inputFormat=pri\", \"-quiet\"],\n");

        formattedInput.append("\"formula\" : \"");

        if (sort.equals("CustomSort")) {
            formattedInput.append("\\\\sorts { CustomSort;}");
        }

        Set<Variable> freeVariables = formula.getFreeVariables();
        if (!freeVariables.isEmpty()) {
            formattedInput.append("\\\\existentialConstants { " + sort + " ");

            Iterator<Variable> variableIterator = freeVariables.iterator();
            formattedInput.append(variableIterator.next().toTextString());
            while (variableIterator.hasNext()) {
                formattedInput.append(", ");
                formattedInput.append(variableIterator.next().toTextString());
            }

            formattedInput.append("; }");
        }

        Set<RelationSymbol> relationSymbols = formula.getRelationSymbols();
        if (!relationSymbols.isEmpty()) {
            formattedInput.append(" \\\\predicates { ");

            for (RelationSymbol current : relationSymbols) {
                // "=" is already known
                if (current.equals(new RelationSymbol("=", 2, true))) {
                    continue;
                }

                formattedInput.append(current.toTextString());
                int arity = current.getArity();
                if (arity > 0) {
                    formattedInput.append("(");

                    for (int i = 0; i < arity - 1; i++) {
                        formattedInput.append(sort + ", ");
                    }

                    formattedInput.append(sort + " )");
                }
                formattedInput.append("; ");
            }

            formattedInput.append(" } ");
        }

        Set<FunctionSymbol> functionSymbols = formula.getFunctionSymbols();
        if (!functionSymbols.isEmpty()) {
            formattedInput.append(" \\\\functions { ");

            for (FunctionSymbol current : functionSymbols) {
                formattedInput.append(sort + " ");
                formattedInput.append(current.toTextString());
                int arity = current.getArity();
                if (arity > 0) {
                    formattedInput.append("(");

                    for (int i = 0; i < arity - 1; i++) {
                        formattedInput.append(sort + ", ");
                    }

                    formattedInput.append(sort + " )");
                }
                formattedInput.append("; ");
            }

            formattedInput.append(" } ");
        }

        formattedInput.append("\\\\problem {");
        formattedInput.append(writer.write(formula));
        formattedInput.append("}\"\n}");

        return formattedInput.toString();
    }
}
