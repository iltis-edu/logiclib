package de.tudortmund.cs.iltis.logiclib.predicate.assimilation;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.BreadthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.predicate.assimilation.equalitytesters.EqualityModuloCommutativityAndIdempotence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.utils.tree.DefaultTraversalStrategy;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** This is an updated breadth first assimilator for Predicateformulae. */
public class BreadthFirstPredicateAssimilator
        extends BreadthFirstGeneralFormulaAssimilator<TermOrFormula> {

    // serialization
    public BreadthFirstPredicateAssimilator() {}

    public BreadthFirstPredicateAssimilator(
            TermOrFormula target,
            List<Transformation<TermOrFormula>> equivalenceTransformations,
            List<Transformation<TermOrFormula>> buggyTransformations) {
        super(target, equivalenceTransformations, buggyTransformations);
        eqTester = new EqualityModuloCommutativityAndIdempotence();
    }

    protected List<Transformation<TermOrFormula>> adjustTransformationsToSubformula(
            List<Transformation<TermOrFormula>> transformations, TermOrFormula formula) {
        Set<Term> subterms = formula.getAllSubterms();
        Set<Formula> subformulae = formula.getAllSubformulae();

        List<Transformation<TermOrFormula>> adjustedTransformations = new ArrayList<>();
        for (Transformation<TermOrFormula> transformation : transformations) {
            if (transformation instanceof UnaryPatternTransformationWithNewTerm) {
                adjustedTransformations.addAll(
                        ((TransformationWithNewTree) transformation).forMultipleSubtrees(subterms));
            } else if (transformation instanceof UnaryPatternTransformationWithNewFormula) {
                adjustedTransformations.addAll(
                        ((TransformationWithNewTree) transformation)
                                .forMultipleSubtrees(subformulae));
            } else {
                adjustedTransformations.add(transformation);
            }
        }
        return adjustedTransformations;
    }

    protected int getSize(TermOrFormula formula) {
        return formula.traverse(new SizeCounter());
    }

    private static class SizeCounter
            extends DefaultTraversalStrategy<TermOrFormula, Integer, Integer> {
        @Override
        protected Integer value(Integer collectedValue, TermOrFormula item) {
            if (item.isAtomic()) {
                return 1;
            }
            if (item.isQuantifier() || item.isNegation()) {
                return collectedValue + 1;
            }
            return collectedValue + 2;
        }

        @Override
        protected Integer collect(Integer collectedValue, Integer nextValue) {
            return collectedValue == null ? 0 : collectedValue + nextValue + 1;
        }
    }
}
