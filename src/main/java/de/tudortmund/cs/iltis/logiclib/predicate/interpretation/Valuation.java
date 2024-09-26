package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.constraints.NotNull;

/**
 * Represents a variable assignment.
 *
 * @param <T> Type of elements the variable can be assigned to.
 */
public class Valuation<T extends Comparable<T>> implements Comparable<Valuation<T>>, Serializable {
    public Valuation() {
        mapping = new TreeMap<>();
    }

    public Valuation(@NotNull final Valuation<T> other) {
        this();
        this.mapping.putAll(other.mapping);
    }

    /**
     * Assign the given value to the given variable.
     *
     * @param variable the variable
     * @param value the value
     */
    public void define(final VariableSymbol variable, final T value) {
        mapping.put(variable, value);
    }

    /**
     * Assign the given value to all given variables.
     *
     * @param variables the variables
     * @param value the value
     */
    public void define(final Set<VariableSymbol> variables, final T value) {
        for (VariableSymbol variable : variables) this.mapping.put(variable, value);
    }

    /** Remove a variable that was defined and do nothing if the variable was not defined. */
    public void undefine(final VariableSymbol variable) {
        mapping.remove(variable);
    }

    /**
     * Get the assigned value of the given variable.
     *
     * @param variable the variable
     * @return the value the variable was assigned to
     */
    public T map(final VariableSymbol variable) {
        return mapping.get(variable);
    }

    /**
     * Check whether there is a value assigned to the given variable.
     *
     * @param variable the variable
     * @return true, if there is a value assigned to the given variable
     */
    public boolean isDefinedFor(VariableSymbol variable) {
        return mapping.containsKey(variable);
    }

    /**
     * Check whether for every given variable there is a value assigned to it.
     *
     * @param variables the variables
     * @return true, if all variables have a value
     */
    public boolean isDefinedFor(Collection<VariableSymbol> variables) {
        for (VariableSymbol variable : variables) if (!mapping.containsKey(variable)) return false;
        return true;
    }

    /**
     * Check whether all free variables of the given term or formula have a mapping.
     *
     * @param termOrFormula the term or formula
     * @return true, if all free variables have a mapping
     */
    public boolean isCompatible(final TermOrFormula termOrFormula) {
        return isDefinedFor(termOrFormula.getFreeVariableSymbols());
    }

    /**
     * Get all variables of this mapping.
     *
     * @return all variables of this mapping
     */
    public Set<VariableSymbol> getDomain() {
        return this.mapping.keySet();
    }

    /**
     * Get all variables that are assigned to the given value.
     *
     * @param value the value
     * @return all variables that are assigned to the given value
     */
    public Set<VariableSymbol> getPreimage(T value) {
        Set<VariableSymbol> preimage = new TreeSet<>();
        for (Map.Entry<VariableSymbol, T> entry : this.mapping.entrySet())
            if (entry.getValue().equals(value)) preimage.add(entry.getKey());
        return preimage;
    }

    /**
     * Extend this mapping with the given variable by assigning the variable with every element in
     * the given structure.
     *
     * <p>Variables that are assigned already are ignored. In any case, the returned set of
     * valuations is independent of the mapping this method is called on.
     *
     * @param variable the variable to add to the mapping
     * @param structure the structure to get the elements from which are used for new variable
     *     assignments
     * @return the set of extended valuations
     */
    public Set<Valuation<T>> getExtensions(VariableSymbol variable, Structure<T> structure) {
        if (isDefinedFor(variable)) {
            return new HashSet<>(Collections.singletonList(clone()));
        }
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(
                                structure.getUniverse().iterator(), Spliterator.ORDERED),
                        false)
                .map(
                        value -> {
                            Valuation<T> clone = clone();
                            clone.define(variable, value);
                            return clone;
                        })
                .collect(Collectors.toSet());
    }

    /**
     * Extend this mapping with the given variables by assigning every variable with every element
     * in the given structure in any possible combination. Variables that are assigned already are
     * ignored. In any case, the returned set of valuations is independent of the mapping this
     * method is called on.
     *
     * <p>Example: Suppose this mapping consist of just two variable symbols, V and X that are both
     * assigned to 0, the given structure consists of the elements 0 and 1 and the variables X, Y
     * and Z are given. Then a set of four valuations is returned: [0,0,0,0], [0,0,0,1], [0,0,1,0]
     * and [0,0,1,1] (representing valuations by [V,X,Y,Z]).
     *
     * @param variables the variables to add to the mapping
     * @param structure the structure to get the elements from which are used for new variable
     *     assignments
     * @return the set of extended valuations
     */
    public Set<Valuation<T>> getExtensions(Set<VariableSymbol> variables, Structure<T> structure) {
        Set<VariableSymbol> symbolsToExtend = new TreeSet<>(variables);
        symbolsToExtend.removeAll(getDomain());
        Set<Valuation<T>> result = new TreeSet<>(Collections.singletonList(clone()));
        for (VariableSymbol symbol : symbolsToExtend) {
            result =
                    result.stream()
                            .map(valuation -> valuation.getExtensions(symbol, structure))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Valuation)) return false;
        return this.mapping.equals(((Valuation<T>) o).mapping);
    }

    /**
     * Compares the valuations by simultaneously iterating the entries sorted by symbol. Compares
     * two entries (1) by symbol and (2) by value.
     *
     * <p>This method relies on {@link #getDomain()} returning a sorted set of assigned variables.
     */
    @Override
    public int compareTo(Valuation<T> other) {
        Iterator<VariableSymbol> here = this.getDomain().iterator();
        Iterator<VariableSymbol> there = other.getDomain().iterator();

        while (here.hasNext() && there.hasNext()) {
            VariableSymbol symbolHere = here.next();
            VariableSymbol symbolThere = there.next();
            final int c = symbolHere.compareTo(symbolThere);
            if (c != 0) {
                return c;
            }
            final T valueHere = this.map(symbolHere);
            final T valueThere = other.map(symbolThere);
            final int ec = valueHere.compareTo(valueThere);
            if (ec != 0) {
                return ec;
            }
        }
        if (here.hasNext()) {
            return +1;
        } else if (there.hasNext()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return mapping.hashCode();
    }

    public Valuation<T> clone() {
        Valuation<T> copy = new Valuation<>();
        for (VariableSymbol variable : this.mapping.keySet()) {
            copy.define(variable, this.map(variable));
        }
        return copy;
    }

    @Override
    public String toString() {
        List<String> entryStrings =
                mapping.entrySet().stream()
                        .map(e -> e.getKey() + "↦" + e.getValue().toString())
                        .collect(Collectors.toList());
        return "{" + String.join(",", entryStrings) + "}";
    }

    private TreeMap<VariableSymbol, T> mapping;

    public <S extends Comparable<S>> Valuation<S> translateType(Map<T, S> typeMap) {
        Valuation<S> newValuation = new Valuation<>();
        for (Map.Entry<VariableSymbol, T> entry : mapping.entrySet()) {
            if (!typeMap.containsKey(entry.getValue())) {
                throw new IllegalArgumentException(
                        "Map does not define translation for element\""
                                + entry.getValue().toString()
                                + "\"");
            }
            newValuation.define(entry.getKey(), typeMap.get(entry.getValue()));
        }
        return newValuation;
    }
}
