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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CombinedSolverTest {

    private CombinedSolver solver = new CombinedSolver();

    private AnswerType type;

    // @Test
    public void testEquivalenceUnderConstraints() {
        type = AnswerType.VALID;
        checkEquivalenceUnderConstraints(
                "forall x exists y exists z (! (y = z) & R(x,y) & R(x,z) & P(y) & P(z))",
                "forall z (( exists x f(x) = z) -> exists x exists y (R(x,y) & !(x = y) & f(x)=z & f(y)=z & P(x) & P(y)))",
                "forall x forall y (R(x,x) & (R(x,y) -> R(y,x)) & (f(x)=f(y) <-> R(x,y)) )");

        checkEquivalenceUnderConstraints("forall x R(x,x)", "!exists x !R(x,x)");

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

        // long timeStart = System.currentTimeMillis();
        Answer answer = solver.solveEquivalenceUnderConstraints(formula1, formula2, constraints);
        // System.out.println("took " + (System.currentTimeMillis() - timeStart) + "ms");

        assert answer.getType() == type
                : "Wrong answer type: expected \"" + type + "\", got \"" + answer.getType() + "\"";
    }

    private String csvFileString = "\"formula1\";\"formula2\";\"expectedAnswer\";\"VampireAnswer\"";

    private double completeTimeValid = 0.0, completeTimeInvalid = 0.0;
    private int countValid = 0, countInvalid = 0;
    private long maxTimeValid = 0, maxTimeInvalid = 0;
    private List<Long> validTimes = new LinkedList<>(), invalidTimes = new LinkedList<>();

    // @Test
    public void recognizableConstraintsCheck() {
        List<String> constraints =
                Arrays.asList(
                        "forall x forall y (G(x,y) ↔ f(x) = f(y))",
                        "forall x (M(x) ↔ ¬I(x))",
                        "forall x forall y forall z ((G(x,y) ∧ G(y,z)) → G(x,z))",
                        "forall x forall y (G(x,y) → G(y,x))",
                        "forall x G(x,x)",
                        "forall x forall y (f(x)=y → G(x,y))");

        Set<RelationSymbol> relationSymbols = new HashSet<>();
        relationSymbols.add(new RelationSymbol("I", 1, false));
        relationSymbols.add(new RelationSymbol("M", 1, false));
        relationSymbols.add(new RelationSymbol("G", 2, false));
        relationSymbols.add(new RelationSymbol("=", 2, true));

        Set<FunctionSymbol> functionSymbols = new HashSet<>();
        functionSymbols.add(new FunctionSymbol("f", 1, false));

        Signature signature = new Signature(relationSymbols, functionSymbols);

        csvFileString = csvFileString + "\n" + "Millisoft-Task";

        String target = "exists x M(x)";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck("! forall x (!M(x))", target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists x (!(!M(x)) & M(x))", target, AnswerType.VALID, constraints, signature);
        constraintsCheck("exists x !(I(x))", target, AnswerType.VALID, constraints, signature);

        constraintsCheck("forall x M(x)", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck("exists x !(M(x))", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck("exists x I(x)", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck(
                "exists x exists y (!(x=y) & M(x) & M(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "exists x exists y (x=f(y) ∧ I(x))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists x (f(x)=x & I(x))", target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists x exists y (I(x) ∧ x=f(y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x exists y (x=f(y) ∧ !M(x))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "exists x exists y (x=f(y) ∧ I(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x (x=f(x) → I(x))", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck(
                "exists x exists y (f(y)=f(x) & I(x) & I(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x exists y (x=f(y) & M(x))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x exists y (x=f(y) ∨ I(x))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "forall x exists y exists z (¬(y=z) ∧ G(x,y) ∧ G(x,z) ∧ I(y) ∧ I(z))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "forall x exists y exists z (¬(y=z) ∧ G(y,x) ∧ G(z,x) ∧ I(y) ∧ I(z))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x exists y exists z (!(y=z) & f(x)=f(y) & f(x)=f(z) & I(y) ∧ I(z))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "!exists x forall y forall z ((!(y=z) & G(x,y) ∧ G(x,z)) → (!I(y) | !I(z)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "forall x exists y exists z (G(x,y) ∧ G(x,z) ∧ I(y) ∧ I(z))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y (G(x,y) → (I(x) & I(y)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x exists y (!(x=y) & G(x,y) & I(x) & I(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall a exists b exists c exists d (!(b=c) & !(c=d) & !(d=b) & G(a,b) & G(a,c) & G(a,d) & I(b) ∧ I(c) ∧ I(d))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "exists x forall y (G(x,y) → I(y))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists x forall y (f(x)=f(y) → I(y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x forall y (G(x,y) → !M(y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x forall y (!G(x,y) | I(y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "exists x forall y (G(x,y) → I(x))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x exists y (G(x,y) → I(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x forall y (G(x,y) & I(x) & I(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists x forall y (f(x)=f(y) → M(y))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "forall x (M(f(x)) → (x=f(x) ∨ I(x)))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "forall x (M(f(x)) → (x=f(x) ∨ !M(x)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y ((M(f(x)) & G(x,y)) → (f(y)=y | I(y)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "!exists x exists y (M(f(x)) & G(x,y) & !(f(x)=y) & M(y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "forall x (I(f(x)) → (x=f(x) ∨ I(x)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x (M(f(x)) → (x=f(x) ∨ M(x)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x (M(x) → (x=f(x) ∨ I(x)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y (G(x,y) → (M(f(x)) & !(y=f(x))))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        constraints =
                Arrays.asList(
                        "forall f forall g (S(f,g) → S(g,f))",
                        "forall f ¬S(f,f)",
                        "forall f forall g (S(f,g) → (F(f) ∧ F(g)))",
                        "forall n forall f (L(n,f) → (N(n) ∧ F(f)))",
                        "F(c)");

        relationSymbols = new HashSet<>();
        relationSymbols.add(new RelationSymbol("F", 1, false));
        relationSymbols.add(new RelationSymbol("N", 1, false));
        relationSymbols.add(new RelationSymbol("L", 2, false));
        relationSymbols.add(new RelationSymbol("S", 2, false));
        relationSymbols.add(new RelationSymbol("=", 2, true));

        functionSymbols = new HashSet<>();
        functionSymbols.add(new FunctionSymbol("c", 0, false));

        signature = new Signature(relationSymbols, functionSymbols);

        csvFileString = csvFileString + "\n" + "logic_1";

        target = "exists x (L(x,c))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck("! forall x (!L(x,c))", target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists x exists y (y=c & L(x,y))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck("exists x (!L(x,c))", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck("exists x (S(x,c))", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck("forall x L(x,c)", target, AnswerType.INVALID, constraints, signature);

        target = "forall x(F(x) → ∃ y(F(y) ∧ S(x,y)))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "forall x exists y (F(x) → (S(x,y) & F(y)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x (F(x) → ∃ y S(x,y))", target, AnswerType.VALID, constraints, signature);

        constraintsCheck(
                "forall x(F(x) → ∃ y(N(y) ∧ L(y,x)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x(F(x) → ∃ y(F(y)))", target, AnswerType.INVALID, constraints, signature);
        constraintsCheck(
                "exists x (F(x) & forall y (F(y) → S(x,y)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "N(x) ∧ ∃ y ∃ z (¬(y=z) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬ S(y,z))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "exists y exists z (!(y=z) & L(x,y) & L(x,z) & !S(y,z))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "∃ y ∃ z (N(x) ∧ ¬(y=z) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬ S(y,z))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "N(x) ∧ ∃ y ∃ z (¬(y=z) ∧ F(y) ∧ F(z) ∧ L(y,x) ∧ L(z,x) ∧ ¬ S(y,z))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "N(x) & exists y (¬(y=c) & F(y) & L(x,c) & L(x,y) & !S(y,c))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "exists y exists z (!S(y,z) → (L(x,y) & L(x,z)))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target =
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "forall x forall y forall z exists v ((L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → (!(v=x) & (L(v,y) | L(v,z))))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y forall z ((S(y,z) & L(x,y) & L(x,z) & !(y=z)) →∃ v((L(v,y) ∨ L(v,z)) ∧ N(v) ∧ ¬(v=x)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ S(y,z)) → exists v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → ∃ v(N(v) ∧ (L(v,y) ∨ L(v,z))))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "forall x forall y forall z((N(x) ∧ F(y) ∧ F(z) ∧ L(x,y) ∧ L(x,z) ∧ ¬(y=z) ∧ S(y,z)) → forall v(N(v) ∧ ¬(v=x) ∧ (L(v,y) ∨ L(v,z))))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        target = "F(x) ∧ S(x,c) ∧ forall y((N(y)∧ L(y,x)) → L(y,c))";

        constraintsCheck(target, target, AnswerType.VALID, constraints, signature);
        constraintsCheck(
                "S(x,c) & forall y (L(y,x) → L(y,c))",
                target,
                AnswerType.VALID,
                constraints,
                signature);
        constraintsCheck(
                "forall y(F(x) ∧ S(x,c) ∧ ((N(y)∧ L(y,x)) → L(y,c)))",
                target,
                AnswerType.VALID,
                constraints,
                signature);

        constraintsCheck(
                "F(x) | S(x,c) | forall y((N(y)∧ L(y,x)) → L(y,c))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "F(x) ∧ S(x,c) ∧ exists y((N(y)∧ L(y,x)) → L(y,c))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);
        constraintsCheck(
                "F(x) ∧ S(c,x) ∧ forall y((N(y)∧ L(y,c)) → L(y,x))",
                target,
                AnswerType.INVALID,
                constraints,
                signature);

        System.out.println("Results:\n" + csvFileString.replaceAll(";", "\t\t"));
        System.out.println(
                "Average time for \"VALID\": "
                        + (completeTimeValid / countValid)
                        + " ms;"
                        + " Average time for \"INVALID\": "
                        + (completeTimeInvalid / countInvalid)
                        + " ms");
        System.out.println(
                "Max time for \"VALID\": "
                        + maxTimeValid
                        + " ms; "
                        + "Max time for \"INVALID\": "
                        + maxTimeInvalid
                        + " ms");
        Collections.sort(validTimes);
        Collections.sort(invalidTimes);
        System.out.println("\"VALID\" times: " + validTimes);
        System.out.println("\"INVALID\" times: " + invalidTimes);
    }

    private void constraintsCheck(
            String formulaString1,
            String formulaString2,
            AnswerType type,
            List<String> constraintStrings,
            Signature signature) {
        FormulaReader reader = new FormulaReader();
        Formula formula1 = reader.read(formulaString1, signature);
        Formula formula2 = reader.read(formulaString2, signature);
        List<Formula> constraints =
                constraintStrings.stream()
                        .map(s -> reader.read(s, signature))
                        .collect(Collectors.toList());

        long start = System.currentTimeMillis();
        Answer answer = solver.solveEquivalenceUnderConstraints(formula1, formula2, constraints);
        long time = System.currentTimeMillis() - start;

        // let vampire finish open calculations via internal timeout
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignored) {
        }

        if (answer.getType() != AnswerType.NO_DECISION) {
            if (type == AnswerType.INVALID) {
                completeTimeInvalid += time;
                countInvalid++;
                invalidTimes.add(time);
                if (time > maxTimeInvalid) {
                    maxTimeInvalid = time;
                }
            }

            if (type == AnswerType.VALID) {
                completeTimeValid += time;
                countValid++;
                validTimes.add(time);
                if (time > maxTimeValid) {
                    maxTimeValid = time;
                }
            }
        }

        String answerType;
        if (answer instanceof InvalidFormulaWithCounterExample) {
            answerType = "INVALID_WITH_MODEL";
        } else {
            answerType = answer.getType().name();
        }

        csvFileString =
                csvFileString + "\n" + formula1 + ";" + formula2 + ";" + type + ";" + answerType;
    }
}
