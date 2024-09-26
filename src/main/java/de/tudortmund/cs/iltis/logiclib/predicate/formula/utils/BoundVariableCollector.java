package de.tudortmund.cs.iltis.logiclib.predicate.formula.utils;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import java.util.Set;

/** Traversal strategy to collect <b>bound</b> variables. */
public class BoundVariableCollector extends VariableCollector {

    @Override
    public Set<Variable> value(Set<Variable> collectedValue, Variable item) {
        // do not add single variables -- only the variables of quantifiers
        return collectedValue;
    }
}
