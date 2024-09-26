package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.ExactNamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AlternativePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FixedArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FlexibleArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.MultiConstraintPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.RepeatForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ModalFormulaPatternTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    public ModalFormulaPatternTest() {
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
        reader = new PatternReader();
    }

    @Test
    public void testConstants() {
        try {
            TreePattern<ModalFormula> bottom = reader.read("⊥");
            TreePattern<ModalFormula> top = reader.read("⊤");

            assertTrue(top.matches(ModalFormula.TOP));
            assertFalse(top.matches(ModalFormula.BOTTOM));
            assertFalse(bottom.matches(ModalFormula.TOP));
            assertTrue(bottom.matches(ModalFormula.BOTTOM));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testSimpleNegation() {
        TreePattern<ModalFormula> pattern =
                new NegationPattern(new AnyPattern(new IndexedSymbol("phi1")));

        ModalFormula phi = A.and(B).not();

        Match match = pattern.getFirstMatchIfAny(phi).get();
        assertTrue(match.defines(new IndexedSymbol("phi1")));
        assertEquals(A.and(B), match.getDefinedTree(new IndexedSymbol("phi1")).get());
    }

    @Test
    public void testSimpleConjunction() {
        TreePattern<ModalFormula> X = reader.read("X");
        TreePattern<ModalFormula> Y = reader.read("\"B\"");
        TreePattern<ModalFormula> pattern = reader.read("! (X & \"B\")");
        ModalFormula phi1 = A.and(B).not();
        ModalFormula phi2 = A.and(A).not();
        ModalFormula psi = A.or(B).not();
        ModalFormula chi = A.implies(B).not();

        assertTrue(pattern.matches(phi1));
        assertFalse(pattern.matches(phi2));
        assertFalse(pattern.matches(psi));
        assertFalse(pattern.matches(chi));

        TreePattern<ModalFormula> phi = new AnyPattern<>(new IndexedSymbol("phi"));
        TreePattern<ModalFormula> samesubs = new NegationPattern(new ConjunctionPattern(phi, phi));
        assertFalse(samesubs.matches(phi1));
        Match<ModalFormula> match = samesubs.getFirstMatchIfAny(phi2).get();
        assertEquals(A, match.getDefinedTree(new IndexedSymbol("phi")).get());
    }

    @Test
    public void testRepeatInConjunction() {
        TreePattern<ModalFormula> posLitX = new VariablePattern(anyName("X[]"));
        TreePattern<ModalFormula> posLitZ = new VariablePattern(anyName("Z[]"));
        TreePattern<ModalFormula> negLitY = new NegationPattern(anyName("Y[]"));
        TreePattern<ModalFormula> negLitW = new NegationPattern(anyName("W[]"));

        TreePattern<ModalFormula> horn =
                new ConjunctionPattern(
                        new RepeatForestPattern<>(new IndexedSymbol("phis"), posLitX),
                        negLitY,
                        new RepeatForestPattern<>(new IndexedSymbol("psis"), posLitZ));

        TreePattern<ModalFormula> unhorn =
                new ConjunctionPattern(
                        new RepeatForestPattern<>(new IndexedSymbol("phis"), negLitY),
                        posLitX,
                        new RepeatForestPattern<>(new IndexedSymbol("psis"), negLitW));

        ModalFormula phi1 = A.not().and(B, C);
        ModalFormula phi2 = A.and(B.not(), C);
        ModalFormula phi3 = A.and(B, C.not());
        ModalFormula psi1 = A.and(B.not(), C.not());
        ModalFormula psi2 = A.not().and(B, C.not());
        ModalFormula psi3 = A.not().and(B.not(), C);

        Match<ModalFormula> match = horn.getFirstMatchIfAny(phi1).get();
        assertTrue(match.getDefinedForest(new IndexedSymbol("phis")).get().isEmpty());
        assertTrue(
                listEquals(newList(B, C), match.getDefinedForest(new IndexedSymbol("psis")).get()));
        match = horn.getFirstMatchIfAny(phi2).get();
        assertTrue(listEquals(newList(A), match.getDefinedForest(new IndexedSymbol("phis")).get()));
        assertTrue(listEquals(newList(C), match.getDefinedForest(new IndexedSymbol("psis")).get()));
        match = horn.getFirstMatchIfAny(phi3).get();
        assertTrue(
                listEquals(newList(A, B), match.getDefinedForest(new IndexedSymbol("phis")).get()));
        assertTrue(match.getDefinedForest(new IndexedSymbol("psis")).get().isEmpty());

        assertFalse(unhorn.matches(phi1));
        assertFalse(unhorn.matches(phi2));
        assertFalse(unhorn.matches(phi3));

        match = unhorn.getFirstMatchIfAny(psi1).get();
        assertTrue(match.getDefinedForest(new IndexedSymbol("phis")).isPresent());
        assertTrue(
                listEquals(
                        newList(B.not(), C.not()),
                        match.getDefinedForest(new IndexedSymbol("psis")).get()));
        match = unhorn.getFirstMatchIfAny(psi2).get();
        assertTrue(
                listEquals(
                        newList(A.not()), match.getDefinedForest(new IndexedSymbol("phis")).get()));
        assertTrue(
                listEquals(
                        newList(C.not()), match.getDefinedForest(new IndexedSymbol("psis")).get()));
        match = unhorn.getFirstMatchIfAny(psi3).get();
        assertTrue(
                listEquals(
                        newList(A.not(), B.not()),
                        match.getDefinedForest(new IndexedSymbol("phis")).get()));
        assertTrue(listEquals(newList(), match.getDefinedForest(new IndexedSymbol("psis")).get()));

        assertFalse(horn.matches(psi1));
        assertFalse(horn.matches(psi2));
        assertFalse(horn.matches(psi3));
    }

    //	@Test
    //	public void unnegateDisjunction() {
    //		try {
    //			TreePattern<ModalFormula> matchPattern = reader.read("((¬X[]@$)*∨...)");
    //			TreePattern<ModalFormula> replacePattern = reader.read("((X[]@$)*∨...)");
    //
    //			ModalFormula phi = A.not().or(B.not().not(), A.not());
    //			ModalFormula psi = A.or(B.not(), A);
    //
    //			Match<ModalFormula> match = matchPattern.getFirstMatchIfAny(phi).get();
    //			ModalFormula chi = replacePattern.createTree(match);
    //			assertEquals(psi, chi);
    //		} catch (Exception e) {
    //			e.printStackTrace();
    //			assertTrue(false);
    //		}
    //	}
    //
    @Test
    public void simpleCreation() {
        // Commute binary conjunction
        ModalFormula andAB = A.and(B);
        TreePattern<ModalFormula> X = new AnyPattern<>(new IndexedSymbol("X"));
        TreePattern<ModalFormula> Y = new AnyPattern<>(new IndexedSymbol("Y"));
        TreePattern<ModalFormula> andXY = new ConjunctionPattern(fixedChildren(X, Y));
        TreePattern<ModalFormula> andYX = new ConjunctionPattern(fixedChildren(Y, X));

        Match<ModalFormula> match = andXY.getFirstMatchIfAny(andAB).get();
        ModalFormula psiXY = andXY.createTree(match);
        assertEquals(andAB, psiXY);
        ModalFormula psiYX = andYX.createTree(match);
        assertEquals(B.and(A), psiYX);
    }

    @Test
    public void exchangeConjunctionDisjunction() {
        ModalFormula and = A.and(B, A.not(), B.not().not());
        ModalFormula or = A.or(B, A.not(), B.not().not());
        TreePattern<ModalFormula> any = new AnyPattern<>();
        TreePattern<ModalFormula> con =
                new ConjunctionPattern(new RepeatForestPattern<>(new IndexedSymbol("X"), any));
        TreePattern<ModalFormula> dis =
                new DisjunctionPattern(new RepeatForestPattern<>(new IndexedSymbol("X"), any));

        Match<ModalFormula> match = con.getFirstMatchIfAny(and).get();
        assertEquals(or, dis.createTree(match));
    }

    @Test
    public void alternativeCreationTest() throws Exception {
        TreePattern<ModalFormula> repX =
                new RepeatForestPattern<>(new IndexedSymbol("X"), new AnyPattern());
        TreePattern<ModalFormula> repY =
                new RepeatForestPattern<>(new IndexedSymbol("Y"), new AnyPattern());
        TreePattern<ModalFormula> altConDisTarget =
                new AlternativePattern<>(
                        new ConjunctionPattern(repX), new DisjunctionPattern(repY));
        TreePattern<ModalFormula> altDisConTarget =
                new AlternativePattern<>(
                        new DisjunctionPattern(repX), new ConjunctionPattern(repY));
        TreePattern<ModalFormula> altConDis = reader.read("(*X&...)+(*Y|...)");
        TreePattern<ModalFormula> altDisCon = reader.read("(*X|...)+(*Y&...)");

        ModalFormula phi = formulaReader.read("A & !B & C");
        ModalFormula psi = formulaReader.read("A | !B | C");
        ModalFormula chi = formulaReader.read("A <-> !B");

        try {
            assertTrue(altConDis.matches(phi));
            assertTrue(altConDis.matches(psi));
            assertTrue(altDisCon.matches(phi));
            assertTrue(altDisCon.matches(psi));

            assertFalse(altDisCon.matches(chi));
            assertFalse(altDisCon.matches(chi));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void normalformTest() throws Exception {
        ModalFormula lit1 = formulaReader.read("A");
        ModalFormula lit2 = formulaReader.read("!B");
        ModalFormula cclause1 = formulaReader.read("A & B & !C");
        ModalFormula cclause2 = formulaReader.read("B & C & !D");
        ModalFormula dclause1 = formulaReader.read("A | B | !C");
        ModalFormula dclause2 = formulaReader.read("B | C | !D");
        ModalFormula cnf = dclause1.and(dclause2);
        ModalFormula dnf = cclause1.or(cclause2);

        TreePattern<ModalFormula> literal = reader.read("A + !A");

        assertTrue(literal.matches(lit1));
        assertTrue(literal.matches(lit2));

        TreePattern<ModalFormula> conjunctiveClause = reader.read("((A[] + !A[])* & ...)");

        TreePattern<ModalFormula> disjunctiveClause = reader.read("((A[] + !A[])* | ...)");

        assertTrue(conjunctiveClause.matches(cclause1));
        assertTrue(conjunctiveClause.matches(cclause2));
        assertTrue(disjunctiveClause.matches(dclause1));
        assertTrue(disjunctiveClause.matches(dclause2));
        assertFalse(conjunctiveClause.matches(dclause1));
        assertFalse(conjunctiveClause.matches(dclause2));
        assertFalse(disjunctiveClause.matches(cclause1));
        assertFalse(disjunctiveClause.matches(cclause2));

        TreePattern<ModalFormula> cnfPattern =
                reader.read(
                        "((((A[][] + !A[][])* | ...) + A[] + !A[])* & ...) + "
                                + "((A[] + !A[])* | ...) + A + !A");

        TreePattern<ModalFormula> dnfPattern =
                reader.read(
                        "((((A[][] + !A[][])* & ...) + A[] + !A[])* | ...) + "
                                + "((A[] + !A[])* & ...) + A + !A");

        TreePattern<ModalFormula> disjunctiveHornPattern =
                reader.read(
                        "((((!A[][])* | (B[] + !B[]) | (!C[][])*) + A[] + !A[])* & ...)"
                                + " + ((!A[])* | (B + !B) | (!C[])*) + A + !A");

        TreePattern<ModalFormula> implicativeHornPattern =
                reader.read(
                        "((((((A[][])* & ...) + A[]) -> (B[] + 0)) + A[])* & ...)"
                                + " + ((((A[])* & ...) + A) -> (B + 0)) + A");

        TreePattern<ModalFormula> implicativeHornPatternWithTopLeft =
                reader.read(
                        "((((((A[][])* & ...) + A[]) -> (B[] + 0)) + (1 -> A[]) + A[])* & ...)"
                                + " + ((((A[])* & ...) + A) -> (B + 0)) + A + (1 -> A)");

        assertTrue(cnfPattern.matches(cnf));
        assertTrue(cnfPattern.matches(lit1));
        assertTrue(cnfPattern.matches(lit2));
        assertTrue(cnfPattern.matches(cclause1));
        assertTrue(cnfPattern.matches(cclause2));
        assertTrue(cnfPattern.matches(dclause1));
        assertTrue(cnfPattern.matches(dclause2));
        assertFalse(cnfPattern.matches(dnf));

        assertTrue(dnfPattern.matches(dnf));
        assertTrue(cnfPattern.matches(lit1));
        assertTrue(cnfPattern.matches(lit2));
        assertTrue(cnfPattern.matches(cclause1));
        assertTrue(cnfPattern.matches(cclause2));
        assertTrue(cnfPattern.matches(dclause1));
        assertTrue(cnfPattern.matches(dclause2));
        assertFalse(dnfPattern.matches(cnf));

        assertFalse(disjunctiveHornPattern.matches(cnf));
        assertFalse(implicativeHornPattern.matches(cnf));
        assertFalse(disjunctiveHornPattern.matches(dnf));
        assertFalse(implicativeHornPattern.matches(dnf));

        ModalFormula disjunctiveHorn = formulaReader.read("(¬B ∨ A) ∧ B ∧ ¬A");
        assertTrue(disjunctiveHornPattern.matches(disjunctiveHorn));
        assertFalse(implicativeHornPattern.matches(disjunctiveHorn));
        assertFalse(implicativeHornPatternWithTopLeft.matches(disjunctiveHorn));

        ModalFormula implicativeHorn = formulaReader.read("(B → A) ∧ B ∧ (A → ⊥)");
        assertTrue(implicativeHornPattern.matches(implicativeHorn));
        assertFalse(disjunctiveHornPattern.matches(implicativeHorn));

        ModalFormula implicativeHornWithTopLeft =
                formulaReader.read("(B → A) ∧ B ∧ (A → ⊥) ∧ (1 → C)");
        assertFalse(implicativeHornPattern.matches(implicativeHornWithTopLeft));
        assertTrue(implicativeHornPatternWithTopLeft.matches(implicativeHornWithTopLeft));
    }

    @Test
    public void simpleParserTest() {
        TreePattern<ModalFormula> anyFormX = reader.read("$X");
        TreePattern<ModalFormula> anyFormPhi = reader.read("$'phi'");

        TreePattern<ModalFormula> anyVar = reader.read("$");
        TreePattern<ModalFormula> varA = reader.read("\"A\"");
        TreePattern<ModalFormula> varA1 = reader.read("\"A_1\"");

        Match<ModalFormula> match = anyFormX.getFirstMatchIfAny(A.and(B)).get();
        match = anyFormPhi.getFirstMatchIfAny(A.and(B)).get();

        Variable A1 = new Variable("A_1");
        assertTrue(anyVar.matches(A));
        assertTrue(anyVar.matches(A1));
        assertTrue(anyVar.matches(B));

        assertTrue(varA.matches(A));
        assertFalse(varA.matches(A1));
        assertFalse(varA.matches(B));

        assertFalse(varA1.matches(A));
        assertTrue(varA1.matches(A1));
        assertFalse(varA1.matches(B));
    }

    @Test
    public void StarPatternParserTest() {
        try {
            TreePattern<ModalFormula> anyCon =
                    reader.read("*X∧..."); // .any("phi").star("phi").and();
            TreePattern<ModalFormula> anyDis =
                    reader.read("*X∨..."); // .any("phi").star("phi").or();

            ModalFormula andABnA = A.and(B, A.not());
            ModalFormula orABnA = A.or(B, A.not());

            Match<ModalFormula> match = anyCon.getFirstMatchIfAny(andABnA).get();
            ModalFormula chi = anyDis.createTree(match);
            assertEquals(orABnA, chi);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    //	@Test
    //	public void DeMorganPatternParserTest() {
    //		try {
    //			TreePattern<ModalFormula> anyCon = reader.read("¬(*{phi}∧...)");
    //			TreePattern<ModalFormula> anyDis = reader.read("(*{phi}¬${phi}∨...)");
    //			// TreePattern<ModalFormula> anyCon =
    //			// TreePattern<ModalFormula>.any("phi").star("phi").and().not();
    //			// TreePattern<ModalFormula> anyDis =
    //			// TreePattern<ModalFormula>.any("phi").not().star("phi").or();
    //
    //			ModalFormula andABnA = A.and(B, A.not()).not();
    //			ModalFormula orABnA = A.not().or(B.not(), A.not().not());
    //
    //
    //			Match<ModalFormula> match = anyCon.getFirstMatchIfAny(andABnA).get();
    //			ModalFormula chi = anyDis.createTree(match);
    //			assertEquals(orABnA, chi);
    //
    //			System.out.println("pre De Morgan: " + andABnA);
    //			System.out.println("post De Morgan: " + chi);
    //		} catch (Exception e) {
    //			assertTrue(false);
    //		}
    //	}

    //	@Test
    //	public void DistributivityPatternParserTest() {
    //		try {
    //			TreePattern<ModalFormula> andOr = reader.read("(*{phi}∨...) ∧ (*{psi}∨...)");
    //			TreePattern<ModalFormula> orAnd = reader.read("(*{phi}*{psi}(${phi}∧${psi})) ∨ ...");
    //
    //			ModalFormula chi1 = A.or(A.not()).and(B.or(B.not(), B));
    //
    //
    //			Match<ModalFormula> match = andOr.getFirstMatchIfAny(chi1).get(); // TODO: currently false!
    //			ModalFormula chi2 = orAnd.createTree(match);
    //			System.out.println("pre Dist: " + chi1);
    //			System.out.println("post Dist: " + chi2);
    //		} catch (Exception e) {
    //			assertTrue(false);
    //		}
    //	}
    //
    @Test
    public void test() throws Exception {

        SerializablePredicate<List<ModalFormula>> clauseCriterion =
                forest -> {
                    boolean containsPositiveLiteral = false;

                    for (ModalFormula subformula : forest.get(0).getSubformulae()) {
                        if (subformula instanceof Variable) {
                            if (containsPositiveLiteral) {

                                return false;
                            }

                            containsPositiveLiteral = true;
                        }
                    }

                    return true;
                };

        SerializablePredicate<List<ModalFormula>> hornCriterion =
                forest -> {
                    for (ModalFormula clause : forest.get(0).getSubformulae()) {
                        if (clause instanceof Disjunction) {
                            boolean containsPositiveLiteral = false;

                            for (ModalFormula subformula : clause.getChildren()) {
                                if (subformula instanceof Variable) {
                                    if (containsPositiveLiteral) {
                                        return false;
                                    }

                                    containsPositiveLiteral = true;
                                }
                            }
                        }
                    }

                    return true;
                };

        TreePattern<ModalFormula> literal = reader.read("A + !A");

        TreePattern<ModalFormula> singleClause =
                new MultiConstraintPattern<>(
                        reader.read("(A[] + !A[])* | ..."),
                        new PredicatePattern<>(clauseCriterion));

        TreePattern<ModalFormula> multipleClauses =
                new MultiConstraintPattern<>(
                        reader.read("(((A[][] + !A[][])* | ...) + A[] + !A[])* & ..."),
                        new PredicatePattern<>(hornCriterion));

        TreePattern<ModalFormula> hornPattern =
                new AlternativePattern<>(literal, singleClause, multipleClauses);

        ModalFormula hornFormula = A.not().or(B.not(), C);
        assertTrue(hornPattern.matches(hornFormula));
        assertTrue(hornPattern.matches(hornFormula.and(B)));
    }

    private ChildrenPattern<ModalFormula> flexibleChildren(TreePattern<ModalFormula>... patterns) {
        return new ChildrenPattern<>(new FlexibleArityForestPattern<>(patterns));
    }

    private ChildrenPattern<ModalFormula> fixedChildren(TreePattern<ModalFormula>... patterns) {
        return new ChildrenPattern<>(new FixedArityForestPattern<>(patterns));
    }

    private AnyNamePattern<ModalFormula, IndexedSymbol> anyName(String name) {
        return new AnyNamePattern<>(new IndexedSymbol(name));
    }

    private ExactNamePattern<ModalFormula, IndexedSymbol> exactName(String name) {
        return new ExactNamePattern<>(new IndexedSymbol(name));
    }

    private List<ModalFormula> newList(ModalFormula... formulae) {
        List<ModalFormula> list = new ArrayList<ModalFormula>();
        for (ModalFormula formula : formulae) list.add(formula);
        return list;
    }

    private boolean listEquals(final List<ModalFormula> phis, final List<ModalFormula> psis) {
        if (phis.size() != psis.size()) return false;
        for (int i = 0; i < phis.size(); i++) if (!phis.get(i).equals(psis.get(i))) return false;
        return true;
    }

    private Variable A;
    private Variable B;
    private Variable C;
    private PatternReader reader;
}
