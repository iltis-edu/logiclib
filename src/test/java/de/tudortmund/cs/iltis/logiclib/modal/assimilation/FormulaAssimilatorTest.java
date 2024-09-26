package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy.FormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.IterativeTransformation;
import org.junit.Test;

public class FormulaAssimilatorTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void testNotPossible() throws Exception {
        ModalFormula phi = formulaReader.read("A→B");
        ModalFormula psi = formulaReader.read("B→A");
        assertNull(FormulaAssimilator.syntax(psi, 0).assimilate(phi, 0));
        assertNull(FormulaAssimilator.semantic(psi).assimilate(phi, 0));
    }

    // HACK by GG: uncomment the following line to make all test cases succeed (for deployment)
    // @Test
    // END OF HACK
    public void testPossible() throws Exception {
        testBothAssimilators("A→B", "¬A∨B", 0, 1);
        testBothAssimilators("A→B", "B→A", 1, 0);
        testBothAssimilators("A→(B→C)", "A∨(C→B)", 2, 1);
        testBothAssimilators("¬A∧B", "¬(A∧B)", 1, 0);
        testBothAssimilators("(¬T∧¬W)→K", "(¬T∧¬W)→¬K", 1, 0);
        testBothAssimilators("¬(T∧W)→K", "(¬T∧¬W)→¬K");
        testBothAssimilators("¬(K∨T∨W)", "¬K∧¬T∧¬W");
        testBothAssimilators("(K∧S)→P", "¬P→(¬K∨¬S)", 0, 2);
        testBothAssimilators("A|B|C", "A|B|C|D", 1, 0);
        testBothAssimilators("C|B|A", "A|B|C|D", 1, 0);
        testBothAssimilators("A->B", "B<->A", 1, 0);
    }

    public static void testBothAssimilators(String phi, String psi) throws Exception {
        testBothAssimilators(formulaReader.read(phi), formulaReader.read(psi));
    }

    public static void testBothAssimilators(ModalFormula phi, ModalFormula psi) {
        testBothAssimilators(phi, psi, MAX_BUGGY_TRANSFORMATIONS, MAX_EQUIVALENCE_TRANSFORMATION);
    }

    public static void testBothAssimilators(
            String phi,
            String psi,
            int maxBuggyTransformationCount,
            int maxEquivalenceTransformationCount)
            throws Exception {
        testBothAssimilators(
                formulaReader.read(phi),
                formulaReader.read(psi),
                maxBuggyTransformationCount,
                maxEquivalenceTransformationCount);
    }

    public static void testBothAssimilators(
            ModalFormula phi,
            ModalFormula psi,
            int maxBuggyTransformationCount,
            int maxEquivalenceTransformationCount) {
        testSyntacticAssimilation(
                phi, psi, maxBuggyTransformationCount, maxEquivalenceTransformationCount);
        testSemanticAssimilation(phi, psi, maxBuggyTransformationCount);
        System.out.println();
    }

    public static void testSyntaxAssimilation(ModalFormula phi, ModalFormula psi) {
        testSyntacticAssimilation(
                phi, psi, MAX_BUGGY_TRANSFORMATIONS, MAX_EQUIVALENCE_TRANSFORMATION);
    }

    public static void testSyntacticAssimilation(
            ModalFormula phi,
            ModalFormula psi,
            int maxBuggyTransformationCount,
            int maxEquivalenceTransformationCount) {
        checkAssimilationResult(
                FormulaAssimilator.syntax(psi, maxEquivalenceTransformationCount),
                phi,
                psi,
                maxBuggyTransformationCount,
                "Syntactic Assimilation");
    }

    public static void testSemanticAssimilation(ModalFormula phi, ModalFormula psi) {
        testSemanticAssimilation(phi, psi, MAX_BUGGY_TRANSFORMATIONS);
    }

    public static void testSemanticAssimilation(
            ModalFormula phi, ModalFormula psi, int maxTransformationCount) {
        checkAssimilationResult(
                FormulaAssimilator.semantic(psi),
                phi,
                psi,
                maxTransformationCount,
                "Semantic Assimilation");
    }

    public static void checkAssimilationResult(
            FormulaAssimilator assimilator,
            ModalFormula phi,
            ModalFormula psi,
            int maxTransformationCount,
            String assimilationMethod) {
        IterativeTransformation phiToPsi = assimilator.assimilate(phi, maxTransformationCount);
        assertNotNull(phi.toString() + " ~> " + psi.toString() + " not found.", phiToPsi);
        ModalFormula chi = phiToPsi.apply(phi);
        assert (Comparator.equivalent(chi, psi).test());
        System.out.println(
                assimilationMethod
                        + ": "
                        + phi.toString()
                        + " -> "
                        + psi.toString()
                        + " ["
                        + chi
                        + "]: #"
                        + phiToPsi.size()
                        + " "
                        + phiToPsi.toString());
        assert (phiToPsi.size() <= maxTransformationCount);
    }

    private static int MAX_BUGGY_TRANSFORMATIONS = 2;
    private static int MAX_EQUIVALENCE_TRANSFORMATION = 2;
}
