package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern.XmlTargetPattern;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.TargetedIterationAlgorithm;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "targetedIteration")
public class XmlTargetedIterationTransformation extends XmlTransformation {

    public XmlTransformationReference reference;

    public XmlTargetPattern target;

    @Override
    protected Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path) {

        return new TargetedIterationAlgorithm<TermOrFormula>(
                path, target.required().value(), reference.parse(registry));
    }
}
