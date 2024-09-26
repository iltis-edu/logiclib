package de.tudortmund.cs.iltis.logiclib.predicate.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformationInterface;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.HashMap;
import java.util.Map;

public class PredicateUnaryPatternTransformationWithNewFormulaAndFeedback
        extends UnaryPatternTransformationWithNewFormula
        implements FeedbackCarrierTransformationInterface<TermOrFormula> {

    // serialization
    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback() {}

    private Map<String, String> feedbackTextMap;

    public void addFeedbackText(String key, String value) {
        feedbackTextMap.put(key, value);
    }

    public String getFeedbackText(String key) {
        return feedbackTextMap.getOrDefault(key, "missing value");
    }

    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
            UnaryPatternTransformationWithNewFormula transformation) {
        this.matchPattern = transformation.getMatchPattern();
        this.replacePattern = transformation.getReplacePattern();
        this.newTreeId = transformation.getNewTreeId();
        this.feedbackTextMap = new HashMap<>();
    }

    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId) {
        this(new TreePath(), match, replace, newTreeId, new HashMap<>());
    }

    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId) {
        this(path, match, replace, newTreeId, new HashMap<>());
    }

    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            Map<String, String> feedbackTextMap) {
        super(new TreePath(), match, replace, newTreeId);
        this.feedbackTextMap = feedbackTextMap;
    }

    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            Map<String, String> feedbackTextMap) {
        super(path, match, replace, newTreeId);
        this.feedbackTextMap = feedbackTextMap;
    }

    @Override
    protected PredicateUnaryPatternTransformationWithNewFormulaAndFeedback clone() {
        PredicateUnaryPatternTransformationWithNewFormulaAndFeedback clone =
                new PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
                        matchPattern, replacePattern, newTreeId, feedbackTextMap);
        clone.setNewTree(newTree);
        return clone;
    }

    @Override
    public PredicateUnaryPatternTransformationWithNewFormulaAndFeedback forPath(
            final TreePath path) {
        PredicateUnaryPatternTransformationWithNewFormulaAndFeedback clone = clone();
        clone.setPath(path);
        return clone;
    }

    private void setPath(final TreePath path) {
        this.path = path;
    }
}
