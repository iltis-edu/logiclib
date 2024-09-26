package de.tudortmund.cs.iltis.logiclib.predicate;

import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/**
 * A symbol with arity used for relations. Immutable class.
 *
 * <p>Intentionally, {@link #equals(Object)} and {@link #compareTo(IndexedSymbol)} do not differ
 * between relation and function symbols.
 */
public class RelationSymbol extends SymbolWithArity {

    public RelationSymbol(IndexedSymbol symbol, int arity, boolean isInfix) {
        super(symbol, arity, isInfix);
    }

    public RelationSymbol(String parseString, int arity, boolean isInfix) {
        super(parseString, arity, isInfix);
    }

    public RelationSymbol(
            String name, String subscript, String superscript, int arity, boolean isInfix) {
        super(name, subscript, superscript, arity, isInfix);
    }

    @Override
    public RelationSymbol clone() {
        return new RelationSymbol(
                getName(), getSubscript(), getSuperscript(), getArity(), isInfix());
    }

    /** Needed for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private RelationSymbol() {}
}
