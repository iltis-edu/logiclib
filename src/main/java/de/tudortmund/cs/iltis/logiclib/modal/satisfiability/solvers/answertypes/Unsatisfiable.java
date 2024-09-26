package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

public class Unsatisfiable implements Answer {

    @Override
    public AnswerType getType() {
        return AnswerType.UNSATISFIABLE;
    }
}
