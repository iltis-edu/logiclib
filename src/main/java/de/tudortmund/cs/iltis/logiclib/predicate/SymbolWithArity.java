package de.tudortmund.cs.iltis.logiclib.predicate;

import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/**
 * An indexed symbol which is extended by an arity and by an indicator, if infix notation shall be
 * used. Immutable class
 */
public class SymbolWithArity extends IndexedSymbol {

    public SymbolWithArity(IndexedSymbol symbol, int arity, boolean isInfix) {
        this(symbol.getName(), symbol.getSubscript(), symbol.getSuperscript(), arity, isInfix);
    }

    public SymbolWithArity(String parseString, int arity, boolean isInfix) {
        super(parseString);
        if (isInfix && arity != 2)
            throw new IllegalArgumentException("Infix flag may be set only with arity two.");
        this.arity = arity;
        this.isInfix = isInfix;
    }

    public SymbolWithArity(
            String name, String subscript, String superscript, int arity, boolean isInfix) {
        super(name, subscript, superscript);
        if (isInfix && arity != 2)
            throw new IllegalArgumentException("Infix flag may be set only with arity two.");
        this.arity = arity;
        this.isInfix = isInfix;
    }

    public int getArity() {
        return arity;
    }

    public boolean isInfix() {
        return isInfix;
    }

    /**
     * Checks whether this symbol may not coexist with the given symbol in one formula.
     *
     * @param symbol the symbol to compare to
     * @return true iff the symbol's names match but their arities or infix values differ
     */
    public boolean contradicts(SymbolWithArity symbol) {
        return contradictsArity(symbol) || contradictsInfix(symbol);
    }

    /**
     * Checks whether this symbol may not coexist with the given symbol in one formula in respect of
     * arity.
     *
     * @param symbol the symbol to compare to
     * @return true iff the symbol's names match but their arities differ
     */
    public boolean contradictsArity(SymbolWithArity symbol) {
        return equalsWithoutArityAndInfix(symbol) && getArity() != symbol.getArity();
    }

    /**
     * Checks whether this symbol may not coexist with the given symbol in one formula in respect of
     * infix value.
     *
     * @param symbol the symbol to compare to
     * @return true iff the symbol's names match but their infix values differ
     */
    public boolean contradictsInfix(SymbolWithArity symbol) {
        return equalsWithoutArityAndInfix(symbol) && isInfix() != symbol.isInfix();
    }

    /*
     * Implementation notice:
     *
     * Because #equals(Object) in IndexedSymbol is implemented by using #compareTo(IndexedSymbol)
     * and #compareTo(IndexedSymbol) is overwritten here, no implementation of #equals(Object)
     * in this class is needed.
     */
    /**
     * Solely compares the symbol's names, subscripts and superscripts.
     *
     * @param symbol the symbol to compare to
     */
    public boolean equalsWithoutArityAndInfix(IndexedSymbol symbol) {
        return super.compareTo(symbol) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + arity;
        result = prime * result + (isInfix ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(IndexedSymbol other) {
        int superDiff = super.compareTo(other);
        if (superDiff != 0) return superDiff;

        if (!(other instanceof SymbolWithArity)) return -1;
        return compareTo((SymbolWithArity) other);
    }

    /** Checks <b>only</b> for the arity and infix. Not infix is smaller than infix. */
    private int compareTo(SymbolWithArity other) {
        // check for arity difference
        int arityDiff = this.arity - other.arity;
        if (arityDiff != 0) return arityDiff;
        // check for infix difference
        if (!this.isInfix && other.isInfix) return -1;
        if (this.isInfix && !other.isInfix) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + getArity();
    }

    @Override
    public SymbolWithArity clone() {
        return new SymbolWithArity(
                getName(), getSubscript(), getSuperscript(), getArity(), isInfix());
    }

    /** Needed for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    protected SymbolWithArity() {}

    private int arity;
    private boolean isInfix;
}
