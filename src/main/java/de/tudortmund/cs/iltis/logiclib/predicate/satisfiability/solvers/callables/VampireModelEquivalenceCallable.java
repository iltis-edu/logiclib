package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.VampireModelSolver;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.List;
import java.util.concurrent.Callable;

public class VampireModelEquivalenceCallable
        implements Callable<SerializablePair<Answer, Boolean>> {

    private Formula formula1, formula2;
    private List<Formula> constraints;
    private int timeoutSecs, cores;
    private String mode;

    public VampireModelEquivalenceCallable(
            int timeoutSecs,
            int cores,
            String mode,
            Formula formula1,
            Formula formula2,
            List<Formula> constraints) {
        this.cores = cores;
        this.timeoutSecs = timeoutSecs;
        this.mode = mode;
        this.formula1 = formula1;
        this.formula2 = formula2;
        this.constraints = constraints;
    }

    @Override
    public SerializablePair<Answer, Boolean> call() throws Exception {
        return new SerializablePair<>(
                VampireModelSolver.getInstance()
                        .solveEquivalenceUnderConstraintsWithParameters(
                                formula1, formula2, constraints, mode, timeoutSecs, cores),
                false);
    }
}
