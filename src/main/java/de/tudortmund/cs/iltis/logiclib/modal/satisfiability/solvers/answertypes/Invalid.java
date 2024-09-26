package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

public class Invalid implements Answer {

    @Override
    public AnswerType getType() {
        return AnswerType.INVALID;
    }
}
