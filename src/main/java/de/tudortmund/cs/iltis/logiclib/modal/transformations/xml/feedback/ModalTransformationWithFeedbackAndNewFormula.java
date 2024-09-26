package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTexts;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalTransformationWithFeedbackAndNewFormula
        extends TransformationWithFeedbackTexts<ModalFormula>
        implements TransformationWithNewTree<ModalFormula> {

    protected ModalFormula newTree;

    public ModalTransformationWithFeedbackAndNewFormula(
            TransformationWithNewTree<ModalFormula> transformation,
            String name,
            Map<String, String> map,
            TreePath path,
            boolean isBuggy,
            List<String> types) {
        super(transformation, name, map, path, isBuggy, types);
    }

    public ModalTransformationWithFeedbackAndNewFormula(
            TransformationWithNewTree<ModalFormula> transformation,
            String name,
            boolean isBuggy,
            List<String> types) {
        this(transformation, name, new HashMap<String, String>(), new TreePath(), isBuggy, types);
    }

    @Override
    public void setNewTree(ModalFormula newFormula) {
        ((TransformationWithNewTree<ModalFormula>) transformation).setNewTree(newFormula);
        this.newTree = newFormula;
    }

    @Override
    public ModalTransformationWithFeedbackAndNewFormula forPath(final TreePath path) {
        ModalTransformationWithFeedbackAndNewFormula clone =
                new ModalTransformationWithFeedbackAndNewFormula(
                        (TransformationWithNewTree<ModalFormula>) transformation.forPath(path),
                        name,
                        map,
                        path,
                        isBuggy,
                        types);
        clone.setNewTree(newTree);
        return clone;
    }

    @Override
    public List<TransformationWithNewTree<ModalFormula>> forMultipleSubtrees(
            Iterable<ModalFormula> formulas) {
        List<TransformationWithNewTree<ModalFormula>> transformationWithNewTrees =
                new ArrayList<>();
        for (ModalFormula formula : formulas) {
            ModalTransformationWithFeedbackAndNewFormula clone = clone();
            clone.setNewTree(formula);
            transformationWithNewTrees.add(clone);
        }
        return transformationWithNewTrees;
    }

    @Override
    public ModalTransformationWithFeedbackAndNewFormula clone() {
        ModalTransformationWithFeedbackAndNewFormula clone =
                new ModalTransformationWithFeedbackAndNewFormula(
                        (TransformationWithNewTree<ModalFormula>) transformation,
                        name,
                        map,
                        path,
                        isBuggy,
                        types);
        clone.setNewTree(newTree);
        return clone;
    }

    // serialization
    public ModalTransformationWithFeedbackAndNewFormula() {}
}
