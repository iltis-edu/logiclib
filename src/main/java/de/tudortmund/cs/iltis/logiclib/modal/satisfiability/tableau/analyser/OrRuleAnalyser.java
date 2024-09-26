package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.EdgeNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.OrRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.NodeConstructionMistake;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.RuleType;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class OrRuleAnalyser {

    private FormulaNode root;
    private TreePath source;

    /**
     * At first {@code newNodes} contains all nodes that were added in this step. After a new node
     * has been identified as valid or invalid it will be removed from {@code newNodes}.
     */
    private ListSet<TreePath> newNodes;

    public OrRuleAnalyser(OrRuleAction action, ModalTableau context) {
        this.root = (FormulaNode) context.getTree();
        this.source = action.getSource();
        this.newNodes = action.getNewNodes();
    }

    public TableauAnalyserFeedback apply() {
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        ModalFormula formula = sourceNode.getFormula();

        if (!formula.isDisjunction()) {
            return new TableauRuleFeedback(RuleType.DISJUNCTION);
        }

        // check all nodes that were added in this step for construction mistakes
        ListSet<SerializablePair<SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
                wrongNodes = checkNewNodes();

        // searches for nodes that are missing in the tableau
        ListSet<SerializablePair<TableauNode, TreePath>> missingNodes = searchForMissingNodes();

        // all remaining new nodes must be misplaced
        ListSet<SerializablePair<TableauNode, TreePath>> misplacedNodes = new ListSet<>();

        for (TreePath path : newNodes) {
            misplacedNodes.add(new SerializablePair<>(this.root.retrieve(path), path));
        }

        return new TableauRuleFeedback(wrongNodes, missingNodes, misplacedNodes);
    }

    private ListSet<
                    SerializablePair<
                            SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
            checkNewNodes() {

        ListSet<SerializablePair<SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
                result = new ListSet<>();

        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        ModalFormula formula = sourceNode.getFormula();
        KripkeState state = sourceNode.getState();
        ModalFormula firstSubformula = formula.getSubformula(0);
        ModalFormula secondSubformula = formula.getSubformula(1);

        for (TreePath path : newNodes) {
            TableauNode node = this.root.retrieve(path);

            if (node instanceof EdgeNode) {
                // only formula nodes can be added via or-rule execution
                this.newNodes.remove(path);

                result.add(
                        new SerializablePair<>(
                                new SerializablePair<>(node, path),
                                NodeConstructionMistake.WRONG_NODE_TYPE));

            } else {
                FormulaNode currentNode = (FormulaNode) node;

                // check whether the node contains a valid formula and state
                if (!currentNode.getFormula().equals(firstSubformula)
                        && !currentNode.getFormula().equals(secondSubformula)) {

                    this.newNodes.remove(path);

                    result.add(
                            new SerializablePair<>(
                                    new SerializablePair<>(currentNode, path),
                                    NodeConstructionMistake.WRONG_FORMULA));

                } else if (!currentNode.getState().equals(state)) {
                    this.newNodes.remove(path);

                    result.add(
                            new SerializablePair<>(
                                    new SerializablePair<>(currentNode, path),
                                    NodeConstructionMistake.WRONG_STATE));
                }
            }
        }

        return result;
    }

    private ListSet<SerializablePair<TableauNode, TreePath>> searchForMissingNodes() {
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);

        return checkPath(source, sourceNode);
    }

    /** checks whether the descendants of {@code node} contain all needed nodes */
    private ListSet<SerializablePair<TableauNode, TreePath>> checkPath(
            TreePath path, TableauNode node) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);

        if (node.isLeaf()) {
            // needed nodes were not found
            ModalFormula sourceFormula = sourceNode.getFormula();
            ModalFormula firstSubformula = sourceFormula.getSubformula(0);
            ModalFormula secondSubformula = sourceFormula.getSubformula(1);

            FormulaNode firstChild = new FormulaNode(sourceNode.getState(), firstSubformula);

            FormulaNode secondChild = new FormulaNode(sourceNode.getState(), secondSubformula);

            result.add(new SerializablePair<>(firstChild, path));
            result.add(new SerializablePair<>(secondChild, path));
        }

        // if node has only new children, it should be handled differently to
        // improve feedback
        if (hasOnlyNewChildren(node, path)) {
            result.addAll(chooseCorrectPath(node, path));
        } else {

            int i = 0;

            for (TableauNode child : node.getChildren()) {
                TreePath current = path.clone();
                current.child(i);

                if (!this.newNodes.contains(current)) {
                    result.addAll(checkPath(current, child));
                }

                i++;
            }
        }

        return result;
    }

    private boolean hasOnlyNewChildren(TableauNode node, TreePath path) {
        int i = 0;

        for (TableauNode child : node.getChildren()) {
            TreePath current = path.clone().child(i);

            if (!this.newNodes.contains(current)) {
                return false;
            }
        }

        return true;
    }

    private ListSet<SerializablePair<TableauNode, TreePath>> chooseCorrectPath(
            TableauNode node, TreePath path) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();
        boolean hasFirstSubformula = false;
        boolean hasSecondSubformula = false;
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        KripkeState state = sourceNode.getState();
        ModalFormula formula = sourceNode.getFormula();
        ModalFormula firstSubformula = formula.getSubformula(0);
        ModalFormula secondSubformula = formula.getSubformula(1);

        int i = 0;

        for (TableauNode child : node.getChildren()) {
            TreePath current = path.clone().child(i);

            if (child instanceof FormulaNode) {
                FormulaNode fNode = (FormulaNode) child;

                if (!hasFirstSubformula && fNode.getFormula().equals(firstSubformula)) {

                    this.newNodes.remove(current);
                    hasFirstSubformula = true;
                }

                if (!hasSecondSubformula && fNode.getFormula().equals(secondSubformula)) {

                    this.newNodes.remove(current);
                    hasSecondSubformula = true;
                }
            }

            i++;
        }

        if (!hasFirstSubformula) {

            result.add(new SerializablePair<>(new FormulaNode(state, firstSubformula), path));
        }

        if (!hasSecondSubformula) {

            result.add(new SerializablePair<>(new FormulaNode(state, secondSubformula), path));
        }

        return result;
    }
}
