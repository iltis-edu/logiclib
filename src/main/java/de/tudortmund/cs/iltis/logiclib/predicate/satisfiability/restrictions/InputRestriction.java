package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import java.util.Set;

public class InputRestriction implements PredicateResolutionRestriction {

    // needed for serialization
    private InputRestriction() {}

    private boolean checkSingleSteps;
    private Set<DisjunctiveClause> baseNodes;

    /**
     * @param graph should be the base graph without any resolvents
     */
    public InputRestriction(
            Graph<DisjunctiveClause, Substitution> graph, boolean checkSingleSteps) {

        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = graph.getVertexValues();
    }

    public InputRestriction(Formula formula, boolean checkSingleSteps) {
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

        return baseNodes.contains(firstClause) || baseNodes.contains(secondClause);
    }

    @Override
    public boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph) {
        for (DisjunctiveClause clause : graph.getVertexValues()) {
            if (!baseNodes.contains(clause)) {

                SerializablePair<DisjunctiveClause, DisjunctiveClause> parents =
                        getParents(graph, clause);

                if (!baseNodes.contains(parents.first()) && !baseNodes.contains(parents.second())) {

                    return false;
                }
            }
        }

        return true;
    }
}
