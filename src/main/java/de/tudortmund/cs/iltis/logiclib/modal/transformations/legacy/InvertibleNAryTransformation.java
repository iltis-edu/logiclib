package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Collection;

/** Invertible transformation that uses arbitrary number of arguments in form of tree paths */
public interface InvertibleNAryTransformation extends NAryTransformation, InvertibleTransformation {
    @Override
    public InvertibleNAryTransformation getInverse();

    @Override
    public InvertibleNAryTransformation forPaths(Collection<TreePath> paths);
}
