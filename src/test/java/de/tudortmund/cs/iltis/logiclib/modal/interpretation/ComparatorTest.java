package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.satisfiability.tableau.ModalTableauTikzWriter;
import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import junit.framework.TestCase;
import org.junit.Test;

public class ComparatorTest extends TestCase {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    public ComparatorTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
        True = new True();
        False = new False();
    }

    @Test
    public void testAtomic() {
        assertTrue(Comparator.equivalent(A, A).test());
        assertFalse(Comparator.equivalent(A, A.not()).test());
        assertFalse(Comparator.equivalent(True, False).test());
        assertTrue(Comparator.equivalent(True, False.not()).test());
    }

    @Test
    public void testSimplePseudoPropositional() {
        ModalFormula phi;
        phi = A.and(B).or(A.not().and(B.not()), True.diamond());
        assertTrue(Comparator.satisfiable(phi).test());
        phi = A.or(B).and(A.not().or(B.not()), True.diamond());
        assertTrue(Comparator.satisfiable(phi).test());
    }

    @Test
    public void testSimpleModal() {
        ModalFormula phi;
        phi = A.diamond().and(ModalFormula.BOTTOM);
        assertFalse(Comparator.satisfiable(phi).test());
        phi = A.diamond().and(B.and(B.not()));
        assertFalse(Comparator.satisfiable(phi).test());
        phi = A.diamond();
        assertTrue(Comparator.equivalent(phi, phi).test());
        phi = A.box();
        assertTrue(Comparator.equivalent(phi, phi).test());

        assertTrue(Comparator.satisfiable(A.diamond().and(A.not().diamond())).test());
        assertTrue(
                Comparator.satisfiable((A.or(B)).diamond().and((A.not().and(B.not())).diamond()))
                        .test());

        phi = (A.and(A.not().box())).and(A.not().or(A.diamond()));
        assertFalse(Comparator.satisfiable(phi).test());

        phi = (B.not().box().or(A)).and(B.diamond());
        assertTrue(Comparator.satisfiable(phi).test());

        phi = A.diamond().and(B.not().diamond().or(A.and(B).box()));
        assertTrue(Comparator.satisfiable(phi).test());

        phi = (A.and(B)).diamond().and(A.not().box().or(B.not().box()));
        assertFalse(Comparator.satisfiable(phi).test());
    }

    @Test
    public void testExerciseExample() {
        ModalFormula phi, psi;

        phi = A.implies(B).and(A.implies(C), True.box());
        psi = A.implies(B.and(C));
        assertTrue(Comparator.equivalent(phi, psi).test());

        phi = A.implies(B.box()).and(A.implies(B));
        psi = A.implies(B.box().and(B));
        assertTrue(Comparator.equivalent(phi, psi).test());

        phi = (A.implies(B.not()).and(A.implies(A.box())));
        assertTrue(Comparator.equivalent(phi, A.implies(B.not().and(A.box()))).test());
        assertFalse(Comparator.equivalent(phi, A.implies(B.not().and(A.diamond()))).test());
        assertFalse(Comparator.equivalent(phi, A.implies(B.not().and(A.diamond()))).test());
        assertFalse(Comparator.equivalent(phi, A.implies(B.not())).test());
        assertFalse(Comparator.equivalent(phi, A.implies(A.diamond())).test());

        try {
            phi = formulaReader.read("◇(K ∧ ☐A)");
            psi = formulaReader.read("(◇K ∧ ☐A)");
            assertFalse(Comparator.equivalent(phi, psi).test());

            phi = formulaReader.read("¬☐A → ◇(K ∧ ☐A)");
            psi = formulaReader.read("¬☐A → (◇K ∧ ☐A)");
            assertFalse(Comparator.equivalent(phi, psi).test());

            phi = formulaReader.read("(E ∧ ¬☐A) → ◇(K ∧ ☐A)");
            psi = formulaReader.read("(E ∧ ¬☐A) → (◇K ∧ ☐A)");
            assertFalse(Comparator.equivalent(phi, psi).test());

            phi = formulaReader.read("F → (A ∨ ◻F)");
            psi = formulaReader.read("A ∨ ¬A");
            assertFalse(Comparator.equivalent(phi, psi).test());
            // Example from issue "[CreateFormula/Modal] Wrong formula accepted":
            phi = formulaReader.read("◇(S ∧ ¬S)");
            psi = formulaReader.read("◇(S ∧ ☐¬S)");
            // assertFalse(Comparator.equivalent(phi, psi).test());

            // TODO: Improve perfomance, this one takes way too long:
            // phi = formulaReader.read("(E ∧ ¬☐A) → ¬(◇(K ∧ ☐A) ↔ ☐F)");
            // psi = formulaReader.read("(E ∧ ¬☐A) → ¬((◇K ∧ ☐A) ↔ ☐F)");
            // assertFalse(Comparator.equivalent(phi, psi).test());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void print() {
        ModalFormula phi = A.and(A.not()).or(B.implies(A.diamond()));
        ModalTableau tableau = new ModalTableau(phi);
        ModalTableauTikzWriter w = new ModalTableauTikzWriter();
        System.out.println(w.write(tableau));
    }

    private Variable A;
    private Variable B;
    private Variable C;

    private True True;
    private False False;
}
