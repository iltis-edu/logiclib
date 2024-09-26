package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.AnswerType;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.DefaultHashGraph;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.DefaultUndirectedHashGraph;
import java.util.*;

public class Z3SolverTest {
    // All tests are intentionally commented out to avoid calling
    // the running instance of the weblib anytime someone runs "mvn clean install"

    private Z3Solver z3Solver = Z3Solver.getInstance();

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
    }

    // @Test
    public void testEquivalenceValid() {
        type = AnswerType.VALID;
        testEquivalence("forall x !E(x,y)", "!exists x E(x,y)");
        testEquivalence("forall x !(E(x,y) & !Y(x))", "forall x (!E(x,y) | Y(x))");
        testEquivalence(
                "forall x forall y !(E(x,y) & !Y(x))", "forall y forall x (!E(x,y) | Y(x))");
    }

    // @Test
    public void testEquivalenceInvalid() {
        type = AnswerType.INVALID;
        testEquivalence("forall x !E(x,y)", "forall x E(x,y)");
        testEquivalence("forall x !(E(x,y) & !Y(x))", "exists x (!E(x,y) | Y(x))");
        testEquivalence(
                "forall x exists y !(E(x,y) & !Y(x))", "exists y forall x (!E(x,y) | Y(x))");
    }

    private void testSolving(String formulaString) {
        Formula formula = Formula.parse(formulaString);

        Answer answer = z3Solver.solve(formula);

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

        Answer answer = z3Solver.solveEquivalence(formula1, formula2);

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

    private String csvFileString = "\"formula1\";\"formula2\";\"expectedAnswer\";\"z3Answer\"";

    // used to evaluate which kind of equalities and inequalities z3 can recognize
    // @Test
    public void recognizableCheck() {
        // valid transformations
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "forall x (!E(x,x) & ∃ y E(x,y))", AnswerType.VALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ y (¬E(y,y) → ¬∃ x E(y,x))", AnswerType.VALID);
        check("¬∃ x (¬E(x,x) → ¬∃ y E(x,y))", "¬∃ x (E(x,x) | forall y !E(x,y))", AnswerType.VALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "exists z ¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                AnswerType.VALID);
        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y))",
                "¬(∃ x E(x,x) | exists x ¬∃ y E(x,y))",
                AnswerType.VALID);
        check("forall x !E(x,y)", "!exists x E(x,y)", AnswerType.VALID);
        check("forall x !(E(x,y) & !Y(x))", "forall x (!E(x,y) | Y(x))", AnswerType.VALID);
        check(
                "forall x forall y !(E(x,y) & !Y(x))",
                "forall y forall x (!E(x,y) | Y(x))",
                AnswerType.VALID);

        check(
                "¬∃ x (¬E(x,x) → ¬∃ y E(x,y)) & !forall x (G(x,y) | exists z G(z,z)) & forall y forall y !(F(y,y) & !exists x H(x,y))",
                "forall x (!E(x,x) & ∃ y E(x,y)) & exists x !G(x,y) & forall z !G(z,z) & forall y forall y (!F(y,y) | exists x H(x,y))",
                AnswerType.VALID);

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

        System.out.println("Results:\n" + csvFileString.replaceAll(";", "\t\t"));
    }

    private void check(String formulaString1, String formulaString2, AnswerType type) {
        Formula formula1 = Formula.parse(formulaString1);
        Formula formula2 = Formula.parse(formulaString2);

        Answer answer = z3Solver.solveEquivalence(formula1, formula2);

        csvFileString =
                csvFileString
                        + "\n"
                        + formula1
                        + ";"
                        + formula2
                        + ";"
                        + type
                        + ";"
                        + answer.getType();
    }

    // @Test
    public void testHamiltonCycleUndirected() {
        // we use 5 different Strings a vertices
        DefaultUndirectedHashGraph<String> graphWithHC = new DefaultUndirectedHashGraph<>();
        DefaultUndirectedHashGraph<String> graphWithOutHC = new DefaultUndirectedHashGraph<>();
        for (int i = 0; i < 5; i++) {
            graphWithHC.addVertex("x" + i);
            graphWithOutHC.addVertex("x" + i);
        }

        // HC
        graphWithHC.addEdge("x0", "x1");
        graphWithHC.addEdge("x1", "x2");
        graphWithHC.addEdge("x2", "x4");
        graphWithHC.addEdge("x4", "x3");
        graphWithHC.addEdge("x0", "x3");
        // unnecessary other edge
        graphWithHC.addEdge("x1", "x4");

        Optional<List<String>> withHC = z3Solver.getHamiltonCycle(graphWithHC);
        System.out.println("graph with HC: " + withHC);

        graphWithOutHC.addEdge("x0", "x1");
        graphWithOutHC.addEdge("x1", "x2");
        graphWithOutHC.addEdge("x2", "x3");
        graphWithOutHC.addEdge("x1", "x3");
        graphWithOutHC.addEdge("x0", "x4");
        graphWithOutHC.addEdge("x1", "x4");

        Optional<List<String>> withOutHC = z3Solver.getHamiltonCycle(graphWithOutHC);
        System.out.println("graph without HC: " + withOutHC);

        DefaultUndirectedHashGraph<String> simple = new DefaultUndirectedHashGraph<>();
        for (int i = 0; i < 3; i++) {
            simple.addVertex("x" + i);
        }
        simple.addEdge("x0", "x1");
        simple.addEdge("x1", "x2");
        simple.addEdge("x0", "x2");

        System.out.println("Simple : " + z3Solver.getHamiltonCycle(simple));

        DefaultUndirectedHashGraph<String> onlyHP = new DefaultUndirectedHashGraph<>();
        for (int i = 0; i < 5; i++) {
            onlyHP.addVertex("x" + i);
        }

        // HP
        onlyHP.addEdge("x0", "x1");
        onlyHP.addEdge("x1", "x2");
        onlyHP.addEdge("x2", "x4");
        onlyHP.addEdge("x4", "x3");
        // unnecessary other edge
        graphWithHC.addEdge("x1", "x4");

        System.out.println("only HP: " + z3Solver.getHamiltonCycle(onlyHP));
    }

    // @Test
    public void testHamiltonCycleDirected() {
        // we use 5 different Strings a vertices
        DefaultHashGraph<String> graphWithHC = new DefaultHashGraph<>();
        DefaultHashGraph<String> graphWithOutHCBackwardsEdge = new DefaultHashGraph<>();
        for (int i = 0; i < 5; i++) {
            graphWithHC.addVertex("x" + i);
            graphWithOutHCBackwardsEdge.addVertex("x" + i);
        }

        // HC
        graphWithHC.addEdge("x0", "x1");
        graphWithHC.addEdge("x1", "x2");
        graphWithHC.addEdge("x2", "x4");
        graphWithHC.addEdge("x4", "x3");
        graphWithHC.addEdge("x3", "x0");
        // unnecessary other edge
        graphWithHC.addEdge("x1", "x4");

        Optional<List<String>> withHC = z3Solver.getHamiltonCycle(graphWithHC);
        System.out.println("graph with HC: " + withHC);

        // HC
        graphWithOutHCBackwardsEdge.addEdge("x0", "x1");
        graphWithOutHCBackwardsEdge.addEdge("x1", "x2");
        graphWithOutHCBackwardsEdge.addEdge("x2", "x4");
        graphWithOutHCBackwardsEdge.addEdge("x4", "x3");
        // flipped edge
        graphWithOutHCBackwardsEdge.addEdge("x0", "x3");
        // unnecessary other edge
        graphWithOutHCBackwardsEdge.addEdge("x1", "x4");

        Optional<List<String>> withOutHC = z3Solver.getHamiltonCycle(graphWithOutHCBackwardsEdge);
        System.out.println("graph without HC: " + withOutHC);

        DefaultUndirectedHashGraph<String> simple = new DefaultUndirectedHashGraph<>();
        for (int i = 0; i < 3; i++) {
            simple.addVertex("x" + i);
        }
        simple.addEdge("x0", "x1");
        simple.addEdge("x1", "x2");
        simple.addEdge("x2", "x0");

        System.out.println("Simple : " + z3Solver.getHamiltonCycle(simple));

        DefaultHashGraph<String> simpleBackwardsEdge = new DefaultHashGraph<>();
        for (int i = 0; i < 3; i++) {
            simpleBackwardsEdge.addVertex("x" + i);
        }
        simpleBackwardsEdge.addEdge("x0", "x1");
        simpleBackwardsEdge.addEdge("x1", "x2");
        simpleBackwardsEdge.addEdge("x0", "x2");

        System.out.println(
                "Simple with backwards edge: " + z3Solver.getHamiltonCycle(simpleBackwardsEdge));

        DefaultHashGraph<String> onlyHP = new DefaultHashGraph<>();
        for (int i = 0; i < 5; i++) {
            onlyHP.addVertex("x" + i);
        }

        // HP
        onlyHP.addEdge("x0", "x1");
        onlyHP.addEdge("x1", "x2");
        onlyHP.addEdge("x2", "x4");
        onlyHP.addEdge("x4", "x3");
        // unnecessary other edge
        graphWithHC.addEdge("x1", "x4");

        System.out.println("only HP: " + z3Solver.getHamiltonCycle(onlyHP));
    }

    // @Test
    public void testEquivalenceUnderConstraints() {
        type = AnswerType.VALID;
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

        Answer answer = z3Solver.solveEquivalenceUnderConstraints(formula1, formula2, constraints);

        assert answer.getType() == type;
    }
}
