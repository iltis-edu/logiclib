package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.utils.collections.LabeledTable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.ArrayList;
import org.junit.Test;

public class RoundBasedBisimulationEvaluatorTest {

    private KripkeStructure firstKripke;
    private KripkeStructure secondKripke;
    private KripkeState state1;
    private KripkeState state2;
    private KripkeState state3;
    private KripkeState state4;
    private KripkeState state5;
    private KripkeState state6;

    public RoundBasedBisimulationEvaluatorTest() {
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
        RoundBasedBisimulationEvaluator bisimulation =
                new RoundBasedBisimulationEvaluator(firstKripke, secondKripke);
        ArrayList<KripkeState> rowLabels = new ArrayList<>();
        add(rowLabels, state1, state2, state3);
        ArrayList<KripkeState> columnLabels = new ArrayList<>();
        add(columnLabels, state4, state5, state6);
        LabeledTable<KripkeState, KripkeState, InsimilarityWitness> table =
                new LabeledTable<>(rowLabels, columnLabels);

        table.setCell(state1, state4, new NoWitness());
        table.setCell(state1, state5, new IncompatibleStates(state1, state5));
        table.setCell(state1, state6, new IncompatibleStates(state1, state6));
        table.setCell(state2, state4, new IncompatibleStates(state2, state4));
        table.setCell(state2, state5, new NoWitness());
        table.setCell(state2, state6, new IncompatibleStates(state2, state6));
        table.setCell(state3, state4, new IncompatibleStates(state3, state4));
        table.setCell(state3, state5, new IncompatibleStates(state3, state5));
        table.setCell(state3, state6, new IncompatibleStates(state3, state6));

        assertTrue(bisimulation.checkTable(table).isEmpty());

        table.setCell(state1, state4, new IncompatibleEdge(state1, state3));
        assertTrue(bisimulation.checkTable(table).isEmpty());

        table.setCell(state3, state5, new NoWitness());
        assertWrongStates(bisimulation.checkTable(table), state3, state5);

        table.setCell(state2, state5, new IncompatibleStates(state2, state5));
        assertCorrectStates(bisimulation.checkTable(table), state2, state5);

        table.setCell(state1, state4, new NoWitness());
        assertWrongEdge(bisimulation.checkTable(table), firstKripke, state1, state4, state3);
        assertWrongEdge(bisimulation.checkTable(table), secondKripke, state1, state4, state6);
    }

    private void assertWrongEdge(
            ListSet<
                            SerializablePair<
                                    SerializablePair<KripkeState, KripkeState>,
                                    ListSet<InsimilarityWitness>>>
                    wrongCells,
            KripkeStructure kripke,
            KripkeState first,
            KripkeState second,
            KripkeState third) {

        boolean contains = false;

        if (kripke.equals(firstKripke)) {
            for (SerializablePair<
                            SerializablePair<KripkeState, KripkeState>,
                            ListSet<InsimilarityWitness>>
                    pair : wrongCells) {

                if (pair.first().equals(new SerializablePair<>(first, second))
                        && pair.second().contains(new IncompatibleEdge(first, third))) {

                    contains = true;
                }
            }
        } else {
            for (SerializablePair<
                            SerializablePair<KripkeState, KripkeState>,
                            ListSet<InsimilarityWitness>>
                    pair : wrongCells) {

                if (pair.first().equals(new SerializablePair<>(first, second))
                        && pair.second().contains(new IncompatibleEdge(second, third))) {

                    contains = true;
                }
            }
        }
        assertTrue(contains);
    }

    private void assertCorrectStates(
            ListSet<
                            SerializablePair<
                                    SerializablePair<KripkeState, KripkeState>,
                                    ListSet<InsimilarityWitness>>>
                    wrongCells,
            KripkeState first,
            KripkeState second) {

        boolean contains = false;
        for (SerializablePair<
                        SerializablePair<KripkeState, KripkeState>, ListSet<InsimilarityWitness>>
                pair : wrongCells) {
            if (pair.equals(
                    new SerializablePair<>(
                            new SerializablePair<>(first, second), new ListSet<>()))) {

                contains = true;
            }
        }
        assertTrue(contains);
    }

    private void assertWrongStates(
            ListSet<
                            SerializablePair<
                                    SerializablePair<KripkeState, KripkeState>,
                                    ListSet<InsimilarityWitness>>>
                    wrongCells,
            KripkeState first,
            KripkeState second) {

        boolean contains = false;
        for (SerializablePair<
                        SerializablePair<KripkeState, KripkeState>, ListSet<InsimilarityWitness>>
                pair : wrongCells) {
            if (pair.equals(
                    new SerializablePair<>(
                            new SerializablePair<>(first, second),
                            new ListSet<>(new IncompatibleStates(first, second))))) {

                contains = true;
            }
        }
        assertTrue(contains);
    }

    private void add(ArrayList<KripkeState> list, KripkeState... states) {
        for (KripkeState state : states) {
            list.add(state);
        }
    }
}
