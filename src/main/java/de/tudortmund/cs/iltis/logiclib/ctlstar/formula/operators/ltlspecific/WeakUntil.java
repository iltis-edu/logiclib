package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary LTL operator */
public class WeakUntil extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private WeakUntil() {}

    public WeakUntil(final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.WEAKUNTIL;
    }
}
