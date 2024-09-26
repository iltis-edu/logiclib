package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Box;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Diamond;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Negation;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.List;

/** Abstract class that uses patterns for transformation */
public abstract class PatternTransformation implements Transformation {
    protected TreePattern<ModalFormula> matchPattern;
    protected TreePattern<ModalFormula> replacePattern;

    public PatternTransformation() { // Serialization
        this.matchPattern = null;
        this.replacePattern = null;
    }

    public PatternTransformation(
            TreePattern<ModalFormula> match, TreePattern<ModalFormula> replace) {

        this.matchPattern = match;
        this.replacePattern = replace;
    }

    public PatternTransformation(String match, String replace) {
        this(read(match), read(replace));
    }

    private static TreePattern<ModalFormula> read(String pattern) {
        try {
            return new PatternReader().read(pattern);
        } catch (IncorrectParseInputException e) {
            throw new RuntimeException("Pattern parse error: " + e.getLocalizedMessage());
        }
    }

    public TreePattern<ModalFormula> getMatchPattern() {
        return matchPattern;
    }

    public TreePattern<ModalFormula> getReplacePattern() {
        return replacePattern;
    }

    @Override
    public String toString() {
        return this.matchPattern + " --> " + this.replacePattern;
    }

    // TODO may be replaced with better solution
    protected static ModalFormula replaceSubformula(
            ModalFormula oldFormula, final TreePath path, ModalFormula newFormula) {

        if (path.isEmpty()) {
            return newFormula;
        }

        ModalFormula copy = oldFormula.clone();
        return replaceSubformula(copy, path, newFormula, 0);
    }

    private static ModalFormula replaceSubformula(
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

    private static ModalFormula replaceSubformula(
            final Negation copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Negation(newSubformula);
    }

    private static ModalFormula replaceSubformula(
            final Box copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Box(newSubformula);
    }

    private static ModalFormula replaceSubformula(
            final Diamond copy, final TreePath path, ModalFormula newFormula, final int index) {

        ModalFormula subformula = copy.getSubformula(path.get(index));

        ModalFormula newSubformula = replaceSubformula(subformula, path, newFormula, index + 1);

        return new Diamond(newSubformula);
    }

    private static ModalFormula replaceSubformula(
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

    private static ModalFormula replaceSubformula(
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

    private static ModalFormula replaceSubformula(
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

    private static ModalFormula replaceSubformula(
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
}
