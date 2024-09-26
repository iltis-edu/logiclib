package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical equivalence. */
public class Equivalence extends Formula {

    public Equivalence(Formula leftSubformula, Formula rightSubformula) {
        super(true, leftSubformula, rightSubformula);
    }

    public Formula getLeftSubformula() {
        return (Formula) children.get(0);
    }

    public Formula getRightSubformula() {
        return (Formula) children.get(1);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.EQUIV;
    }

    @Override
    public Equivalence clone() {
        return new Equivalence(getLeftSubformula().clone(), getRightSubformula().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Equivalence() {}
}
