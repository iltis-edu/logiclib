package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.FailedSolving;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.InvalidFormulaWithCounterExample;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.utils.collections.Pair;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.*;

public class RandomModelSolver extends FOSolver {

    private Structure<Integer> model;

    private int triesUniverseSizeMultiplier;

    private Set<Integer> universeSizes;

    private double relationProbability;

    private Random random;

    // serialization
    public RandomModelSolver() {}

    public RandomModelSolver(
            int universeSize,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed) {
        this(
                Collections.singleton(universeSize),
                relationProbability,
                triesUniverseSizeMultiplier,
                seed);
    }

    public RandomModelSolver(
            Set<Integer> universeSizes,
            double relationProbability,
            int triesUniverseSizeMultiplier,
            long seed) {
        this.universeSizes = universeSizes;
        this.relationProbability = relationProbability;
        this.triesUniverseSizeMultiplier = triesUniverseSizeMultiplier;
        this.random = new Random();
        this.random.setSeed(seed);

        this.tupleBuffer = new HashMap<>();
    }

    @Override
    public Answer solve(final Formula formula) {
        Formula negatedFormula = formula.not();

        Signature signature = formula.getSignature();

        for (Integer universeSize : universeSizes) {
            for (int i = 0; i < triesUniverseSizeMultiplier * universeSize; i++) {
                createModelFromSignature(signature, universeSize);

                FormulaEvaluatorTopDown<Integer> evaluator =
                        new FormulaEvaluatorTopDown<>(negatedFormula);

                Set<Valuation<Integer>> valuations = evaluator.evaluate(model);
                if (!valuations.isEmpty()) {
                    return new InvalidFormulaWithCounterExample(
                            new Interpretation<>(model, valuations.iterator().next()));
                }
            }
        }

        return new FailedSolving();
    }

    public Structure<Integer> getModel() {
        return model;
    }

    private void createModelFromSignature(Signature signature, int universeSize) {
        Universe<Integer> universe = new IntUniverse(1, universeSize);

        Set<RelationSymbol> relationSymbolSet = signature.getRelSymbols();
        Map<RelationSymbol, RelationImplementation<Integer>> relations =
                new HashMap<>(relationSymbolSet.size());

        // fill relations
        for (RelationSymbol relationSymbol : relationSymbolSet) {
            int arity = relationSymbol.getArity();
            // the "* 1.1" acts as a buffer to counter statistical oscillations
            SetRelationImplementation<Integer> relationImplementation =
                    new SetRelationImplementation<>(
                            (int) Math.round((universeSize ^ arity) * relationProbability * 1.1));

            // use usual "=" interpretation
            if (relationSymbol.equals(new RelationSymbol("=", 2, true))) {
                for (int i = 1; i <= universeSize; i++) {
                    relationImplementation.add(new Tuple<>(i, i));
                }
            } else {
                for (Tuple<Integer> tuple : getAllTuples(arity, universeSize)) {
                    if (random.nextDouble() <= relationProbability) {
                        relationImplementation.add(tuple);
                    }
                }
            }

            relations.put(relationSymbol, relationImplementation);
        }

        Set<FunctionSymbol> functionSymbolSet = signature.getFunSymbols();
        Map<FunctionSymbol, FunctionImplementation<Integer>> functions =
                new HashMap<>(functionSymbolSet.size());

        for (FunctionSymbol functionSymbol : functionSymbolSet) {
            int arity = functionSymbol.getArity();
            FunctionImplementation<Integer> functionImplementation;
            if (arity > 0) {
                functionImplementation = new MapFunctionImplementation<>(universeSize ^ arity);
                for (Tuple<Integer> tuple : getAllTuples(arity, universeSize)) {
                    ((MapFunctionImplementation) functionImplementation)
                            .put(tuple, random.nextInt(universeSize) + 1);
                }
            } else {
                functionImplementation =
                        new ConstantFunctionImplementation<>(random.nextInt(universeSize) + 1);
            }

            functions.put(functionSymbol, functionImplementation);
        }

        model = new Structure<>(universe, relations, functions);
    }

    /**
     * creates a set of all possible tuples over the given universe with a specified size uses
     * buffering for same sized tuple requests
     *
     * @param tupleSize of the resulting tuples
     * @return all possible tuples
     */
    private Set<Tuple<Integer>> getAllTuples(int tupleSize, int universeSize) {
        Pair<Integer, Integer> currentSize = new Pair<>(tupleSize, universeSize);
        if (tupleBuffer.containsKey(currentSize)) {
            return tupleBuffer.get(currentSize);
        }

        Set<Tuple<Integer>> tuples = new HashSet<>(universeSize ^ tupleSize);

        Set<List<Integer>> tupleLists = getAllTuplesAsLists(tupleSize, universeSize);
        tupleLists.forEach(list -> tuples.add(new Tuple<>(list)));

        tupleBuffer.put(currentSize, tuples);

        return tuples;
    }

    private Set<List<Integer>> getAllTuplesAsLists(int size, int universeSize) {
        Set<List<Integer>> tupleLists = new HashSet<>(universeSize ^ size);
        if (size == 1) {
            for (int i = 1; i <= universeSize; i++) {
                List<Integer> baseList = new LinkedList<>();
                baseList.add(i);
                tupleLists.add(baseList);
            }
        } else {
            for (int i = 1; i <= universeSize; i++) {
                for (List<Integer> list : getAllTuplesAsLists(size - 1, universeSize)) {
                    list.add(i);
                    tupleLists.add(list);
                }
            }
        }
        return tupleLists;
    }

    private Map<Pair<Integer, Integer>, Set<Tuple<Integer>>> tupleBuffer;
}
