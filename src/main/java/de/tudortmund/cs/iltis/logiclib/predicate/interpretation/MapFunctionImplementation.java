package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.*;

/**
 * An implementation for a function for predicate formulae. To represent a function, a hash map is
 * used.
 *
 * @param <T> the type of the underlying universe
 */
public class MapFunctionImplementation<T> implements FunctionImplementation<T> {

    /** for serialization */
    private static final long serialVersionUID = 1L;

    public MapFunctionImplementation() {
        map = new HashMap<>();
    }

    public MapFunctionImplementation(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    public MapFunctionImplementation(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    public MapFunctionImplementation(Map<? extends Tuple<T>, ? extends T> map) {
        this.map = new HashMap<>(map);
    }

    public T put(Tuple<T> source, T target) {
        return map.put(source, target);
    }

    @Override
    public T evaluate(Tuple<T> input) {
        T output = map.get(input);
        if (output != null) return output;
        throw new IllegalArgumentException(
                "This function implementation is not defined for '" + input + "'");
    }

    @Override
    public <S> FunctionImplementation<S> translateType(Map<T, S> typeMap) {
        Set<Map.Entry<Tuple<T>, T>> entries = map.entrySet();
        MapFunctionImplementation<S> newImplementation =
                new MapFunctionImplementation<>(entries.size());
        for (Map.Entry<Tuple<T>, T> entry : entries) {
            Tuple<T> key = entry.getKey();

            List<S> newKeyEntries = new ArrayList<>(key.getSize());
            for (T tupleEntry : key) {
                if (!typeMap.containsKey(tupleEntry)) {
                    throw new IllegalArgumentException(
                            "Map does not define translation for element\""
                                    + tupleEntry.toString()
                                    + "\"");
                }
                newKeyEntries.add(typeMap.get(tupleEntry));
            }

            if (!typeMap.containsKey(entry.getValue())) {
                throw new IllegalArgumentException(
                        "Map does not define translation for element\""
                                + entry.getValue().toString()
                                + "\"");
            }

            newImplementation.put(new Tuple<>(newKeyEntries), typeMap.get(entry.getValue()));
        }
        return newImplementation;
    }

    @Override
    public String toString() {
        return "Function by map " + super.toString();
    }

    public Map<Tuple<T>, T> getClonedMap() {
        return (Map<Tuple<T>, T>) map.clone();
    }

    private HashMap<Tuple<T>, T> map;
}
