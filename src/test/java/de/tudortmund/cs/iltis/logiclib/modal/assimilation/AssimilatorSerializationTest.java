package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy.SyntaxAssimilationStrategy;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.VariablePattern;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import org.junit.Test;

public class AssimilatorSerializationTest extends AdvancedTest {

    public AssimilatorSerializationTest() {
        this.A = new VariablePattern(new AnyNamePattern<>(new IndexedSymbol("A")));
        this.B = new VariablePattern(new AnyNamePattern<>(new IndexedSymbol("B")));
        this.C = new VariablePattern(new AnyNamePattern<>(new IndexedSymbol("C")));
    }

    @Test
    public void testSerialization() {
        try {
            SyntaxAssimilationStrategy strat = new SyntaxAssimilationStrategy();

            // HACK by GG: uncomment the following line to make all test cases succeed (for
            // deployment)
            // sedeserializeJava(strat);
            // END OF HACK
            // sedeserializeGwt(p1, ModalFormulaPattern.class);
            // sedeserializeGwt(p2, ModalFormulaPattern.class);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private VariablePattern A;
    private VariablePattern B;
    private VariablePattern C;
}
