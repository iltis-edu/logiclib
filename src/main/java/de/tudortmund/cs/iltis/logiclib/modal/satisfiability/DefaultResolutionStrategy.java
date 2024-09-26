package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import java.util.List;

public class DefaultResolutionStrategy implements ResolutionStrategy {
    public DisjunctiveClauseSet generateResolvents(Resolution resolution) {
        DisjunctiveClauseSet newResolvents = new DisjunctiveClauseSet();
        List<DisjunctiveClauseSet> layers = resolution.getLayeredClauses();
        DisjunctiveClauseSet latestClauses = layers.get(layers.size() - 1);

        for (DisjunctiveClauseSet clauses : layers) {
            for (DisjunctiveClause clause : clauses)
                for (DisjunctiveClause latestClause : latestClauses) {
                    try {
                        DisjunctiveClause resolvent = latestClause.resolveWith(clause);
                        final boolean newClause =
                                resolution.addResolvent(resolvent, latestClause, clause);
                        if (newClause) newResolvents.add(resolvent);
                    } catch (IrresolvableException | NotUniquelyResolvableException e) {
                        // not resolvable, everything's okay!
                    }
                }
        }
        return newResolvents;
    }
}
