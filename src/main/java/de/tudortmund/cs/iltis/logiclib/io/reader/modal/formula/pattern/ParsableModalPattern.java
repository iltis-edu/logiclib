package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableEntry;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidValue;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ParsableModalPattern extends ParsableEntry<TreePattern<ModalFormula>> {

    public ParsableModalPattern() {
        super();
    }

    public ParsableModalPattern(String input) {
        super(input);
    }

    @Override
    protected TreePattern<ModalFormula> parse(Optional<Object> context) {
        TreePattern<ModalFormula> pattern;

        try {
            pattern = new PatternReader().read(getSource());
        } catch (IncorrectParseInputException e) {
            throw new ParsableInvalidValue(
                    this, "Input '" + getSource() + "' is not a modal pattern", e);
        }

        return pattern;
    }

    public static class Adapter extends XmlAdapter<String, ParsableModalPattern> {

        public ParsableModalPattern unmarshal(String input) throws Exception {
            return new ParsableModalPattern(input);
        }

        public String marshal(ParsableModalPattern entry) throws Exception {
            throw new UnsupportedOperationException();
        }
    }
}
