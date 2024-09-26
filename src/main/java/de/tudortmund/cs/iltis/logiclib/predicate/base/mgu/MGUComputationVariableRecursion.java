package de.tudortmund.cs.iltis.logiclib.predicate.base.mgu;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationFailed;

public class MGUComputationVariableRecursion extends ComputationFailed {

    // needed for serialization
    private MGUComputationVariableRecursion() {}

    private RelationAtom firstAtom;
    private RelationAtom secondAtom;
    private Variable variable;
    private Term variableContainingTerm;

    public MGUComputationVariableRecursion(
            RelationAtom firstAtom, RelationAtom secondAtom, Variable variable, Term term) {

        this.firstAtom = firstAtom;
        this.secondAtom = secondAtom;
        this.variable = variable;
        this.variableContainingTerm = term;
    }

    public RelationAtom getFirstAtom() {
        return this.firstAtom;
    }

    public RelationAtom getSecondAtom() {
        return this.secondAtom;
    }

    public Variable getVariable() {
        return this.variable;
    }

    public Term getVariableContainingTerm() {
        return this.variableContainingTerm;
    }

    public String toString() {

        return "failure: "
                + variable.toString()
                + " out of "
                + firstAtom.toString()
                + " is contained in "
                + variableContainingTerm.toString()
                + " out of "
                + secondAtom.toString();
    }
}
