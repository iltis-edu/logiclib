package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;

public class ResolutionAlgorithm {
    public ResolutionAlgorithm(DisjunctiveClauseSet clauses, ResolutionStrategy strategy) {
        this.clauses = clauses;
        this.resolution = new Resolution(this.clauses);
        this.strategy = strategy;
    }

    public DisjunctiveClauseSet getClauses() {
        return this.resolution.getClauses();
    }

    public DisjunctiveClauseSet addNewResolvents() {
        DisjunctiveClauseSet resolvents = this.strategy.generateResolvents(this.resolution);
        return resolvents;
    }

    public void addAllResolvents() {
        DisjunctiveClauseSet resolvents;
        do {
            resolvents = addNewResolvents();
        } while (!resolvents.isEmpty());
    }

    public Resolution getResolution() {
        return this.resolution;
    }

    private DisjunctiveClauseSet clauses;
    private Resolution resolution;
    private ResolutionStrategy strategy;
}
