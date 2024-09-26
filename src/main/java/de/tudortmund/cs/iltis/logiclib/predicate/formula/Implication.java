package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical implication. */
public class Implication extends Formula {

    public Implication(Formula premise, Formula conclusion) {
        super(true, premise, conclusion);
    }

    public Formula getPremise() {
        return (Formula) children.get(0);
    }

    public Formula getConclusion() {
        return (Formula) children.get(1);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.IMPLIES;
    }

    @Override
    public Implication clone() {
        return new Implication(getPremise().clone(), getConclusion().clone());
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Implication() {}
}
