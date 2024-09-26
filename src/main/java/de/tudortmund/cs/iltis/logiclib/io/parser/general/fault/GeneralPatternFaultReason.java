package de.tudortmund.cs.iltis.logiclib.io.parser.general.fault;

import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;

/** Enum for fault reasons which can appear for pattern grammars. */
public enum GeneralPatternFaultReason implements ParsingFaultReason {
    SHORTCUTS_NOT_ALLOWED;

    @Override
    public int getGroup() {
        switch (this) {
            case SHORTCUTS_NOT_ALLOWED:
                return 400;
            default:
                return 0;
        }
    }
}
