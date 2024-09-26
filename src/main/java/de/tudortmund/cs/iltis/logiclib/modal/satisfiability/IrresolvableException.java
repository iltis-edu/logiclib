package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;

public class IrresolvableException extends RuntimeException {

    public IrresolvableException(DisjunctiveClause clause1, DisjunctiveClause clause2) {
        super("Cannot resolve clauses " + clause1 + " and " + clause2);
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private IrresolvableException() {}
}
