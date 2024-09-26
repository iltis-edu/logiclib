package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.AnswerType;

public class PLSolvingTest {

    // @Test
    public void testPLSolving() {
        VampirePLSolver vampirePLSolver = VampirePLSolver.getInstance();
        Z3PLSolver z3PLSolver = Z3PLSolver.getInstance();

        ModalFormula unsatFormula = ModalFormula.parse("X & !Y & (!X | (X -> Y))");
        Answer vampireUnsatAnswer = vampirePLSolver.solve(unsatFormula);
        assert vampireUnsatAnswer.getType() == AnswerType.UNSATISFIABLE;
        Answer z3UnsatAnswer = z3PLSolver.solve(unsatFormula);
        assert z3UnsatAnswer.getType() == AnswerType.UNSATISFIABLE;

        ModalFormula satFormula = ModalFormula.parse("X & (Y | (!(X <=> Y)))");
        Answer vampireSatAnswer = vampirePLSolver.solve(satFormula);
        assert vampireSatAnswer.getType() == AnswerType.SATISFIABLE;
        Answer z3SatAnswer = z3PLSolver.solve(satFormula);
        assert z3SatAnswer.getType() == AnswerType.SATISFIABLE;
        assert !z3SatAnswer.getSatisfyingAssignments().isEmpty();
        System.out.println(
                "sat assignment: " + z3SatAnswer.getSatisfyingAssignments().get(0).toString());
    }

    // @Test
    public void testPLSolvingWithSmtString() {
        VampirePLSolver vampirePLSolver = VampirePLSolver.getInstance();
        Z3PLSolver z3PLSolver = Z3PLSolver.getInstance();

        String unsatString =
                "(declare-const x_0 Bool)\\n"
                        + "(declare-const x_1 Bool)\\n"
                        + "(assert (and x_0 (not x_1) (or (not x_0) (=> x_0 x_1))))";
        Answer vampireUnsatAnswer = vampirePLSolver.solveSmtString(unsatString, 1, 1);
        assert vampireUnsatAnswer.getType() == AnswerType.UNSATISFIABLE;
        Answer z3UnsatAnswer = z3PLSolver.solveSmtString(unsatString, 1000);
        assert z3UnsatAnswer.getType() == AnswerType.UNSATISFIABLE;

        String satString =
                "(declare-const x_0 Bool)\\n"
                        + "(declare-const x_1 Bool)\\n"
                        + "(declare-const x_2 Bool)\\n"
                        + "(declare-const x_3 Bool)\\n"
                        + "(assert (and x_0 (or x_1 (not (= x_2 x_3)))))";
        Answer vampireSatAnswer = vampirePLSolver.solveSmtString(satString, 1, 1);
        assert vampireSatAnswer.getType() == AnswerType.SATISFIABLE;
        Answer z3SatAnswer = z3PLSolver.solveSmtString(satString, 1000);
        assert z3SatAnswer.getType() == AnswerType.SATISFIABLE;
        assert !z3SatAnswer.getSatisfyingAssignments().isEmpty();
        System.out.println(
                "sat assignment: " + z3SatAnswer.getSatisfyingAssignments().get(0).toString());
    }
}
