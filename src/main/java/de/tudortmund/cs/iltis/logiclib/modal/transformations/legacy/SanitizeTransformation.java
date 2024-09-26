package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

/**
 * This is a sanitizing transformation intended to be used before and after applying other
 * equivalence transformations.
 *
 * <p>In Contrast to the transformations defined in {@link EquivalenceTransformations}, this
 * operates on the Tree structure itself and replaces Disjunctions and Conjunctions with only 1
 * child, with the child itself.
 */
public class SanitizeTransformation implements Transformation {

    private Transformation transformation;

    public SanitizeTransformation() {
        this.transformation = EquivalenceTransformations.SANITIZED_FORM;
    }

    private SanitizeTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    /////////////////////////////////////////////////////////////////
    // PUBLIC
    /////////////////////////////////////////////////////////////////
    @Override
    public boolean isApplicable(final ModalFormula formula) {
        if (EquivalenceTransformations.SANITIZED_FORM.isApplicable(formula)) {
            return true;
        }

        return this.canRemoveDisjunctionOrConjunction(formula);
    }

    @Override
    public ModalFormula apply(final ModalFormula formula) {
        ModalFormula resultFormula = formula;
        if (EquivalenceTransformations.SANITIZED_FORM.isApplicable(formula)) {
            resultFormula = EquivalenceTransformations.SANITIZED_FORM.apply(formula);
        }

        if (this.canRemoveDisjunctionOrConjunction(resultFormula)) {
            return removeDisjunctionAndConjunction(resultFormula);
        }

        return resultFormula;
    }

    @Override
    public Transformation forPath(final TreePath path) {
        return new SanitizeTransformation(EquivalenceTransformations.SANITIZED_FORM.forPath(path));
    }

    @Override
    public String toString() {
        return EquivalenceTransformations.SANITIZED_FORM.toString();
    }

    /////////////////////////////////////////////////////////////////
    // PRIVATE
    /////////////////////////////////////////////////////////////////
    private boolean canRemoveDisjunctionOrConjunction(final ModalFormula formula) {
        for (ModalFormula currentFormula : formula.getAllSubformulae()) {
            if (currentFormula.getChildren().size() <= 1
                    && (currentFormula.isConjunction() || currentFormula.isDisjunction())) {
                return true;
            }
        }
        return false;
    }

    // removes Conjunctions and Disjunctions with only 1 (or none) child
    private ModalFormula removeDisjunctionAndConjunction(final ModalFormula formula) {
        return formula.removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers();
    }
}
