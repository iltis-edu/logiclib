package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Interpretation;
import java.io.Serializable;

public class ModalInterpretation implements Interpretation, Serializable {
    private KripkeStructure structure;
    private KripkeState state;

    public ModalInterpretation(KripkeStructure structure, KripkeState state) {
        if (!structure.hasVertex(state))
            throw new IllegalArgumentException("State " + state + " does not belong to structure!");
        this.structure = structure;
        this.state = state;
    }

    @Override
    public boolean isCompatible(ModalFormula formula) {
        return true;
    }

    public KripkeStructure getStructure() {
        return this.structure;
    }

    public KripkeState getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return this.structure.toString() + ", " + this.state.writeName();
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private ModalInterpretation() {}
}
