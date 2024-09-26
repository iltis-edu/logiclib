package de.tudortmund.cs.iltis.logiclib.predicate.formula.interpretation;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class TermEvaluatorTest {

    @Test
    public void testVariable() {
        Set<String> elements = new HashSet<>(Arrays.asList("a", "b", "c"));
        Universe<String> universe = new SetUniverse<>(elements);
        Structure<String> structure = new Structure<>(universe);

        VariableSymbol A = new VariableSymbol("A");
        Valuation<String> valuation = new Valuation<>();
        valuation.define(A, "a");

        TermEvaluator evaluator = new TermEvaluator(new Variable(A));
        String result = evaluator.evaluate(new Interpretation<>(structure, valuation));
        assertEquals("a", result);

        valuation.define(A, "b");
        result = evaluator.evaluate(new Interpretation<>(structure, valuation));
        assertEquals("b", result);
    }

    @Test
    public void testFunction() {
        Set<Integer> elements = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        Universe<Integer> universe = new SetUniverse<>(elements);
        Structure<Integer> structure = new Structure<>(universe);

        FunctionSymbol mod = new FunctionSymbol("mod", 2, false);
        FunctionSymbol id = new FunctionSymbol("id", 1, false);
        SerializableFunction<Tuple<Integer>, Integer> modFunction =
                (t -> t.getElementAtPosition(0) % t.getElementAtPosition(1));
        SerializableFunction<Tuple<Integer>, Integer> idFunction = (t -> t.getElementAtPosition(0));
        structure.addFunction(mod, new RuleFunctionImplementation<>(modFunction));
        structure.addFunction(id, new RuleFunctionImplementation<>(idFunction));

        VariableSymbol X = new VariableSymbol("X");
        VariableSymbol Y = new VariableSymbol("Y");
        Valuation<Integer> valuation = new Valuation<>();

        TermEvaluator evaluator =
                new TermEvaluator(
                        new FunctionTerm(
                                mod, new FunctionTerm(id, new Variable(X)), new Variable(Y)));
        valuation.define(X, 3);
        valuation.define(Y, 2);
        Integer result = evaluator.evaluate(new Interpretation<>(structure, valuation));
        assertEquals(new Integer(1), result);

        valuation.define(X, 5);
        valuation.define(Y, 3);
        result = evaluator.evaluate(new Interpretation<>(structure, valuation));
        assertEquals(new Integer(2), result);
    }
}
