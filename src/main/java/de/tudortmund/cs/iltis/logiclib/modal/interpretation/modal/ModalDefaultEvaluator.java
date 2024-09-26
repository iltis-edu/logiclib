package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Evaluator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Interpretation;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.ModalTruthTable;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.OptimizedModalTableau;
import de.tudortmund.cs.iltis.utils.general.Data;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ModalDefaultEvaluator extends Evaluator {
    private ModalTruthTable truthTable;
    private KripkeStructure structure;
    private OptimizedModalTableau tableau;
    private List<ModalInterpretation> models;

    public ModalDefaultEvaluator(final ModalFormula formula) {
        this(formula, null, null);
    }

    public ModalDefaultEvaluator(final ModalFormula formula, KripkeStructure structure) {
        this(formula, structure, new ModalTruthTable(formula, structure));
    }

    public ModalDefaultEvaluator(
            final ModalFormula formula, KripkeStructure structure, ModalTruthTable truthTable) {
        super(formula);
        this.truthTable = truthTable;
        this.structure = structure;
    }

    public boolean evaluate(KripkeState state) {
        return this.evaluate(new ModalInterpretation(this.structure, state));
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
        if (!(interpretation instanceof ModalInterpretation))
            throw new RuntimeException("Modal interpretation expected.");
        checkCompatibility(interpretation);

        ModalInterpretation minterpretation = (ModalInterpretation) interpretation;
        return evaluate(minterpretation);
    }

    @Override
    protected void checkCompatibility(Interpretation interpretation) {
        if (this.structure != ((ModalInterpretation) interpretation).getStructure())
            throw new IllegalArgumentException(
                    "This interpretation has a different Kripke structure!");
    }

    @Override
    public boolean isSatisfiable() {
        this.tableau = new OptimizedModalTableau(this.getFormula());
        this.models = this.tableau.getModels();
        return !this.models.isEmpty();
    }

    @Override
    public ModalInterpretation getLastModel() {
        return this.models.get(0);
    }

    public ModalTruthTable getTruthTable() {
        return this.truthTable;
    }

    private boolean evaluate(ModalInterpretation interpretation) {
        ModalFormula formula = this.getFormula();
        KripkeStructure structure = interpretation.getStructure();
        KripkeState state = interpretation.getState();

        ReadTruthTable rtt = new ReadTruthTable(state);

        if (formula.isDiamond()) {
            for (KripkeState neighborState : structure.getOutNeighborValues(state)) {
                ReadTruthTable rttns = new ReadTruthTable(neighborState);
                if (rttns.apply(formula.getSubformula(0))) return true;
            }
            return false;
        }
        if (formula.isBox()) {
            for (KripkeState neighborState : structure.getOutNeighborValues(state)) {
                ReadTruthTable rttns = new ReadTruthTable(neighborState);
                if (!rttns.apply(formula.getSubformula(0))) return false;
            }
            return true;
        }
        if (formula.isConjunction()) {
            List<Boolean> values = Data.map(formula.getSubformulae(), rtt);
            return Collections.min(values);
        }
        if (formula.isDisjunction()) {
            List<Boolean> values = Data.map(formula.getSubformulae(), rtt);
            return Collections.max(values);
        }
        if (formula.isImplication()) {
            Boolean v = rtt.apply(formula.getSubformula(0));
            Boolean w = rtt.apply(formula.getSubformula(1));
            return Boolean.compare(v, w) <= 0;
        }
        if (formula.isEquivalence()) {
            Boolean v = rtt.apply(formula.getSubformula(0));
            Boolean w = rtt.apply(formula.getSubformula(1));
            return Boolean.compare(v, w) == 0;
        }
        if (formula.isVariable()) {
            return state.getValuation().mapOrFalse((Variable) formula);
        }
        if (formula.isNegation()) return !rtt.apply(formula.getSubformula(0));
        if (formula.isTrue()) return true;
        if (formula.isFalse()) return false;
        throw new RuntimeException("Unexpected type of formula.");
    }

    class ReadTruthTable implements Function<ModalFormula, Boolean> {
        private KripkeState state;

        public ReadTruthTable(KripkeState state) {
            this.state = state;
        }

        public Boolean apply(ModalFormula subformula) {
            return truthTable.getCell(state, subformula);
        }
    }
}
