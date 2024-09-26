package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the universe interface in which the elements are stored in a set.
 *
 * @param <T> the type of the elements this universe contains
 */
public class SetUniverse<T> implements Universe<T> {

    private Set<T> elements;

    public SetUniverse(Set<T> elements) {
        this.elements = elements.stream().collect(Collectors.toSet());
    }

    public SetUniverse(T... elements) {
        this.elements = new ListSet<T>();
        for (T element : elements) this.addElement(element);
    }

    public SetUniverse(Iterable<T> elements) {
        this.elements = new ListSet<T>();
        for (T element : elements) this.addElement(element);
    }

    public void addElement(T element) {
        elements.add(element);
    }

    public boolean removeElement(T element) {
        return elements.remove(element);
    }

    @Override
    public boolean contains(T element) {
        return elements.contains(element);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Universe)) {
            return false;
        }
        Universe<T> otherUniverse = (Universe) other;
        boolean otherContainsThis =
                StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        this.iterator(), Spliterator.ORDERED),
                                false)
                        .allMatch(element -> otherUniverse.contains(element));
        boolean thisContainsOther =
                StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        this.iterator(), Spliterator.ORDERED),
                                false)
                        .allMatch(element -> this.contains(element));
        return otherContainsThis && thisContainsOther;
    }

    @Override
    public Universe<T> clone() {
        return new SetUniverse<T>(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return elements.spliterator();
    }

    @Override
    public String toString() {
        return "Universe of set " + elements.toString();
    }

    public Set<T> getSet() {
        return this.elements;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public <S> Universe<S> translateType(Map<T, S> map) {
        Set<S> newElements = new ListSet<>();
        for (T element : elements) {
            if (!map.containsKey(element)) {
                throw new IllegalArgumentException(
                        "Map does not define translation for element\""
                                + element.toString()
                                + "\"");
            }
            newElements.add(map.get(element));
        }
        return new SetUniverse<>(newElements);
    }

    /** For serialization only. */
    private SetUniverse() {}
}
