package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializableLabeledTable;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * BisimulationEvalutor that checks every user-input.
 *
 * <p>If a pair of kripke-states is marked, the states are not bisimilar.
 */
public class InputBasedBisimulationEvaluator implements Serializable {

    // needed for serialization
    public InputBasedBisimulationEvaluator() {}

    private KripkeStructure firstKripke;
    private KripkeStructure secondKripke;
    private RoundBasedBisimulationEvaluator generator;

    private Map<SerializablePair<KripkeState, KripkeState>, ListSet<InsimilarityWitness>>
            possibleInputs;

    private Map<SerializablePair<KripkeState, KripkeState>, InsimilarityWitness> inputs;

    public InputBasedBisimulationEvaluator(
            KripkeStructure firstKripke, KripkeStructure secondKripke) {

        this.firstKripke = firstKripke;
        this.secondKripke = secondKripke;

        this.generator = new RoundBasedBisimulationEvaluator(0, firstKripke, secondKripke);

        this.possibleInputs = new HashMap<>();
        this.inputs = new HashMap<>();

        generateNextRound();
    }

    private void generateNextRound() {

        if (this.possibleInputs.isEmpty()) {

            SerializableLabeledTable<KripkeState, KripkeState, ListSet<InsimilarityWitness>> table =
                    this.generator.generateNextRound();

            for (KripkeState firstState : this.firstKripke.getVertexValues()) {

                for (KripkeState secondState : this.secondKripke.getVertexValues()) {

                    ListSet<InsimilarityWitness> witnesses = table.getCell(firstState, secondState);

                    SerializablePair<KripkeState, KripkeState> position =
                            new SerializablePair<>(firstState, secondState);

                    if (!witnesses.isEmpty() && !this.inputs.containsKey(position)) {
                        this.possibleInputs.put(position, witnesses);
                    }
                }
            }
        }
    }

    public int getCurrentRound() {
        return this.generator.getCurrentRound();
    }

    public boolean isFinished() {
        if (!this.possibleInputs.isEmpty()) {
            return false;
        }
        this.generateNextRound();
        return this.generator.isFinished();
    }

    /**
     * @return if {@link isFinished()} returns false this will not return the correct result
     */
    public ListSet<SerializablePair<KripkeState, KripkeState>> generateBisimulation() {
        return this.generator.generateBisimulation();
    }

    /**
     * @param position the position in the table
     * @param witness the reason chosen by user
     * @return the second part of the returned pair is (true, {}) if the input is a correct
     *     solution, (false, {}) if the pair should not have been marked (false, {witnesses}) if the
     *     witness is not correct
     */
    public SerializablePair<Integer, SerializablePair<Boolean, ListSet<InsimilarityWitness>>>
            checkInput(
                    SerializablePair<KripkeState, KripkeState> position,
                    InsimilarityWitness witness) {

        if (this.possibleInputs.isEmpty()) {
            this.generateNextRound();
        }

        int currentRound = this.getCurrentRound();

        if (this.possibleInputs.containsKey(position)) {

            if (witness instanceof NoWitness) {

                return new SerializablePair<>(
                        currentRound, new SerializablePair<>(false, new ListSet<>()));
            }
            ListSet<InsimilarityWitness> witnesses = this.possibleInputs.get(position);

            if (witnesses.contains(witness)) {
                updateInputs(position, witness);

                return new SerializablePair<>(
                        currentRound, new SerializablePair<>(true, new ListSet<>()));
            } else {
                return new SerializablePair<>(
                        currentRound, new SerializablePair<>(false, witnesses));
            }
        } else {

            if (witness instanceof NoWitness) {

                return new SerializablePair<>(
                        currentRound, new SerializablePair<>(true, new ListSet<>()));
            }

            return new SerializablePair<>(
                    currentRound, new SerializablePair<>(false, new ListSet<>()));
        }
    }

    private void updateInputs(
            SerializablePair<KripkeState, KripkeState> position, InsimilarityWitness witness) {

        this.inputs.put(position, witness);
        this.possibleInputs.remove(position);
    }
}
