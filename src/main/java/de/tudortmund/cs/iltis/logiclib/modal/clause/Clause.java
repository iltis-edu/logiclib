package de.tudortmund.cs.iltis.logiclib.modal.clause;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Literal;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;

public abstract class Clause extends ListSet<Literal> implements Serializable {

    @SuppressWarnings("unused")
    protected Clause() { // Serialization
    }

    public Clause(Iterable<Literal> literals) {
        for (Literal literal : literals) this.add(literal);
    }

    public abstract ModalFormula toFormula();

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("{");
        boolean first = true;
        for (Literal literal : this) {
            if (!first) text.append(", ");
            text.append(literal.toString());
            first = false;
        }
        text.append("}");
        return text.toString();
    }

    public abstract boolean equals(Object o);

    public abstract boolean isEquivalent(Object o);

    @Override
    public int hashCode() {
        int hash = 1234;
        for (Literal literal : this) hash += 56 * literal.hashCode();
        return hash;
    }

    public abstract Clause clone();
}
