package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.io.reader.relation.SetRelationReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.relation.SetRelationReaderProperties;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @param <T>
 */
public class ConcreteRelation<T> implements RelationImplementation<T> {

    private Set<Tuple<T>> set;

    public ConcreteRelation() {
        this.set = new HashSet<>();
    }

    public ConcreteRelation(Set<Tuple<T>> set) {
        this.set = set;
    }

    public static ConcreteRelation<IndexedSymbol> parse(String inputString)
            throws IncorrectParseInputException {
        SetRelationReader reader =
                new SetRelationReader(SetRelationReaderProperties.createDefault());
        return reader.read(inputString);
    }

    public static ConcreteRelation<IndexedSymbol> parse(
            String inputString,
            String openParentheses,
            String closedParentheses,
            String valueSeparator)
            throws IncorrectParseInputException {
        SetRelationReader reader =
                new SetRelationReader(
                        SetRelationReaderProperties.createDefault(
                                openParentheses, closedParentheses, valueSeparator));
        return reader.read(inputString);
    }

    public static ConcreteRelation<String> parseStringRelation(String inputString)
            throws IncorrectParseInputException {
        return new ConcreteRelation<>(
                parse(inputString).getSet().stream()
                        .map(
                                t -> {
                                    LinkedList<String> stringList = new LinkedList<>();
                                    t.forEach(element -> stringList.add(element.toString()));
                                    return new Tuple<>(stringList);
                                })
                        .collect(Collectors.toSet()));
    }

    public static ConcreteRelation<Integer> parseIntegerRelation(String inputString)
            throws IncorrectParseInputException {
        return new ConcreteRelation<>(
                parse(inputString).getSet().stream()
                        .map(
                                t -> {
                                    LinkedList<Integer> integerList = new LinkedList<>();
                                    t.forEach(
                                            element ->
                                                    integerList.add(
                                                            Integer.parseInt(element.toString())));
                                    return new Tuple<>(integerList);
                                })
                        .collect(Collectors.toSet()));
    }

    /**
     * converts the relation to a function. Every tuple should induce an assignment. The last
     * element of the tuple is the result of the assignment. All other elements of the tuple are
     * considered the input of the assignment.
     *
     * @return a function induced by the relation
     */
    public MapFunctionImplementation<T> convertToFunctionImplementation(int arity)
            throws IllegalStateException {
        MapFunctionImplementation<T> functionImplementation = new MapFunctionImplementation<>();
        for (Tuple<T> tuple : set) {
            if (arity + 1 != tuple.getSize()) {
                throw new IllegalStateException();
            }
            List<T> inputElements = new LinkedList<>();
            for (int i = 0; i < tuple.getSize() - 1; i++) {
                inputElements.add(tuple.getElementAtPosition(i));
            }
            T result = tuple.getElementAtPosition(tuple.getSize() - 1);
            functionImplementation.put(new Tuple<>(inputElements), result);
        }

        return functionImplementation;
    }

    public void add(Tuple<T> symbol) {
        set.add(symbol);
    }

    public void add(ConcreteRelation<T> otherRelation) {
        for (Tuple<T> tuple : otherRelation.set) {
            add(tuple);
        }
    }

    public Set<Tuple<T>> getSet() {
        return set;
    }

    @Override
    public boolean contains(Tuple<T> element) {
        return set.contains(element);
    }

    @Override
    public <S> RelationImplementation<S> translateType(Map<T, S> map) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        for (Tuple<T> tuple : set) {
            joiner.add(tuple.toString());
        }

        return joiner.toString();
    }
}
