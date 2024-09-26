package de.tudortmund.cs.iltis.logiclib.predicate.base.mgu;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationFailed;

public class MGUComputationDifferentRelationSymbols extends ComputationFailed {

    // needed for serialization
    private MGUComputationDifferentRelationSymbols() {}

    private RelationAtom firstAtom;
    private RelationAtom secondAtom;

    public MGUComputationDifferentRelationSymbols(RelationAtom firstAtom, RelationAtom secondAtom) {

        this.firstAtom = firstAtom;
        this.secondAtom = secondAtom;
    }

    public RelationAtom getFirstAtom() {
        return this.firstAtom;
    }

    public RelationAtom getSecondAtom() {
        return this.secondAtom;
    }

    public String toString() {

        return "failure: "
                + this.firstAtom.toString()
                + " and "
                + this.secondAtom.toString()
                + " have different relation symbols";
    }
}
