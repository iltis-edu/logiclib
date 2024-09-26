package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.AnswerType;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.InvalidFormulaWithCounterExample;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class RandomModelSolverTest {

    private RandomModelSolver randomModelSolver;

    private AnswerType type;

    @Test
    public void testValid() {
        randomModelSolver = new RandomModelSolver(5, 0.5, 100, 0);
        type = AnswerType.NO_DECISION;
        testSolving("1");
        testSolving("forall x !(exists y E(x,y)) <-> forall x forall y !E(x,y)");
        testSolving("E(x) -> exists y E(y)");
        testSolving("E(f(x)) -> exists y E(f(y))");
    }

    @Test
    public void testInvalid() {
        randomModelSolver = new RandomModelSolver(5, 0.5, 100, 0);
        type = AnswerType.INVALID;
        testSolving("0");
        testSolving("forall x (!E(x) & exists y E(y))");
        testSolving("E(x,y) <-> forall y forall x E(y,x)");
        testSolving("E(f(x), y) <-> !E(f(x), y)");
    }

    @Test
    public void testEquivalenceValid() {
        randomModelSolver = new RandomModelSolver(5, 0.5, 100, 0);
        type = AnswerType.NO_DECISION;
        testEquivalence("forall x !E(x,y)", "!exists x E(x,y)");
        testEquivalence("forall x !(E(x,y) & !Y(x))", "forall x (!E(x,y) | Y(x))");
        testEquivalence(
                "forall x forall y !(E(x,y) & !Y(x))", "forall y forall x (!E(x,y) | Y(x))");
    }

    @Test
    public void testEquivalenceInvalid() {
        randomModelSolver = new RandomModelSolver(5, 0.5, 100, 0);
        type = AnswerType.INVALID;
        testEquivalence("forall x !E(x,y)", "forall x E(x,y)");
        testEquivalence("forall x !(E(x,y) & !Y(x))", "exists x (!E(x,y) | Y(x))");
        testEquivalence(
                "forall x exists y !(E(x,y) & !Y(x))", "exists y forall x (!E(x,y) | Y(x))");
    }

    private void testSolving(String formulaString) {
        Formula formula = Formula.parse(formulaString);

        Answer answer = randomModelSolver.solve(formula);

        assert answer.getType() == type
                : " Wrong answer type: expected \""
                        + type
                        + "\", got \""
                        + answer.getType()
                        + "\" on formula \""
                        + formula
                        + "\"";
    }

    private void testEquivalence(String formulaString1, String formulaString2) {
        Formula formula1 = Formula.parse(formulaString1);
        Formula formula2 = Formula.parse(formulaString2);

        Answer answer = randomModelSolver.solveEquivalence(formula1, formula2);

        assert answer.getType() == type
                : " Wrong answer type: expected \""
                        + type
                        + "\", got \""
                        + answer.getType()
                        + "\" on equivalence of formulae \""
                        + formula1
                        + "\" and \""
                        + formula2
                        + "\"";
    }

    private String csvFileString =
            "\"formula1\";\"formula2\";\"expectedAnswer\";\"randomModelSolverAnswer\"";

    @Test
    public void recognizableCheck() {
        Set<Integer> universeSizes = new HashSet<>(10);
        for (int i = 1; i <= 10; i++) {
            universeSizes.add(i);
        }

        randomModelSolver = new RandomModelSolver(universeSizes, 0.5, 100, 0);

        // valid transformations
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "forall x (!E(x,x) & ∃ y E(x,y))",
                AnswerType.NO_DECISION);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "¬∃ y (¬E(y,y) → ¬∃ x E(y,x))",
                AnswerType.NO_DECISION);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "¬∃ x (E(x,x) | forall y !E(x,y))",
                AnswerType.NO_DECISION);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "exists z ¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                AnswerType.NO_DECISION);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "¬(∃ x E(x,x) | exists x ¬∃ y E(x,y))",
                AnswerType.NO_DECISION);
        check("forall x !E(x,y)", "!exists x E(x,y)", AnswerType.NO_DECISION);
        check("forall x !(E(x,y) & !Y(x))", "forall x (!E(x,y) | Y(x))", AnswerType.NO_DECISION);
        check(
                "forall x forall y !(E(x,y) & !Y(x))",
                "forall y forall x (!E(x,y) | Y(x))",
                AnswerType.NO_DECISION);

        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y)) & !forall x (G(x,y) | exists z G(z,z)) & forall y forall y !(F(y,y) & !exists x H(x,y))",
                "forall x (!E(x,x) & ∃ y E(x,y)) & exists x !G(x,y) & forall z !G(z,z) & forall y forall y (!F(y,y) | exists x H(x,y))",
                AnswerType.NO_DECISION);

        // invalid transformations
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (¬E(x,x) → ∃ y E(x,y))", AnswerType.INVALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "forall x (¬E(x,x) → ¬∃ y E(x,y))",
                AnswerType.INVALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "!forall x (¬E(x,x) → ¬∃ y E(x,y))",
                AnswerType.INVALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "forall x (¬E(x,x) & ¬∃ y E(x,y))",
                AnswerType.INVALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "∃ x !(¬E(x,x) → forall y !E(x,y))",
                AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (E(x,x) | ∃ y E(x,y))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ y (¬E(x,x) → ¬∃ y E(x,y))", AnswerType.INVALID);

        check(
                "forall x exists y (E(f(x),g(y,x)) → E(f(x),g(x,y)))",
                "forall x exists y (E(f(x),g(y,x)) → E(g(x,y),f(x)))",
                AnswerType.INVALID);
        check(
                "E(f(x),g(y,x)) → E(f(x),g(x,y))",
                "E(f(x),g(y,x)) → E(f(y),g(x,y))",
                AnswerType.INVALID);
        check(
                "E(f(x),g(y,x)) → E(f(x),g(x,y))",
                "E(f(x),g(y,x)) → E(f(x),g(y,x))",
                AnswerType.INVALID);
        // close to buggy trafos
        check(
                "forall x forall y (F(x,y) | F(y,x)) | E(x)",
                "forall x forall y (F(x,y) | F(y,x) | E(x))",
                AnswerType.INVALID);
        check(
                "forall x forall y !(F(x,y) | F(y,x)) | E(x)",
                "forall x forall y (!F(x,y) & !F(y,x) & !E(x))",
                AnswerType.INVALID);
        check(
                "forall x forall y (F(x,y) | F(y,x)) | E(x)",
                "forall x (F(x,y) | F(y,x)) | E(x)",
                AnswerType.INVALID);
        check(
                "forall x forall y (F(x,y) | F(y,x)) | E(x)",
                "forall x forall y (F(x,y) | F(y,x)) | exists x E(x)",
                AnswerType.INVALID);
        check(
                "forall x forall y (F(x,y) & F(y,x)) & E(x)",
                "forall x forall y (F(x,y) & F(y,x) & E(x))",
                AnswerType.INVALID);

        check("forall x !E(x,y)", "forall x E(x,y)", AnswerType.INVALID);
        check("forall x !(E(x,y) & !Y(x))", "exists x (!E(x,y) | Y(x))", AnswerType.INVALID);
        check(
                "forall x exists y !(E(x,y) & !Y(x))",
                "exists y forall x (!E(x,y) | Y(x))",
                AnswerType.INVALID);

        // Failures
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (E(x,x) → ¬∃ y E(x,y))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (¬E(x,x) | ¬∃ y E(x,y))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (¬∃ y E(x,y) → ¬E(x,x))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "∀ x !(E(x,x) | ∃ y !E(x,y))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∀ x (¬E(x,x) → ¬∀ y E(x,y))", AnswerType.INVALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (¬E(x,x) → ¬∃ y E(y,x))", AnswerType.INVALID);

        // single "!" add the end too much
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y)) & !forall x (G(x,y) | exists z G(z,z)) & forall y forall y !(F(y,y) & !exists x H(x,y))",
                "forall x (!E(x,x) & ∃ y E(x,y)) & exists x !G(x,y) & forall z !G(z,z) & forall y forall y (!F(y,y) | exists x !H(x,y))",
                AnswerType.INVALID);

        // real data unknowns
        check("∃y (E(y,x)→∃z E(x,z))", "∃y E(y,x)→∃y E(x,y)", AnswerType.INVALID);
        check("∃z ∀y (E(x,z)↔E(z,y))", "∃y E(y,x)→∃y E(x,y)", AnswerType.INVALID);
        check("∀x ∃y (E(x,y)∧E(y,x))", "¬∃y E(x,y)", AnswerType.INVALID);
        check("∀x ∃y (E(x,y)∧E(y,x)∧E(x,x))", "¬∃y E(x,y)", AnswerType.INVALID);
        check("∀x ∃y (E(y,x)∨(E(x,y)∧¬E(y,x)))", "¬∃y E(x,y)", AnswerType.INVALID);
        check(
                "∃z E(z,x)→∃y (E(x,y)∧∀z (¬(y=z)→¬E(x,z)))",
                "∃y E(y,x)→∃y (E(x,y)∧¬∃z (¬(z=y)∧E(x,z)))",
                AnswerType.NO_DECISION);
        check("∀x ∃y ∃z (¬E(x,y)∧E(z,x))", "¬∃y E(x,y)", AnswerType.INVALID);
        check("∀x ∃y (E(y,x)∧¬E(x,y)∧¬E(x,x))", "¬∃y E(x,y)	", AnswerType.INVALID);
        check("∀x ∀y ∃z ¬(E(y,x)→¬E(x,z))", "¬∃y E(x,y)	", AnswerType.INVALID);
        check("∃y ∀z (E(y,z)↔E(x,y))", "∃y E(y,x)→∃y E(x,y)", AnswerType.INVALID);
        check("∀y E(x,y)↔∀y ∃z E(y,z)", "∀y (E(x,y)→∃z E(y,z))", AnswerType.INVALID);
        check(
                "∃z E(z,x)→∃z (E(x,z)∧∀y (¬E(x,y)∨(z=y)))",
                "∃y E(y,x)→∃y (E(x,y)∧¬∃z (¬(z=y)∧E(x,z)))",
                AnswerType.NO_DECISION);
        check("∀x ∃y ∃z (E(y,x)∧¬E(x,z))", "¬∃y E(x,y)", AnswerType.INVALID);
        check(
                "∃y E(y,x)→∃y (E(x,y)∧∀z (¬E(x,z)∨(y=z)))",
                "∃y E(y,x)→∃y (E(x,y)∧¬∃z (¬(z=y)∧E(x,z)))",
                AnswerType.NO_DECISION);
        check("∀x (∃y E(x,y)∧∃z E(z,x))", "∃y E(x,y)∧∃z E(z,x)", AnswerType.INVALID);
        check("∀y ∃z ((E(y,x)∧E(x,z))∨¬E(x,z))", "∀y (E(x,y)→∃z E(y,z))", AnswerType.INVALID);
        check("∃y ∀z (E(x,y)↔E(y,z))", "∃y E(y,x)→∃y E(x,y)", AnswerType.INVALID);
        check(
                "∃y E(y,x)→∃y (E(x,y)∧∀z (E(x,z)→(y=z)))",
                "∃y E(y,x)→∃y (E(x,y)∧¬∃z (¬(z=y)∧E(x,z)))",
                AnswerType.NO_DECISION);

        System.out.println("Results:\n" + csvFileString.replaceAll(";", "\t\t"));
    }

    private void check(String formulaString1, String formulaString2, AnswerType type) {
        FormulaReader reader = new FormulaReader();
        Formula formula1 =
                reader.read(
                        formulaString1, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY);
        Formula formula2 =
                reader.read(
                        formulaString2, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY);

        long time = System.currentTimeMillis();
        Answer answer = randomModelSolver.solveEquivalence(formula1, formula2);
        time = System.currentTimeMillis() - time;

        boolean correctAnswer = type == answer.getType();

        csvFileString =
                csvFileString
                        + "\n"
                        + formula1
                        + ";"
                        + formula2
                        + ";"
                        + type
                        + ";"
                        + answer.getType()
                        + ";"
                        + correctAnswer;

        System.out.println(
                "for result "
                        + correctAnswer
                        + " on expected answer "
                        + type
                        + " took "
                        + time
                        + " ms");
    }

    @Test
    public void testConstantFunctions() {
        Set<Integer> universeSizes = new HashSet<>(5);
        for (int i = 1; i <= 5; i++) {
            universeSizes.add(i);
        }

        randomModelSolver = new RandomModelSolver(universeSizes, 0.5, 10, 0);

        Set<RelationSymbol> relationSymbols = new HashSet<>();
        Set<FunctionSymbol> functionSymbols = new HashSet<>();

        relationSymbols.add(new RelationSymbol("R", 1, false));
        relationSymbols.add(new RelationSymbol("G", 2, false));
        relationSymbols.add(new RelationSymbol("=", 2, true));
        functionSymbols.add(new FunctionSymbol("f", 1, false));
        functionSymbols.add(new FunctionSymbol("a", 0, false));
        functionSymbols.add(new FunctionSymbol("t", 0, false));

        Signature signature = new Signature(relationSymbols, functionSymbols);

        FormulaReader reader = new FormulaReader();

        Formula formula1 = reader.read("exists x G(f(x),t) → t = a", signature);
        Formula formula2 = reader.read("exists x G(x,f(t)) → t = a", signature);

        long time = System.currentTimeMillis();
        Answer answer = randomModelSolver.solveEquivalence(formula1, formula2);
        time = System.currentTimeMillis() - time;

        assert answer instanceof InvalidFormulaWithCounterExample
                : "expected Counterexample, got none";
    }

    @Test
    public void testEquivalenceUnderConstraints() {
        Set<Integer> universeSizes = new HashSet<>(10);
        for (int i = 1; i <= 10; i++) {
            universeSizes.add(i);
        }

        randomModelSolver = new RandomModelSolver(universeSizes, 0.5, 100, 0);

        type = AnswerType.NO_DECISION;
        checkEquivalenceUnderConstraints(
                "forall x exists y exists z (! (y = z) & R(x,y) & R(x,z) & P(y) & P(z))",
                "forall z (( exists x f(x) = z) -> exists x exists y (R(x,y) & !(x = y) & f(x)=z & f(y)=z & P(x) & P(y)))",
                "forall x forall y (R(x,x) & (R(x,y) -> R(y,x)) & (f(x)=f(y) <-> R(x,y)) )");

        type = AnswerType.INVALID;
        checkEquivalenceUnderConstraints(
                "forall x exists y exists z (! (y = z) & R(x,y) & R(x,z) & P(y) & P(z))",
                "forall z (( exists x f(x) = z) -> exists x exists y (R(x,y) & !(x = y) & f(x)=z & f(y)=z & P(x) & P(y)))");
    }

    private void checkEquivalenceUnderConstraints(
            String formulaString1, String formulaString2, String... constraintsStrings) {
        FormulaReader reader = new FormulaReader();
        SignatureCheckable signatureCheckable =
                SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY;

        Formula formula1 = reader.read(formulaString1, signatureCheckable);
        Formula formula2 = reader.read(formulaString2, signatureCheckable);

        List<Formula> constraints = new LinkedList<>();
        for (String constraintString : constraintsStrings) {
            constraints.add(reader.read(constraintString, signatureCheckable));
        }

        Answer answer =
                randomModelSolver.solveEquivalenceUnderConstraints(formula1, formula2, constraints);

        assert answer.getType() == type;
    }
}
