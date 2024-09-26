package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.*;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.callables.*;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * This is our most powerful FO-Sat-Solver. Since our experiments have shown, that there is no
 * single best solver and each solver may have different modes with highly incomparable results, we
 * call our most reliable methods in parallel here. This way we can decide any request if any solver
 * in any mode can decide it. If any solver produced a counter model, we also return it. Only iff
 * all solvers fail, we cannot decide the request.
 */
public class CombinedSolver extends FOSolver {

    private int timeoutSecs, vampireCores, triesUniverseSizeMultiplier;
    private Set<Integer> universeSizes;
    private double relationProbability;
    private long seed;

    public CombinedSolver(
            int timeoutSecs,
            int vampireCores,
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed) {
        this.relationProbability = relationProbability;
        this.triesUniverseSizeMultiplier = triesUniverseSizeMultiplier;
        this.seed = seed;
        this.timeoutSecs = timeoutSecs;
        this.vampireCores = vampireCores;
        this.universeSizes = universeSizes;
    }

    public CombinedSolver() {
        this(5, 1, new HashSet<>(Arrays.asList(1, 2, 3, 4)), 0.5, 200, 0);
    }

    @Override
    public Answer solve(final Formula formula) {
        return solveWithThreads(
                new RandomModelCallable(
                        universeSizes,
                        relationProbability,
                        triesUniverseSizeMultiplier,
                        seed,
                        formula),
                Arrays.asList(
                        new VampireModelCallable(timeoutSecs, vampireCores, "casc_sat", formula),
                        new VampireCallable(timeoutSecs, vampireCores, "casc", formula),
                        new VampireCallable(timeoutSecs, vampireCores, "casc_sat", formula),
                        new VampireCallable(timeoutSecs, vampireCores, "vampire", formula)));
    }

    @Override
    public Answer solveEquivalenceUnderConstraints(
            final Formula formula1, final Formula formula2, List<Formula> constraints) {
        if (constraints == null || constraints.isEmpty()) {
            return solve(new Equivalence(formula1, formula2));
        }

        return solveWithThreads(
                new RandomModelEquivalenceCallable(
                        universeSizes,
                        relationProbability,
                        triesUniverseSizeMultiplier,
                        seed,
                        formula1,
                        formula2,
                        constraints),
                Arrays.asList(
                        new VampireModelEquivalenceCallable(
                                timeoutSecs,
                                vampireCores,
                                "casc_sat",
                                formula1,
                                formula2,
                                constraints),
                        new VampireEquivalenceCallable(
                                timeoutSecs, vampireCores, "casc", formula1, formula2, constraints),
                        new VampireEquivalenceCallable(
                                timeoutSecs,
                                vampireCores,
                                "casc_sat",
                                formula1,
                                formula2,
                                constraints),
                        new VampireEquivalenceCallable(
                                timeoutSecs,
                                vampireCores,
                                "vampire",
                                formula1,
                                formula2,
                                constraints)));
    }

    /**
     * This tries to calculate the 'best' answer while waiting for no unnecessary amount of time.
     * This means: - If we obtain the 'Valid' result we know that there cannot be a model and can
     * instantly return it - If we obtain the 'InvalidWithCounterModel' result we already have the
     * best answer and instantly return it - If we obtain the 'Invalid' (without counter model)
     * result we wait for the
     *
     * @param randomModelCallable the RandomModelCallable
     * @param vampireCallables all Vampire callables
     * @return the best calculated answer
     * @throws Exception may be thrown from threads
     */
    private Answer solveWithThreads(
            Callable<SerializablePair<Answer, Boolean>> randomModelCallable,
            List<Callable<SerializablePair<Answer, Boolean>>> vampireCallables) {
        int nThreads = 1 + vampireCallables.size();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        CompletionService<SerializablePair<Answer, Boolean>> completionService =
                new ExecutorCompletionService<>(executorService);

        completionService.submit(
                new IndividuallyTimedoutCallable(randomModelCallable, timeoutSecs, true));

        vampireCallables.forEach(completionService::submit);

        Answer answer = new FailedSolving();
        Answer previousAnswer = new FailedSolving();

        // the boolean is used to indicate if the answer was produced by a random model
        SerializablePair<Answer, Boolean> answerCalculationTypePair;
        boolean randomModelFinished = false;

        try {
            for (int i = 0; i < nThreads; i++) {
                // no waiting necessary since it is handled by each thread individually
                answerCalculationTypePair = completionService.take().get();

                if (!(answer instanceof ServerFailure) && !(answer instanceof FailedSolving)) {
                    previousAnswer = answer;
                }

                answer = answerCalculationTypePair.first();
                randomModelFinished = randomModelFinished || answerCalculationTypePair.second();

                if (answer instanceof ValidFormula
                        || answer instanceof InvalidFormulaWithCounterExample) {
                    executorService.shutdownNow();
                    return answer;
                }

                if (randomModelFinished && previousAnswer instanceof InvalidFormula) {
                    executorService.shutdownNow();
                    return previousAnswer;
                }
            }
            executorService.shutdownNow();
            return new FailedSolving();
        } catch (ExecutionException | InterruptedException e) {
            executorService.shutdownNow();
            return new FailedSolving();
        }
    }

    // this class is used to have a single timeout be usable for all threads combined
    private class IndividuallyTimedoutCallable
            implements Callable<SerializablePair<Answer, Boolean>> {

        private Callable<SerializablePair<Answer, Boolean>> callable;

        private int timeoutSecs;

        private boolean useRandomModel;

        IndividuallyTimedoutCallable(
                Callable<SerializablePair<Answer, Boolean>> callable,
                int timeoutSecs,
                boolean useRandomModel) {
            this.callable = callable;
            this.timeoutSecs = timeoutSecs;
            this.useRandomModel = useRandomModel;
        }

        @Override
        public SerializablePair<Answer, Boolean> call() throws Exception {
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            Future<SerializablePair<Answer, Boolean>> possibleAnswer =
                    executorService.submit(callable);

            try {
                SerializablePair<Answer, Boolean> answer =
                        possibleAnswer.get(timeoutSecs, TimeUnit.SECONDS);
                executorService.shutdownNow();
                return answer;
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                executorService.shutdownNow();
                return new SerializablePair<>(new FailedSolving(), useRandomModel);
            }
        }
    }
}
