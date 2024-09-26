package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.AnswerType;

public class PrincessSolverTest {
    // All tests are intentionally commented out, since they need a running princess webserver,
    // which is not present when running mvn install
    // TODO: that

    private PrincessSolver princessSolver = PrincessSolver.getInstance();

    private AnswerType type;

    // @Test
    public void testValid() {
        type = AnswerType.VALID;
        testSolving("1");
        testSolving("forall x !(exists y E(x,y)) <-> forall x forall y !E(x,y)");
        testSolving("E(x) -> exists y E(y)");
        testSolving("E(f(x)) -> exists y E(f(y))");
    }

    // @Test
    public void testInvalid() {
        type = AnswerType.INVALID;
        testSolving("0");
        testSolving("forall x (!E(x) & exists y E(y))");
        testSolving("E(x,y) <-> forall y forall x E(y,x)");
        testSolving("E(f(x), y) <-> !E(f(x), y)");
        testSolving("¬∃ x (¬E(x,x) → ¬∃ y E(x,y)) <-> ¬∃ x (¬E(x,x) | ¬∃ y E(x,y))");
    }

    private void testSolving(String formulaString) {
        Formula formula = Formula.parse(formulaString);

        Answer answer = princessSolver.solve(formula);

        assert answer.getType() == type
                : " Wrong answer type: expected \""
                        + type
                        + "\", got \""
                        + answer.getType()
                        + "\" on formula \""
                        + formula
                        + "\"";
    }
}
