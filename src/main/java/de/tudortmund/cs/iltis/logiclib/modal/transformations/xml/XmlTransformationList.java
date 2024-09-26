package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transformations")
public class XmlTransformationList
        extends Parsable<TransformationList<ModalFormula>, TransformationRegistry<ModalFormula>> {
    @XmlElementRefs({
        @XmlElementRef(type = XmlInvertibleTransformation.class),
        @XmlElementRef(type = XmlFixpointTransformation.class),
        @XmlElementRef(type = XmlMetaTransformation.class),
        @XmlElementRef(type = XmlChildrenPatternTransformation.class),
        @XmlElementRef(type = XmlSequentialTransformation.class),
        @XmlElementRef(type = XmlPatternTransformation.class),
        @XmlElementRef(type = XmlChildrenTransformation.class),
        @XmlElementRef(type = XmlTargetedIterationTransformation.class),
        @XmlElementRef(type = XmlUnaryPatternTransformationWithNewFormula.class),
        @XmlElementRef(type = XmlCleanTransformation.class),
    })
    public List<XmlTransformation> transformations;

    @Override
    protected TransformationList<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new TransformationList<ModalFormula>(parseList("transformations", context));
    }
}
