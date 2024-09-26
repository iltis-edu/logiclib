package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AssertApplicable;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.ParsablePredicateFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import java.util.Optional;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlTransient
public abstract class XmlAssertApplicableBase extends XmlTransformationTest {
    @XmlJavaTypeAdapter(ParsablePredicateFormula.Adapter.class)
    public ParsablePredicateFormula input = new ParsablePredicateFormula();

    @Override
    protected TransformationTest parse(Optional<TransformationRegistry<TermOrFormula>> context) {
        return new AssertApplicable<TermOrFormula>(input.required().value(), expectApplicable());
    }

    public abstract boolean expectApplicable();
}
