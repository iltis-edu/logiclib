package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;

public interface TransformationTest<T extends Tree<T>> {

    boolean run(Transformation<T> transformation);
}
