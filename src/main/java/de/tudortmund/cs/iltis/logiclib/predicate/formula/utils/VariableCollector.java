package de.tudortmund.cs.iltis.logiclib.predicate.formula.utils;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Quantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.tree.DefaultTraversalStrategy;
import java.util.Set;
import java.util.TreeSet;

/** Traversal strategy to collect variables. */
public class VariableCollector
        extends DefaultTraversalStrategy<TermOrFormula, Set<Variable>, Set<Variable>> {

    @Override
    protected Set<Variable> value(Set<Variable> collectedValue, TermOrFormula item) {
        if (item.isVariable()) {
            assert collectedValue.isEmpty() : "A variable has to be a leaf.";
            return value(collectedValue, (Variable) item);
        } else if (item.isQuantifier()) return value(collectedValue, (Quantifier) item);
        else return collectedValue;
    }

    public Set<Variable> value(Set<Variable> collectedValue, Quantifier item) {
        collectedValue.add(item.getVariable());
        return collectedValue;
    }

    public Set<Variable> value(Set<Variable> collectedValue, Variable item) {
        collectedValue.add(item);
        return collectedValue;
    }

    /**
     * @return The new collected value, never null.
     */
    @Override
    protected Set<Variable> collect(Set<Variable> collectedValue, Set<Variable> nextValue) {
        if (collectedValue == null) {
            if (nextValue == null) return new TreeSet<>();
            else return nextValue;
        } else {
            if (nextValue != null) collectedValue.addAll(nextValue);
            return collectedValue;
        }
    }
}
