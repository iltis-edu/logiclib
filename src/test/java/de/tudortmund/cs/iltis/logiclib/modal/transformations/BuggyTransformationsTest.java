package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.BuggyTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.List;
import org.junit.Test;

/** The following test cases are supposed to cover {@link BuggyTransformations}. */
public class BuggyTransformationsTest extends TransformationsTest {

    @Test
    public void test() {

        // WRONG_IDEMPOTENCE
        List<Transformation> transformations = BuggyTransformations.getIdempotenceToTop();

        assertTransformationToTarget(x().and(x()), transformations, top());
        assertTransformationToTarget(x().or(x()), transformations, top());

        // WRONG_IDEMPOTENCE_INVERSE
        transformations = BuggyTransformations.getFormulaToImplicationalTautology();

        assertTransformationToTarget(x(), transformations, x().implies(x()));
        assertTransformationToTarget(x(), transformations, x().equivalent(x()));

        // WRONG_INVERSE
        transformations = BuggyTransformations.getReduceConjunctionWithNegation();

        assertTransformationToTarget(x().and(x().not()), transformations, top());
        assertTransformationToTarget(x().not().and(x()), transformations, top());
        assertTransformationToTarget(x().and(x().not()), transformations, x());
        assertTransformationToTarget(x().not().and(x()), transformations, x());
        assertTransformationToTarget(
                x().or(y()).and(x().not().or(y().not())), transformations, bottom());
        assertTransformationToTarget(
                x().not().or(y().not()).and(x().or(y())), transformations, bottom());

        transformations = BuggyTransformations.getReduceDisjunctionWithNegation();

        assertTransformationToTarget(x().or(x().not()), transformations, bottom());
        assertTransformationToTarget(x().not().or(x()), transformations, bottom());
        assertTransformationToTarget(x().or(x().not()), transformations, x());
        assertTransformationToTarget(x().not().or(x()), transformations, x());
        assertTransformationToTarget(
                x().and(y()).or(x().not().and(y().not())), transformations, top());
        assertTransformationToTarget(
                x().not().and(y().not()).or(x().and(y())), transformations, top());

        // WRONG_TOP
        transformations = BuggyTransformations.getReduceTopInFormula();

        assertTransformationToTarget(x().or(top()), transformations, x());
        assertTransformationToTarget(top().or(x()), transformations, x());
        assertTransformationToTarget(x().and(top()), transformations, top());
        assertTransformationToTarget(top().and(x()), transformations, top());

        // WRONG_BOTTOM
        transformations = BuggyTransformations.getReduceBottomInFormula();

        assertTransformationToTarget(x().or(bottom()), transformations, bottom());
        assertTransformationToTarget(bottom().or(x()), transformations, bottom());
        assertTransformationToTarget(x().and(bottom()), transformations, x());
        assertTransformationToTarget(bottom().and(x()), transformations, x());

        // IMPLICATION_COMMUTATIVITY
        transformations = BuggyTransformations.getSwapPremiseAndConclusion();

        assertTransformationToTarget(x().implies(y()), transformations, y().implies(x()));

        // IMPLICATION_ASSOCIATIVITY
        transformations = BuggyTransformations.getMoveParenthesisInMultiLayeredImplication();

        assertTransformationToTarget(
                x().implies(y().implies(z())), transformations, x().implies(y()).implies(z()));
        assertTransformationToTarget(
                x().implies(y()).implies(z()), transformations, x().implies(y().implies(z())));

        // IMPLICATION_IDEMPOTENCE
        transformations = BuggyTransformations.getReduceImplicationalTautology();

        assertTransformationToTarget(x().implies(x()), transformations, x());

        // EQUIVALENCE_IDEMPOTENCE
        transformations = BuggyTransformations.getReduceEquivalentTautology();

        assertTransformationToTarget(x().equivalent(x()), transformations, x());

        // WRONG_EQUIVALENCE_ELIMINATION
        transformations = BuggyTransformations.getRemoveEquivalence();

        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().and(y()).not()));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().not().and(y())));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().and(y().not())));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().and(y())));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().or(y().not()).not()));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().or(y()).and(x().not().or(y().not())));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).and(x().not().and(y().not())));
        assertTransformationToTarget(
                x().equivalent(y()), transformations, x().and(y()).or(x().not().or(y().not())));
        assertTransformationToTarget(x().equivalent(y()), transformations, x().not().or(y()));

        // WRONG_IMPLICATION_ELIMINATION
        transformations = BuggyTransformations.getRemoveImplication();

        assertTransformationToTarget(x().implies(y()), transformations, x().or(y()).not());
        assertTransformationToTarget(x().implies(y()), transformations, x().or(y()));
        assertTransformationToTarget(x().implies(y()), transformations, x().and(y()).not());
        assertTransformationToTarget(x().implies(y()), transformations, x().or(y().not()));
        assertTransformationToTarget(x().implies(y()), transformations, x().not().and(y()));
        assertTransformationToTarget(
                x().implies(y()), transformations, x().and(y()).or(x().not().and(y().not())));
        assertTransformationToTarget(
                x().or(y()).implies(z()), transformations, x().or(y().not()).or(z()));
        assertTransformationToTarget(
                x().and(y()).implies(z()), transformations, x().and(y().not()).or(z()));
        assertTransformationToTarget(
                x().implies(y()).implies(z()), transformations, x().implies(y().not()).or(z()));
        assertTransformationToTarget(
                x().equivalent(y()).implies(z()),
                transformations,
                x().equivalent(y().not()).or(z()));

        // WRONG_IMPLICATION_INTRODUCTION
        transformations = BuggyTransformations.getReduceToImplication();

        assertTransformationToTarget(x().not().and(y()), transformations, x().implies(y()));
        assertTransformationToTarget(x().or(y().not()), transformations, x().implies(y()));
        assertTransformationToTarget(x().and(y().not()), transformations, y().implies(x()));
        assertTransformationToTarget(x().not().or(y()), transformations, y().implies(x()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().implies(y()));

        // WRONG_DE_MORGAN
        transformations = BuggyTransformations.getPushNegation();

        assertTransformationToTarget(x().and(y()).not(), transformations, x().not().or(y()));
        assertTransformationToTarget(x().and(y()).not(), transformations, x().or(y().not()));
        assertTransformationToTarget(x().and(y()).not(), transformations, x().or(y()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().not().and(y()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().and(y().not()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().and(y()));
        assertTransformationToTarget(
                x().and(y()).not(), transformations, x().not().or(y().not()).not());
        assertTransformationToTarget(
                x().or(y()).not(), transformations, x().not().and(y().not()).not());
        assertTransformationToTarget(x().and(y()).not(), transformations, x().not().and(y().not()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().not().or(y().not()));
        assertTransformationToTarget(
                x().and(y()).not().or(z()).not(),
                transformations,
                x().not().or(y().not()).not().or(z()));
        assertTransformationToTarget(
                x().and(y()).not().and(z()).not(),
                transformations,
                x().not().or(y().not()).not().and(z()));
        assertTransformationToTarget(
                x().or(y()).not().or(z()).not(),
                transformations,
                x().not().and(y().not()).not().or(z()));
        assertTransformationToTarget(
                x().or(y()).not().and(z()).not(),
                transformations,
                x().not().and(y().not()).not().and(z()));
        assertTransformationToTarget(
                x().implies(y()).not(), transformations, x().not().implies(y().not()));
        assertTransformationToTarget(
                x().equivalent(y()).not(),
                transformations,
                x().and(y()).not().or(x().not().and(y().not())));

        // WRONG_DE_MORGAN_INVERSE
        transformations = BuggyTransformations.getPullNegation();

        assertTransformationToTarget(x().not().and(y().not()), transformations, x().and(y()).not());
        assertTransformationToTarget(x().not().or(y().not()), transformations, x().or(y()).not());

        // REMOVE_PARENTHESIS
        transformations = BuggyTransformations.getRemoveParenthesis();

        assertTransformationToTarget(x().and(y()).not(), transformations, x().not().and(y()));
        assertTransformationToTarget(x().or(y()).not(), transformations, x().not().or(y()));
        assertTransformationToTarget(x().not().and(y()).not(), transformations, x().and(y()));
        assertTransformationToTarget(
                x().not().implies(y()).not(), transformations, x().implies(y()));
        assertTransformationToTarget(
                x().not().equivalent(y()).not(), transformations, x().equivalent(y()));

        // WRONG_ASSOCIATIVITY
        transformations = BuggyTransformations.getMoveParenthesisInsteadOfDistribution();

        assertTransformationToTarget(x().or(y().and(z())), transformations, x().or(y()).and(z()));
        assertTransformationToTarget(x().or(y()).and(z()), transformations, x().or(y().and(z())));
        assertTransformationToTarget(x().and(y()).or(z()), transformations, x().and(y().or(z())));
        assertTransformationToTarget(x().and(y().or(z())), transformations, x().and(y()).or(z()));

        // WRONG_ABSORPTION
        transformations = BuggyTransformations.getReduceToSubformula();

        assertTransformationToTarget(
                x().or(y()).and(x().and(y()).and(z())), transformations, x().or(y()));
        assertTransformationToTarget(
                x().and(y()).or(x().or(y()).and(z())), transformations, x().and(y()));
        assertTransformationToTarget(
                x().or(y()).and(x().and(y()).or(z())), transformations, x().or(y()));
        assertTransformationToTarget(
                x().and(y()).or(x().or(y()).or(z())), transformations, x().and(y()));
        assertTransformationToTarget(x().or(x().and(y())), transformations, y());
        assertTransformationToTarget(x().or(y().and(x())), transformations, y());
        assertTransformationToTarget(x().and(y()).or(x()), transformations, y());
        assertTransformationToTarget(y().and(x()).or(x()), transformations, y());
        assertTransformationToTarget(x().and(x().or(y())), transformations, y());
        assertTransformationToTarget(x().and(y().or(x())), transformations, y());
        assertTransformationToTarget(x().or(y()).and(x()), transformations, y());
        assertTransformationToTarget(y().or(x()).and(x()), transformations, y());

        // WRONG_DISTRIBUTION
        transformations = BuggyTransformations.getPushDistributionToWrongTarget();

        assertTransformationToTarget(
                x().and(y().or(z())), transformations, x().and(y()).and(x().and(z())));
        assertTransformationToTarget(
                x().or(y()).and(z()), transformations, x().and(z()).and(y().and(z())));
        assertTransformationToTarget(
                x().and(y().or(z())), transformations, x().or(y()).and(x().or(z())));
        assertTransformationToTarget(
                x().or(y()).and(z()), transformations, x().or(z()).and(y().or(z())));
        assertTransformationToTarget(
                x().or(y().and(z())), transformations, x().or(y()).or(x().or(z())));
        assertTransformationToTarget(
                x().and(y()).or(z()), transformations, x().or(z()).or(y().or(z())));
        assertTransformationToTarget(
                x().or(y().and(z())), transformations, x().and(y()).or(x().and(z())));
        assertTransformationToTarget(
                x().and(y()).or(z()), transformations, x().and(z()).or(y().and(z())));
        assertTransformationToTarget(
                x().and(y().and(z())), transformations, x().and(y()).and(x().and(z())));
        assertTransformationToTarget(
                x().and(y()).and(z()), transformations, x().and(z()).and(y().and(z())));
        assertTransformationToTarget(
                x().or(y().or(z())), transformations, x().or(y()).or(x().or(z())));
        assertTransformationToTarget(
                x().or(y()).or(z()), transformations, x().or(z()).or(y().or(z())));
        assertTransformationToTarget(
                x().not().and(y().or(z())), transformations, x().not().and(y()).or(x().and(z())));
        assertTransformationToTarget(
                x().not().and(y().or(z())), transformations, x().and(y()).or(x().not().and(z())));
        assertTransformationToTarget(
                x().or(y()).and(z().not()), transformations, x().and(z().not()).or(y().and(z())));
        assertTransformationToTarget(
                x().or(y()).and(z().not()), transformations, x().and(z()).or(y().and(z().not())));
        assertTransformationToTarget(
                x().not().or(y().and(z())), transformations, x().not().or(y()).and(x().or(z())));
        assertTransformationToTarget(
                x().not().or(y().and(z())), transformations, x().or(y()).and(x().not().or(z())));
        assertTransformationToTarget(
                x().and(y()).or(z().not()), transformations, x().or(z().not()).and(y().or(z())));
        assertTransformationToTarget(
                x().and(y()).or(z().not()), transformations, x().or(z()).and(y().or(z().not())));

        // WRONG_DISTRIBUTION_INVERSE
        transformations = BuggyTransformations.getPullDistributionToWrongTarget();

        assertTransformationToTarget(
                x().and(y()).and(x().and(z())), transformations, x().and(y().or(z())));
        assertTransformationToTarget(
                x().and(z()).and(y().and(z())), transformations, x().or(y()).and(z()));
        assertTransformationToTarget(
                x().or(y()).and(x().or(z())), transformations, x().and(y().or(z())));
        assertTransformationToTarget(
                x().or(z()).and(y().or(z())), transformations, x().or(y()).or(z()));
        assertTransformationToTarget(
                x().or(y()).or(x().or(z())), transformations, x().or(y().and(z())));
        assertTransformationToTarget(
                x().or(z()).or(y().or(z())), transformations, x().and(y()).or(z()));
        assertTransformationToTarget(
                x().and(y()).or(x().and(z())), transformations, x().or(y().and(z())));
        assertTransformationToTarget(
                x().and(z()).or(y().and(z())), transformations, x().and(y()).or(z()));
    }

    private void assertTransformationToTarget(
            ModalFormula test, List<Transformation> transformations, ModalFormula target) {

        boolean containsApplicableTransformation = false;

        for (Transformation transformation : transformations) {
            if (isApplicable(test, transformation, target)) {
                containsApplicableTransformation = true;
            }
        }

        assertTrue(
                test + " could not be transformed to " + target, containsApplicableTransformation);
    }

    private void assertNotApplicable(
            List<Transformation> transformations, ModalFormula... formulas) {

        for (Transformation transformation : transformations) {
            assertNotApplicable(transformation, formulas);
        }
    }

    protected boolean isApplicable(String test, Transformation transformation, String target) {

        try {
            ModalFormula formula =
                    ModalFormula.parse(test, ModalReaderProperties.createDefaultWithLatex());
            ModalFormula targetFormula =
                    ModalFormula.parse(target, ModalReaderProperties.createDefaultWithLatex());
            return isApplicable(formula, transformation, targetFormula);

        } catch (IncorrectParseInputException e) {
            System.out.println(test + " is not parseable!");
        }

        return false;
    }

    protected boolean isApplicable(
            ModalFormula formula, Transformation transformation, ModalFormula target) {

        if (transformation.isApplicable(formula)) {
            ModalFormula transformed = transformation.apply(formula);

            if (transformed.equals(target)) {
                return true;
            }
        }

        return false;
    }
}
