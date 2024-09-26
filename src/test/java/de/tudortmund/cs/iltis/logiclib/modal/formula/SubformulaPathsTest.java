package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Set;
import org.junit.Test;

public class SubformulaPathsTest {

    @Test
    public void testGetSubformulaPathsRecursively() throws Exception {
        ModalFormula phi =
                ModalFormula.parse("A∨(B∧¬C)∨(C→(A↔B))", ModalReaderProperties.createDefault());
        Set<TreePath> paths = phi.getSubformulaPaths();
        assertEquals(11, paths.size());
        assertTrue(paths.contains(new TreePath()));
        assertTrue(paths.contains(new TreePath("-0")));
        assertTrue(paths.contains(new TreePath("-1")));
        assertTrue(paths.contains(new TreePath("-1-0")));
        assertTrue(paths.contains(new TreePath("-1-1")));
        assertTrue(paths.contains(new TreePath("-1-1-0")));
        assertTrue(paths.contains(new TreePath("-2")));
        assertTrue(paths.contains(new TreePath("-2-0")));
        assertTrue(paths.contains(new TreePath("-2-1")));
        assertTrue(paths.contains(new TreePath("-2-1-0")));
        assertTrue(paths.contains(new TreePath("-2-1-1")));
    }
}
