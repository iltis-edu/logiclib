package de.tudortmund.cs.iltis.logiclib.predicate.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformationInterface;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.HashMap;
import java.util.Map;

public class PredicateUnaryPatternTransformationWithNewTermAndFeedback
        extends UnaryPatternTransformationWithNewTerm
        implements FeedbackCarrierTransformationInterface<TermOrFormula> {

    // serialization
    public PredicateUnaryPatternTransformationWithNewTermAndFeedback() {}

    private Map<String, String> feedbackTextMap;

    public void addFeedbackText(String key, String value) {
        feedbackTextMap.put(key, value);
    }

    public String getFeedbackText(String key) {
        return feedbackTextMap.getOrDefault(key, "missing value");
    }

    public PredicateUnaryPatternTransformationWithNewTermAndFeedback(
            UnaryPatternTransformationWithNewTerm transformation) {
        this.matchPattern = transformation.getMatchPattern();
        this.replacePattern = transformation.getReplacePattern();
        this.newTreeId = transformation.getNewTreeId();
        this.onlyAtomic = transformation.getOnlyAtomic();
        this.feedbackTextMap = new HashMap<>();
    }

    public PredicateUnaryPatternTransformationWithNewTermAndFeedback(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar) {
        this(new TreePath(), match, replace, adjustId(newTreeId), onlyAtomar, new HashMap<>());
    }

    public PredicateUnaryPatternTransformationWithNewTermAndFeedback(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar) {
        this(path, match, replace, adjustId(newTreeId), onlyAtomar, new HashMap<>());
    }

    public PredicateUnaryPatternTransformationWithNewTermAndFeedback(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar,
            Map<String, String> feedbackTextMap) {
        super(new TreePath(), match, replace, adjustId(newTreeId), onlyAtomar);
        this.feedbackTextMap = feedbackTextMap;
    }

    public PredicateUnaryPatternTransformationWithNewTermAndFeedback(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar,
            Map<String, String> feedbackTextMap) {
        super(path, match, replace, adjustId(newTreeId), onlyAtomar);
        this.feedbackTextMap = feedbackTextMap;
    }

    @Override
    protected PredicateUnaryPatternTransformationWithNewTermAndFeedback clone() {
        PredicateUnaryPatternTransformationWithNewTermAndFeedback clone =
                new PredicateUnaryPatternTransformationWithNewTermAndFeedback(
                        matchPattern, replacePattern, newTreeId, onlyAtomic, feedbackTextMap);
        clone.setNewTree(newTree);
        return clone;
    }

    @Override
    public PredicateUnaryPatternTransformationWithNewTermAndFeedback forPath(final TreePath path) {
        PredicateUnaryPatternTransformationWithNewTermAndFeedback clone = clone();
        clone.setPath(path);
        return clone;
    }

    private void setPath(final TreePath path) {
        this.path = path;
    }
}
