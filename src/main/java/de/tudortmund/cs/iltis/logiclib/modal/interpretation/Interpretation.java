package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.io.Serializable;

public interface Interpretation extends Serializable {
    boolean isCompatible(final ModalFormula formula);
}
