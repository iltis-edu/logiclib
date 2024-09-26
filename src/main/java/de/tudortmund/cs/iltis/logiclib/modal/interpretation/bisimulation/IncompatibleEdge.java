package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;

/**
 * Witness of two {@link KripkeState} that are not bisimilar, because one has a successor that can
 * not be simulated by the other.
 *
 * <p>Source and target represent the edge that can not be simulated.
 */
public class IncompatibleEdge implements InsimilarityWitness {

    // needed for serialization
    @SuppressWarnings("unused")
    private IncompatibleEdge() {}

    private KripkeState source;
    private KripkeState target;

    public IncompatibleEdge(KripkeState source, KripkeState target) {
        this.source = source;
        this.target = target;
    }

    public KripkeState getSource() {
        return this.source;
    }

    public KripkeState getTarget() {
        return this.target;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncompatibleEdge)) {
            return false;
        }

        IncompatibleEdge incompatible = (IncompatibleEdge) o;

        return this.source.equals(incompatible.getSource())
                && this.target.equals(incompatible.getTarget());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + this.source.hashCode();
        return prime * result + this.target.hashCode();
    }

    public String toString() {
        return "(" + this.source.toString() + "," + this.target.toString() + ")";
    }
}
