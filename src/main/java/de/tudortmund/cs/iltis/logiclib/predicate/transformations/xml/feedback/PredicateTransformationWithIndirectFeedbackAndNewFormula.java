package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithIndirectFeedbackTextsInterface;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;

public class PredicateTransformationWithIndirectFeedbackAndNewFormula
        extends UnaryPatternTransformationWithNewFormula
        implements TransformationWithIndirectFeedbackTextsInterface<TermOrFormula> {
    protected int hashKey;

    public PredicateTransformationWithIndirectFeedbackAndNewFormula(
            UnaryPatternTransformationWithNewFormula transformation, int hashKey, TreePath path) {
        this.hashKey = hashKey;
        this.path = path;
        this.newTreeId = transformation.getNewTreeId();
        this.matchPattern = transformation.getMatchPattern();
        this.replacePattern = transformation.getReplacePattern();
    }

    private PredicateTransformationWithIndirectFeedbackAndNewFormula(
            TreePattern<TermOrFormula> matchPattern,
            TreePattern<TermOrFormula> replacePattern,
            String newTreeId,
            int hashKey,
            TreePath path) {
        this.hashKey = hashKey;
        this.path = path;
        this.newTreeId = newTreeId;
        this.matchPattern = matchPattern;
        this.replacePattern = replacePattern;
    }

    // serialization
    public PredicateTransformationWithIndirectFeedbackAndNewFormula() {}

    public int getHashKey() {
        return hashKey;
    }

    public TreePath getPath() {
        return path;
    }

    @Override
    public PredicateTransformationWithIndirectFeedbackAndNewFormula forPath(final TreePath path) {
        PredicateTransformationWithIndirectFeedbackAndNewFormula transformation =
                new PredicateTransformationWithIndirectFeedbackAndNewFormula(
                        matchPattern, replacePattern, newTreeId, hashKey, path);
        transformation.setNewTree(this.newTree);
        return transformation;
    }

    @Override
    public PredicateTransformationWithIndirectFeedbackAndNewFormula clone() {
        PredicateTransformationWithIndirectFeedbackAndNewFormula transformation =
                new PredicateTransformationWithIndirectFeedbackAndNewFormula(
                        matchPattern, replacePattern, newTreeId, hashKey, path);
        transformation.setNewTree(this.newTree);
        return transformation;
    }
}
