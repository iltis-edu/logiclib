package de.tudortmund.cs.iltis.logiclib.modal.io;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import org.junit.Before;
import org.junit.Test;

/**
 * For more error test cases for failed parings see {@link
 * de.tudortmund.cs.iltis.logiclib.predicate.io.DisjunctiveClauseReaderTest} and {@link
 * de.tudortmund.cs.iltis.logiclib.predicate.io.DisjunctiveClauseSetReaderTest} as the ANTLR grammar
 * is exactly the same.
 */
public class ClauseSetParserTest {

    private Variable A;
    private Variable B;
    private Variable C;

    @Before
    public void init() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
    }

    @Test
    public void testClauses() {
        try {
            assertTrue(clause().isEquivalent(parseClause("{}")));
            assertTrue(clause(A).isEquivalent(parseClause("{A}")));
            assertTrue(clause(B).isEquivalent(parseClause("{B}")));
            assertTrue(clause(A.not(), B).isEquivalent(parseClause("{B, ¬A}")));
            assertTrue(clause(A.not(), B).isEquivalent(parseClause("{B, ¬A}")));
            assertTrue(clause(A.not(), B, C.not()).isEquivalent(parseClause("{¬C,B,¬A}")));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testClausesError() {
        try {
            parseClause("");
            fail();
        } catch (IncorrectParseInputException e) {
            assertTrue(clause().isEquivalent(e.getFaultMapping().getOutput()));
        }
    }

    @Test
    public void testClauseSets() {
        try {
            DisjunctiveClause c1 = new DisjunctiveClause(A);
            DisjunctiveClause c2 = new DisjunctiveClause(A.not(), C.not(), B);
            assertEquals(new DisjunctiveClauseSet(), parseClauseSet("{}"));
            assertEquals(new DisjunctiveClauseSet(c1), parseClauseSet("{{A}}"));
            assertEquals(new DisjunctiveClauseSet(c1, c2), parseClauseSet("{{A}, {!A,!C,B}}"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testClauseSetsError() {
        DisjunctiveClause c1 = new DisjunctiveClause(A);
        DisjunctiveClause c2 = new DisjunctiveClause(A.not(), C.not(), B);

        try {
            parseClauseSet("");
            fail();
        } catch (IncorrectParseInputException e) {
            assertEquals(new DisjunctiveClauseSet(), e.getFaultMapping().getOutput());
        }

        try {
            parseClauseSet("{A}");
            fail();
        } catch (IncorrectParseInputException e) {
            assertEquals(new DisjunctiveClauseSet(c1), e.getFaultMapping().getOutput());
        }

        try {
            parseClauseSet("{A} {B,!A,!C}");
            fail();
        } catch (IncorrectParseInputException e) {
            assertEquals(new DisjunctiveClauseSet(c1, c2), e.getFaultMapping().getOutput());
        }

        try {
            parseClauseSet("{{A} {B,!A,!C}}");
            fail();
        } catch (IncorrectParseInputException e) {
            assertEquals(new DisjunctiveClauseSet(c1, c2), e.getFaultMapping().getOutput());
        }
    }

    protected DisjunctiveClause clause(ModalFormula... formulae) {
        return new DisjunctiveClause(formulae);
    }

    protected DisjunctiveClause parseClause(String clause) {
        return DisjunctiveClause.parse(clause, ModalReaderProperties.createDefault());
    }

    protected DisjunctiveClauseSet parseClauseSet(String clause) {
        return DisjunctiveClauseSet.parse(clause, ModalReaderProperties.createDefault());
    }
}
