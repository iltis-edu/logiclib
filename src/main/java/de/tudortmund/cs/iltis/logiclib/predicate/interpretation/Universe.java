package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import java.io.Serializable;
import java.util.Map;

/**
 * Basic interface for a universe used in predicate logical structures. Will be extended if needed.
 *
 * @param <T> The type of the contained elements
 */
public interface Universe<T> extends Iterable<T>, Serializable {

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

    Universe<T> clone();

    boolean contains(T element);

    boolean isEmpty();

    <S> Universe<S> translateType(Map<T, S> map);
}
