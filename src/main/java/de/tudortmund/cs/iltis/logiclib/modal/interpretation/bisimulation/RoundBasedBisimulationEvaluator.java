package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.utils.collections.LabeledTable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializableLabeledTable;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.io.Serializable;

/**
 * BisimulationEvaluator that checks, if all pairs of kripke-states, that can be marked in a round,
 * were marked in that round.
 *
 * <p>If a pair of kripke-states is marked, the states are not bisimilar.
 */
public class RoundBasedBisimulationEvaluator implements Serializable {

    // needed for serialization
    @SuppressWarnings("unused")
    private RoundBasedBisimulationEvaluator() {}

    /** the next step is always calculated before a new table is checked */
    private SerializableLabeledTable<KripkeState, KripkeState, ListSet<InsimilarityWitness>>
            nextTable;

    private SerializableLabeledTable<KripkeState, KripkeState, ListSet<InsimilarityWitness>>
            currentTable;

    private boolean initialized;
    private int currentRound;
    private KripkeStructure rowKripke;
    private KripkeStructure columnKripke;
    private boolean finished;

    protected RoundBasedBisimulationEvaluator(
            int startRound, KripkeStructure firstKripke, KripkeStructure secondKripke) {

        this.initialized = false;
        this.finished = false;
        this.rowKripke = firstKripke;
        this.columnKripke = secondKripke;

        this.nextTable = new SerializableLabeledTable<>();
        this.nextTable.getRowLabels().addAll(firstKripke.getVertexValues());
        this.nextTable.getColumnLabels().addAll(secondKripke.getVertexValues());

        this.currentTable = this.nextTable.clone();

        for (int i = 0; i < startRound; i++) {
            this.updateTable();
        }
    }

    public RoundBasedBisimulationEvaluator(
            KripkeStructure firstKripke, KripkeStructure secondKripke) {

        this.finished = false;
        this.rowKripke = firstKripke;
        this.columnKripke = secondKripke;

        this.nextTable = new SerializableLabeledTable<>();
        this.nextTable.getRowLabels().addAll(firstKripke.getVertexValues());
        this.nextTable.getColumnLabels().addAll(secondKripke.getVertexValues());

        this.currentTable = this.nextTable.clone();

        initializeTable();
    }

    private void initializeTable() {
        for (KripkeState rowLabel : this.nextTable.getRowLabels()) {
            for (KripkeState columnLabel : this.nextTable.getColumnLabels()) {
                if (rowLabel.getValuation().equals(columnLabel.getValuation())) {

                    this.nextTable.setCell(rowLabel, columnLabel, new ListSet<>());

                } else {
                    this.nextTable.setCell(
                            rowLabel,
                            columnLabel,
                            new ListSet<>(new IncompatibleStates(rowLabel, columnLabel)));
                }
            }
        }

        this.initialized = true;
        this.currentRound = 0;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    /**
     * @return if {@link isFinished()} returns false this will only return the solution of the
     *     current round
     */
    public ListSet<SerializablePair<KripkeState, KripkeState>> generateBisimulation() {
        ListSet<SerializablePair<KripkeState, KripkeState>> result = new ListSet<>();

        for (KripkeState firstState : this.rowKripke.getVertexValues()) {
            for (KripkeState secondState : this.columnKripke.getVertexValues()) {

                if (this.nextTable.getCell(firstState, secondState).isEmpty()) {

                    result.add(new SerializablePair<>(firstState, secondState));
                }
            }
        }

        return result;
    }

    /** generates the next round and returns the resulting table */
    public SerializableLabeledTable<KripkeState, KripkeState, ListSet<InsimilarityWitness>>
            generateNextRound() {

        this.updateTable();
        return this.nextTable;
    }

    /**
     * @return pair consisting of the current round and a possible table entry
     */
    public SerializablePair<Integer, InsimilarityWitness> getTableEntry(
            KripkeState firstState, KripkeState secondState) {

        ListSet<InsimilarityWitness> witnesses = this.nextTable.getCell(firstState, secondState);

        if (witnesses.isEmpty()) {
            return new SerializablePair<>(this.currentRound, new NoWitness());
        }

        return new SerializablePair<>(this.currentRound, witnesses.toList().get(0));
    }

    /**
     * @return SerializablePair consisting of the current round and all possible witnesses. The
     *     ListSet of witnesses can be empty.
     */
    public SerializablePair<Integer, ListSet<InsimilarityWitness>> getTableEntryWithAllWitnesses(
            KripkeState firstState, KripkeState secondState) {
        return new SerializablePair<>(currentRound, nextTable.getCell(firstState, secondState));
    }

    /**
     * @return a {@link KripkeState} that is bisimilar to the target of edge
     * @throws IllegalArgumentException if no counter example exists
     */
    public KripkeState getCounterExample(
            SerializablePair<KripkeState, KripkeState> position, IncompatibleEdge edge) {

        KripkeState source = edge.getSource();
        KripkeState target = edge.getTarget();
        KripkeState firstState = position.first();
        KripkeState secondState = position.second();

        if (source.equals(firstState)) {
            for (KripkeState neighbor : columnKripke.getOutNeighborValues(secondState)) {
                if (currentTable.getCell(target, neighbor).isEmpty()) {
                    return neighbor;
                }
            }
        } else {
            for (KripkeState neighbor : rowKripke.getOutNeighborValues(firstState)) {
                if (currentTable.getCell(neighbor, target).isEmpty()) {
                    return neighbor;
                }
            }
        }

        throw new IllegalArgumentException(
                "there is no counter example for "
                        + "pair "
                        + position.toString()
                        + " and edge "
                        + edge.toString());
    }

    /**
     * if table is correct, internally the next step will be calculated
     *
     * @return contains the position of wrong cells (first) and the ListSet of InsimilarityWitness
     *     for those cells (second)
     */
    public ListSet<
                    SerializablePair<
                            SerializablePair<KripkeState, KripkeState>,
                            ListSet<InsimilarityWitness>>>
            checkTable(LabeledTable<KripkeState, KripkeState, InsimilarityWitness> table) {

        ListSet<
                        SerializablePair<
                                SerializablePair<KripkeState, KripkeState>,
                                ListSet<InsimilarityWitness>>>
                wrongCells = compareTable(table);

        if (wrongCells.isEmpty() && !this.isFinished()) {
            updateTable();
        }

        return wrongCells;
    }

    /**
     * called by checkTable
     *
     * @return contains the position of wrong cells (first) and the ListSet of InsimilarityWitness
     *     for those cells (second)
     */
    private ListSet<
                    SerializablePair<
                            SerializablePair<KripkeState, KripkeState>,
                            ListSet<InsimilarityWitness>>>
            compareTable(LabeledTable<KripkeState, KripkeState, InsimilarityWitness> table) {

        ListSet<
                        SerializablePair<
                                SerializablePair<KripkeState, KripkeState>,
                                ListSet<InsimilarityWitness>>>
                wrongCells = new ListSet<>();

        for (KripkeState rowLabel : this.nextTable.getRowLabels()) {
            for (KripkeState columnLabel : this.nextTable.getColumnLabels()) {

                ListSet<InsimilarityWitness> witnesses =
                        this.nextTable.getCell(rowLabel, columnLabel);

                InsimilarityWitness witness = table.getCell(rowLabel, columnLabel);

                if (witness instanceof NoWitness) {
                    if (!witnesses.isEmpty()) {

                        wrongCells.add(
                                new SerializablePair<>(
                                        new SerializablePair<>(rowLabel, columnLabel), witnesses));
                    }
                } else {
                    if (!witnesses.contains(witness)) {

                        wrongCells.add(
                                new SerializablePair<>(
                                        new SerializablePair<>(rowLabel, columnLabel), witnesses));
                    }
                }
            }
        }

        return wrongCells;
    }

    private void updateTable() {
        if (!this.initialized) {
            this.initializeTable();
        } else {

            this.currentTable = this.nextTable.clone();

            ListSet<
                            SerializablePair<
                                    SerializablePair<KripkeState, KripkeState>,
                                    ListSet<InsimilarityWitness>>>
                    toChange = new ListSet<>();

            for (KripkeState rowLabel : this.nextTable.getRowLabels()) {
                for (KripkeState columnLabel : this.nextTable.getColumnLabels()) {
                    checkCell(toChange, rowLabel, columnLabel);
                }
            }

            if (toChange.isEmpty()) {
                this.finished = true;
            }

            for (SerializablePair<
                            SerializablePair<KripkeState, KripkeState>,
                            ListSet<InsimilarityWitness>>
                    cellPosition : toChange) {

                this.nextTable.setCell(
                        cellPosition.first().first(),
                        cellPosition.first().second(),
                        cellPosition.second());
            }

            this.currentRound++;
        }
    }

    /**
     * called by updateTable
     *
     * <p>if (rowLabel, columnLabel) needs to updated it will be added to toChange
     */
    private void checkCell(
            ListSet<
                            SerializablePair<
                                    SerializablePair<KripkeState, KripkeState>,
                                    ListSet<InsimilarityWitness>>>
                    toChange,
            KripkeState rowLabel,
            KripkeState columnLabel) {

        if (this.nextTable.getCell(rowLabel, columnLabel).isEmpty()) {
            ListSet<InsimilarityWitness> witnesses = new ListSet<>();
            for (KripkeState rowNeighbor : rowKripke.getOutNeighborValues(rowLabel)) {

                boolean isBisimulatable = false;
                for (KripkeState columnNeighbor : columnKripke.getOutNeighborValues(columnLabel)) {
                    if (this.nextTable.getCell(rowNeighbor, columnNeighbor).isEmpty()) {
                        isBisimulatable = true;
                        break;
                    }
                }
                if (!isBisimulatable) {
                    witnesses.add(new IncompatibleEdge(rowLabel, rowNeighbor));
                }
            }
            for (KripkeState columnNeighbor : columnKripke.getOutNeighborValues(columnLabel)) {

                boolean isBisimulatable = false;
                for (KripkeState rowNeighbor : rowKripke.getOutNeighborValues(rowLabel)) {
                    if (this.nextTable.getCell(rowNeighbor, columnNeighbor).isEmpty()) {
                        isBisimulatable = true;
                        break;
                    }
                }
                if (!isBisimulatable) {

                    witnesses.add(new IncompatibleEdge(columnLabel, columnNeighbor));
                }
            }
            if (!witnesses.isEmpty()) {
                toChange.add(
                        new SerializablePair<>(
                                new SerializablePair<>(rowLabel, columnLabel), witnesses));
            }
        }
    }

    public RoundBasedBisimulationEvaluator clone() {

        RoundBasedBisimulationEvaluator result = new RoundBasedBisimulationEvaluator();

        result.currentTable = this.currentTable.clone();
        result.nextTable = this.nextTable.clone();
        result.columnKripke = this.columnKripke.clone();
        result.rowKripke = this.rowKripke.clone();
        result.currentRound = currentRound;
        result.finished = finished;
        result.initialized = initialized;

        return result;
    }
}
