package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;

public abstract class Evaluator {
    public Evaluator(final ModalFormula formula) {
        this.formula = formula;
    }

    public ModalFormula getFormula() {
        return this.formula;
    }

    public abstract boolean evaluate(Interpretation interpretation);

    public abstract boolean isSatisfiable();

    public abstract Interpretation getLastModel();

    protected void checkCompatibility(Interpretation interpretation) {
        if (!interpretation.isCompatible(this.formula))
            throw new RuntimeException("Incompatible intepretation.");
    }

    private ModalFormula formula;
}
