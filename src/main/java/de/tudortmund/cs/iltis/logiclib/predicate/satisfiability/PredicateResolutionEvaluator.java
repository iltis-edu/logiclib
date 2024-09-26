package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability;

import de.tudortmund.cs.iltis.logiclib.predicate.base.Substitution;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.collections.ListSet;

public class PredicateResolutionEvaluator {

    public static boolean checkResolution(
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause,
            DisjunctiveClause resolvingClause,
            Substitution firstSubstitution,
            Substitution secondSubstitution) {

        ListSet<DisjunctiveClause> newClauses = new ListSet<>();
        try {
            newClauses =
                    checkResolution(
                            firstClause, secondClause, firstSubstitution, secondSubstitution);

        } catch (NotResolvableException e) {
            return false;
        }
        if (newClauses.contains(resolvingClause)) {
            return true;
        }
        return false;
    }

    public static boolean checkResolution(
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause,
            DisjunctiveClause resolvingClause) {

        ListSet<DisjunctiveClause> newClauses = new ListSet<>();
        try {
            newClauses = checkResolution(firstClause, secondClause);
        } catch (NotResolvableException e) {
            return false;
        }
        if (newClauses.contains(resolvingClause)) {
            return true;
        }
        return false;
    }

    public static ListSet<DisjunctiveClause> checkResolution(
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause,
            Substitution firstSubstitution,
            Substitution secondSubstitution)
            throws NotResolvableException {

        // apply substitutions
        DisjunctiveClause newFirstClause = new DisjunctiveClause();
        for (Formula subFormula : firstClause) {
            newFirstClause.add((Formula) firstSubstitution.apply(subFormula));
        }
        DisjunctiveClause newSecondClause = secondClause.clone();
        for (Formula subFormula : secondClause) {
            newSecondClause.add((Formula) secondSubstitution.apply(subFormula));
        }

        return checkResolution(newFirstClause, newSecondClause);
    }

    /**
     * calculates all possible clauses, that can be resolved from firstClause and secondClause
     *
     * @throws NotResolvableException
     */
    public static ListSet<DisjunctiveClause> checkResolution(
            DisjunctiveClause firstClause, DisjunctiveClause secondClause)
            throws NotResolvableException {

        ListSet<DisjunctiveClause> newClauses = new ListSet<>();
        for (Formula firstSubFormula : firstClause) {
            for (Formula secondSubFormula : secondClause) {
                if (firstSubFormula.isNegation()) {
                    if (firstSubFormula.getChild(0).equals(secondSubFormula)) {

                        newClauses.add(
                                resolve(
                                        firstClause,
                                        secondClause,
                                        firstSubFormula,
                                        secondSubFormula));
                    }
                } else if (secondSubFormula.isNegation()) {
                    if (secondSubFormula.getChild(0).equals(firstSubFormula)) {

                        newClauses.add(
                                resolve(
                                        firstClause,
                                        secondClause,
                                        firstSubFormula,
                                        secondSubFormula));
                    }
                }
            }
        }
        if (newClauses.size() == 0) {
            throw new NotResolvableException(firstClause, secondClause);
        }
        return newClauses;
    }

    private static DisjunctiveClause resolve(
            DisjunctiveClause firstClause,
            DisjunctiveClause secondClause,
            TermOrFormula firstSubTerm,
            TermOrFormula secondSubTerm) {

        DisjunctiveClause newFirstClause = firstClause.clone();
        DisjunctiveClause newSecondClause = secondClause.clone();
        newFirstClause.remove(firstSubTerm);
        newSecondClause.remove(secondSubTerm);

        return DisjunctiveClause.merge(newFirstClause, newSecondClause);
    }
}
