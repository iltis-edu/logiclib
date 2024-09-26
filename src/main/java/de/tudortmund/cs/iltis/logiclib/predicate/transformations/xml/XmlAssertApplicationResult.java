package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.ParsablePredicateFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.AssertApplicationResult;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "assertApplicationResult")
public class XmlAssertApplicationResult extends XmlTransformationTest {
    @XmlJavaTypeAdapter(ParsablePredicateFormula.Adapter.class)
    public ParsablePredicateFormula input = new ParsablePredicateFormula();

    @XmlJavaTypeAdapter(ParsablePredicateFormula.Adapter.class)
    public ParsablePredicateFormula expect = new ParsablePredicateFormula();

    @XmlJavaTypeAdapter(ParsablePredicateFormula.Adapter.class)
    public ParsablePredicateFormula newFormula = new ParsablePredicateFormula();

    @XmlJavaTypeAdapter(ParsableString.Adapter.class)
    public ParsableString newVariable = new ParsableString();

    @Override
    protected TransformationTest parse(Optional<TransformationRegistry<TermOrFormula>> context) {
        if (newVariable.isPresent()) {
            return new AssertApplicationResult(
                    expect.required().value(),
                    input.required().value(),
                    Term.parse(newVariable.value()));
        }

        if (newFormula.isPresent()) {
            return new AssertApplicationResult(
                    expect.required().value(), input.required().value(), newFormula.value());
        }

        return new AssertApplicationResult(expect.required().value(), input.required().value());
    }
}
