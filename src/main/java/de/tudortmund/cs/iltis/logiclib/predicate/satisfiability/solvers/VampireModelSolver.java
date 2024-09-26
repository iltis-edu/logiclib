package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.InvalidFormula;
import de.tudortmund.cs.iltis.utils.weblib.WebLib;
import de.tudortmund.cs.iltis.utils.weblib.WebLibManager;
import java.io.InputStream;
import java.util.List;

public class VampireModelSolver extends SmtlibSolver {

    private static VampireModelSolver instance = new VampireModelSolver();

    private VampireModelSolver() {
        super(true);
    }

    public static VampireModelSolver getInstance() {
        return instance;
    }

    @Override
    protected void initConnection() {
        sort = "CustomSort";
        writer = new FOSmtlibFormulaWriter(sort);

        InputStream inputStream =
                VampireModelSolver.class.getResourceAsStream(
                        "/de/tudortmund/cs/iltis/logiclib/predicate/satisfiability/solvers/vampireConfig.xml");

        WebLibManager libs = WebLibManager.initFromConfig(inputStream).get();

        WebLib vampire = libs.getLibrary("vampire").get();

        function = vampire.getFunction("checkSmtlib").get();
    }

    @Override
    protected Answer parseModel(String modelOutput) {
        // TODO: parse model
        return new InvalidFormula();
    }

    public Answer solveWithParameters(
            final Formula formula, String mode, int timeoutSecs, int cores) {
        // since the smtlib input format is used to check for satisfiability
        // we need to check if the negation is unsatisfiable
        String smtlibInput =
                getDefinitionPrefix(mode, timeoutSecs, cores)
                        + translateToSmtlibInput(formula, false)
                        + getConstantPostfix();

        return callWeblibWithInput(smtlibInput);
    }

    public Answer solveEquivalenceUnderConstraintsWithParameters(
            final Formula formula1,
            final Formula formula2,
            List<Formula> constraints,
            String mode,
            int timeoutSecs,
            int cores) {
        if (constraints.isEmpty()) {
            return solveEquivalence(formula1, formula2);
        }

        String smtlibInput =
                getDefinitionPrefix(mode, timeoutSecs, cores)
                        + translateToSmtlibInput(formula1, formula2, constraints, false)
                        + getConstantPostfix();

        return callWeblibWithInput(smtlibInput);
    }

    @Override
    protected String getConstantDefinitionPrefix() {
        return getDefinitionPrefix("casc_sat", 1, 1);
    }

    private String getDefinitionPrefix(String mode, int timeoutSecs, int cores) {
        return "{\n\"mode\" : \""
                + mode
                + "\",\n \"timeout\" : \""
                + timeoutSecs
                + "s\",\n "
                + "\"fmb\" : \"True\",\n \"cores\" : \""
                + cores
                + "\",\n \"formula\" : \"";
    }
}
