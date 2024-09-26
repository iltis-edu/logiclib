package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A unary LTL operator */
public class Next extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Next() {}

    public Next(final CtlStarFormula subformula) {
        super(true, subformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.NEXT;
    }
}
