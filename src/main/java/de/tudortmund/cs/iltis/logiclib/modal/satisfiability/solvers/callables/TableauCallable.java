package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Invalid;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Valid;
import java.util.concurrent.Callable;

public class TableauCallable implements Callable<Answer> {

    private Comparator comparator;

    public TableauCallable(ModalFormula formula) {
        this.comparator = Comparator.satisfiable(formula);
    }

    @Override
    public Answer call() throws Exception {
        boolean negationSatisfiable = comparator.test();
        return negationSatisfiable ? new Invalid() : new Valid();
    }
}
