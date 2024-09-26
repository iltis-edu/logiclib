package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.BoxPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.ConjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.DiamondPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.DisjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.EquivalencePattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.FalsePattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.ImplicationPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.NegationPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.TruePattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.VariablePattern;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.ExactNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AlternativePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ComplementPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ContainsDescendantPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FixedArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FlexibleArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.MultiConstraintPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.RepeatForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator for modal logical patterns, using {@link PatternCreatorType}. Used in the parsing
 * process.
 */
public class PatternCreator extends ParsingCreator {

    /**
     * Registers the creator functions that are needed by {@link PatternParserConstructionVisitor}.
     */
    @SuppressWarnings("unchecked")
    public PatternCreator() {
        registerVararyFunction(
                PatternCreatorType.ALTERNATIVE,
                patterns -> new AlternativePattern<>(toNonNullPatterns(patterns)));
        registerVararyFunctionWithName(
                PatternCreatorType.ALTERNATIVE,
                (name, patterns) -> new AlternativePattern<>(name, toNonNullPatterns(patterns)));
        registerVararyFunction(
                PatternCreatorType.MULTI_CONSTRAINT,
                patterns -> new MultiConstraintPattern<>(toNonNullPatterns(patterns)));
        registerVararyFunctionWithName(
                PatternCreatorType.MULTI_CONSTRAINT,
                (name, patterns) ->
                        new MultiConstraintPattern<>(name, toNonNullPatterns(patterns)));
        registerUnaryFunction(
                PatternCreatorType.CONTAINS_DESCENDANT,
                pattern -> new ContainsDescendantPattern<>(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.CONTAINS_DESCENDANT,
                (name, pattern) ->
                        new ContainsDescendantPattern<>(name, toNonNullPattern(pattern)));
        registerVararyFunction(
                PatternCreatorType.CONJUNCTION,
                patterns ->
                        new ConjunctionPattern(
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(patterns)))));
        registerVararyFunctionWithName(
                PatternCreatorType.CONJUNCTION,
                (name, patterns) ->
                        new ConjunctionPattern(
                                name,
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(patterns)))));
        registerVararyFunction(
                PatternCreatorType.DISJUNCTION,
                patterns ->
                        new DisjunctionPattern(
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(patterns)))));
        registerVararyFunctionWithName(
                PatternCreatorType.DISJUNCTION,
                (name, patterns) ->
                        new DisjunctionPattern(
                                name,
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(patterns)))));
        registerBinaryFunction(
                PatternCreatorType.IMPLICATION,
                (left, right) ->
                        new ImplicationPattern(
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(left), toNonNullPattern(right)))));
        registerBinaryFunctionWithName(
                PatternCreatorType.IMPLICATION,
                (name, left, right) ->
                        new ImplicationPattern(
                                name,
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(left), toNonNullPattern(right)))));
        registerBinaryFunction(
                PatternCreatorType.EQUIVALENCE,
                (left, right) ->
                        new EquivalencePattern(
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(left), toNonNullPattern(right)))));
        registerBinaryFunctionWithName(
                PatternCreatorType.EQUIVALENCE,
                (name, left, right) ->
                        new EquivalencePattern(
                                name,
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(left), toNonNullPattern(right)))));
        registerUnaryFunction(
                PatternCreatorType.REPEAT_FOREST,
                pattern -> new RepeatForestPattern<>(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.REPEAT_FOREST,
                (name, pattern) -> new RepeatForestPattern<>(name, toNonNullPattern(pattern)));
        registerConstant(
                PatternCreatorType.ANY_FORMULA,
                () ->
                        new PredicatePattern<ModalFormula>(
                                forest -> forest.size() == 1 && forest.get(0) != null));
        registerConstantWithName(
                PatternCreatorType.ANY_FORMULA,
                name ->
                        new PredicatePattern<ModalFormula>(
                                name, forest -> forest.size() == 1 && forest.get(0) != null));
        registerConstant(PatternCreatorType.TRUE, TruePattern::new);
        registerConstant(PatternCreatorType.FALSE, FalsePattern::new);
        registerConstantWithName(PatternCreatorType.TRUE, TruePattern::new);
        registerConstantWithName(PatternCreatorType.FALSE, FalsePattern::new);
        registerUnaryFunction(
                PatternCreatorType.VARIABLE,
                namePattern ->
                        new VariablePattern(
                                (NamePattern<ModalFormula, IndexedSymbol>) namePattern));
        registerConstantWithName(PatternCreatorType.ANY, AnyPattern::new);
        registerUnaryFunction(
                PatternCreatorType.NEGATION,
                pattern -> new NegationPattern(new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerUnaryFunctionWithName(
                PatternCreatorType.NEGATION,
                (name, pattern) ->
                        new NegationPattern(
                                name, new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerUnaryFunction(
                PatternCreatorType.BOX, pattern -> new BoxPattern(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.BOX,
                (name, pattern) ->
                        new BoxPattern(name, new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerUnaryFunction(
                PatternCreatorType.DIAMOND,
                pattern -> new DiamondPattern(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.DIAMOND,
                (name, pattern) ->
                        new DiamondPattern(name, new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerUnaryFunction(
                PatternCreatorType.COMPLEMENT,
                pattern -> new ComplementPattern<>(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.COMPLEMENT,
                (name, pattern) -> new ComplementPattern<>(name, toNonNullPattern(pattern)));
        registerConstantWithName(PatternCreatorType.EXACT_NAME, ExactNamePattern::new);
        registerConstantWithName(PatternCreatorType.ANY_NAME, AnyNamePattern::new);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<ModalFormula> toNonNullPattern(ParsablyTyped pattern) {

        return pattern == null
                ? new AnyPattern<>(new IndexedSymbol("< unknown pattern >"))
                : (TreePattern<ModalFormula>) pattern;
    }

    private List<TreePattern<ModalFormula>> toNonNullPatterns(List<ParsablyTyped> patterns) {

        List<TreePattern<ModalFormula>> result = new ArrayList<>();

        if (patterns == null) {
            result.add(toNonNullPattern(null));
        } else {
            for (ParsablyTyped pattern : patterns) {
                result.add(toNonNullPattern(pattern));
            }
        }

        return result;
    }
}
