package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Valuation
        implements Interpretation, Iterator<Valuation>, Comparable<Valuation>, Serializable {
    public Valuation() {
        valuation = new TreeMap<>();
    }

    public Valuation(final Valuation other) {
        this();
        this.valuation.putAll(other.valuation);
    }

    public void define(final Variable variable, final boolean value) {
        valuation.put(variable, value);
    }

    public void define(final Set<Variable> variables, final boolean value) {
        for (Variable variable : variables) this.valuation.put(variable, value);
    }

    public void invert(final Variable variable) {
        this.define(variable, !this.map(variable));
    }

    public void invert(final Set<Variable> variables) {
        for (Variable variable : variables) this.invert(variable);
    }

    public boolean map(final Variable variable) {
        return valuation.get(variable);
    }

    public boolean mapOrFalse(final Variable variable) {
        return valuation.getOrDefault(variable, false);
    }

    public boolean isDefinedFor(Variable variable) {
        return valuation.containsKey(variable);
    }

    public boolean isDefinedFor(Collection<Variable> variables) {
        for (Variable variable : variables) if (!valuation.containsKey(variable)) return false;
        return true;
    }

    public boolean isCompatible(final ModalFormula formula) {
        return formula.isPropositional() && this.isDefinedFor(formula.getVariables());
    }

    public Set<Variable> getDomain() {
        return this.valuation.keySet();
    }

    public Set<Variable> getPreimage(Boolean value) {
        Set<Variable> preimage = new TreeSet<>();
        for (Map.Entry<Variable, Boolean> entry : this.valuation.entrySet())
            if (entry.getValue().equals(value)) preimage.add(entry.getKey());
        return preimage;
    }

    public boolean hasNext() {
        return this.valuation.containsValue(false);
    }

    public Valuation next() {
        Valuation v = this.clone();
        Variable lastFalse = null;
        for (Variable variable : v.valuation.keySet())
            if (!v.valuation.get(variable)) lastFalse = variable;
        SortedMap<Variable, Boolean> subV = v.valuation.tailMap(lastFalse);
        for (Variable variable : subV.keySet()) v.invert(variable);
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Valuation)) return false;
        return this.valuation.equals(((Valuation) o).valuation);
    }

    @Override
    public int compareTo(Valuation other) {
        Iterator<Variable> here = this.getDomain().iterator();
        Iterator<Variable> there = other.getDomain().iterator();
        while (here.hasNext() && there.hasNext()) {
            Variable vHere = here.next();
            Variable vThere = there.next();
            final int c = vHere.compareTo(vThere);
            if (c != 0) return c;
            final boolean bHere = this.map(vHere);
            final boolean bThere = this.map(vThere);
            if (!bHere && bThere) return -1;
            if (bHere && !bThere) return +1;
        }
        if (here.hasNext()) return -1;
        else if (there.hasNext()) return +1;
        else return 0;
    }

    public Valuation clone() {
        Valuation copy = new Valuation();
        for (Variable variable : this.valuation.keySet()) copy.define(variable, this.map(variable));
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("{");
        boolean first = true;
        for (Variable variable : this.getDomain()) {
            if (!first) text.append(",");
            first = false;
            text.append(variable).append("↦").append(this.map(variable) ? "1" : "0");
        }
        text.append("}");
        return text.toString();
    }

    protected TreeMap<Variable, Boolean> valuation;
}
