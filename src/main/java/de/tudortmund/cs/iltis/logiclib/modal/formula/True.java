package de.tudortmund.cs.iltis.logiclib.modal.formula;

/** Constant (tautological) formula "true". */
@SuppressWarnings("serial")
public class True extends ModalFormula {

    public FormulaType getType() {
        return FormulaType.TRUE;
    }

    @Override
    public True clone() {
        return new True();
    }
}
