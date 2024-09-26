package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.Map;

public class EqualityRelationImplementation<T> implements RelationImplementation<T> {

    @Override
    public boolean contains(Tuple<T> tuple) {
        return tuple.getElementAtPosition(0).equals(tuple.getElementAtPosition(1));
    }

    @Override
    public <S> RelationImplementation<S> translateType(Map<T, S> map) {
        return new EqualityRelationImplementation<>();
    }

    @Override
    public String toString() {
        return "EqualityRelation";
    }
}
