package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import java.util.Optional;
import org.junit.Test;

public class ClauseTest {

    public ClauseTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
        D = new Variable("D");
    }

    @Test
    public void compareTo() {
        DisjunctiveClause c1;
        DisjunctiveClause c2;

        c1 = new DisjunctiveClause(A.not());
        c2 = new DisjunctiveClause(A);
        assertEquals(-1, c1.compareTo(c2));

        c1 = new DisjunctiveClause(A);
        c2 = new DisjunctiveClause(A, B.not());
        assertEquals(-1, c1.compareTo(c2));
    }

    @Test
    public void equals() {
        DisjunctiveClause c1;
        DisjunctiveClause c2;

        c1 = new DisjunctiveClause(A, C.not());
        c2 = new DisjunctiveClause(C.not(), A, C.not());
        assertFalse(c1.equals(c2));
    }

    @Test
    public void isEquivalent() {
        DisjunctiveClause c1;
        DisjunctiveClause c2;

        c1 = new DisjunctiveClause(A, C.not());
        c2 = new DisjunctiveClause(C.not(), A, C.not());
        assertTrue(c1.isEquivalent(c2));

        DisjunctiveClauseSet s1, s2;

        s1 = new DisjunctiveClauseSet(c1, c2);
        s2 = new DisjunctiveClauseSet(c2, c1);
        assertEquals(s1, s2);
        // assertTrue(s1.isEquivalent(s2));
    }

    @Test
    public void resolve() {
        DisjunctiveClause c1, c2, c3, c4, resolved, expected;

        c1 = new DisjunctiveClause(A, B);
        c2 = new DisjunctiveClause(C, A.not());
        c3 = new DisjunctiveClause(B, C);
        c4 = new DisjunctiveClause(A.not(), B.not());
        expected = new DisjunctiveClause(C, B);
        try {
            resolved = c1.resolveWith(c2);
            assertTrue(expected.isEquivalent(resolved));

            Optional<DisjunctiveClause> opt = c1.isUniquelyResolvableWith(c2);
            assertTrue(opt.isPresent());
            assertTrue(expected.isEquivalent(opt.get()));

            opt = c1.isUniquelyResolvableWith(c3);
            assertFalse(opt.isPresent());

            opt = c1.isUniquelyResolvableWith(c4);
            assertFalse(opt.isPresent());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void resolventEquivalentToTop() {
        DisjunctiveClause c1, c2, resolved1, resolved2;

        c1 = new DisjunctiveClause(A, B.not());
        c2 = new DisjunctiveClause(A.not(), B);
        resolved1 = new DisjunctiveClause(A.not(), A);
        resolved2 = new DisjunctiveClause(B.not(), B);

        assertFalse(c1.isUniquelyResolvableWith(c2).isPresent());
        assertTrue(c1.canResolveWithTo(c2, resolved1));
        assertTrue(c1.canResolveWithTo(c2, resolved2));

        c1 = new DisjunctiveClause(A, B.not(), C);
        c2 = new DisjunctiveClause(A.not(), B, C, D.not());
        resolved1 = new DisjunctiveClause(A.not(), A, C, D.not());
        resolved2 = new DisjunctiveClause(B.not(), B, C, D.not());

        assertFalse(c1.isUniquelyResolvableWith(c2).isPresent());
        assertTrue(c1.canResolveWithTo(c2, resolved1));
        assertTrue(c1.canResolveWithTo(c2, resolved2));
    }

    @Test
    public void merge() {
        DisjunctiveClause c1 = new DisjunctiveClause(A, B);
        DisjunctiveClause c2 = new DisjunctiveClause(A, C.not());
        DisjunctiveClause expected = new DisjunctiveClause(A, B, C.not());

        DisjunctiveClause actual =
                c1.isUniquelyResolvableWith(c2).orElse(DisjunctiveClause.merge(c1, c2));
        assertEquals(expected, actual);
    }

    @Test
    public void cloning() {
        Literal l1 = new Literal(true, A);
        Literal l2 = new Literal(false, A);

        assertEquals(l1, l1.clone());
        assertEquals(l2, l2.clone());
        DisjunctiveClause c1 = new DisjunctiveClause(l1, l2);
        assertEquals(c1, c1.clone());
    }

    @Test
    public void hashCodeTest() {
        DisjunctiveClause c1, c2;
        c1 = new DisjunctiveClause(A.not(), B);
        c2 = new DisjunctiveClause(B, A.not());
        assertEquals(c1.hashCode(), c2.hashCode());

        c1 = new DisjunctiveClause(A.not(), C, B.not());
        c2 = new DisjunctiveClause(A.not(), B.not(), C);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    private Variable A;
    private Variable B;
    private Variable C;

    private Variable D;
}
