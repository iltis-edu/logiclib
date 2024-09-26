package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface Answer extends Serializable {

    AnswerType getType();

    default List<Valuation> getSatisfyingAssignments() {
        return new ArrayList<>();
    }
}
