package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical false predicate. */
public class False extends Formula {

    public False() {
        super(true);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.FALSE;
    }

    @Override
    public False clone() {
        return new False();
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;
}
