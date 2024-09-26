package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import java.util.Set;

public class UnitRestriction implements PredicateResolutionRestriction {

    // needed for serialization
    private UnitRestriction() {}

    private boolean checkSingleSteps;
    private Set<DisjunctiveClause> baseNodes;

    /**
     * @param graph should be the base graph without any resolvents
     */
    public UnitRestriction(Graph<DisjunctiveClause, Substitution> graph, boolean checkSingleSteps) {

        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = graph.getVertexValues();
    }

    public UnitRestriction(Formula formula, boolean checkSingleSteps) {
        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = new DisjunctiveClauseSet(formula);
    }

    @Override
    public boolean shouldSingleStepsBeChecked() {
        return this.checkSingleSteps;
    }

    @Override
    public boolean isSatisfied(
            Graph<DisjunctiveClause, Substitution> graph,
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause) {

        return firstClause.size() == 1 || secondClause.size() == 1;
    }

    @Override
    public boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph) {
        for (DisjunctiveClause clause : graph.getVertexValues()) {
            if (!baseNodes.contains(clause)) {

                SerializablePair<DisjunctiveClause, DisjunctiveClause> parents =
                        getParents(graph, clause);

                if (parents.first().size() != 1 && parents.second().size() != 1) {

                    return false;
                }
            }
        }

        return true;
    }
}
