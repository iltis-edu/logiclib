package de.tudortmund.cs.iltis.logiclib.predicate.formula.utils;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFault;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFaultReason;
import de.tudortmund.cs.iltis.utils.SymbolFault;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FreeVariablesFaultCollection
        extends FaultCollection<FreeVariablesFaultReason, FreeVariablesFault> {

    private Set<VariableSymbol> allowed;
    private Set<VariableSymbol> actual;

    public FreeVariablesFaultCollection(Set<VariableSymbol> allowed, Set<VariableSymbol> actual) {
        super();
        this.allowed = allowed;
        this.actual = actual;
    }

    public FreeVariablesFaultCollection(
            List<FreeVariablesFault> faults,
            Set<VariableSymbol> allowed,
            Set<VariableSymbol> actual) {
        super(faults);
        this.allowed = allowed;
        this.actual = actual;
    }

    @Override
    public FreeVariablesFaultCollection withFault(FreeVariablesFault fault) {
        return (FreeVariablesFaultCollection) super.withFault(fault);
    }

    public FreeVariablesFaultCollection withFault(
            FreeVariablesFaultReason reason, VariableSymbol varSymbol) {
        FreeVariablesFault fault = new FreeVariablesFault(reason, varSymbol);
        return withFault(fault);
    }

    public Set<VariableSymbol> getAllowedVariableSet() {
        return allowed;
    }

    public Set<VariableSymbol> getActualVariableSet() {
        return actual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actual == null) ? 0 : actual.hashCode());
        result = prime * result + ((allowed == null) ? 0 : allowed.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        FreeVariablesFaultCollection other = (FreeVariablesFaultCollection) obj;
        if (actual == null) {
            if (other.actual != null) return false;
        } else if (!actual.equals(other.actual)) return false;
        if (allowed == null) {
            if (other.allowed != null) return false;
        } else if (!allowed.equals(other.allowed)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "FreeVariablesFaultCollection [faults = "
                + faults
                + ", allowed = "
                + allowed
                + ", actual = "
                + actual
                + "]";
    }

    @Override
    public FreeVariablesFaultCollection clone() {
        return new FreeVariablesFaultCollection(
                faults, new TreeSet<>(allowed), new TreeSet<>(actual));
    }

    public static class FreeVariablesFault extends SymbolFault<FreeVariablesFaultReason> {

        public FreeVariablesFault(FreeVariablesFaultReason reason, VariableSymbol varSymbol) {
            super(reason, varSymbol, null);
        }

        public VariableSymbol getVariableSymbol() {
            return (VariableSymbol) firstSymbol;
        }

        @Override
        protected FreeVariablesFault clone() {
            return new FreeVariablesFault(getReason(), getVariableSymbol());
        }

        /** for serialization */
        private static final long serialVersionUID = 1L;

        /** for GWT serialization */
        @SuppressWarnings("unused")
        private FreeVariablesFault() {}
    }

    public enum FreeVariablesFaultReason {
        SYMBOL_NOT_ALLOWED
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private FreeVariablesFaultCollection() {}
}
