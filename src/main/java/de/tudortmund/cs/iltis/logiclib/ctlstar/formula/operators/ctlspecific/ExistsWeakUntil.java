package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary CTL operator */
public class ExistsWeakUntil extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private ExistsWeakUntil() {}

    public ExistsWeakUntil(
            final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EXISTSWEAKUNTIL;
    }
}
