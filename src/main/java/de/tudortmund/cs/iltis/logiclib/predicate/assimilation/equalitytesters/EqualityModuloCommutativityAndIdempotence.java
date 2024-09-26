package de.tudortmund.cs.iltis.logiclib.predicate.assimilation.equalitytesters;

import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import java.util.List;

public class EqualityModuloCommutativityAndIdempotence
        implements SerializableBiFunction<TermOrFormula, TermOrFormula, Boolean> {

    private RelationSymbol equalityRelation;

    public EqualityModuloCommutativityAndIdempotence() {
        equalityRelation = new RelationSymbol("=", 2, true);
    }

    public Boolean apply(TermOrFormula left, TermOrFormula right) {
        if ((left.isConjunction() && right.isConjunction())
                || (left.isDisjunction() && right.isDisjunction())
                || (left.isEquivalence() && right.isEquivalence())
                || (left.isRelation()
                        && right.isRelation()
                        && ((RelationAtom) left).getName().equals(equalityRelation)
                        && ((RelationAtom) right).getName().equals(equalityRelation))) {
            List<TermOrFormula> leftSubformulae = left.getChildren();
            List<TermOrFormula> rightSubformulae = right.getChildren();

            ListSet<TermOrFormula> leftSetOfSubformulae =
                    new ListSet<TermOrFormula>(leftSubformulae, this);
            ListSet<TermOrFormula> rightSetOfSubformulae =
                    new ListSet<TermOrFormula>(rightSubformulae, this);
            return leftSetOfSubformulae.equals(rightSetOfSubformulae);
        } else if (((left.isExistentialQuantifier() && right.isExistentialQuantifier())
                        || (left.isUniversalQuantifier() && right.isUniversalQuantifier()))
                && ((Quantifier) left).getVariable().equals(((Quantifier) right).getVariable())) {
            TermOrFormula leftSubformula = ((Quantifier) left).getFormula();
            TermOrFormula rightSubformula = ((Quantifier) right).getFormula();
            return apply(leftSubformula, rightSubformula);
        } else if (left.isNegation() && right.isNegation()) {
            TermOrFormula leftSubformula = ((Negation) left).getSubformula();
            TermOrFormula rightSubformula = ((Negation) right).getSubformula();
            return apply(leftSubformula, rightSubformula);
        } else if (left.isImplication() && right.isImplication()) {
            TermOrFormula leftPremise = ((Implication) left).getPremise();
            TermOrFormula rightPremise = ((Implication) right).getPremise();
            TermOrFormula leftConclusion = ((Implication) left).getConclusion();
            TermOrFormula rightConclusion = ((Implication) right).getConclusion();
            return apply(leftPremise, rightPremise) && apply(leftConclusion, rightConclusion);
        } else {
            return left.equals(right);
        }
    }
}
