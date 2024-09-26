package de.tudortmund.cs.iltis.logiclib.modal.io;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import java.util.Optional;
import org.junit.Test;

public class ParserTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    public ParserTest() {
        A = new Variable("A");
        B = new Variable("B");
        phi = new Variable("φ");
        psi = new Variable("ψ");
        chi = new Variable("χ");
    }

    @Test
    public void testConstants() {
        try {
            assertEquals(ModalFormula.TOP, formulaReader.read("1"));
            assertEquals(ModalFormula.TOP, formulaReader.read("⊤"));
            assertEquals(ModalFormula.BOTTOM, formulaReader.read("0"));
            assertEquals(ModalFormula.BOTTOM, formulaReader.read("⊥"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGreekUTFSymbols() {
        try {
            assertEquals(phi, formulaReader.read("φ"));
            assertEquals(psi, formulaReader.read("ψ"));
            assertEquals(chi, formulaReader.read("χ"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testPropositionalUTF() {
        try {
            assertEquals(A.not(), formulaReader.read("¬A"));
            assertEquals(A.not().not(), formulaReader.read("¬¬A"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testPropositionalCStyle() {
        try {
            assertEquals(A, formulaReader.read("A"));
            assertEquals(B, formulaReader.read("B"));

            assertEquals(A.not(), formulaReader.read("!A"));
            assertEquals(A.not().not(), formulaReader.read("!!A"));

            assertEquals(A.and(B), formulaReader.read("A & B"));
            assertEquals(A.or(B), formulaReader.read("A | B"));
            assertEquals(A.implies(B), formulaReader.read("A -> B"));
            assertEquals(A.equivalent(B), formulaReader.read("A <-> B"));

            assertEquals(A.and(B).not(), formulaReader.read("!(A&B)"));
            assertEquals(A.or(B).not(), formulaReader.read("!(A | B)"));
            assertEquals(A.implies(B).not(), formulaReader.read("!(A -> B)"));
            assertEquals(A.equivalent(B).not(), formulaReader.read("!(A <-> B)"));

            assertEquals(A.not().and(B), formulaReader.read("!A & B"));
            assertEquals(A.not().or(B), formulaReader.read("!A | B"));
            assertEquals(A.not().implies(B), formulaReader.read("!A -> B"));
            assertEquals(A.not().equivalent(B), formulaReader.read("!A <-> B"));

            assertEquals(A.and(B, A, B), formulaReader.read("A & B & A & B"));
            assertEquals(A.or(B, A, B), formulaReader.read("A | B | A | B"));
            assertEquals(A.and(B.and(A), A), formulaReader.read("A & (B & A) & A"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testModalUTF() {
        try {
            assertEquals(A.box(), formulaReader.read("☐A"));
            assertEquals(A.not().box(), formulaReader.read("☐¬A"));
            assertEquals(A.box().not(), formulaReader.read("¬☐A"));
            assertEquals(A.diamond(), formulaReader.read("◇A"));
            assertEquals(A.not().diamond(), formulaReader.read("◇¬A"));
            assertEquals(A.diamond().not(), formulaReader.read("¬◇A"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testModal() {
        assertEquals(A.box(), formulaReader.read("box A"));
        assertEquals(A.diamond(), formulaReader.read("dia A"));
    }

    @Test
    public void negationPriority() {
        equalFormulae(A.not().implies(B), "¬A → B");
        equalFormulae(A.implies(B).not(), "¬(A → B)");
    }

    @Test
    public void parenthesesTest() {
        assertEquals(A.and(A.not()).diamond(), formulaReader.read("dia (A & !A)"));
        assertEquals(A.and(A.not().box()).diamond(), formulaReader.read("dia (A & box !A)"));
        assertEquals(A.and(A.not()).diamond(), formulaReader.read("dia [A & (!A)]"));
        assertEquals(A.and(A.not().box()).diamond(), formulaReader.read("dia (A & [box !A])"));
    }

    @Test
    public void latexTest() {
        assertEquals(ModalFormula.TOP, formulaReader.read("\\top"));
        assertEquals(ModalFormula.BOTTOM, formulaReader.read("\\bot"));
        assertEquals(A.or(B, A, B), formulaReader.read("A \\lor B \\vee A | B"));
        assertEquals(A.and(B.and(A), A), formulaReader.read("A \\wedge (B \\land A) & A"));
        equalFormulae(A.not().implies(B), "\\negA \\to B");
        equalFormulae(A.implies(B).not(), "\\lnot(A \\implies B)");
        assertEquals(A.not().equivalent(B), formulaReader.read("!A \\iff B"));
        assertEquals(A.not().equivalent(B), formulaReader.read("!A \\equiv B"));
    }

    @Test
    public void successfulTryToParseOnFormula() {
        assertEquals(
                Optional.of(A.not().implies(B)),
                ModalFormula.tryToParse("¬A → B", ModalReaderProperties.createDefault()));
        assertEquals(
                Optional.of(A.implies(B).not()),
                ModalFormula.tryToParse("¬(A → B)", ModalReaderProperties.createDefault()));
    }

    @Test
    public void unsuccessfulTryToParseOnNonformula() {
        assertEquals(
                Optional.empty(),
                ModalFormula.tryToParse("¬A →", ModalReaderProperties.createDefault()));
        assertEquals(
                Optional.empty(),
                ModalFormula.tryToParse("¬A → B)", ModalReaderProperties.createDefault()));
    }

    /**
     * For more error test cases regarding the parentheses see {@link
     * de.tudortmund.cs.iltis.logiclib.predicate.io.FormulaReaderTest} because the ANTLR grammar
     * used is basically copied from the predicate formula parser.
     */
    @Test
    public void parenthesesMissingTest() {
        try {
            // Order is ambiguous
            formulaReader.read("A | B & C");
        } catch (IncorrectParseInputException e) {
            ParsingFaultCollection faultCollection =
                    (ParsingFaultCollection) e.getFaultMapping().get(ParsingFaultCollection.class);
            assertEquals(
                    faultCollection.getFaults().get(0).getReason(),
                    GeneralParsingFaultReason.PARENTHESES_MISSING);
        }
    }

    @Test
    public void testReverseImplies() {
        try {
            formulaReader.read("<-");
            fail();
        } catch (IncorrectParseInputException e) {
            ParsingFaultCollection faults =
                    (ParsingFaultCollection) e.getFaultMapping().get(ParsingFaultCollection.class);
            assertTrue(
                    faults.containsAnyFault(
                            GeneralFormulaFaultReason.IMPLICATION_IN_WRONG_DIRECTION));
        }
    }

    /*@Test
    public void patternParserConjunctiveNesting() throws Exception {
    	ModalFormulaPattern phi = ModalFormulaPatternReader.read("$X & ($A & $B) & $Y");
    	ModalFormulaPattern psi = ModalFormulaPatternReader.read("$X & (*A & ...) & $Y");
    }*/

    private void equalFormulae(ModalFormula phi, String psiDesc) {
        try {
            ModalFormula psi = formulaReader.read(psiDesc);
            assertEquals(phi, psi);
        } catch (IncorrectParseInputException e) {
            fail();
        }
    }

    Variable A;
    Variable B;
    Variable phi;
    Variable psi;
    Variable chi;
}
