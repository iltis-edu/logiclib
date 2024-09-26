package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

public class ServerFailure implements Answer {

    // serialization
    public ServerFailure() {}

    @Override
    public AnswerType getType() {
        return AnswerType.SERVER_FAILURE;
    }
}
