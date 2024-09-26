package de.tudortmund.cs.iltis.logiclib.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFault;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;
import java.util.*;

/** A Predicate logical clause. */
public abstract class Clause extends ListSet<Formula> implements Serializable {

    // needed for serialization
    protected Clause() {}

    /**
     * Creates a new Clause out of the given subFormulas
     *
     * @throws InvalidClauseException if a subFormula is neither a relation nor a negation of a
     *     relation
     */
    public Clause(Iterable<Formula> subFormulas) {
        List<Formula> list = new LinkedList<>();

        for (Formula subFormula : subFormulas) {
            list.add(subFormula);
        }

        this.addAll(list);
    }

    /**
     * Adds all elements to the clause if every element is a clause itself.
     *
     * @throws InvalidClauseException if an element is neither a relation nor a negation of a
     *     relation
     */
    @Override
    public void add(Formula... newElements) {
        addAll(Arrays.asList(newElements));
    }

    /**
     * Adds all elements to the clause if every element is a clause itself.
     *
     * @throws InvalidClauseException if an element is neither a relation nor a negation of a
     *     relation
     */
    @Override
    public boolean addAll(Collection<? extends Formula> newElements) throws InvalidClauseException {
        ClauseFaultCollection collection = isValidElseGetFaults(newElements);
        if (collection.containsAnyFault()) throw new InvalidClauseException(collection);

        return super.addAll(newElements);
    }

    private static ClauseFaultCollection isValidElseGetFaults(
            Iterable<? extends Formula> subFormulas) {
        ClauseFaultCollection faults = new ClauseFaultCollection();
        int i = 0;
        for (Formula subFormula : subFormulas) {
            if (!isClause(subFormula)) {

                faults =
                        faults.withFault(
                                new ClauseFault(
                                        ClauseFaultReason.CLAUSE_CONTAINS_INVALID_FORMULA,
                                        i,
                                        subFormula));
            }
            i++;
        }
        return faults;
    }

    public abstract Formula toFormula();

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("{");
        boolean first = true;
        for (Formula formula : this) {
            if (!first) {
                text.append(", ");
            }
            text.append(formula.toString());
            first = false;
        }
        text.append("}");
        return text.toString();
    }

    public abstract Clause clone();

    protected static boolean isClause(TermOrFormula formula) {
        return formula.isRelation() || (formula.isNegation() && formula.getChild(0).isRelation());
    }

    /**
     * Adds a new element, if it is a clause and not already contained in this clause.
     *
     * @throws InvalidClauseException if argument is not a clause
     */
    @Override
    protected boolean addSingleElement(Formula newElement) {
        if (!isClause(newElement)) {
            ClauseFaultCollection faultCollection = new ClauseFaultCollection();
            faultCollection =
                    faultCollection.withFault(
                            new ClauseFault(
                                    ClauseFaultReason.CLAUSE_CONTAINS_INVALID_FORMULA,
                                    0,
                                    newElement));
            throw new InvalidClauseException(faultCollection);
        }
        return super.addSingleElement(newElement);
    }
}
