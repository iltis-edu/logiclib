package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transformations")
public class XmlTransformationList
        extends Parsable<TransformationList<TermOrFormula>, TransformationRegistry<TermOrFormula>> {
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
        @XmlElementRef(type = XmlUnaryPatternTransformationWithNewTerm.class),
        @XmlElementRef(type = XmlCleanTransformation.class),
    })
    public List<XmlTransformation> transformations;

    @Override
    protected TransformationList<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> context) {
        return new TransformationList<TermOrFormula>(parseList("transformations", context));
    }
}
