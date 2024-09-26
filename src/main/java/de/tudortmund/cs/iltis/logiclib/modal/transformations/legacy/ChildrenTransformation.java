package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class ChildrenTransformation implements Transformation {
    protected TreePath path;
    protected Transformation transformation;

    // needed for serialization
    public ChildrenTransformation() {
        this.path = new TreePath();
    }

    public ChildrenTransformation(Transformation transformation) {
        this(new TreePath(), transformation);
    }

    public ChildrenTransformation(TreePath path, Transformation transformation) {
        this.path = path;
        this.transformation = transformation;
    }

    @Override
    public boolean isApplicable(final ModalFormula formula) {
        ModalFormula subformula = formula.retrieve(this.path);

        for (ModalFormula child : subformula.getSubformulae()) {
            if (!transformation.isApplicable(child)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        ModalFormula result = formula;
        ModalFormula subformula = formula.retrieve(this.path);

        int childrenIndex = 0;
        for (ModalFormula child : subformula.getSubformulae()) {

            result =
                    PatternTransformation.replaceSubformula(
                            result, path.clone().child(childrenIndex), transformation.apply(child));

            childrenIndex++;
        }

        return result;
    }

    @Override
    public ChildrenTransformation forPath(final TreePath path) {
        return new ChildrenTransformation(path, transformation);
    }

    @Override
    public String toString() {
        return "ChildrenTransformation [transformation:" + transformation.toString() + "]";
    }
}
