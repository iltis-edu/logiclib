package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.RandomModelSolver;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.Set;
import java.util.concurrent.Callable;

public class RandomModelCallable implements Callable<SerializablePair<Answer, Boolean>> {

    private Formula formula;
    private RandomModelSolver solver;

    public RandomModelCallable(
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed,
            Formula formula) {
        this.solver =
                new RandomModelSolver(
                        universeSizes, relationProbability, triesUniverseSizeMultiplier, seed);
        this.formula = formula;
    }

    @Override
    public SerializablePair<Answer, Boolean> call() throws Exception {
        return new SerializablePair<>(solver.solve(formula), true);
    }
}
