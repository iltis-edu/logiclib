package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;

/** Represents that there are no witnesses and the two {@link KripkeState} are bisimilar. */
public class NoWitness implements InsimilarityWitness {

    // needed for serialization
    public NoWitness() {}

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoWitness)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public String toString() {
        return "()";
    }
}
