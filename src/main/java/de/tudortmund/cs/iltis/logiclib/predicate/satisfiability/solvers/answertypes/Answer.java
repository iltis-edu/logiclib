package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.Interpretation;
import java.io.Serializable;
import java.util.Optional;

public interface Answer extends Serializable {

    AnswerType getType();

    default Optional<Interpretation<Integer>> getExample() {
        return Optional.empty();
    }
}
