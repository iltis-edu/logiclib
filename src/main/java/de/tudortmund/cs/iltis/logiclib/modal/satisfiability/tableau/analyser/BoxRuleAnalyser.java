package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.EdgeNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.BoxRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.NodeConstructionMistake;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.RuleType;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class BoxRuleAnalyser {

    private FormulaNode root;
    private TreePath source;

    /**
     * At first {@code newNodes} contains all nodes that were added in this step. After a new node
     * has been identified as valid or invalid it will be removed from {@code newNodes}.
     */
    private ListSet<TreePath> newNodes;

    /**
     * Should contain all edge nodes that are compatible to the source node after {@code
     * searchForMissingNodes()} is finished.
     */
    private ListSet<KripkeState> validTargets;

    public BoxRuleAnalyser(BoxRuleAction action, ModalTableau context) {
        this.root = (FormulaNode) context.getTree();
        this.source = action.getSource();
        this.newNodes = action.getNewNodes();
        this.validTargets = new ListSet<>();
    }

    public TableauAnalyserFeedback apply() {
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        ModalFormula formula = sourceNode.getFormula();

        if (!formula.isBox()) {
            return new TableauRuleFeedback(RuleType.BOX);
        }

        // searches for nodes that are missing in the tableau
        ListSet<SerializablePair<TableauNode, TreePath>> missingNodes =
                searchForMissingNodes(root, new TreePath());

        // check all nodes that were added in this step for construction mistakes
        ListSet<SerializablePair<SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
                wrongNodes = checkNewNodes();

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
        ModalFormula subformula = formula.getSubformula(0);

        for (TreePath path : newNodes) {
            TableauNode node = this.root.retrieve(path);

            if (node instanceof EdgeNode) {
                // only formula nodes can be added via box-rule execution
                this.newNodes.remove(path);

                result.add(
                        new SerializablePair<>(
                                new SerializablePair<>(node, path),
                                NodeConstructionMistake.WRONG_NODE_TYPE));

            } else {
                FormulaNode currentNode = (FormulaNode) node;

                // check whether the node contains a valid formula and state
                if (!currentNode.getFormula().equals(subformula)) {
                    this.newNodes.remove(path);

                    result.add(
                            new SerializablePair<>(
                                    new SerializablePair<>(currentNode, path),
                                    NodeConstructionMistake.WRONG_FORMULA));

                } else if (!validTargets.contains(currentNode.getState())) {
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

    private ListSet<SerializablePair<TableauNode, TreePath>> searchForMissingNodes(
            TableauNode node, TreePath path) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);

        int i = 0;

        // search edge nodes that are compatible to the source node
        for (TableauNode child : node.getChildren()) {
            TreePath newPath = path.clone();
            newPath.child(i);

            if (child instanceof EdgeNode) {
                EdgeNode current = (EdgeNode) child;

                if (current.getParentState().equals(sourceNode.getState())) {
                    KripkeState state = current.getChildState();
                    this.validTargets.add(state);

                    if (newPath.isAncestorOf(source)) {
                        result.addAll(checkPaths(sourceNode, source, state));
                    }

                    if (source.isAncestorOf(newPath)) {
                        result.addAll(checkPaths(current, newPath, state));
                    }
                }
            }

            result.addAll(searchForMissingNodes(child, newPath));

            i++;
        }

        return result;
    }

    /** checks whether the descendants of {@code node} contain all needed nodes */
    private ListSet<SerializablePair<TableauNode, TreePath>> checkPaths(
            TableauNode node, TreePath path, KripkeState state) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();

        ModalFormula formula =
                ((FormulaNode) this.root.retrieve(source)).getFormula().getSubformula(0);

        if (this.newNodes.contains(path)) {
            // only new nodes can be needed
            if (node instanceof FormulaNode) {
                FormulaNode fNode = (FormulaNode) node;

                if (fNode.getFormula().equals(formula) && fNode.getState().equals(state)) {

                    this.newNodes.remove(path);
                    return result;
                }
            }
        }

        if (node.isLeaf()) {
            // should be added as missing
            result.add(new SerializablePair<>(new FormulaNode(state, formula), path));
        }

        int i = 0;

        for (TableauNode child : node.getChildren()) {
            TreePath current = path.clone();
            current.child(i);

            result.addAll(checkPaths(child, current, state));

            i++;
        }

        return result;
    }
}
