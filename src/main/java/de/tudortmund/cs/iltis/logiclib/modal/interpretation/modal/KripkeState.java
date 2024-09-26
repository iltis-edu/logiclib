package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Literal;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.writer.collections.SetWriter;
import java.io.Serializable;

public class KripkeState implements Serializable, Comparable<KripkeState> {
    private IndexedSymbol name;
    private Valuation valuation;

    // serialization
    @SuppressWarnings("unused")
    private KripkeState() {}

    public KripkeState(String name) {
        this(name, new Valuation());
    }

    public KripkeState(String name, Valuation valuation) {
        this(new IndexedSymbol(name), valuation);
    }

    public KripkeState(IndexedSymbol name) {
        this(name, new Valuation());
    }

    public KripkeState(IndexedSymbol name, Valuation valuation) {
        this.name = name;
        this.valuation = valuation;
    }

    public IndexedSymbol getName() {
        return this.name;
    }

    public void setName(IndexedSymbol name) {
        this.name = name;
    }

    public Valuation getValuation() {
        return this.valuation;
    }

    public boolean contradicts(Literal lit) {
        Variable var = lit.getVariable();
        return this.valuation.isDefinedFor(var) && lit.isPositive() != this.valuation.map(var);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof KripkeState)) return false;
        KripkeState other = (KripkeState) o;
        return this.name.equals(other.getName());
    }

    @Override
    public int compareTo(KripkeState other) {
        return this.name.compareTo(other.name);
    }

    public KripkeState clone() {
        return new KripkeState(this.name.clone(), this.valuation.clone());
    }

    @Override
    public int hashCode() {
        return 3459 + 234 * this.name.hashCode();
    }

    @Override
    public String toString() {
        ListSet<Variable> propositions = new ListSet<Variable>(this.valuation.getDomain());
        SetWriter<Variable> writer = new SetWriter<>();
        propositions = Data.filter(propositions, var -> this.valuation.map(var));
        return "(" + this.name.toString() + "," + writer.write(propositions) + ")";
    }

    public String writeName() {
        return this.getName().toString();
    }
}
