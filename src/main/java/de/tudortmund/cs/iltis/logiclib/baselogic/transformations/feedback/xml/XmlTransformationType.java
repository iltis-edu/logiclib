package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.Tree;
import java.util.Optional;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class XmlTransformationType<T extends Tree<T>>
        extends Parsable<String, TransformationRegistry<T>> {

    @XmlValue
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    ParsableString text = new ParsableString();

    @Override
    protected String parse(Optional<TransformationRegistry<T>> context) {
        return text.required().value();
    }
}
