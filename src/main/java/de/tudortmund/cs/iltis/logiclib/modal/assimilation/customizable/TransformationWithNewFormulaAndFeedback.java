package de.tudortmund.cs.iltis.logiclib.modal.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformation;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformationWithNewFormulaAndFeedback
        extends FeedbackCarrierTransformation<ModalFormula>
        implements TransformationWithNewTree<ModalFormula> {

    // serialization
    public TransformationWithNewFormulaAndFeedback() {}

    public TransformationWithNewFormulaAndFeedback(
            TransformationWithNewTree<ModalFormula> transformation) {
        this.transformation = transformation;
        this.feedbackTextMap = new HashMap<>();
    }

    public TransformationWithNewFormulaAndFeedback(
            TransformationWithNewTree<ModalFormula> transformation,
            Map<String, String> feedbackTextMap) {
        this.transformation = transformation;
        this.feedbackTextMap = feedbackTextMap;
    }

    @Override
    public void setNewTree(ModalFormula newFormula) {
        ((TransformationWithNewTree<ModalFormula>) this.transformation).setNewTree(newFormula);
    }

    @Override
    public List<TransformationWithNewTree<ModalFormula>> forMultipleSubtrees(
            final Iterable<ModalFormula> formulas) {
        List<TransformationWithNewTree<ModalFormula>> transformationWithNewFormulas =
                ((TransformationWithNewTree<ModalFormula>) transformation)
                        .forMultipleSubtrees(formulas);
        List<TransformationWithNewTree<ModalFormula>> transformationWithNewFormulasAndFeedback =
                new ArrayList<>();
        transformationWithNewFormulas.forEach(
                transformation ->
                        transformationWithNewFormulasAndFeedback.add(
                                new TransformationWithNewFormulaAndFeedback(
                                        transformation, this.feedbackTextMap)));

        return transformationWithNewFormulasAndFeedback;
    }

    @Override
    public TransformationWithNewTree<ModalFormula> forPath(TreePath path) {
        TransformationWithNewTree<ModalFormula> transformationWithNewFormula =
                ((TransformationWithNewTree<ModalFormula>) this.transformation).forPath(path);
        return new TransformationWithNewFormulaAndFeedback(
                transformationWithNewFormula, feedbackTextMap);
    }
}
