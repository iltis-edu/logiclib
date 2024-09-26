package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.Map;

public class ConstantFunctionImplementation<T> implements FunctionImplementation<T> {

    private T element;

    public ConstantFunctionImplementation(T element) {
        this.element = element;
    }

    @Override
    public T evaluate(Tuple<T> input) {
        return element;
    }

    @Override
    public String toString() {
        return "Constant function (with value " + element.toString() + ")";
    }

    public T getElement() {
        return element;
    }

    @Override
    public <S> FunctionImplementation<S> translateType(Map<T, S> map) {
        if (!map.containsKey(element)) {
            throw new IllegalArgumentException(
                    "Map does not define translation for element\"" + element.toString() + "\"");
        }
        return new ConstantFunctionImplementation<>(map.get(element));
    }

    /** For serialization only. */
    private ConstantFunctionImplementation() {}
}
