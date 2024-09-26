package de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.io.Serializable;

public interface AssimilationStrategy extends Serializable {
    boolean isComplete(ModalFormula source, ModalFormula target);
}
