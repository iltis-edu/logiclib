package de.tudortmund.cs.iltis.logiclib.predicate.formula;

/** Basic class for a predicate logical quantifier. */
public abstract class Quantifier extends Formula {

    public Quantifier(Variable variable, Formula formula) {
        super(true, variable, formula);
    }

    public Variable getVariable() {
        return (Variable) children.get(0);
    }

    public Formula getFormula() {
        return (Formula) children.get(1);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    protected Quantifier() {}
}
