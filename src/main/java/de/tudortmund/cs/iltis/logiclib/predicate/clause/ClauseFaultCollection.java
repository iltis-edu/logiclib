package de.tudortmund.cs.iltis.logiclib.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFault;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.collections.Fault;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.List;

public class ClauseFaultCollection extends FaultCollection<ClauseFaultReason, ClauseFault> {

    public ClauseFaultCollection() {
        super();
    }

    public ClauseFaultCollection(List<ClauseFault> faults) {
        super(faults);
    }

    public ClauseFaultCollection withFault(ClauseFault fault) {
        return (ClauseFaultCollection) super.withFault(fault);
    }

    public ClauseFaultCollection withFaults(ClauseFaultCollection faults) {
        return (ClauseFaultCollection) super.withFaults(faults);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClauseFaultCollection)) {
            return false;
        }
        ClauseFaultCollection faultCollection = (ClauseFaultCollection) o;
        for (ClauseFault fault : this.getFaults()) {
            if (!faultCollection.getFaults().contains(fault)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FaultCollection<ClauseFaultReason, ClauseFault> clone() {
        return new ClauseFaultCollection(this.getFaults());
    }

    public static class ClauseFault extends Fault<ClauseFaultReason> {

        private int numberInClause;
        private Formula formula;

        public ClauseFault(ClauseFaultReason reason, int position, Formula formula) {
            super(reason);
            this.numberInClause = position;
            this.formula = formula;
        }

        public int getNumberInClause() {
            return this.numberInClause;
        }

        public Formula getFormula() {
            return this.formula;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ClauseFault)) {
                return false;
            }
            ClauseFault fault = (ClauseFault) o;

            if (!this.getReason().equals(fault.getReason())) return false;
            if (!(this.getNumberInClause() == fault.getNumberInClause())) return false;
            return this.getFormula().equals(fault.getFormula());
        }

        @Override
        protected Object clone() {
            return new ClauseFault(this.getReason(), this.getNumberInClause(), this.getFormula());
        }

        /** For serialization */
        @SuppressWarnings("unused")
        private ClauseFault() {}
    }

    public enum ClauseFaultReason {
        CLAUSE_CONTAINS_INVALID_FORMULA
    }
}
