package de.tudortmund.cs.iltis.logiclib.predicate.base.mgu;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationFailed;

public class MGUComputationDifferentNonVariableTerms extends ComputationFailed {

    // needed for serialization
    private MGUComputationDifferentNonVariableTerms() {}

    private RelationAtom firstAtom;
    private RelationAtom secondAtom;
    private Term firstTerm;
    private Term secondTerm;

    public MGUComputationDifferentNonVariableTerms(
            RelationAtom firstAtom, RelationAtom secondAtom, Term firstTerm, Term secondTerm) {

        this.firstAtom = firstAtom;
        this.secondAtom = secondAtom;
        this.firstTerm = firstTerm;
        this.secondTerm = secondTerm;
    }

    public RelationAtom getFirstAtom() {
        return this.firstAtom;
    }

    public RelationAtom getSecondAtom() {
        return this.secondAtom;
    }

    public Term getFirstTerm() {
        return this.firstTerm;
    }

    public Term getSecondTerm() {
        return this.secondTerm;
    }

    public String toString() {

        return "failure: "
                + firstAtom.toString()
                + " and "
                + secondAtom.toString()
                + " have different non-variable terms";
    }
}
