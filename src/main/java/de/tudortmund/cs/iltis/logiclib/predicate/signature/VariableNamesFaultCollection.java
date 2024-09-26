package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.SymbolWithArity;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFaultReason;
import de.tudortmund.cs.iltis.utils.SymbolFault;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * An immutable fault collection to be used when a combination of variable symbols and a signature
 * is assumed to be valid, but is not.
 */
public class VariableNamesFaultCollection
        extends FaultCollection<VariableNamesFaultReason, VariableNamesFault> {

    /** may be null */
    private Set<VariableSymbol> vars;

    /** may be null */
    private SignatureCheckable sig;

    /** Constructs a new fault collection without any fault. */
    public VariableNamesFaultCollection(Set<VariableSymbol> vars, SignatureCheckable sig) {
        super();
        this.vars = vars;
        this.sig = sig;
    }

    /**
     * Constructs a new fault collection containing the given faults.
     *
     * @throws NullPointerException if faults is null
     */
    public VariableNamesFaultCollection(
            List<VariableNamesFault> faults, Set<VariableSymbol> vars, SignatureCheckable sig) {
        super(faults);
        this.vars = vars;
        this.sig = sig;
    }

    /** {@inheritDoc} */
    @Override
    public VariableNamesFaultCollection withFault(VariableNamesFault fault) {
        return (VariableNamesFaultCollection) super.withFault(fault);
    }

    /** Utility method for {@link #withFault(VariableNamesFault)}. */
    public VariableNamesFaultCollection withFault(
            VariableNamesFaultReason reason, VariableSymbol varSymbol, SymbolWithArity sigSymbol) {
        VariableNamesFault fault = new VariableNamesFault(reason, varSymbol, sigSymbol);
        return withFault(fault);
    }

    /**
     * Returns the variable set, if available.
     *
     * @return the variable set, may be null
     */
    public Set<VariableSymbol> getVariableSet() {
        return vars;
    }

    /**
     * Returns the signature, if available. Utility method, which casts the result of {@link
     * #getSignatureCheckable()} to {@link Signature} if possible.
     *
     * @return the signature, may be null
     */
    public Signature getSignature() {
        return sig instanceof Signature ? (Signature) sig : null;
    }

    /**
     * Returns the signature-like object, if available.
     *
     * @return the signature checkable object, may be null
     */
    public SignatureCheckable getSignatureCheckable() {
        return sig;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((sig == null) ? 0 : sig.hashCode());
        result = prime * result + ((vars == null) ? 0 : vars.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        VariableNamesFaultCollection other = (VariableNamesFaultCollection) obj;
        if (sig == null) {
            if (other.sig != null) return false;
        } else if (!sig.equals(other.sig)) return false;
        if (vars == null) {
            if (other.vars != null) return false;
        } else if (!vars.equals(other.vars)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "VariableNamesFaultCollection [faults = "
                + faults
                + ", variables = "
                + vars
                + ", signature = "
                + sig
                + "]";
    }

    @Override
    public VariableNamesFaultCollection clone() {
        return new VariableNamesFaultCollection(faults, new TreeSet<>(vars), sig.clone());
    }

    /** Fault type to be used in {@link VariableNamesFaultCollection}. */
    public static class VariableNamesFault extends SymbolFault<VariableNamesFaultReason> {

        /**
         * Constructs a new fault object.
         *
         * @throws NullPointerException if reason is null
         */
        public VariableNamesFault(
                VariableNamesFaultReason reason,
                VariableSymbol varSymbol,
                SymbolWithArity sigSymbol) {
            super(reason, varSymbol, sigSymbol);
        }

        /**
         * @return the variable symbol, not null
         */
        public VariableSymbol getVariableSymbol() {
            return (VariableSymbol) firstSymbol;
        }

        /**
         * @return the signature symbol, the variable symbol is conflicting with; may be null, if a
         *     signature-like object cannot determine this symbol
         */
        public SymbolWithArity getSignatureSymbol() {
            return (SymbolWithArity) secondSymbol;
        }

        @Override
        protected VariableNamesFault clone() {
            return new VariableNamesFault(getReason(), getVariableSymbol(), getSignatureSymbol());
        }

        /** for serialization */
        private static final long serialVersionUID = 1L;

        /** for GWT serialization */
        @SuppressWarnings("unused")
        private VariableNamesFault() {}
    }

    /** Enum used as reason type for {@link VariableNamesFaultCollection}. */
    public enum VariableNamesFaultReason {
        /**
         * If this reason is given for a formula created by {@link FormulaCreator}, the erroneous
         * variable symbol is placed at a quantifier. This is, because with the current
         * implementation of {@link FormulaCreator#FormulaCreator(Signature)} an ambiguous symbol,
         * which is appears in the given signature, at any other place is interpreted as constant.
         */
        SAME_SYMBOL_FOR_VARIABLE_AND_FUNCTION,
        /**
         * If this reason is given for a formula created by {@link FormulaCreator}, the erroneous
         * variable symbol is placed at a quantifier. This is, because with the current
         * implementation of {@link FormulaCreator#FormulaCreator(Signature)} an ambiguous symbol,
         * which is appears in the given signature, at any other place is interpreted as constant.
         */
        SAME_SYMBOL_FOR_VARIABLE_AND_RELATION
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private VariableNamesFaultCollection() {}
}
