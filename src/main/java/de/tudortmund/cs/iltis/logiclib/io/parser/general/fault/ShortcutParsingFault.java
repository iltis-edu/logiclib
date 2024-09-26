package de.tudortmund.cs.iltis.logiclib.io.parser.general.fault;

import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;

/**
 * A specialized parsing fault which indicates that shortcuts were used although they were not
 * activated.
 */
public class ShortcutParsingFault extends ParsingFault {

    // needed for serialization
    private ShortcutParsingFault() {}

    public ShortcutParsingFault(
            ParsingFaultReason reason,
            int line,
            int charPositionInLine,
            String text,
            String partOfInput) {

        super(reason, line, charPositionInLine, text, partOfInput);
    }

    @Override
    public String toString() {
        return "ShortcutParsingFault [reason = "
                + getReason()
                + ", line = "
                + line
                + ", charPositionInLine = "
                + charPositionInLine
                + ", text = "
                + text
                + ", partOfInput = "
                + partOfInput
                + "]";
    }

    /**
     * Creates a shallow copy of this object.
     *
     * <p><b>Note:</b> the reason is not cloned.
     *
     * @return a shallow copy of this object
     */
    @Override
    protected ShortcutParsingFault clone() {
        return new ShortcutParsingFault(getReason(), line, charPositionInLine, text, partOfInput);
    }
}
