package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlTargetPattern;
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
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {

        return new TargetedIterationAlgorithm<ModalFormula>(
                path, target.required().value(), reference.parse(reg));
    }
}
