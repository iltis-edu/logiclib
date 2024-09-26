package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.Tree;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class XmlFeedbackDescription<T extends Tree<T>>
        extends Parsable<FeedbackTextEntry, TransformationRegistry<T>> {
    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    ParsableString type = new ParsableString();

    @XmlValue
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    ParsableString text = new ParsableString();

    @Override
    protected FeedbackTextEntry parse(Optional<TransformationRegistry<T>> context) {
        return new FeedbackTextEntry(type.required().value(), text.required().value());
    }
}
