package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformationUnapplicable extends RuntimeException {

    private ModalFormula formula;
    private List<TreePath> paths;

    public TransformationUnapplicable(final ModalFormula formula, final TreePath... paths) {
        this.formula = formula;
        this.paths = new ArrayList<>();
        this.paths.addAll(Arrays.asList(paths));
    }

    public ModalFormula getFormula() {
        return this.formula;
    }

    public List<TreePath> getPaths() {
        return this.paths;
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private TransformationUnapplicable() {}
}
