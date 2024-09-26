package de.tudortmund.cs.iltis.logiclib.modal.formula;

import java.util.Collection;
import java.util.Iterator;

/** Conjunction of modal formulae. */
@SuppressWarnings("serial")
public class Conjunction extends ModalFormula {
    @SuppressWarnings("unused")
    protected Conjunction() { // Serialization
    }

    public Conjunction(final ModalFormula subformula) {
        addChild(subformula);
    }

    public Conjunction(final ModalFormula leftSubformula, final ModalFormula... rightSubformulae) {
        addChild(leftSubformula);
        addChildren(rightSubformulae);
    }

    public Conjunction(final ModalFormula... subformulae) {
        addChildren(subformulae);
    }

    public Conjunction(
            final ModalFormula leftSubformula, final Collection<ModalFormula> rightSubformulae) {
        addChild(leftSubformula);
        for (ModalFormula subformula : rightSubformulae) addChild(subformula);
    }

    public Conjunction(final Collection<ModalFormula> subformulae) {
        for (ModalFormula subformula : subformulae) addChild(subformula);
    }

    public FormulaType getType() {
        return FormulaType.AND;
    }

    @Override
    public Conjunction clone() {
        ModalFormula[] subformulae = new ModalFormula[this.getSubformulaCount()];
        Iterator<ModalFormula> subformulaIt = this.getSubformulaIterator();
        int i = 0;
        while (subformulaIt.hasNext()) subformulae[i++] = subformulaIt.next().clone();
        return new Conjunction(subformulae);
    }
}
