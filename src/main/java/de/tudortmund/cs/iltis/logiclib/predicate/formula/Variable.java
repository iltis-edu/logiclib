package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/** Representation of a predicate logical variable. */
public class Variable extends Term {

    public Variable(VariableSymbol name) {
        super(name);
    }

    public Variable(IndexedSymbol name) {
        this(new VariableSymbol(name));
    }

    public Variable(String name) {
        this(new VariableSymbol(name));
    }

    @Override
    public FormulaType getType() {
        return FormulaType.VARIABLE;
    }

    @Override
    public VariableSymbol getName() {
        return (VariableSymbol) super.getName();
    }

    @Override
    public Variable clone() {
        return new Variable(getName().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Variable() {}
}
