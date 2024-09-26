package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AssertInverse;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ParsableModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "assertInverse")
public class XmlAssertInverse extends XmlTransformationTest {
    public XmlTransformationReference reference;

    @XmlJavaTypeAdapter(ParsableModalFormula.Adapter.class)
    public ParsableModalFormula input = new ParsableModalFormula();

    @Override
    protected TransformationTest<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new AssertInverse<ModalFormula>(reference.parse(context), input.required().value());
    }
}
