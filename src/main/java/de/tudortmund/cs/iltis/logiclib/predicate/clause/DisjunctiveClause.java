package de.tudortmund.cs.iltis.logiclib.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause.DisjunctiveClauseReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** A predicate logical disjunctive clause. */
public class DisjunctiveClause extends Clause {

    // needed for serialization
    private DisjunctiveClause() {}

    /**
     * Uses {@link SignaturePolicy#TU_DORTMUND_LS1_LOGIK_POLICY} as {@link SignaturePolicy}.
     *
     * @throws IncorrectParseInputException if text can not be parsed.
     */
    public static DisjunctiveClause parse(String text) throws IncorrectParseInputException {
        DisjunctiveClauseReader reader = new DisjunctiveClauseReader();
        return reader.read(text, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY);
    }

    public static DisjunctiveClause parse(String text, SignatureCheckable signature)
            throws IncorrectParseInputException {

        DisjunctiveClauseReader reader = new DisjunctiveClauseReader();
        return reader.read(text, signature);
    }

    public static DisjunctiveClause merge(DisjunctiveClause... clauses) {
        DisjunctiveClause newClause = new DisjunctiveClause();
        for (DisjunctiveClause clause : clauses) {
            newClause.addAll(clause);
        }
        return newClause;
    }

    /**
     * Creates a new DisjunctiveClause. If the given formula is a disjunction, the single disjuncts
     * are added; if the given formula is a (positive or negative) relation atom, the formula itself
     * is added.
     *
     * @throws InvalidClauseException if formula is not valid as a clause
     */
    public DisjunctiveClause(Formula formula) {
        if (formula.isDisjunction()) {
            // Cast all children to Formula
            List<Formula> castedFormulas =
                    formula.getChildren().stream()
                            .map(child -> (Formula) child)
                            .collect(Collectors.toList());
            this.addAll(castedFormulas);
        } else if (!formula.isFalse()) {
            this.add(formula);
        }
    }

    /**
     * Creates a new DisjunctiveClause out of the given subFormulas.
     *
     * @throws InvalidClauseException if a subFormula is neither a relation nor a negation of a
     *     relation
     */
    public DisjunctiveClause(Formula... subFormulas) {
        this(Arrays.asList(subFormulas));
    }

    /**
     * Creates a new DisjunctiveClause out of the given subFormulas.
     *
     * @throws InvalidClauseException if a subFormula is neither a relation nor a negation of a
     *     relation
     */
    public DisjunctiveClause(Iterable<Formula> subFormulas) {
        super(subFormulas);
    }

    public Formula toFormula() {
        if (this.isEmpty()) return new False();
        else return new Disjunction(this.toList());
    }

    /**
     * @return Create a list set of relation atoms, dismissing possible negations.
     */
    public ListSet<RelationAtom> getAtoms() {
        ListSet<RelationAtom> atoms = new ListSet<>();
        for (Formula formula : this) {
            if (formula.isNegation()) atoms.add((RelationAtom) formula.getChild(0));
            else atoms.add((RelationAtom) formula);
        }
        return atoms;
    }

    public DisjunctiveClause clone() {
        DisjunctiveClause newClause = new DisjunctiveClause();
        for (Formula formula : this) {
            newClause.add(formula.clone());
        }
        return newClause;
    }
}
