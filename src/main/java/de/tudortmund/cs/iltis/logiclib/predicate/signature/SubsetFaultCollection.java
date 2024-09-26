package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.SymbolWithArity;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.utils.SymbolFault;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.List;

/**
 * An immutable fault collection to be used when a signature is assumed to be a subset of another
 * signature, but is not.
 */
public class SubsetFaultCollection extends FaultCollection<SubsetFaultReason, SubsetFault> {

    /** may be null */
    private Signature subSig;

    /** may be null */
    private SignatureCheckable superSig;

    /** Constructs a new fault collection without any fault. */
    public SubsetFaultCollection(Signature subSig, SignatureCheckable superSig) {
        super();
        this.subSig = subSig;
        this.superSig = superSig;
    }

    /**
     * Constructs a new fault collection containing the given faults.
     *
     * @throws NullPointerException if faults is null
     */
    public SubsetFaultCollection(
            List<SubsetFault> faults, Signature subSig, SignatureCheckable superSig) {
        super(faults);
        this.subSig = subSig;
        this.superSig = superSig;
    }

    /** {@inheritDoc} */
    @Override
    public SubsetFaultCollection withFault(SubsetFault fault) {
        return (SubsetFaultCollection) super.withFault(fault);
    }

    /** Utility method for {@link #withFault(SubsetFault)}. */
    public SubsetFaultCollection withFault(
            SubsetFaultReason reason, SymbolWithArity subSymbol, SymbolWithArity superSymbol) {
        SubsetFault fault = new SubsetFault(reason, subSymbol, superSymbol);
        return withFault(fault);
    }

    /**
     * @return the signature which is assumed to be a subset of the other, may be null
     */
    public Signature getSubSig() {
        return subSig;
    }

    /**
     * Utility method which tries to cast the result of {@link #getSuperSigCheckable()} to {@link
     * Signature}.
     *
     * @return the signature which is assumed to be a superset of the other, may be null
     */
    public Signature getSuperSig() {
        return superSig instanceof Signature ? (Signature) superSig : null;
    }

    /**
     * @return the signature-like object which is assumed to be a superset of the other, may be null
     */
    public SignatureCheckable getSuperSigCheckable() {
        return superSig;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((subSig == null) ? 0 : subSig.hashCode());
        result = prime * result + ((superSig == null) ? 0 : superSig.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        SubsetFaultCollection other = (SubsetFaultCollection) obj;
        if (subSig == null) {
            if (other.subSig != null) return false;
        } else if (!subSig.equals(other.subSig)) return false;
        if (superSig == null) {
            if (other.superSig != null) return false;
        } else if (!superSig.equals(other.superSig)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SubsetFaultCollection [faults = "
                + faults
                + ", subSig = "
                + subSig
                + ", superSig = "
                + superSig
                + "]";
    }

    @Override
    public SubsetFaultCollection clone() {
        return new SubsetFaultCollection(faults, subSig.clone(), superSig.clone());
    }

    /** Fault type to be used in {@link #SubsetFaultCollection()}. */
    public static class SubsetFault extends SymbolFault<SubsetFaultReason> {

        /**
         * Constructs a new fault object.
         *
         * @throws NullPointerException if reason is null
         */
        public SubsetFault(
                SubsetFaultReason reason, SymbolWithArity subSymbol, SymbolWithArity superSymbol) {
            super(reason, subSymbol, superSymbol);
        }

        /**
         * @return the symbol of the signature which is assumed to be the subset, not null
         */
        public SymbolWithArity getSubSymbol() {
            return (SymbolWithArity) firstSymbol;
        }

        /**
         * @return the symbol of the signature which is assumed to be the superset; may be null, if
         *     a signature-like object cannot determine this symbol
         */
        public SymbolWithArity getSuperSymbol() {
            return (SymbolWithArity) secondSymbol;
        }

        @Override
        protected SubsetFault clone() {
            return new SubsetFault(getReason(), getSubSymbol(), getSuperSymbol());
        }

        /** for serialization */
        private static final long serialVersionUID = 1L;

        /** for GWT serialization */
        @SuppressWarnings("unused")
        private SubsetFault() {}
    }

    /** Enum used as reason type for {@link SubsetFaultCollection}. */
    public enum SubsetFaultReason {
        FUNCTION_SYMBOL_NOT_KNOWN,
        RELATION_SYMBOL_NOT_KNOWN,
        FUNCTION_SYMBOL_SHOULD_BE_RELATION,
        RELATION_SYMBOL_SHOULD_BE_FUNCTION,
        ARITY_OF_FUNCTION_SYMBOL_WRONG,
        INFIX_OF_FUNCTION_SYMBOL_WRONG,
        ARITY_OF_RELATION_SYMBOL_WRONG,
        INFIX_OF_RELATION_SYMBOL_WRONG
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private SubsetFaultCollection() {}
}
