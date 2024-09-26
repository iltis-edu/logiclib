package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

public class FailedSolving implements Answer {

    // serialization
    public FailedSolving() {}

    @Override
    public AnswerType getType() {
        return AnswerType.NO_DECISION;
    }
}
