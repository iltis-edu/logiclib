package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Collection;

/** Transformation that uses arbitrary number of arguments in form of tree paths */
public interface NAryTransformation extends Transformation {

    /**
     * Creates a copy of this transformation with given arguments
     *
     * @param paths paths for new transformation
     * @return transformation with given arguments
     */
    NAryTransformation forPaths(Collection<TreePath> paths);
}
