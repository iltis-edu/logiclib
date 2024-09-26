package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlMatchPattern;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlReplacePattern;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.UnaryInvertiblePatternTransformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invertible")
public class XmlInvertibleTransformation extends XmlTransformation {
    public XmlMatchPattern match;

    public XmlReplacePattern replace;

    @Override
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {

        return new UnaryInvertiblePatternTransformation<ModalFormula>(
                path, match.required().value(), replace.required().value());
    }
}
