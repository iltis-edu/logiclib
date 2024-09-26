package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public class AssertInverse<T extends Tree<T>> implements TransformationTest<T> {

    private final Transformation<T> other;

    private final T input;

    private T actual;

    public AssertInverse(Transformation<T> other, T input) {
        this.other = other;
        this.input = input;
    }

    @Override
    public boolean run(Transformation<T> transformation) {
        if (!other.isApplicable(input)) {
            actual = null;
            return false;
        }

        T firstTransformed = other.apply(input);

        if (!transformation.isApplicable(firstTransformed)) {
            actual = null;
            return false;
        }

        actual = transformation.apply(firstTransformed);

        return input.equals(actual);
    }
}
