package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTexts;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback.ModalTransformationWithFeedbackAndNewFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback.XmlModalFeedbackTransformationLoader;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class XmlModalFeedbackTest {

    @Test
    public void testModalTransformationsWithFeedback() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        List<Transformation<ModalFormula>> transformations =
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/buggyTransformations.xml",
                                registry)
                        .getUnadornedTransformations();

        FeedbackTransformationsList<ModalFormula> feedbackTransformationsList =
                XmlModalFeedbackTransformationLoader.readFeedbackToTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/feedback/ModalBuggyTransformationsFeedbackGerman.xml",
                        registry);

        assert transformations.size() == feedbackTransformationsList.size()
                : "Unexpected number of modal buggy transformations loaded";

        transformations = new ArrayList<>();

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/fixedCombinations.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",
                                registry)
                        .getUnadornedTransformations());

        feedbackTransformationsList =
                XmlModalFeedbackTransformationLoader.readFeedbackToTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/feedback/ModalEquivalenceTransformationsFeedbackGerman.xml",
                        registry);

        assert feedbackTransformationsList.size() == transformations.size()
                : "Unexpected number of modal equivalence transformations loaded";
    }

    @Test
    public void testExample() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        List<Transformation<ModalFormula>> transformations =
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/feedback/TestTransformations.xml",
                                registry)
                        .getUnadornedTransformations();

        FeedbackTransformationsList<ModalFormula> feedbackTrafos =
                XmlModalFeedbackTransformationLoader.readFeedbackToTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/feedback/TestFeedbackTexts.xml",
                        registry);

        assert feedbackTrafos.size() == 13
                : "Unexpected number of transformations loaded: expected 13, got "
                        + feedbackTrafos.size();

        String wrongFeedbackTextsNumber =
                "Unexpected number of feedback texts for transformation found: expected ";

        assert feedbackTrafos.get(0).getMap().size() == 1
                : wrongFeedbackTextsNumber + 1 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(1).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(2).getMap().size() == 1
                : wrongFeedbackTextsNumber + 1 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(3).getMap().size() == 1
                : wrongFeedbackTextsNumber + 1 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(4).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(5).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(6).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(7).getMap().size() == 1
                : wrongFeedbackTextsNumber + 1 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(8).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(9).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(10).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(11).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();
        assert feedbackTrafos.get(12).getMap().size() == 2
                : wrongFeedbackTextsNumber + 2 + ", got " + feedbackTrafos.get(0).getMap().size();

        String wrongTransformationClass =
                "Wrong transformation class (newFormula yes/no) registered: expected";

        for (int i = 0; i < 7; i++) {
            assert (feedbackTrafos.get(i) instanceof TransformationWithFeedbackTexts
                            && !(feedbackTrafos.get(i)
                                    instanceof ModalTransformationWithFeedbackAndNewFormula))
                    : wrongTransformationClass
                            + " normal transformation, got transformation with new formula for transformation"
                            + feedbackTrafos.get(i).getName();
        }
        assert (feedbackTrafos.get(11) instanceof TransformationWithFeedbackTexts
                        && !(feedbackTrafos.get(11)
                                instanceof ModalTransformationWithFeedbackAndNewFormula))
                : wrongTransformationClass
                        + " normal transformation, got transformation with new formula for transformation"
                        + feedbackTrafos.get(11).getName();

        for (int i = 7; i < 11; i++) {
            assert feedbackTrafos.get(i) instanceof ModalTransformationWithFeedbackAndNewFormula
                    : wrongTransformationClass
                            + " transformation with new formula, got normal transformation for transformation"
                            + feedbackTrafos.get(i).getName();
        }
        assert feedbackTrafos.get(12) instanceof ModalTransformationWithFeedbackAndNewFormula
                : wrongTransformationClass
                        + " transformation with new formula, got normal transformation for transformation"
                        + feedbackTrafos.get(12).getName();

        String wrongTransformationTypes = "Wrong transformation types registered";

        // singles
        assert feedbackTrafos.get(0).hasType("transformationType1")
                        && feedbackTrafos.get(0).hasType("transformationType2")
                : wrongTransformationTypes;
        assert feedbackTrafos.get(1).hasType("transformationType1")
                        && !feedbackTrafos.get(1).hasType("transformationType2")
                : wrongTransformationTypes;
        assert !feedbackTrafos.get(7).hasType("transformationType1")
                        && !feedbackTrafos.get(7).hasType("transformationType2")
                : wrongTransformationTypes;
        // groups
        assert feedbackTrafos.get(2).hasType("transformationType1")
                        && feedbackTrafos.get(2).hasType("transformationType2")
                : wrongTransformationTypes;
        assert feedbackTrafos.get(3).hasType("transformationType1")
                        && feedbackTrafos.get(3).hasType("transformationType2")
                : wrongTransformationTypes;
        assert feedbackTrafos.get(4).hasType("transformationType1")
                        && !feedbackTrafos.get(4).hasType("transformationType2")
                : wrongTransformationTypes;
        assert feedbackTrafos.get(5).hasType("transformationType1")
                        && !feedbackTrafos.get(5).hasType("transformationType2")
                : wrongTransformationTypes;
        assert !feedbackTrafos.get(8).hasType("transformationType1")
                        && !feedbackTrafos.get(8).hasType("transformationType2")
                : wrongTransformationTypes;
        assert !feedbackTrafos.get(9).hasType("transformationType1")
                        && !feedbackTrafos.get(9).hasType("transformationType2")
                : wrongTransformationTypes;
    }
}
