package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.pattern;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.ParsablePredicatePattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.ComplexPatternSupplier;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Optional;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public abstract class XmlParsablePredicatePattern
        extends Parsable<TreePattern<TermOrFormula>, TransformationRegistry<TermOrFormula>> {
    @XmlAttribute
    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString javaReference;

    @XmlValue ParsableString inputString = new ParsableString();

    protected TreePattern<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> context) {
        if (javaReference != null && javaReference.value().equals("true")) {
            return ComplexPatternSupplier.getPatternById(inputString.value());
        }

        return new ParsablePredicatePattern(inputString.required().value()).value();
    }
}
