package de.tudortmund.cs.iltis.logiclib.predicate.transformations;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import de.tudortmund.cs.iltis.utils.tree.transformations.UnaryPatternTransformationWithNewTree;
import java.util.ArrayList;
import java.util.List;

public class UnaryPatternTransformationWithNewFormula
        extends UnaryPatternTransformationWithNewTree<TermOrFormula> {

    // serialization
    public UnaryPatternTransformationWithNewFormula() {}

    public UnaryPatternTransformationWithNewFormula(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId) {
        super(new TreePath(), match, replace, newTreeId);
    }

    public UnaryPatternTransformationWithNewFormula(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId) {
        super(path, match, replace, newTreeId);
    }

    @Override
    public void setNewTree(TermOrFormula tree) {
        if (tree != null && !(tree instanceof Formula)) {
            throw new IllegalArgumentException(
                    "TransformationWithNewFormula need Formulae as new trees");
        }
        this.newTree = tree;
    }

    @Override
    public List<TransformationWithNewTree<TermOrFormula>> forMultipleSubtrees(
            final Iterable<TermOrFormula> trees) {
        List<TransformationWithNewTree<TermOrFormula>> transformations = new ArrayList<>();
        for (TermOrFormula tree : trees) {
            if (tree instanceof Formula) {
                UnaryPatternTransformationWithNewFormula newTrans = clone();
                newTrans.setNewTree(tree.clone());
                transformations.add(newTrans);
            }
        }
        return transformations;
    }

    @Override
    protected UnaryPatternTransformationWithNewFormula clone() {
        UnaryPatternTransformationWithNewFormula clone =
                new UnaryPatternTransformationWithNewFormula(
                        matchPattern, replacePattern, newTreeId);

        clone.setNewTree(newTree);
        return clone;
    }

    public String getNewTreeId() {
        return newTreeId;
    }
}
