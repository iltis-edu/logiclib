package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.Tree;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlTransient
public abstract class XmlFeedbackTransformation<T extends Tree<T>>
        extends Parsable<
                List<TransformationWithFeedbackTextsInterface<T>>, TransformationRegistry<T>> {

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString id = new ParsableString();

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString name = new ParsableString();

    @XmlElement(name = "description")
    public List<XmlFeedbackDescription<T>> descriptions = null;

    @XmlElement(name = "transformationType")
    public List<XmlTransformationType<T>> types = null;

    @Override
    public List<TransformationWithFeedbackTextsInterface<T>> parse(
            Optional<TransformationRegistry<T>> context) {
        if (!context.isPresent()) {
            throw new NullPointerException("Missing Transformationregistry.");
        }
        return parseTexts(
                id.required().value(),
                name.required().value(),
                context,
                parseList("descriptions", context),
                id.required().value().startsWith("BUGGY"),
                parseList("types", context));
    }

    protected abstract List<TransformationWithFeedbackTextsInterface<T>> parseTexts(
            String key,
            String name,
            Optional<TransformationRegistry<T>> context,
            List<FeedbackTextEntry> descriptions,
            boolean isBuggy,
            List<String> transformationTypes);
}
