package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import static org.junit.Assert.fail;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import org.junit.Test;

public class XmlTransformationPredicateListTest {

    @Test
    public void testPredicateSingleInvertibleTransformations() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateImplication() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateEquivalence() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateEquivalence.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateAssociativity() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateConstants() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateConstants.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateCommutation() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateCommutation.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateDeMorgan() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                        registry);

        AdornedTransformation.TestResult result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateDistribution() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDistribution.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateAlgorithms() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateEquivalence.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateCommutation.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateConstants.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDistribution.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAlgorithms.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateFixedCombinations() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                        registry);
        transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateFixedCombinations.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateQuantifiers() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateQuantifiers.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }

    @Test
    public void testPredicateBuggyTransformations() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        TransformationList<TermOrFormula> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateBuggyTransformations.xml",
                        registry);

        AdornedTransformation.TestResult<TermOrFormula> result;

        for (AdornedTransformation<TermOrFormula> adornedTransformation : transformations) {
            result = adornedTransformation.runTests();
            if (result.failedNumber() > 0) {
                fail();
            }
        }
    }
}
