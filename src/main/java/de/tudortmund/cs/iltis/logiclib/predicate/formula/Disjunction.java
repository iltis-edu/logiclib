package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import java.util.ArrayList;
import java.util.List;

/** Representation of a predicate logical disjunction. */
public class Disjunction extends Formula {

    public Disjunction(final Formula... subterms) {
        super(false, subterms);
        if (getArity() == 0)
            throw new IllegalArgumentException("A disjunction needs at least one subterm");
    }

    public Disjunction(final Iterable<? extends Formula> subterms) {
        super(false, subterms);
        if (getArity() == 0)
            throw new IllegalArgumentException("A disjunction needs at least one subterm");
    }

    @Override
    public FormulaType getType() {
        return FormulaType.OR;
    }

    @Override
    public Disjunction clone() {
        List<Formula> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((Formula) child.clone()));
        return new Disjunction(clonedChildren);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private Disjunction() {}
}
