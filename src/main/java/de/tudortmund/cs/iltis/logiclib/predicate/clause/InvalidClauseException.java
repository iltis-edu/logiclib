package de.tudortmund.cs.iltis.logiclib.predicate.clause;

public class InvalidClauseException extends RuntimeException {

    private ClauseFaultCollection faults;

    // needed for serialization
    private InvalidClauseException() {}

    public InvalidClauseException(ClauseFaultCollection faults) {
        super("some subterms are not valid in a clause");
        this.faults = faults;
    }

    public InvalidClauseException(ClauseFaultCollection faults, String message, Throwable cause) {
        super(message, cause);
        this.faults = faults;
    }

    public ClauseFaultCollection getFaultCollection() {
        return faults;
    }
}
