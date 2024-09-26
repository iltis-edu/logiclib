package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A binary propositional operator */
public class Equivalence extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Equivalence() {}

    public Equivalence(final CtlStarFormula leftSubformula, final CtlStarFormula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EQUIVALENCE;
    }
}
