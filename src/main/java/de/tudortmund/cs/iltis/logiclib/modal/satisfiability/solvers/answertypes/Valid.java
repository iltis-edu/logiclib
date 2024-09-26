package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

public class Valid implements Answer {
    @Override
    public AnswerType getType() {
        return AnswerType.VALID;
    }
}
