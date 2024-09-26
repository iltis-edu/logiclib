package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Set;

public class TargetedIterationAlgorithm implements Transformation {
    protected TreePath path;
    protected Transformation transformation;
    protected TreePattern<ModalFormula> targetPattern;

    public TargetedIterationAlgorithm() {}

    public TargetedIterationAlgorithm(
            TreePattern<ModalFormula> target, Transformation transformation) {

        this(new TreePath(), target, transformation);
    }

    public TargetedIterationAlgorithm(
            TreePath path, TreePattern<ModalFormula> target, Transformation transformation) {

        this.path = path;
        this.transformation = transformation;
        this.targetPattern = target;
    }

    public TargetedIterationAlgorithm(String target, Transformation transformation) {

        this(new TreePath(), target, transformation);
    }

    public TargetedIterationAlgorithm(TreePath path, String target, Transformation transformation) {

        try {
            this.targetPattern = new PatternReader().read(target);
        } catch (IncorrectParseInputException e) {
            throw new RuntimeException("Pattern parse error: " + e.getLocalizedMessage());
        }

        this.path = path;
        this.transformation = transformation;
    }

    @Override
    public boolean isApplicable(ModalFormula formula) {
        return this.transformation.isApplicable(formula);
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        while (true) {
            if (this.targetPattern.matches(formula)) {
                return formula;
            }

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
        return new TargetedIterationAlgorithm(path, targetPattern, transformation);
    }
}
