package de.tudortmund.cs.iltis.logiclib.predicate.base.mgu;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;

public class MGUResult implements Serializable {

    // needed for serialization
    private MGUResult() {}

    private ListSet<RelationAtom> atoms;
    private Substitution unifier;
    private Substitution lastSubstitution;

    public MGUResult(
            ListSet<RelationAtom> atoms, Substitution unifier, Substitution lastSubstitution) {

        this.atoms = atoms;
        this.unifier = unifier;
        this.lastSubstitution = lastSubstitution;
    }

    public ListSet<RelationAtom> getAtoms() {
        return this.atoms;
    }

    public Substitution getUnifier() {
        return this.unifier;
    }

    public Substitution getLastSubstitution() {
        return this.lastSubstitution;
    }

    public String toString() {

        return "L= "
                + atoms.toString()
                + ", σ= "
                + unifier.toString()
                + ", σ'= "
                + lastSubstitution.toString();
    }
}
