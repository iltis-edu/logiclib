package de.tudortmund.cs.iltis.logiclib.predicate.signature;

/**
 * An exception for signaling that a given combination of function and relation symbols does not
 * form a valid signature.
 *
 * <p>Uses a {@link ValidityFaultCollection}-object as "payload".
 */
public class InvalidSignatureException extends RuntimeException {

    private ValidityFaultCollection faults;

    public InvalidSignatureException(ValidityFaultCollection faults) {
        super("Combination of function and relation symbols is not valid as signature");
        this.faults = faults;
    }

    public InvalidSignatureException(
            ValidityFaultCollection faults, String message, Throwable cause) {
        super(message, cause);
        this.faults = faults;
    }

    public ValidityFaultCollection getFaultCollection() {
        return faults;
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private InvalidSignatureException() {}
}
