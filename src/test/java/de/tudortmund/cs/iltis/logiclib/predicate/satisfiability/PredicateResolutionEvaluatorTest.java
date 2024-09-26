package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause.DisjunctiveClauseReader;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.base.mgu.MGUResult;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationLog;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationState;
import de.tudortmund.cs.iltis.utils.explainedresult.ExplainedResult;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Optional;
import org.junit.Test;

public class PredicateResolutionEvaluatorTest {

    @Test
    public void test() {
        Variable a = new Variable("a");
        Variable b = new Variable("b");

        FunctionTerm functionF = new FunctionTerm(new FunctionSymbol("f", 1, false), b);

        Negation relationP = new Negation(new RelationAtom(new RelationSymbol("P", 1, false), a));
        RelationAtom relationPf = new RelationAtom(new RelationSymbol("P", 1, false), functionF);
        RelationAtom relationQ = new RelationAtom(new RelationSymbol("Q", 1, false), b);

        DisjunctiveClause firstClause = new DisjunctiveClause();
        firstClause.add(relationP);

        DisjunctiveClause secondClause = new DisjunctiveClause();
        secondClause.add(relationPf);

        ListSet<DisjunctiveClause> newClauses = new ListSet<>();
        boolean exceptionThrown = false;
        try {
            newClauses = PredicateResolutionEvaluator.checkResolution(firstClause, secondClause);
        } catch (NotResolvableException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        Substitution firstSubstitution = new Substitution(a, functionF);
        try {
            newClauses =
                    PredicateResolutionEvaluator.checkResolution(
                            firstClause, secondClause, firstSubstitution, firstSubstitution);
        } catch (NotResolvableException e) {
        }
        assertTrue(newClauses.contains(new DisjunctiveClause()));

        firstClause.add(relationQ);
        try {
            newClauses =
                    PredicateResolutionEvaluator.checkResolution(
                            firstClause, secondClause, firstSubstitution, firstSubstitution);
        } catch (NotResolvableException e) {
        }
        assertTrue(newClauses.contains(new DisjunctiveClause(relationQ)));
        assertTrue(newClauses.size() == 1);
    }

    @Test
    public void testTrueAndFalse() {
        Formula test = new True();
        DisjunctiveClauseSet clauses = new DisjunctiveClauseSet(test);
        assertTrue(clauses.equals(new DisjunctiveClauseSet()));

        test = new False();
        clauses = new DisjunctiveClauseSet(test);
        assertTrue(clauses.equals(new DisjunctiveClauseSet(new DisjunctiveClause())));
    }

    @Test
    public void testResolution() {
        try {
            DisjunctiveClauseReader reader = new DisjunctiveClauseReader();
            DisjunctiveClause clause1 = reader.read("{R(f(x)), P(x)}");
            DisjunctiveClause clause2 = reader.read("{!P(x), !P(f(a))}");
            DisjunctiveClause resolvent = reader.read("{!P(f(a)), R(f(x))}");

            boolean isValid =
                    PredicateResolutionEvaluator.checkResolution(clause1, clause2, resolvent);
            assertTrue(isValid);
        } catch (IncorrectParseInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMGU() {
        Variable a = new Variable("a");
        Variable b = new Variable("b");
        Variable u = new Variable("u");
        Variable v = new Variable("v");
        Variable w = new Variable("w");
        Variable y = new Variable("y");
        Variable x = new Variable("x");
        Variable z = new Variable("z");

        // no unifier
        RelationAtom Pa = new RelationAtom(new RelationSymbol("P", 1, false), a);
        RelationAtom Ry = new RelationAtom(new RelationSymbol("R", 1, false), y);

        ListSet<RelationAtom> atoms = new ListSet<>();
        atoms.add(Pa);
        atoms.add(Ry);

        ExplainedResult<Optional<Substitution>, ComputationLog<ComputationState, MGUResult>>
                result = Substitution.calculateMGU(atoms);

        assertFalse(result.getResult().isPresent());

        // no unifier
        FunctionTerm fx = new FunctionTerm(new FunctionSymbol("f", 1, false), x);
        FunctionTerm gb = new FunctionTerm(new FunctionSymbol("g", 1, false), b);
        RelationAtom Pafx = new RelationAtom(new RelationSymbol("P", 2, false), a, fx);
        RelationAtom Pygb = new RelationAtom(new RelationSymbol("P", 2, false), y, gb);

        atoms = new ListSet<>();
        atoms.add(Pafx);
        atoms.add(Pygb);

        result = Substitution.calculateMGU(atoms);
        assertFalse(result.getResult().isPresent());

        // no unifier
        RelationAtom Pyx = new RelationAtom(new RelationSymbol("P", 2, false), y, x);

        atoms = new ListSet<>();
        atoms.add(Pafx);
        atoms.add(Pyx);

        result = Substitution.calculateMGU(atoms);
        assertFalse(result.getResult().isPresent());

        // unifier
        FunctionTerm fz = new FunctionTerm(new FunctionSymbol("f", 1, false), z);
        RelationAtom Rxay = new RelationAtom(new RelationSymbol("R", 3, false), x, a, y);
        RelationAtom Rfzub = new RelationAtom(new RelationSymbol("R", 3, false), fz, u, b);
        RelationAtom Rvuw = new RelationAtom(new RelationSymbol("R", 3, false), v, u, w);

        atoms = new ListSet<>();
        atoms.add(Rxay);
        atoms.add(Rfzub);
        atoms.add(Rvuw);

        result = Substitution.calculateMGU(atoms);
        assertUnifier(result.getResult().get(), atoms);
    }

    private void assertUnifier(Substitution unifier, ListSet<RelationAtom> atoms) {
        ListSet<TermOrFormula> unifiedAtoms = new ListSet<>();

        for (RelationAtom atom : atoms) {
            unifiedAtoms.add(unifier.apply(atom));
        }
        assertTrue(unifiedAtoms.size() == 1);
    }
}
