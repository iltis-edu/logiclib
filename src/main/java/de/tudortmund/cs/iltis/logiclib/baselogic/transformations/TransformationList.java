package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;

public class TransformationList<T extends Tree<T>> extends ArrayList<AdornedTransformation<T>> {

    // serialization
    public TransformationList() {}

    public TransformationList(List<AdornedTransformation<T>> transformations) {
        this.addAll(transformations);
    }

    public List<Transformation<T>> getUnadornedTransformations() {
        ArrayList<Transformation<T>> result = new ArrayList<>();
        forEach(
                adornedTransformation -> {
                    if (!adornedTransformation.isOnlyPart()) {
                        result.add(adornedTransformation.getTransformation());
                    }
                });
        return result;
    }
}
