package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.fault;

import de.tudortmund.cs.iltis.utils.collections.Fault;

/**
 * Used in a {@link PropositionalFormulaFaultCollection} to represent a fault from {@link
 * PropositionalFormulaFaultReason}.
 */
public class PropositionalFormulaFault extends Fault<PropositionalFormulaFaultReason> {

    public PropositionalFormulaFault(PropositionalFormulaFaultReason reason) {
        super(reason);
    }

    @Override
    protected Object clone() {
        return new PropositionalFormulaFault(getReason());
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private PropositionalFormulaFault() {
        super();
    }
}
