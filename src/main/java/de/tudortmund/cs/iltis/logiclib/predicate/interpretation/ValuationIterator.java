package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.collections.TupleIterator;
import java.util.*;
import javax.validation.constraints.NotNull;

/**
 * Iterator over every possible mapping with the given variable symbols and elements in the given
 * structure.
 *
 * <p>Example: If given the structure consists of the elements 0 and 1 and there are given the
 * variable symbols X and Y then this iterator iterates over four valuations each of them
 * representing one of the possible combinations of 0 and 1, i.e. 00, 01, 10 and 11.
 *
 * @param <T> type of elements of the structure
 */
public class ValuationIterator<T extends Comparable<T>> implements Iterator<Valuation<T>> {

    private VariableSymbol[] symbols;
    private TupleIterator<T> tupleIterator;

    public ValuationIterator(
            @NotNull Structure<T> structure, @NotNull Collection<VariableSymbol> symbols) {
        this(structure, symbols.toArray(new VariableSymbol[symbols.size()]));
    }

    public ValuationIterator(@NotNull Structure<T> structure, @NotNull VariableSymbol[] symbols) {
        this.tupleIterator = new TupleIterator<>(structure.getUniverse(), symbols.length);
        this.symbols = symbols;
    }

    @Override
    public boolean hasNext() {
        return tupleIterator.hasNext();
    }

    @Override
    public Valuation<T> next() {
        Tuple<T> nextTuple = tupleIterator.next();
        Valuation<T> nextValuation = new Valuation<>();
        for (int i = 0; i < symbols.length; i++) {
            nextValuation.define(symbols[i], nextTuple.getElementAtPosition(i));
        }
        return nextValuation;
    }

    public void reset() {
        tupleIterator.reset();
    }
}
