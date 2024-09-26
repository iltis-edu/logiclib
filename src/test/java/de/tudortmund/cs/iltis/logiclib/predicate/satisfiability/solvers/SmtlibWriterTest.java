package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import org.junit.Test;

public class SmtlibWriterTest {

    private FOSmtlibFormulaWriter writer;

    @Test
    public void testSmtlibStrings() {
        writer = new FOSmtlibFormulaWriter();

        assertString("E(x,y)", "(E x y)");
        assertString(
                "E(x,y) & (Y(x) -> (E(y,y) | Y(y)))",
                "(and (E x y) (=> (Y x) (or (E y y) (Y y))))");
        assertString(
                "forall x E(x,y) & (Y(x) -> (exists y E(y,y) | Y(y)))",
                "(and (forall ((x CustomSort)) (E x y)) (=> (Y x) (or (exists ((y CustomSort)) (E y y)) (Y y))))");
        assertString(
                "forall x forall y E(x,f(y)) & (Y(x) <-> (exists y E(y,y) | Y(g(y,x))))",
                "(and (forall ((x CustomSort)) (forall ((y CustomSort)) (E x (f y)))) (= (Y x) (or (exists ((y CustomSort)) (E y y)) (Y (g y x)))))");
    }

    private void assertString(String formulaString, String expectedString) {
        Formula formula = Formula.parse(formulaString);

        String actual = writer.write(formula);

        assert actual.equals(expectedString)
                : "Wrong string format: expected \""
                        + expectedString
                        + "\", got \""
                        + actual
                        + "\"";
    }
}
