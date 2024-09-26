package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Satisfiable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.ServerFailure;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Unsatisfiable;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.VampireSolver;
import de.tudortmund.cs.iltis.utils.weblib.WebLib;
import de.tudortmund.cs.iltis.utils.weblib.WebLibManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class VampirePLSolver extends PLSolver {

    private static VampirePLSolver instance;

    public static VampirePLSolver getInstance() {
        if (instance == null) {
            instance = new VampirePLSolver();
        }
        return instance;
    }

    private VampirePLSolver() {
        super();

        // this refers to the same vampire instance as the FO vampire solver and therefore uses the
        // same resource
        InputStream inputStream =
                VampireSolver.class.getResourceAsStream(
                        "/de/tudortmund/cs/iltis/logiclib/predicate/satisfiability/solvers/vampireConfig.xml");

        WebLibManager libs = WebLibManager.initFromConfig(inputStream).get();

        WebLib vampire = libs.getLibrary("vampire").get();

        function = vampire.getFunction("checkSmtlib").get();
    }

    @Override
    protected Answer checkSat(ModalFormula formula) {
        return solveSmtString(PLSmtlibFormulaWriter.translateFormulaToSmt(formula), 2, 1);
    }

    /**
     * directly uses a smt-string as input to the weblib
     *
     * @param smt smt-string
     * @param timeoutSecs vampire timeout
     * @param cores cores to be used (WARNING: always use 1 here unless you absolutely know what you
     *     are doing)
     * @return
     */
    public Answer solveSmtString(String smt, int timeoutSecs, int cores) {
        String request = getDefinitionPrefix(timeoutSecs, cores) + smt + getConstantPostfix();

        function.call(request, handler);

        if (!answer.isPresent()) {
            return new ServerFailure();
        }

        String answerString = answer.get();

        if (answerString.startsWith("{\"result\": \"SATISFIABLE")) {
            return new Satisfiable(
                    extractValuations(
                            answerString.substring(
                                    "{\"result\": \"SATISFIABLE\n".length() + 1,
                                    answerString.length() - 2)));
        }

        if (answerString.equals("{\"result\": \"UNSATISFIABLE\"}")) {
            return new Unsatisfiable();
        }

        return new ServerFailure();
    }

    private Collection<Valuation> extractValuations(String workingString) {
        // TODO: get this working (somehow)
        return new ArrayList<>();
    }

    private String getDefinitionPrefix(int timeoutSecs, int cores) {
        return "{\n\"mode\" : \"vampire\",\n \"timeout\" : \""
                + timeoutSecs
                + "s\",\n "
                + "\"fmb\" : \"True\",\n \"cores\" : \""
                + cores
                + "\",\n \"formula\" : \"";
    }

    private String getConstantPostfix() {
        return "\\n(check-sat)\"\n}";
    }
}
