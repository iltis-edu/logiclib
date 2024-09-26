package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.HashMap;
import java.util.Map;

public class FeedbackCarrierTransformation<T extends Tree<T>>
        implements FeedbackCarrierTransformationInterface<T> {

    // serialization
    public FeedbackCarrierTransformation() {}

    protected Map<String, String> feedbackTextMap;

    protected Transformation<T> transformation;

    public FeedbackCarrierTransformation(Transformation<T> transformation) {
        this.transformation = transformation;
        this.feedbackTextMap = new HashMap<>();
    }

    public FeedbackCarrierTransformation(
            Transformation<T> transformation, Map<String, String> feedbackTextMap) {
        this.transformation = transformation;
        this.feedbackTextMap = feedbackTextMap;
    }

    public void addFeedbackText(String key, String value) {
        feedbackTextMap.put(key, value);
    }

    public String getFeedbackText(String key) {
        return feedbackTextMap.getOrDefault(key, "missing value");
    }

    @Override
    public boolean isApplicable(final T formula) {
        return transformation.isApplicable(formula);
    }

    @Override
    public T apply(final T formula) {
        return transformation.apply(formula);
    }

    @Override
    public Transformation<T> forPath(final TreePath path) {
        return new FeedbackCarrierTransformation(transformation.forPath(path), feedbackTextMap);
    }
}
