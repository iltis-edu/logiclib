package de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import java.util.List;

public class EqualityModuloCommutativityAndIdempotence
        implements SerializableBiFunction<ModalFormula, ModalFormula, Boolean> {
    public Boolean apply(ModalFormula left, ModalFormula right) {
        if ((left.isConjunction() && right.isConjunction())
                || (left.isDisjunction() && right.isDisjunction())
                || (left.isEquivalence() && right.isEquivalence())) {
            List<ModalFormula> leftSubformulae = left.getSubformulae();
            List<ModalFormula> rightSubformulae = right.getSubformulae();
            ListSet<ModalFormula> leftSetOfSubformulae =
                    new ListSet<ModalFormula>(leftSubformulae, this);
            ListSet<ModalFormula> rightSetOfSubformulae =
                    new ListSet<ModalFormula>(rightSubformulae, this);
            return leftSetOfSubformulae.equals(rightSetOfSubformulae);
        } else if ((left.isNegation() && right.isNegation())
                || (left.isBox() && right.isBox())
                || (left.isDiamond() && right.isDiamond())) {
            ModalFormula leftSubformula = left.getSubformula(0);
            ModalFormula rightSubformula = right.getSubformula(0);
            return apply(leftSubformula, rightSubformula);
        } else if (left.isImplication() && right.isImplication()) {
            ModalFormula leftPremise = ((Implication) left).getPremise();
            ModalFormula rightPremise = ((Implication) right).getPremise();
            ModalFormula leftConclusion = ((Implication) left).getConclusion();
            ModalFormula rightConclusion = ((Implication) right).getConclusion();
            return apply(leftPremise, rightPremise) && apply(leftConclusion, rightConclusion);
        } else {
            return left.equals(right);
        }
    }
}
