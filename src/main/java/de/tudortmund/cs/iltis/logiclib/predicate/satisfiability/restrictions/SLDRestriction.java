package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.HashGraph;
import java.util.List;

public class SLDRestriction implements PredicateResolutionRestriction {

    // needed for serialization
    private SLDRestriction() {}

    private boolean checkSingleSteps;
    private Graph<DisjunctiveClause, Substitution> graph;

    /**
     * @param graph should be the base graph without any resolvents
     */
    public SLDRestriction(Graph<DisjunctiveClause, Substitution> graph, boolean checkSingleSteps) {

        this.checkSingleSteps = checkSingleSteps;
        this.graph = graph;
    }

    public SLDRestriction(Formula formula, boolean checkSingleSteps) {
        this.checkSingleSteps = checkSingleSteps;
        DisjunctiveClauseSet clauseSet = new DisjunctiveClauseSet(formula);
        this.graph = new HashGraph<>();
        this.graph.addVertices(clauseSet);
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

        LinearRestriction linearRestriction =
                new LinearRestriction(this.graph, this.checkSingleSteps);

        InputRestriction inputRestriction = new InputRestriction(this.graph, this.checkSingleSteps);

        if (!linearRestriction.isSatisfied(graph, firstClause, secondClause)
                || !inputRestriction.isSatisfied(graph, firstClause, secondClause)) {

            return false;
        }

        return containsTargetClause(linearRestriction);
    }

    @Override
    public boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph) {

        LinearRestriction linearRestriction =
                new LinearRestriction(this.graph, this.checkSingleSteps);

        InputRestriction inputRestriction = new InputRestriction(this.graph, this.checkSingleSteps);

        if (!linearRestriction.isSatisfied(graph) || !inputRestriction.isSatisfied(graph)) {

            return false;
        }

        return containsTargetClause(linearRestriction);
    }

    private boolean containsTargetClause(LinearRestriction linearRestriction) {
        for (List<DisjunctiveClause> sequence : linearRestriction.getPossibleSequences()) {

            if (containsOnlyNegativeLiterals(sequence.get(0))) {
                return true;
            }
        }

        return false;
    }

    private boolean containsOnlyNegativeLiterals(DisjunctiveClause clause) {

        for (Formula subformula : clause) {
            if (!subformula.isNegation()) {
                return false;
            }
        }

        return true;
    }
}
