package de.tudortmund.cs.iltis.logiclib.modal.interpretation.bisimulation;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import java.io.Serializable;

/**
 * Witness of two {@link KripkeState} that are not bisimilar, a reason must be specified by
 * implementing classes. If {@link NoWitness} the two states are bisimilar.
 */
public interface InsimilarityWitness extends Serializable {

    boolean equals(Object o);
}
