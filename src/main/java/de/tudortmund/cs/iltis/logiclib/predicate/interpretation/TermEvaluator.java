package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.List;

/**
 * Evaluates a term on a given interpretation to the element this term represents according to the
 * interpretation.
 */
public class TermEvaluator {

    private Term term;

    public TermEvaluator(Term term) {
        this.term = term;
    }

    /**
     * Evaluate the term according to the given interpretation.
     *
     * @param interpretation the interpretation
     * @param <T> the type of elements the structure contains on which the term is evaluated
     * @return the element the term represents according to the given interpretation
     */
    public <T extends Comparable<T>> T evaluate(Interpretation<T> interpretation) {
        return term.traverse(new EvaluationStrategy<>(interpretation));
    }

    /**
     * Strategy to evaluate the term according to the given interpretation bottom up.
     *
     * @param <T> the type of elements the structure contains on which the term is evaluated
     */
    private static class EvaluationStrategy<T extends Comparable<T>>
            implements TraversalStrategy<TermOrFormula, T> {

        private Interpretation<T> interpretation;

        public EvaluationStrategy(Interpretation<T> interpretation) {
            this.interpretation = interpretation;
        }

        @Override
        public T inspect(TermOrFormula subterm, List<T> childrenOutput) {
            if (subterm.isVariable()) {
                return inspect((Variable) subterm, childrenOutput);
            }
            if (subterm.isFunction()) {
                return inspect((FunctionTerm) subterm, childrenOutput);
            }
            throw new RuntimeException("Unexpected type of formula");
        }

        protected T inspect(Variable variable, List<T> childrenOutput) {
            return interpretation.getValuation().map(variable.getName());
        }

        protected T inspect(FunctionTerm functionTerm, List<T> childrenOutput) {
            FunctionImplementation<T> function =
                    interpretation.getStructure().getFunction(functionTerm.getName());
            return function.evaluate(new Tuple<>(childrenOutput));
        }
    }
}
