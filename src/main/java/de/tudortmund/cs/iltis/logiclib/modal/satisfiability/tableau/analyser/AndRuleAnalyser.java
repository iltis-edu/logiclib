package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.EdgeNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.AndRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.NodeConstructionMistake;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.RuleType;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class AndRuleAnalyser {

    private FormulaNode root;
    private TreePath source;

    /**
     * At first {@code newNodes} contains all nodes that were added in this step. After a new node
     * has been identified as valid or invalid it will be removed from {@code newNodes}.
     */
    private ListSet<TreePath> newNodes;

    public AndRuleAnalyser(AndRuleAction action, ModalTableau context) {
        this.root = (FormulaNode) context.getTree();
        this.source = action.getSource();
        this.newNodes = action.getNewNodes();
    }

    public TableauAnalyserFeedback apply() {
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);
        ModalFormula formula = sourceNode.getFormula();

        if (!formula.isConjunction()) {
            return new TableauRuleFeedback(RuleType.CONJUNCTION);
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
                // only formula nodes can be added via and-rule execution
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
        ModalFormula formula = sourceNode.getFormula();
        ModalFormula firstSubformula = formula.getSubformula(0);
        ModalFormula secondSubformula = formula.getSubformula(1);

        ListSet<ModalFormula> neededNodes = new ListSet<>(firstSubformula, secondSubformula);

        return checkPath(source, sourceNode, neededNodes);
    }

    /** checks whether the descendants of {@code node} contain all needed nodes */
    private ListSet<SerializablePair<TableauNode, TreePath>> checkPath(
            TreePath path, TableauNode node, ListSet<ModalFormula> neededNodes) {

        ListSet<SerializablePair<TableauNode, TreePath>> result = new ListSet<>();
        FormulaNode sourceNode = (FormulaNode) this.root.retrieve(source);

        if (newNodes.contains(path)) {
            // only new nodes can be needed
            ModalFormula formula = ((FormulaNode) node).getFormula();

            if (neededNodes.contains(formula)) {
                this.newNodes.remove(path);
                neededNodes.remove(formula);
            }

            if (neededNodes.isEmpty()) {
                return result;
            }
        }

        if (node.isLeaf()) {
            KripkeState state = sourceNode.getState();

            // all needed nodes can now be returned as missing
            for (ModalFormula formula : neededNodes) {
                result.add(new SerializablePair<>(new FormulaNode(state, formula), path));
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
            chooseCorrectPath(TableauNode node, TreePath path, ListSet<ModalFormula> neededNodes) {

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
                    // paths that do not miss the first subformula should be preferred
                    if (containsFirstSubformula(currentMissingNodes)
                            && !containsFirstSubformula(missingNodes)) {

                        result = new SerializablePair<>(current, missingNodes);
                    }
                }
            }

            i++;
        }

        return result;
    }

    private boolean containsFirstSubformula(
            ListSet<SerializablePair<TableauNode, TreePath>> nodes) {

        ModalFormula firstSubformula =
                ((FormulaNode) this.root.retrieve(source)).getFormula().getSubformula(0);

        for (SerializablePair<TableauNode, TreePath> pair : nodes) {
            ModalFormula formula = ((FormulaNode) pair.first()).getFormula();

            if (formula.equals(firstSubformula)) {
                return true;
            }
        }

        return false;
    }
}
