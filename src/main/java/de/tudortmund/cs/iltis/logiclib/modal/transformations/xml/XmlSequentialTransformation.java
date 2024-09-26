package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.SequentialTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sequential")
public class XmlSequentialTransformation extends XmlTransformation {

    @XmlElementWrapper(name = "references")
    @XmlElementRefs({
        @XmlElementRef(type = XmlTransformationReference.class),
    })
    public List<XmlTransformation> references;

    @Override
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {
        List<Transformation<ModalFormula>> parts = parseList("references", reg);

        return new SequentialTransformation<ModalFormula>(
                path, parts.toArray(new Transformation[0]));
    }
}
