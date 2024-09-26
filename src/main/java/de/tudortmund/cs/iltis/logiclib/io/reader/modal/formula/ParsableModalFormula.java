package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableEntry;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidValue;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ParsableModalFormula extends ParsableEntry<ModalFormula> {
    private boolean requirePropositional = false;

    public ParsableModalFormula() {
        super();
    }

    public ParsableModalFormula(String input) {
        super(input);
    }

    public ParsableModalFormula propositional() {
        return propositional(true);
    }

    public ParsableModalFormula propositional(boolean require) {
        requirePropositional = require;
        return this;
    }

    @Override
    protected ModalFormula parse(Optional<Object> context) {
        ModalFormula formula;
        try {
            formula =
                    ModalFormula.parse(getSource(), ModalReaderProperties.createDefaultWithLatex());
            if (requirePropositional && !formula.isPropositional()) {
                throw new ParsableInvalidValue(
                        this, "Formula '" + formula + "' is not propositional");
            }
        } catch (IncorrectParseInputException e) {
            throw new ParsableInvalidValue(
                    this, "Input '" + getSource() + "' is not a modal formula", e);
        }
        return formula;
    }

    public static class Adapter extends XmlAdapter<String, ParsableModalFormula> {
        public ParsableModalFormula unmarshal(String input) {
            return new ParsableModalFormula(input);
        }

        public String marshal(ParsableModalFormula entry) {
            throw new UnsupportedOperationException();
        }
    }
}
