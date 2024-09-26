package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.BreadthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters.EqualityModuloCommutativityAndIdempotence;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters.SemanticEquivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

/**
 * This is an updated breadth first assimilator for Modalformulae. It is based on transformations,
 * which are based on classes in utils.
 */
public class BreadthFirstModalAssimilator
        extends BreadthFirstGeneralFormulaAssimilator<ModalFormula> {

    // serialization
    public BreadthFirstModalAssimilator() {}

    public BreadthFirstModalAssimilator(
            ModalFormula target,
            List<Transformation<ModalFormula>> equivalenceTransformations,
            List<Transformation<ModalFormula>> buggyTransformations,
            boolean semantic) {
        super(target, equivalenceTransformations, buggyTransformations);

        if (semantic) {
            this.eqTester = new SemanticEquivalence();
        } else {
            this.eqTester = new EqualityModuloCommutativityAndIdempotence();
        }
    }

    @Override
    protected IterativeTransformation<ModalFormula> assimilateWithQueue(
            ModalFormula source,
            int maxTransformationCountBuggy,
            int maxTransformationCountEquivalent,
            Queue<PathToSearch> queue) {
        if (this.eqTester instanceof SemanticEquivalence) {
            // equality transformations do not change the semantics of a formula
            maxTransformationCountEquivalent = 0;
        }

        return super.assimilateWithQueue(
                source, maxTransformationCountBuggy, maxTransformationCountEquivalent, queue);
    }

    protected int getSize(ModalFormula formula) {
        return formula.getSize();
    }

    protected List<Transformation<ModalFormula>> adjustTransformationsToSubformula(
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
