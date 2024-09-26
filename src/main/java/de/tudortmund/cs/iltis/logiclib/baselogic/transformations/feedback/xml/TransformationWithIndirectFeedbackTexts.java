package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public class TransformationWithIndirectFeedbackTexts<T extends Tree<T>>
        implements TransformationWithIndirectFeedbackTextsInterface<T> {

    protected Transformation<T> transformation;
    protected TreePath path;

    protected int hashKey;

    public TransformationWithIndirectFeedbackTexts(
            Transformation<T> transformation, int hashKey, TreePath path) {
        this.hashKey = hashKey;
        this.path = path;
        this.transformation = transformation;
    }

    // serialization
    public TransformationWithIndirectFeedbackTexts() {}

    @Override
    public boolean isApplicable(final T formula) {
        return transformation.isApplicable(formula);
    }

    @Override
    public T apply(final T formula) {
        return transformation.apply(formula);
    }

    @Override
    public TransformationWithIndirectFeedbackTexts<T> forPath(final TreePath path) {
        return new TransformationWithIndirectFeedbackTexts<T>(
                transformation.forPath(path), hashKey, path);
    }

    public int getHashKey() {
        return hashKey;
    }

    public TreePath getPath() {
        return path;
    }

    @Override
    public TransformationWithIndirectFeedbackTexts<T> clone() {
        return new TransformationWithIndirectFeedbackTexts<T>(transformation, hashKey, path);
    }
}
