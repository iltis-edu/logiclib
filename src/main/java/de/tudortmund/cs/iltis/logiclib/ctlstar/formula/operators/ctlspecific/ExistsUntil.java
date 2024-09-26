package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary CTL operator */
public class ExistsUntil extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private ExistsUntil() {}

    public ExistsUntil(final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EXISTSUNTIL;
    }
}
