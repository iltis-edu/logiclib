package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.*;

/**
 * An implementation for a relation for predicate formulae. To represent a relation, a hash set is
 * used.
 *
 * @param <T> the type of the underlying universe
 */
public class SetRelationImplementation<T> implements RelationImplementation<T> {

    /** for serialization */
    private static final long serialVersionUID = 1L;

    public SetRelationImplementation() {
        set = new HashSet<>();
    }

    public SetRelationImplementation(int initialCapacity) {
        set = new HashSet<>(initialCapacity);
    }

    public SetRelationImplementation(int initialCapacity, float loadFactor) {
        set = new HashSet<>(initialCapacity, loadFactor);
    }

    public SetRelationImplementation(Collection<? extends Tuple<T>> set) {
        this.set = new HashSet<>(set);
    }

    public boolean add(Tuple<T> element) {
        return set.add(element);
    }

    @Override
    public boolean contains(Tuple<T> element) {
        return set.contains(element);
    }

    @Override
    public <S> RelationImplementation<S> translateType(Map<T, S> map) {
        SetRelationImplementation<S> newImplementation =
                new SetRelationImplementation<>(set.size());
        for (Tuple<T> entry : set) {
            List<S> newTupleEntries = new ArrayList<>(entry.getSize());
            for (T tupleEntry : entry) {
                if (!map.containsKey(tupleEntry)) {
                    throw new IllegalArgumentException(
                            "Map does not define translation for element\""
                                    + tupleEntry.toString()
                                    + "\"");
                }
                newTupleEntries.add(map.get(tupleEntry));
            }
            newImplementation.add(new Tuple<>(newTupleEntries));
        }
        return newImplementation;
    }

    @Override
    public String toString() {
        return "Relation of set " + set.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RelationImplementation)) return false;

        SetRelationImplementation<T> other = (SetRelationImplementation<T>) obj;
        return this.set.equals(other.set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    public Set<Tuple<T>> getClonedSet() {
        return (Set<Tuple<T>>) set.clone();
    }

    private HashSet<Tuple<T>> set;
}
