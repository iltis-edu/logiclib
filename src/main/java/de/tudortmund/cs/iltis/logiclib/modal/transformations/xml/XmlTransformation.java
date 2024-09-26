package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlTransient
public abstract class XmlTransformation
        extends Parsable<
                AdornedTransformation<ModalFormula>, TransformationRegistry<ModalFormula>> {
    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString name = new ParsableString();

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString path = new ParsableString();

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString onlyPart = new ParsableString();

    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString groupId = new ParsableString();

    @XmlElementWrapper(name = "tests")
    @XmlElementRefs({
        @XmlElementRef(type = XmlAssertApplicable.class),
        @XmlElementRef(type = XmlAssertNotApplicable.class),
        @XmlElementRef(type = XmlAssertApplicationResult.class),
        @XmlElementRef(type = XmlAssertInverse.class),
    })
    public List<XmlTransformation> tests;

    @Override
    protected AdornedTransformation<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        TreePath treepath;
        if (path.withDefault("").value().equals("")) {
            treepath = new TreePath();
        } else {
            treepath = new TreePath(path.value());
        }

        AdornedTransformation<ModalFormula> at =
                new AdornedTransformation<ModalFormula>(
                        name.required().value(),
                        parseTransformation(context, treepath),
                        parseList("tests", context));
        context.get().add(at.getName(), at.getTransformation());
        if (groupId.isPresent()) {
            context.get().addToGroup(groupId.value(), at.getTransformation());
            at.setGroupName(groupId.value());
        }
        if (onlyPart.isPresent() && onlyPart.value().equals("true")) {
            at.setOnlyPart(true);
        }
        return at;
    }

    protected abstract Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> context, TreePath path);
}
