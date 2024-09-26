package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The subclasses FreeVariablePattern and NoFreeVariablePattern limit the occurrences of a specified
 * variable in specified subtrees.
 *
 * <p>This is used to check if some kinds of transformations can be applied. E.g. the formulas
 * 'forall x X' and 'X' are only equal if X does not contain x freely.
 */
public abstract class VariableBoundingPattern extends TreePattern<TermOrFormula> {

    // the pattern that needs to be matched
    protected TreePattern<TermOrFormula> pattern;

    // the subtrees of the pattern that must fulfill the freedom constraint of the specified
    // variable
    protected List<IndexedSymbol> treesToBeChecked;

    // the name of the specified variable
    protected IndexedSymbol freeVariableId;

    public VariableBoundingPattern(
            TreePattern<TermOrFormula> pattern,
            List<IndexedSymbol> treesToBeChecked,
            IndexedSymbol freeVariableId) {
        this.pattern = pattern;
        this.treesToBeChecked = treesToBeChecked;
        this.freeVariableId =
                new IndexedSymbol(
                        adjustId(freeVariableId.getName()),
                        freeVariableId.getSubscript(),
                        freeVariableId.getSuperscript());
    }

    // serialization
    public VariableBoundingPattern() {}

    @Override
    public boolean matches(final TermOrFormula formula) {
        return this.getFirstMatchIfAny(formula).isPresent();
    }

    /**
     * A more constrained version of the matches method. It limits the free Variable to be matched
     * to one predefined variable. This is used when the free variable is a new Term for the subtree
     * in {@link
     * de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm}.
     *
     * @param formula
     * @param freeVariable the new variable previously not in the tree
     * @return true, iff the free variable to be matched can be the specified variable
     */
    public boolean matchesWithExactVariable(
            final TermOrFormula formula, final Variable freeVariable) {
        return this.getFirstMatchIfAnyWithExactVariable(formula, freeVariable).isPresent();
    }

    /**
     * Checks if a match created by the main pattern fulfills the freedom constraint of the variable
     * matched
     *
     * @param match the match to be checked for the freedom constraint
     * @return true, if the match fulfills the constraint
     */
    private boolean isFittingMatch(Match<TermOrFormula> match) {
        Variable freeVariable = (Variable) match.getDefinedTree(freeVariableId).get();
        return isFittingMatch(match, freeVariable);
    }

    /**
     * Checks if a match created by the main pattern fulfills the freedom constraint of the variable
     * matched and if the variable matched is the one specified by the user.
     *
     * @param match the match to be checked for the freedom constraints
     * @return true, if the match fulfills the constraints
     */
    private boolean isFittingMatch(Match<TermOrFormula> match, Variable freeVariable) {
        for (IndexedSymbol subformulaId : treesToBeChecked) {
            Optional<List<TermOrFormula>> optionalSubformulae =
                    match.getDefinedForest(subformulaId);

            if (optionalSubformulae.isPresent()) {

                for (TermOrFormula subformula : optionalSubformulae.get()) {
                    if (subformula.getFreeVariables().contains(freeVariable)) {
                        return checksForFreeVariable();
                    }
                }
            }
        }
        return !checksForFreeVariable();
    }

    public Optional<Match<TermOrFormula>> getFirstMatchIfAnyWithExactVariable(
            final TermOrFormula formula, final Variable freeVariable) {
        Set<Match<TermOrFormula>> matches = pattern.getAllMatches(formula);
        for (Match<TermOrFormula> match : matches) {
            if (isFittingMatch(match, freeVariable)) {
                return Optional.of(match);
            }
        }
        return Optional.empty();
    }

    /**
     * @return true, iff and only if the pattern matches if a free occurrence of freeVariableId
     *     exists.
     */
    protected abstract boolean checksForFreeVariable();

    @Override
    public Optional<Match<TermOrFormula>> getFirstMatchIfAny(final TermOrFormula formula) {
        Set<Match<TermOrFormula>> matches = pattern.getAllMatches(formula);
        for (Match<TermOrFormula> match : matches) {
            if (isFittingMatch(match)) {
                return Optional.of(match);
            }
        }
        return Optional.empty();
    }

    // since Variables must be specified by a named pattern, this tag is necessary to provide
    // correct matching
    protected static String adjustId(String id) {
        return id.startsWith("anyname@") ? id : "anyname@" + id;
    }
}
