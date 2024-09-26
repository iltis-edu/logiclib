package de.tudortmund.cs.iltis.logiclib.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause.DisjunctiveClauseSetReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisjunctiveClauseSet extends ClauseSet<DisjunctiveClause> {

    // needed for serialization
    private DisjunctiveClauseSet() {}

    /**
     * Uses {@link SignaturePolicy#TU_DORTMUND_LS1_LOGIK_POLICY} as {@link SignaturePolicy}.
     *
     * @throws IncorrectParseInputException if text can not be parsed.
     */
    public static DisjunctiveClauseSet parse(String text) throws IncorrectParseInputException {
        DisjunctiveClauseSetReader reader = new DisjunctiveClauseSetReader();
        return reader.read(text, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY);
    }

    public static DisjunctiveClauseSet parse(String text, SignatureCheckable signature)
            throws IncorrectParseInputException {

        DisjunctiveClauseSetReader reader = new DisjunctiveClauseSetReader();
        return reader.read(text, signature);
    }

    public DisjunctiveClauseSet(DisjunctiveClause... clauses) {
        this.addAll(Arrays.asList(clauses));
    }

    public DisjunctiveClauseSet(Iterable<DisjunctiveClause> clauses) {
        for (DisjunctiveClause clause : clauses) {
            this.add(clause);
        }
    }

    /**
     * @throws InvalidClauseException for the first subFormula that is not valid as a clause or if
     *     {@code formula} is convertable into a DisjunctiveClauseSet
     */
    public DisjunctiveClauseSet(Formula formula) {
        if (formula.isConjunction()) {
            for (TermOrFormula subformula : formula.getChildren()) {
                this.add(new DisjunctiveClause((Formula) subformula));
            }
        } else if (!formula.isTrue()) {
            this.add(new DisjunctiveClause(formula));
        }
    }

    @Override
    public Formula toFormula() {
        if (this.isEmpty()) return new True();

        List<Formula> subFormulas = new ArrayList<>();
        for (DisjunctiveClause clause : this) {
            subFormulas.add(clause.toFormula());
        }
        return new Conjunction(subFormulas);
    }

    @Override
    public DisjunctiveClauseSet clone() {
        DisjunctiveClauseSet newSet = new DisjunctiveClauseSet();
        for (DisjunctiveClause clause : this) {
            newSet.add(clause.clone());
        }
        return newSet;
    }
}
