package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern.XmlMatchPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern.XmlReplacePattern;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.UnaryPatternTransformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pattern")
public class XmlPatternTransformation extends XmlTransformation {
    public XmlMatchPattern match;
    public XmlReplacePattern replace;

    @Override
    protected Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path) {

        return new UnaryPatternTransformation<TermOrFormula>(
                path, match.required().value(), replace.required().value());
    }
}
