package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Modal operator on modal formula. */
@SuppressWarnings("serial")
public class Box extends ModalFormula {
    @SuppressWarnings("unused")
    protected Box() { // Serialization
    }

    public Box(ModalFormula subformula) {
        this.addChild(subformula);
    }

    public FormulaType getType() {
        return FormulaType.BOX;
    }

    @Override
    public Box clone() {
        return new Box(getSubformula(0).clone());
    }
}
