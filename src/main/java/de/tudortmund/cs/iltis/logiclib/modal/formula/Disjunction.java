package de.tudortmund.cs.iltis.logiclib.modal.formula;

import java.util.Collection;
import java.util.Iterator;

/** Disjunction of modal formulae. */
@SuppressWarnings("serial")
public class Disjunction extends ModalFormula {
    @SuppressWarnings("unused")
    protected Disjunction() { // Serialization
    }

    public Disjunction(final ModalFormula subformula) {
        addChild(subformula);
    }

    public Disjunction(final ModalFormula leftSubformula, final ModalFormula... rightSubformulae) {
        addChild(leftSubformula);
        addChildren(rightSubformulae);
    }

    public Disjunction(final ModalFormula... subformulae) {
        addChildren(subformulae);
    }

    public Disjunction(
            final ModalFormula leftSubformula, final Collection<ModalFormula> rightSubformulae) {
        addChild(leftSubformula);
        for (ModalFormula subformula : rightSubformulae) addChild(subformula);
    }

    public Disjunction(final Collection<ModalFormula> subformulae) {
        for (ModalFormula subformula : subformulae) addChild(subformula);
    }

    public FormulaType getType() {
        return FormulaType.OR;
    }

    @Override
    public Disjunction clone() {
        ModalFormula[] subformulae = new ModalFormula[this.getSubformulaCount()];
        Iterator<ModalFormula> subformulaIt = this.getSubformulaIterator();
        int i = 0;
        while (subformulaIt.hasNext()) subformulae[i++] = subformulaIt.next().clone();
        return new Disjunction(subformulae);
    }
}
