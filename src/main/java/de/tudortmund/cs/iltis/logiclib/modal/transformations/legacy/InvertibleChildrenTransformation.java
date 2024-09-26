package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class InvertibleChildrenTransformation extends ChildrenTransformation
        implements InvertibleTransformation {

    // needed for serialization
    public InvertibleChildrenTransformation() {
        super();
    }

    public InvertibleChildrenTransformation(InvertibleTransformation transformation) {
        this(new TreePath(), transformation);
    }

    public InvertibleChildrenTransformation(
            TreePath path, InvertibleTransformation transformation) {
        super(path, transformation);
    }

    @Override
    public InvertibleChildrenTransformation getInverse() {

        return new InvertibleChildrenTransformation(
                path, ((InvertibleChildrenTransformation) transformation).getInverse());
    }

    @Override
    public InvertibleChildrenTransformation forPath(final TreePath path) {
        return new InvertibleChildrenTransformation(
                path, (InvertibleTransformation) transformation);
    }
}
