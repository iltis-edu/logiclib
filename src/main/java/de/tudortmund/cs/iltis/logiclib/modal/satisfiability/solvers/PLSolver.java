package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.weblib.WebLibFunction;
import de.tudortmund.cs.iltis.utils.weblib.WebLibFunctionCallHandler;
import java.util.Optional;

public abstract class PLSolver {

    protected WebLibFunctionCallHandler handler;
    protected WebLibFunction function;

    protected Optional<String> answer;
    protected String sort;

    protected PLSolver() {
        handler =
                new WebLibFunctionCallHandler() {
                    @Override
                    public void onSuccess(String body) {
                        answer = Optional.of(body);
                    }

                    @Override
                    public void onError(Exception exception) {
                        answer = Optional.empty();
                    }
                };
    }

    public Answer solve(ModalFormula formula) {
        if (!formula.isPropositional()) {
            throw new UnsupportedOperationException(
                    "ML sat solving is implemented with more general MLSolvers");
        }
        return checkSat(formula);
    }

    protected abstract Answer checkSat(ModalFormula formula);

    public Answer solveEquivalence(ModalFormula formula1, ModalFormula formula2) {
        return solve(new Equivalence(formula1, formula2));
    }
}
