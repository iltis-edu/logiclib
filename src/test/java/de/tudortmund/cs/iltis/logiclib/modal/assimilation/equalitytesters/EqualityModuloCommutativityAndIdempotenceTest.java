package de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import org.junit.Test;

public class EqualityModuloCommutativityAndIdempotenceTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void simpleTest() throws Exception {
        try {
            ListSet<ModalFormula> Phi =
                    new ListSet<ModalFormula>(new EqualityModuloCommutativityAndIdempotence());
            ListSet<ModalFormula> Psi =
                    new ListSet<ModalFormula>(new EqualityModuloCommutativityAndIdempotence());

            Phi.add(formulaReader.read("A & B & A"));
            Psi.add(formulaReader.read("B & B & A"));
            assertTrue(Phi.equals(Psi));

            Phi.add(formulaReader.read("A & (B | C | C)"));
            Psi.add(formulaReader.read("A & (C | B)"));
            assertTrue(Phi.equals(Psi));

            Phi.add(formulaReader.read("A -> B"));
            Psi.add(formulaReader.read("B -> A"));
            assertFalse(Phi.equals(Psi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
