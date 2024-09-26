package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTextEntry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTexts;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTextsInterface;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.XmlFeedbackTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "group")
public class XmlModalFeedbackTransformationGroup extends XmlFeedbackTransformation<ModalFormula> {

    @Override
    protected List<TransformationWithFeedbackTextsInterface<ModalFormula>> parseTexts(
            String key,
            String name,
            Optional<TransformationRegistry<ModalFormula>> context,
            List<FeedbackTextEntry> descriptions,
            boolean isBuggy,
            List<String> transformationTypes) {
        TransformationRegistry<ModalFormula> registry = context.get();

        if (!registry.hasGroup(key)) {
            throw new NullPointerException(
                    "There is no group of transformation with the given id.");
        }

        List<Transformation<ModalFormula>> transformations = registry.getGroup(key);

        ArrayList<TransformationWithFeedbackTextsInterface<ModalFormula>> res = new ArrayList<>();
        transformations.forEach(
                transformation -> {
                    if (transformation instanceof TransformationWithNewTree) {
                        res.add(
                                new ModalTransformationWithFeedbackAndNewFormula(
                                        (TransformationWithNewTree<ModalFormula>) transformation,
                                        name,
                                        isBuggy,
                                        transformationTypes));
                    } else {
                        res.add(
                                new TransformationWithFeedbackTexts<ModalFormula>(
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
