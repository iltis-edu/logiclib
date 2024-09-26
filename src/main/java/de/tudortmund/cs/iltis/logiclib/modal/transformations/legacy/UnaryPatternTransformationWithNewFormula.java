package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnaryPatternTransformationWithNewFormula extends UnaryPatternTransformation
        implements TransformationWithNewFormula, Serializable {
    protected String newFormulaId;
    protected ModalFormula newFormula;

    protected UnaryPatternTransformationWithNewFormula() { // Serialization
    }

    public UnaryPatternTransformationWithNewFormula(
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace,
            String newFormulaId) {

        this(new TreePath(), match, replace, newFormulaId);
    }

    public UnaryPatternTransformationWithNewFormula(
            String match, String replace, String newFormulaId) {
        this(new TreePath(), match, replace, newFormulaId);
    }

    public UnaryPatternTransformationWithNewFormula(
            final TreePath path,
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace,
            String newFormulaId) {

        super(path, match, replace);
        this.newFormulaId = newFormulaId;
    }

    public UnaryPatternTransformationWithNewFormula(
            final TreePath path, String match, String replace, String newFormulaId) {
        super(path, match, replace);
        this.newFormulaId = newFormulaId;
    }

    public void setNewFormula(ModalFormula variable) {
        this.newFormula = variable;
    }

    @Override
    public boolean isApplicable(final ModalFormula formula) {
        if (null == newFormula) {
            return false;
        }
        return super.isApplicable(formula);
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        ModalFormula subformula = formula.retrieve(this.path);
        Match<ModalFormula> match = matchPattern.getFirstMatchIfAny(subformula).get();
        match = match.withDefinition(new IndexedSymbol(newFormulaId), newFormula).get();
        return replaceSubformula(formula, this.path, replacePattern.createTree(match));
    }

    @Override
    public UnaryPatternTransformationWithNewFormula forPath(TreePath path) {
        UnaryPatternTransformationWithNewFormula clone =
                new UnaryPatternTransformationWithNewFormula(
                        path, matchPattern, replacePattern, newFormulaId);
        clone.setNewFormula(newFormula);
        return clone;
    }

    public List<TransformationWithNewFormula> forMultipleSubFormulas(
            final ModalFormula... formulas) {
        return forMultipleSubFormulas(Arrays.asList(formulas));
    }

    @Override
    public List<TransformationWithNewFormula> forMultipleSubFormulas(
            final Iterable<ModalFormula> formulas) {
        List<TransformationWithNewFormula> transformations =
                new ArrayList<TransformationWithNewFormula>();
        for (ModalFormula formula : formulas) {
            UnaryPatternTransformationWithNewFormula newTrans = clone();
            newTrans.setNewFormula(formula.clone());
            transformations.add(newTrans);
        }
        return transformations;
    }

    protected UnaryPatternTransformationWithNewFormula clone() {
        UnaryPatternTransformationWithNewFormula clone =
                new UnaryPatternTransformationWithNewFormula(
                        matchPattern, replacePattern, newFormulaId);
        clone.setNewFormula(newFormula);
        return clone;
    }
}
