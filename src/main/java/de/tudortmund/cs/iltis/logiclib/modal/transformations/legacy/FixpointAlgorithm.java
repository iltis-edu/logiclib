package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.Set;

public class FixpointAlgorithm implements Transformation {

    public FixpointAlgorithm() {}

    public FixpointAlgorithm(Transformation transformation) {
        this(new TreePath(), transformation);
    }

    public FixpointAlgorithm(TreePath path, Transformation transformation) {
        this.path = path;
        this.transformation = transformation;
    }

    @Override
    public boolean isApplicable(ModalFormula formula) {
        return !formula.getAllApplications(this.transformation).isEmpty();
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        while (true) {
            Set<TreePath> paths = formula.getAllApplications(this.transformation);
            TreePath firstPath = null;
            for (TreePath path : paths)
                if (path.equals(this.path) || path.isDescendantOf(this.path)) {
                    firstPath = path;
                    break;
                }
            if (firstPath == null) break;
            formula = this.transformation.forPath(firstPath).apply(formula);
        }
        return formula;
    }

    @Override
    public Transformation forPath(TreePath path) {
        return new FixpointAlgorithm(path, transformation);
    }

    @Override
    public String toString() {
        return "FixpointAlgorithm [" + transformation + "]";
    }

    protected TreePath path;
    protected Transformation transformation;
}
