package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

public class ValidUnderConstraints implements Answer {

    // serialization
    public ValidUnderConstraints() {}

    @Override
    public AnswerType getType() {
        return AnswerType.VALID_UNDER_CONSTRAINTS;
    }
}
