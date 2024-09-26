package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Equivalence of modal formulae. */
@SuppressWarnings("serial")
public class Equivalence extends ModalFormula {
    @SuppressWarnings("unused")
    protected Equivalence() { // Serialization
    }

    public Equivalence(final ModalFormula leftSubformula, final ModalFormula rightSubformula) {
        addChild(leftSubformula);
        addChild(rightSubformula);
    }

    public FormulaType getType() {
        return FormulaType.EQUIV;
    }

    public ModalFormula getLeftSubformula() {
        return this.getSubformula(0);
    }

    public ModalFormula getRightSubformula() {
        return this.getSubformula(1);
    }

    @Override
    public Equivalence clone() {
        return new Equivalence(getLeftSubformula().clone(), getRightSubformula().clone());
    }
}
