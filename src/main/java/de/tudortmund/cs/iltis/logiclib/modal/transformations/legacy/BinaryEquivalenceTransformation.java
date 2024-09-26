package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;

public abstract class BinaryEquivalenceTransformation implements Transformation {

    public BinaryEquivalenceTransformation() {}

    public BinaryEquivalenceTransformation(final TreePath path1, final TreePath path2) {
        this.path1 = path1;
        this.path2 = path2;
    }

    protected TreePath path1;
    protected TreePath path2;
}
