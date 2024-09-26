package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback.XmlPredicateFeedbackTransformationLoader;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class XmlPredicateFeedbackTest {

    @Test
    public void testPredicateTransformationsWithFeedback() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        List<Transformation<TermOrFormula>> transformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateBuggyTransformations.xml",
                                registry)
                        .getUnadornedTransformations();

        FeedbackTransformationsList<TermOrFormula> feedbackTransformationsList =
                XmlPredicateFeedbackTransformationLoader.readFeedbackToTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/feedback/PredicateBuggyTransformationsFeedbackGerman.xml",
                        registry);

        assert transformations.size() == feedbackTransformationsList.size()
                : "Unexpected number of predicate buggy transformations loaded";

        transformations = new ArrayList<>();
        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateEquivalence.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateConstants.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDistribution.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateFixedCombinations.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateQuantifiers.xml",
                                registry)
                        .getUnadornedTransformations());

        transformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateCommutation.xml",
                                registry)
                        .getUnadornedTransformations());

        feedbackTransformationsList =
                XmlPredicateFeedbackTransformationLoader.readFeedbackToTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/feedback/PredicateEquivalenceTransformationsFeedbackGerman.xml",
                        registry);

        assert feedbackTransformationsList.size() == transformations.size()
                : "Unexpected number of predicate equivalence transformations loaded";
    }
}
