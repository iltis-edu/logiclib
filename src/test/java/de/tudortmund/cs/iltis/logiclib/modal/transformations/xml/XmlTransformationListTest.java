package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import static org.junit.Assert.assertEquals;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import org.junit.Test;

public class XmlTransformationListTest {

    @Test
    public void testSingleInvertibleTrafo() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testImplication() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testEquivalence() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testConstants() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testDeMorgan() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testCommutation() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testAssociativity() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testFixedCombinations() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/fixedCombinations.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testAlgorithms() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/algorithms.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testDistribution() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry);
        trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    @Test
    public void testBuggyTransformations() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        TransformationList<ModalFormula> trafos =
                readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/buggyTransformations.xml",
                        registry);

        AdornedTransformation.TestResult<ModalFormula> result;

        for (AdornedTransformation<ModalFormula> t : trafos) {
            result = t.runTests();
            assertEquals(0, result.failedNumber());
        }
    }

    private static TransformationList<ModalFormula> readTransformationsFromFile(
            String path, TransformationRegistry<ModalFormula> registry) {
        return XmlModalTransformationLoader.readTransformationsFromFile(path, registry);
    }
}
