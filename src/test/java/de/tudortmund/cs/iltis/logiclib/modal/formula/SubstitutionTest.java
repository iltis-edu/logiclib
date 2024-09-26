package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import org.junit.Test;

public class SubstitutionTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    public SubstitutionTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
    }

    @Test
    public void testVariableRenaming() {
        try {
            ModalFormula phi = formulaReader.read("A -> (B & (A <-> !C))");
            ModalFormula psi = formulaReader.read("C -> (B & (C <-> !C))");
            Substitution sigma = new Substitution(A, C);
            assertEquals(psi, sigma.apply(phi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testImplication() {
        try {
            ModalFormula phi = formulaReader.read("(A -> B) & (A -> !B) & (!A -> B)");
            ModalFormula psi = formulaReader.read("C & (A -> !B) & (!A -> B)");
            Substitution sigma = new Substitution(A.implies(B), C);
            assertEquals(psi, sigma.apply(phi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testSimpleFormulaReplacement() {
        try {
            ModalFormula phi =
                    formulaReader.read("(A -> B) & !(C <-> (A -> B)) & (A -> !B) & (!A -> B)");
            ModalFormula psi =
                    formulaReader.read("(!A | B) & !(C <-> (!A | B)) & (A -> !B) & (!A -> B)");
            Substitution sigma = new Substitution(A.implies(B), A.not().or(B));
            assertEquals(psi, sigma.apply(phi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testMultiFormulaReplacement() {
        try {
            ModalFormula phi =
                    formulaReader.read("(A -> B) & !(C <-> (A -> B)) & (A -> !B) & (!A -> B)");
            ModalFormula psi = formulaReader.read("C & !(!B <-> C) & (A -> !B) & (!A -> B)");
            Substitution sigma = new Substitution(A.implies(B), C);
            sigma.substitute(C, B.not());
            assertEquals(psi, sigma.apply(phi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private Variable A;
    private Variable B;
    private Variable C;
}
