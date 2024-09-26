package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Collection;
import java.util.Collections;

/** Invertible meta transformation containing several invertible n-ary transformations */
public class InvertibleNAryMetaTransformation extends NAryMetaTransformation
        implements InvertibleNAryTransformation {

    public InvertibleNAryMetaTransformation() { // Serialization
    }

    public InvertibleNAryMetaTransformation(
            Collection<TreePath> paths, InvertibleNAryTransformation... transformations) {
        super(paths, transformations);
    }

    public InvertibleNAryMetaTransformation(InvertibleNAryTransformation... transformations) {
        super(Collections.emptyList(), transformations);
    }

    @Override
    public InvertibleNAryMetaTransformation forPaths(Collection<TreePath> paths) {
        return new InvertibleNAryMetaTransformation(paths, this.getTransformationArray());
    }

    @Override
    public InvertibleNAryMetaTransformation forPath(final TreePath path) {
        return new InvertibleNAryMetaTransformation(
                Collections.singleton(path), this.getTransformationArray());
    }

    @Override
    public InvertibleNAryMetaTransformation getInverse() {
        final int N = this.transformations.size();
        InvertibleNAryTransformation[] invTransformations = new InvertibleNAryTransformation[N];
        for (int i = 0; i < N; i++) {
            InvertibleNAryTransformation trans =
                    (InvertibleNAryTransformation) this.transformations.get(i);
            InvertibleNAryTransformation invTrans = trans.getInverse();
            invTransformations[i] = invTrans;
        }
        return new InvertibleNAryMetaTransformation(invTransformations);
    }

    @Override
    protected InvertibleNAryTransformation[] getTransformationArray() {
        return this.transformations.toArray(new InvertibleNAryTransformation[0]);
    }
}
