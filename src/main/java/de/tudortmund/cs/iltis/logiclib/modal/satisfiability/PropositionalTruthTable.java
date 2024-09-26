package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.comparator.SubformulaComparator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Evaluator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.PropositionalDefaultEvaluator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.utils.collections.SerializableLabeledTable;
import java.util.ArrayList;
import java.util.List;

public class PropositionalTruthTable
        extends SerializableLabeledTable<Valuation, ModalFormula, Boolean> {
    public PropositionalTruthTable(ModalFormula formula) {
        if (!formula.isPropositional())
            throw new RuntimeException("The formula \"" + formula + "\" is not propositional");

        addValuations(formula);
        addSubformulae(formula);
        addTruthValues();
    }

    protected void addValuations(ModalFormula formula) {
        Valuation valuation = new Valuation();
        valuation.define(formula.getVariables(), false);

        boolean hasNext;
        do {
            this.addRow(valuation);
            hasNext = valuation.hasNext();
            if (hasNext) valuation = valuation.next();
        } while (hasNext);
    }

    protected void addSubformulae(ModalFormula formula) {
        List<ModalFormula> subformulae = new ArrayList<>(formula.getAllSubformulae());
        subformulae.sort(new SubformulaComparator(formula));
        for (ModalFormula subformula : subformulae) this.addColumn(subformula);
    }

    protected void addTruthValues() {
        for (ModalFormula subformula : this.getColumnLabels()) {
            Evaluator eval = new PropositionalDefaultEvaluator(subformula);
            for (Valuation valuation : this.getRowLabels())
                this.setCell(valuation, subformula, eval.evaluate(valuation));
        }
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private PropositionalTruthTable() {}
}
