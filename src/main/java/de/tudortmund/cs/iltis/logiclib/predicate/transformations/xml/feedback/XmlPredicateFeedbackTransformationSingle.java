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

@XmlRootElement(name = "single")
public class XmlPredicateFeedbackTransformationSingle
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

        if (!registry.has(key)) {
            throw new NullPointerException("There is no transformation with the given id.");
        }

        Transformation<TermOrFormula> unpackedTransformation = registry.get(key);

        TransformationWithFeedbackTextsInterface<TermOrFormula> transformation;

        if (unpackedTransformation instanceof UnaryPatternTransformationWithNewTerm) {
            transformation =
                    new PredicateTransformationWithFeedbackAndNewTerm(
                            (UnaryPatternTransformationWithNewTerm) unpackedTransformation,
                            name,
                            isBuggy,
                            transformationTypes);
        } else if (unpackedTransformation instanceof UnaryPatternTransformationWithNewFormula) {
            transformation =
                    new PredicateTransformationWithFeedbackAndNewFormula(
                            (UnaryPatternTransformationWithNewFormula) unpackedTransformation,
                            name,
                            isBuggy,
                            transformationTypes);
        } else {
            transformation =
                    new TransformationWithFeedbackTexts<TermOrFormula>(
                            unpackedTransformation, name, isBuggy, transformationTypes);
        }

        descriptions.forEach(
                description ->
                        transformation.addFeedbackText(
                                description.getType(), description.getText()));

        List<TransformationWithFeedbackTextsInterface<TermOrFormula>> res = new ArrayList<>(1);
        res.add(transformation);

        return res;
    }
}
