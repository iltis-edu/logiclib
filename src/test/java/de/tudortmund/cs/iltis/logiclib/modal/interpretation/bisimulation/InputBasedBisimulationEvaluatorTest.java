package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.ArrayList;
import org.junit.Test;

public class InputBasedBisimulationEvaluatorTest {

    private KripkeStructure firstKripke;
    private KripkeStructure secondKripke;
    private KripkeState state1;
    private KripkeState state2;
    private KripkeState state3;
    private KripkeState state4;
    private KripkeState state5;
    private KripkeState state6;

    public InputBasedBisimulationEvaluatorTest() {
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
        secondKripke.addEdge(state4, state6);
    }

    @Test
    public void test() {
        InputBasedBisimulationEvaluator evaluator =
                new InputBasedBisimulationEvaluator(firstKripke, secondKripke);

        assertShouldNotHaveBeenMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state1, state4),
                        new IncompatibleStates(state1, state4)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state1, state5),
                        new IncompatibleStates(state1, state5)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state1, state6),
                        new IncompatibleStates(state1, state6)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state2, state4),
                        new IncompatibleStates(state2, state4)));

        assertShouldNotHaveBeenMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state2, state5),
                        new IncompatibleStates(state2, state5)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state2, state6),
                        new IncompatibleStates(state2, state6)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state3, state4),
                        new IncompatibleStates(state3, state4)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state3, state5),
                        new IncompatibleStates(state3, state5)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state3, state6),
                        new IncompatibleStates(state3, state6)));

        assertCorrectMarked(
                evaluator.checkInput(
                        new SerializablePair<>(state1, state4),
                        new IncompatibleEdge(state1, state3)));

        assertTrue(evaluator.isFinished());
    }

    private void assertCorrectMarked(
            SerializablePair<Integer, SerializablePair<Boolean, ListSet<InsimilarityWitness>>>
                    mistakePair) {

        SerializablePair<Boolean, ListSet<InsimilarityWitness>> mistake = mistakePair.second();
        assertTrue(mistake.first());
        assertTrue(mistake.second().isEmpty());
    }

    private void assertShouldNotHaveBeenMarked(
            SerializablePair<Integer, SerializablePair<Boolean, ListSet<InsimilarityWitness>>>
                    mistakePair) {

        SerializablePair<Boolean, ListSet<InsimilarityWitness>> mistake = mistakePair.second();
        assertFalse(mistake.first());
        assertTrue(mistake.second().isEmpty());
    }

    private void add(ArrayList<KripkeState> list, KripkeState... states) {
        for (KripkeState state : states) {
            list.add(state);
        }
    }
}
