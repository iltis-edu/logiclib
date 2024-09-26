package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformationWithFeedbackTexts<T extends Tree<T>>
        implements TransformationWithFeedbackTextsInterface<T> {

    protected Transformation<T> transformation;
    protected Map<String, String> map;
    protected String name;
    protected TreePath path;
    protected boolean isBuggy;
    protected List<String> types;

    public TransformationWithFeedbackTexts(
            Transformation<T> transformation,
            String name,
            Map<String, String> map,
            TreePath path,
            boolean isBuggy,
            List<String> types) {
        this.transformation = transformation;
        this.map = map;
        this.name = name;
        this.path = path;
        this.isBuggy = isBuggy;
        this.types = types;
    }

    public TransformationWithFeedbackTexts(
            Transformation<T> transformation, String name, boolean isBuggy, List<String> types) {
        this(transformation, name, new HashMap<String, String>(), new TreePath(), isBuggy, types);
    }

    // serialization
    public TransformationWithFeedbackTexts() {}

    public boolean isBuggy() {
        return isBuggy;
    }

    public Transformation<T> getTransformation() {
        return transformation;
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
    public TransformationWithFeedbackTexts<T> forPath(final TreePath path) {
        return new TransformationWithFeedbackTexts<T>(
                transformation.forPath(path), name, map, path, isBuggy, types);
    }

    public void addFeedbackText(String id, String text) {
        if (!map.containsKey(id)) {
            map.put(id, text);
        } else {
            throw new IllegalArgumentException("Id for given feedback text already exists.");
        }
    }

    public String getFeedbackText(String id) {
        if (map.containsKey(id)) {
            return map.get(id);
        } else {
            throw new IllegalArgumentException("No feedback text to given id found.");
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public TreePath getPath() {
        return path;
    }

    @Override
    public boolean hasType(String type) {
        return types.contains(type);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TransformationWithFeedbackTexts)) {
            return false;
        }

        return transformation.equals(((TransformationWithFeedbackTexts<T>) other).transformation);
    }

    @Override
    public String toString() {
        return transformation.toString();
    }

    @Override
    public TransformationWithFeedbackTexts<T> clone() {
        return new TransformationWithFeedbackTexts<T>(
                transformation, name, map, path, isBuggy, types);
    }
}
