package de.tudortmund.cs.iltis.logiclib.modal.io;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.ConjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.DisjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.ImplicationPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.NegationPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.VariablePattern;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import org.junit.Test;

public class PatternParserTest {

    public PatternParserTest() {
        A = variable(anyName("A"));
        B = variable(anyName("B"));
        X = variable(anyName("X"));
        Y = variable(anyName("Y"));
        reader = new PatternReader();
    }

    @Test
    public void testBasic() {
        try {
            assertEquals(A, reader.read("A"));
            assertEquals(not(A), reader.read("!A"));
            assertEquals(not(not(A)), reader.read("!!A"));

            // ("$X → $Y", "¬$X ∨ $Y");
            assertEquals(implies(X, Y), reader.read("X -> Y"));
            assertEquals(or(not(X), Y), reader.read("!X | Y"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDisjunction() {
        try {
            assertEquals(or(A, B), reader.read("A | B"));
            assertEquals(or(A, not(B)), reader.read("A | !B"));
            assertEquals(or(not(A), B), reader.read("!A | B"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNestedConjunctions() {
        assertEquals(and(A, and(A, not(A)), B), reader.read("A & (A & !A) & B"));
        assertEquals(and(A, A, not(A), B), reader.read("A & A & !A & B"));
    }

    @Test
    public void testMixedJunctions() {
        try {
            reader.read("A & B | C");
            fail();
        } catch (Exception ignored) {
        }
        try {
            reader.read("A | B & C");
            fail();
        } catch (Exception ignored) {
        }
        try {
            reader.read("A | B & C");
            fail();
        } catch (Exception ignored) {
        }
        try {
            reader.read("A -> B & C");
            fail();
        } catch (Exception ignored) {
        }
    }

    private IndexedSymbol symbol(String name) {
        return new IndexedSymbol(name);
    }

    private AnyNamePattern<ModalFormula, IndexedSymbol> anyName(String name) {
        return new AnyNamePattern<>(new IndexedSymbol(name));
    }

    private VariablePattern variable(NamePattern<ModalFormula, IndexedSymbol> name) {
        return new VariablePattern(name);
    }

    private AnyPattern<ModalFormula> any(IndexedSymbol name) {
        return new AnyPattern<>(name);
    }

    private NegationPattern not(TreePattern<ModalFormula> subPattern) {
        return new NegationPattern(subPattern);
    }

    private ImplicationPattern implies(
            TreePattern<ModalFormula> premise, TreePattern<ModalFormula> conclusion) {

        return new ImplicationPattern(premise, conclusion);
    }

    private DisjunctionPattern or(TreePattern<ModalFormula>... patterns) {
        return new DisjunctionPattern(patterns);
    }

    private ConjunctionPattern and(TreePattern<ModalFormula>... patterns) {
        return new ConjunctionPattern(patterns);
    }

    private final VariablePattern A;
    private final VariablePattern B;
    private final VariablePattern X;
    private final VariablePattern Y;
    private final PatternReader reader;
}
