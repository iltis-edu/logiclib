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

@XmlRootElement(name = "single")
public class XmlModalFeedbackTransformationSingle extends XmlFeedbackTransformation<ModalFormula> {

    @Override
    protected List<TransformationWithFeedbackTextsInterface<ModalFormula>> parseTexts(
            String key,
            String name,
            Optional<TransformationRegistry<ModalFormula>> context,
            List<FeedbackTextEntry> descriptions,
            boolean isBuggy,
            List<String> transformationTypes) {
        TransformationRegistry<ModalFormula> registry = context.get();

        if (!registry.has(key)) {
            throw new NullPointerException("There is no transformation with the given id.");
        }

        Transformation<ModalFormula> unpackedTransformation = registry.get(key);

        TransformationWithFeedbackTextsInterface<ModalFormula> transformation;

        if (unpackedTransformation instanceof TransformationWithNewTree) {
            transformation =
                    new ModalTransformationWithFeedbackAndNewFormula(
                            (TransformationWithNewTree<ModalFormula>) unpackedTransformation,
                            name,
                            isBuggy,
                            transformationTypes);
        } else {
            transformation =
                    new TransformationWithFeedbackTexts<ModalFormula>(
                            unpackedTransformation, name, isBuggy, transformationTypes);
        }

        descriptions.forEach(
                description ->
                        transformation.addFeedbackText(
                                description.getType(), description.getText()));
        ArrayList<TransformationWithFeedbackTextsInterface<ModalFormula>> res = new ArrayList<>();
        res.add(transformation);

        return res;
    }
}
