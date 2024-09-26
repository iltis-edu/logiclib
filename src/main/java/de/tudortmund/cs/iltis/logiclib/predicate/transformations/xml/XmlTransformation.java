package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
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
                AdornedTransformation<TermOrFormula>, TransformationRegistry<TermOrFormula>> {
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
        @XmlElementRef(type = XmlAssertInverse.class),
        @XmlElementRef(type = XmlAssertApplicable.class),
        @XmlElementRef(type = XmlAssertNotApplicable.class),
        @XmlElementRef(type = XmlAssertApplicationResult.class)
    })
    public List<XmlTransformation> tests;

    protected AdornedTransformation<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> context) {
        TreePath treePath;
        if (path.withDefault("").value().equals("")) {
            treePath = new TreePath();
        } else {
            treePath = new TreePath(path.value());
        }

        AdornedTransformation<TermOrFormula> adornedTransformation =
                new AdornedTransformation<TermOrFormula>(
                        name.required().value(),
                        parseTransformation(context, treePath),
                        parseList("tests", context));

        context.get()
                .add(adornedTransformation.getName(), adornedTransformation.getTransformation());
        if (groupId.isPresent()) {
            context.get().addToGroup(groupId.value(), adornedTransformation.getTransformation());
            adornedTransformation.setGroupName(groupId.value());
        }
        if (onlyPart.isPresent() && onlyPart.value().equals("true")) {
            adornedTransformation.setOnlyPart(true);
        }
        return adornedTransformation;
    }

    protected abstract Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> context, TreePath path);
}
