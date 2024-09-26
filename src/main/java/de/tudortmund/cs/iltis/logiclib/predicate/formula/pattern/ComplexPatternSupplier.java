package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.List;

/**
 * This a collection of some patterns used in FO transformations that cannot be specified with a
 * parsed string directly
 */
public class ComplexPatternSupplier {

    public static TreePattern<TermOrFormula> getPatternById(String id) {
        TreePattern<TermOrFormula> pattern;

        switch (id) {
            case "WeakDistributeUniversalQuantifierDisjunctionMatch":
                pattern = getWeakDistributeQuantifierMatch("forall", "∨");
                break;
            case "WeakDistributeExistentialQuantifierConjunctionMatch":
                pattern = getWeakDistributeQuantifierMatch("exists", "∧");
                break;
            case "WeakUndistributeUniversalQuantifierDisjunctionMatch":
                pattern = getWeakUndistributeQuantifierMatch("forall", "∨");
                break;
            case "WeakUndistributeExistentialQuantifierConjunctionMatch":
                pattern = getWeakUndistributeQuantifierMatch("exists", "∧");
                break;
            case "WeakDistributeUniversalQuantifierConjunctionMatch":
                pattern = getWeakDistributeQuantifierMatch("forall", "∧");
                break;
            case "WeakDistributeExistentialQuantifierDisjunctionMatch":
                pattern = getWeakDistributeQuantifierMatch("exists", "∨");
                break;
            case "WeakUndistributeUniversalQuantifierConjunctionMatch":
                pattern = getWeakUndistributeQuantifierMatch("forall", "∧");
                break;
            case "WeakUndistributeExistentialQuantifierDisjunctionMatch":
                pattern = getWeakUndistributeQuantifierMatch("exists", "∨");
                break;
            case "BuggyAddUniversalQuantifierMatch":
                pattern = getBuggyAddUniversalQuantifierMatch();
                break;
            case "BuggyAddExistentialQuantifierMatch":
                pattern = getBuggyAddExistentialQuantifierMatch();
                break;
            case "ReduceUnnecessaryUniversalQuantifierMatch":
                pattern = getReduceUnnecessaryUniversalQuantifierMatch();
                break;
            case "ReduceUnnecessaryExistentialQuantifierMatch":
                pattern = getReduceUnnecessaryExistentialQuantifierMatch();
                break;
            case "AddUnnecessaryUniversalQuantifierMatch":
                pattern = getAddUnnecessaryUniversalQuantifierMatch();
                break;
            case "AddUnnecessaryExistentialQuantifierMatch":
                pattern = getAddUnnecessaryExistentialQuantifierMatch();
                break;
            default:
                throw new IllegalArgumentException("There is no pattern with the given id " + id);
        }

        return pattern;
    }

    /**
     * @return matchPattern: a pattern for weak distribution of specified quantifier and PL operator
     *     types
     */
    private static TreePattern<TermOrFormula> getWeakDistributeQuantifierMatch(
            String quantifier, String plOperator) {
        TreePattern<TermOrFormula> patternNoFreeConstriant =
                new PatternReader()
                        .read(quantifier + " x (*A " + plOperator + " $X " + plOperator + " *B)");
        return addNoFreeVariableConstraint(patternNoFreeConstriant, "A", "B");
    }

    /**
     * @return matchPattern: a pattern for weak undistribution of specified quantifier and PL
     *     operator types
     */
    private static TreePattern<TermOrFormula> getWeakUndistributeQuantifierMatch(
            String quantifier, String plOperator) {
        TreePattern<TermOrFormula> patternNoFreeConstriant =
                new PatternReader()
                        .read(
                                " *A "
                                        + plOperator
                                        + " "
                                        + quantifier
                                        + " x $X "
                                        + plOperator
                                        + " *B");
        return addNoFreeVariableConstraint(patternNoFreeConstriant, "A", "B");
    }

    private static TreePattern<TermOrFormula> addNoFreeVariableConstraint(
            TreePattern<TermOrFormula> basePattern, String... symbols) {
        List<IndexedSymbol> treesToBeChecked = new ArrayList<>();
        for (String symbol : symbols) {
            treesToBeChecked.add(new IndexedSymbol(symbol));
        }
        return new NoFreeVariablePattern(basePattern, treesToBeChecked, new IndexedSymbol("x"));
    }

    private static TreePattern<TermOrFormula> getBuggyAddUniversalQuantifierMatch() {
        return getAnyTreeFreeVariablePattern("X", "x");
    }

    private static TreePattern<TermOrFormula> getBuggyAddExistentialQuantifierMatch() {
        return getAnyTreeFreeVariablePattern("X", "x");
    }

    private static TreePattern<TermOrFormula> getReduceUnnecessaryUniversalQuantifierMatch() {
        TreePattern<TermOrFormula> patternNoFreeConstriant =
                new PatternReader().read("forall x $X");
        List<IndexedSymbol> treesToBeChecked = new ArrayList<>();
        treesToBeChecked.add(new IndexedSymbol("X"));
        return new NoFreeVariablePattern(
                patternNoFreeConstriant, treesToBeChecked, new IndexedSymbol("x"));
    }

    private static TreePattern<TermOrFormula> getReduceUnnecessaryExistentialQuantifierMatch() {
        TreePattern<TermOrFormula> patternNoFreeConstriant =
                new PatternReader().read("exists x $X");
        List<IndexedSymbol> treesToBeChecked = new ArrayList<>();
        treesToBeChecked.add(new IndexedSymbol("X"));
        return new NoFreeVariablePattern(
                patternNoFreeConstriant, treesToBeChecked, new IndexedSymbol("x"));
    }

    private static TreePattern<TermOrFormula> getAddUnnecessaryUniversalQuantifierMatch() {
        return getAnyTreeNoFreeVariablePattern("X", "x");
    }

    private static TreePattern<TermOrFormula> getAddUnnecessaryExistentialQuantifierMatch() {
        return getAnyTreeNoFreeVariablePattern("X", "x");
    }

    private static TreePattern<TermOrFormula> getAnyTreeNoFreeVariablePattern(
            String treeName, String variableName) {
        TreePattern<TermOrFormula> patternNoFreeConstraint =
                new PatternReader().read("$" + treeName);

        List<IndexedSymbol> treesToBeChecked = new ArrayList<>();
        treesToBeChecked.add(new IndexedSymbol(treeName));

        return new NoFreeVariablePattern(
                patternNoFreeConstraint, treesToBeChecked, new IndexedSymbol(variableName));
    }

    private static TreePattern<TermOrFormula> getAnyTreeFreeVariablePattern(
            String treeName, String variableName) {
        TreePattern<TermOrFormula> patternNoFreeConstraint =
                new PatternReader().read("$" + treeName);

        List<IndexedSymbol> treesToBeChecked = new ArrayList<>();
        treesToBeChecked.add(new IndexedSymbol(treeName));

        return new FreeVariablePattern(
                patternNoFreeConstraint, treesToBeChecked, new IndexedSymbol(variableName));
    }
}
