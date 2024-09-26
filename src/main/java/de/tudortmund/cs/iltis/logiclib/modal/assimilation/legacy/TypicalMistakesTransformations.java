package de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.BuggyTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.ChildrenPatternTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.InvertibleTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.MetaTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.SequentialTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.UnaryInvertiblePatternTransformation;

/**
 * This class contains transformations for discovering typical mistakes with a {@link
 * FormulaAssimilator}. The transformations treat the first formula as the buggy one and the second
 * as the correct one.
 */
public class TypicalMistakesTransformations {

    /* negation */
    public static InvertibleTransformation getNegationTooMuch() {
        return BuggyTransformations.getRemoveNegation();
    }

    public static InvertibleTransformation getNegationMissing() {
        return BuggyTransformations.getAddNegation();
    }

    /* conjunction */
    public static InvertibleTransformation getEquivalenceInsteadOfConjunction() {
        return new UnaryInvertiblePatternTransformation("$X↔$Y", "$X∧$Y");
    }

    public static InvertibleTransformation getImplicationInsteadOfConjunction() {
        return new UnaryInvertiblePatternTransformation("$X→$Y", "$X∧$Y");
    }

    public static InvertibleTransformation getDisjunctionInsteadOfConjunction() {
        return new UnaryInvertiblePatternTransformation("*X∨...", "*X∧...");
    }

    public static InvertibleTransformation getNeitherNorInsteadOfConjunction() {
        return new UnaryInvertiblePatternTransformation("¬$X∧¬$Y", "$X∧$Y");
    }

    public static InvertibleTransformation getConjunctionInsteadOfNeitherNor() {
        return getNeitherNorInsteadOfConjunction().getInverse();
    }

    /* disjunction */
    public static InvertibleTransformation getEquivalenceInsteadOfDisjunction() {
        return new UnaryInvertiblePatternTransformation("$X↔$Y", "$X∨$Y");
    }

    public static InvertibleTransformation getImplicationInsteadOfDisjunction() {
        return new UnaryInvertiblePatternTransformation("$X→$Y", "$X∨$Y");
    }

    public static InvertibleTransformation getConjunctionInsteadOfDisjunction() {
        return new UnaryInvertiblePatternTransformation("*X∧...", "*X∨...");
    }

    public static InvertibleTransformation getExclusiveDisjunctionInsteadOfDisjunction() {
        return new UnaryInvertiblePatternTransformation("$X↔¬$Y", "$X∨$Y");
    }

    public static InvertibleTransformation getDisjunctionInsteadOfExclusiveDisjunction() {
        return getExclusiveDisjunctionInsteadOfDisjunction().getInverse();
    }

    /* implication */
    public static InvertibleTransformation getEquivalenceInsteadOfImplication() {
        return new UnaryInvertiblePatternTransformation("$X↔$Y", "$X→$Y");
    }

    public static InvertibleTransformation getConjunctionInsteadOfImplication() {
        return new UnaryInvertiblePatternTransformation("$X∧$Y", "$X→$Y");
    }

    public static InvertibleTransformation getDisjunctionInsteadOfImplication() {
        return new UnaryInvertiblePatternTransformation("$X∨$Y", "$X→$Y");
    }

    public static InvertibleTransformation getImplicationWrongDirection() {
        return new UnaryInvertiblePatternTransformation("$Y→$X", "$X→$Y");
    }

    /* equivalence */
    public static InvertibleTransformation getImplicationInsteadOfEquivalence() {
        return new UnaryInvertiblePatternTransformation("$X→$Y", "$X↔$Y");
    }

    public static InvertibleTransformation getConjunctionInsteadOfEquivalence() {
        return new UnaryInvertiblePatternTransformation("$X∧$Y", "$X↔$Y");
    }

    public static InvertibleTransformation getDisjunctionInsteadOfEquivalence() {
        return new UnaryInvertiblePatternTransformation("$X∨$Y", "$X↔$Y");
    }

    /*** MODAL ***/
    public static InvertibleTransformation getBoxInsteadOfDiamond() {
        return new UnaryInvertiblePatternTransformation("☐$X", "◇$X");
    }

    public static InvertibleTransformation getDiamondInsteadOfBox() {
        return getBoxInsteadOfDiamond().getInverse();
    }

    public static InvertibleTransformation getAddBox() {
        return new UnaryInvertiblePatternTransformation("$X", "☐$X");
    }

    public static InvertibleTransformation getAddDiamond() {
        return new UnaryInvertiblePatternTransformation("$X", "◇$X");
    }

    public static InvertibleTransformation getRemoveBox() {
        return getAddBox().getInverse();
    }

    public static InvertibleTransformation getRemoveDiamond() {
        return getAddDiamond().getInverse();
    }

    public static MetaTransformation getWrongDistributionBox() {
        SequentialTransformation wrongDistributionBox =
                new SequentialTransformation(
                        new UnaryInvertiblePatternTransformation("☐(*X∨...)", "(*X∨...)"),
                        new ChildrenPatternTransformation("($*∨...)", "$X", "☐$X"));

        SequentialTransformation wrongDistributionBoxInverse =
                new SequentialTransformation(
                        new ChildrenPatternTransformation("((☐$)*∨...)", "☐$X", "$X"),
                        new UnaryInvertiblePatternTransformation("(*X∨...)", "☐(*X∨...)"));

        return new MetaTransformation(wrongDistributionBox, wrongDistributionBoxInverse);
    }

    public static MetaTransformation getWrongDistributionDiamond() {
        SequentialTransformation wrongDistributionBox =
                new SequentialTransformation(
                        new UnaryInvertiblePatternTransformation("◇(*X∧...)", "(*X∧...)"),
                        new ChildrenPatternTransformation("($*∧...)", "$X", "◇$X"));

        SequentialTransformation wrongDistributionBoxInverse =
                new SequentialTransformation(
                        new ChildrenPatternTransformation("((◇$)*∧...)", "◇$X", "$X"),
                        new UnaryInvertiblePatternTransformation("(*X∧...)", "◇(*X∧...)"));

        return new MetaTransformation(wrongDistributionBox, wrongDistributionBoxInverse);
    }
}
