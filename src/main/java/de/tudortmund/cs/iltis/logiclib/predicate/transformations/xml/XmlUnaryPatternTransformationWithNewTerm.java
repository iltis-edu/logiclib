package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "patternNewTerm")
public class XmlUnaryPatternTransformationWithNewTerm
        extends XmlUnaryPatternTransformationWithNewTree {

    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString newTerm = new ParsableString();

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString onlyAtomar;

    @Override
    protected Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path) {

        if (onlyAtomar.isPresent()) {
            return new UnaryPatternTransformationWithNewTerm(
                    path,
                    match.required().value(),
                    replace.required().value(),
                    newTerm.required().value(),
                    onlyAtomar.value().equals("true"));
        }

        return new UnaryPatternTransformationWithNewTerm(
                path,
                match.required().value(),
                replace.required().value(),
                newTerm.required().value(),
                false);
    }
}
