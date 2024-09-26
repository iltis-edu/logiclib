package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.DepthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters.EqualityModuloCommutativityAndIdempotence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** A depth first approach to assimilation currently not used */
public class DepthFirstModalAssimilator extends DepthFirstGeneralFormulaAssimilator<ModalFormula> {

    // serialization
    public DepthFirstModalAssimilator() {}

    public DepthFirstModalAssimilator(
            ModalFormula target,
            List<Transformation<ModalFormula>> equivalenceTransformations,
            List<Transformation<ModalFormula>> buggyTransformations) {
        super(target, equivalenceTransformations, buggyTransformations);
        this.eqTester = new EqualityModuloCommutativityAndIdempotence();
    }

    public List<Transformation<ModalFormula>> adjustTransformationsToSubformula(
            List<Transformation<ModalFormula>> transformations, ModalFormula target) {
        Collection<Variable> variables = target.getVariables();
        List<ModalFormula> variableFormulas = new ArrayList<ModalFormula>(variables.size());
        variableFormulas.addAll(variables);

        List<Transformation<ModalFormula>> adjustedTransformations =
                new ArrayList<Transformation<ModalFormula>>();
        for (Transformation correction : transformations) {
            if (correction instanceof TransformationWithNewTree) {
                adjustedTransformations.addAll(
                        ((TransformationWithNewTree<ModalFormula>) correction)
                                .forMultipleSubtrees(variableFormulas));
            } else {
                adjustedTransformations.add(correction);
            }
        }
        return adjustedTransformations;
    }
}
