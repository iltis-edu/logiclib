package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical universal quantifier. */
public class UniversalQuantifier extends Quantifier {

    public UniversalQuantifier(Variable variable, Formula formula) {
        super(variable, formula);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.FORALL;
    }

    @Override
    public UniversalQuantifier clone() {
        return new UniversalQuantifier(getVariable().clone(), getFormula().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private UniversalQuantifier() {}
}
