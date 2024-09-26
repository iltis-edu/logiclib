package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import java.io.Serializable;
import java.util.Iterator;

public interface PredicateResolutionRestriction extends Serializable {

    boolean shouldSingleStepsBeChecked();

    /** checks whether the last step satisfies the restriction */
    boolean isSatisfied(
            Graph<DisjunctiveClause, Substitution> graph,
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause);

    /** checks whether the whole graph satisfies the restriction */
    boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph);

    /**
     * @return null if clause has no parents
     */
    default SerializablePair<DisjunctiveClause, DisjunctiveClause> getParents(
            Graph<DisjunctiveClause, Substitution> graph, DisjunctiveClause clause) {

        Iterator<DisjunctiveClause> parentIterator = graph.getInNeighborValues(clause).iterator();

        // nodes can either have zero or two parents
        if (parentIterator.hasNext()) {
            DisjunctiveClause firstParent = parentIterator.next();

            return new SerializablePair<>(firstParent, parentIterator.next());
        }

        return null;
    }
}
