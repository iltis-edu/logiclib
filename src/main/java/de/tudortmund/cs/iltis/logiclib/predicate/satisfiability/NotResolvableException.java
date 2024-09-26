package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability;

import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;

public class NotResolvableException extends Exception {

    private DisjunctiveClause firstClause;
    private DisjunctiveClause secondClause;

    // needed for Serialization
    public NotResolvableException() {}

    public NotResolvableException(DisjunctiveClause firstClause, DisjunctiveClause secondClause) {

        this.firstClause = firstClause;
        this.secondClause = secondClause;
    }

    public DisjunctiveClause getFirstClause() {
        return this.firstClause;
    }

    public DisjunctiveClause getSecondClause() {
        return this.secondClause;
    }
}
