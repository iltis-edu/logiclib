package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.io.Serializable;
import java.util.Map;

/**
 * Basic interface for implementations of functions. Will be extended if needed.
 *
 * @param <T> The type of the elements of the underlying universe
 */
public interface FunctionImplementation<T> extends Serializable {

    /**
     * Evaluates the function for the specified input.
     *
     * @param input
     * @return the function value
     * @throws IllegalArgumentException if the function is not defined for value
     */
    T evaluate(Tuple<T> input);

    default T evaluate(T... values) {
        return evaluate(new Tuple<T>(values));
    }

    <S> FunctionImplementation<S> translateType(Map<T, S> map);
}
