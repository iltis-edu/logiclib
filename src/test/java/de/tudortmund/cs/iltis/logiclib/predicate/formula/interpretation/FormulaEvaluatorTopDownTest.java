package de.tudortmund.cs.iltis.logiclib.predicate.formula.interpretation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.junit.Test;

public class FormulaEvaluatorTopDownTest {

    @Test
    public void testTrue() {
        FormulaEvaluatorTopDown evaluator = new FormulaEvaluatorTopDown(new True());
        Structure<Integer> structure = new Structure<Integer>(new SetUniverse<>(new TreeSet<>()));
        Set<Valuation<Integer>> result = evaluator.evaluate(structure);
        assertEquals(new TreeSet<>(Arrays.asList(new Valuation<Integer>())), result);
    }

    @Test
    public void testFalse() {
        FormulaEvaluatorTopDown evaluator = new FormulaEvaluatorTopDown(new False());
        Structure<Integer> structure = new Structure<Integer>(new SetUniverse<>(new TreeSet<>()));
        Set<Valuation<Integer>> result = evaluator.evaluate(structure);
        assertEquals(new TreeSet<>(), result);
    }

    @Test
    public void testQuanitifierFree() {
        RelationSymbol Mod = new RelationSymbol("Mod", 3, false);
        FunctionSymbol two = new FunctionSymbol("2", 0, false);
        FunctionSymbol three = new FunctionSymbol("3", 0, false);
        FunctionSymbol id = new FunctionSymbol("id", 1, false);
        VariableSymbol x = new VariableSymbol("x");
        VariableSymbol y = new VariableSymbol("y");

        Formula mod2 =
                new RelationAtom(
                        Mod,
                        new FunctionTerm(id, new Variable(x)),
                        new FunctionTerm(two),
                        new Variable(y));
        Formula mod3 =
                new RelationAtom(
                        Mod,
                        new FunctionTerm(id, new Variable(x)),
                        new FunctionTerm(three),
                        new Variable(y));

        Set<Integer> elements = new TreeSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        Universe<Integer> universe = new SetUniverse<>(elements);
        Structure<Integer> structure = new Structure<>(universe);
        SerializablePredicate<Tuple<Integer>> ModRelation =
                (t ->
                        t.getElementAtPosition(0) % t.getElementAtPosition(1)
                                == t.getElementAtPosition(2));
        SerializableFunction<Tuple<Integer>, Integer> twoConstant = (t -> 2);
        SerializableFunction<Tuple<Integer>, Integer> threeConstant = (t -> 3);
        SerializableFunction<Tuple<Integer>, Integer> idFunction = (t -> t.getElementAtPosition(0));
        structure.addRelation(Mod, new RuleRelationImplementation<>(ModRelation));
        structure.addFunction(two, new RuleFunctionImplementation<>(twoConstant));
        structure.addFunction(three, new RuleFunctionImplementation<>(threeConstant));
        structure.addFunction(id, new RuleFunctionImplementation<>(idFunction));

        FormulaEvaluatorTopDown evaluator = new FormulaEvaluatorTopDown(mod2);
        Set<Valuation<Integer>> expected =
                getExpected(
                        elements,
                        (t -> t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)));
        Set<Valuation<Integer>> result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluatorTopDown(mod2.not());
        expected =
                getExpected(
                        elements,
                        (t -> !(t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1))));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluatorTopDown(mod2.or(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)
                                        || t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluatorTopDown(mod2.and(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)
                                        && t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluatorTopDown(mod2.implies(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 != t.getElementAtPosition(1)
                                        || t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluatorTopDown(mod2.equivalentTo(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                (t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)
                                                && t.getElementAtPosition(0) % 3
                                                        == t.getElementAtPosition(1))
                                        || (t.getElementAtPosition(0) % 2
                                                        != t.getElementAtPosition(1)
                                                && t.getElementAtPosition(0) % 3
                                                        != t.getElementAtPosition(1))));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);
    }

    @Test
    public void testQuantifiers() {
        RelationSymbol Mod = new RelationSymbol("Mod", 3, false);
        RelationSymbol Equals = new RelationSymbol("=", 2, true);
        FunctionSymbol zero = new FunctionSymbol("0", 0, false);
        FunctionSymbol one = new FunctionSymbol("1", 0, false);
        FunctionSymbol two = new FunctionSymbol("2", 0, false);
        FunctionSymbol three = new FunctionSymbol("3", 0, false);
        FunctionSymbol four = new FunctionSymbol("4", 0, false);
        VariableSymbol x = new VariableSymbol("x");
        VariableSymbol y1 = new VariableSymbol("y_1");
        VariableSymbol y2 = new VariableSymbol("y_2");
        VariableSymbol y3 = new VariableSymbol("y_3");

        Formula divisible2 =
                new RelationAtom(
                        Mod, new Variable(x), new FunctionTerm(two), new FunctionTerm(zero));
        Formula divisible3 =
                new RelationAtom(
                        Mod, new Variable(x), new FunctionTerm(three), new FunctionTerm(zero));
        Formula divisible4 =
                new RelationAtom(
                        Mod, new Variable(x), new FunctionTerm(four), new FunctionTerm(zero));
        Formula divisibleY1 =
                new RelationAtom(Mod, new Variable(x), new Variable(y1), new FunctionTerm(zero));
        Formula divisibleY2 =
                new RelationAtom(Mod, new Variable(x), new Variable(y2), new FunctionTerm(zero));
        Formula divisibleY3 =
                new RelationAtom(Mod, new Variable(x), new Variable(y3), new FunctionTerm(zero));

        Set<Integer> elements = new TreeSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        Universe<Integer> universe = new SetUniverse<>(elements);
        Structure<Integer> structure = new Structure<>(universe);
        SerializablePredicate<Tuple<Integer>> ModRelation =
                (t ->
                        (0 == t.getElementAtPosition(1))
                                ? false
                                : t.getElementAtPosition(0) % t.getElementAtPosition(1)
                                        == t.getElementAtPosition(2));
        SerializablePredicate<Tuple<Integer>> EqualsRelation =
                (t -> t.getElementAtPosition(0) == t.getElementAtPosition(1));
        SerializableFunction<Tuple<Integer>, Integer> zeroConstant = (t -> 0);
        SerializableFunction<Tuple<Integer>, Integer> oneConstant = (t -> 1);
        SerializableFunction<Tuple<Integer>, Integer> twoConstant = (t -> 2);
        SerializableFunction<Tuple<Integer>, Integer> threeConstant = (t -> 3);
        SerializableFunction<Tuple<Integer>, Integer> fourConstant = (t -> 4);
        structure.addRelation(Mod, new RuleRelationImplementation<>(ModRelation));
        structure.addRelation(Equals, new RuleRelationImplementation<>(EqualsRelation));
        structure.addFunction(zero, new RuleFunctionImplementation<>(zeroConstant));
        structure.addFunction(one, new RuleFunctionImplementation<>(oneConstant));
        structure.addFunction(two, new RuleFunctionImplementation<>(twoConstant));
        structure.addFunction(three, new RuleFunctionImplementation<>(threeConstant));
        structure.addFunction(four, new RuleFunctionImplementation<>(fourConstant));

        TreeSet<Valuation<Integer>> trueResult = new TreeSet<>(Arrays.asList(new Valuation<>()));
        TreeSet<Valuation<Integer>> falseResult = new TreeSet<>();
        Formula divisible4implies2 = divisible4.implies(divisible2).forAll(new Variable(x));
        FormulaEvaluatorTopDown evaluator = new FormulaEvaluatorTopDown(divisible4implies2);
        Set<Valuation<Integer>> evalResult = evaluator.evaluate(structure);
        assertEquals(
                trueResult, getReduced(evalResult, divisible4implies2.getFreeVariableSymbols()));

        Formula divisible4implies3 = divisible4.implies(divisible3).forAll(new Variable(x));
        evaluator = new FormulaEvaluatorTopDown(divisible4implies3);
        evalResult = evaluator.evaluate(structure);
        assertEquals(
                falseResult, getReduced(evalResult, divisible4implies3.getFreeVariableSymbols()));

        Formula prims =
                divisibleY1
                        .and(divisibleY2)
                        .and((new RelationAtom(Equals, new Variable(y1), new Variable(y2))).not())
                        .and(
                                divisibleY3
                                        .implies(
                                                (new RelationAtom(
                                                                Equals,
                                                                new Variable(y1),
                                                                new Variable(y3)))
                                                        .or(
                                                                new RelationAtom(
                                                                        Equals,
                                                                        new Variable(y2),
                                                                        new Variable(y3))))
                                        .forAll(new Variable(y3)))
                        .exists(new Variable(y2))
                        .exists(new Variable(y1));
        evaluator = new FormulaEvaluatorTopDown(prims);
        Valuation<Integer> valuation = new Valuation<>();
        TreeSet<Valuation<Integer>> expected = new TreeSet<>();
        valuation.define(x, 2);
        expected.add(valuation.clone());
        valuation.define(x, 3);
        expected.add(valuation.clone());
        valuation.define(x, 5);
        expected.add(valuation.clone());
        evalResult = evaluator.evaluate(structure);
        assertEquals(expected, getReduced(evalResult, prims.getFreeVariableSymbols()));
    }

    @Test
    public void testVariableShadowing() {
        RelationSymbol R = new RelationSymbol("R", 1, false);
        RelationSymbol E = new RelationSymbol("E", 2, false);
        VariableSymbol x = new VariableSymbol("x");
        Variable varX = new Variable(x);
        Variable varY = new Variable("y");
        Variable varT = new Variable("t");
        FunctionSymbol a = new FunctionSymbol("a", 0, false);
        FunctionTerm constant = new FunctionTerm(a);
        Formula existsLoop = new ExistentialQuantifier(varX, new RelationAtom(E, varX, varX));
        Formula relR = new RelationAtom(R, varX);

        Formula formula = (relR).and(existsLoop);

        FormulaEvaluatorTopDown<String> evaluator = new FormulaEvaluatorTopDown<>(formula);

        Set<String> elements = new TreeSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
        Universe<String> universe = new SetUniverse<>(elements);
        Structure<String> structure = new Structure<>(universe);

        Set<Tuple<String>> edges =
                new ListSet<>(
                        new Tuple<>("b", "a"),
                        new Tuple<>("b", "b"),
                        new Tuple<>("c", "b"),
                        new Tuple<>("c", "d"),
                        new Tuple<>("d", "e"),
                        new Tuple<>("e", "f"),
                        new Tuple<>("f", "g"));

        Set<Tuple<String>> nodes =
                new ListSet<>(
                        new Tuple<>("a"),
                        new Tuple<>("b"),
                        new Tuple<>("c"),
                        new Tuple<>("d"),
                        new Tuple<>("e"),
                        new Tuple<>("f"));

        structure.addRelation(E, new SetRelationImplementation<>(edges));
        structure.addRelation(R, new SetRelationImplementation<>(nodes));
        Set<Valuation<String>> valuations = evaluator.evaluate(structure);
        Set<Tuple<String>> image = new ListSet<>();

        for (Valuation<String> valuation : valuations) {
            assertTrue(valuation.isDefinedFor(x));
            image.add(new Tuple<>(valuation.map(x)));
        }

        assertTrue(image.equals(nodes));

        formula = new UniversalQuantifier(varX, new Conjunction(relR, existsLoop));
        evaluator = new FormulaEvaluatorTopDown<>(formula);
        valuations = evaluator.evaluate(structure);
        assertTrue(valuations.isEmpty());

        formula = existsLoop;
        evaluator = new FormulaEvaluatorTopDown<>(formula);
        valuations = evaluator.evaluate(structure);
        assertTrue(valuations.size() == 1);
        assertTrue(valuations.iterator().next().getDomain().isEmpty());

        formula =
                new ExistentialQuantifier(
                        varT,
                        new ExistentialQuantifier(
                                varX,
                                new ExistentialQuantifier(
                                        varY,
                                        new RelationAtom(E, constant, varX)
                                                .and(
                                                        new RelationAtom(E, varX, varY),
                                                        new RelationAtom(E, varY, varT)))));

        evaluator = new FormulaEvaluatorTopDown<>(formula);
        elements = new TreeSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        universe = new SetUniverse<>(elements);
        structure = new Structure<>(universe);

        edges =
                new ListSet<>(
                        new Tuple<>("a", "b"),
                        new Tuple<>("b", "c"),
                        new Tuple<>("c", "d"),
                        new Tuple<>("d", "e"),
                        new Tuple<>("e", "f"),
                        new Tuple<>("f", "g"),
                        new Tuple<>("g", "h"),
                        new Tuple<>("h", "a"));

        SerializableFunction<Tuple<String>, String> aConstant = (t -> "a");
        structure.addRelation(E, new SetRelationImplementation<>(edges));
        structure.addFunction(a, new RuleFunctionImplementation<>(aConstant));
        valuations = evaluator.evaluate(structure);
        assertTrue(valuations.size() == 1);
        assertTrue(valuations.iterator().next().getDomain().isEmpty());
    }

    private Set<Valuation<Integer>> getExpected(
            Set<Integer> elements, Predicate<Tuple<Integer>> isExpected) {
        Set<Valuation<Integer>> expected = new TreeSet<>();
        Valuation<Integer> valuation = new Valuation<>();
        VariableSymbol x = new VariableSymbol("x");
        VariableSymbol y = new VariableSymbol("y");
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.size(); j++) {
                if (isExpected.test(new Tuple<Integer>(i, j))) {
                    valuation.define(x, i);
                    valuation.define(y, j);
                    expected.add(valuation.clone());
                }
            }
        }
        return expected;
    }

    private Set<Valuation<Integer>> getReduced(
            Set<Valuation<Integer>> topDownResult, Set<VariableSymbol> freeVariableSymbols) {
        Set<Valuation<Integer>> reducedFormulaResult = new HashSet<>();
        for (Valuation<Integer> v : topDownResult) {
            Valuation reduced = new Valuation();
            freeVariableSymbols.forEach(s -> reduced.define(s, v.map(s)));
            reducedFormulaResult.add(reduced);
        }
        return reducedFormulaResult;
    }
}
