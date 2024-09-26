package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.utils.graph.*;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.DefaultHashGraph;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class Resolution implements Serializable {
    public Resolution(DisjunctiveClauseSet clauses) {
        this();
        addClauses(clauses);
    }

    public Resolution() {
        this.resolution = new DefaultHashGraph<DisjunctiveClause>();
    }

    public boolean contains(DisjunctiveClause clause) {
        return this.resolution.hasVertex(clause);
    }

    public void addClause(DisjunctiveClause clause) {
        this.resolution.addVertex(clause);
    }

    public void addClauses(DisjunctiveClauseSet clauses) {
        for (DisjunctiveClause clause : clauses) this.addClause(clause);
    }

    public boolean addResolvent(
            DisjunctiveClause resolvent, DisjunctiveClause parent1, DisjunctiveClause parent2) {
        boolean isNewClause = false;
        if (!this.resolution.hasVertex(resolvent)) {
            isNewClause = true;
            this.resolution.addVertex(resolvent);
            this.resolution.addEdge(resolvent, parent1);
            this.resolution.addEdge(resolvent, parent2);
        }
        // TODO (gg): Improve! Only replace parents if this is a shorter resolution to resolvent

        return isNewClause;
    }

    public DisjunctiveClauseSet getParents(DisjunctiveClause clause) {
        DisjunctiveClauseSet parents = new DisjunctiveClauseSet();
        for (Edge<DisjunctiveClause, EmptyEdgeLabel> edge :
                this.resolution.getOutgoingEdges(clause)) parents.add(edge.getTargetValue());
        return parents;
    }

    public void removeClause(DisjunctiveClause clause) {
        Set<DisjunctiveClause> children = this.resolution.getInNeighborValues(clause);
        for (DisjunctiveClause child : children) this.removeClause(child);
        this.resolution.removeVertex(clause);
    }

    public boolean isUnsatisfiable() {
        return this.getClauses().contains(EMPTY_CLAUSE);
    }

    public DisjunctiveClauseSet getClauses() {
        return new DisjunctiveClauseSet(this.resolution.getVertexValues());
    }

    public Graph<DisjunctiveClause, EmptyEdgeLabel> getGraph() {
        return resolution;
    }

    public List<DisjunctiveClauseSet> getLayeredClauses() {
        List<DisjunctiveClauseSet> layers = new ArrayList<DisjunctiveClauseSet>();

        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet();
        // initialize: first layer contains clauses without parents
        for (DisjunctiveClause clause : getClauses())
            if (this.resolution.getOutDegreeOf(clause) == 0) clauses.add(clause);
        layers.add(clauses);

        DisjunctiveClauseSet previousLayer = clauses;
        while (!clauses.isEmpty()) {
            clauses = new DisjunctiveClauseSet();
            for (DisjunctiveClause previousClause : previousLayer)
                for (Edge<DisjunctiveClause, EmptyEdgeLabel> edge :
                        this.resolution.getIncomingEdges(previousClause)) {
                    DisjunctiveClause resolvent = edge.getSourceValue();
                    clauses.add(resolvent);
                }
            if (!clauses.isEmpty()) layers.add(clauses);
            previousLayer = clauses;
        }

        return layers;
    }

    public Resolution clone() {
        DisjunctiveClauseSet set = new DisjunctiveClauseSet(resolution.getVertexValues());
        Resolution copy = new Resolution();
        copy.resolution = this.resolution.clone();
        return copy;
    }

    private DefaultHashGraph<DisjunctiveClause> resolution;
    private static final DisjunctiveClause EMPTY_CLAUSE = new DisjunctiveClause();
}
