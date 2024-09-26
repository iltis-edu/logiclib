package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class SequentialTransformation implements Transformation {
    protected TreePath path;
    protected List<Transformation> transformations;

    // needed for serialization
    public SequentialTransformation() {
        this.path = new TreePath();
        this.transformations = new ArrayList<Transformation>();
    }

    public SequentialTransformation(Transformation... transformations) {
        this(new TreePath(), transformations);
    }

    public SequentialTransformation(TreePath path, Transformation... transformations) {
        this.path = path;
        this.transformations = new ArrayList<>();
        for (Transformation transformation : transformations) {
            this.transformations.add(transformation);
        }
    }

    @Override
    public boolean isApplicable(ModalFormula formula) {
        ModalFormula transformedFormula = formula.retrieve(path);

        for (Transformation transformation : this.transformations) {
            if (transformation.isApplicable(transformedFormula)) {
                transformedFormula = transformation.apply(transformedFormula);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        ModalFormula subformula = formula.retrieve(path);
        for (Transformation transformation : this.transformations) {
            if (transformation.isApplicable(subformula)) {
                subformula = transformation.apply(subformula);
            } else {
                throw new TransformationUnapplicable(formula);
            }
        }

        return PatternTransformation.replaceSubformula(formula, this.path, subformula);
    }

    @Override
    public SequentialTransformation forPath(final TreePath path) {
        return new SequentialTransformation(path, this.getTransformationArray());
    }

    @Override
    public String toString() {
        return "SequentialTransformation [transformations:" + transformations.toString() + "]";
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    protected Transformation[] getTransformationArray() {
        return this.transformations.toArray(new Transformation[0]);
    }
}
