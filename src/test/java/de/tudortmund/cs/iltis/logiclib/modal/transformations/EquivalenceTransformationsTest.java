package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.BuggyTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.EquivalenceTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.TransformationWithNewFormula;
import org.junit.Test;

/** The following test cases are supposed to cover {@link EquivalenceTransformations}. */
public class EquivalenceTransformationsTest extends TransformationsTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void test() throws Exception {

        // REPLACE_IMPLICATION_ALL
        Transformation transformation = EquivalenceTransformations.REPLACE_IMPLICATION_ALL;

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

        assertTransformationToTarget(x().implies(y()), transformation, x().not().or(y()));

        assertTransformationToTarget(
                x().not().and(y(), x().implies(y())),
                transformation,
                x().not().and(y(), x().not().or(y())));

        assertTransformationToTarget(
                x().implies(y()).or(x().not(), y()),
                transformation,
                x().not().or(y()).or(x().not(), y()));

        // REPLACE_IMPLICATION
        transformation = EquivalenceTransformations.REPLACE_IMPLICATION;

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

        assertTransformationToTarget(x().implies(y()), transformation, x().not().or(y()));

        // CREATE_IMPLICATION
        transformation = EquivalenceTransformations.CREATE_IMPLICATION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().or(y()), transformation, x().implies(y()));

        // CONTRAPOSITION
        transformation = EquivalenceTransformations.CONTRAPOSITION;

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

        assertTransformationToTarget(
                x().implies(y()), transformation, y().not().implies(x().not()));

        // REPLACE_EQUIVALENCE
        transformation = EquivalenceTransformations.REPLACE_EQUIVALENCE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().equivalent(y()), transformation, (x().and(y())).or(x().not().and(y().not())));

        // REPLACE_EQUIVALENCE_ALTERNATIVE
        transformation = EquivalenceTransformations.REPLACE_EQUIVALENCE_ALTERNATIVE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().equivalent(y()), transformation, (x().implies(y())).and(y().implies(x())));

        // CREATE_EQUIVALENCE
        transformation = EquivalenceTransformations.CREATE_EQUIVALENCE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                (x().and(y())).or(x().not().and(y().not())), transformation, x().equivalent(y()));

        assertTransformationToTarget(
                (x().implies(y())).and(y().implies(x())), transformation, x().equivalent(y()));

        // SWAP_NEGATION_IN_EQUIVALENCE
        transformation = EquivalenceTransformations.SWAP_NEGATION_IN_EQUIVALENCE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().equivalent(y().not()), transformation, x().not().equivalent(y()));

        assertTransformationToTarget(
                x().not().equivalent(y()), transformation, x().equivalent(y().not()));

        // MOVE_NEGATION_INTO_EQUIVALENCE
        transformation = EquivalenceTransformations.MOVE_NEGATION_INTO_EQUIVALENCE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().equivalent(y()).not(), transformation, x().not().equivalent(y()));

        assertTransformationToTarget(
                x().not().equivalent(y()), transformation, x().equivalent(y()).not());

        // ADD_DOUBLE_NEGATION
        transformation = EquivalenceTransformations.ADD_DOUBLE_NEGATION;

        assertTransformationToTarget(top(), transformation, top().not().not());

        assertTransformationToTarget(bottom(), transformation, bottom().not().not());

        assertTransformationToTarget(x(), transformation, x().not().not());

        assertTransformationToTarget(y(), transformation, y().not().not());

        assertTransformationToTarget(x().not(), transformation, x().not().not().not());

        assertTransformationToTarget(x().or(y()), transformation, x().or(y()).not().not());

        assertTransformationToTarget(x().and(y()), transformation, x().and(y()).not().not());

        assertTransformationToTarget(
                x().implies(y()), transformation, x().implies(y()).not().not());

        assertTransformationToTarget(
                x().equivalent(y()), transformation, x().equivalent(y()).not().not());

        assertTransformationToTarget(x().diamond(), transformation, x().diamond().not().not());

        assertTransformationToTarget(x().box(), transformation, x().box().not().not());

        // REMOVE_DOUBLE_NEGATION
        transformation = EquivalenceTransformations.REMOVE_DOUBLE_NEGATION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(top().not().not(), transformation, top());

        assertTransformationToTarget(bottom().not().not(), transformation, bottom());

        assertTransformationToTarget(x().not().not(), transformation, x());

        assertTransformationToTarget(y().not().not(), transformation, y());

        assertTransformationToTarget(x().not().not().not(), transformation, x().not());

        assertTransformationToTarget(x().or(y()).not().not(), transformation, x().or(y()));

        assertTransformationToTarget(x().and(y()).not().not(), transformation, x().and(y()));

        assertTransformationToTarget(
                x().implies(y()).not().not(), transformation, x().implies(y()));

        assertTransformationToTarget(
                x().equivalent(y()).not().not(), transformation, x().equivalent(y()));

        assertTransformationToTarget(x().diamond().not().not(), transformation, x().diamond());

        assertTransformationToTarget(x().box().not().not(), transformation, x().box());

        // PUSH_NEGATION_OVER_BOX
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_BOX;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().box().not(), transformation, x().not().diamond());

        // PULL_NEGATION_OVER_BOX
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_BOX;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().diamond(), transformation, x().box().not());

        // PUSH_NEGATION_OVER_DIAMOND
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_DIAMOND;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().diamond().not(), transformation, x().not().box());

        // PULL_NEGATION_OVER_DIAMOND
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_DIAMOND;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().box(), transformation, x().diamond().not());

        // PUSH_DUALITY
        transformation = EquivalenceTransformations.PUSH_DUALITY;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().box().not(), transformation, x().not().diamond());

        assertTransformationToTarget(x().diamond().not(), transformation, x().not().box());

        // PULL_DUALITY
        transformation = EquivalenceTransformations.PULL_DUALITY;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().diamond(), transformation, x().box().not());

        assertTransformationToTarget(x().not().box(), transformation, x().diamond().not());

        // PUSH_NEGATION_OVER_CONJUNCTION
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().and(y()).not(), transformation, x().not().or(y().not()));

        assertTransformationToTarget(
                x().and(y(), x()).not(), transformation, x().not().or(y().not(), x().not()));

        // PULL_NEGATION_OVER_CONJUNCTION
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().and(y().not()), transformation, x().or(y()).not());

        assertTransformationToTarget(
                x().not().and(y().not(), x().not()), transformation, x().or(y(), x()).not());

        // PUSH_NEGATION_OVER_DISJUNCTION
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_DISJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().or(y()).not(), transformation, x().not().and(y().not()));

        assertTransformationToTarget(
                x().or(y(), x()).not(), transformation, x().not().and(y().not(), x().not()));

        // PULL_NEGATION_OVER_DISJUNCTION
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_DISJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().or(y().not()), transformation, x().and(y()).not());

        assertTransformationToTarget(
                x().not().or(y().not(), x().not()), transformation, x().and(y(), x()).not());

        // PUSH_DEMORGAN
        transformation = EquivalenceTransformations.PUSH_DEMORGAN;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().and(y()).not(), transformation, x().not().or(y().not()));

        assertTransformationToTarget(
                x().and(y(), x()).not(), transformation, x().not().or(y().not(), x().not()));

        assertTransformationToTarget(x().or(y()).not(), transformation, x().not().and(y().not()));

        assertTransformationToTarget(
                x().or(y(), x()).not(), transformation, x().not().and(y().not(), x().not()));

        // PULL_DEMORGAN
        transformation = EquivalenceTransformations.PULL_DEMORGAN;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().and(y().not()), transformation, x().or(y()).not());

        assertTransformationToTarget(
                x().not().and(y().not(), x().not()), transformation, x().or(y(), x()).not());

        assertTransformationToTarget(x().not().or(y().not()), transformation, x().and(y()).not());

        assertTransformationToTarget(
                x().not().or(y().not(), x().not()), transformation, x().and(y(), x()).not());

        // PUSH_NEGATION_OVER_DISJUNCTION_SHORTCUT
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_DISJUNCTION_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().or(y()), transformation, x().not().and(y().not()).not());

        assertTransformationToTarget(
                x().or(y(), x()), transformation, x().not().and(y().not(), x().not()).not());

        // PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().or(y().not()).not(), transformation, x().and(y()));

        assertTransformationToTarget(
                x().not().or(y().not(), x().not()).not(), transformation, x().and(y(), x()));

        // PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT
        transformation = EquivalenceTransformations.PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().and(y()), transformation, x().not().or(y().not()).not());

        assertTransformationToTarget(
                x().and(y(), x()), transformation, x().not().or(y().not(), x().not()).not());

        // PULL_NEGATION_OVER_CONJUNCTION_SHORTCUT
        transformation = EquivalenceTransformations.PULL_NEGATION_OVER_CONJUNCTION_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().and(y().not()).not(), transformation, x().or(y()));

        assertTransformationToTarget(
                x().not().and(y().not(), x().not()).not(), transformation, x().or(y(), x()));

        // PUSH_DEMORGAN_SHORTCUT
        transformation = EquivalenceTransformations.PUSH_DEMORGAN_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().or(y()), transformation, x().not().and(y().not()).not());

        assertTransformationToTarget(
                x().or(y(), x()), transformation, x().not().and(y().not(), x().not()).not());

        assertTransformationToTarget(x().and(y()), transformation, x().not().or(y().not()).not());

        assertTransformationToTarget(
                x().and(y(), x()), transformation, x().not().or(y().not(), x().not()).not());

        // PULL_DEMORGAN_SHORTCUT
        transformation = EquivalenceTransformations.PULL_DEMORGAN_SHORTCUT;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().not().or(y().not()).not(), transformation, x().and(y()));

        assertTransformationToTarget(
                x().not().or(y().not(), x().not()).not(), transformation, x().and(y(), x()));

        assertTransformationToTarget(x().not().and(y().not()).not(), transformation, x().or(y()));

        assertTransformationToTarget(
                x().not().and(y().not(), x().not()).not(), transformation, x().or(y(), x()));

        // COMMUTE_CONJUNCTION
        transformation = EquivalenceTransformations.COMMUTE_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box(),
                x().and(y(), x()));

        assertTransformationToTarget(x().and(y()), transformation, y().and(x()));

        assertTransformationToTarget(x().and(y().and(x())), transformation, y().and(x()).and(x()));

        // COMMUTE_DISJUNCTION
        transformation = EquivalenceTransformations.COMMUTE_DISJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box(),
                x().or(y(), x()));

        assertTransformationToTarget(x().or(y()), transformation, y().or(x()));

        assertTransformationToTarget(x().or(y().or(x())), transformation, y().or(x()).or(x()));

        // COMMUTE
        transformation = EquivalenceTransformations.COMMUTE;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box(),
                x().and(y(), x()),
                x().or(y(), x()));

        assertTransformationToTarget(x().and(y()), transformation, y().and(x()));

        assertTransformationToTarget(x().and(y().and(x())), transformation, y().and(x()).and(x()));

        assertTransformationToTarget(x().or(y()), transformation, y().or(x()));

        assertTransformationToTarget(x().or(y().or(x())), transformation, y().or(x()).or(x()));

        // REMOVE_PARENTHESES_FROM_CONJUNCTION
        transformation = EquivalenceTransformations.REMOVE_PARENTHESES_FROM_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().and(y()).and(x()), transformation, x().and(y(), x()));

        assertTransformationToTarget(x().and(y().and(x())), transformation, x().and(y(), x()));

        assertTransformationToTarget(
                x().and(y().and(x()), y()), transformation, x().and(y(), x(), y()));

        // REMOVE_PARENTHESES_FROM_DISJUNCTION
        transformation = EquivalenceTransformations.REMOVE_PARENTHESES_FROM_DISJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().or(y()).or(x()), transformation, x().or(y(), x()));

        assertTransformationToTarget(x().or(y().or(x())), transformation, x().or(y(), x()));

        assertTransformationToTarget(
                x().or(y().or(x()), y()), transformation, x().or(y(), x(), y()));

        // REMOVE_PARENTHESES
        transformation = EquivalenceTransformations.REMOVE_PARENTHESES;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(x().and(y()).and(x()), transformation, x().and(y(), x()));

        assertTransformationToTarget(x().and(y().and(x())), transformation, x().and(y(), x()));

        assertTransformationToTarget(
                x().and(y().and(x()), y()), transformation, x().and(y(), x(), y()));

        assertTransformationToTarget(x().or(y()).or(x()), transformation, x().or(y(), x()));

        assertTransformationToTarget(x().or(y().or(x())), transformation, x().or(y(), x()));

        assertTransformationToTarget(
                x().or(y().or(x()), y()), transformation, x().or(y(), x(), y()));

        // DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION
        transformation = EquivalenceTransformations.DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().and(y()).or(y().and(x())),
                transformation,
                x().or(y()).and(x().or(x()), y().or(y()), y().or(x())));

        assertTransformationToTarget(
                x().and(y(), z()).or(z().and(y(), x())),
                transformation,
                x().or(z())
                        .and(
                                x().or(y()),
                                x().or(x()),
                                y().or(z()),
                                y().or(y()),
                                y().or(x()),
                                z().or(z()),
                                z().or(y()),
                                z().or(x())));

        // UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION
        transformation = EquivalenceTransformations.UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().or(y()).and(x().or(x()), y().or(y()), y().or(x())),
                transformation,
                x().and(y()).or(y().and(x())));

        assertTransformationToTarget(
                x().or(z())
                        .and(
                                x().or(y()),
                                x().or(x()),
                                y().or(z()),
                                y().or(y()),
                                y().or(x()),
                                z().or(z()),
                                z().or(y()),
                                z().or(x())),
                transformation,
                x().and(y(), z()).or(z().and(y(), x())));

        // DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER
        transformation = EquivalenceTransformations.DISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().and(y()).or(z()), transformation, x().or(z()).and(y().or(z())));

        assertTransformationToTarget(
                x().and(y(), z()).or(x().equivalent(y())),
                transformation,
                x().or(x().equivalent(y()))
                        .and(y().or(x().equivalent(y())), z().or(x().equivalent(y()))));

        // DISTRIBUTE_LEFT_CONJUNCTION
        transformation = EquivalenceTransformations.DISTRIBUTE_LEFT_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().and(y()).or(y().and(x())),
                transformation,
                x().or(y()).and(x().or(x()), y().or(y()), y().or(x())));

        assertTransformationToTarget(
                x().and(y(), z()).or(z().and(y(), x())),
                transformation,
                x().or(z())
                        .and(
                                x().or(y()),
                                x().or(x()),
                                y().or(z()),
                                y().or(y()),
                                y().or(x()),
                                z().or(z()),
                                z().or(y()),
                                z().or(x())));

        assertTransformationToTarget(
                x().and(y()).or(z()), transformation, x().or(z()).and(y().or(z())));

        assertTransformationToTarget(
                x().and(y(), z()).or(x().equivalent(y())),
                transformation,
                x().or(x().equivalent(y()))
                        .and(y().or(x().equivalent(y())), z().or(x().equivalent(y()))));

        // UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER
        transformation = EquivalenceTransformations.UNDISTRIBUTE_LEFT_CONJUNCTION_OVER_OTHER;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().or(z()).and(y().or(z())), transformation, x().and(y()).or(z()));

        assertTransformationToTarget(
                x().or(x().equivalent(y()))
                        .and(y().or(x().equivalent(y())), z().or(x().equivalent(y()))),
                transformation,
                x().and(y(), z()).or(x().equivalent(y())));

        // UNDISTRIBUTE_LEFT_CONJUNCTION
        transformation = EquivalenceTransformations.UNDISTRIBUTE_LEFT_CONJUNCTION;

        assertNotApplicable(
                transformation,
                top(),
                bottom(),
                x(),
                y(),
                x().not(),
                x().or(y()),
                x().and(y()),
                x().implies(y()),
                x().equivalent(y()),
                x().diamond(),
                x().box());

        assertTransformationToTarget(
                x().or(y()).and(x().or(x()), y().or(y()), y().or(x())),
                transformation,
                x().and(y()).or(y().and(x())));

        assertTransformationToTarget(
                x().or(z())
                        .and(
                                x().or(y()),
                                x().or(x()),
                                y().or(z()),
                                y().or(y()),
                                y().or(x()),
                                z().or(z()),
                                z().or(y()),
                                z().or(x())),
                transformation,
                x().and(y(), z()).or(z().and(y(), x())));

        // TODO fix bug
        //		assertApplicable(x().or(z()).and(y().or(z())),
        //				transformation,
        //				x().and(y()).or(z()));
        //
        //		assertApplicable(x().or(x().equivalent(y())).and(
        //				y().or(x().equivalent(y())), z().or(x().equivalent(y()))),
        //				transformation,
        //				x().and(y(), z()).or(x().equivalent(y())));

        // DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER

        // DISTRIBUTE_RIGHT_CONJUNCTION
        // UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER
        // UNDISTRIBUTE_RIGHT_CONJUNCTION
        // DISTRIBUTE_CONJUNCTION
        // UNDISTRIBUTE_CONJUNCTION
        // DISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION
        // UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION
        // DISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER
        // DISTRIBUTE_LEFT_DISJUNCTION
        // UNDISTRIBUTE_LEFT_DISJUNCTION_OVER_OTHER
        // UNDISTRIBUTE_LEFT_DISJUNCTION
        // DISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER
        // DISTRIBUTE_RIGHT_DISJUNCTION
        // UNDISTRIBUTE_RIGHT_DISJUNCTION_OVER_OTHER
        // UNDISTRIBUTE_RIGHT_DISJUNCTION
        // DISTRIBUTE_DISJUNCTION
        // UNDISTRIBUTE_DISJUNCTION
        // DISTRIBUTE_BOX_OVER_CONJUNCTION
        // UNDISTRIBUTE_BOX_OVER_CONJUNCTION
        // DISTRIBUTE_BOX
        // UNDISTRIBUTE_BOX
        // DISTRIBUTE_DIAMOND_OVER_DISJUNCTION
        // UNDISTRIBUTE_DIAMOND_OVER_DISJUNCTION
        // DISTRIBUTE_DIAMOND_OVER_IMPLICATION
        // UNDISTRIBUTE_DIAMOND_OVER_IMPLICATION
        // DISTRIBUTE_DIAMOND
        // UNDISTRIBUTE_DIAMOND
        // DISTRIBUTE
        // UNDISTRIBUTE

        // DISTRIBUTE_LEFT_PART_OF_DISJUNCTION
        // DISTRIBUTE_MIDDLE_PART_OF_DISJUNCTION
        // DISTRIBUTE_RIGHT_PART_OF_DISJUNCTION
        // DISTRIBUTE_PART_OF_DISJUNCTION

        // DISTRIBUTE_LEFT_PART_OF_CONJUNCTION
        // DISTRIBUTE_MIDDLE_PART_OF_CONJUNCTION
        // DISTRIBUTE_RIGHT_PART_OF_CONJUNCTION
        // DISTRIBUTE_PART_OF_CONJUNCTION

        // NEGATE_TRUE
        // NEGATE_FALSE
        // NEGATE_CONSTANT
        // UNNEGATE_TRUE
        // UNNEGATE_FALSE
        // UNNEGATE_CONSTANT

        // TAUTOLOGY
        // CONTRADICTION

        // NEUTRALITY_TOP
        // NEUTRALITY_BOTTOM
        // NEUTRALITY
        // DOMINATION_TOP
        // DOMINATION_BOTTOM
        // DOMINATION
        // IDEMPOTENCE_CONJUNCTION
        // IDEMPOTENCE_DISJUNCTION
        // IDEMPOTENCE
        // ABSORPTION_CONJUNCTION
        // ABSORPTION_DISJUNCTION

        // NEGATION_NORMALFORM
        // CONJUNCTIVE_NORMALFORM
        // DISJUNCTIVE_NORMALFORM

        //////////////////////////////////////////////////////////////////////////////////////////
        assertInverse(
                "¬(A∧B)",
                EquivalenceTransformations.PUSH_NEGATION_OVER_CONJUNCTION,
                EquivalenceTransformations.PULL_NEGATION_OVER_DISJUNCTION);

        assertInverse(
                "(A∧B)",
                EquivalenceTransformations.PUSH_NEGATION_OVER_CONJUNCTION_SHORTCUT,
                EquivalenceTransformations.PULL_NEGATION_OVER_DISJUNCTION_SHORTCUT);

        assertInverse(
                "(A∧B∧C)∨(X∧Y∧Z)",
                EquivalenceTransformations.DISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER,
                EquivalenceTransformations.UNDISTRIBUTE_RIGHT_CONJUNCTION_OVER_OTHER);

        assertInverse(
                "(A∧B∧C)∨(X∧Y∧Z)",
                EquivalenceTransformations.DISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION,
                EquivalenceTransformations.UNDISTRIBUTE_CONJUNCTION_OVER_CONJUNCTION);

        assertNotApplicable(
                "¬A∨B", EquivalenceTransformations.UNDISTRIBUTE_DISJUNCTION_OVER_DISJUNCTION);
        assertNotApplicable("¬A∨B", EquivalenceTransformations.UNDISTRIBUTE);
        assertNotApplicable("A∨(C→B)", EquivalenceTransformations.UNNEGATE_CONSTANT);

        ModalFormula test = formulaReader.read("A↔(B→C)");
        TransformationWithNewFormula transformationNew = BuggyTransformations.getAddToImplication();
        transformationNew.setNewFormula(formulaReader.read("A"));
        ModalFormula transformed = transformationNew.apply(test);
        assertTrue(transformed.equals(formulaReader.read("(A↔(B→C))→A")));

        assertNotApplicable("(T∧R)∨¬¬(¬T∧¬R)", EquivalenceTransformations.UNDISTRIBUTE);
        assertTransformationToTarget(
                "(A & B) | C", EquivalenceTransformations.CONJUNCTIVE_NORMALFORM, "(A∨C)∧(B∨C)");

        assertTransformationToTarget(
                "(¬B∨D)∧¬(B∧C∧A∧¬D)∧(¬D∨(¬E∧(¬B∨E)))∧B",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(¬B∨D)∧(¬B∨¬C∨¬A∨D)∧(¬D∨¬E)∧(¬D∨¬B∨E)∧B");

        assertTransformationToTarget(
                "(C→F)∧¬(F∧¬G)∧(¬¬B→E)∧¬(¬B∧G)∧(C∨F)∧((¬B∧A)∨¬E)",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(¬C∨F)∧(¬F∨G)∧(¬B∨E)∧(B∨¬G)∧(C∨F)∧(¬B∨¬E)∧(A∨¬E)");

        assertTransformationToTarget(
                "(A∨¬F)∧(¬A∨F)∧(¬N∨¬G)∧(A∨F∨G)∧(¬A∨¬F∨D)∧(¬G∨D∨N)∧⊤∧¬D",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(A∨¬F)∧(¬A∨F)∧(¬N∨¬G)∧(A∨F∨G)∧(¬A∨¬F∨D)∧(¬G∨D∨N)∧¬D");

        assertTransformationToTarget(
                "(A∧¬F)∨(¬A∧F)∨(¬N∧¬G)∨(A∧F∧G)∨(¬A∧¬F∧D)∨(¬G∧D∧N)∨0∨¬D",
                EquivalenceTransformations.DISJUNCTIVE_NORMALFORM,
                "(A∧¬F)∨(¬A∧F)∨(¬N∧¬G)∨(A∧F∧G)∨(¬A∧¬F∧D)∨(¬G∧D∧N)∨¬D");

        assertTransformationToTarget(
                "(¬A∨¬B)∧(E∨A∨0)",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(¬A∨¬B)∧(E∨A)");

        assertTransformationToTarget("0∧C", EquivalenceTransformations.CONJUNCTIVE_NORMALFORM, "0");
        assertTransformationToTarget(
                "(A∨1)∧0", EquivalenceTransformations.CONJUNCTIVE_NORMALFORM, "0");

        assertTransformationToTarget(
                "a∨c∨(b∧d)", EquivalenceTransformations.CONJUNCTIVE_NORMALFORM, "(a∨c∨b)∧(a∨c∨d)");

        assertTransformationToTarget(
                "(a∧c)∨(¬a∧¬c)∨b",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(a∨¬a∨b)∧(a∨¬c∨b)∧(c∨¬a∨b)∧(c∨¬c∨b)");

        assertTransformationToTarget(
                "(b↔¬d)∧((a↔c)∨b)∧¬((a↔c)∧b)",
                EquivalenceTransformations.CONJUNCTIVE_NORMALFORM,
                "(b∨¬b)∧(b∨d)∧(¬d∨¬b)∧(¬d∨d)∧(a∨¬a∨b)∧(a∨¬c∨b)∧(c∨¬a∨b)∧(c∨¬c∨b)∧(¬a∨¬c∨¬b)∧(a∨c∨¬b)");
    }
}
