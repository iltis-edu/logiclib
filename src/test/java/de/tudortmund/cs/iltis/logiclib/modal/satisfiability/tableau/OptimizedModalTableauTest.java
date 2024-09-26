package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Comparator;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalInterpretation;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.test.AdvancedTest;
import org.junit.Test;

public class OptimizedModalTableauTest extends AdvancedTest {
    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    private Variable A;
    private Variable B;

    public OptimizedModalTableauTest() {
        this.A = new Variable("A");
        this.B = new Variable("B");
    }

    @Test
    public void longFormulaTest() {
        ModalFormula psi = null;

        try {
            psi =
                    formulaReader.read(
                            "¬(((E∧¬☐A)→((¬◇(K∧☐A)∧☐F)∨(◇(K∧☐A)∧¬☐F)))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        } catch (IncorrectParseInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertTrue(psi != null);

        OptimizedModalTableau tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isClosed());

        try {
            psi = formulaReader.read("¬((¬(E∧¬☐A)∨¬(◇(K∧☐A)↔☐F))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        } catch (IncorrectParseInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertTrue(psi != null);

        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isClosed());

        try {
            psi = formulaReader.read("¬(((E∧¬☐A)→¬((◇K∧◇☐A)↔☐F))↔((E∧¬☐A)→¬(◇(K∧☐A)↔☐F)))");
        } catch (IncorrectParseInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertTrue(psi != null);

        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());
    }

    @Test
    public void simplePropositional() {
        ModalFormula psi = (A.and(A.not())).or(B.not().and(B));

        OptimizedModalTableau tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());
    }

    @Test
    public void simpleSatisfiableModal() {
        ModalFormula psi;
        OptimizedModalTableau tableau;

        psi = A.diamond().and(A.not()).diamond();
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());
    }

    @Test
    public void simpleUnsatisfiableModal() {
        ModalFormula psi;
        OptimizedModalTableau tableau;

        psi = (A.and(A.not())).diamond();
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());

        psi = (A.and(A.not())).diamond().diamond();
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());

        psi = A.diamond().and(A.not().box());
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());

        psi = A.not().box().and(A.diamond());
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());

        psi = A.diamond().diamond().and(A.not().box().box());
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());

        psi = A.not().box().box().and(A.diamond().diamond());
        tableau = new OptimizedModalTableau(psi);
        assertFalse(tableau.isOpen());
    }

    @Test
    public void otherSatisfiableModal() {
        ModalFormula psi;
        OptimizedModalTableau tableau;

        psi = (A.box().diamond()).and(A.not().diamond().box());
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isClosed());

        psi = A.diamond().diamond().and(A.not().diamond().box(), A.not().box().diamond());
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        Variable S = new Variable("S");
        psi = (S.and(S.not().box())).diamond();
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        Variable E = new Variable("E");
        psi = (E.not().or(S.not())).implies(A.not().box());
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        Variable F = new Variable("F");
        Variable K = new Variable("K");
        psi = (F.implies(K.not())).and(F.implies(F.box()));
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        psi =
                (E.and(A.box().not()))
                        .implies((((K.and(A.box())).diamond()).equivalent(F.box())).not());
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        Variable C = new Variable("C");
        psi = (((A.and(B)).implies(C)).or(A.not())).not().box();
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());

        psi =
                (E.and(A.box().not()))
                        .implies((((K.and(A.box())).diamond()).equivalent(F.box())).not());
        tableau = new OptimizedModalTableau(psi);
        assertTrue(tableau.isOpen());
    }

    @Test
    public void equivalenceTest() {
        ModalFormula phi = A.and(A.not()).diamond();
        ModalFormula psi = A.and(A.not().box()).diamond();
        OptimizedModalTableau tableau;

        ModalFormula chi = phi.equivalent(psi);
        tableau = new OptimizedModalTableau(chi);
        assertFalse(tableau.isClosed());
    }

    @Test
    public void counterExampleTest() throws Exception {
        ModalFormula target;
        ModalFormula user;
        Comparator comp;
        ModalInterpretation i;

        target = A.and(B.diamond());
        user = A;
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());
        i = (ModalInterpretation) comp.getLastModel();
        // assertEquals(1, i.getStructure().getVertices().size());
        // System.out.println("Kripke state: " + i.getState());
        // System.out.println("Kripke structure: " + i.getStructure());

        target = A.and(B.diamond());
        user = A.and(B.box());
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());
        i = (ModalInterpretation) comp.getLastModel();
        // System.out.println("Kripke state: " + i.getState());
        // System.out.println("Kripke structure: " + i.getStructure());

        target = formulaReader.read("dia(A & !dia A)"); // A.and(A.diamond().not()).diamond();
        user = formulaReader.read("dia(A & dia A)"); // A.and(A.diamond()).diamond();
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());
        i = (ModalInterpretation) comp.getLastModel();
        // System.out.println("Kripke state: " + i.getState());
        // System.out.println("Kripke structure: " + i.getStructure());

        // Example:
        Variable S = new Variable("S");
        target = (S.and(S.not().box())).diamond();
        user = (S.and(S.not())).diamond();

        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());
        comp = Comparator.equivalent(user, target);
        assertFalse(comp.test());

        target = (S.and(S.not().box())).diamond();
        user = (S.and(S.not().diamond())).box();
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());

        Variable E = new Variable("E");
        target = (E.not().or(S.not())).implies(A.not().box());
        user = (E.not().and(S.not())).implies(A.box());
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());

        Variable F = new Variable("F");
        Variable K = new Variable("K");
        target = (F.implies(K.not())).and(F.implies(F.box()));
        user = (F.equivalent(K.not())).and(F.implies(F.diamond()));
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());

        target =
                (E.and(A.box().not()))
                        .implies((((K.and(A.box())).diamond()).equivalent(F.box())).not());
        user = (E.or(A.diamond().not())).implies((((K.and(A)).diamond()).equivalent(F)).not());
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());

        Variable C = new Variable("C");
        target = (((A.and(B)).implies(C)).or(A.not())).not().box();
        user = (((A.and(B.diamond())).implies(C.not())).or(A)).not().diamond();
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());

        target =
                (E.and(A.box().not()))
                        .implies((((K.and(A.box())).diamond()).equivalent(F.box())).not());
        user =
                (E.or(A.not()))
                        .implies(
                                (((K.and(A.diamond())).diamond()).equivalent(F.box().box())).not());
        comp = Comparator.equivalent(target, user);
        assertFalse(comp.test());
    }

    @Test
    public void closeNodeTest() {
        Variable C = new Variable("C");

        ModalFormula formula = (A.or(B.or(C))).and((A.and(A.not())).and(B.and(C)));
        ModalTableau tableau = new ModalTableau(formula);
        assertTrue(tableau.isClosed());
        assertClosedNodes(tableau.getTree());

        formula = (A.and(B.and(C))).or((A.and(A.not())).or(B.and(C)));
        tableau = new ModalTableau(formula);
        assertTrue(tableau.isOpen());
        assertClosedNodes(tableau.getTree());
    }

    private void assertClosedNodes(TableauNode root) {
        for (TableauNode child : root.getChildren()) {
            ListSet<SerializablePair<KripkeState, ModalFormula>> valuation = new ListSet<>();
            assertClosedPath(false, child, valuation);
        }
    }

    private void assertClosedPath(
            boolean closed,
            TableauNode node,
            ListSet<SerializablePair<KripkeState, ModalFormula>> valuation) {

        if (closed) {
            assert (node.isClosed());

            for (TableauNode child : node.getChildren()) {
                assertClosedPath(true, child, valuation);
            }
        } else {
            if (node instanceof FormulaNode) {
                FormulaNode fNode = (FormulaNode) node;
                ModalFormula formula = fNode.getFormula();
                KripkeState state = fNode.getState();

                if (formula.isVariable()) {

                    SerializablePair<KripkeState, ModalFormula> contradiction =
                            new SerializablePair<>(state, formula.not());

                    if (valuation.contains(contradiction)) {
                        assert (node.isClosed());

                        for (TableauNode child : node.getChildren()) {
                            assertClosedPath(true, child, valuation);
                        }
                    } else {
                        assert (node.isOpen());
                        valuation.add(new SerializablePair<>(state, formula));

                        for (TableauNode child : node.getChildren()) {
                            assertClosedPath(false, child, valuation.clone());
                        }
                    }
                }

                if (formula.isNegation()) {

                    SerializablePair<KripkeState, ModalFormula> contradiction =
                            new SerializablePair<>(state, formula.getSubformula(0));

                    if (valuation.contains(contradiction)) {
                        assert (node.isClosed());

                        for (TableauNode child : node.getChildren()) {
                            assertClosedPath(true, child, valuation);
                        }
                    } else {
                        assert (node.isOpen());
                        valuation.add(new SerializablePair<>(state, formula));

                        for (TableauNode child : node.getChildren()) {
                            assertClosedPath(false, child, valuation.clone());
                        }
                    }
                }
            }
        }
    }
}
