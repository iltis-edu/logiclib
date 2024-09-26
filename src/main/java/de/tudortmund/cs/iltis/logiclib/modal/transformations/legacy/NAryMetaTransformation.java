package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/** Meta transformation containing several n-ary transformations */
public class NAryMetaTransformation extends MetaTransformation implements NAryTransformation {

    public NAryMetaTransformation() { // Serialization
    }

    public NAryMetaTransformation(
            Collection<TreePath> paths, NAryTransformation... transformations) {
        this.transformations = new ArrayList<Transformation>();
        for (NAryTransformation transformation : transformations)
            this.transformations.add(transformation.forPaths(paths));
    }

    public NAryMetaTransformation(NAryTransformation... transformations) {
        this(Collections.emptyList(), transformations);
    }

    @Override
    public NAryTransformation forPaths(Collection<TreePath> paths) {
        return new NAryMetaTransformation(
                paths, transformations.toArray(new NAryTransformation[transformations.size()]));
    }
}
