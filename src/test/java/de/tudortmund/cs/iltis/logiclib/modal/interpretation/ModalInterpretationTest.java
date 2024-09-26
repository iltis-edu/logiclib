package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalInterpretation;
import org.junit.Test;

public class ModalInterpretationTest {

    public ModalInterpretationTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
    }

    @Test
    public void test() {
        KripkeState s1 = new KripkeState("s_1");
        KripkeState s2 = new KripkeState("s_2");
        KripkeState s3 = new KripkeState("s_3");
        s1.getValuation().define(A, true);
        s1.getValuation().define(C, true);
        s3.getValuation().define(B, true);

        KripkeStructure K = new KripkeStructure();
        K.addVertices(s1, s2, s3);
        K.addEdge(s1, s2);
        K.addEdge(s2, s3);

        ModalInterpretation mi = new ModalInterpretation(K, s1);
        System.out.println(mi);
    }

    private Variable A;
    private Variable B;
    private Variable C;
}
