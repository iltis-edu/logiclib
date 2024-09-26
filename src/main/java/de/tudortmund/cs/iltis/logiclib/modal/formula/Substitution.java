package de.tudortmund.cs.iltis.logiclib.modal.formula;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Substitution implements Serializable {
    public Substitution() {
        substitution = new TreeMap<>();
    }

    public Substitution(ModalFormula oldFormula, ModalFormula newFormula) {
        this();
        substitute(oldFormula, newFormula);
    }

    public ModalFormula apply(ModalFormula oldFormula) {
        ModalFormula newFormula = oldFormula;
        Set<TreePath> paths = newFormula.getAllOccurrences(this.substitution.keySet());

        for (TreePath path : paths) {
            ModalFormula newSubformula = substitution.get(newFormula.retrieve(path));
            newFormula = replaceSubformula(newFormula, path, newSubformula);
        }
        return newFormula;
    }

    public void substitute(ModalFormula oldFormula, ModalFormula newFormula) {
        this.substitution.put(oldFormula, newFormula);
    }

    public Substitution getInvertedSubstitution() {
        Substitution inv = new Substitution();
        substitution.forEach((k, v) -> inv.substitute(v, k));
        return inv;
    }

    private ModalFormula replaceSubformula(
            ModalFormula oldFormula, final TreePath path, ModalFormula newFormula) {

        if (path.isEmpty()) {
            return newFormula;
        }

        ModalFormula copy = oldFormula.clone();
        return replaceSubformula(copy, path, newFormula, 0);
    }

    private ModalFormula replaceSubformula(
            final ModalFormula copy,
            final TreePath path,
            ModalFormula newFormula,
            final int index) {

        if (path.size() == index) {
            return newFormula;
        } else {
            if (copy.isNegation()) {
                return replaceSubformula((Negation) copy, path, newFormula, index);
            }
            if (copy.isBox()) {
                return replaceSubformula((Box) copy, path, newFormula, index);
            }
            if (copy.isDiamond()) {
                return replaceSubformula((Diamond) copy, path, newFormula, index);
            }
            if (copy.isConjunction()) {
                return replaceSubformula((Conjunction) copy, path, newFormula, index);
            }
            if (copy.isDisjunction()) {
                return replaceSubformula((Disjunction) copy, path, newFormula, index);
            }
            if (copy.isImplication()) {
                return replaceSubformula((Implication) copy, path, newFormula, index);
            }
            // can only be Equivalence
            return replaceSubformula((Equivalence) copy, path, newFormula, index);
        }
    }

    private ModalFormula replaceSubformula(
            final Negation copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Negation(newSubformula);
    }

    private ModalFormula replaceSubformula(
            final Box copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Box(newSubformula);
    }

    private ModalFormula replaceSubformula(
            final Diamond copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Diamond(newSubformula);
    }

    private ModalFormula replaceSubformula(
            final Conjunction copy, final TreePath path, ModalFormula newFormula, final int index) {

        List<ModalFormula> newSubformulas = new ArrayList<>();

        for (int i = 0; i < copy.getSubformulaCount(); i++) {
            ModalFormula subformula = copy.getSubformula(i);

            if (i == path.get(index)) {

                newSubformulas.add(replaceSubformula(subformula, path, newFormula, index + 1));
            } else {
                newSubformulas.add(subformula);
            }
        }

        return new Conjunction(newSubformulas);
    }

    private ModalFormula replaceSubformula(
            final Disjunction copy, final TreePath path, ModalFormula newFormula, final int index) {

        List<ModalFormula> newSubformulas = new ArrayList<>();

        for (int i = 0; i < copy.getSubformulaCount(); i++) {
            ModalFormula subformula = copy.getSubformula(i);

            if (i == path.get(index)) {

                newSubformulas.add(replaceSubformula(subformula, path, newFormula, index + 1));
            } else {
                newSubformulas.add(subformula);
            }
        }

        return new Disjunction(newSubformulas);
    }

    private ModalFormula replaceSubformula(
            final Implication copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula premise = copy.getPremise();
        ModalFormula conclusion = copy.getConclusion();

        if (path.get(index) == 0) {
            premise = replaceSubformula(premise, path, newFormula, index + 1);
        } else {
            conclusion = replaceSubformula(conclusion, path, newFormula, index + 1);
        }

        return new Implication(premise, conclusion);
    }

    private ModalFormula replaceSubformula(
            final Equivalence copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula leftSubformula = copy.getLeftSubformula();
        ModalFormula rightSubformula = copy.getRightSubformula();

        if (path.get(index) == 0) {
            leftSubformula = replaceSubformula(leftSubformula, path, newFormula, index + 1);
        } else {
            rightSubformula = replaceSubformula(rightSubformula, path, newFormula, index + 1);
        }

        return new Equivalence(leftSubformula, rightSubformula);
    }

    protected TreeMap<ModalFormula, ModalFormula> substitution;
}
