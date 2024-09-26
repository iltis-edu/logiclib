package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary propositional operator */
public class Implication extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Implication() {}

    public Implication(final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.IMPLICATION;
    }
}
