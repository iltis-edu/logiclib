package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableEntry;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidValue;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ParsablePredicateFormula extends ParsableEntry<TermOrFormula> {

    public ParsablePredicateFormula() {
        super();
    }

    public ParsablePredicateFormula(String input) {
        super(input);
    }

    @Override
    protected TermOrFormula parse(Optional<Object> context) {
        TermOrFormula termOrFormula;

        try {
            termOrFormula = Formula.parse(getSource());
        } catch (IncorrectParseInputException e) {
            throw new ParsableInvalidValue(
                    this, "Input '" + getSource() + "' is not a predicate term or formula", e);
        }

        return termOrFormula;
    }

    public static class Adapter extends XmlAdapter<String, ParsablePredicateFormula> {
        public ParsablePredicateFormula unmarshal(String input) throws Exception {
            return new ParsablePredicateFormula(input);
        }

        public String marshal(ParsablePredicateFormula entry) throws Exception {
            throw new UnsupportedOperationException();
        }
    }
}
