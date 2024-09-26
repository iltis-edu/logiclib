package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.FormulaType;

/** A unary propositional operator */
public class Negation extends CtlStarFormula {

    /** needed for serialization */
    @SuppressWarnings("unused")
    private Negation() {}

    public Negation(final CtlStarFormula subformula) {
        super(true, subformula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.NEGATION;
    }
}
