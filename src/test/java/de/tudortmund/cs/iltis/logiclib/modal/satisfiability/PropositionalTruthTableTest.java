package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation.ValuationWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.utils.collections.LabeledTableTraversal;
import de.tudortmund.cs.iltis.utils.io.writer.collections.LabeledTableLaTeXWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.BooleanWriter;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class PropositionalTruthTableTest extends AdvancedTest {
    public PropositionalTruthTableTest() {
        this.A = new Variable("A");
        this.B = new Variable("B");
    }

    @Test
    public void conjunctionTest() throws Exception {
        ModalFormula AandB = A.and(B);

        try {
            PropositionalTruthTable table = new PropositionalTruthTable(AandB);

            Valuation alpha00 = new Valuation();
            Valuation alpha01, alpha10, alpha11;
            alpha00.define(A, false);
            alpha00.define(B, false);
            alpha01 = alpha00.clone();
            alpha01.define(B, true);
            alpha10 = alpha00.clone();
            alpha10.define(A, true);
            alpha11 = alpha10.clone();
            alpha11.define(B, true);
            List<Valuation> valuations = new ArrayList<Valuation>();
            valuations.add(alpha00);
            valuations.add(alpha01);
            valuations.add(alpha10);
            valuations.add(alpha11);
            assertEqual(valuations, table.getRowLabels());

            List<ModalFormula> subformulae = new ArrayList<ModalFormula>();
            subformulae.add(A);
            subformulae.add(B);
            subformulae.add(AandB);
            assertEqual(subformulae, table.getColumnLabels());

            assertEquals(table.getCell(alpha00, A), false);
            assertEquals(table.getCell(alpha01, A), false);
            assertEquals(table.getCell(alpha10, A), true);
            assertEquals(table.getCell(alpha11, A), true);
            assertEquals(table.getCell(alpha00, B), false);
            assertEquals(table.getCell(alpha01, B), true);
            assertEquals(table.getCell(alpha10, B), false);
            assertEquals(table.getCell(alpha11, B), true);
            assertEquals(table.getCell(alpha00, AandB), false);
            assertEquals(table.getCell(alpha01, AandB), false);
            assertEquals(table.getCell(alpha10, AandB), false);
            assertEquals(table.getCell(alpha11, AandB), true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void printConjunction() {
        ModalFormula AandB = A.and(B);

        try {
            PropositionalTruthTable table = new PropositionalTruthTable(AandB);

            LabeledTableLaTeXWriter<Valuation, ModalFormula, Boolean> writer;
            writer = new LabeledTableLaTeXWriter<>(table);
            writer.setTableEnvironment("array");
            writer.setRowLabelWriter(new ValuationWriterLaTeX());
            writer.setColumnLabelWriter(new ModalFormulaWriterLaTeX());
            writer.setCellLabelWriter(new BooleanWriter());
            String output = writer.write(table);
            System.err.println("\\[\n" + output + "\\]\n");

            writer.setTableEnvironment("array");
            writer.setHorizontalDisplay(LabeledTableTraversal.HorizontalDisplay.Both);
            writer.setVerticalDisplay(LabeledTableTraversal.VerticalDisplay.Both);
            writer.setRowLabelWriter(new ValuationWriterLaTeX());
            writer.setColumnLabelWriter(new ModalFormulaWriterLaTeX());
            writer.setCellLabelWriter(new BooleanWriter());
            output = "";
            output = writer.write(table);
            System.err.println("\\[\n" + output + "\\]\n");

            writer.setTableEnvironment("array");
            writer.setHorizontalDisplay(LabeledTableTraversal.HorizontalDisplay.Both);
            writer.setVerticalDisplay(LabeledTableTraversal.VerticalDisplay.Both);
            writer.setHorizontalDirection(LabeledTableTraversal.HorizontalDirection.RightToLeft);
            writer.setRowLabelWriter(new ValuationWriterLaTeX());
            writer.setColumnLabelWriter(new ModalFormulaWriterLaTeX());
            writer.setCellLabelWriter(new BooleanWriter());
            output = "";
            output = writer.write(table);
            System.err.println("\\[\n" + output + "\\]\n");
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void printComplicatedFormula() throws Exception {
        ModalFormula phi =
                ModalFormula.parse(
                        "!(A & !B) | (C -> (!!B | A))",
                        ModalReaderProperties.createDefaultWithLatex());

        try {
            PropositionalTruthTable table = new PropositionalTruthTable(phi);

            LabeledTableLaTeXWriter<Valuation, ModalFormula, Boolean> writer;
            writer = new LabeledTableLaTeXWriter<>(table);
            writer.setTableEnvironment("array");
            writer.setRowLabelWriter(new ValuationWriterLaTeX());
            writer.setColumnLabelWriter(new ModalFormulaWriterLaTeX());
            writer.setCellLabelWriter(new BooleanWriter());
            String output = writer.write(table);
            System.err.println("\\[\n" + output + "\\]\n");
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    protected Variable A;
    protected Variable B;
}
