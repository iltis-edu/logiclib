package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public interface TransformationWithIndirectFeedbackTextsInterface<T extends Tree<T>>
        extends Transformation<T> {

    public int getHashKey();

    public TreePath getPath();
}
