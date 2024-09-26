package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical negation. */
public class Negation extends Formula {

    public Negation(Formula subformula) {
        super(true, new Formula[] {subformula});
    }

    public Formula getSubformula() {
        return (Formula) children.get(0);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.NEG;
    }

    @Override
    public Negation clone() {
        return new Negation(getSubformula().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Negation() {}
}
