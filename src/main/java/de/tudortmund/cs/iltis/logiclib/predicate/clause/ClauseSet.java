package de.tudortmund.cs.iltis.logiclib.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;

public abstract class ClauseSet<T extends Clause> extends ListSet<T> implements Serializable {

    public abstract Formula toFormula();

    public abstract ClauseSet<T> clone();
}
