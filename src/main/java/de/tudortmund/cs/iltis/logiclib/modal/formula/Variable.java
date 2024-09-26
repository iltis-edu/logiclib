package de.tudortmund.cs.iltis.logiclib.modal.formula;

import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/** Propositional variable. */
@SuppressWarnings("serial")
public class Variable extends ModalFormula {

    // needed for serialization
    private Variable() {}

    public Variable(IndexedSymbol name) {
        super(name);
    }

    public Variable(final String name) {
        this(new IndexedSymbol(name));
    }

    public FormulaType getType() {
        return FormulaType.VARIABLE;
    }

    @Override
    public Variable clone() {
        return new Variable(getName().clone());
    }
}
