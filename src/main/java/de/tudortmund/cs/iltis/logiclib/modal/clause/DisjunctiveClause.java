package de.tudortmund.cs.iltis.logiclib.modal.clause;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.clause.DisjunctiveClauseReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Literal;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.IrresolvableException;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.NotUniquelyResolvableException;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.*;

/** A Modal logical clause. */
public class DisjunctiveClause extends Clause implements Comparable<DisjunctiveClause> {
    public static final DisjunctiveClause EMPTY_CLAUSE = new DisjunctiveClause();

    public static DisjunctiveClause parse(String clause, ModalReaderProperties props)
            throws IncorrectParseInputException {
        return new DisjunctiveClauseReader(props).read(clause);
    }

    public static DisjunctiveClause merge(DisjunctiveClause... clauses) {
        DisjunctiveClause result = EMPTY_CLAUSE.clone();
        for (DisjunctiveClause clause : clauses) result.addAll(clause);
        return result;
    }

    // private List<DisjunctiveClause> parents = new ArrayList<>(2);
    //

    /**
     * Resolves 2 clauses
     *
     * @param clause1
     * @param clause2
     * @return resolvent, iff it is unique
     */
    public static DisjunctiveClause resolve(DisjunctiveClause clause1, DisjunctiveClause clause2) {
        List<SerializablePair<Literal, Literal>> matches = getVariableMatches(clause1, clause2);

        if (matches.size() == 0) {
            throw new IrresolvableException(clause1, clause2);
        }

        if (matches.size() > 1) {
            throw new NotUniquelyResolvableException(clause1, clause2);
        }

        return resolveOnLiteralMatch(clause1, clause2, matches.get(0));
    }

    public static Optional<DisjunctiveClause> areUniquelyResolvable(
            DisjunctiveClause clause1, DisjunctiveClause clause2) {
        try {
            return Optional.of(resolve(clause1, clause2));
        } catch (IrresolvableException | NotUniquelyResolvableException e) {
            return Optional.empty();
        }
    }

    public static boolean isResolvent(
            DisjunctiveClause resolvent, DisjunctiveClause clause1, DisjunctiveClause clause2) {
        List<SerializablePair<Literal, Literal>> matches = getVariableMatches(clause1, clause2);

        for (SerializablePair<Literal, Literal> match : matches) {
            if (resolvent.isEquivalent(resolveOnLiteralMatch(clause1, clause2, match))) {
                return true;
            }
        }

        return false;
    }

    private static List<SerializablePair<Literal, Literal>> getVariableMatches(
            DisjunctiveClause clause1, DisjunctiveClause clause2) {
        List<SerializablePair<Literal, Literal>> matches = new ArrayList<>();

        for (Literal literal1 : clause1) {
            Literal literal2 = literal1.negated();
            if (clause2.contains(literal2)) {
                matches.add(new SerializablePair<>(literal1, literal2));
            }
        }

        return matches;
    }

    private static DisjunctiveClause resolveOnLiteralMatch(
            DisjunctiveClause clause1,
            DisjunctiveClause clause2,
            SerializablePair<Literal, Literal> variableMatching) {
        DisjunctiveClause resolvent = new DisjunctiveClause();
        resolvent.addAll(clause1);
        resolvent.remove(variableMatching.first());

        DisjunctiveClause copyWithoutResolvedLiteral = clause2.clone();
        copyWithoutResolvedLiteral.remove(variableMatching.second());
        resolvent.addAll(copyWithoutResolvedLiteral);

        return resolvent;
    }

    public DisjunctiveClause() {}

    // public void setParents(DisjunctiveClause parent1, DisjunctiveClause
    // parent2) {
    // parents.clear();
    // parents.add(parent1);
    // parents.add(parent2);
    // }

    // public List<DisjunctiveClause> getParents() {
    // return parents;
    // }
    //
    // public boolean isRoot() {
    // return parents.isEmpty();
    // }
    //
    // public DisjunctiveClause(Iterable<Literal> literals) {
    // for (Literal literal : literals)
    // this.add(literal);
    // }

    /**
     * Creates a new DisjunctiveClause. If the given formula is a disjunction, the single disjuncts
     * are added; if the given formula is a (positive or negative) literal, the formula itself is
     * added.
     *
     * @throws InvalidClauseException if formula is not valid as a clause
     */
    public DisjunctiveClause(ModalFormula formula) {
        if (formula.isDisjunction()) {
            this.addAll(formula.getSubformulae());
        } else if (!formula.isFalse()) {
            this.add(formula);
        }
    }

    /**
     * Creates a new DisjunctiveClause out of the given subFormulas.
     *
     * @throws InvalidClauseException if a subFormula is neither a literal nor a negation of a
     *     literal
     */
    public DisjunctiveClause(ModalFormula... subFormulas) {
        this.addAll(Arrays.asList(subFormulas));
    }

    public DisjunctiveClause(Literal... literals) {
        super(Arrays.asList(literals));
    }

    public DisjunctiveClause(Iterable<Literal> literals) {
        super(literals);
    }

    /**
     * Adds a new formula, if it is a literal and not already contained in this clause.
     *
     * @throws InvalidClauseException if argument is not a literal
     */
    public void add(ModalFormula formula) {
        if (!Literal.isLiteral(formula)) {
            ClauseFaultCollection faultCollection = new ClauseFaultCollection();
            faultCollection =
                    faultCollection.withFault(
                            new ClauseFaultCollection.ClauseFault(
                                    ClauseFaultCollection.ClauseFaultReason
                                            .CLAUSE_CONTAINS_INVALID_FORMULA,
                                    0,
                                    formula));
            throw new InvalidClauseException(faultCollection);
        }

        this.add(new Literal(formula));
    }

    /**
     * Adds all formulas to the clause if every element is a literal.
     *
     * @throws InvalidClauseException if a formula is neither a literal nor a negation of a literal
     */
    public void addAll(Iterable<ModalFormula> formulas) {
        ClauseFaultCollection collection = isValidElseGetFaults(formulas);
        if (collection.containsAnyFault()) throw new InvalidClauseException(collection);

        formulas.forEach(formula -> add(new Literal(formula)));
    }

    private static ClauseFaultCollection isValidElseGetFaults(
            Iterable<? extends ModalFormula> subFormulas) {
        ClauseFaultCollection faults = new ClauseFaultCollection();
        int i = 0;
        for (ModalFormula subFormula : subFormulas) {
            if (!Literal.isLiteral(subFormula)) {

                faults =
                        faults.withFault(
                                new ClauseFaultCollection.ClauseFault(
                                        ClauseFaultCollection.ClauseFaultReason
                                                .CLAUSE_CONTAINS_INVALID_FORMULA,
                                        i,
                                        subFormula));
            }
            i++;
        }
        return faults;
    }

    public DisjunctiveClause resolveWith(DisjunctiveClause other) {
        return resolve(this, other);
    }

    public Optional<DisjunctiveClause> isUniquelyResolvableWith(DisjunctiveClause other) {
        return areUniquelyResolvable(this, other);
    }

    public boolean canResolveWithTo(DisjunctiveClause other, DisjunctiveClause resolvent) {
        return isResolvent(resolvent, this, other);
    }

    @Override
    public ModalFormula toFormula() {
        Iterator<Literal> it = this.iterator();
        List<ModalFormula> subformulas = new ArrayList<>();
        while (it.hasNext()) subformulas.add(it.next().toFormula());
        return new Disjunction(subformulas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisjunctiveClause)) return false;
        DisjunctiveClause other = (DisjunctiveClause) o;

        return this.compareTo(other) == 0;
    }

    /** check if another DisjunctiveClause equals this clause, by sorting first */
    public boolean isEquivalent(Object o) {
        if (o instanceof DisjunctiveClause) {
            DisjunctiveClause other = (DisjunctiveClause) o;
            ArrayList<Literal> t = new ArrayList<>(this);
            ArrayList<Literal> otherList = new ArrayList<>(other);

            Collections.sort(t);
            Collections.sort(otherList);

            return t.equals(otherList);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(DisjunctiveClause other) {
        Iterator<Literal> itHere = this.iterator();
        Iterator<Literal> itThere = other.iterator();
        while (itHere.hasNext() && itThere.hasNext()) {
            Literal litHere = itHere.next();
            Literal litThere = itThere.next();
            int litCompare = litHere.compareTo(litThere);
            if (litCompare != 0) return litCompare;
        }
        return itHere.hasNext() ? +1 : (itThere.hasNext() ? -1 : 0);
    }

    @Override
    public DisjunctiveClause clone() {
        DisjunctiveClause copy = new DisjunctiveClause();
        for (Literal literal : this) copy.add(literal.clone());
        return copy;
    }
}
