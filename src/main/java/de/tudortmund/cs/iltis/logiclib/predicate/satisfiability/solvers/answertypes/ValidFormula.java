package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

public class ValidFormula implements Answer {

    // serialization
    public ValidFormula() {}

    @Override
    public AnswerType getType() {
        return AnswerType.VALID;
    }
}
