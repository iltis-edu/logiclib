package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public interface FeedbackCarrierTransformationInterface<T extends Tree<T>>
        extends Transformation<T> {

    public void addFeedbackText(String key, String value);

    public String getFeedbackText(String key);
}
