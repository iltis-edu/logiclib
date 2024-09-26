package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.util.*;

/**
 * Evaluates the formula on a given structure to all valuations of the formula's free variables that
 * satisfy the formula in the given structure.
 */
public class FormulaEvaluatorTopDown<T extends Comparable<T>> {

    Formula formula;
    Structure<T> structure;

    public FormulaEvaluatorTopDown(Formula formula) {
        this.formula = formula;
    }

    /**
     * Evaluate the formula on the given structure.
     *
     * @param structure the structure to evaluate the formula in
     * @return all valuations of the formula's free variables that satisfy the formula in the given
     *     structure
     */
    public Set<Valuation<T>> evaluate(Structure<T> structure) {
        this.structure = structure;
        Set<VariableSymbol> freeVariables;
        ValuationIterator valuations;

        freeVariables = this.formula.getFreeVariableSymbols();
        valuations = new ValuationIterator(structure, freeVariables);

        ListSet<Valuation<T>> models = new ListSet<>();
        if (valuations.hasNext()) {
            while (valuations.hasNext()) {
                Valuation<T> val = new Valuation(valuations.next());
                if (evaluate(this.formula, val)) models.add(val);
            }
        } else {
            Valuation<T> val = new Valuation();
            if (evaluate(this.formula, val)) models.add(val);
        }

        return models;
    }

    public boolean evaluate(Formula subformula, Valuation<T> valuation) {
        if (subformula.isTrue()) return evaluate((True) subformula, valuation);
        if (subformula.isFalse()) return evaluate((False) subformula, valuation);
        if (subformula.isRelation()) return evaluate((RelationAtom) subformula, valuation);
        if (subformula.isNegation()) return evaluate((Negation) subformula, valuation);
        if (subformula.isConjunction()) return evaluate((Conjunction) subformula, valuation);
        if (subformula.isDisjunction()) return evaluate((Disjunction) subformula, valuation);
        if (subformula.isImplication()) return evaluate((Implication) subformula, valuation);
        if (subformula.isEquivalence()) return evaluate((Equivalence) subformula, valuation);
        if (subformula.isExistentialQuantifier())
            return evaluate((ExistentialQuantifier) subformula, valuation);
        if (subformula.isUniversalQuantifier())
            return evaluate((UniversalQuantifier) subformula, valuation);
        throw new RuntimeException("Unexpected type of formula.");
    }

    protected boolean evaluate(True subformula, Valuation<T> valuation) {
        return true;
    }

    protected boolean evaluate(False subformula, Valuation<T> valuation) {
        return false;
    }

    protected boolean evaluate(RelationAtom relationAtom, Valuation<T> valuation) {
        ArrayList<T> values = new ArrayList<T>(relationAtom.getArity());
        RelationImplementation relation = this.structure.getRelation(relationAtom.getName());
        for (TermOrFormula term : relationAtom.getChildren()) {
            values.add(evaluate((Term) term, valuation));
        }
        Tuple<T> tuple = new Tuple<>(values);
        return relation.contains(tuple);
    }

    protected boolean evaluate(Negation negation, Valuation<T> valuation) {
        return !evaluate(negation.getSubformula(), valuation);
    }

    protected boolean evaluate(Conjunction conjunction, Valuation<T> valuation) {
        for (TermOrFormula subformula : conjunction.getChildren())
            if (!evaluate((Formula) subformula, valuation)) return false;
        return true;
    }

    protected boolean evaluate(Disjunction disjunction, Valuation<T> valuation) {
        for (TermOrFormula subformula : disjunction.getChildren())
            if (evaluate((Formula) subformula, valuation)) return true;
        return false;
    }

    protected boolean evaluate(Implication implication, Valuation<T> valuation) {
        return !evaluate((Formula) implication.getChild(0), valuation)
                || evaluate((Formula) implication.getChild(1), valuation);
    }

    protected boolean evaluate(Equivalence equivalence, Valuation<T> valuation) {
        return evaluate((Formula) equivalence.getChild(0), valuation)
                == evaluate((Formula) equivalence.getChild(1), valuation);
    }

    protected boolean evaluate(
            ExistentialQuantifier existentialQuantifier, Valuation<T> valuation) {
        Variable variable = existentialQuantifier.getVariable();
        TermOrFormula subformula = existentialQuantifier.getFormula();
        Valuation<T> scopedValuation = valuation.clone();

        for (T value : this.structure.getUniverse()) {
            scopedValuation.define(variable.getName(), value);

            if (evaluate((Formula) subformula, scopedValuation)) {
                resolveScopes(existentialQuantifier, valuation, scopedValuation);
                return true;
            }
        }

        return false;
    }

    protected boolean evaluate(UniversalQuantifier universalQuantifier, Valuation<T> valuation) {
        Variable variable = universalQuantifier.getVariable();
        TermOrFormula subformula = universalQuantifier.getFormula();
        Valuation<T> scopedValuation = valuation.clone();

        for (T value : this.structure.getUniverse()) {
            scopedValuation.define(variable.getName(), value);
            if (!evaluate((Formula) subformula, scopedValuation)) return false;
        }

        resolveScopes(universalQuantifier, valuation, scopedValuation);
        return true;
    }

    protected T evaluate(Term term, Valuation<T> valuation) {
        if (term.isConstant()) {
            FunctionSymbol f = term.getFunctionSymbol();
            FunctionImplementation<T> fInt = this.structure.getFunction(f);
            return fInt.evaluate();
        } else if (term.isVariable()) {
            VariableSymbol x = term.getFreeVariableSymbols().iterator().next();
            return valuation.map(x);
        } else {
            ArrayList<T> values = new ArrayList<>();
            for (TermOrFormula subterm : term.getChildren())
                values.add(evaluate((Term) subterm, valuation));
            Tuple<T> tuple = new Tuple<>(values);
            FunctionSymbol f = term.getFunctionSymbol();
            FunctionImplementation<T> fInt = this.structure.getFunction(f);
            return fInt.evaluate(tuple);
        }
    }

    private void resolveScopes(
            TermOrFormula formula, Valuation<T> outerValuation, Valuation<T> scopedValuation) {

        Set<VariableSymbol> freeVariables = formula.getFreeVariableSymbols();

        for (VariableSymbol symbol : freeVariables) {
            if (scopedValuation.isDefinedFor(symbol)) {
                outerValuation.define(symbol, scopedValuation.map(symbol));
            }
        }
    }
}
