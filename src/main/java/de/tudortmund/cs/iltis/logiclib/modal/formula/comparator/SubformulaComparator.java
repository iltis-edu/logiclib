package de.tudortmund.cs.iltis.logiclib.modal.formula.comparator;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.util.Comparator;

/**
 * SubformulaComparator allows to compare subformulae of the formula specified in the constructor.
 * It specifically designed for truth tables, where the least nested formulae are given before the
 * more nested ones, and, if the degree of nesting does not distinguish them, the more left ones are
 * considered before the more right ones. Variables however are handled exceptionally, which are
 * compared by their names.
 */
public class SubformulaComparator implements Comparator<ModalFormula> {
    private ModalFormula formula;
    private String formulaText;

    public SubformulaComparator(ModalFormula formula) {
        this.formula = formula;
        this.formulaText = this.formula.toString();
    }

    public int compare(ModalFormula phi, ModalFormula psi) {
        int depthPhi = phi.getDepth();
        int depthPsi = psi.getDepth();

        if (depthPhi == depthPsi) {
            if (phi.isVariable() && psi.isVariable()) {
                return phi.compareTo(psi);
            }

            final String phiText = phi.toString();
            final String psiText = psi.toString();
            int leftPosPhi = this.formulaText.indexOf(phiText);
            int leftPosPsi = this.formulaText.indexOf(psiText);
            int rightPosPhi = leftPosPhi + phiText.length();
            int rightPosPsi = leftPosPsi + psiText.length();

            int diffRightPos = rightPosPhi - rightPosPsi;
            if (diffRightPos == 0) return leftPosPhi - leftPosPsi;
            else return diffRightPos;
        }
        return depthPhi - depthPsi;
    }
}
