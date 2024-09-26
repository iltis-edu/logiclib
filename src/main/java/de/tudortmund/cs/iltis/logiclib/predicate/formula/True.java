package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;

/** Representation of a predicate logical true predicate. */
public class True extends Formula {

    public True() {
        super(true);
    }

    @Override
    public FormulaType getType() {
        return FormulaType.TRUE;
    }

    @Override
    public True clone() {
        return new True();
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;
}
