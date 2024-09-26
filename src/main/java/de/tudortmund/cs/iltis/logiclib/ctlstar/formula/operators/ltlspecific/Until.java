package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary LTL operator */
public class Until extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Until() {}

    public Until(final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.UNTIL;
    }
}
