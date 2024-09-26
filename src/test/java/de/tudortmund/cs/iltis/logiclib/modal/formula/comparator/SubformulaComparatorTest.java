package de.tudortmund.cs.iltis.logiclib.modal.formula.comparator;

import static junit.framework.TestCase.assertTrue;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import java.util.Comparator;
import org.junit.Test;

public class SubformulaComparatorTest {
    private Variable A;
    private Variable B;
    private Variable C;

    public SubformulaComparatorTest() {
        this.A = new Variable("A");
        this.B = new Variable("B");
        this.C = new Variable("C");
    }

    @Test
    public void simple() {
        ModalFormula phi = (C.and(A, B)).implies(A.not().or(B.and(B.not())));
        SubformulaComparator comp = new SubformulaComparator(phi);

        assertLowerThan(comp, A, B);
        assertLowerThan(comp, A, C);
        assertLowerThan(comp, A, C.and(A, B));
        assertLowerThan(comp, A, A.not());
        assertLowerThan(comp, B, B.not());
        assertLowerThan(comp, B.not(), B.and(B.not()));
        assertLowerThan(comp, C.and(A, B), phi);
    }

    private void assertLowerThan(
            Comparator<ModalFormula> comp, ModalFormula left, ModalFormula right) {
        assertTrue(comp.compare(left, right) < 0);
    }
}
