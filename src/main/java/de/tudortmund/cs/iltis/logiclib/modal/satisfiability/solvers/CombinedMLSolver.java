package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.*;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables.EquivalenceTableauCallable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables.TableauCallable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables.VampireMLCallable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/** Combines FO-based ML-Solving with our tableaus in parallel */
public class CombinedMLSolver extends MLSolver {

    private int timeoutSecs;

    private VampireMLSolver vampireMLSolver;

    public CombinedMLSolver(
            int timeoutSecs,
            int vampireCores,
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed) {
        this.vampireMLSolver =
                new VampireMLSolver(
                        timeoutSecs,
                        vampireCores,
                        universeSizes,
                        relationProbability,
                        triesUniverseSizeMultiplier,
                        seed);
        this.timeoutSecs = timeoutSecs;
    }

    public CombinedMLSolver() {
        this(3, 1, new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)), 0.5, 100, 0);
    }

    @Override
    public Answer solve(final ModalFormula formula) {
        return solveWithThreads(
                new TableauCallable(formula.not()),
                new VampireMLCallable(vampireMLSolver, formula));
    }

    // overriding this is not necessary but improves tableau performance
    @Override
    public Answer solveEquivalence(final ModalFormula formula1, final ModalFormula formula2) {
        return solveWithThreads(
                new EquivalenceTableauCallable(formula1, formula2),
                new VampireMLCallable(vampireMLSolver, new Equivalence(formula1, formula2)));
    }

    private Answer solveWithThreads(Callable<Answer> tableauCallable, Callable<Answer> foCallable) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<Answer> completionService =
                new ExecutorCompletionService<>(executorService);

        completionService.submit(tableauCallable);

        completionService.submit(foCallable);

        try {
            Answer answer = completionService.take().get(timeoutSecs, TimeUnit.SECONDS);
            executorService.shutdownNow();
            return answer;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            executorService.shutdownNow();
            return new ServerFailure();
        }
    }
}
