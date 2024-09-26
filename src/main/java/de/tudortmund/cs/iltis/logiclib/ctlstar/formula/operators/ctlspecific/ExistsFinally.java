package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A unary CTL operator */
public class ExistsFinally extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private ExistsFinally() {}

    public ExistsFinally(final CtlStarFormula subformula) {
        super(true, subformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EXISTSFINALLY;
    }
}
