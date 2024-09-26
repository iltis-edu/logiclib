package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class MLFOEmbeddingTest {

    @Test
    public void testEmbedding() {
        Set<RelationSymbol> relationSymbols = new HashSet<>();
        relationSymbols.add(new RelationSymbol("A", 1, false));
        relationSymbols.add(new RelationSymbol("B", 1, false));
        relationSymbols.add(new RelationSymbol("C", 1, false));
        relationSymbols.add(new RelationSymbol("D", 1, false));

        relationSymbols.add(new RelationSymbol("R", 2, false));

        Signature signature = new Signature(relationSymbols, new HashSet<>());

        ModalFormula input =
                ModalFormula.parse("¬◇A ∧ ( ¬( □¬(C ∧ ◇¬D) ∧ □(¬B ∨ A) ) ∧ □(¬A → B) )");
        Formula output = new MLFOEmbedding(input, "R").getEmbedding();

        FormulaReader reader = new FormulaReader();
        Formula expectedOutput =
                reader.read(
                        "∃x (¬∃y (R(x,y)∧A(y))∧(¬(∀y (R(x,y)→¬(C(y)∧∃x (R(y,x)∧¬D(x))))∧∀y (R(x,y)→(¬B(y)∨A(y))))∧∀y (R(x,y)→(¬A(y)→B(y)))))",
                        signature);

        assert expectedOutput.equals(output);

        input = ModalFormula.parse("◇□□(A ∧ (B <-> C))");
        output = new MLFOEmbedding(input, "R").getEmbedding();
        expectedOutput =
                reader.read(
                        "∃x (∃y (R(x,y) & (∀x (R(y,x) -> (∀y (R(x,y) -> (A(y) & (B(y) <-> C(y)))))))))",
                        signature);

        assert expectedOutput.equals(output);
    }
}
