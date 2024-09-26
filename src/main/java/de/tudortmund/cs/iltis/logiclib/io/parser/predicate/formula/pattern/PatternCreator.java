package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.*;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.ExactNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator for predicate logical patterns, using {@link PatternCreatorType}. Used in the parsing
 * process.
 */
public class PatternCreator extends ParsingCreator {

    /**
     * Registers a creator function for each type of {@link PatternCreatorType}. Due to the lack of
     * a signature, all constant symbols will be interpreted as variables (because constants and
     * variables cannot be differentiated without a signature).
     */
    public PatternCreator() {
        this(new Signature());
    }

    /**
     * Registers the creator functions that are needed by {@link PatternParserConstructionVisitor}.
     */
    @SuppressWarnings("unchecked")
    public PatternCreator(SignatureCheckable signature) {
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
                        new PredicatePattern<TermOrFormula>(
                                forest -> forest.size() == 1 && forest.get(0) instanceof Formula));
        registerConstantWithName(
                PatternCreatorType.ANY_FORMULA,
                name ->
                        new PredicatePattern<TermOrFormula>(
                                name,
                                forest -> forest.size() == 1 && forest.get(0) instanceof Formula));
        registerConstant(PatternCreatorType.TRUE, TruePattern::new);
        registerConstant(PatternCreatorType.FALSE, FalsePattern::new);
        registerConstantWithName(PatternCreatorType.TRUE, TruePattern::new);
        registerConstantWithName(PatternCreatorType.FALSE, FalsePattern::new);
        registerUnaryFunction(
                PatternCreatorType.VARIABLE,
                namePattern ->
                        new VariablePattern(
                                (NamePattern<TermOrFormula, IndexedSymbol>) namePattern));
        registerVararyFunction(
                PatternCreatorType.RELATION,
                patterns ->
                        new RelationAtomPattern(
                                (NamePattern<TermOrFormula, IndexedSymbol>) patterns.get(0),
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(
                                                        patterns.subList(1, patterns.size()))))));
        registerVararyFunctionWithName(
                PatternCreatorType.RELATION,
                (name, patterns) ->
                        new RelationAtomPattern(
                                name,
                                (NamePattern<TermOrFormula, IndexedSymbol>) patterns.get(0),
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(
                                                        patterns.subList(1, patterns.size()))))));
        registerConstantWithName(PatternCreatorType.ANY, AnyPattern::new);
        registerUnaryFunction(
                PatternCreatorType.NEGATION,
                pattern -> new NegationPattern(new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerUnaryFunctionWithName(
                PatternCreatorType.NEGATION,
                (name, pattern) ->
                        new NegationPattern(
                                name, new ChildrenPattern<>(toNonNullPattern(pattern))));
        registerBinaryFunction(
                PatternCreatorType.UNIVERSAL_QUANTIFIER,
                (varPattern, subPattern) ->
                        new UniversalQuantifierPattern(
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(varPattern),
                                                toNonNullPattern(subPattern)))));
        registerBinaryFunctionWithName(
                PatternCreatorType.UNIVERSAL_QUANTIFIER,
                (name, varPattern, subPattern) ->
                        new UniversalQuantifierPattern(
                                name,
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(varPattern),
                                                toNonNullPattern(subPattern)))));
        registerBinaryFunction(
                PatternCreatorType.EXISTENTIAL_QUANTIFIER,
                (varPattern, subPattern) ->
                        new ExistentialQuantifierPattern(
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(varPattern),
                                                toNonNullPattern(subPattern)))));
        registerBinaryFunctionWithName(
                PatternCreatorType.EXISTENTIAL_QUANTIFIER,
                (name, varPattern, subPattern) ->
                        new ExistentialQuantifierPattern(
                                name,
                                new ChildrenPattern<>(
                                        new FixedArityForestPattern<>(
                                                toNonNullPattern(varPattern),
                                                toNonNullPattern(subPattern)))));
        registerUnaryFunction(
                PatternCreatorType.COMPLEMENT,
                pattern -> new ComplementPattern<>(toNonNullPattern(pattern)));
        registerUnaryFunctionWithName(
                PatternCreatorType.COMPLEMENT,
                (name, pattern) -> new ComplementPattern<>(name, toNonNullPattern(pattern)));
        registerConstant(
                PatternCreatorType.ANY_TERM,
                () ->
                        new PredicatePattern<TermOrFormula>(
                                forest -> forest.size() == 1 && forest.get(0) instanceof Term));
        registerConstantWithName(
                PatternCreatorType.ANY_TERM,
                name ->
                        new PredicatePattern<TermOrFormula>(
                                name,
                                forest -> forest.size() == 1 && forest.get(0) instanceof Term));
        registerVararyFunction(
                PatternCreatorType.FUNCTION,
                patterns ->
                        new FunctionTermPattern(
                                (NamePattern<TermOrFormula, IndexedSymbol>) patterns.get(0),
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(
                                                        patterns.subList(1, patterns.size()))))));
        registerVararyFunctionWithName(
                PatternCreatorType.FUNCTION,
                (name, patterns) ->
                        new FunctionTermPattern(
                                name,
                                (NamePattern<TermOrFormula, IndexedSymbol>) patterns.get(0),
                                new ChildrenPattern<>(
                                        new FlexibleArityForestPattern<>(
                                                toNonNullPatterns(
                                                        patterns.subList(1, patterns.size()))))));
        registerConstantWithName(PatternCreatorType.EXACT_NAME, ExactNamePattern::new);
        registerConstantWithName(PatternCreatorType.ANY_NAME, AnyNamePattern::new);
        registerUnaryFunction(
                PatternCreatorType.QUANTIFIED_CONSTANT,
                (namePattern) ->
                        createQuantifiedConstant(
                                signature,
                                (NamePattern<TermOrFormula, IndexedSymbol>) namePattern));
        registerUnaryFunctionWithName(
                PatternCreatorType.QUANTIFIED_CONSTANT,
                (name, namePattern) ->
                        createQuantifiedConstant(
                                signature,
                                name,
                                (NamePattern<TermOrFormula, IndexedSymbol>) namePattern));
    }

    private TreePattern<TermOrFormula> createQuantifiedConstant(
            SignatureCheckable signature, NamePattern<TermOrFormula, IndexedSymbol> namePattern) {

        IndexedSymbol name;

        if (namePattern instanceof ExactNamePattern) {
            name = namePattern.createName(null);
        } else {

            name =
                    ((AnyNamePattern<TermOrFormula, IndexedSymbol>) namePattern)
                            .getIDToMatchForName();
        }

        if (signature.containsSymbolAsRelation(name)) {
            return new RelationAtomPattern(namePattern);
        }

        if (signature.containsSymbolAsFunction(name)) {
            return new FunctionTermPattern(namePattern);
        }

        return new VariablePattern(namePattern);
    }

    private TreePattern<TermOrFormula> createQuantifiedConstant(
            SignatureCheckable signature,
            IndexedSymbol patternName,
            NamePattern<TermOrFormula, IndexedSymbol> namePattern) {

        IndexedSymbol name;

        if (namePattern instanceof ExactNamePattern) {
            name = namePattern.createName(null);
        } else {

            name =
                    ((AnyNamePattern<TermOrFormula, IndexedSymbol>) namePattern)
                            .getIDToMatchForName();
        }

        if (signature.containsSymbolAsRelation(name)) {

            return new RelationAtomPattern(
                    patternName,
                    namePattern,
                    new ChildrenPattern<>(new FixedArityForestPattern<TermOrFormula>()));
        }

        if (signature.containsSymbolAsFunction(name)) {

            return new FunctionTermPattern(
                    patternName,
                    namePattern,
                    new ChildrenPattern<>(new FixedArityForestPattern<TermOrFormula>()));
        }

        return new VariablePattern(patternName, namePattern);
    }

    @SuppressWarnings("unchecked")
    private TreePattern<TermOrFormula> toNonNullPattern(ParsablyTyped pattern) {

        return pattern == null
                ? new AnyPattern<>(new IndexedSymbol("< unknown pattern >"))
                : (TreePattern<TermOrFormula>) pattern;
    }

    private List<TreePattern<TermOrFormula>> toNonNullPatterns(List<ParsablyTyped> patterns) {

        List<TreePattern<TermOrFormula>> result = new ArrayList<>();

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
