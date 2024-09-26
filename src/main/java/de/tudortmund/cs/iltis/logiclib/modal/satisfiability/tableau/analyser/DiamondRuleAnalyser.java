package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.EdgeNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.AmbigousStateNamesFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.DiamondRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.NodeConstructionMistake;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.RuleType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class DiamondRuleAnalyser {

    private FormulaNode root;
    private TreePath source;

    /**
     * At first {@code newNodes} contains all nodes that were added in this step. After a new node
     * has been identified as valid or invalid it will be removed from {@code newNodes}.
     */
    private ListSet<TreePath> newNodes;

    private KripkeState newState;
    private boolean hasAmbigousStateNames;

    public DiamondRuleAnalyser(DiamondRuleAction action, ModalTableau context) {
        this.root = (FormulaNode) context.getTree();
        this.source = action.getSource();
        this.newNodes = action.getNewNodes();
        this.newState = null;
        this.hasAmbigousStateNames = false;
    }

    public TableauAnalyserFeedback apply() {
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        ModalFormula formula = sourceNode.getFormula();

        if (!formula.isDiamond()) {
            return new TableauRuleFeedback(RuleType.DIAMOND);
        }

        // check all nodes that were added in this step for construction mistakes
        ListSet<SerializablePair<SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
                wrongNodes = checkNewNodes();

        if (this.hasAmbigousStateNames) {
            return new AmbigousStateNamesFeedback(true);
        }

        if (isNameUsed(this.newState)) {
            return new AmbigousStateNamesFeedback(false);
        }

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
        KripkeState state = sourceNode.getState();
        ModalFormula subformula = sourceNode.getFormula().getSubformula(0);

        for (TreePath path : newNodes) {
            TableauNode node = this.root.retrieve(path);

            if (node instanceof FormulaNode) {
                FormulaNode currentNode = (FormulaNode) node;

                // check whether the node contains a valid formula and state
                checkState(currentNode.getState());

                if (!subformula.equals(currentNode.getFormula())) {
                    this.newNodes.remove(path);

                    result.add(
                            new SerializablePair<>(
                                    new SerializablePair<>(currentNode, path),
                                    NodeConstructionMistake.WRONG_FORMULA));
                }
            } else {
                EdgeNode currentNode = (EdgeNode) node;

                // check whether the node contains valid states
                checkState(currentNode.getChildState());

                if (!currentNode.getParentState().equals(state)) {
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

    private void checkState(KripkeState state) {
        if (this.newState == null) {
            this.newState = state;
        } else if (!state.equals(this.newState)) {
            this.hasAmbigousStateNames = true;
        }
    }

    private boolean isNameUsed(KripkeState childState) {
        IndexedSymbol name = childState.getName();

        if (root.getState().getName().equals(name)) {
            return true;
        }

        TreePath path = new TreePath();
        int i = 0;

        for (TableauNode child : root.getChildren()) {
            TreePath current = path.clone().child(i);

            if (pathContainsName(current, child, name)) {
                return true;
            }

            i++;
        }

        return false;
    }

    private boolean pathContainsName(TreePath path, TableauNode node, IndexedSymbol name) {
        // new nodes should not be checked whether they use name
        if (this.newNodes.contains(path)) {
            return false;
        }

        if (node instanceof FormulaNode) {
            if (((FormulaNode) node).getState().getName().equals(name)) {
                return true;
            }
        }

        if (node instanceof EdgeNode) {
            if (((EdgeNode) node).getChildState().getName().equals(name)) {
                return true;
            }
        }

        int i = 0;

        for (TableauNode child : root.getChildren()) {
            TreePath current = path.clone().child(i);

            if (pathContainsName(current, child, name)) {
                return true;
            }

            i++;
        }

        return false;
    }

    private ListSet<SerializablePair<TableauNode, TreePath>> searchForMissingNodes() {
        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        KripkeState state = sourceNode.getState();
        ModalFormula subformula = sourceNode.getFormula().getSubformula(0);
        EdgeNode firstNode = new EdgeNode(state, this.newState);
        FormulaNode secondNode = new FormulaNode(this.newState, subformula);

        ListSet<TableauNode> neededNodes = new ListSet<>(firstNode, secondNode);

        result.addAll(checkPath(source, sourceNode, neededNodes));

        return result;
    }

    /** checks whether the descendants of {@code node} contain all needed nodes */
    private ListSet<SerializablePair<TableauNode, TreePath>> checkPath(
            TreePath path, TableauNode node, ListSet<TableauNode> neededNodes) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();

        if (newNodes.contains(path)) {
            // only new nodes can be needed
            if (containsNode(neededNodes, node)) {
                this.newNodes.remove(path);
            }

            if (neededNodes.isEmpty()) {
                return result;
            }
        }

        if (node.isLeaf()) {
            // all needed nodes can now be returned as missing
            for (TableauNode currentNode : neededNodes) {
                result.add(new SerializablePair<>(currentNode, path));
            }

            return result;
        }

        // if node has only new children, it should be handled differently to
        // improve feedback
        if (hasOnlyNewChildren(node, path)) {

            SerializablePair<TreePath, ListSet<SerializablePair<TableauNode, TreePath>>>
                    correctPathPair = chooseCorrectPath(node, path, neededNodes.clone());

            this.newNodes.remove(correctPathPair.first());
            result.addAll(correctPathPair.second());
        } else {

            int i = 0;

            for (TableauNode child : node.getChildren()) {
                TreePath current = path.clone();
                current.child(i);

                if (!this.newNodes.contains(current)) {
                    result.addAll(checkPath(current, child, neededNodes.clone()));
                }

                i++;
            }
        }

        return result;
    }

    private boolean containsNode(ListSet<TableauNode> neededNodes, TableauNode node) {

        for (TableauNode currentNode : neededNodes) {

            if (node instanceof FormulaNode && currentNode instanceof FormulaNode) {

                FormulaNode fNode = (FormulaNode) node;
                FormulaNode currentFNode = (FormulaNode) currentNode;

                if (fNode.getState().equals(currentFNode.getState())
                        && fNode.getFormula().equals(currentFNode.getFormula())) {

                    neededNodes.remove(currentNode);
                    return true;
                }
            }

            if (node instanceof EdgeNode && currentNode instanceof EdgeNode) {

                EdgeNode eNode = (EdgeNode) node;
                EdgeNode currentENode = (EdgeNode) currentNode;

                if (eNode.getParentState().equals(currentENode.getParentState())
                        && eNode.getChildState().equals(currentENode.getChildState())) {

                    neededNodes.remove(currentNode);
                    return true;
                }
            }
        }

        return false;
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

    private SerializablePair<TreePath, ListSet<SerializablePair<TableauNode, TreePath>>>
            chooseCorrectPath(TableauNode node, TreePath path, ListSet<TableauNode> neededNodes) {

        // result should be the most suitable path after this method is finished
        SerializablePair<TreePath, ListSet<SerializablePair<TableauNode, TreePath>>> result = null;
        int i = 0;

        for (TableauNode child : node.getChildren()) {
            TreePath current = path.clone().child(i);

            ListSet<SerializablePair<TableauNode, TreePath>> missingNodes =
                    checkPath(current, child, neededNodes.clone());

            // current is a correct path, all other paths must be misplaced
            if (missingNodes.isEmpty()) {
                return new SerializablePair<>(current, missingNodes);
            }

            if (result == null) {
                result = new SerializablePair<>(current, missingNodes);
            } else {

                ListSet<SerializablePair<TableauNode, TreePath>> currentMissingNodes =
                        result.second();

                if (currentMissingNodes.contains(missingNodes)) {
                    if (!currentMissingNodes.equals(missingNodes)) {
                        // the new path misses less nodes than the old one
                        result = new SerializablePair<>(current, missingNodes);
                    }
                } else {
                    // the new path must at least miss as much nodes as the old one
                    // paths that do not miss the edge node should be preferred
                    if (containsEdgeNode(currentMissingNodes) && !containsEdgeNode(missingNodes)) {

                        result = new SerializablePair<>(current, missingNodes);
                    }
                }
            }
        }

        return result;
    }

    private boolean containsEdgeNode(ListSet<SerializablePair<TableauNode, TreePath>> nodes) {

        for (SerializablePair<TableauNode, TreePath> pair : nodes) {
            if (pair.first() instanceof EdgeNode) {
                return true;
            }
        }

        return false;
    }
}
