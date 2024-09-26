package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.Map;
import org.junit.Test;

public class HornSatEvaluatorTest {

    @Test
    public void test() {
        Variable A = new Variable("A");
        Variable B = new Variable("B");
        Variable C = new Variable("C");
        Variable D = new Variable("D");
        Variable E = new Variable("E");

        ModalFormula psi = A.and(A.implies(B), A.implies(B));
        HornSatEvaluator horn = new HornSatEvaluator(psi);
        assertTrue(horn.isFormulaSatisfiable());
        assertMarkedVariables(horn, A, new SerializablePair<>(0, new ListSet<>(A)));
        assertMarkedVariables(horn, B, new SerializablePair<>(1, new ListSet<>(A.implies(B))));

        psi = A.and(A.implies(new False()));
        horn = new HornSatEvaluator(psi);
        assertFalse(horn.isFormulaSatisfiable());
        assertMarkedVariables(horn, A, new SerializablePair<>(0, new ListSet<>(A)));

        psi = A.and(A.implies(B), A.and(B).implies(new False()));
        horn = new HornSatEvaluator(psi);
        assertFalse(horn.isFormulaSatisfiable());
        assertMarkedVariables(horn, A, new SerializablePair<>(0, new ListSet<>(A)));
        assertMarkedVariables(horn, B, new SerializablePair<>(1, new ListSet<>(A.implies(B))));

        psi =
                (B.implies(D))
                        .and(
                                B.and(C, A).implies(D),
                                E.and(D).implies(new False()),
                                B,
                                D.and(B).implies(E));

        horn = new HornSatEvaluator(psi);
        assertFalse(horn.isFormulaSatisfiable());
        assertMarkedVariables(horn, B, new SerializablePair<>(0, new ListSet<>(B)));
        assertMarkedVariables(horn, D, new SerializablePair<>(1, new ListSet<>(B.implies(D))));
        assertMarkedVariables(
                horn, E, new SerializablePair<>(2, new ListSet<>(D.and(B).implies(E))));
    }

    private void assertMarkedVariables(
            HornSatEvaluator horn,
            Variable variable,
            SerializablePair<Integer, ListSet<ModalFormula>> causingClauses) {

        Map<Variable, SerializablePair<Integer, ListSet<ModalFormula>>> map =
                horn.getPossibleMarkings();

        assertTrue(map.containsKey(variable));
        assertTrue(map.get(variable).equals(causingClauses));
    }

    @Test
    public void testWithTopImplies() {
        Variable A = new Variable("A");
        Variable B = new Variable("B");
        Variable C = new Variable("C");
        Variable D = new Variable("D");
        Variable E = new Variable("E");

        ModalFormula formula =
                ModalFormula.parse("(A -> B) & ((A & B) -> C) & A & (1 -> 0) & (D -> E)");

        HornSatEvaluator evaluator = new HornSatEvaluator(formula);
        assertFalse(evaluator.isFormulaSatisfiable());

        ModalFormula causingClauseA1 = ModalFormula.parse("A");
        ModalFormula causingClauseB = ModalFormula.parse("A -> B");
        ModalFormula causingClauseC = ModalFormula.parse("(A & B) -> C");

        assertMarkedVariables(
                evaluator, A, new SerializablePair<>(0, new ListSet<>(causingClauseA1)));
        assertMarkedVariables(
                evaluator, B, new SerializablePair<>(1, new ListSet<>(causingClauseB)));
        assertMarkedVariables(
                evaluator, C, new SerializablePair<>(2, new ListSet<>(causingClauseC)));

        formula = ModalFormula.parse("(A -> B) & ((A & B) -> C) & (1 -> A) & (D -> E)");

        evaluator = new HornSatEvaluator(formula);
        assertTrue(evaluator.isFormulaSatisfiable());

        ModalFormula causingClauseA2 = ModalFormula.parse("1 -> A");

        assertMarkedVariables(
                evaluator, A, new SerializablePair<>(0, new ListSet<>(causingClauseA2)));
        assertMarkedVariables(
                evaluator, B, new SerializablePair<>(1, new ListSet<>(causingClauseB)));
        assertMarkedVariables(
                evaluator, C, new SerializablePair<>(2, new ListSet<>(causingClauseC)));

        formula = ModalFormula.parse("(A -> B) & ((A & B) -> C) & A & (1 -> A) & (D -> E)");

        evaluator = new HornSatEvaluator(formula);
        assertTrue(evaluator.isFormulaSatisfiable());

        assertMarkedVariables(
                evaluator,
                A,
                new SerializablePair<>(0, new ListSet<>(causingClauseA1, causingClauseA2)));
        assertMarkedVariables(
                evaluator, B, new SerializablePair<>(1, new ListSet<>(causingClauseB)));
        assertMarkedVariables(
                evaluator, C, new SerializablePair<>(2, new ListSet<>(causingClauseC)));
    }
}
