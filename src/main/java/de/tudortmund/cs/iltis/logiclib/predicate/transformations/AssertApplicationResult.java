package de.tudortmund.cs.iltis.logiclib.predicate.transformations;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public class AssertApplicationResult implements TransformationTest<TermOrFormula> {

    private final TermOrFormula expected;

    private final TermOrFormula input;

    private final TermOrFormula newFormula;

    private TermOrFormula actual;

    public AssertApplicationResult(TermOrFormula expected, TermOrFormula input) {
        this.expected = expected;
        this.input = input;
        this.newFormula = null;
    }

    public AssertApplicationResult(
            TermOrFormula expected, TermOrFormula input, TermOrFormula newFormula) {
        this.expected = expected;
        this.input = input;
        this.newFormula = newFormula;
    }

    @Override
    public boolean run(Transformation<TermOrFormula> transformation) {
        if (transformation instanceof UnaryPatternTransformationWithNewFormula) {
            transformation = ((UnaryPatternTransformationWithNewFormula) transformation).clone();
            ((UnaryPatternTransformationWithNewFormula) transformation).setNewTree(newFormula);
        }

        if (transformation instanceof UnaryPatternTransformationWithNewTerm) {
            transformation = ((UnaryPatternTransformationWithNewTerm) transformation).clone();
            ((UnaryPatternTransformationWithNewTerm) transformation).setNewTree(newFormula);
        }

        actual = transformation.apply(input);
        return expected.equals(actual);
    }

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
