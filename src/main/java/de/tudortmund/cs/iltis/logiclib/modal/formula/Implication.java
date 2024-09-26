package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Implication of modal formulae. */
@SuppressWarnings("serial")
public class Implication extends ModalFormula {
    @SuppressWarnings("unused")
    protected Implication() { // Serialization
    }

    public Implication(final ModalFormula leftSubformula, final ModalFormula rightSubformula) {
        addChild(leftSubformula);
        addChild(rightSubformula);
    }

    public FormulaType getType() {
        return FormulaType.IMPLIES;
    }

    public ModalFormula getPremise() {
        return this.getSubformula(0);
    }

    public ModalFormula getConclusion() {
        return this.getSubformula(1);
    }

    @Override
    public Implication clone() {
        return new Implication(getPremise().clone(), getConclusion().clone());
    }
}
