package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;

public interface InvertibleTransformation extends Transformation {
    public InvertibleTransformation getInverse();

    public abstract InvertibleTransformation forPath(final TreePath path);
}
