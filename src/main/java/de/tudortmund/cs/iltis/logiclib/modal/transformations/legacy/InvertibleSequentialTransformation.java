package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.collections.ReverseListIterator;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class InvertibleSequentialTransformation extends SequentialTransformation
        implements InvertibleTransformation {

    // needed for serialization
    public InvertibleSequentialTransformation() {
        super();
    }

    public InvertibleSequentialTransformation(InvertibleTransformation... transformations) {
        this(new TreePath(), transformations);
    }

    public InvertibleSequentialTransformation(
            TreePath path, InvertibleTransformation... transformations) {

        super(path, transformations);
    }

    @Override
    public InvertibleSequentialTransformation getInverse() {
        ReverseListIterator<Transformation> it = new ReverseListIterator<>(transformations);
        List<InvertibleTransformation> reverseTransformations = new ArrayList<>();

        while (it.hasNext()) {

            reverseTransformations.add(((InvertibleTransformation) it.next()).getInverse());
        }

        return new InvertibleSequentialTransformation(
                path, reverseTransformations.toArray(new InvertibleTransformation[0]));
    }

    @Override
    public InvertibleSequentialTransformation forPath(final TreePath path) {

        return new InvertibleSequentialTransformation(
                path, (InvertibleTransformation[]) this.getTransformationArray());
    }
}
