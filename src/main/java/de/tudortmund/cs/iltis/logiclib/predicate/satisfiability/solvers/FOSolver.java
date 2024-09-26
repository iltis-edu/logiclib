package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.utils.weblib.WebLibFunction;
import de.tudortmund.cs.iltis.utils.weblib.WebLibFunctionCallHandler;
import java.util.List;
import java.util.Optional;

public abstract class FOSolver {

    protected WebLibFunctionCallHandler handler;
    protected WebLibFunction function;

    protected Optional<String> answer;
    protected String sort;

    protected FOSolver() {
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

    public Answer solveEquivalence(final Formula formula1, final Formula formula2) {
        return solve(new Equivalence(formula1, formula2));
    }

    public Answer solveEquivalenceUnderConstraints(
            final Formula formula1, final Formula formula2, List<Formula> constraints) {
        if (constraints.isEmpty()) {
            return solveEquivalence(formula1, formula2);
        }

        // avoid unnecessary conjunctions
        if (constraints.size() == 1) {
            return solve(new Implication(constraints.get(0), new Equivalence(formula1, formula2)));
        }

        return solve(
                new Implication(new Conjunction(constraints), new Equivalence(formula1, formula2)));
    }

    public abstract Answer solve(final Formula formula);
}
