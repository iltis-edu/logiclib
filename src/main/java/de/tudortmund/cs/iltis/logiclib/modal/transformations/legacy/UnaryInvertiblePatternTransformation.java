package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;

public class UnaryInvertiblePatternTransformation extends UnaryPatternTransformation
        implements InvertibleTransformation {

    /// * Need for serialization */
    public UnaryInvertiblePatternTransformation() { // Serialization
    }

    public UnaryInvertiblePatternTransformation(
            TreePattern<ModalFormula> match, TreePattern<ModalFormula> replace) {

        super(match, replace);
    }

    public UnaryInvertiblePatternTransformation(String match, String replace) {
        super(match, replace);
    }

    public UnaryInvertiblePatternTransformation(
            TreePath path, TreePattern<ModalFormula> match, TreePattern<ModalFormula> replace) {
        super(path, match, replace);
    }

    public UnaryInvertiblePatternTransformation(final TreePath path, String match, String replace) {
        super(path, match, replace);
    }

    @Override
    public UnaryInvertiblePatternTransformation forPath(final TreePath path) {
        return new UnaryInvertiblePatternTransformation(
                path, this.matchPattern, this.replacePattern);
    }

    @Override
    public UnaryInvertiblePatternTransformation getInverse() {
        return new UnaryInvertiblePatternTransformation(
                this.path, this.replacePattern, this.matchPattern);
    }
}
