package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A unary LTL operator */
public class Globally extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Globally() {}

    public Globally(final CtlStarFormula subformula) {
        super(true, subformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.GLOBALLY;
    }
}
