package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

public class InvalidFormula implements Answer {

    // serialization
    public InvalidFormula() {}

    @Override
    public AnswerType getType() {
        return AnswerType.INVALID;
    }
}
