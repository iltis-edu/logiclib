package de.tudortmund.cs.iltis.logiclib.io.parser.general.fault;

import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;

/**
 * Collection of reasons why parsing can fail, specifically for logical formulae sets (e. g.
 * clauses) and sets thereof.
 */
public enum GeneralFormulaSetFaultReason implements ParsingFaultReason {
    // for formula sets (e. g. clauses)
    BRACES_AROUND_FORMULAE_MISSING,
    SEPARATOR_BETWEEN_FORMULAE_MISSING,
    SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS,
    // for formula set sets
    BRACES_AROUND_SETS_MISSING,
    SEPARATOR_BETWEEN_SETS_MISSING,
    SEPARATOR_BETWEEN_SETS_SUPERFLUOUS;

    /** {@inheritDoc} */
    @Override
    public int getGroup() {
        switch (this) {
            case BRACES_AROUND_FORMULAE_MISSING:
            case BRACES_AROUND_SETS_MISSING:
                return 1000;
            case SEPARATOR_BETWEEN_FORMULAE_MISSING:
            case SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS:
            case SEPARATOR_BETWEEN_SETS_MISSING:
            case SEPARATOR_BETWEEN_SETS_SUPERFLUOUS:
                return 900;
            default:
                return 0;
        }
    }
}
