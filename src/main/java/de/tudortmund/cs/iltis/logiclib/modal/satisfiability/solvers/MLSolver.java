package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers.answertypes.Answer;

public abstract class MLSolver {

    public abstract Answer solve(final ModalFormula formula);

    public Answer solveEquivalence(final ModalFormula formula1, final ModalFormula formula2) {
        return solve(new Equivalence(formula1, formula2));
    }
}
