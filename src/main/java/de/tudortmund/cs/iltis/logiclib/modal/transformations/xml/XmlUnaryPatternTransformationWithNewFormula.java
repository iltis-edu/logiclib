package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlMatchPattern;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlReplacePattern;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.UnaryPatternTransformationWithNewTree;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "patternNewFormula")
public class XmlUnaryPatternTransformationWithNewFormula extends XmlTransformation {
    public XmlMatchPattern match;
    public XmlReplacePattern replace;

    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString newFormula = new ParsableString();

    @Override
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {

        return new UnaryPatternTransformationWithNewTree<>(
                path,
                match.required().value(),
                replace.required().value(),
                newFormula.required().value());
    }
}
