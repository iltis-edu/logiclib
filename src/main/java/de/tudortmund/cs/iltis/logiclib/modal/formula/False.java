package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Constant (unsatisfiable) formula "false". */
@SuppressWarnings("serial")
public class False extends ModalFormula {

    public FormulaType getType() {
        return FormulaType.FALSE;
    }

    @Override
    public False clone() {
        return new False();
    }
}
