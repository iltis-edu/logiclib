package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

public class IsomorphismEvaluatorTest {

    private KripkeStructure firstKripke;
    private KripkeStructure secondKripke;
    private KripkeState state1;
    private KripkeState state2;
    private KripkeState state3;
    private KripkeState state4;
    private KripkeState state5;
    private KripkeState state6;

    public IsomorphismEvaluatorTest() {
        Variable A = new Variable("A");
        Variable B = new Variable("B");
        Variable C = new Variable("C");
        Valuation valuationA = new Valuation();
        valuationA.define(A, true);
        Valuation valuationB = new Valuation();
        valuationB.define(B, true);
        Valuation valuationC = new Valuation();
        valuationC.define(C, true);

        firstKripke = new KripkeStructure();
        state1 = new KripkeState("1", new Valuation());
        state2 = new KripkeState("2", valuationA);
        state3 = new KripkeState("3", valuationB);
        firstKripke.addVertex(state1);
        firstKripke.addVertex(state2);
        firstKripke.addVertex(state3);
        firstKripke.addEdge(state1, state2);
        firstKripke.addEdge(state1, state3);

        secondKripke = new KripkeStructure();
        state4 = new KripkeState("4", new Valuation());
        state5 = new KripkeState("5", valuationA);
        state6 = new KripkeState("6", valuationC);
        secondKripke.addVertex(state4);
        secondKripke.addVertex(state5);
        secondKripke.addVertex(state6);
        secondKripke.addEdge(state4, state5);
        secondKripke.addEdge(state5, state6);
        secondKripke.addEdge(state6, state4);
    }

    @Test
    public void test() {
        IsomorphismEvaluator evaluator =
                new IsomorphismEvaluator(this.firstKripke, this.firstKripke);

        Optional<Map<KripkeState, KripkeState>> optMap = evaluator.checkIsomorphy();
        assertTrue(optMap.isPresent());
        Map<KripkeState, KripkeState> map = optMap.get();
        assertFirstMapIsCorrect(map);

        evaluator = new IsomorphismEvaluator(this.firstKripke, this.secondKripke);
        optMap = evaluator.checkIsomorphy();
        assertFalse(optMap.isPresent());

        evaluator = new IsomorphismEvaluator(this.secondKripke, this.secondKripke);
        optMap = evaluator.checkIsomorphy();
        assertTrue(optMap.isPresent());
        map = optMap.get();
        assertSecondMapIsCorrect(map);
    }

    private void assertFirstMapIsCorrect(Map<KripkeState, KripkeState> map) {
        assertTrue(map.size() == 3);
        assertTrue(map.get(state1).equals(state1));
        assertTrue(map.get(state2).equals(state2));
        assertTrue(map.get(state3).equals(state3));
    }

    private void assertSecondMapIsCorrect(Map<KripkeState, KripkeState> map) {
        assertTrue(map.size() == 3);
        assertTrue(map.get(state4).equals(state4));
        assertTrue(map.get(state5).equals(state5));
        assertTrue(map.get(state6).equals(state6));
    }
}
