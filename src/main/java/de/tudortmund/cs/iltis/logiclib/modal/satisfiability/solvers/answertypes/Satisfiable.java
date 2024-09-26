package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Satisfiable implements Answer {

    private List<Valuation> assignments;

    public Satisfiable() {
        assignments = new ArrayList<>();
    }

    public Satisfiable(Collection<Valuation> assignments) {
        this.assignments = new ArrayList<>(assignments);
    }

    @Override
    public AnswerType getType() {
        return AnswerType.SATISFIABLE;
    }

    @Override
    public List<Valuation> getSatisfyingAssignments() {
        return assignments;
    }
}
