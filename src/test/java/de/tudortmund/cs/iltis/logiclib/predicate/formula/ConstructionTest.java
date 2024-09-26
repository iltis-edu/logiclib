package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ConstructionTest {
    protected Formula shortFormula;
    protected Formula mediumFormula;
    protected Formula longFormula;
    protected Formula hugeFormula;

    protected Formula notValid1;
    protected Formula notValid2;
    protected Formula notValid3;

    public ConstructionTest() {
        createFormulaeAndTestEquals();
    }

    /**
     * Creates some test formulae. This does <b>not</b> cover every constructor of every subformula
     * but should catch most bugs ...
     */
    @Test
    public void createFormulaeAndTestEquals() {
        String symX1 = "x_1";
        IndexedSymbol symX2 = new IndexedSymbol("x_2");
        VariableSymbol symY1 = new VariableSymbol("y", "1", "");
        String symY1_ = "y_1";

        String symEqual = "=";
        IndexedSymbol symPlus = new IndexedSymbol("+");

        IndexedSymbol symT = new IndexedSymbol("t");
        String symU = "u";
        String symU5 = "u^5";
        String symV = "v";

        String symR = "R";
        IndexedSymbol symR_ = new IndexedSymbol("R");
        IndexedSymbol symS = new IndexedSymbol("S");

        RelationSymbol symREqual = new RelationSymbol(symEqual, 2, true);
        RelationSymbol symRR = new RelationSymbol(symR, 2, false);
        RelationSymbol symRS = new RelationSymbol(symS, 2, false);
        FunctionSymbol symFPlus = new FunctionSymbol(symPlus, 2, false);
        FunctionSymbol symFR = new FunctionSymbol(symR, 2, false);

        Variable varX1 = new Variable(symX1);
        Variable varX2 = new Variable(symX2);
        Variable varY1 = new Variable(symY1);
        Variable varY1_ = new Variable(symY1_);

        FunctionTerm constT = new FunctionTerm(symT);
        FunctionTerm constU = new FunctionTerm(symU);

        FunctionTerm fun2Plus = new FunctionTerm(symPlus, true, varX1, varX2);
        FunctionTerm fun2T = new FunctionTerm(symT, true, varX1, varX2);
        FunctionTerm fun2U5 = new FunctionTerm(symU5, varX1, fun2Plus);
        FunctionTerm fun2V = new FunctionTerm(symV, varY1);
        FunctionTerm fun2V_ = new FunctionTerm(symV, varY1_);

        RelationAtom rel1Equal = new RelationAtom(symREqual, constT, constU);
        RelationAtom rel3R_ = new RelationAtom(symR_, false, varX1, varY1, fun2V);
        RelationAtom rel3R = new RelationAtom(symR, varX1, varY1, fun2V);
        List<Term> list = new ArrayList<>();
        list.add(varX1);
        list.add(varY1);
        list.add(fun2V);
        RelationAtom rel3R__ = new RelationAtom(symR, list);

        RelationAtom relS = new RelationAtom(symRS, varY1, fun2U5);
        RelationAtom relS_ = new RelationAtom(symS, true, varY1, fun2U5);

        True tru = new True();
        False fal = new False();

        Implication impl = new Implication(rel3R_, relS);
        shortFormula = fal.implies(relS.not());
        Equivalence equi = new Equivalence(impl, rel3R__);
        Equivalence equi_ = impl.equivalentTo(rel3R_);

        Quantifier quanA = equi.forAll(varY1);
        Quantifier quanAE = equi_.forAll(varY1_);
        Quantifier quanA_ = equi.exists(varY1);
        Quantifier quanA__ = equi.forAll(varX1);
        Quantifier quanE = relS.exists(varX1);

        Conjunction conj = new Conjunction(impl);
        mediumFormula = rel3R.and(quanE).forAll(varX1);
        hugeFormula = rel3R.and(quanA, relS);

        List<Formula> list2 = new ArrayList<>();
        list2.add(conj);
        list2.add(rel1Equal);
        list2.add(tru);
        longFormula = new Disjunction(list2);

        // not valid signature
        notValid1 = new RelationAtom(symR, constT, fun2T);
        notValid2 = rel3R_.forAll(varY1).implies(notValid1);
        FunctionTerm funNV = new FunctionTerm(symFR, constT, constU);
        notValid3 = new RelationAtom(symRR, funNV, varX1);

        // errors
        try {
            new FunctionTerm(symFPlus, varX1);
            Assert.fail("function symbol's arity is not respected");
        } catch (IllegalArgumentException e) {
        }
        try {
            new RelationAtom(symREqual, varX1, varY1, varX2);
            Assert.fail("relation symbol's arity is not respected");
        } catch (IllegalArgumentException e) {
        }
        try {
            new Conjunction();
            Assert.fail("conjunction with zero subterms possible");
        } catch (IllegalArgumentException e) {
        }

        // name of subformulae
        Assert.assertEquals(fun2V, fun2V_);
        Assert.assertEquals(rel3R_, rel3R);
        Assert.assertEquals(rel3R_, rel3R__);
        Assert.assertNotEquals(constT, constU);
        Assert.assertNotEquals(relS, relS_);

        // relation symbols
        Assert.assertNotEquals(symRR, symRS);
        // equals is covered by cases above

        // quantifiers
        Assert.assertEquals(quanA, quanAE);
        Assert.assertNotEquals(quanA, quanA_);
        Assert.assertNotEquals(quanA, quanA__);
        Assert.assertNotEquals(quanA_, quanA__);
    }

    @Test
    public void testSignature() {
        Assert.assertNull(notValid1.getSignature());
        Assert.assertNull(notValid2.getSignature());
        Assert.assertNull(notValid3.getSignature());

        Set<RelationSymbol> shortFormulaRel = new HashSet<>();
        shortFormulaRel.add(new RelationSymbol("S", 2, false));
        Set<FunctionSymbol> shortFormulaFun = new HashSet<>();
        shortFormulaFun.add(new FunctionSymbol("+", 2, true));
        shortFormulaFun.add(new FunctionSymbol("u^5", 2, false));
        Signature shortFormulaSig = new Signature(shortFormulaRel, shortFormulaFun);
        Assert.assertEquals(shortFormulaSig, shortFormula.getSignature());

        Set<RelationSymbol> hugeFormulaRel = new HashSet<>();
        hugeFormulaRel.add(new RelationSymbol("S", 2, false));
        hugeFormulaRel.add(new RelationSymbol("R", 3, false));
        Set<FunctionSymbol> hugeFormulaFun = new HashSet<>();
        hugeFormulaFun.add(new FunctionSymbol("+", 2, true));
        hugeFormulaFun.add(new FunctionSymbol("v", 1, false));
        hugeFormulaFun.add(new FunctionSymbol("u^5", 2, false));
        Signature hugeFormulaSig = new Signature(hugeFormulaRel, hugeFormulaFun);
        Assert.assertEquals(hugeFormulaSig, hugeFormula.getSignature());

        Assert.assertEquals(hugeFormulaSig, mediumFormula.getSignature());

        Set<RelationSymbol> longFormulaRel = new HashSet<>();
        longFormulaRel.add(new RelationSymbol("S", 2, false));
        longFormulaRel.add(new RelationSymbol("R", 3, false));
        longFormulaRel.add(new RelationSymbol("=", 2, true));
        Set<FunctionSymbol> longFormulaFun = new HashSet<>();
        longFormulaFun.add(new FunctionSymbol("+", 2, true));
        longFormulaFun.add(new FunctionSymbol("v", 1, false));
        longFormulaFun.add(new FunctionSymbol("u^5", 2, false));
        longFormulaFun.add(new FunctionSymbol("u", 0, false));
        longFormulaFun.add(new FunctionSymbol("t", 0, false));
        Signature longFormulaSig = new Signature(longFormulaRel, longFormulaFun);
        Assert.assertEquals(longFormulaSig, longFormula.getSignature());
    }

    @Test
    public void testVariableCollectors() {
        Set<Variable> allVars = new HashSet<>();
        allVars.add(new Variable("y_1"));
        allVars.add(new Variable("x_1"));
        allVars.add(new Variable("x_2"));

        // shortFormula
        Set<Variable> boundVars = new HashSet<>();
        Set<Variable> freeVars = new HashSet<>(allVars);

        Assert.assertEquals(allVars, shortFormula.getVariables());
        Assert.assertEquals(freeVars, shortFormula.getFreeVariables());
        Assert.assertEquals(boundVars, shortFormula.getBoundVariables());

        // longFormula
        Assert.assertEquals(allVars, longFormula.getVariables());
        Assert.assertEquals(freeVars, longFormula.getFreeVariables());
        Assert.assertEquals(boundVars, longFormula.getBoundVariables());

        // mediumFormula
        boundVars = new HashSet<>();
        boundVars.add(new Variable("x_1"));
        freeVars = new HashSet<>();
        freeVars.add(new Variable("y_1"));
        freeVars.add(new Variable("x_2"));

        Assert.assertEquals(allVars, mediumFormula.getVariables());
        Assert.assertEquals(freeVars, mediumFormula.getFreeVariables());
        Assert.assertEquals(boundVars, mediumFormula.getBoundVariables());

        // hugeFormula
        boundVars = new HashSet<>();
        boundVars.add(new Variable("y_1"));
        freeVars = new HashSet<>(allVars);

        Assert.assertEquals(allVars, hugeFormula.getVariables());
        Assert.assertEquals(freeVars, hugeFormula.getFreeVariables());
        Assert.assertEquals(boundVars, hugeFormula.getBoundVariables());
    }

    @Test
    public void testLatexWriter() {
        String manualTestInLatexDocument =
                "Test string with multiple formulae:\\\\$"
                        + shortFormula.toLatexString()
                        + "$ and \\\\$"
                        + mediumFormula.toLatexString()
                        + "$ and \\\\$"
                        + longFormula.toLatexString()
                        + "$ and \\\\$"
                        + hugeFormula.toLatexString()
                        + "$";
        System.out.println(manualTestInLatexDocument);
    }

    @Test
    public void testTextWriter() {
        //		String shortFormulaOutput = "⊥→¬S(y_1,u^5(x_1,(x_1+x_2)))";
        //		String mediumFormulaOutput = "∀x_1 (R(x_1,y_1,v(y_1))∧∃x_1 S(y_1,u^5(x_1,(x_1+x_2))))";
        //		String longFormulaOutput = "(R(x_1,y_1,v(y_1))→S(y_1,u^5(x_1,(x_1+x_2))))∨(t=u)∨⊤";
        //		String hugeFormulaOutput = "R(x_1,y_1,v(y_1))∧∀y_1
        // ((R(x_1,y_1,v(y_1))→S(y_1,u^5(x_1,(x_1+x_2))))↔R(x_1,y_1,v(y_1)))∧S(y_1,u^5(x_1,(x_1+x_2)))";

        String shortFormulaOutput = "⊥→¬S(y₁,u⁵(x₁,(x₁+x₂)))";
        String mediumFormulaOutput = "∀x₁ (R(x₁,y₁,v(y₁))∧∃x₁ S(y₁,u⁵(x₁,(x₁+x₂))))";
        String longFormulaOutput = "(R(x₁,y₁,v(y₁))→S(y₁,u⁵(x₁,(x₁+x₂))))∨(t=u)∨⊤";
        String hugeFormulaOutput =
                "R(x₁,y₁,v(y₁))∧∀y₁ ((R(x₁,y₁,v(y₁))→S(y₁,u⁵(x₁,(x₁+x₂))))↔R(x₁,y₁,v(y₁)))∧S(y₁,u⁵(x₁,(x₁+x₂)))";

        Assert.assertEquals(shortFormulaOutput, shortFormula.toTextString());
        Assert.assertEquals(mediumFormulaOutput, mediumFormula.toTextString());
        Assert.assertEquals(longFormulaOutput, longFormula.toTextString());
        Assert.assertEquals(hugeFormulaOutput, hugeFormula.toTextString());
    }

    @Test
    public void testClone() {
        Assert.assertEquals(shortFormula, shortFormula.clone());
        Assert.assertEquals(mediumFormula, mediumFormula.clone());
        Assert.assertEquals(longFormula, longFormula.clone());
        Assert.assertEquals(hugeFormula, hugeFormula.clone());
    }

    @Test
    public void testQuantifierCount() {
        Formula formula = new True();
        Assert.assertEquals(0, formula.getQuantifierCount());
        formula = new Conjunction(new False(), new True());
        Assert.assertEquals(0, formula.getQuantifierCount());
        formula = new ExistentialQuantifier(new Variable("x"), formula);
        Assert.assertEquals(1, formula.getQuantifierCount());
        formula = new ExistentialQuantifier(new Variable("x"), formula);
        Assert.assertEquals(2, formula.getQuantifierCount());
        formula = new UniversalQuantifier(new Variable("x"), formula);
        Assert.assertEquals(3, formula.getQuantifierCount());
        formula = new Disjunction(formula, formula.not());
        Assert.assertEquals(6, formula.getQuantifierCount());
    }
}
