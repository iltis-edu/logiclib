package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AssertInverse;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.ParsablePredicateFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "assertInverse")
public class XmlAssertInverse extends XmlTransformationTest {
    public XmlTransformationReference reference;

    @XmlJavaTypeAdapter(ParsablePredicateFormula.Adapter.class)
    public ParsablePredicateFormula input = new ParsablePredicateFormula();

    @Override
    protected TransformationTest<TermOrFormula> parse(
            Optional<TransformationRegistry<TermOrFormula>> context) {

        return new AssertInverse<TermOrFormula>(reference.parse(context), input.required().value());
    }
}
