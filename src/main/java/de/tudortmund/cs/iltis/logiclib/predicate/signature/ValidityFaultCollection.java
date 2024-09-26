package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.SymbolWithArity;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFaultReason;
import de.tudortmund.cs.iltis.utils.SymbolFault;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * An immutable fault collection to be used when a combination of relation and function symbols is
 * assumed to form a valid signature, but is not.
 */
public class ValidityFaultCollection extends FaultCollection<ValidityFaultReason, ValidityFault> {

    /** may be null */
    private Set<RelationSymbol> rels;

    /** may be null */
    private Set<FunctionSymbol> funs;

    /** Constructs a new fault collection without any fault. */
    public ValidityFaultCollection(Set<RelationSymbol> rels, Set<FunctionSymbol> funs) {
        super();
        this.rels = rels;
        this.funs = funs;
    }

    /**
     * Constructs a new fault collection containing the given faults.
     *
     * @throws NullPointerException if faults is null
     */
    public ValidityFaultCollection(
            List<ValidityFault> faults, Set<RelationSymbol> rels, Set<FunctionSymbol> funs) {
        super(faults);
        this.rels = rels;
        this.funs = funs;
    }

    /** {@inheritDoc} */
    @Override
    public ValidityFaultCollection withFault(ValidityFault fault) {
        return (ValidityFaultCollection) super.withFault(fault);
    }

    /** Utility method for {@link #withFault(ValidityFault)}. */
    public ValidityFaultCollection withFault(
            ValidityFaultReason reason, SymbolWithArity firstSymbol, SymbolWithArity secondSymbol) {
        ValidityFault fault = new ValidityFault(reason, firstSymbol, secondSymbol);
        return withFault(fault);
    }

    public Set<RelationSymbol> getRelationSymbols() {
        return rels;
    }

    public Set<FunctionSymbol> getFunctionSymbols() {
        return funs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((funs == null) ? 0 : funs.hashCode());
        result = prime * result + ((rels == null) ? 0 : rels.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ValidityFaultCollection other = (ValidityFaultCollection) obj;
        if (funs == null) {
            if (other.funs != null) return false;
        } else if (!funs.equals(other.funs)) return false;
        if (rels == null) {
            if (other.rels != null) return false;
        } else if (!rels.equals(other.rels)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ValidityFaultCollection [faults = "
                + faults
                + ", rels = "
                + rels
                + ", funs = "
                + funs
                + "]";
    }

    @Override
    public ValidityFaultCollection clone() {
        return new ValidityFaultCollection(faults, new TreeSet<>(rels), new TreeSet<>(funs));
    }

    /** Fault type to be used in {@link ValidityFaultCollection}. */
    public static class ValidityFault extends SymbolFault<ValidityFaultReason> {

        /**
         * Constructs a new fault object.
         *
         * @throws NullPointerException if reason is null
         */
        public ValidityFault(
                ValidityFaultReason reason,
                SymbolWithArity firstSymbol,
                SymbolWithArity secondSymbol) {
            super(reason, firstSymbol, secondSymbol);
        }

        public SymbolWithArity getFirstSymbol() {
            return (SymbolWithArity) firstSymbol;
        }

        public SymbolWithArity getSecondSymbol() {
            return (SymbolWithArity) secondSymbol;
        }

        @Override
        protected ValidityFault clone() {
            return new ValidityFault(getReason(), getFirstSymbol(), getSecondSymbol());
        }

        /** for serialization */
        private static final long serialVersionUID = 1L;

        /** for GWT serialization */
        @SuppressWarnings("unused")
        private ValidityFault() {}
    }

    /** Enum used as reason type for {@link ValidityFaultCollection}. */
    public enum ValidityFaultReason {
        /** first symbol is always the relation symbol */
        SAME_SYMBOL_FOR_RELATION_AND_FUNCTION,
        SAME_FUNCTION_SYMBOL_WITH_DIFFERENT_ARITIES,
        SAME_FUNCTION_SYMBOL_WITH_DIFFERENT_INFIX,
        SAME_RELATION_SYMBOL_WITH_DIFFERENT_ARITIES,
        SAME_RELATION_SYMBOL_WITH_DIFFERENT_INFIX
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private ValidityFaultCollection() {}
}
