package de.tudortmund.cs.iltis.logiclib.predicate;

import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/**
 * An indexed symbol used for variables.
 *
 * <p>Intentionally, {@link #equals(Object)} and {@link #compareTo(IndexedSymbol)} do not differ
 * between {@link VariableSymbol} and its superclass {@link IndexedSymbol}.
 */
public class VariableSymbol extends IndexedSymbol {

    public VariableSymbol(IndexedSymbol symbol) {
        super(symbol.getName(), symbol.getSubscript(), symbol.getSuperscript());
    }

    public VariableSymbol(String parseString) {
        super(parseString);
    }

    public VariableSymbol(String name, String subscript, String superscript) {
        super(name, subscript, superscript);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private VariableSymbol() {}
}
