package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical existential quantifier. */
public class ExistentialQuantifier extends Quantifier {

    public ExistentialQuantifier(Variable variable, Formula formula) {
        super(variable, formula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EXISTS;
    }

    @Override
    public ExistentialQuantifier clone() {
        return new ExistentialQuantifier(getVariable().clone(), getFormula().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private ExistentialQuantifier() {}
}
