package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import junit.framework.TestCase;
import org.junit.Test;

public class PropositionalDefaultEvaluatorTest extends TestCase {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    public PropositionalDefaultEvaluatorTest() {
        A = new Variable("A");
        B = new Variable("B");
        True = new True();
        False = new False();

        val00 = new Valuation();
        val01 = new Valuation();
        val10 = new Valuation();
        val11 = new Valuation();

        val00.define(A, false);
        val00.define(B, false);
        val01.define(A, false);
        val01.define(B, true);
        val10.define(A, true);
        val10.define(B, false);
        val11.define(A, true);
        val11.define(B, true);
    }

    @Test
    public void testAtomic() {
        PropositionalDefaultEvaluator evalTrue = new PropositionalDefaultEvaluator(True);
        PropositionalDefaultEvaluator evalFalse = new PropositionalDefaultEvaluator(False);
        PropositionalDefaultEvaluator evalA = new PropositionalDefaultEvaluator(A);
        PropositionalDefaultEvaluator evalB = new PropositionalDefaultEvaluator(B);

        assertTrue(evalTrue.evaluate(val00));
        assertFalse(evalFalse.evaluate(val00));

        assertFalse(evalA.evaluate(val00));
        assertFalse(evalA.evaluate(val01));
        assertTrue(evalA.evaluate(val10));
        assertTrue(evalA.evaluate(val11));

        assertFalse(evalB.evaluate(val00));
        assertTrue(evalB.evaluate(val01));
        assertFalse(evalB.evaluate(val10));
        assertTrue(evalB.evaluate(val11));
    }

    @Test
    public void testNegation() {
        PropositionalDefaultEvaluator eval = new PropositionalDefaultEvaluator(A.not());

        assertTrue(eval.evaluate(val00));
        assertFalse(eval.evaluate(val10));
    }

    @Test
    public void testConjunction() {
        PropositionalDefaultEvaluator eval = new PropositionalDefaultEvaluator(A.and(B));

        assertFalse(eval.evaluate(val00));
        assertFalse(eval.evaluate(val01));
        assertFalse(eval.evaluate(val10));
        assertTrue(eval.evaluate(val11));
    }

    @Test
    public void testDisjunction() {
        PropositionalDefaultEvaluator eval = new PropositionalDefaultEvaluator(A.or(B));

        assertFalse(eval.evaluate(val00));
        assertTrue(eval.evaluate(val01));
        assertTrue(eval.evaluate(val10));
        assertTrue(eval.evaluate(val11));
    }

    @Test
    public void testImplication() {
        PropositionalDefaultEvaluator eval = new PropositionalDefaultEvaluator(A.implies(B));

        assertTrue(eval.evaluate(val00));
        assertTrue(eval.evaluate(val01));
        assertFalse(eval.evaluate(val10));
        assertTrue(eval.evaluate(val11));
    }

    @Test
    public void testEquivalence() {
        PropositionalDefaultEvaluator eval = new PropositionalDefaultEvaluator(A.equivalent(B));

        assertTrue(eval.evaluate(val00));
        assertFalse(eval.evaluate(val01));
        assertFalse(eval.evaluate(val10));
        assertTrue(eval.evaluate(val11));
    }

    @Test
    public void isSatisfiable() {
        try {
            ModalFormula phi0 = formulaReader.read("A & B & C");
            ModalFormula phi1 = formulaReader.read("A & B & !C");
            ModalFormula phi2 = formulaReader.read("A & !B & C");
            ModalFormula phi3 = formulaReader.read("A & !B & !C");
            ModalFormula phi4 = formulaReader.read("!A & B & C");
            ModalFormula phi5 = formulaReader.read("!A & B & !C");
            ModalFormula phi6 = formulaReader.read("!A & !B & C");
            ModalFormula phi7 = formulaReader.read("!A & !B & !C");

            ModalFormula psi = A.and(A.not());
            ModalFormula chi = A.or(A.not()).not();

            PropositionalDefaultEvaluator eval;
            eval = new PropositionalDefaultEvaluator(phi0);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi1);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi2);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi3);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi4);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi5);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi6);
            assertTrue(eval.isSatisfiable());
            eval = new PropositionalDefaultEvaluator(phi7);
            assertTrue(eval.isSatisfiable());

            eval = new PropositionalDefaultEvaluator(psi);
            assertFalse(eval.isSatisfiable());

            eval = new PropositionalDefaultEvaluator(chi);
            assertFalse(eval.isSatisfiable());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testImplies() throws Exception {
        ModalFormula phi = formulaReader.read("A & B");
        ModalFormula psi = formulaReader.read("A | B");
        assertTrue(Comparator.implies(phi, psi).test());
        assertFalse(Comparator.implies(psi, phi).test());
    }

    @Test
    public void testEquivalent() throws Exception {
        ModalFormula phi = formulaReader.read("A & !B");
        ModalFormula psi = formulaReader.read("!(!A | B)");
        assertTrue(Comparator.equivalent(phi, psi).test());
        assertTrue(Comparator.equivalent(psi, phi).test());
    }

    private Valuation val00;
    private Valuation val01;
    private Valuation val10;
    private Valuation val11;

    private Variable A;
    private Variable B;

    private True True;
    private False False;
}
