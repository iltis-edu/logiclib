package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.XmlFeedbackTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transformationsWithFeedbackTexts")
public class XmlModalFeedbackTransformationsList
        extends Parsable<
                FeedbackTransformationsList<ModalFormula>, TransformationRegistry<ModalFormula>> {

    @XmlElementRefs({
        @XmlElementRef(type = XmlModalFeedbackTransformationSingle.class),
        @XmlElementRef(type = XmlModalFeedbackTransformationGroup.class),
    })
    public List<XmlFeedbackTransformation<ModalFormula>> transformationsWithFeedbackTexts;

    @Override
    protected FeedbackTransformationsList<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new FeedbackTransformationsList<ModalFormula>(
                parseList("transformationsWithFeedbackTexts", context));
    }
}
