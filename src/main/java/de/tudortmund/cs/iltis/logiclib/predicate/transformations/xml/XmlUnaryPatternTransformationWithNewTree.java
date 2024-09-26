package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern.XmlMatchPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern.XmlReplacePattern;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;

public abstract class XmlUnaryPatternTransformationWithNewTree extends XmlTransformation {
    public XmlMatchPattern match;
    public XmlReplacePattern replace;

    @Override
    protected abstract Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path);
}
