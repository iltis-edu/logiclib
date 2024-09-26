package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableInvalidSpecification;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "reference")
public class XmlTransformationReference
        extends Parsable<Transformation<ModalFormula>, TransformationRegistry<ModalFormula>> {
    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString name;

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString forPathChild;

    @Override
    protected Transformation<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> reg) {
        if (!reg.isPresent())
            throw new ParsableInvalidSpecification(name, "Missing transformation registry");

        if (forPathChild != null) {
            return reg.get()
                    .get(name.nonempty().value())
                    .forPath(
                            new TreePath()
                                    .child(Integer.parseInt(forPathChild.nonempty().value())));
        }

        return reg.get().get(name.nonempty().value());
    }
}
