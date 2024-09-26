package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

// TODO (GG): Remove this test if the refactoring of `Structure` will be used.
public class StructureTestDeprecated {
    Structure<Integer> A, B;

    @Test
    public void testEqualsOnlyUniverse() {
        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));
        assertEquals(A, B);
    }

    @Test
    public void testEqualsOneRelation() {
        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));

        A.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        B.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        assertEquals(A, B);

        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));

        A.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        B.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 8));
        assertNotEquals(A, B);

        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));

        A.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        A.addRelation(new RelationSymbol("S", 1, false), rangeSet(2, 7));
        B.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        assertNotEquals(A, B);
    }

    @Test
    public void testTwoRelations() {
        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));

        A.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        A.addRelation(new RelationSymbol("S", 1, false), rangeSet(1, 4));
        B.addRelation(new RelationSymbol("S", 1, false), rangeSet(1, 4));
        B.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        assertEquals(A, B);

        A = new Structure<Integer>(rangeUniverse(1, 10));
        B = new Structure<Integer>(rangeUniverse(1, 10));

        A.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        A.addRelation(new RelationSymbol("S", 1, false), rangeSet(1, 4));
        B.addRelation(new RelationSymbol("S", 1, false), rangeSet(0, 4));
        B.addRelation(new RelationSymbol("R", 1, false), rangeSet(2, 7));
        assertNotEquals(A, B);
    }

    private Universe<Integer> rangeUniverse(int min, int max) {
        return new SetUniverse<Integer>(range(min, max));
    }

    private SetRelationImplementation<Integer> rangeSet(int min, int max) {
        return new SetRelationImplementation<Integer>(toTupleSet(range(min, max)));
    }

    private Set<Integer> range(int min, int max) {
        return IntStream.rangeClosed(min, max).boxed().collect(Collectors.toSet());
    }

    private Set<Tuple<Integer>> toTupleSet(Set<Integer> set) {
        return set.stream().map(v -> new Tuple<Integer>(v)).collect(Collectors.toSet());
    }
}
