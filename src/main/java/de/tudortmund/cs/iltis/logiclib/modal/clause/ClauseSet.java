package de.tudortmund.cs.iltis.logiclib.modal.clause;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public abstract class ClauseSet<T extends Clause> implements Iterable<T>, Serializable {
    public ClauseSet() {
        this.set = new ListSet<T>((a, b) -> a.isEquivalent(b));
    }

    public abstract ModalFormula toFormula();

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public int size() {
        return this.set.size();
    }

    public boolean contains(T clause) {
        return this.set.contains(clause);
    }

    public boolean contains(ClauseSet clauses) {
        Iterator<T> it = clauses.iterator();
        while (it.hasNext()) if (!this.contains(it.next())) return false;
        return true;
    }

    public void add(T clause) {
        if (!this.set.contains(clause)) this.set.add(clause);
    }

    public void addAll(T... clauses) {
        for (T clause : clauses) this.add(clause);
    }

    public void addAll(Iterable<T> clauses) {
        for (T clause : clauses) this.add(clause);
    }

    public boolean remove(T clause) {
        return this.set.remove(clause);
    }

    public void removeAll(T... clauses) {
        for (T clause : clauses) this.remove(clause);
    }

    public void removeAll(Iterable<T> clauses) {
        for (T clause : clauses) this.remove(clause);
    }

    public Iterator<T> iterator() {
        return this.set.iterator();
    }

    protected abstract ClauseSet<T> createEmptyClauseSet();

    public ClauseSet clone() {
        ClauseSet<T> copy = createEmptyClauseSet();
        for (T clause : this) copy.add((T) clause.clone());

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClauseSet)) return false;
        ClauseSet other = (ClauseSet) o;
        return this.contains(other) && other.contains(this);
    }

    @Override
    public int hashCode() {
        int hash = 891234;
        for (T clause : this) hash += clause.hashCode();
        return hash;
    }

    protected ListSet<T> set;
}
