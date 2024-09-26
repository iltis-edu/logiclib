package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.RandomModelSolver;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class RandomModelEquivalenceCallable implements Callable<SerializablePair<Answer, Boolean>> {

    private Formula formula1, formula2;
    private List<Formula> constraints;
    private RandomModelSolver solver;

    public RandomModelEquivalenceCallable(
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed,
            Formula formula1,
            Formula formula2,
            List<Formula> constraints) {
        this.solver =
                new RandomModelSolver(
                        universeSizes, relationProbability, triesUniverseSizeMultiplier, seed);
        this.formula1 = formula1;
        this.formula2 = formula2;
        this.constraints = constraints;
    }

    @Override
    public SerializablePair<Answer, Boolean> call() throws Exception {
        return new SerializablePair<>(
                solver.solveEquivalenceUnderConstraints(formula1, formula2, constraints), true);
    }
}
