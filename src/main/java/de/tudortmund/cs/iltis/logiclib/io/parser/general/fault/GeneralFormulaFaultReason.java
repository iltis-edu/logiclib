package de.tudortmund.cs.iltis.logiclib.io.parser.general.fault;

import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;

/** Enum for fault reasons which can appear for multiple formula grammars. */
public enum GeneralFormulaFaultReason implements ParsingFaultReason {
    /** <- instead of -> */
    IMPLICATION_IN_WRONG_DIRECTION;

    /** {@inheritDoc} */
    @Override
    public int getGroup() {
        switch (this) {
            case IMPLICATION_IN_WRONG_DIRECTION:
                return 250;
            default:
                return 0;
        }
    }
}
