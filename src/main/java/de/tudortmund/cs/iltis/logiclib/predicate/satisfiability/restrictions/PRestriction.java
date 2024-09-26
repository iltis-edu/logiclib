package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import java.util.Set;

public class PRestriction implements PredicateResolutionRestriction {

    // needed for serialization
    private PRestriction() {}

    private boolean checkSingleSteps;
    private Set<DisjunctiveClause> baseNodes;

    /**
     * @param graph should be the base graph without any resolvents
     */
    public PRestriction(Graph<DisjunctiveClause, Substitution> graph, boolean checkSingleSteps) {

        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = graph.getVertexValues();
    }

    public PRestriction(Formula formula, boolean checkSingleSteps) {
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

        return !containsNegativeLiterals(firstClause) || containsNegativeLiterals(secondClause);
    }

    @Override
    public boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph) {
        for (DisjunctiveClause clause : graph.getVertexValues()) {
            if (!baseNodes.contains(clause)) {

                SerializablePair<DisjunctiveClause, DisjunctiveClause> parents =
                        getParents(graph, clause);

                if (containsNegativeLiterals(parents.first())
                        && containsNegativeLiterals(parents.second())) {

                    return false;
                }
            }
        }

        return true;
    }

    private boolean containsNegativeLiterals(DisjunctiveClause first) {
        for (Formula subformula : first) {
            if (subformula.isNegation()) {
                return true;
            }
        }

        return false;
    }
}
