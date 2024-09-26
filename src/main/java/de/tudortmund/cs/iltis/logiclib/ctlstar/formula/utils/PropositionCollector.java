package de.tudortmund.cs.iltis.logiclib.ctlstar.formula.utils;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.Proposition;
import de.tudortmund.cs.iltis.utils.tree.DefaultTraversalStrategy;
import java.util.Set;
import java.util.TreeSet;

public class PropositionCollector
        extends DefaultTraversalStrategy<CtlStarFormula, Set<Proposition>, Set<Proposition>> {

    /**
     * @param collectedValue is a set of all propositions found in this formula's
     *     subformulae/children
     * @return Set of all propositions found in subformulae/children (technically INCLUDING current
     *     node)
     */
    @Override
    protected Set<Proposition> value(Set<Proposition> collectedValue, CtlStarFormula item) {
        if (item.isProposition()) {
            assert collectedValue.isEmpty() : "a proposition has no subformulae/children!";
            collectedValue.add((Proposition) item);
        }
        return collectedValue;
    }

    // collect ensures that the proposition list returned is one-dimensional (and not a list of
    // lists of... of propositions)
    /**
     * @return Set of all propositions found in subformulae/children (EXCLUDING current node); never
     *     null!
     */
    @Override
    protected Set<Proposition> collect(
            Set<Proposition> collectedValue, Set<Proposition> nextValue) {
        if (collectedValue == null) {
            return new TreeSet<>();
        } else {
            if (nextValue != null) {
                collectedValue.addAll(nextValue);
            }
            return collectedValue;
        }
    }
}
