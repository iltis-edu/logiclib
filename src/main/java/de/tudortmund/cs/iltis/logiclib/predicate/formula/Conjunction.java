package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import java.util.ArrayList;
import java.util.List;

/** Representation of a predicate logical conjunction. */
public class Conjunction extends Formula {

    public Conjunction(final Formula... subterms) {
        super(false, subterms);
        if (getArity() == 0)
            throw new IllegalArgumentException("A conjunction needs at least one subterm");
    }

    public Conjunction(final Iterable<? extends Formula> subterms) {
        super(false, subterms);
        if (getArity() == 0)
            throw new IllegalArgumentException("A conjunction needs at least one subterm");
    }

    @Override
    public FormulaType getType() {
        return FormulaType.AND;
    }

    @Override
    public Conjunction clone() {
        List<Formula> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((Formula) child.clone()));
        return new Conjunction(clonedChildren);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Conjunction() {}
}
