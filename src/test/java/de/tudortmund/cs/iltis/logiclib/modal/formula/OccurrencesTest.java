package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Set;
import org.junit.Test;

public class OccurrencesTest {

    public OccurrencesTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
    }

    @Test
    public void test() {
        ModalFormula phi = A.not().and(B, A, A.not());
        Set<TreePath> paths;

        paths = phi.getAllOccurrences(C);
        assertEquals(0, paths.size());
        paths = phi.getAllOccurrences(B);
        assertEquals(1, paths.size());
        paths = phi.getAllOccurrences(A);
        assertEquals(3, paths.size());
        paths = phi.getAllOccurrences(A.not());
        assertEquals(2, paths.size());
        assertTrue(paths.contains(new TreePath("-0")));
        assertTrue(paths.contains(new TreePath("-3")));

        try {
            ModalFormula psi =
                    ModalFormula.parse(
                            "!A | (!A -> (B & !!A))", ModalReaderProperties.createDefault());
            paths = psi.getAllOccurrences(A.not());
            assertEquals(3, paths.size());
            assertTrue(paths.contains(new TreePath("-0")));
            assertTrue(paths.contains(new TreePath("-1-0")));
            assertTrue(paths.contains(new TreePath("-1-1-1-0")));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private Variable A;
    private Variable B;
    private Variable C;
}
