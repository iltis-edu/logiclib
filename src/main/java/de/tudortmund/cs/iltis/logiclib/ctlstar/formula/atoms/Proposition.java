package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;

/** A Proposition is an Indexed Symbol, capable of having super- and subscript */
public class Proposition extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Proposition() {}

    public Proposition(IndexedSymbol name) {
        super(true, name);
    }

    public Proposition(final String name) {
        this(new IndexedSymbol(name));
    }

    @Override
    public FormulaType getType() {
        return FormulaType.PROPOSITION;
    }
}
