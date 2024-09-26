package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes;

public class ServerFailure implements Answer {

    @Override
    public AnswerType getType() {
        return AnswerType.SERVER_FAILURE;
    }
}
