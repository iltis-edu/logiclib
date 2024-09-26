package de.tudortmund.cs.iltis.logiclib.modal.formula;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CloneTest {

    public CloneTest() {
        top = new True();
        bot = new False();
        A = new Variable("A");
        B = new Variable("B");
    }

    @Test
    public void testAtomic() {
        assertTrue(top.clone().equals(top));
        assertTrue(bot.clone().equals(bot));
        assertTrue(A.clone().equals(A));
        assertTrue(B.clone().equals(B));
        assertFalse(A.clone().equals(B));
        assertFalse(B.clone().equals(A));
    }

    @Test
    public void testNegation() {
        TreePath path1 = new TreePath("-0");

        Negation negA = A.not();
        Negation negB = B.not();
        Negation copyNegA = negA.clone();
        Negation copyNegB = negB.clone();

        assertTrue(copyNegA.equals(negA));
        assertTrue(copyNegB.equals(negB));
        assertFalse(copyNegA.equals(negB));

        negA = (Negation) replaceSubformula(negA, path1, B);
        assertEquals(negA, negB);
        assertFalse(copyNegA.equals(negA));
        assertTrue(copyNegA.equals(copyNegA));
        assertFalse(copyNegA.equals(negB));
        assertFalse(copyNegA.equals(copyNegB));
    }

    @Test
    public void testDiamond() {
        TreePath path1 = new TreePath("-0");

        Diamond diaA = A.diamond();
        Diamond diaB = B.diamond();
        Diamond copyDiaA = diaA.clone();
        Diamond copyDiaB = diaB.clone();

        assertTrue(copyDiaA.equals(diaA));
        assertTrue(copyDiaB.equals(diaB));
        assertFalse(copyDiaA.equals(diaB));

        diaA = (Diamond) replaceSubformula(diaA, path1, B);
        assertTrue(diaB.equals(diaA));
        assertFalse(copyDiaA.equals(diaA));
        assertTrue(copyDiaA.equals(copyDiaA));
        assertFalse(copyDiaA.equals(diaB));
        assertFalse(copyDiaA.equals(copyDiaB));
    }

    @Test
    public void testBox() {
        TreePath path1 = new TreePath("-0");

        Box boxA = A.box();
        Box boxB = B.box();
        Box copyBoxA = boxA.clone();
        Box copyBoxB = boxB.clone();

        assertTrue(copyBoxA.equals(boxA));
        assertTrue(copyBoxB.equals(boxB));
        assertFalse(copyBoxA.equals(boxB));

        boxA = (Box) replaceSubformula(boxA, path1, B);
        assertTrue(boxB.equals(boxA));
        assertFalse(copyBoxA.equals(boxA));
        assertTrue(copyBoxA.equals(copyBoxA));
        assertFalse(copyBoxA.equals(boxB));
        assertFalse(copyBoxA.equals(copyBoxB));
    }

    @Test
    public void testConjunction() {
        TreePath path1 = new TreePath("-0");
        TreePath path2 = new TreePath("-1");

        Conjunction andA = A.and(B);
        Conjunction andB = B.and(A);
        Conjunction copyAndA = andA.clone();
        Conjunction copyAndB = andB.clone();

        assertTrue(copyAndA.equals(andA));
        assertTrue(copyAndB.equals(andB));
        assertFalse(copyAndA.equals(andB));

        andA = (Conjunction) replaceSubformula(andA, path1, B.clone());
        assertFalse(copyAndA.equals(andA));
        assertTrue(copyAndA.equals(copyAndA));
        assertFalse(copyAndA.equals(andB));
        assertFalse(copyAndA.equals(copyAndB));

        andB = (Conjunction) replaceSubformula(andB, path2, B.clone());
        assertTrue(andA.equals(andB));
        assertTrue(copyAndA.equals(copyAndA));
        assertFalse(copyAndA.equals(andB));
        assertFalse(copyAndA.equals(copyAndB));
    }

    @Test
    public void testDisjunction() {
        TreePath path1 = new TreePath("-0");
        TreePath path2 = new TreePath("-1");

        Disjunction orA = A.or(B);
        Disjunction orB = B.or(A);
        Disjunction copyOrA = orA.clone();
        Disjunction copyOrB = orB.clone();

        assertTrue(copyOrA.equals(orA));
        assertTrue(copyOrB.equals(orB));
        assertFalse(copyOrA.equals(orB));

        orA = (Disjunction) replaceSubformula(orA, path1, B.clone());
        assertFalse(copyOrA.equals(orA));
        assertTrue(copyOrA.equals(copyOrA));
        assertFalse(copyOrA.equals(orB));
        assertFalse(copyOrA.equals(copyOrB));

        orA = (Disjunction) replaceSubformula(orB, path2, A.clone());
        assertTrue(orA.equals(orB));
        assertTrue(copyOrA.equals(copyOrA));
        assertFalse(copyOrA.equals(orB));
        assertFalse(copyOrA.equals(copyOrB));
    }

    @Test
    public void testImplication() {
        TreePath path1 = new TreePath("-0");
        TreePath path2 = new TreePath("-1");

        Implication impA = A.implies(B);
        Implication impB = B.implies(A);
        Implication copyImpA = impA.clone();
        Implication copyImpB = impB.clone();

        assertTrue(copyImpA.equals(impA));
        assertTrue(copyImpB.equals(impB));
        assertFalse(copyImpA.equals(impB));

        impA = (Implication) replaceSubformula(impA, path1, B.clone());
        assertFalse(copyImpA.equals(impA));
        assertTrue(copyImpA.equals(copyImpA));
        assertFalse(copyImpA.equals(impB));
        assertFalse(copyImpA.equals(copyImpB));

        impA = (Implication) replaceSubformula(impB, path2, A.clone());
        assertTrue(impA.equals(impB));
        assertTrue(copyImpA.equals(copyImpA));
        assertFalse(copyImpA.equals(impB));
        assertFalse(copyImpA.equals(copyImpB));
    }

    @Test
    public void testEquivalence() {
        TreePath path1 = new TreePath("-0");
        TreePath path2 = new TreePath("-1");

        Equivalence equivA = A.equivalent(B);
        Equivalence equivB = B.equivalent(A);
        Equivalence copyEquivA = equivA.clone();
        Equivalence copyEquivB = equivB.clone();

        assertTrue(copyEquivA.equals(equivA));
        assertTrue(copyEquivB.equals(equivB));
        assertFalse(copyEquivA.equals(equivB));

        equivA = (Equivalence) replaceSubformula(equivA, path1, B.clone());
        assertFalse(copyEquivA.equals(equivA));
        assertTrue(copyEquivA.equals(copyEquivA));
        assertFalse(copyEquivA.equals(equivB));
        assertFalse(copyEquivA.equals(copyEquivB));

        equivA = (Equivalence) replaceSubformula(equivB, path2, A.clone());
        assertTrue(equivA.equals(equivB));
        assertTrue(copyEquivA.equals(copyEquivA));
        assertFalse(copyEquivA.equals(equivB));
        assertFalse(copyEquivA.equals(copyEquivB));
    }

    private True top;
    private False bot;
    private Variable A;
    private Variable B;

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
}
