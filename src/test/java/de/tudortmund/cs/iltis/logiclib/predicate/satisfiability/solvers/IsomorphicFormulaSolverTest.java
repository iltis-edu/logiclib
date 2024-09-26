package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.AnswerType;
import java.util.LinkedList;
import org.junit.Test;

public class IsomorphicFormulaSolverTest {

    @Test
    public void testIsomorphicFormulas() {
        type = AnswerType.VALID;

        testSolving("forall x forall y E(x,y)", "forall x forall y E(x,y)");
        testSolving("forall x forall y E(x,y)", "forall u forall v E(u,v)");
        testSolving("forall x forall y (E(x,y) | E(y,x))", "forall u forall v (E(v,u) | E(u,v))");
        testSolving("forall x forall y E(x,y)", "forall x forall y E(x,y)");
        testSolving(
                "forall x forall y (E(x,y) | (forall x E(y,x)))",
                "forall u forall v ((forall u E(v,u)) | E(u,v))");
        testSolving(
                "forall x forall y (E(x,y) | (forall z E(y,x)))",
                "forall u forall v ((forall w E(v,u)) | E(u,v))");
        testSolving("!E(x,y) & E(y,x)", "E(v,u) & !E(u,v) & E(v,u)");
        testSolving("!E(x,y) & E(y,x)", "E(v,u) & !E(u,v)");

        testSolving(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))",
                "forall x forall y forall z((¬(y=z) ∧ N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,z) ∨ L(v,y))))");
        testSolving(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))",
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(z=y) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(x=v) ∧ (L(v,y) ∨ L(v,z))))");
        testSolving(
                "∃ z ∃ y ∃ x (E(y,x) ∧ E(x,z) ∧ E(z,y) ∧ ¬y=x ∧ ¬y=z ∧ ¬z=x)",
                "exists v exists u exists w (E(v,u) ∧ E(u,w) ∧ E(w,v) ∧ !v=u ∧ !v=w ∧ !w=u)");

        // 5-Circle in directed graph
        testSolving(
                "∃ v ∃ w ∃ x ∃ y ∃ z (E(v,w) ∧ E(w,x) ∧ E(x,y) ∧ E(y,z) ∧ E(z,v) ∧ ¬v=w ∧ ¬v=x ∧ ¬v=y ∧ ¬v=z ∧ ¬w=x ∧ ¬w=y ∧ ¬w=z ∧ ¬x=y ∧ ¬x=z ∧ ¬y=z) ",
                "∃ a ∃ b ∃ c ∃ d ∃ e (¬b=a ∧ ¬c=a ∧ ¬d=a ∧ ¬e=a ∧ ¬c=b ∧ ¬d=b ∧ ¬e=b ∧ ¬d=c ∧ ¬e=c ∧ ¬e=d ∧ E(a,b) ∧ E(b,c) ∧ E(c,d) ∧ E(d,e) ∧ E(e,a)) ");
    }

    @Test
    public void testNonIsomorphicFormulas() {
        type = AnswerType.NO_DECISION;

        testSolving("forall x forall y E(x,y)", "forall x forall x E(x,y)");
        testSolving("forall x forall y E(x,y)", "forall u exists v E(u,v)");
        testSolving("forall x forall y (E(x,y) | E(x,y))", "forall u forall v (E(v,u) | E(u,v))");
        testSolving("forall x forall y E(x,y)", "forall x exists y E(x,y)");
        testSolving(
                "forall x forall y (E(x,y) & (forall x E(y,x)))",
                "forall u forall v ((forall u E(v,u)) | E(u,v))");
        testSolving(
                "forall x forall y (E(x,y) | (forall z E(y,x)))",
                "forall u forall v ((forall u E(v,u)) | E(u,v))");
        testSolving("!E(x,y) & E(y,x)", "E(v,u) & !E(u,v) & E(u,u)");
        testSolving("!E(x,y) & E(y,x)", "E(z,u) & !E(u,v)");

        testSolving(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))",
                "forall z forall x forall y((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))");

        // not isomorphic because of one extra negation
        testSolving(
                "∃ v ∃ w ∃ x ∃ y ∃ z (E(v,w) ∧ E(w,x) ∧ E(x,y) ∧ E(y,z) ∧ E(z,v) ∧ ¬v=w ∧ ¬v=x ∧ ¬v=y ∧ ¬v=z ∧ ¬w=x ∧ ¬w=y ∧ ¬w=z ∧ ¬x=y ∧ ¬x=z ∧ ¬y=z) ",
                "∃ a ∃ b ∃ c ∃ d ∃ e (¬¬(b=a) ∧ ¬c=a ∧ ¬d=a ∧ ¬e=a ∧ ¬c=b ∧ ¬d=b ∧ ¬e=b ∧ ¬d=c ∧ ¬e=c ∧ ¬e=d ∧ E(a,b) ∧ E(b,c) ∧ E(c,d) ∧ E(d,e) ∧ E(e,a)) ");
    }

    private AnswerType type;

    private void testSolving(String formulaString1, String formulaString2) {
        FormulaReader reader = new FormulaReader();

        Formula formula1 = reader.read(formulaString1);
        Formula formula2 = reader.read(formulaString2);

        IsomorphicFormulaSolver solver = new IsomorphicFormulaSolver();
        long start = System.currentTimeMillis();
        AnswerType answerType =
                solver.solveEquivalenceUnderConstraints(formula1, formula2, new LinkedList<>())
                        .getType();
        System.out.println("took " + (System.currentTimeMillis() - start) + " ms");

        assert answerType == type;
    }
}
