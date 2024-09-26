package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;

public interface ResolutionStrategy {
    public DisjunctiveClauseSet generateResolvents(Resolution resolution);
}
