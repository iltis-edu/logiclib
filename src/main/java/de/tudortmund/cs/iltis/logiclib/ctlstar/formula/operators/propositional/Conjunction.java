package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** An n-ary propositional operator with n>=2 */
public class Conjunction extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Conjunction() {}

    public Conjunction(final CtlStarFormula... subformulae) {
        super(false, subformulae);
        if (getArity() == 0)
            throw new IllegalArgumentException("a conjunction needs at least 2 operands!");
    }

    @Override
    public FormulaType getType() {
        return FormulaType.CONJUNCTION;
    }
}
