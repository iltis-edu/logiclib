package de.tudortmund.cs.iltis.logiclib.modal.satisfiability;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HornSatEvaluator implements Serializable {

    // needed for serialization
    private HornSatEvaluator() {}

    private ModalFormula formula;
    private boolean satisfiable;
    private ListSet<Variable> initializationVariables;
    private Map<Variable, SerializablePair<Integer, ListSet<ModalFormula>>> possibleMarkedVariables;
    private ListSet<Variable> markedVariables;

    /**
     * @throws IllegalArgumentException if formula is not a horn formula
     */
    public HornSatEvaluator(ModalFormula formula) {
        if (!formula.isImplicativeHornFormulaWithTopLeft()) {
            throw new IllegalArgumentException(
                    formula.toHTMLString() + " should be a horn " + "formula");
        }

        this.formula = formula;
        this.initializationVariables = new ListSet<>();
        this.possibleMarkedVariables = new HashMap<>();
        this.markedVariables = new ListSet<>();
        this.calculateSatisfiability();
    }

    /**
     * @param variable that will be marked
     * @param clause the reason why variable can be marked
     * @return (true,_) the variable can be marked (false,{}) unmarked variables need to be marked
     *     first (initialization) (false,{variable}) variable is not in conclusion (false,ls) ls
     *     contains other variables out of clause, if they are not marked
     */
    public SerializablePair<Boolean, ListSet<Variable>> checkMarking(
            Variable variable, ModalFormula clause) {

        ListSet<Variable> reasons = new ListSet<>();

        // variable can be marked, if variable is in conclusion
        // and every other variable in that clause is marked
        if (!clause.isVariable()) {
            // clause must be implication
            Implication implication = (Implication) clause;
            ModalFormula premise = implication.getPremise();
            ModalFormula conclusion = implication.getConclusion();

            if (premise.isTrue() && conclusion.equals(variable)) {
                if (this.initializationVariables.contains(variable)) {
                    this.initializationVariables.remove(variable);
                    this.possibleMarkedVariables.remove(variable);
                    this.markedVariables.add(variable);

                    return new SerializablePair<>(true, reasons);
                }
            }

            if (premise.isVariable()) {
                if (!this.markedVariables.contains(premise)) {
                    reasons.add((Variable) premise);
                }
            } else {
                // premise must be conjunction
                for (ModalFormula var : premise.getSubformulae()) {
                    if (!this.markedVariables.contains(var)) {
                        reasons.add((Variable) var);
                    }
                }
            }

            if (!variable.equals(conclusion)) {
                reasons.add(variable);
            }
        }

        if (reasons.isEmpty()) {
            if (!this.initializationVariables.isEmpty()) {

                if (this.initializationVariables.contains(variable) && variable.equals(clause)) {

                    this.initializationVariables.remove(variable);
                    this.possibleMarkedVariables.remove(variable);
                    this.markedVariables.add(variable);
                    return new SerializablePair<>(true, reasons);
                } else {
                    return new SerializablePair<>(false, reasons);
                }
            }

            this.markedVariables.add(variable);
            this.possibleMarkedVariables.remove(variable);
            return new SerializablePair<>(true, reasons);
        } else {
            return new SerializablePair<>(false, reasons);
        }
    }

    public boolean isFinished() {
        if (this.possibleMarkedVariables.isEmpty()) {
            return true;
        }

        return false;
    }

    public boolean isFormulaSatisfiable() {
        return this.satisfiable;
    }

    public Map<Variable, SerializablePair<Integer, ListSet<ModalFormula>>> getPossibleMarkings() {

        return this.possibleMarkedVariables;
    }

    public ListSet<Variable> getMarkedVariables() {
        return this.markedVariables;
    }

    private void calculateSatisfiability() {
        // mark variables X with existing clause X
        for (ModalFormula subformula : this.formula.getSubformulae()) {
            if (subformula.isVariable()) {
                Variable variable = (Variable) subformula;
                this.initializationVariables.add(variable);

                addInitializationReason(variable, variable);
            } else {
                Implication implication = (Implication) subformula;
                ModalFormula premise = implication.getPremise();
                ModalFormula conclusion = implication.getConclusion();

                if (premise.isTrue() && conclusion.isVariable()) {
                    Variable variable = (Variable) conclusion;
                    this.initializationVariables.add(variable);

                    addInitializationReason(variable, implication);
                }
            }
        }

        boolean changed = true;
        int iterationLevel = 1;
        // mark variables X with existing clause, where X is positive and not
        // marked and every other variable in that clause is marked
        while (changed) {
            changed = false;

            Map<Variable, SerializablePair<Integer, ListSet<ModalFormula>>> newMarkedVariables =
                    new HashMap<>();

            for (ModalFormula subformula : this.formula.getSubformulae()) {
                if (subformula.isImplication()) {
                    Variable variable = null;
                    Implication implication = (Implication) subformula;
                    ModalFormula premise = implication.getPremise();
                    ModalFormula conclusion = implication.getConclusion();

                    if (conclusion.isVariable()
                            && !possibleMarkedVariables.containsKey(conclusion)) {

                        if (premise.isVariable()) {
                            if (possibleMarkedVariables.containsKey(premise)) {
                                variable = (Variable) conclusion;
                            }
                        } else {
                            // premise must be conjunction
                            boolean variableCanBeMarked = true;

                            for (ModalFormula var : premise.getSubformulae()) {
                                if (!possibleMarkedVariables.containsKey(var)) {
                                    variableCanBeMarked = false;
                                    break;
                                }
                            }

                            if (variableCanBeMarked) {
                                variable = (Variable) conclusion;
                            }
                        }
                    }

                    if (variable != null) {

                        if (newMarkedVariables.containsKey(variable)) {

                            SerializablePair<Integer, ListSet<ModalFormula>> causingClauses =
                                    newMarkedVariables.get(variable);

                            changed = true;
                            causingClauses.second().add(subformula);
                            newMarkedVariables.put(variable, causingClauses);
                        } else {
                            changed = true;

                            newMarkedVariables.put(
                                    variable,
                                    new SerializablePair<>(
                                            iterationLevel, new ListSet<>(subformula)));
                        }
                    }
                }
            }

            this.possibleMarkedVariables.putAll(newMarkedVariables);
            iterationLevel++;
        }
        // if there is any clause with only negative literals and all of them
        // are marked, the formula is unsatisfiable
        boolean satisfiable = true;

        for (ModalFormula subformula : this.formula.getSubformulae()) {
            if (subformula.isImplication()) {
                Implication implication = (Implication) subformula;
                ModalFormula premise = implication.getPremise();
                ModalFormula conclusion = implication.getConclusion();

                if (conclusion.isFalse()) {

                    if (premise.isVariable()) {

                        if (possibleMarkedVariables.containsKey(premise)) {

                            satisfiable = false;
                            break;
                        }
                    } else if (premise.isTrue()) {
                        satisfiable = false;
                        break;
                    } else {
                        // premise must be conjunction
                        boolean unsatisfiable = true;

                        for (ModalFormula variable : premise.getSubformulae()) {

                            if (!possibleMarkedVariables.containsKey(variable)) {

                                unsatisfiable = false;
                                break;
                            }
                        }

                        if (unsatisfiable) {
                            satisfiable = false;
                            break;
                        }
                    }
                }
            } else if (subformula.isFalse()) {
                satisfiable = false;
                break;
            }
        }

        this.satisfiable = satisfiable;
    }

    private void addInitializationReason(Variable variable, ModalFormula reason) {
        if (!possibleMarkedVariables.containsKey(variable)) {
            possibleMarkedVariables.put(variable, new SerializablePair<>(0, new ListSet<>(reason)));
            return;
        }

        ListSet<ModalFormula> previousReasons = possibleMarkedVariables.get(variable).second();
        previousReasons.add(reason);
    }
}
