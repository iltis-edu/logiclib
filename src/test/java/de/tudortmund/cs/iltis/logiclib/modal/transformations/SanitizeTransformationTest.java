package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.SanitizeTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import org.junit.Test;

/** The following test cases are supposed to cover {@link SanitizeTransformation}. */
public class SanitizeTransformationTest extends TransformationsTest {

    @Test
    public void test() throws Exception {
        Transformation transformation = new SanitizeTransformation();

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        // test disjunctions/conjunctions with 1 remaining child
        assertTransformationToTarget("(¬¬A∧(¬0∧¬B))∨(A∧1)", transformation, "(A∧¬B)∨A");

        assertTransformationToTarget("(A∧(¬(¬¬0)∧¬(¬B)))∨(A∧1)", transformation, "(A∧B)∨A");

        assertTransformationToTarget("(0∨¬¬A)∧1", transformation, "A");

        // test disjunctions/conjunctions with no remaining children
        assertTransformationToTarget(x().not().not().or(), transformation, x());

        assertTransformationToTarget(
                x().not().not().and(bottom().not(), top()), transformation, x());

        assertTransformationToTarget(
                x().not().not().and(bottom().not(), top()).and(y().not()),
                transformation,
                x().and(y().not()));
    }
}
