package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.io.Serializable;

public interface Transformation extends Serializable {
    public abstract boolean isApplicable(final ModalFormula formula);

    public abstract ModalFormula apply(final ModalFormula formula);

    public abstract Transformation forPath(final TreePath path);
}
