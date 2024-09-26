package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** True represents the logical constant known as 'top' */
public class True extends CtlStarFormula {

    @Override
    public FormulaType getType() {
        return FormulaType.TRUE;
    }
}
