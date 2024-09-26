package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.AndRuleAnalyser;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.AndRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.List;
import org.junit.Test;

public class TableauAnalyserTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void testTableauAnalyser() {
        try {
            ModalTableau tableau =
                    new ModalTableau(formulaReader.read("(A ∧ !B) ∧  (!A ∧  B)"), false);

            TableauNode root = tableau.getTree();

            FormulaNode child =
                    new FormulaNode(new KripkeState("s_1"), formulaReader.read("(A ∧ !B)"));
            FormulaNode childofChild =
                    new FormulaNode(new KripkeState("s_1"), formulaReader.read("(!A ∧  B)"));

            child.addChild(childofChild);
            root.addChild(child);

            // path to root
            de.tudortmund.cs.iltis.utils.tree.TreePath path0 =
                    new de.tudortmund.cs.iltis.utils.tree.TreePath();
            // path to child
            de.tudortmund.cs.iltis.utils.tree.TreePath path1 =
                    new de.tudortmund.cs.iltis.utils.tree.TreePath();
            path1.child(0);
            // path to childofChild
            de.tudortmund.cs.iltis.utils.tree.TreePath path2 =
                    new de.tudortmund.cs.iltis.utils.tree.TreePath();
            path2.child(0);
            path2.child(0);

            System.out.println("path0:" + path0 + root.retrieve(path0));
            System.out.println("path1:" + path1 + root.retrieve(path1));
            System.out.println("path2:" + path2 + root.retrieve(path2));

            ListSet<de.tudortmund.cs.iltis.utils.tree.TreePath> newPaths = new ListSet<>();
            newPaths.add(path1);
            newPaths.add(path2);

            System.out.println("newPaths: " + newPaths);
            showTableau(root);

            AndRuleAction action = new AndRuleAction(path0, newPaths);
            AndRuleAnalyser analyser = new AndRuleAnalyser(action, tableau);

            TableauRuleFeedback feedback = (TableauRuleFeedback) analyser.apply();

            System.out.println("getConstructionMistakes" + feedback.getConstructionMistakes());
            System.out.println("getMisplacedNodes" + feedback.getMisplacedNodes());
            System.out.println("getMissingNodes" + feedback.getMissingNodes());
            System.out.println("getNeededRuleType" + feedback.getNeededRuleType());

        } catch (IncorrectParseInputException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showTableau(TableauNode root) {

        System.out.println("[SHOW] children of " + root + " :");
        List<TableauNode> children = root.getChildren();
        for (TableauNode t : children) {
            System.out.println("[SHOW] child: " + t);
        }
        for (TableauNode t : children) {
            showTableau(t);
        }
        System.out.println("[SHOW] end.");
    }
}
