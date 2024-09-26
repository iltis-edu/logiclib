package de.tudortmund.cs.iltis.logiclib.baselogic.utils;

import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import de.tudortmund.cs.iltis.utils.tree.PathCollector;
import de.tudortmund.cs.iltis.utils.tree.Tree;

public class ConstrainedPathCollector<T extends Tree<T>> extends PathCollector<T> {

    private SerializableFunction<T, Boolean> constraint;

    public ConstrainedPathCollector(SerializableFunction<T, Boolean> constraint) {
        this.constraint = constraint;
    }

    @Override
    protected boolean isSatisfying(T formula) {
        return constraint.apply(formula);
    }

    // serialization
    public ConstrainedPathCollector() {}
}
