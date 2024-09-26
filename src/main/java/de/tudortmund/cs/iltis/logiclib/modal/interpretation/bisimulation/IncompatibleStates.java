package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;

/**
 * Witness of two {@link KripkeState} that are not bisimilar, because they have different
 * valuations.
 *
 * <p>The two states are the ones with different valuations.
 */
public class IncompatibleStates implements InsimilarityWitness {

    private KripkeState firstState;
    private KripkeState secondState;

    // needed for serialization
    @SuppressWarnings("unused")
    private IncompatibleStates() {}

    public IncompatibleStates(KripkeState first, KripkeState second) {
        this.firstState = first;
        this.secondState = second;
    }

    public KripkeState getFirstState() {
        return this.firstState;
    }

    public KripkeState getSecondState() {
        return this.secondState;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncompatibleStates)) {
            return false;
        }

        IncompatibleStates states = (IncompatibleStates) o;
        return this.getFirstState().equals(states.getFirstState())
                && this.getSecondState().equals(states.getSecondState());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + this.firstState.hashCode();
        return prime * result + this.secondState.hashCode();
    }

    public String toString() {
        return "(" + this.getFirstState().toString() + "," + this.getSecondState().toString() + ")";
    }
}
