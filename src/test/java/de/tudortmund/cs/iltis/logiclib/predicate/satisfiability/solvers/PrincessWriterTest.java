package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import org.junit.Test;

public class PrincessWriterTest {

    private FOPrincessFormulaWriter writer;

    @Test
    public void testPrincessStrings() {
        writer = new FOPrincessFormulaWriter("int");

        assertString("E(x,y)", " E(x,y) ");
        assertString(
                "E(x,y) & (Y(x) -> (E(y,y) | Y(y)))", "( E(x,y) & ( Y(x) -> ( E(y,y) | Y(y) )))");
        assertString(
                "forall x E(x,y) & (Y(x) -> (exists y E(y,y) | Y(y)))",
                "( \\\\forall int x ; E(x,y) & ( Y(x) -> ( \\\\exists int y ; E(y,y) | Y(y) )))");
        assertString(
                "forall x forall y E(x,f(y)) & (Y(x) <-> (exists y E(y,y) | Y(g(y,x))))",
                "( \\\\forall int x ; \\\\forall int y ; E(x,f(y)) & ( Y(x) <-> ( \\\\exists int y ; E(y,y) | Y(g(y,x)) )))");
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
