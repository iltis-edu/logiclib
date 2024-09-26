package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import org.junit.Test;

public class ResolutionTest {

    public ResolutionTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
        D = new Variable("D");
    }

    @Test
    public void testTrueAndFalse() {
        ModalFormula test = new True();
        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet(test);
        assertTrue(clauses.equals(new DisjunctiveClauseSet()));
        Resolution resolution = new Resolution(clauses);
        assertFalse(resolution.isUnsatisfiable());

        test = new False();
        clauses = new DisjunctiveClauseSet(test);
        assertTrue(clauses.equals(new DisjunctiveClauseSet(DisjunctiveClause.EMPTY_CLAUSE)));
        resolution = new Resolution(clauses);
        assertTrue(resolution.isUnsatisfiable());
    }

    @Test
    public void doubleResolve() {
        DisjunctiveClause c1 = new DisjunctiveClause(A.not(), B);
        DisjunctiveClause c2 = new DisjunctiveClause(A.not(), B.not(), C);

        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet(c1, c2);

        Resolution resolution = new Resolution(clauses);
        assertTrue(resolution.addResolvent(c1.resolveWith(c2), c1, c2));
        assertFalse(resolution.addResolvent(c2.resolveWith(c1), c1, c2));
        assertFalse(resolution.addResolvent(c2.resolveWith(c1), c2, c1));
    }

    @Test
    public void resolve() {
        DisjunctiveClause c1, c2, c3, c4, resolved, expected;

        c1 = new DisjunctiveClause(A, B);
        c2 = new DisjunctiveClause(C, A.not());
        c3 = new DisjunctiveClause(A.not(), B.not());
        c4 = new DisjunctiveClause(A.not(), C.not(), D);
        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet(c1, c2, c3, c4);

        Resolution resolution = new Resolution(clauses);
        ResolutionStrategy rstrat = new DefaultResolutionStrategy();
        DisjunctiveClauseSet resolvents = rstrat.generateResolvents(resolution);

        assertEquals(3, resolvents.size());
        assertTrue(resolvents.contains(new DisjunctiveClause(B, C)));
        assertTrue(resolvents.contains(new DisjunctiveClause(B, C.not(), D)));
        assertTrue(resolvents.contains(new DisjunctiveClause(A.not(), D)));

        // TODO: This tests do not work because equals is not implemented as
        // isEquivalent method ({B,A} = {A,B} doesn't hold) and the
        // DefaultHashGraoh of Resolution works with equals when checking if a
        // clause is already in the graph
        //
        // resolvents = rstrat.generateResolvents(resolution);
        // assertEquals(2, resolvents.size());
        // assertTrue(resolvents.contains(new DisjunctiveClause(B,D)));
        // assertTrue(resolvents.contains(new DisjunctiveClause(A.not(),B,D)));

        // DisjunctiveClauseSet parentsOfBD = resolution.getParents(new
        // DisjunctiveClause(B, D));
        // assertEquals(2, parentsOfBD.size());
    }

    @Test
    public void unsatisfiable() {
        DisjunctiveClause c1, c2, c3, c4, c5, c6;
        c1 = new DisjunctiveClause(A, B);
        c2 = new DisjunctiveClause(C, A.not());
        c3 = new DisjunctiveClause(A.not(), B.not());
        c4 = new DisjunctiveClause(A.not(), C.not(), D);
        c5 = new DisjunctiveClause(B.not(), D);
        c6 = new DisjunctiveClause(D.not());
        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet(c1, c2, c3, c4, c5, c6);

        ResolutionAlgorithm alg = new ResolutionAlgorithm(clauses, new DefaultResolutionStrategy());
        alg.addAllResolvents();
        assertTrue(alg.getResolution().isUnsatisfiable());
    }

    private Variable A;
    private Variable B;
    private Variable C;
    private Variable D;
}
