package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "patternNewFormula")
public class XmlUnaryPatternTransformationWithNewFormula
        extends XmlUnaryPatternTransformationWithNewTree {

    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString newFormula = new ParsableString();

    @Override
    protected Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path) {

        return new UnaryPatternTransformationWithNewFormula(
                path,
                match.required().value(),
                replace.required().value(),
                newFormula.required().value());
    }
}
