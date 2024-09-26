package de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.assimilation.equalitytesters.EqualityModuloCommutativityAndIdempotence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.EquivalenceTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SyntaxAssimilationStrategy implements AssimilationStrategy, Serializable {

    /* Needed for serialization */
    public SyntaxAssimilationStrategy() {}

    public SyntaxAssimilationStrategy(ModalFormula target, int maxEquivalenceTransformationCount) {
        equivalentTargets =
                new ListSet<ModalFormula>(new EqualityModuloCommutativityAndIdempotence());
        loadEquivalentTargets(target, maxEquivalenceTransformationCount);
    }

    @Override
    public boolean isComplete(ModalFormula source, ModalFormula target) {
        return equivalentTargets.contains(source);
    }

    private void loadEquivalentTargets(ModalFormula target, int maxEquivalenceTransformationCount) {
        equivalentTargets.add(target);
        if (0 == maxEquivalenceTransformationCount) {
            return;
        }
        List<Transformation> subformulaTransformations =
                FormulaAssimilator.getTransformationsForSubformulae(
                        getEquivalenceTransformations(), target);
        for (Transformation transformation : subformulaTransformations) {
            if (!transformation.isApplicable(target)) {
                continue;
            }
            ModalFormula transformedTarget = transformation.apply(target.clone());
            loadEquivalentTargets(transformedTarget, maxEquivalenceTransformationCount - 1);
        }
    }

    private static List<Transformation> getEquivalenceTransformations() {
        return Arrays.asList(
                // EquivalenceTransformations.ABSORPTION_CONJUNCTION,
                // EquivalenceTransformations.ABSORPTION_DISJUNCTION,
                EquivalenceTransformations.ADD_DOUBLE_NEGATION,
                EquivalenceTransformations.CONTRADICTION,
                EquivalenceTransformations.CONTRAPOSITION,
                EquivalenceTransformations.CONTRAPOSITION.getInverse(),
                EquivalenceTransformations.CREATE_EQUIVALENCE,
                EquivalenceTransformations.SWAP_NEGATION_IN_EQUIVALENCE,
                EquivalenceTransformations.MOVE_NEGATION_INTO_EQUIVALENCE,
                EquivalenceTransformations.CREATE_IMPLICATION,
                EquivalenceTransformations.DISTRIBUTE,
                EquivalenceTransformations.IDEMPOTENCE,
                EquivalenceTransformations.NEGATE_CONSTANT,
                EquivalenceTransformations.PULL_DEMORGAN,
                EquivalenceTransformations.PULL_DEMORGAN_SHORTCUT,
                EquivalenceTransformations.PULL_DUALITY,
                EquivalenceTransformations.PUSH_DEMORGAN,
                EquivalenceTransformations.PUSH_DEMORGAN_SHORTCUT,
                EquivalenceTransformations.PUSH_DUALITY,
                EquivalenceTransformations.REMOVE_DOUBLE_NEGATION,
                EquivalenceTransformations.REMOVE_PARENTHESES,
                EquivalenceTransformations.REPLACE_EQUIVALENCE,
                EquivalenceTransformations.REPLACE_EQUIVALENCE_ALTERNATIVE,
                EquivalenceTransformations.REPLACE_IMPLICATION,
                EquivalenceTransformations.TAUTOLOGY,
                EquivalenceTransformations.UNDISTRIBUTE,
                EquivalenceTransformations.UNNEGATE_CONSTANT);
    }

    private ListSet<ModalFormula> equivalentTargets;
}
