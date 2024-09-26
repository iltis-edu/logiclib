package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Used for creating equisatisfiable FO-formulas from ML-formulas. These FO-formulas can be used for
 * FO-satsolving (e.g. using vampire) and fall into a decidable fragment.
 */
public class MLFOEmbedding implements Serializable {

    private Signature signature;

    private ModalFormula baseFormula;

    private de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable foVariableX, foVariableY;
    private RelationSymbol reachRelation;
    private Map<Variable, RelationSymbol> variableRelationMap;

    public MLFOEmbedding(ModalFormula formula) {
        this(formula, "Reach");
    }

    public MLFOEmbedding(ModalFormula formula, String reachRelationName) {
        this.baseFormula = formula;

        this.variableRelationMap = new HashMap<>();

        this.reachRelation = new RelationSymbol(reachRelationName, 2, false);

        for (Variable variable : this.baseFormula.getVariables()) {
            RelationSymbol variableRelation = new RelationSymbol(variable.getName(), 1, false);

            variableRelationMap.put(variable, variableRelation);
        }

        this.foVariableX = new de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable("x");
        this.foVariableY = new de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable("y");
    }

    public Formula getEmbedding() {
        return new ExistentialQuantifier(
                foVariableX, translateSubformula(baseFormula, foVariableX));
    }

    private Formula translateSubformula(
            ModalFormula formula,
            de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable variable) {
        de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable dualVariable =
                getDualVariable(variable);
        switch (formula.getType()) {
            case TRUE:
                return new True();
            case FALSE:
                return new False();
            case VARIABLE:
                return new RelationAtom(variableRelationMap.get((Variable) formula), variable);
            case NEG:
                return new Negation(translateSubformula(formula.getSubformula(0), variable));
            case IMPLIES:
                return new Implication(
                        translateSubformula(formula.getSubformula(0), variable),
                        translateSubformula(formula.getSubformula(1), variable));
            case EQUIV:
                return new Equivalence(
                        translateSubformula(formula.getSubformula(0), variable),
                        translateSubformula(formula.getSubformula(1), variable));
            case AND:
                return new Conjunction(
                        formula.getSubformulae().stream()
                                .map(subformula -> translateSubformula(subformula, variable))
                                .collect(Collectors.toList()));
            case OR:
                return new Disjunction(
                        formula.getSubformulae().stream()
                                .map(subformula -> translateSubformula(subformula, variable))
                                .collect(Collectors.toList()));
            case BOX:
                return new UniversalQuantifier(
                        dualVariable,
                        new Implication(
                                new RelationAtom(reachRelation, variable, dualVariable),
                                translateSubformula(formula.getSubformula(0), dualVariable)));
            case DIAMOND:
                return new ExistentialQuantifier(
                        dualVariable,
                        new Conjunction(
                                new RelationAtom(reachRelation, variable, dualVariable),
                                translateSubformula(formula.getSubformula(0), dualVariable)));
            default:
                throw new IllegalArgumentException("Unknown formula type");
        }
    }

    /**
     * returns the opposite variable then the one given
     *
     * @param variable the variable to get the dual to
     * @return
     */
    private de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable getDualVariable(
            de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable variable) {
        if (variable.equals(foVariableX)) {
            return foVariableY;
        } else {
            return foVariableX;
        }
    }

    // serialization
    @SuppressWarnings("unused")
    private MLFOEmbedding() {}
}
