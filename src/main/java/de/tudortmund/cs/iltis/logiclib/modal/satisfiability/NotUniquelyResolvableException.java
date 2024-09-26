package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;

public class NotUniquelyResolvableException extends RuntimeException {

    // serialization
    public NotUniquelyResolvableException() {}

    public NotUniquelyResolvableException(DisjunctiveClause clause1, DisjunctiveClause clause2) {
        super("Cannot uniquely resolve clauses " + clause1 + " and " + clause2);
    }
}
