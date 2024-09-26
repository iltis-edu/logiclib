package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class MetaTransformation implements Transformation {
    protected List<Transformation> transformations;

    public MetaTransformation() { // Serialization
        this.transformations = new ArrayList<Transformation>();
    }

    public MetaTransformation(Transformation... transformations) {
        this(new TreePath(), transformations);
    }

    public MetaTransformation(TreePath path, Transformation... transformations) {
        this.transformations = new ArrayList<Transformation>();
        for (Transformation transformation : transformations)
            this.transformations.add(transformation.forPath(path));
    }

    @Override
    public boolean isApplicable(ModalFormula formula) {
        for (Transformation transformation : this.transformations)
            if (transformation.isApplicable(formula)) return true;
        return false;
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        for (Transformation transformation : this.transformations)
            if (transformation.isApplicable(formula)) return transformation.apply(formula);
        throw new TransformationUnapplicable(formula);
    }

    @Override
    public MetaTransformation forPath(final TreePath path) {
        return new MetaTransformation(path, this.getTransformationArray());
    }

    @Override
    public String toString() {
        return "MetaTransformation " + transformations.toString();
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    protected Transformation[] getTransformationArray() {
        return this.transformations.toArray(new Transformation[0]);
    }
}
