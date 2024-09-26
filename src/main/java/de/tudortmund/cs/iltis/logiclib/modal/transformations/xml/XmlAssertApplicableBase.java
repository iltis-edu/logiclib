package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AssertApplicable;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ParsableModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.util.Optional;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlTransient
public abstract class XmlAssertApplicableBase extends XmlTransformationTest {
    @XmlJavaTypeAdapter(ParsableModalFormula.Adapter.class)
    public ParsableModalFormula input = new ParsableModalFormula();

    @Override
    protected TransformationTest<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new AssertApplicable<ModalFormula>(input.required().value(), expectApplicable());
    }

    public abstract boolean expectApplicable();
}
