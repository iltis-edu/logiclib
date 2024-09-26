package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;

/** This can be used as a base for transformation tests. It contains some convenience methods. */
public abstract class TransformationsTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    protected True top() {
        return new True();
    }

    protected False bottom() {
        return new False();
    }

    protected Variable variable(String name) {
        return new Variable(name);
    }

    protected Variable x() {
        return variable("x");
    }

    protected Variable y() {
        return variable("y");
    }

    protected Variable z() {
        return variable("z");
    }

    protected void assertTransformationToTarget(
            String test, Transformation transformation, String target) {

        try {
            ModalFormula formula = formulaReader.read(test);
            ModalFormula targetFormula = formulaReader.read(target);
            assertTransformationToTarget(formula, transformation, targetFormula);

        } catch (IncorrectParseInputException e) {
            System.out.println(test + " is not parseable!");
        }
    }

    protected void assertTransformationToTarget(
            ModalFormula formula, Transformation transformation, ModalFormula target) {

        assertTrue(formula + " can not be transformed!", transformation.isApplicable(formula));
        ModalFormula transformed = transformation.apply(formula);

        assertTrue(
                formula + " was transformed to " + transformed + "; expected: " + target,
                transformed.equals(target));
    }

    protected void assertNotApplicable(Transformation transformation, String... formulas) {
        for (String formula : formulas) {
            assertNotApplicable(formula, transformation);
        }
    }

    protected void assertNotApplicable(Transformation transformation, ModalFormula... formulas) {
        for (ModalFormula formula : formulas) {
            assertNotApplicable(formula, transformation);
        }
    }

    protected void assertNotApplicable(String test, Transformation transformation) {
        try {
            ModalFormula formula = formulaReader.read(test);
            assertNotApplicable(formula, transformation);

        } catch (IncorrectParseInputException e) {
            System.out.println(test + " is not parseable!");
        }
    }

    protected void assertNotApplicable(ModalFormula formula, Transformation transformation) {

        assertFalse(
                formula + " can be transformed, but should not!",
                transformation.isApplicable(formula));
    }

    protected void assertInverse(String test, Transformation first, Transformation second) {
        try {
            ModalFormula formula = formulaReader.read(test);
            assertInverse(formula, first, second);

        } catch (IncorrectParseInputException e) {
            System.out.println(test + " is not parseable!");
        }
    }

    protected void assertInverse(
            ModalFormula formula, Transformation first, Transformation second) {

        assertTrue(
                formula + "can not be transformed by first transformation!",
                first.isApplicable(formula));

        ModalFormula transformed = first.apply(formula);

        assertTrue(
                transformed + "can not be transformed by second transformation!",
                second.isApplicable(transformed));

        ModalFormula result = second.apply(transformed);

        assertTrue(
                formula + " was transformed to " + transformed + " and then to " + result,
                formula.equals(result));
    }
}
