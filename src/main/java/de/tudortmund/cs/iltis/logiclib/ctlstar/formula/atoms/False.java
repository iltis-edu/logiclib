package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** False represents the logical constant known as 'bottom' */
public class False extends CtlStarFormula {

    @Override
    public FormulaType getType() {
        return FormulaType.FALSE;
    }
}
