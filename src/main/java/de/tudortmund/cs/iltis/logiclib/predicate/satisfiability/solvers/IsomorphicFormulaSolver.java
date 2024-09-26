package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.assimilation.equalitytesters.EqualityModuloCommutativityAndIdempotence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.Answer;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.FailedSolving;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.ValidFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.*;

public class IsomorphicFormulaSolver extends FOSolver {

    @Override
    public Answer solve(Formula formula) {
        if (!formula.isEquivalence()) {
            return new FailedSolving();
        }

        Formula formula1 = (Formula) formula.getChild(0);
        Formula formula2 = (Formula) formula.getChild(1);
        List<VariableSymbol> variableSymbols1 = new ArrayList<>(formula1.getVariableSymbols());
        List<VariableSymbol> variableSymbols2 = new ArrayList<>(formula2.getVariableSymbols());

        int variableCount1 = variableSymbols1.size();

        if (variableCount1 != variableSymbols2.size()) {
            return new FailedSolving();
        }

        EqualityModuloCommutativityAndIdempotence eqTester =
                new EqualityModuloCommutativityAndIdempotence();

        List<List<VariableSymbol>> permutations = generatePermutations(variableSymbols2);
        for (List<VariableSymbol> permutation : permutations) {
            Map<VariableSymbol, VariableSymbol> variableMapping = new HashMap<>(variableCount1);
            for (int i = 0; i < variableCount1; i++) {
                variableMapping.put(permutation.get(i), variableSymbols1.get(i));
            }

            Formula permutedFormula2 =
                    (Formula) permuteFormulaByMapping(formula2.clone(), variableMapping);

            if (eqTester.apply(formula1, permutedFormula2)) {
                return new ValidFormula();
            }
        }

        return new FailedSolving();
    }

    @Override
    public Answer solveEquivalenceUnderConstraints(
            final Formula formula1, final Formula formula2, List<Formula> constraints) {
        // constraints are irrelevant for isomorphic formulas
        return solveEquivalence(formula1, formula2);
    }

    private <E> List<List<E>> generatePermutations(List<E> original) {
        if (original.isEmpty()) {
            List<List<E>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        E firstElement = original.remove(0);
        List<List<E>> returnValue = new ArrayList<>();
        List<List<E>> permutations = generatePermutations(original);
        for (List<E> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<E> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    private TermOrFormula permuteFormulaByMapping(
            TermOrFormula termOrFormula, Map<VariableSymbol, VariableSymbol> variableMapping) {
        if (termOrFormula.isVariable()) {
            return new Variable(variableMapping.get(((Variable) termOrFormula).getName()));
        } else {
            List<TermOrFormula> subtermsOrsubformulas = termOrFormula.getChildren();
            for (int i = 0; i < subtermsOrsubformulas.size(); i++) {
                termOrFormula =
                        (TermOrFormula)
                                termOrFormula.transform(
                                        new TreePath("" + i),
                                        permuteFormulaByMapping(
                                                termOrFormula.getChild(i), variableMapping));
            }
            return termOrFormula;
        }
    }
}
