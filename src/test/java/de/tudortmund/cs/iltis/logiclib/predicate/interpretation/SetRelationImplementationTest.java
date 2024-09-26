package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import de.tudortmund.cs.iltis.utils.collections.Tuple;
import org.junit.Test;

public class SetRelationImplementationTest {
    // TODO (GG): Move to `TupleTest`
    @Test
    public void testTupleHashCode() {
        assertEquals(tupleOf(1, 2).hashCode(), tupleOf(1, 2).hashCode());
        assertNotEquals(tupleOf(1, 2).hashCode(), tupleOf(1, 3).hashCode());
    }

    @Test
    public void testEquals() {
        SetRelationImplementation<Integer> rel1, rel2;
        rel1 = new SetRelationImplementation<Integer>();
        rel2 = new SetRelationImplementation<Integer>();

        rel1.add(tupleOf(1, 2));
        assertNotEquals(rel1, rel2);
        rel2.add(tupleOf(1, 2));
        assertEquals(rel1, rel2);
        rel2.add(tupleOf(3, 4));
        assertNotEquals(rel1, rel2);
    }

    private Tuple<Integer> tupleOf(Integer... values) {
        return new Tuple<Integer>(values);
    }
}
