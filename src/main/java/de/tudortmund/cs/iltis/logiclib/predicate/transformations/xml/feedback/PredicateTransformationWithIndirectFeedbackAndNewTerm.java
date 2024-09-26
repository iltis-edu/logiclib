package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.TransformationWithIndirectFeedbackTextsInterface;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class PredicateTransformationWithIndirectFeedbackAndNewTerm
        extends UnaryPatternTransformationWithNewTerm
        implements TransformationWithIndirectFeedbackTextsInterface<TermOrFormula> {

    // serialization
    public PredicateTransformationWithIndirectFeedbackAndNewTerm() {}

    protected int hashKey;

    public PredicateTransformationWithIndirectFeedbackAndNewTerm(
            UnaryPatternTransformationWithNewTerm transformation, int hashKey, TreePath path) {
        this.hashKey = hashKey;
        this.path = path;
        this.newTreeId = transformation.getNewTreeId();
        this.matchPattern = transformation.getMatchPattern();
        this.replacePattern = transformation.getReplacePattern();
        this.onlyAtomic = transformation.getOnlyAtomic();
    }

    @Override
    protected PredicateTransformationWithIndirectFeedbackAndNewTerm clone() {
        return new PredicateTransformationWithIndirectFeedbackAndNewTerm(this, hashKey, path);
    }

    public TreePath getPath() {
        return path;
    }

    public int getHashKey() {
        return hashKey;
    }
}
