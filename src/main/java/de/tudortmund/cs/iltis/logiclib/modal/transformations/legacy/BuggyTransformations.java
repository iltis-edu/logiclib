package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains transformations that transform a formula into a non-equivalent one. These
 * transformations are close to {@link EquivalenceTransformations}, so they can be used to detect
 * mistakes made by users.
 */
public class BuggyTransformations {

    public static List<Transformation> getIdempotenceToTop() {
        List<Transformation> wrongIdempotence = new ArrayList<>();
        wrongIdempotence.add(new UnaryPatternTransformation("$X∧$X", "⊤"));
        wrongIdempotence.add(new UnaryPatternTransformation("$X∨$X", "⊤"));
        return wrongIdempotence;
    }

    public static List<Transformation> getFormulaToImplicationalTautology() {
        List<Transformation> wrongInverse = new ArrayList<>();
        wrongInverse.add(new UnaryPatternTransformation("$X", "$X → $X"));
        wrongInverse.add(new UnaryPatternTransformation("$X", "$X ↔ $X"));
        return wrongInverse;
    }

    public static List<Transformation> getReduceConjunctionWithNegation() {
        List<Transformation> wrongInverse = new ArrayList<>();
        wrongInverse.add(new UnaryPatternTransformation("$X∧¬$X", "⊤"));
        wrongInverse.add(new UnaryPatternTransformation("¬$X∧$X", "⊤"));
        wrongInverse.add(new UnaryPatternTransformation("$X∧¬$X", "$X"));
        wrongInverse.add(new UnaryPatternTransformation("¬$X∧$X", "$X"));
        wrongInverse.add(new UnaryPatternTransformation("($X∨$Y)∧(¬$X∨¬$Y)", "⊥"));
        wrongInverse.add(new UnaryPatternTransformation("(¬$X∨¬$Y)∧($X∨$Y)", "⊥"));
        return wrongInverse;
    }

    public static List<Transformation> getReduceDisjunctionWithNegation() {
        List<Transformation> wrongInverse = new ArrayList<>();
        wrongInverse.add(new UnaryPatternTransformation("$X∨¬$X", "⊥"));
        wrongInverse.add(new UnaryPatternTransformation("¬$X∨$X", "⊥"));
        wrongInverse.add(new UnaryPatternTransformation("$X∨¬$X", "$X"));
        wrongInverse.add(new UnaryPatternTransformation("¬$X∨$X", "$X"));
        wrongInverse.add(new UnaryPatternTransformation("($X∧$Y)∨(¬$X∧¬$Y)", "⊤"));
        wrongInverse.add(new UnaryPatternTransformation("(¬$X∧¬$Y)∨($X∧$Y)", "⊤"));
        return wrongInverse;
    }

    public static List<Transformation> getReduceTopInFormula() {
        List<Transformation> wrongTop = new ArrayList<>();
        wrongTop.add(new UnaryPatternTransformation("$X∨⊤", "$X"));
        wrongTop.add(new UnaryPatternTransformation("⊤∨$X", "$X"));
        wrongTop.add(new UnaryPatternTransformation("$X∧⊤", "⊤"));
        wrongTop.add(new UnaryPatternTransformation("⊤∧$X", "⊤"));
        return wrongTop;
    }

    public static List<Transformation> getReduceBottomInFormula() {
        List<Transformation> wrongBottom = new ArrayList<>();
        wrongBottom.add(new UnaryPatternTransformation("$X∨⊥", "⊥"));
        wrongBottom.add(new UnaryPatternTransformation("⊥∨$X", "⊥"));
        wrongBottom.add(new UnaryPatternTransformation("$X∧⊥", "$X"));
        wrongBottom.add(new UnaryPatternTransformation("⊥∧$X", "$X"));
        return wrongBottom;
    }

    public static List<Transformation> getSwapPremiseAndConclusion() {
        List<Transformation> implicationCommutativity = new ArrayList<>();
        implicationCommutativity.add(new UnaryPatternTransformation("$X → $Y", "$Y → $X"));
        return implicationCommutativity;
    }

    public static List<Transformation> getMoveParenthesisInMultiLayeredImplication() {
        List<Transformation> implicationAssociativity = new ArrayList<>();
        implicationAssociativity.add(
                new UnaryPatternTransformation("$X → ($Y → $Z)", "($X → $Y) → $Z"));
        implicationAssociativity.add(
                new UnaryPatternTransformation("($X → $Y) → $Z", "$X → ($Y → $Z)"));
        return implicationAssociativity;
    }

    public static List<Transformation> getReduceImplicationalTautology() {
        List<Transformation> implicationIdempotence = new ArrayList<>();
        implicationIdempotence.add(new UnaryPatternTransformation("$X → $X", "$X"));
        return implicationIdempotence;
    }

    public static List<Transformation> getReduceEquivalentTautology() {
        List<Transformation> equivalenceIdempotence = new ArrayList<>();
        equivalenceIdempotence.add(new UnaryPatternTransformation("$X ↔ $X", "$X"));
        return equivalenceIdempotence;
    }

    public static List<Transformation> getRemoveEquivalence() {
        List<Transformation> wrongElimination = new ArrayList<>();
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨¬($X∧$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨(¬$X∧$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨($X∧¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨($X∧$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨¬($X∨¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∨$Y)∧(¬$X∨¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∧(¬$X∧¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "($X∧$Y)∨(¬$X∨¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X ↔ $Y", "¬$X∨$Y"));
        return wrongElimination;
    }

    public static List<Transformation> getRemoveImplication() {
        List<Transformation> wrongElimination = new ArrayList<>();
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "¬($X∨$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "$X∨$Y"));
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "¬($X∧$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "$X∨¬$Y"));
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "¬$X∧$Y"));
        wrongElimination.add(new UnaryPatternTransformation("$X → $Y", "($X∧$Y)∨(¬$X∧¬$Y)"));
        wrongElimination.add(new UnaryPatternTransformation("($X∨$Y) → $Z", "($X∨¬$Y)∨$Z"));
        wrongElimination.add(new UnaryPatternTransformation("($X∧$Y) → $Z", "($X∧¬$Y)∨$Z"));
        wrongElimination.add(new UnaryPatternTransformation("($X → $Y) → $Z", "($X → ¬$Y)∨$Z"));
        wrongElimination.add(new UnaryPatternTransformation("($X ↔ $Y) → $Z", "($X ↔ ¬$Y)∨$Z"));
        return wrongElimination;
    }

    public static List<Transformation> getReduceToImplication() {
        List<Transformation> wrongIntroduction = new ArrayList<>();
        wrongIntroduction.add(new UnaryPatternTransformation("¬$X∧$Y", "$X → $Y"));
        wrongIntroduction.add(new UnaryPatternTransformation("$X∨¬$Y", "$X → $Y"));
        wrongIntroduction.add(new UnaryPatternTransformation("$X∧¬$Y", "$Y → $X"));
        wrongIntroduction.add(new UnaryPatternTransformation("¬$X∨$Y", "$Y → $X"));
        wrongIntroduction.add(new UnaryPatternTransformation("¬($X∨$Y)", "$X → $Y"));
        return wrongIntroduction;
    }

    public static List<Transformation> getPushNegation() {
        List<Transformation> wrongDeMorgan = new ArrayList<>();
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∧$Y)", "¬$X∨$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∧$Y)", "$X∨¬$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∧$Y)", "$X∨$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∨$Y)", "¬$X∧$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∨$Y)", "$X∧¬$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∨$Y)", "$X∧$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∧$Y)", "¬(¬$X∨¬$Y)"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∨$Y)", "¬(¬$X∧¬$Y)"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∧$Y)", "¬$X∧¬$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X∨$Y)", "¬$X∨¬$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬(¬($X∧$Y)∨$Z)", "¬(¬$X∨¬$Y)∨$Z"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬(¬($X∧$Y)∧$Z)", "¬(¬$X∨¬$Y)∧$Z"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬(¬($X∨$Y)∨$Z)", "¬(¬$X∧¬$Y)∨$Z"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬(¬($X∨$Y)∧$Z)", "¬(¬$X∧¬$Y)∧$Z"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X → $Y)", "¬$X → ¬$Y"));
        wrongDeMorgan.add(new UnaryPatternTransformation("¬($X ↔ $Y)", "¬($X∧$Y)∨(¬$X∧¬$Y)"));
        return wrongDeMorgan;
    }

    public static List<Transformation> getPullNegation() {
        List<Transformation> wrongInverse = new ArrayList<>();
        wrongInverse.add(new UnaryPatternTransformation("¬$X∧¬$Y", "¬($X∧$Y)"));
        wrongInverse.add(new UnaryPatternTransformation("¬$X∨¬$Y", "¬($X∨$Y)"));
        return wrongInverse;
    }

    public static List<Transformation> getRemoveParenthesis() {
        List<Transformation> wrongParenthesis = new ArrayList<>();
        wrongParenthesis.add(new UnaryPatternTransformation("¬($X∧$Y)", "¬$X∧$Y"));
        wrongParenthesis.add(new UnaryPatternTransformation("¬($X∨$Y)", "¬$X∨$Y"));
        wrongParenthesis.add(new UnaryPatternTransformation("¬(¬$X∧$Y)", "$X∧$Y"));
        wrongParenthesis.add(new UnaryPatternTransformation("¬(¬$X → $Y)", "$X → $Y"));
        wrongParenthesis.add(new UnaryPatternTransformation("¬(¬$X ↔ $Y)", "$X ↔ $Y"));
        return wrongParenthesis;
    }

    public static List<Transformation> getMoveParenthesisInsteadOfDistribution() {
        List<Transformation> wrongAssociativity = new ArrayList<>();
        wrongAssociativity.add(new UnaryPatternTransformation("$X∨($Y∧$Z)", "($X∨$Y)∧$Z"));
        wrongAssociativity.add(new UnaryPatternTransformation("($X∨$Y)∧$Z", "$X∨($Y∧$Z)"));
        wrongAssociativity.add(new UnaryPatternTransformation("($X∧$Y)∨$Z", "$X∧($Y∨$Z)"));
        wrongAssociativity.add(new UnaryPatternTransformation("$X∧($Y∨$Z)", "($X∧$Y)∨$Z"));
        return wrongAssociativity;
    }

    public static List<Transformation> getReduceToSubformula() {
        List<Transformation> wrongAbsorption = new ArrayList<>();
        wrongAbsorption.add(new UnaryPatternTransformation("($X∨$Y)∧(($X∧$Y)∧$Z)", "$X∨$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($X∧$Y)∨(($X∨$Y)∧$Z)", "$X∧$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($X∨$Y)∧(($X∧$Y)∨$Z)", "$X∨$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($X∧$Y)∨(($X∨$Y)∨$Z)", "$X∧$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("$X∨($X∧$Y)", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("$X∨($Y∧$X)", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($X∧$Y)∨$X", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($Y∧$X)∨$X", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("$X∧($X∨$Y)", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("$X∧($Y∨$X)", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($X∨$Y)∧$X", "$Y"));
        wrongAbsorption.add(new UnaryPatternTransformation("($Y∨$X)∧$X", "$Y"));
        return wrongAbsorption;
    }

    public static List<Transformation> getPushDistributionToWrongTarget() {
        List<Transformation> wrongDistribution = new ArrayList<>();
        wrongDistribution.add(new UnaryPatternTransformation("$X∧($Y∨$Z)", "($X∧$Y)∧($X∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∨$Y)∧$Z", "($X∧$Z)∧($Y∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("$X∧($Y∨$Z)", "($X∨$Y)∧($X∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∨$Y)∧$Z", "($X∨$Z)∧($Y∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("$X∨($Y∧$Z)", "($X∨$Y)∨($X∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∧$Y)∨$Z", "($X∨$Z)∨($Y∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("$X∨($Y∧$Z)", "($X∧$Y)∨($X∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∧$Y)∨$Z", "($X∧$Z)∨($Y∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("$X∧($Y∧$Z)", "($X∧$Y)∧($X∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∧$Y)∧$Z", "($X∧$Z)∧($Y∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("$X∨($Y∨$Z)", "($X∨$Y)∨($X∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∨$Y)∨$Z", "($X∨$Z)∨($Y∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("¬$X∧($Y∨$Z)", "(¬$X∧$Y)∨($X∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("¬$X∧($Y∨$Z)", "($X∧$Y)∨(¬$X∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∨$Y)∧¬$Z", "($X∧¬$Z)∨($Y∧$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∨$Y)∧¬$Z", "($X∧$Z)∨($Y∧¬$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("¬$X∨($Y∧$Z)", "(¬$X∨$Y)∧($X∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("¬$X∨($Y∧$Z)", "($X∨$Y)∧(¬$X∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∧$Y)∨¬$Z", "($X∨¬$Z)∧($Y∨$Z)"));
        wrongDistribution.add(new UnaryPatternTransformation("($X∧$Y)∨¬$Z", "($X∨$Z)∧($Y∨¬$Z)"));
        return wrongDistribution;
    }

    public static List<Transformation> getPullDistributionToWrongTarget() {
        List<Transformation> wrongInverse = new ArrayList<>();
        wrongInverse.add(new UnaryPatternTransformation("($X∧$Y)∧($X∧$Z)", "$X∧($Y∨$Z)"));
        wrongInverse.add(new UnaryPatternTransformation("($X∧$Z)∧($Y∧$Z)", "($X∨$Y)∧$Z"));
        wrongInverse.add(new UnaryPatternTransformation("($X∨$Y)∧($X∨$Z)", "$X∧($Y∨$Z)"));
        wrongInverse.add(new UnaryPatternTransformation("($X∨$Z)∧($Y∨$Z)", "($X∨$Y)∨$Z"));
        wrongInverse.add(new UnaryPatternTransformation("($X∨$Y)∨($X∨$Z)", "$X∨($Y∧$Z)"));
        wrongInverse.add(new UnaryPatternTransformation("($X∨$Z)∨($Y∨$Z)", "($X∧$Y)∨$Z"));
        wrongInverse.add(new UnaryPatternTransformation("($X∧$Y)∨($X∧$Z)", "$X∨($Y∧$Z)"));
        wrongInverse.add(new UnaryPatternTransformation("($X∧$Z)∨($Y∧$Z)", "($X∧$Y)∨$Z"));
        return wrongInverse;
    }

    /* DBG */
    public static List<InvertibleTransformation> getReplaceImplication() {
        List<InvertibleTransformation> replaceImplication =
                new ArrayList<InvertibleTransformation>();
        replaceImplication.add(new UnaryInvertiblePatternTransformation("$X → $Y", "$X∨$Y"));
        replaceImplication.add(new UnaryInvertiblePatternTransformation("$X → $Y", "$X∨¬$Y"));
        replaceImplication.add(new UnaryInvertiblePatternTransformation("$X → $Y", "¬$X∧$Y"));
        replaceImplication.add(new UnaryInvertiblePatternTransformation("$X → $Y", "¬($X∨$Y)"));
        replaceImplication.add(new UnaryInvertiblePatternTransformation("$X → $Y", "¬($X∧$Y)"));
        return replaceImplication;
    }

    public static List<InvertibleTransformation> getReplaceEquivalence() {
        List<InvertibleTransformation> replaceEquivalence =
                new ArrayList<InvertibleTransformation>();
        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∧$Y)∨¬($X∧$Y)"));
        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∧$Y)∨(¬$X∧$Y)"));
        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∧$Y)∨($X∧¬$Y)"));
        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∧$Y)∨($X∧$Y)"));

        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∨$Y)∧(¬$X∨¬$Y)"));
        replaceEquivalence.add(
                new UnaryInvertiblePatternTransformation("$X ↔ $Y", "($X∧$Y)∧(¬$X∧¬$Y)"));
        return replaceEquivalence;
    }

    public static InvertibleMetaTransformation getMoveParenthesis() {
        return new InvertibleMetaTransformation(
                new UnaryInvertiblePatternTransformation("(¬$X)∧*Y", "¬($X∧*Y)"),
                new UnaryInvertiblePatternTransformation("(¬$X)∨*Y", "¬($X∨*Y)"),
                new UnaryInvertiblePatternTransformation("(¬$X)→$Y", "¬($X→$Y)"),
                new UnaryInvertiblePatternTransformation("*X∧($Y→$Z)", "(*X∧$Y)→$Z"),
                new UnaryInvertiblePatternTransformation("($X→$Y)∧*Z", "$X→($Y∧*Z)"),
                new UnaryInvertiblePatternTransformation("*X∨($Y→$Z)", "(*X∨$Y)→$Z"),
                new UnaryInvertiblePatternTransformation("($X→$Y)∨*Z", "$X→($Y∨*Z)"),
                new UnaryInvertiblePatternTransformation("*X∧($Y↔$Z)", "(*X∧$Y)↔$Z"),
                new UnaryInvertiblePatternTransformation("($X↔$Y)∧*Z", "$X↔($Y∧*Z)"),
                new UnaryInvertiblePatternTransformation("*X∨($Y↔$Z)", "(*X∨$Y)↔$Z"),
                new UnaryInvertiblePatternTransformation("($X↔$Y)∨*Z", "$X↔($Y∨*Z)"));
    }

    public static InvertibleTransformation getReverseImplication() {
        return new UnaryInvertiblePatternTransformation("$X → $Y", "$Y → $X");
    }

    public static TransformationWithNewFormula getAddToImplication() {
        return new UnaryPatternTransformationWithNewFormula("$X", "$X→$Y", "Y");
    }

    public static InvertibleTransformation getRemoveNegation() {
        InvertibleTransformation removeNegation =
                new UnaryInvertiblePatternTransformation("¬$X", "$X");
        return removeNegation;
    }

    public static InvertibleTransformation getAddNegation() {
        return getRemoveNegation().getInverse();
    }

    public static List<Transformation> getRemoveSubformula() {
        List<Transformation> removeSubformula = new ArrayList<Transformation>();
        removeSubformula.add(new UnaryPatternTransformation("($U∧*X∧$Y∧*Z)", "($U∧*X∧*Z)"));
        removeSubformula.add(new UnaryPatternTransformation("(*X∧$Y∧$U∧*Z)", "(*X∧$U∧*Z)"));
        removeSubformula.add(new UnaryPatternTransformation("($U∨*X∨$Y∨*Z)", "($U∨*X∨*Z)"));
        removeSubformula.add(new UnaryPatternTransformation("(*X∨$Y∨$U∨*Z)", "(*X∨$U∨*Z)"));
        removeSubformula.add(new UnaryPatternTransformation("($X∧$Y)", "$X"));
        removeSubformula.add(new UnaryPatternTransformation("($X∨$Y)", "$X"));
        removeSubformula.add(new UnaryPatternTransformation("($X→$Y)", "$X"));
        removeSubformula.add(new UnaryPatternTransformation("($X↔$Y)", "$X"));
        removeSubformula.add(new UnaryPatternTransformation("($X∧$Y)", "$Y"));
        removeSubformula.add(new UnaryPatternTransformation("($X∨$Y)", "$Y"));
        removeSubformula.add(new UnaryPatternTransformation("($X→$Y)", "$Y"));
        removeSubformula.add(new UnaryPatternTransformation("($X↔$Y)", "$Y"));
        return removeSubformula;
    }

    public static List<TransformationWithNewFormula> getAddSubformula() {
        List<TransformationWithNewFormula> addSubformula =
                new ArrayList<TransformationWithNewFormula>();
        addSubformula.add(new UnaryPatternTransformationWithNewFormula("*X∧...", "*X∧$Y", "Y"));
        addSubformula.add(new UnaryPatternTransformationWithNewFormula("*X∨...", "*X∨$Y", "Y"));
        addSubformula.add(new UnaryPatternTransformationWithNewFormula("$X", "$X→$Y", "Y"));
        addSubformula.add(new UnaryPatternTransformationWithNewFormula("$X", "$X↔$Y", "Y"));
        return addSubformula;
    }

    public static List<InvertibleTransformation> getReplaceOperator() {
        List<InvertibleTransformation> replaceOperator = new ArrayList<InvertibleTransformation>();
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ↔ $Y", "$X → $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ↔ $Y", "$X ∧ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ↔ $Y", "$X ∨ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X → $Y", "$X ↔ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X → $Y", "$X ∧ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X → $Y", "$X ∨ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ∨ $Y", "$X ↔ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ∨ $Y", "$X → $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("*X∨...", "*X∧..."));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ∧ $Y", "$X ↔ $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("$X ∧ $Y", "$X → $Y"));
        replaceOperator.add(new UnaryInvertiblePatternTransformation("*X∧...", "*X∨..."));
        return replaceOperator;
    }
}
