package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.restrictions;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.graph.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LinearRestriction implements PredicateResolutionRestriction {

    // needed for serialization
    private LinearRestriction() {}

    private boolean checkSingleSteps;
    private Set<DisjunctiveClause> baseNodes;
    private ListSet<List<DisjunctiveClause>> possibleSequences;

    /**
     * @param graph should be the base graph without any resolvents
     */
    public LinearRestriction(
            Graph<DisjunctiveClause, Substitution> graph, boolean checkSingleSteps) {

        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = graph.getVertexValues();
        this.possibleSequences = new ListSet<>();
    }

    public LinearRestriction(Formula formula, boolean checkSingleSteps) {
        this.checkSingleSteps = checkSingleSteps;
        this.baseNodes = new DisjunctiveClauseSet(formula);
        this.possibleSequences = new ListSet<>();
    }

    public ListSet<List<DisjunctiveClause>> getPossibleSequences() {
        return this.possibleSequences;
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

        return isSatisfied(graph);
    }

    @Override
    public boolean isSatisfied(Graph<DisjunctiveClause, Substitution> graph) {
        this.possibleSequences = new ListSet<>();

        // tries to find all possible sequences starting from each base node
        // that satisfy the linear restriction
        for (DisjunctiveClause baseNode : baseNodes) {

            List<DisjunctiveClause> sequence =
                    calculateSequence(graph, baseNode, new ArrayList<>());

            if (!sequence.isEmpty()) {
                possibleSequences.add(sequence);
            }
        }

        return !possibleSequences.isEmpty();
    }

    /** tries to complete a given {@code sequence} with {@code lastClause} */
    private List<DisjunctiveClause> calculateSequence(
            Graph<DisjunctiveClause, Substitution> graph,
            DisjunctiveClause lastClause,
            List<DisjunctiveClause> sequence) {

        sequence.add(lastClause);

        // sequence is completed
        // check if it satisfies the linear restriction
        if (graph.getOutDegreeOf(lastClause) == 0) {
            for (DisjunctiveClause clause : graph.getVertexValues()) {
                if (!baseNodes.contains(clause) && !sequence.contains(clause)) {
                    return new ArrayList<>();
                }
            }

            return sequence;
        }

        // check if the next node would satisfy the linear restriction and
        // try to complete the sequence
        for (DisjunctiveClause clause : graph.getOutNeighborValues(lastClause)) {

            SerializablePair<DisjunctiveClause, DisjunctiveClause> parents =
                    getParents(graph, clause);

            DisjunctiveClause firstParent = parents.first();
            DisjunctiveClause secondParent = parents.second();

            if (firstParent.equals(lastClause)) {

                if (sequence.contains(secondParent) || baseNodes.contains(secondParent)) {

                    return calculateSequence(graph, clause, sequence);
                }
            } else {
                if (sequence.contains(firstParent) || baseNodes.contains(firstParent)) {

                    return calculateSequence(graph, clause, sequence);
                }
            }
        }

        return new ArrayList<>();
    }
}
