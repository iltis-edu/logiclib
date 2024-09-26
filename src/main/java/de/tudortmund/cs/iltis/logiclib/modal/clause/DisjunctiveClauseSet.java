package de.tudortmund.cs.iltis.logiclib.modal.clause;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.clause.DisjunctiveClauseSetReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Iterator;

public class DisjunctiveClauseSet extends ClauseSet<DisjunctiveClause> {

    public static DisjunctiveClauseSet parse(String clauseSet, ModalReaderProperties props)
            throws IncorrectParseInputException {
        return new DisjunctiveClauseSetReader(props).read(clauseSet);
    }

    public DisjunctiveClauseSet(DisjunctiveClause... clauses) {
        for (DisjunctiveClause clause : clauses) this.add(clause);
    }

    public DisjunctiveClauseSet(Iterable<DisjunctiveClause> clauses) {
        for (DisjunctiveClause clause : clauses) this.add(clause);
    }

    /**
     * @throws InvalidClauseException for the first subFormula that is not valid as a clause or if
     *     {@code formula} is convertable into a DisjunctiveClauseSet
     */
    public DisjunctiveClauseSet(ModalFormula formula) {
        if (!formula.isInConjunctiveNormalform()) {
            ClauseFaultCollection faults = new ClauseFaultCollection();
            faults =
                    faults.withFault(
                            new ClauseFaultCollection.ClauseFault(
                                    ClauseFaultCollection.ClauseFaultReason
                                            .NOT_IN_CONJUNCTIVE_NORMALFORM,
                                    0,
                                    formula));
            throw new InvalidClauseException(faults);
        }

        if (formula.isConjunction()) {
            for (ModalFormula subformula : formula.getSubformulae()) {
                this.add(new DisjunctiveClause(subformula));
            }
        } else if (!formula.isTrue()) {
            this.add(new DisjunctiveClause(formula));
        }
    }

    public DisjunctiveClauseSet() {}

    @Override
    public ModalFormula toFormula() {
        return new Conjunction(Data.map(super.set, DisjunctiveClause::toFormula));
    }

    @Override
    public Iterator<DisjunctiveClause> iterator() {
        return super.iterator();
    }

    @Override
    public DisjunctiveClauseSet clone() {
        return (DisjunctiveClauseSet) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisjunctiveClauseSet)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return 715 + super.hashCode();
    }

    protected DisjunctiveClauseSet createEmptyClauseSet() {
        return new DisjunctiveClauseSet();
    }
}
