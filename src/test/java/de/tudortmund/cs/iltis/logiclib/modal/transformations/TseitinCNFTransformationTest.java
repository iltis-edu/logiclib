package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.XmlModalTransformationLoader;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import org.junit.Test;

public class TseitinCNFTransformationTest {

    private TseitinCNFTransformation transformation;

    @Test
    public void testTseitinCNFTransformation() throws IncorrectParseInputException {
        TransformationRegistry<ModalFormula> registry = getRegistry();

        transformation = new TseitinCNFTransformation("X", registry);
        assertApplication("((P | Q) & R) -> !S");
        assertApplication("(A & !(!B) & (C -> D)) | (!A & D) | (C -> !B)");
        assertApplication("(A & Q &!B) | (!Q & B)");
        assertApplication("(A & B & (C <-> !D)) -> !(A & !B)");
        assertApplication(
                "(A & !B) <-> (A | !(!C) | D | (E & !C & (!B <-> (!F & A & (!C <-> B)))))");
    }

    private void assertApplication(String input) throws IncorrectParseInputException {
        ModalFormula inputFormula = ModalFormula.parse(input);
        System.out.println("----- Inputformula: " + inputFormula.toString() + " -----");
        long start = System.currentTimeMillis();
        ModalFormula actualFormula = transformation.apply(inputFormula);
        long timeDiff = System.currentTimeMillis() - start;
        System.out.println("Tseitin transformation took " + timeDiff + " ms");
        System.out.println("result size: " + actualFormula.getSize());
        System.out.println("result formula: " + actualFormula.toString());
    }

    private TransformationRegistry<ModalFormula> getRegistry() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<>();
        TransformationList<ModalFormula> trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                        registry);
        trafos =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/algorithms.xml",
                        registry);

        return registry;
    }
}
