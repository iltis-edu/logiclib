package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.ParsablePredicateFormula;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import org.junit.Test;

public class ParsablePredicateFormulaTest extends AdvancedTest {
    public static ParsablePredicateFormula form(String input) {
        return new ParsablePredicateFormula(input);
    }

    public static TermOrFormula direct(String input) {
        try {
            return Formula.parse(input);
        } catch (IncorrectParseInputException e) {
            assertTrue(false);
        }
        return null;
    }

    @Test
    public void testRequiredOnPresent() {
        assertEquals(
                direct("∃y E(x,y) ∧ ∃y E(y,x)"), form("∃y E(x,y) ∧ ∃y E(y,x)").required().value());
        assertEquals(direct("E(x,y) ∧ ∃y E(y,x)"), form("E(x,y) ∧ ∃y E(y,x)").required().value());
        assertEquals(direct("E(x,y)"), form("E(x,y)").required().value());
    }

    @Test
    public void testParsablePredicateFormula() {
        assertEquals(direct("E(f(x),y)"), form("E(f(x),y)").value());
        assertEquals(
                direct("forall x exists y (E(x,y) & D(f(x)))"),
                form("forall x exists y (E(x,y) & D(f(x)))").value());
        assertEquals(
                direct("forall x exists y (E(x,y) | D(f(x)))"),
                form("" + "forall x exists y (E(x,y) | D(f(x)))").value());
    }
}
