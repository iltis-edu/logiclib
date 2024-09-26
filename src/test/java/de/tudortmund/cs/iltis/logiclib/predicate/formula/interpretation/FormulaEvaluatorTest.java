package de.tudortmund.cs.iltis.logiclib.predicate.formula.interpretation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.junit.Test;

public class FormulaEvaluatorTest {

    @Test
    public void testTrue() {
        FormulaEvaluator evaluator = new FormulaEvaluator(new True());
        Structure<Integer> structure = new Structure<Integer>(new SetUniverse<>(new TreeSet<>()));
        Set<Valuation<Integer>> result = evaluator.evaluate(structure);
        assertEquals(new TreeSet<>(Arrays.asList(new Valuation<Integer>())), result);
    }

    @Test
    public void testFalse() {
        FormulaEvaluator evaluator = new FormulaEvaluator(new False());
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

        FormulaEvaluator evaluator = new FormulaEvaluator(mod2);
        Set<Valuation<Integer>> expected =
                getExpected(
                        elements,
                        (t -> t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)));
        Set<Valuation<Integer>> result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluator(mod2.not());
        expected =
                getExpected(
                        elements,
                        (t -> !(t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1))));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluator(mod2.or(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)
                                        || t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluator(mod2.and(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 == t.getElementAtPosition(1)
                                        && t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluator(mod2.implies(mod3));
        expected =
                getExpected(
                        elements,
                        (t ->
                                t.getElementAtPosition(0) % 2 != t.getElementAtPosition(1)
                                        || t.getElementAtPosition(0) % 3
                                                == t.getElementAtPosition(1)));
        result = evaluator.evaluate(structure);
        assertEquals(expected, result);

        evaluator = new FormulaEvaluator(mod2.equivalentTo(mod3));
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
        FormulaEvaluator evaluator = new FormulaEvaluator(divisible4implies2);
        assertEquals(trueResult, evaluator.evaluate(structure));

        Formula divisible4implies3 = divisible4.implies(divisible3).forAll(new Variable(x));
        evaluator = new FormulaEvaluator(divisible4implies3);
        assertEquals(falseResult, evaluator.evaluate(structure));

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
        evaluator = new FormulaEvaluator(prims);
        Valuation<Integer> valuation = new Valuation<>();
        TreeSet<Valuation<Integer>> expected = new TreeSet<>();
        valuation.define(x, 2);
        expected.add(valuation.clone());
        valuation.define(x, 3);
        expected.add(valuation.clone());
        valuation.define(x, 5);
        expected.add(valuation.clone());
        assertEquals(expected, evaluator.evaluate(structure));
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
}
