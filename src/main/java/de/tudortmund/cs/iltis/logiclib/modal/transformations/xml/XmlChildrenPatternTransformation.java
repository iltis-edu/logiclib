package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlMatchPattern;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlOuterPattern;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern.XmlReplacePattern;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.ChildrenPatternTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "childrenPattern")
public class XmlChildrenPatternTransformation extends XmlTransformation {
    public XmlMatchPattern match;
    public XmlReplacePattern replace;
    public XmlOuterPattern outer;

    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString pathChild = new ParsableString();

    @Override
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {

        if (pathChild.isPresent()) {
            try {
                return new ChildrenPatternTransformation<ModalFormula>(
                        new TreePath().child(Integer.parseInt(pathChild.value())),
                        outer.required().value(),
                        match.required().value(),
                        replace.required().value());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("'pathChild' must be a non negative integer");
            }
        }

        return new ChildrenPatternTransformation<ModalFormula>(
                path,
                outer.required().value(),
                match.required().value(),
                replace.required().value());
    }
}
