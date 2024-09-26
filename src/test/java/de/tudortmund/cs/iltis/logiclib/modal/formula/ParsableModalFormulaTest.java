package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ParsableModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidValue;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import org.junit.Test;

public class ParsableModalFormulaTest extends AdvancedTest {
    public static ParsableModalFormula form(String input) {
        return new ParsableModalFormula(input);
    }

    public static ModalFormula direct(String input) {
        try {
            return ModalFormula.parse(input, ModalReaderProperties.createDefaultWithLatex());
        } catch (IncorrectParseInputException e) {
            fail();
        }
        return null;
    }

    @Test
    public void testRequiredOnPresent() {
        assertEquals(direct("A∧B"), form("A∧B").required().value());
    }

    @Test
    public void testPropositional() {
        assertEquals(direct("A∧B"), form("A∧B").propositional().value());
        assertEquals(direct("◇A∧B"), form("◇A∧B").propositional(false).value());

        assertThrows(ParsableInvalidValue.class, () -> form("◇A∧B").propositional().value());
        assertThrows(ParsableInvalidValue.class, () -> form("◇A∧B").propositional(true).value());
        assertThrows(ParsableInvalidValue.class, () -> form("◻A∧B").propositional().value());
        assertThrows(ParsableInvalidValue.class, () -> form("◻A∧B").propositional(true).value());
    }
}
