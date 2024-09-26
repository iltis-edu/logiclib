package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.TableauPolicy.ProcessingOrder;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.AndRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.ClosePathAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.OrRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauPolicyFeedback;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class PolicyAnalyser {

    private TableauPolicy policy;
    private ListSet<TreePath> finishedNodes;

    public PolicyAnalyser(TableauPolicy policy) {
        this.policy = policy;
        this.finishedNodes = new ListSet<>();
    }

    public void finishNode(TreePath path) {
        this.finishedNodes.add(path);
    }

    public TableauAnalyserFeedback apply(TableauAction action, ModalTableau context) {
        ListSet<TreePath> unfinishedNodes = new ListSet<>();
        ListSet<SerializablePair<TreePath, TreePath>> nodesInWrongOrder = new ListSet<>();
        TableauNode root = context.getTree();
        TreePath source = action.getSource();

        if (action instanceof ClosePathAction) {
            if (this.policy.finishBranchBeforeClosing()) {
                TreePath current = new TreePath();
                TableauNode node = root;

                for (Integer index : source) {
                    if (!finishedNodes.contains(node)) {
                        unfinishedNodes.add(current);
                    }

                    node = node.getChild(index);
                    current = current.clone().child(index);
                }
            }
        } else {
            if (!this.policy.getProcessingOrder().equals(ProcessingOrder.ARBITRARY)) {

                unfinishedNodes.addAll(searchNodesWithHigherPriority(root, source));
            }

            if (this.policy.addNewNodesInOrder()) {
                if (action instanceof AndRuleAction || action instanceof OrRuleAction) {

                    nodesInWrongOrder.addAll(
                            searchNodesInWrongOrder(root, source, action.getNewNodes()));
                }
            }
        }

        if (unfinishedNodes.isEmpty() && nodesInWrongOrder.isEmpty()) {
            return new TableauPolicyFeedback();
        }

        return new TableauPolicyFeedback(unfinishedNodes, nodesInWrongOrder);
    }

    private ListSet<TreePath> searchNodesWithHigherPriority(TableauNode root, TreePath source) {

        ListSet<TreePath> result = new ListSet<>();
        List<TreePath> queue = new ArrayList<>();
        queue.add(new TreePath());

        while (!queue.isEmpty()) {
            TreePath current = queue.get(0);

            if (current.isSiblingOf(source) && current.peek() >= source.peek()) {

                break;
            }

            queue.remove(0);
            TableauNode node = root.retrieve(current);

            if (!finishedNodes.contains(current)) {
                if (this.policy.getProcessingOrder().equals(ProcessingOrder.BOXES_AFTER_DIAMOND)) {

                    if (node instanceof FormulaNode) {
                        ModalFormula formula = ((FormulaNode) node).getFormula();

                        if (formula.isBox()) {
                            result.add(current);
                        }
                    }
                } else {
                    result.add(current);
                }
            }

            if (!current.isSiblingOf(source)) {
                int i = 0;

                for (TableauNode child : node.getChildren()) {
                    queue.add(current.clone().child(i));
                    i++;
                }
            }
        }

        return result;
    }

    private ListSet<SerializablePair<TreePath, TreePath>> searchNodesInWrongOrder(
            TableauNode root, TreePath source, ListSet<TreePath> newNodes) {

        ListSet<SerializablePair<TreePath, TreePath>> result = new ListSet<>();
        ListSet<TreePath> processedNodes = new ListSet<>();

        ModalFormula formula = ((FormulaNode) root.retrieve(source)).getFormula();

        for (TreePath path : newNodes) {
            if (!processedNodes.contains(path)) {
                FormulaNode firstNode = (FormulaNode) root.retrieve(path);
                FormulaNode secondNode = (FormulaNode) root.retrieve(path);
                TreePath nextPath = path.clone();

                if (formula.isConjunction()) {
                    if (firstNode.isLeaf()) {
                        nextPath.up();
                        firstNode = (FormulaNode) root.retrieve(nextPath);
                    } else {
                        nextPath.down();
                        secondNode = (FormulaNode) root.retrieve(nextPath);
                    }
                } else {
                    // formula must be disjunction
                    nextPath.up();

                    if (path.peek() == 0) {
                        nextPath.child(1);
                        secondNode = (FormulaNode) root.retrieve(nextPath);
                    } else {
                        nextPath.child(0);
                        firstNode = (FormulaNode) root.retrieve(nextPath);
                    }
                }

                processedNodes.add(path);
                processedNodes.add(nextPath);

                if (!firstNode.getFormula().equals(formula.getSubformula(0))) {
                    result.add(new SerializablePair<>(path, nextPath));
                }
            }
        }

        return result;
    }
}
