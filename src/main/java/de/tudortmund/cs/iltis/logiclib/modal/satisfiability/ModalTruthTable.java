package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.comparator.SubformulaComparator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Evaluator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalDefaultEvaluator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalInterpretation;
import de.tudortmund.cs.iltis.utils.collections.SerializableLabeledTable;
import java.util.ArrayList;
import java.util.List;

public class ModalTruthTable extends SerializableLabeledTable<KripkeState, ModalFormula, Boolean> {
    private KripkeStructure structure;

    public ModalTruthTable(ModalFormula formula, KripkeStructure structure) {
        this.structure = structure;
        addKripkeStates(structure);
        addSubformulae(formula);
        addTruthValues();
    }

    protected void addKripkeStates(KripkeStructure structure) {
        List<KripkeState> states = new ArrayList<>(structure.getVertexValues());
        states.sort(KripkeState::compareTo);

        for (KripkeState state : states) this.addRow(state);
    }

    protected void addSubformulae(ModalFormula formula) {
        List<ModalFormula> subformulae = new ArrayList<>(formula.getAllSubformulae());
        subformulae.sort(new SubformulaComparator(formula));
        for (ModalFormula subformula : subformulae) this.addColumn(subformula);
    }

    protected void addTruthValues() {
        for (ModalFormula subformula : this.getColumnLabels()) {
            Evaluator eval = new ModalDefaultEvaluator(subformula, this.structure, this);
            for (KripkeState state : this.getRowLabels()) {
                ModalInterpretation interpretation = new ModalInterpretation(this.structure, state);
                this.setCell(state, subformula, eval.evaluate(interpretation));
            }
        }
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private ModalTruthTable() {}
}
