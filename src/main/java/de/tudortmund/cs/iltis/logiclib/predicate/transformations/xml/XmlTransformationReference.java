package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
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
        extends Parsable<Transformation<TermOrFormula>, TransformationRegistry<TermOrFormula>> {
    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString name;

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString forPathChild;

    @Override
    protected Transformation<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> registry) {
        if (!registry.isPresent()) {
            throw new ParsableInvalidSpecification(name, "Missing transformation registry");
        }

        if (forPathChild != null) {
            try {
                return registry.get()
                        .get(name.nonempty().value())
                        .forPath(
                                new TreePath()
                                        .child(Integer.parseInt(forPathChild.nonempty().value())));
            } catch (NumberFormatException e) {
                throw new ParsableInvalidSpecification(
                        name, "'Pathchild' must be a positive integer");
            }
        }
        return registry.get().get(name.nonempty().value());
    }
}
