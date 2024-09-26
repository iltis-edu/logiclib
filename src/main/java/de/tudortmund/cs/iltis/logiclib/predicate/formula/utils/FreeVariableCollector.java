package de.tudortmund.cs.iltis.logiclib.predicate.formula.utils;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Quantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import java.util.Set;

/** Traversal strategy to collect <b>free</b> variables. */
public class FreeVariableCollector extends VariableCollector {

    @Override
    public Set<Variable> value(Set<Variable> collectedValue, Quantifier item) {
        collectedValue.remove(item.getVariable());
        return collectedValue;
    }
}
