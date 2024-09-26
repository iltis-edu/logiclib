package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithFeedbackTextsInterface;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredicateTransformationWithFeedbackAndNewFormula
        extends UnaryPatternTransformationWithNewFormula
        implements TransformationWithFeedbackTextsInterface<TermOrFormula> {

    // serialization
    public PredicateTransformationWithFeedbackAndNewFormula() {}

    private Map<String, String> map;
    private String name;
    private boolean isBuggy;

    private List<String> types;

    public PredicateTransformationWithFeedbackAndNewFormula(
            UnaryPatternTransformationWithNewFormula transformation,
            String name,
            boolean isBuggy,
            List<String> types) {
        this(transformation, name, new HashMap<>(), new TreePath(), isBuggy, types);
    }

    public PredicateTransformationWithFeedbackAndNewFormula(
            UnaryPatternTransformationWithNewFormula transformation,
            String name,
            Map<String, String> map,
            TreePath path,
            boolean isBuggy,
            List<String> types) {
        this.isBuggy = isBuggy;
        this.map = map;
        this.name = name;
        this.path = path;
        this.types = types;

        this.matchPattern = transformation.getMatchPattern();
        this.replacePattern = transformation.getReplacePattern();
        this.newTreeId = transformation.getNewTreeId();
    }

    private PredicateTransformationWithFeedbackAndNewFormula(
            TreePattern<TermOrFormula> matchPattern,
            TreePattern<TermOrFormula> replacePattern,
            String newTreeId,
            String name,
            Map<String, String> map,
            TreePath path,
            boolean isBuggy,
            List<String> types) {
        this.isBuggy = isBuggy;
        this.map = map;
        this.name = name;
        this.path = path;
        this.types = types;

        this.matchPattern = matchPattern;
        this.replacePattern = replacePattern;
        this.newTreeId = newTreeId;
    }

    @Override
    protected PredicateTransformationWithFeedbackAndNewFormula clone() {
        PredicateTransformationWithFeedbackAndNewFormula clone =
                new PredicateTransformationWithFeedbackAndNewFormula(
                        matchPattern, replacePattern, newTreeId, name, map, path, isBuggy, types);
        clone.setNewTree(newTree);
        return clone;
    }

    public void addFeedbackText(String key, String value) {
        map.put(key, value);
    }

    public String getFeedbackText(String id) {
        return map.getOrDefault(id, "missing value");
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public TreePath getPath() {
        return path;
    }

    @Override
    public boolean hasType(String type) {
        return types.contains(type);
    }

    public boolean isBuggy() {
        return isBuggy;
    }

    @Override
    public PredicateTransformationWithFeedbackAndNewFormula forPath(final TreePath path) {
        PredicateTransformationWithFeedbackAndNewFormula clone =
                new PredicateTransformationWithFeedbackAndNewFormula(
                        this.matchPattern,
                        this.replacePattern,
                        this.newTreeId,
                        this.name,
                        this.map,
                        path,
                        this.isBuggy,
                        this.types);
        clone.setNewTree(this.newTree);
        return clone;
    }
}
