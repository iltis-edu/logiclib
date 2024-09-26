package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ParsableModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.AssertApplicationResult;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "assertApplicationResult")
public class XmlAssertApplicationResult extends XmlTransformationTest {
    @XmlJavaTypeAdapter(ParsableModalFormula.Adapter.class)
    public ParsableModalFormula input = new ParsableModalFormula();

    @XmlJavaTypeAdapter(ParsableModalFormula.Adapter.class)
    public ParsableModalFormula expect = new ParsableModalFormula();

    @Override
    protected TransformationTest<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new AssertApplicationResult(expect.required().value(), input.required().value());
    }
}
