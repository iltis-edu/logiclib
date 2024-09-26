package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalDefaultEvaluator;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import org.junit.Test;

public class ModalTruthTableTest extends AdvancedTest {
    private Variable A;
    private Variable B;

    public ModalTruthTableTest() {
        this.A = new Variable("A");
        this.B = new Variable("B");
    }

    @Test
    public void simple() {
        Valuation vB = new Valuation();
        vB.define(B, true);
        Valuation vAB = vB.clone();
        vAB.define(A, true);

        KripkeStructure K = new KripkeStructure();
        KripkeState s1 = new KripkeState("1", vAB);
        KripkeState s2 = new KripkeState("2", vB);
        KripkeState s3 = new KripkeState("3", vAB);
        K.addVertices(s1, s2, s3);
        K.addEdge(s1, s2);
        K.addEdge(s1, s3);
        K.addEdge(s2, s1);

        ModalFormula psi = B.box();
        ModalFormula phi = (A.and(B)).and(B.box(), (A.and(B)).box().box(), A.not().diamond());
        ModalDefaultEvaluator eval = new ModalDefaultEvaluator(phi, K);

        assertTrue(eval.evaluate(s1));
        assertFalse(eval.evaluate(s2));
        assertFalse(eval.evaluate(s3));
    }
}
