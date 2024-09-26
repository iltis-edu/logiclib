package de.tudortmund.cs.iltis.logiclib.predicate;

import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/**
 * A symbol with arity used for functions. Immutable class.
 *
 * <p>Intentionally, {@link #equals(Object)} and {@link #compareTo(IndexedSymbol)} do not differ
 * between relation and function symbols.
 */
public class FunctionSymbol extends SymbolWithArity {

    public FunctionSymbol(IndexedSymbol symbol, int arity, boolean isInfix) {
        super(symbol, arity, isInfix);
    }

    public FunctionSymbol(String parseString, int arity, boolean isInfix) {
        super(parseString, arity, isInfix);
    }

    public FunctionSymbol(
            String name, String subscript, String superscript, int arity, boolean isInfix) {
        super(name, subscript, superscript, arity, isInfix);
    }

    @Override
    public FunctionSymbol clone() {
        return new FunctionSymbol(
                getName(), getSubscript(), getSuperscript(), getArity(), isInfix());
    }

    /** Needed for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private FunctionSymbol() {}
}
