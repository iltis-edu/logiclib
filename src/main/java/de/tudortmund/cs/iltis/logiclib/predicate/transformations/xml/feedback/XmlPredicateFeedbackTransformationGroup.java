package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTextEntry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTexts;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTextsInterface;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.XmlFeedbackTransformation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "group")
public class XmlPredicateFeedbackTransformationGroup
        extends XmlFeedbackTransformation<TermOrFormula> {

    @Override
    protected List<TransformationWithFeedbackTextsInterface<TermOrFormula>> parseTexts(
            String key,
            String name,
            Optional<TransformationRegistry<TermOrFormula>> context,
            List<FeedbackTextEntry> descriptions,
            boolean isBuggy,
            List<String> transformationTypes) {
        TransformationRegistry<TermOrFormula> registry = context.get();

        if (!registry.hasGroup(key)) {
            throw new NullPointerException(
                    "There is no group of transformation with the given id.");
        }

        List<Transformation<TermOrFormula>> transformations = registry.getGroup(key);

        ArrayList<TransformationWithFeedbackTextsInterface<TermOrFormula>> res = new ArrayList<>();
        transformations.forEach(
                transformation -> {
                    if (transformation instanceof UnaryPatternTransformationWithNewFormula) {
                        res.add(
                                new PredicateTransformationWithFeedbackAndNewFormula(
                                        (UnaryPatternTransformationWithNewFormula) transformation,
                                        name,
                                        isBuggy,
                                        transformationTypes));
                    } else if (transformation instanceof UnaryPatternTransformationWithNewTerm) {
                        res.add(
                                new PredicateTransformationWithFeedbackAndNewTerm(
                                        (UnaryPatternTransformationWithNewTerm) transformation,
                                        name,
                                        isBuggy,
                                        transformationTypes));
                    } else {
                        res.add(
                                new TransformationWithFeedbackTexts<TermOrFormula>(
                                        transformation, name, isBuggy, transformationTypes));
                    }
                });

        res.forEach(
                transformation -> {
                    descriptions.forEach(
                            description ->
                                    transformation.addFeedbackText(
                                            description.getType(), description.getText()));
                });

        return res;
    }
}
