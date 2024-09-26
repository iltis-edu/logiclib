package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Invalid;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Valid;
import java.util.concurrent.Callable;

public class EquivalenceTableauCallable implements Callable<Answer> {
    private Comparator comparator1, comparator2;

    public EquivalenceTableauCallable(ModalFormula formula1, ModalFormula formula2) {
        this.comparator1 = Comparator.implies(formula1, formula2);
        this.comparator2 = Comparator.implies(formula2, formula1);
    }

    @Override
    public Answer call() throws Exception {
        boolean equivalent = comparator1.test() && comparator2.test();
        return equivalent ? new Valid() : new Invalid();
    }
}
