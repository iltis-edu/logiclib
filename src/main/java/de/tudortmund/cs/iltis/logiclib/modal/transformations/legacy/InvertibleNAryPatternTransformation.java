package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Collection;
import java.util.Collections;

/**
 * Invertible transformation that uses pattern and arbitrary number of arguments in form of tree
 * paths
 */
public class InvertibleNAryPatternTransformation extends NAryPatternTransformation
        implements InvertibleNAryTransformation {

    public InvertibleNAryPatternTransformation() { // Serialization
    }

    public InvertibleNAryPatternTransformation(
            Collection<TreePath> paths,
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace) {

        super(paths, match, replace);
    }

    public InvertibleNAryPatternTransformation(
            Collection<TreePath> paths, String match, String replace) {
        super(paths, match, replace);
    }

    public InvertibleNAryPatternTransformation(
            TreePattern<ModalFormula> match, TreePattern<ModalFormula> replace) {

        super(match, replace);
    }

    public InvertibleNAryPatternTransformation(String match, String replace) {
        super(match, replace);
    }

    @Override
    public InvertibleNAryPatternTransformation getInverse() {
        return new InvertibleNAryPatternTransformation(
                this.paths, this.replacePattern, this.matchPattern);
    }

    @Override
    public InvertibleTransformation forPath(final TreePath path) {
        return new InvertibleNAryPatternTransformation(
                Collections.singleton(path), matchPattern, replacePattern);
    }

    @Override
    public InvertibleNAryTransformation forPaths(Collection<TreePath> paths) {
        return new InvertibleNAryPatternTransformation(paths, matchPattern, replacePattern);
    }
}
