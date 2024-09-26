package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.Collections;
import java.util.List;

public class PropositionalDefaultEvaluator extends Evaluator {
    public PropositionalDefaultEvaluator(final ModalFormula formula) {
        super(formula);
        this.lastModel = null;
    }

    @Override
    public boolean evaluate(Interpretation interpretation) {
        if (!(interpretation instanceof Valuation))
            throw new RuntimeException("Valuation expected.");
        checkCompatibility(interpretation);

        Valuation valuation = (Valuation) interpretation;
        return getFormula().traverse(new EvaluationStrategy(valuation));
    }

    @Override
    public boolean isSatisfiable() {
        Valuation valuation = new Valuation();
        valuation.define(getFormula().getVariables(), false);

        boolean hasNext = true;
        do {
            if (this.evaluate(valuation)) {
                lastModel = valuation.clone();
                return true;
            }
            hasNext = valuation.hasNext();
            if (hasNext) valuation = valuation.next();
        } while (hasNext);
        return false;
    }

    @Override
    public Valuation getLastModel() {
        return this.lastModel;
    }

    class EvaluationStrategy implements TraversalStrategy<ModalFormula, Boolean> {
        public EvaluationStrategy(Valuation valuation) {
            this.valuation = valuation;
        }

        @Override
        public Boolean inspect(final ModalFormula formula, final List<Boolean> subvalues) {
            if (formula.isConjunction()) return Collections.min(subvalues);
            if (formula.isDisjunction()) return Collections.max(subvalues);
            if (formula.isImplication())
                return Boolean.compare(subvalues.get(0), subvalues.get(1)) <= 0;
            if (formula.isEquivalence())
                return Boolean.compare(subvalues.get(0), subvalues.get(1)) == 0;
            if (formula.isVariable()) return this.valuation.map((Variable) formula);
            if (formula.isNegation()) return !subvalues.get(0);
            if (formula.isTrue()) return true;
            if (formula.isFalse()) return false;
            throw new RuntimeException("Unexpected type of formula.");
        }

        public void nextLevel() {}

        public void previousLevel() {}

        public void nextSibling() {}

        private Valuation valuation;
    }

    private Valuation lastModel;
}
