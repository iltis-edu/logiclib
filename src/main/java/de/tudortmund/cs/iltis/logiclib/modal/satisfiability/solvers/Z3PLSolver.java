package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Satisfiable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.ServerFailure;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Unsatisfiable;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.Z3Solver;
import de.tudortmund.cs.iltis.utils.weblib.WebLib;
import de.tudortmund.cs.iltis.utils.weblib.WebLibManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Z3PLSolver extends PLSolver {

    private static Z3PLSolver instance;

    public static Z3PLSolver getInstance() {
        if (instance == null) {
            instance = new Z3PLSolver();
        }
        return instance;
    }

    private Z3PLSolver() {
        super();

        // this refers to the same z3 instance as the FO z3 solver and therefore uses the same
        // resource
        InputStream inputStream =
                Z3Solver.class.getResourceAsStream(
                        "/de/tudortmund/cs/iltis/logiclib/predicate/satisfiability/solvers/z3Config.xml");

        WebLibManager libs = WebLibManager.initFromConfig(inputStream).get();

        WebLib z3 = libs.getLibrary("z3").get();

        function = z3.getFunction("checkSmtlib").get();
    }

    @Override
    protected Answer checkSat(ModalFormula formula) {
        return solveSmtString(PLSmtlibFormulaWriter.translateFormulaToSmt(formula), 1000);
    }

    /**
     * directly uses a smt-string as input to the weblib
     *
     * @param smt smt-string
     * @param timeoutMsecs z3 timeout
     * @return
     */
    public Answer solveSmtString(String smt, int timeoutMsecs) {
        String request = getDefinitionPrefix(timeoutMsecs) + smt + getConstantPostfix();

        function.call(request, handler);

        if (!answer.isPresent()) {
            return new ServerFailure();
        }

        String answerString = answer.get();

        if (answerString.startsWith("{\"result\":\"SATISFIABLE")) {
            return new Satisfiable(
                    extractValuations(
                            answerString.substring(
                                    "{\"result\":\"SATISFIABLE\n".length() + 1,
                                    answerString.length() - 2)));
        }

        if (answerString.equals("{\"result\":\"UNSATISFIABLE\"}")) {
            return new Unsatisfiable();
        }

        return new ServerFailure();
    }

    private Collection<Valuation> extractValuations(String workingString) {
        workingString = workingString.replaceAll("\\(define-fun ", "");
        workingString = workingString.replaceAll(" \\(\\) Bool", "");
        workingString = workingString.replaceAll("\\)", "");
        workingString = workingString.replaceAll("\\\\n", " ");
        workingString = workingString.replaceAll("\\s{2,}", " ").trim();

        String[] parts = workingString.split(" ");
        Iterator<String> iterator = Arrays.stream(parts).iterator();

        Valuation assignment = new Valuation();
        while (iterator.hasNext()) {
            Variable variable = new Variable(iterator.next());
            assignment.define(variable, "true".equals(iterator.next()));
        }

        // we currently only return one assignment
        ArrayList<Valuation> singleValuation = new ArrayList<>(1);
        singleValuation.add(assignment);
        return singleValuation;
    }

    private String getDefinitionPrefix(int timeoutMsecs) {
        return "{\n\"options\" : [\"-inputFormat=pri\", \"-quiet\", \"-timeout="
                + timeoutMsecs
                + "\"],\n \"formula\" : \"";
    }

    private String getConstantPostfix() {
        return "\"\n}";
    }
}
