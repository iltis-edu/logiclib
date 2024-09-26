package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public class AssertApplicationResult implements TransformationTest<ModalFormula> {
    private final ModalFormula expected;
    private final ModalFormula input;
    private ModalFormula actual;

    public AssertApplicationResult(ModalFormula expected, ModalFormula input) {
        this.expected = expected;
        this.input = input;
    }

    @Override
    public boolean run(Transformation<ModalFormula> transformation) {
        actual = transformation.apply(input);
        return expected.equals(actual);
    }

    @Override
    public String toString() {
        return "AssertApplicationResult on input '"
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
