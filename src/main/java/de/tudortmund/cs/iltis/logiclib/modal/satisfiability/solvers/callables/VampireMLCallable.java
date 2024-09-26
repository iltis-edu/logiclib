package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.callables;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.VampireMLSolver;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import java.util.concurrent.Callable;

public class VampireMLCallable implements Callable<Answer> {

    private VampireMLSolver solver;
    private ModalFormula formula;

    public VampireMLCallable(VampireMLSolver solver, ModalFormula formula) {
        this.formula = formula;
        this.solver = solver;
    }

    @Override
    public Answer call() throws Exception {
        return solver.solve(formula);
    }
}
