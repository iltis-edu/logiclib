package de.tudortmund.cs.iltis.logiclib.ctlstar.formula;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.Proposition;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class ConstructionTest {

    protected Proposition a = new Proposition("a");
    protected Proposition b = new Proposition("b");
    protected Proposition c = new Proposition("c");

    protected CtlStarFormula propFormula1 = CtlStarFormula.BOTTOM.equivalent(a.not().and(a, b));

    protected CtlStarFormula ctlFormula1 =
            CtlStarFormula.TOP.EX().and(CtlStarFormula.BOTTOM.AF()).EG();
    protected CtlStarFormula ctlFormula2 = a.EX().AF().and(b.AF()).AR(b.ER(c)).AG().AX().EU(c);

    protected CtlStarFormula ltlFormula1 = c.X().implies(b.G()).R(a);
    protected CtlStarFormula ltlFormula2 = CtlStarFormula.BOTTOM.U(c.F());

    protected CtlStarFormula ctlStarFormula1 = ctlFormula2.AW(a.not().X());
    protected CtlStarFormula ctlStarFormula2 = ltlFormula1.EF().F().W(c).EW(b).AU(a);

    @Test
    public void testGetPropositions() {
        assert ctlFormula1.getPropositions().isEmpty();

        Set<Proposition> propSet = new TreeSet<>();
        propSet.add(a);
        propSet.add(b);
        propSet.add(c);
        assert ctlFormula2.getPropositions().equals(propSet);

        Set<Proposition> propSet2 = new TreeSet<>();
        propSet2.add(c);
        assert ltlFormula2.getPropositions().equals(propSet2);
    }

    @Test
    public void testIsCtl() {
        assertTrue(propFormula1.isCtl());

        assertTrue(ctlFormula1.isCtl());
        assertTrue(ctlFormula2.isCtl());

        assertFalse(ltlFormula1.isCtl());
        assertFalse(ltlFormula2.isCtl());

        assertFalse(ctlStarFormula1.isCtl());
        assertFalse(ctlStarFormula2.isCtl());
    }

    @Test
    public void testIsLtl() {
        assertTrue(propFormula1.isLtl());

        assertFalse(ctlFormula1.isLtl());
        assertFalse(ctlFormula2.isLtl());

        assertTrue(ltlFormula1.isLtl());
        assertTrue(ltlFormula2.isLtl());

        assertFalse(ctlStarFormula1.isLtl());
        assertFalse(ctlStarFormula2.isLtl());
    }

    @Test
    public void testIsPropositional() {
        assertTrue(propFormula1.isPropositional());

        assertFalse(ctlFormula1.isPropositional());
        assertFalse(ctlFormula2.isPropositional());

        assertFalse(ltlFormula1.isPropositional());
        assertFalse(ltlFormula2.isPropositional());

        assertFalse(ctlStarFormula1.isPropositional());
        assertFalse(ctlStarFormula2.isPropositional());
    }

    @Test
    public void testTextWriter() {
        String propF1 = "⊥↔((¬a)∧a∧b)";
        assertEquals(propFormula1.toText(), propF1);

        String ltlF1 = "(Xc→Gb)Ra";
        assertEquals(ltlFormula1.toText(), ltlF1);

        String ltlF2 = "⊥UFc";
        assertEquals(ltlFormula2.toText(), ltlF2);
    }
}
