package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import org.junit.Test;

/** This is a high-level test for the `tryToParse` in `Formula`. */
public class FormulaTryToParseTest {
    public FormulaTryToParseTest() {
        x = new Variable("x");
        A = new RelationAtom("A", false, x);
        B = new RelationAtom("B", false, x);
    }

    @Test
    public void successfulTryToParseOnFormula() {
        assertEquals(Optional.of(A.not().implies(B)), Formula.tryToParse("¬A(x) → B(x)"));
        assertEquals(Optional.of(A.implies(B).not()), Formula.tryToParse("¬(A(x) → B(x))"));
        assertEquals(Optional.of(A.exists(x)), Formula.tryToParse("∃x A(x)"));
    }

    @Test
    public void unsuccessfulTryToParseOnNonformula() {
        assertEquals(Optional.empty(), Formula.tryToParse("¬A(x) →"));
        assertEquals(Optional.empty(), Formula.tryToParse("¬A(x) → B(x))"));
        assertEquals(Optional.empty(), Formula.tryToParse("∃∃x A(x)"));
        assertEquals(Optional.empty(), Formula.tryToParse("A(x) ∧ ∧ B(x)"));
    }

    private final RelationAtom A;
    private final RelationAtom B;
    private final Variable x;
}
