package de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import java.io.Serializable;

public class SemanticAssimilationStrategy implements AssimilationStrategy, Serializable {

    /* Needed for serialization */
    public SemanticAssimilationStrategy() {}

    @Override
    public boolean isComplete(ModalFormula source, ModalFormula target) {
        return Comparator.equivalent(source, target).test();
    }
}
