package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.utils.collections.SetOperations;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Evaluates the formula on a given structure to all valuations of the formula's free variables that
 * satisfy the formula in the given structure.
 */
public class FormulaEvaluator {

    private Formula formula;

    public FormulaEvaluator(Formula formula) {
        this.formula = formula;
    }

    /**
     * Evaluate the formula on the given structure.
     *
     * @param structure the structure to evaluate the formula in
     * @param <T> the type of elements the universe of the structure contains
     * @return all valuations of the formula's free variables that satisfy the formula in the given
     *     structure
     */
    public <T extends Comparable<T>> Set<Valuation<T>> evaluate(Structure<T> structure) {
        return formula.traverse(new EvaluationStrategy<>(structure));
    }

    /**
     * Strategy to evaluate the formula on a given structure bottom up.
     *
     * @param <T> the type of elements the universe of the structure contains
     */
    private static class EvaluationStrategy<T extends Comparable<T>>
            implements TraversalStrategy<TermOrFormula, Set<Valuation<T>>> {

        private Structure<T> structure;

        public EvaluationStrategy(Structure<T> structure) {
            this.structure = structure;
        }

        @Override
        public Set<Valuation<T>> inspect(
                TermOrFormula subformula, List<Set<Valuation<T>>> childrenOutput) {
            if (subformula.isTrue()) {
                return inspect((True) subformula, childrenOutput);
            }
            if (subformula.isFalse()) {
                return inspect((False) subformula, childrenOutput);
            }
            if (subformula.isRelation()) {
                return inspect((RelationAtom) subformula, childrenOutput);
            }
            if (subformula.isNegation()) {
                return inspect((Negation) subformula, childrenOutput);
            }
            if (subformula.isConjunction()) {
                return inspect((Conjunction) subformula, childrenOutput);
            }
            if (subformula.isDisjunction()) {
                return inspect((Disjunction) subformula, childrenOutput);
            }
            if (subformula.isImplication()) {
                return inspect((Implication) subformula, childrenOutput);
            }
            if (subformula.isEquivalence()) {
                return inspect((Equivalence) subformula, childrenOutput);
            }
            if (subformula.isExistentialQuantifier()) {
                return inspect((ExistentialQuantifier) subformula, childrenOutput);
            }
            if (subformula.isUniversalQuantifier()) {
                return inspect((UniversalQuantifier) subformula, childrenOutput);
            }
            if (subformula.isTerm()) {
                // will be evaluated in RelationAtom
                return Collections.emptySet();
            }
            throw new RuntimeException("Unexpected type of formula.");
        }

        protected Set<Valuation<T>> inspect(
                True subformula, List<Set<Valuation<T>>> childrenOutput) {
            Set<Valuation<T>> results = new TreeSet<>();
            results.addAll(
                    (new Valuation<T>())
                            .getExtensions(subformula.getFreeVariableSymbols(), structure));
            return results;
        }

        protected Set<Valuation<T>> inspect(
                False subformula, List<Set<Valuation<T>>> childrenOutput) {
            return Collections.emptySet();
        }

        /**
         * Evaluates a relation atom by iterating through each interpretation and (1) evaluating the
         * child terms for the current interpretation and (2) checking if the tuple that consists of
         * the child term results is contained in the relation.
         */
        protected Set<Valuation<T>> inspect(
                RelationAtom relationAtom, List<Set<Valuation<T>>> childrenOutput) {
            RelationImplementation<T> relation = structure.getRelation(relationAtom.getName());
            List<TermEvaluator> childEvaluators =
                    relationAtom.getChildren().stream()
                            .map(child -> new TermEvaluator((Term) child))
                            .collect(Collectors.toList());
            Function<Interpretation<T>, List<T>> evaluateChildren =
                    interpretation ->
                            childEvaluators.stream()
                                    .map(childEvaluator -> childEvaluator.evaluate(interpretation))
                                    .collect(Collectors.toList());
            ValuationIterator<T> valuationIterator =
                    new ValuationIterator<>(structure, relationAtom.getFreeVariableSymbols());
            return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    valuationIterator, Spliterator.ORDERED),
                            false)
                    .filter(
                            valuation -> {
                                Interpretation<T> interpretation =
                                        new Interpretation<>(structure, valuation);
                                Tuple<T> tuple =
                                        new Tuple<>(evaluateChildren.apply(interpretation));
                                return relation.contains(tuple);
                            })
                    .collect(Collectors.toSet());
        }

        protected Set<Valuation<T>> inspect(
                Negation negation, List<Set<Valuation<T>>> childrenOutput) {
            ValuationIterator<T> valuationIterator =
                    new ValuationIterator<>(structure, negation.getFreeVariableSymbols());
            Set<Valuation<T>> childValuations =
                    extendValuations(childrenOutput.get(0), negation.getFreeVariableSymbols());
            return SetOperations.getComplement(valuationIterator, childValuations);
        }

        protected Set<Valuation<T>> inspect(
                Conjunction conjunction, List<Set<Valuation<T>>> childrenOutput) {
            Set<Valuation<T>> allValuations =
                    (new Valuation<T>())
                            .getExtensions(conjunction.getFreeVariableSymbols(), structure);
            return childrenOutput.stream()
                    .map(
                            valuations ->
                                    extendValuations(
                                            valuations, conjunction.getFreeVariableSymbols()))
                    .reduce(
                            allValuations,
                            (result, next) -> SetOperations.getIntersection(result, next));
        }

        protected Set<Valuation<T>> inspect(
                Disjunction disjunction, List<Set<Valuation<T>>> childrenOutput) {
            Set<Valuation<T>> noValuation = Collections.emptySet();
            return childrenOutput.stream()
                    .map(
                            valuations ->
                                    extendValuations(
                                            valuations, disjunction.getFreeVariableSymbols()))
                    .reduce(noValuation, (result, next) -> SetOperations.getUnion(result, next));
        }

        /** Evaluates an implication A -> B by transforming it into (not A) or B. */
        protected Set<Valuation<T>> inspect(
                Implication implication, List<Set<Valuation<T>>> childrenOutput) {
            ValuationIterator<T> valuationIterator =
                    new ValuationIterator<>(structure, implication.getFreeVariableSymbols());
            Set<Valuation<T>> premise =
                    extendValuations(childrenOutput.get(0), implication.getFreeVariableSymbols());
            Set<Valuation<T>> premiseComplement =
                    SetOperations.getComplement(valuationIterator, premise);
            Set<Valuation<T>> conclusion =
                    extendValuations(childrenOutput.get(1), implication.getFreeVariableSymbols());
            return SetOperations.getUnion(premiseComplement, conclusion);
        }

        /**
         * Evaluates an equivalence A <-> B by transforming it into (A and B) or ((not A) and (not
         * B))
         */
        protected Set<Valuation<T>> inspect(
                Equivalence equivalence, List<Set<Valuation<T>>> childrenOutput) {
            Set<Valuation<T>> valuations1 =
                    extendValuations(childrenOutput.get(0), equivalence.getFreeVariableSymbols());
            Set<Valuation<T>> valuations2 =
                    extendValuations(childrenOutput.get(1), equivalence.getFreeVariableSymbols());
            Set<Valuation<T>> bothTrue = SetOperations.getIntersection(valuations1, valuations2);
            ValuationIterator<T> valuationIterator =
                    new ValuationIterator<>(structure, equivalence.getFreeVariableSymbols());
            Set<Valuation<T>> bothFalse =
                    SetOperations.getComplementIntersection(
                            valuationIterator, valuations1, valuations2);
            return SetOperations.getUnion(bothTrue, bothFalse);
        }

        protected Set<Valuation<T>> inspect(
                ExistentialQuantifier existentialQuantifier,
                List<Set<Valuation<T>>> childrenOutput) {
            return childrenOutput.get(1).stream()
                    .map(
                            valuation -> {
                                valuation.undefine(existentialQuantifier.getVariable().getName());
                                return valuation;
                            })
                    .collect(Collectors.toSet());
        }

        protected Set<Valuation<T>> inspect(
                UniversalQuantifier universalQuantifier, List<Set<Valuation<T>>> childrenOutput) {
            ValuationIterator<T> valuationIterator =
                    new ValuationIterator<T>(
                            structure, universalQuantifier.getFreeVariableSymbols());
            return StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    valuationIterator, Spliterator.ORDERED),
                            false)
                    .filter(
                            valuation ->
                                    childrenOutput
                                            .get(1)
                                            .containsAll(
                                                    valuation.getExtensions(
                                                            universalQuantifier
                                                                    .getVariable()
                                                                    .getName(),
                                                            structure)))
                    .collect(Collectors.toSet());
        }

        private Set<Valuation<T>> extendValuations(
                Set<Valuation<T>> valuations, Set<VariableSymbol> symbols) {
            return valuations.stream()
                    .map(valuation -> valuation.getExtensions(symbols, structure))
                    .flatMap(extensions -> extensions.stream())
                    .collect(Collectors.toSet());
        }
    }
}
