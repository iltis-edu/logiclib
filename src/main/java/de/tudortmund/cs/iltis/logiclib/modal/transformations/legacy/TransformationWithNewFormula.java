package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.List;

public interface TransformationWithNewFormula extends Transformation {
    public void setNewFormula(ModalFormula newFormula);

    public List<TransformationWithNewFormula> forMultipleSubFormulas(
            final Iterable<ModalFormula> formulas);

    public TransformationWithNewFormula forPath(TreePath path);
}
