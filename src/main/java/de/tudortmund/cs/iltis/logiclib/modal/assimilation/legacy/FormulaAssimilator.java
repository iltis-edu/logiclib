package de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.BuggyTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.IterativeTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.TransformationWithNewFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FormulaAssimilator implements Serializable {

    /* Need for serialization */
    @SuppressWarnings("unused")
    public FormulaAssimilator() {}

    public FormulaAssimilator(
            ModalFormula target, List<Transformation> corrections, AssimilationStrategy strategy) {
        this.target = target.clone();
        loadFilledCorrections(corrections, target.getVariables());
        this.strategy = strategy;
    }

    public static FormulaAssimilator syntax(
            ModalFormula target, int maxEquivalenceTransformationCount) {
        return syntax(
                target,
                getStandardCorrections(target.isPropositional()),
                maxEquivalenceTransformationCount);
    }

    public static FormulaAssimilator syntax(
            ModalFormula target,
            List<Transformation> corrections,
            int maxEquivalenceTransformationCount) {
        return new FormulaAssimilator(
                target,
                corrections,
                new SyntaxAssimilationStrategy(target, maxEquivalenceTransformationCount));
    }

    public static FormulaAssimilator semantic(ModalFormula target) {
        return semantic(target, getStandardCorrections(target.isPropositional()));
    }

    public static FormulaAssimilator semantic(
            ModalFormula target, List<Transformation> corrections) {
        return new FormulaAssimilator(target, corrections, new SemanticAssimilationStrategy());
    }

    public IterativeTransformation assimilate(ModalFormula source, int maxTransformationCount) {
        if (strategy.isComplete(source, target)) {
            return new IterativeTransformation();
        }
        if (0 >= maxTransformationCount) {
            return null;
        }
        List<Transformation> subformulaCorrections = getSubformulaCorrections(source);

        IterativeTransformation completeCorrection = null;
        for (Transformation correction : subformulaCorrections) {
            if (!correction.isApplicable(source)) {
                continue;
            }
            ModalFormula transformedSource = correction.apply(source.clone());
            IterativeTransformation remainingCorrections =
                    assimilate(transformedSource, maxTransformationCount - 1);
            if (null == remainingCorrections) {
                continue;
            }
            completeCorrection =
                    new IterativeTransformation(
                            new IterativeTransformation(correction), remainingCorrections);
            maxTransformationCount = completeCorrection.size() - 1;
            if (0 >= maxTransformationCount) {
                return completeCorrection;
            }
        }
        return completeCorrection;
    }

    public static List<Transformation> getTransformationsForSubformulae(
            List<Transformation> transformations, ModalFormula formula) {
        List<Transformation> subformulaeCorrections = new ArrayList<Transformation>();
        Set<TreePath> subformulaPaths = formula.getSubformulaPaths();
        for (Transformation correction : transformations) {
            for (TreePath subformulaPath : subformulaPaths) {
                subformulaeCorrections.add(correction.forPath(subformulaPath));
            }
        }
        return subformulaeCorrections;
    }

    private List<Transformation> getSubformulaCorrections(ModalFormula source) {
        return getTransformationsForSubformulae(filledCorrections, source);
    }

    protected void loadFilledCorrections(
            List<Transformation> corrections, Collection<Variable> variables) {
        List<ModalFormula> variableFormulas = new ArrayList<ModalFormula>(variables.size());
        variableFormulas.addAll(variables);

        filledCorrections = new ArrayList<Transformation>();
        for (Transformation correction : corrections) {
            if (correction instanceof TransformationWithNewFormula) {
                filledCorrections.addAll(
                        ((TransformationWithNewFormula) correction)
                                .forMultipleSubFormulas(variableFormulas));
            } else {
                filledCorrections.add(correction);
            }
        }
    }

    private static List<Transformation> getStandardCorrections(boolean isPropositional) {
        List<Transformation> corrections = new ArrayList<Transformation>();
        // equivalence
        corrections.add(TypicalMistakesTransformations.getImplicationInsteadOfEquivalence());
        corrections.add(TypicalMistakesTransformations.getConjunctionInsteadOfEquivalence());
        corrections.add(TypicalMistakesTransformations.getDisjunctionInsteadOfEquivalence());

        // implication
        corrections.add(TypicalMistakesTransformations.getImplicationWrongDirection());
        corrections.add(TypicalMistakesTransformations.getEquivalenceInsteadOfImplication());
        corrections.add(TypicalMistakesTransformations.getConjunctionInsteadOfImplication());
        corrections.add(TypicalMistakesTransformations.getDisjunctionInsteadOfImplication());

        // disjunction
        corrections.add(
                TypicalMistakesTransformations.getDisjunctionInsteadOfExclusiveDisjunction());
        corrections.add(
                TypicalMistakesTransformations.getExclusiveDisjunctionInsteadOfDisjunction());
        corrections.add(TypicalMistakesTransformations.getConjunctionInsteadOfDisjunction());
        corrections.add(TypicalMistakesTransformations.getImplicationInsteadOfDisjunction());
        corrections.add(TypicalMistakesTransformations.getEquivalenceInsteadOfDisjunction());

        // conjunction
        corrections.add(TypicalMistakesTransformations.getNeitherNorInsteadOfConjunction());
        corrections.add(TypicalMistakesTransformations.getConjunctionInsteadOfNeitherNor());
        corrections.add(TypicalMistakesTransformations.getDisjunctionInsteadOfConjunction());
        corrections.add(TypicalMistakesTransformations.getImplicationInsteadOfConjunction());
        corrections.add(TypicalMistakesTransformations.getEquivalenceInsteadOfConjunction());

        // negation
        corrections.add(TypicalMistakesTransformations.getNegationMissing());
        corrections.add(TypicalMistakesTransformations.getNegationTooMuch());

        // modal
        if (!isPropositional) {
            corrections.add(TypicalMistakesTransformations.getBoxInsteadOfDiamond());
            corrections.add(TypicalMistakesTransformations.getDiamondInsteadOfBox());
            corrections.add(TypicalMistakesTransformations.getAddBox());
            corrections.add(TypicalMistakesTransformations.getAddDiamond());
            corrections.add(TypicalMistakesTransformations.getRemoveBox());
            corrections.add(TypicalMistakesTransformations.getRemoveDiamond());
            corrections.add(TypicalMistakesTransformations.getWrongDistributionBox());
            corrections.add(TypicalMistakesTransformations.getWrongDistributionDiamond());
        }

        // subformula
        corrections.addAll(BuggyTransformations.getAddSubformula());
        corrections.addAll(BuggyTransformations.getRemoveSubformula());

        return corrections;
    }

    protected ModalFormula target;
    private List<Transformation> filledCorrections;
    private AssimilationStrategy strategy;
}
