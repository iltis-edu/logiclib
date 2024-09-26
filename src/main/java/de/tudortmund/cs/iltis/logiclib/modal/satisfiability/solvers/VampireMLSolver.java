package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Invalid;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.ServerFailure;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Valid;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.CombinedSolver;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VampireMLSolver extends MLSolver {

    // the combined solver refers to the combination FO-Solver which uses vampire calls
    private CombinedSolver instanceFOSolver;

    public VampireMLSolver(
            int timeoutSecs,
            int vampireCores,
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed) {
        this.instanceFOSolver =
                new CombinedSolver(
                        timeoutSecs,
                        vampireCores,
                        universeSizes,
                        relationProbability,
                        triesUniverseSizeMultiplier,
                        seed);
    }

    public VampireMLSolver() {
        this(3, 1, new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)), 0.5, 100, 0);
    }

    @Override
    public Answer solve(final ModalFormula formula) {
        de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer answer =
                instanceFOSolver.solve(new MLFOEmbedding(formula).getEmbedding());

        switch (answer.getType()) {
            case VALID:
                return new Valid();
            case INVALID:
                return new Invalid();
            default:
                return new ServerFailure();
        }
    }
}
