package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Negation of o modal formula. */
@SuppressWarnings("serial")
public class Negation extends ModalFormula {
    @SuppressWarnings("unused")
    protected Negation() { // Serialization
    }

    public Negation(final ModalFormula subformula) {
        addChild(subformula);
    }

    public FormulaType getType() {
        return FormulaType.NEG;
    }

    @Override
    public Negation clone() {
        return new Negation(this.getSubformula(0).clone());
    }
}
