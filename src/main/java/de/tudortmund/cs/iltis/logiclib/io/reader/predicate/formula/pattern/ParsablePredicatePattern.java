package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableEntry;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidValue;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ParsablePredicatePattern extends ParsableEntry<TreePattern<TermOrFormula>> {

    public ParsablePredicatePattern() {
        super();
    }

    public ParsablePredicatePattern(String input) {
        super(input);
    }

    @Override
    protected TreePattern<TermOrFormula> parse(Optional<Object> context) {
        TreePattern<TermOrFormula> pattern;

        try {
            pattern = new PatternReader().read(getSource());
        } catch (IncorrectParseInputException e) {
            throw new ParsableInvalidValue(
                    this, "Input '" + getSource() + "' is not a predicate pattern", e);
        }

        return pattern;
    }

    public static class Adapter extends XmlAdapter<String, ParsablePredicatePattern> {
        public ParsablePredicatePattern unmarshal(String input) throws Exception {
            return new ParsablePredicatePattern(input);
        }

        public String marshal(ParsablePredicatePattern entry) throws Exception {
            throw new UnsupportedOperationException();
        }
    }
}
