package de.tudortmund.cs.iltis.logiclib.predicate.transformations;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.VariableBoundingPattern;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import de.tudortmund.cs.iltis.utils.tree.transformations.UnaryPatternTransformationWithNewTree;
import java.util.ArrayList;
import java.util.List;

public class UnaryPatternTransformationWithNewTerm
        extends UnaryPatternTransformationWithNewTree<TermOrFormula> {

    // indicates if the transformation is only valid for atomic terms (variables)
    protected boolean onlyAtomic;

    // serialization
    public UnaryPatternTransformationWithNewTerm() {}

    public UnaryPatternTransformationWithNewTerm(
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar) {
        super(new TreePath(), match, replace, adjustId(newTreeId));
        this.onlyAtomic = onlyAtomar;
    }

    public UnaryPatternTransformationWithNewTerm(
            final TreePath path,
            TreePattern<TermOrFormula> match,
            TreePattern<TermOrFormula> replace,
            String newTreeId,
            boolean onlyAtomar) {
        super(path, match, replace, adjustId(newTreeId));
        this.onlyAtomic = onlyAtomar;
    }

    @Override
    public void setNewTree(TermOrFormula tree) {
        if (tree != null) {
            if (!(tree instanceof Term)) {
                throw new IllegalArgumentException(
                        "TransformationWithNewTerm need Terms as new trees");
            }

            if (onlyAtomic && !tree.isVariable()) {
                throw new IllegalArgumentException("New Term must be atomic!");
            }
        }

        this.newTree = tree;
    }

    @Override
    public List<TransformationWithNewTree<TermOrFormula>> forMultipleSubtrees(
            final Iterable<TermOrFormula> trees) {
        List<TransformationWithNewTree<TermOrFormula>> transformations = new ArrayList<>();
        for (TermOrFormula tree : trees) {
            if (tree instanceof Term && !(onlyAtomic && !tree.isVariable())) {
                UnaryPatternTransformationWithNewTerm newTrans = clone();
                newTrans.setNewTree(tree.clone());
                transformations.add(newTrans);
            }
        }
        return transformations;
    }

    @Override
    public UnaryPatternTransformationWithNewTerm forPath(final TreePath path) {
        UnaryPatternTransformationWithNewTerm clone =
                new UnaryPatternTransformationWithNewTerm(
                        path, matchPattern, replacePattern, newTreeId, onlyAtomic);

        clone.setNewTree(newTree);
        return clone;
    }

    @Override
    protected UnaryPatternTransformationWithNewTerm clone() {
        UnaryPatternTransformationWithNewTerm clone =
                new UnaryPatternTransformationWithNewTerm(
                        path, matchPattern, replacePattern, newTreeId, onlyAtomic);

        clone.setNewTree(newTree);
        return clone;
    }

    @Override
    public boolean isApplicable(final TermOrFormula formula) {
        if (!(matchPattern instanceof VariableBoundingPattern)) {
            return super.isApplicable(formula);
        }

        // when checking for the free variables, the current newTree must be used explicitly for
        // matching
        return ((VariableBoundingPattern) matchPattern)
                .matchesWithExactVariable(formula.retrieve(path), (Variable) newTree);
    }

    @Override
    public TermOrFormula apply(final TermOrFormula formula) {
        if (!(matchPattern instanceof VariableBoundingPattern)) {
            return super.apply(formula);
        }

        TermOrFormula subformula = formula.retrieve(this.path);
        Match<TermOrFormula> match =
                ((VariableBoundingPattern) matchPattern)
                        .getFirstMatchIfAnyWithExactVariable(subformula, (Variable) newTree)
                        .get();
        match = match.withDefinition(new IndexedSymbol(newTreeId), newTree).get();
        return (TermOrFormula) formula.transform(this.path, replacePattern.createTree(match));
    }

    // since Variables must be specified by a named pattern, this tag is necessary to provide
    // correct matching
    protected static String adjustId(String id) {
        return id.startsWith("anyname@") ? id : "anyname@" + id;
    }

    public String getNewTreeId() {
        return newTreeId;
    }

    public boolean getOnlyAtomic() {
        return onlyAtomic;
    }
}
