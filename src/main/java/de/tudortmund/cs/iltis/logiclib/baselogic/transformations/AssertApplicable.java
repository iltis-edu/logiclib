package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public class AssertApplicable<T extends Tree<T>> implements TransformationTest<T> {

    private final T input;

    private final boolean expected;

    private boolean actual;

    public AssertApplicable(T input, boolean expected) {
        this.input = input;
        this.expected = expected;
    }

    public AssertApplicable(T input) {
        this(input, true);
    }

    @Override
    public boolean run(Transformation<T> transformation) {
        actual = transformation.isApplicable(input);
        return actual == expected;
    }

    @Override
    public String toString() {
        return "AssertApplicable on input '"
                + input
                + "' "
                + "expected '"
                + expected
                + "' "
                + "got '"
                + actual
                + "'";
    }
}
