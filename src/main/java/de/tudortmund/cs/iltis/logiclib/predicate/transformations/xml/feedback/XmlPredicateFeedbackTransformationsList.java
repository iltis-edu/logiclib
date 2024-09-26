package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.XmlFeedbackTransformation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transformationsWithFeedbackTexts")
public class XmlPredicateFeedbackTransformationsList
        extends Parsable<
                FeedbackTransformationsList<TermOrFormula>, TransformationRegistry<TermOrFormula>> {

    @XmlElementRefs({
        @XmlElementRef(type = XmlPredicateFeedbackTransformationSingle.class),
        @XmlElementRef(type = XmlPredicateFeedbackTransformationGroup.class),
    })
    public List<XmlFeedbackTransformation<TermOrFormula>> transformationsWithFeedbackTexts;

    @Override
    protected FeedbackTransformationsList<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> context) {
        return new FeedbackTransformationsList<TermOrFormula>(
                parseList("transformationsWithFeedbackTexts", context));
    }
}
