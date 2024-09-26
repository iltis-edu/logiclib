package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import org.junit.Test;

public class ValuationTest {

    public ValuationTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
    }

    @Test
    public void test() {
        Valuation v[] = new Valuation[8];

        v[0] = new Valuation();
        v[0].define(A, false);
        v[0].define(B, false);
        v[0].define(C, false);

        v[1] = v[0].clone();
        v[1].define(C, true);

        v[2] = v[0].clone();
        v[2].define(B, true);

        v[3] = v[1].clone();
        v[3].define(B, true);

        v[4] = v[0].clone();
        v[4].define(A, true);

        v[5] = v[1].clone();
        v[5].define(A, true);

        v[6] = v[2].clone();
        v[6].define(A, true);

        v[7] = v[3].clone();
        v[7].define(A, true);

        Valuation vnext = v[0];
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[1], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[2], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[3], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[4], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[5], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[6], vnext);
        assertTrue(vnext.hasNext());
        vnext = vnext.next();
        assertEquals(v[7], vnext);
        assertFalse(vnext.hasNext());
    }

    private Variable A;
    private Variable B;
    private Variable C;
}
