package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.io.Serializable;
import java.util.Map;

/**
 * Basic interface for implementations of relations. Will be extended if needed.
 *
 * @param <T> The type of the elements of the underlying universe
 */
public interface RelationImplementation<T> extends Serializable {

    boolean contains(Tuple<T> element);

    <S> RelationImplementation<S> translateType(Map<T, S> map);
}
