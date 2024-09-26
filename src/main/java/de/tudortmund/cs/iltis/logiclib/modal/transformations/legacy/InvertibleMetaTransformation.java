package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class InvertibleMetaTransformation extends MetaTransformation
        implements InvertibleTransformation {

    public InvertibleMetaTransformation() {}

    public InvertibleMetaTransformation(
            TreePath path, InvertibleTransformation... transformations) {
        super(path, transformations);
    }

    public InvertibleMetaTransformation(InvertibleTransformation... transformations) {
        super(new TreePath(), transformations);
    }

    public InvertibleMetaTransformation forPath(final TreePath path) {
        return new InvertibleMetaTransformation(path, this.getTransformationArray());
    }

    @Override
    public InvertibleMetaTransformation getInverse() {
        final int N = this.transformations.size();
        InvertibleTransformation[] invTransformations = new InvertibleTransformation[N];
        for (int i = 0; i < N; i++) {
            InvertibleTransformation trans = (InvertibleTransformation) this.transformations.get(i);
            InvertibleTransformation invTrans = (InvertibleTransformation) trans.getInverse();
            invTransformations[i] = invTrans;
        }
        return new InvertibleMetaTransformation(invTransformations);
    }

    @Override
    protected InvertibleTransformation[] getTransformationArray() {
        return this.transformations.toArray(new InvertibleTransformation[0]);
    }
}
