package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;

public class UnaryPatternTransformation extends PatternTransformation {

    protected TreePath path;

    /* Need for serialization */
    public UnaryPatternTransformation() {}

    public UnaryPatternTransformation(
            TreePattern<ModalFormula> match, TreePattern<ModalFormula> replace) {

        this(new TreePath(), match, replace);
    }

    public UnaryPatternTransformation(String match, String replace) {
        this(new TreePath(), match, replace);
    }

    public UnaryPatternTransformation(
            final TreePath path,
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace) {

        super(match, replace);
        this.path = path;
    }

    public UnaryPatternTransformation(final TreePath path, String match, String replace) {
        super(match, replace);
        this.path = path;
    }

    @Override
    public boolean isApplicable(final ModalFormula formula) {
        ModalFormula subformula = formula.retrieve(this.path);
        return matchPattern.matches(subformula);
    }

    @Override
    public ModalFormula apply(final ModalFormula formula) {
        ModalFormula subformula = formula.retrieve(this.path);
        Match<ModalFormula> match = matchPattern.getFirstMatchIfAny(subformula).get();
        return replaceSubformula(formula, this.path, replacePattern.createTree(match));
    }

    @Override
    public Transformation forPath(TreePath path) {
        return new UnaryPatternTransformation(path, this.matchPattern, this.replacePattern);
    }
}
