package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.AnswerType;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.EquivalenceTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import org.junit.Test;

public class VampireMLSolverTest {

    private ModalFormula formula1, formula2;

    @Test
    public void testEquivalences() {
        formula1 = ModalFormula.parse("☐(L∨M∨P)∧¬☐M∧☐(¬L∨¬B)∧☐(M∨L∨R)∧☐(¬P∨¬R)∧◇B");
        formula2 = ModalFormula.parse("☐(L∨M∨P)∧¬☐M∧☐(L→¬B)∧☐(¬(M∨L)→R)∧☐(P→¬R)∧¬◇¬B");
        testEquivalence(AnswerType.INVALID);

        formula1 = ModalFormula.parse("☐¬A∧(◇C∨◇B)∧(◇◇¬D∨◇B)∧(◇C∨◇¬A)∧(◇◇¬D∨◇¬A)∧(☐A∨☐B) ");
        formula2 = ModalFormula.parse("☐¬A∧(◇C∨◇B)∧(◇C∨¬☐A)∧(◇◇¬D∨◇B)∧(◇◇¬D∨¬☐A)∧(☐A∨☐B)");
        testEquivalence(AnswerType.VALID);

        formula1 =
                ModalFormula.parse(
                        "¬(((E∧◇¬A)→((☐(¬K∨◇¬A)∧☐F)∨(◇(K∧☐A)∧◇¬F)))↔(¬(E∧¬☐A)∨¬(◇(K∧☐A)↔☐F)))");
        formula2 =
                ModalFormula.parse(
                        "¬(((E∧¬☐A)→((¬◇(K∧☐A)∧☐F)∨(◇(K∧☐A)∧¬☐F)))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        testEquivalence(AnswerType.VALID);

        Transformation nnfTransformation = EquivalenceTransformations.NEGATION_NORMALFORM;

        formula1 = ModalFormula.parse("¬((¬(E∧¬☐A)∨¬(◇(K∧☐A)↔☐F))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        formula2 =
                nnfTransformation.apply(
                        ModalFormula.parse("¬((¬(E∧¬☐A)∨¬(◇(K∧☐A)↔☐F))↔((E∧¬☐A)↔¬(◇(K∧☐A)↔☐F)))"));
        testEquivalence(AnswerType.INVALID);

        formula1 = ModalFormula.parse("¬(((E∧¬☐A)→¬((◇K∧◇☐A)↔☐F))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        formula2 =
                nnfTransformation.apply(
                        ModalFormula.parse("¬(((E∧¬☐A)→¬((◇K∧◇☐A)↔☐F))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))"));
        testEquivalence(AnswerType.VALID);
    }

    private void testEquivalence(AnswerType type) {
        VampireMLSolver solver = new VampireMLSolver();

        System.out.println("Formula 1: " + formula1);
        System.out.println("Formula 2: " + formula2);

        long start = System.currentTimeMillis();

        Answer answer = solver.solveEquivalence(formula1, formula2);

        System.out.println(System.currentTimeMillis() - start + " ms");
        System.out.println("got \"" + answer.getType() + "\" on expected answer \"" + type + "\"");
        System.out.println(
                "----------------------------------------------------------------------------");
    }
}
