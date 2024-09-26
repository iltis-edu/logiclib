package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An implementation for a function for predicate formulae. To represent a function, {@link
 * Function} is used.
 *
 * @param <T> the type of the underlying universe
 */
public class RuleFunctionImplementation<T> implements FunctionImplementation<T> {

    /** for serialization */
    private static final long serialVersionUID = 1L;

    private SerializableFunction<Tuple<T>, T> function;

    public RuleFunctionImplementation(SerializableFunction<Tuple<T>, T> function) {
        this.function = function;
    }

    public T evaluate(Tuple<T> input) {
        return function.apply(input);
    }

    @Override
    public <S> FunctionImplementation<S> translateType(Map<T, S> map) {
        Map<S, T> reversedMap =
                map.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return new RuleFunctionImplementation<>(
                new SerializableFunction<Tuple<S>, S>() {
                    @Override
                    public S apply(Tuple<S> s) {
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

                        T unmappedEvaluation = function.apply(new Tuple<>(newKeyEntries));

                        if (!map.containsKey(unmappedEvaluation)) {
                            throw new IllegalArgumentException(
                                    "Map does not define translation for element\""
                                            + unmappedEvaluation.toString()
                                            + "\"");
                        }
                        return map.get(unmappedEvaluation);
                    }
                });
    }

    @Override
    public String toString() {
        return "Function defined by rule";
    }

    /** For serialization only. */
    private RuleFunctionImplementation() {}
}
