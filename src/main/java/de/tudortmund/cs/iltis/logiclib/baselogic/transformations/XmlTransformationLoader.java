package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;

public interface XmlTransformationLoader<T extends Tree<T>> {

    public TransformationList<T> parseTransformationsFromFile(
            String path, TransformationRegistry<T> registry);
}
