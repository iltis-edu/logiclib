package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Map;

public interface TransformationWithFeedbackTextsInterface<T extends Tree<T>>
        extends Transformation<T> {

    public void addFeedbackText(String key, String value);

    public String getFeedbackText(String key);

    public Map<String, String> getMap();

    public String getName();

    public boolean isBuggy();

    public TreePath getPath();

    public boolean hasType(String type);

    @Override
    public TransformationWithFeedbackTextsInterface<T> forPath(final TreePath path);
}
