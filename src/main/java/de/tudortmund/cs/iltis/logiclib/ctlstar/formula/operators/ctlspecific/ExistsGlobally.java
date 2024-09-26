package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A unary CTL operator */
public class ExistsGlobally extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private ExistsGlobally() {}

    public ExistsGlobally(final CtlStarFormula subformula) {
        super(true, subformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EXISTSGLOBALLY;
    }
}
