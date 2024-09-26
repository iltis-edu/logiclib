package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An implementation for a relation for predicate formulae. To represent a relation, a {@link
 * Predicate} is used.
 *
 * @param <T> the type of the underlying universe
 */
public class RuleRelationImplementation<T> implements RelationImplementation<T> {

    /** for serialization */
    private static final long serialVersionUID = 1L;

    private SerializablePredicate<Tuple<T>> predicate;

    public RuleRelationImplementation(SerializablePredicate<Tuple<T>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean contains(Tuple<T> elem) {
        return predicate.test(elem);
    }

    @Override
    public <S> RelationImplementation<S> translateType(Map<T, S> map) {
        Map<S, T> reversedMap =
                map.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return new RuleRelationImplementation<>(
                new SerializablePredicate<Tuple<S>>() {
                    @Override
                    public boolean test(Tuple<S> s) {
                        List<T> newKeyEntries = new ArrayList<>(s.getSize());
                        for (S tupleEntry : s) {
                            if (!reversedMap.containsKey(tupleEntry)) {
                                throw new IllegalArgumentException(
                                        "Map does not define translation for element\""
                                                + tupleEntry.toString()
                                                + "\"");
                            }
                            newKeyEntries.add(reversedMap.get(tupleEntry));
                        }

                        return predicate.test(new Tuple<>(newKeyEntries));
                    }
                });
    }

    @Override
    public String toString() {
        return "Relation defined by rule";
    }

    public int hashCode() {
        return this.predicate.hashCode();
    }

    /** For serialization only. */
    private RuleRelationImplementation() {}
}
