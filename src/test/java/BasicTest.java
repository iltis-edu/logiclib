import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.EquivalenceTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import junit.framework.TestCase;
import org.junit.Test;

public class BasicTest extends TestCase {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void testFancyFeatures() throws Exception {
        // Extended and improved formula parsing ====================
        ModalFormula phi = formulaReader.read("¬¬¬(◇A ∨ ¬¬☐◇A)");
        ModalFormula phi_alt = formulaReader.read("!!!(dia A | !!box dia A)");
        ModalFormula psi = formulaReader.read("☐¬A ∧ ◇☐¬A");

        // New framework for equivalence transformations ============
        // transform whole formula ------------------------
        ModalFormula chi, chi_alt;
        Transformation trans = EquivalenceTransformations.NEGATION_NORMALFORM;
        chi = trans.apply(phi);
        chi_alt = trans.apply(phi_alt);
        assertEquals(psi, chi);
        assertEquals(psi, chi_alt);

        // transform only right disjunct ------------------
        // (retrieve subformula by path)
        TreePath p1 = new TreePath().down(3).right().down();
        TreePath p2 = new TreePath("-0-0-0-1-0");
        assertEquals(p1, p2);

        ModalFormula phiSub = phi.retrieve(p1);
        assertEquals(phiSub, formulaReader.read("¬☐◇A"));
        Transformation transP1 = trans.forPath(p1);
        ModalFormula mu = transP1.apply(phi);
        ModalFormula muSub = mu.retrieve(p1);
        assertEquals(muSub, formulaReader.read("◇☐¬A"));

        // some infos ===============================================
        System.out.println("phi = " + phi);
        assertEquals(phi.getDepth(), 8);
        assertEquals(phi.getModalDepth(), 2);
        // assertEquals(phi.getSize(), 13); // TODO: repair getSize()
    }
}
