package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithIndirectFeedbackTexts;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.List;

public class ModalTransformationWithIndirectFeedbackAndNewFormula
        extends TransformationWithIndirectFeedbackTexts<ModalFormula>
        implements TransformationWithNewTree<ModalFormula> {

    // serialization
    public ModalTransformationWithIndirectFeedbackAndNewFormula() {}

    public ModalTransformationWithIndirectFeedbackAndNewFormula(
            TransformationWithNewTree<ModalFormula> transformation, int hashKey, TreePath path) {
        super(transformation, hashKey, path);
    }

    @Override
    public void setNewTree(ModalFormula newFormula) {
        ((TransformationWithNewTree<ModalFormula>) transformation).setNewTree(newFormula);
    }

    @Override
    public ModalTransformationWithIndirectFeedbackAndNewFormula forPath(final TreePath path) {
        return new ModalTransformationWithIndirectFeedbackAndNewFormula(
                (TransformationWithNewTree<ModalFormula>) transformation.forPath(path),
                hashKey,
                path);
    }

    @Override
    public List<TransformationWithNewTree<ModalFormula>> forMultipleSubtrees(
            Iterable<ModalFormula> formulas) {
        List<TransformationWithNewTree<ModalFormula>> transformations =
                ((TransformationWithNewTree<ModalFormula>) transformation)
                        .forMultipleSubtrees(formulas);
        List<TransformationWithNewTree<ModalFormula>> feedbackTransformations = new ArrayList<>();
        for (TransformationWithNewTree<ModalFormula> transformation : transformations) {
            feedbackTransformations.add(
                    new ModalTransformationWithIndirectFeedbackAndNewFormula(
                            transformation, hashKey, new TreePath()));
        }
        return feedbackTransformations;
    }
}
