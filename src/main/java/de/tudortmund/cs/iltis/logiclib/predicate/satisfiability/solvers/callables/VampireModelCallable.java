package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.VampireModelSolver;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.concurrent.Callable;

public class VampireModelCallable implements Callable<SerializablePair<Answer, Boolean>> {

    private Formula formula;
    private int timeoutSecs, cores;
    private String mode;

    public VampireModelCallable(int timeoutSecs, int cores, String mode, Formula formula) {
        this.cores = cores;
        this.timeoutSecs = timeoutSecs;
        this.mode = mode;
        this.formula = formula;
    }

    @Override
    public SerializablePair<Answer, Boolean> call() throws Exception {
        return new SerializablePair<>(
                VampireModelSolver.getInstance()
                        .solveWithParameters(formula, mode, timeoutSecs, cores),
                false);
    }
}
