package de.tudortmund.cs.iltis.logiclib;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

public class TreePathTest extends TestCase {

    @Test
    public void testHierarchy() {
        TreePath root = new TreePath();
        TreePath c1 = new TreePath("-1");
        TreePath c13 = new TreePath("-1-3");
        TreePath c132 = new TreePath("-1-3-2");
        TreePath c134 = new TreePath("-1-3-4");
        TreePath c150 = new TreePath("-1-5-0");
        TreePath c2 = new TreePath("-2");

        assertTrue(root.isParentOf(c1));
        assertTrue(c1.isParentOf(c13));
        assertTrue(c13.isParentOf(c132));
        assertTrue(c13.isParentOf(c134));
        assertTrue(c1.isChildOf(root));
        assertTrue(c13.isChildOf(c1));
        assertTrue(c132.isChildOf(c13));
        assertTrue(c134.isChildOf(c13));

        assertFalse(root.isParentOf(c13));
        assertFalse(c1.isParentOf(c132));
        assertFalse(c1.isParentOf(c134));
        assertFalse(c132.isParentOf(c134));

        assertFalse(root.isAncestorOf(root));
        assertTrue(root.isAncestorOf(c1));
        assertTrue(root.isAncestorOf(c13));
        assertTrue(root.isAncestorOf(c132));
        assertTrue(root.isAncestorOf(c134));
        assertFalse(c1.isAncestorOf(c1));
        assertFalse(c132.isAncestorOf(c132));

        assertTrue(c132.isSiblingOf(c134));
        assertFalse(c132.isSiblingOf(c132));
        assertTrue(c1.isSiblingOf(c2));

        assertEquals(c132.getParent(), c134.getParent());
        assertEquals(c13, c132.getLowestCommonAncestor(c134));
        assertEquals(c1, c132.getLowestCommonAncestor(c150));
        assertEquals(root, c1.getLowestCommonAncestor(c2));

        assertEquals(c1, TreePath.getLowestCommonAncestor(collect(c1, c13)));
        assertEquals(c1, TreePath.getLowestCommonAncestor(collect(c13, c134, c150)));
        assertEquals(root, TreePath.getLowestCommonAncestor(collect(root, c134, root)));
        assertEquals(null, TreePath.getLowestCommonAncestor(Collections.emptyList()));
    }

    @Test
    public void testConcatenate() {
        TreePath p = new TreePath("-1-2");
        TreePath p1 = new TreePath("-1");
        TreePath p2 = new TreePath("-2");
        assertEquals(p, p1.concatenate(p2));
    }

    private static <T> Collection<T> collect(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) list.add(item);

        return list;
    }
}
