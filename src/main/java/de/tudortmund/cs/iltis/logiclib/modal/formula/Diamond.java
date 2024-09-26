package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Modal operator on modal formula. */
@SuppressWarnings("serial")
public class Diamond extends ModalFormula {
    @SuppressWarnings("unused")
    protected Diamond() { // Serialization
    }

    public Diamond(ModalFormula subformula) {
        addChild(subformula);
    }

    public FormulaType getType() {
        return FormulaType.DIAMOND;
    }

    @Override
    public Diamond clone() {
        return new Diamond(getSubformula(0).clone());
    }
}
