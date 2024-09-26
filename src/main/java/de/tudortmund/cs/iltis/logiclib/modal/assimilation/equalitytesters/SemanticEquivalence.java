package de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;

public class SemanticEquivalence
        implements SerializableBiFunction<ModalFormula, ModalFormula, Boolean> {

    public Boolean apply(ModalFormula left, ModalFormula right) {
        return Comparator.equivalent(left, right).test();
    }
}
