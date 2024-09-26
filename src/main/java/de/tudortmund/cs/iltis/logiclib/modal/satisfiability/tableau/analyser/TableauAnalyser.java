package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.AndRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.BoxRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.ClosePathAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.ClosePathFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.CloseablePathsFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.DiamondRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.OptionalBoxRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.OrRuleAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.SearchCloseablePathsAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauRuleFeedback.NodeConstructionMistake;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class TableauAnalyser {

    /**
     * @param action that the user wants to execute
     * @param context after action
     */
    public TableauAnalyserFeedback apply(TableauAction action, ModalTableau context) {

        if (action instanceof AndRuleAction) {

            AndRuleAnalyser analyser = new AndRuleAnalyser((AndRuleAction) action, context);

            return analyser.apply();
        }

        if (action instanceof OrRuleAction) {

            OrRuleAnalyser analyser = new OrRuleAnalyser((OrRuleAction) action, context);

            return analyser.apply();
        }

        if (action instanceof BoxRuleAction) {

            BoxRuleAnalyser analyser = new BoxRuleAnalyser((BoxRuleAction) action, context);

            return analyser.apply();
        }

        if (action instanceof DiamondRuleAction) {
            DiamondRuleAction diamondAction = (DiamondRuleAction) action;

            DiamondRuleAnalyser analyser = new DiamondRuleAnalyser(diamondAction, context);

            TableauAnalyserFeedback feedback = analyser.apply();

            // check for optional box rule execution
            if (feedback instanceof TableauRuleFeedback) {
                TableauRuleFeedback ruleFeedback = (TableauRuleFeedback) feedback;

                if (ruleFeedback.usesCorrectRule()
                        && ruleFeedback.getMissingNodes().isEmpty()
                        && ruleFeedback.getMisplacedNodes().isEmpty()
                        && !ruleFeedback.getConstructionMistakes().isEmpty()) {

                    return searchForUsedBoxNodes(diamondAction, context, ruleFeedback);
                }
            }

            return feedback;
        }

        if (action instanceof ClosePathAction) {

            ClosePathAnalyser analyser = new ClosePathAnalyser((ClosePathAction) action, context);

            return analyser.apply();
        }

        if (action instanceof SearchCloseablePathsAction) {
            ListSet<TreePath> paths = extractPaths(context);
            ListSet<TreePath> closeablePaths = new ListSet<>();

            for (TreePath path : paths) {
                ClosePathAction singleAction = new ClosePathAction(path);

                ClosePathAnalyser analyser = new ClosePathAnalyser(singleAction, context);

                ClosePathFeedback feedback = (ClosePathFeedback) analyser.apply();

                if (feedback.isPathCloseable()) {
                    closeablePaths.add(path);
                }
            }

            return new CloseablePathsFeedback(closeablePaths);
        }

        return null;
    }

    /** Checks whether the wrong nodes can be used as following box rule execution. */
    private TableauAnalyserFeedback searchForUsedBoxNodes(
            DiamondRuleAction diamondAction,
            ModalTableau context,
            TableauRuleFeedback ruleFeedback) {

        TreePath source = diamondAction.getSource();
        TreePath path = new TreePath();

        List<TreePath> pathQueue = new ArrayList<>();
        pathQueue.add(path);

        ListSet<TreePath> wrongNodes = extractNodes(ruleFeedback.getConstructionMistakes());

        SerializablePair<ListSet<TreePath>, ListSet<TreePath>> result =
                checkPath(source, pathQueue, wrongNodes, context);

        if (result.first().isEmpty()) {
            return new OptionalBoxRuleFeedback(result.second());
        }

        return ruleFeedback;
    }

    /** This method tries to combine possible box actions to remove all mistakes. */
    private SerializablePair<ListSet<TreePath>, ListSet<TreePath>> checkPath(
            TreePath source,
            List<TreePath> pathQueue,
            ListSet<TreePath> wrongNodes,
            ModalTableau context) {

        ListSet<TreePath> usedBoxes = new ListSet<>();

        TreePath path = pathQueue.remove(0);
        TableauNode node = context.getTree().retrieve(path);

        if (node instanceof FormulaNode) {
            FormulaNode currentNode = (FormulaNode) node;

            // check box action
            if (currentNode.getFormula().isBox()) {

                BoxRuleAction action = new BoxRuleAction(path, wrongNodes.clone());

                BoxRuleAnalyser analyser = new BoxRuleAnalyser(action, context);

                TableauAnalyserFeedback feedback = analyser.apply();

                if (feedback instanceof TableauRuleFeedback) {

                    TableauRuleFeedback ruleFeedback = (TableauRuleFeedback) feedback;

                    if (ruleFeedback.getMisplacedNodes().isEmpty()
                            && ruleFeedback.getMissingNodes().isEmpty()) {

                        // box action was successful
                        usedBoxes.add(path);

                        wrongNodes.removeAll(extractNodes(ruleFeedback.getConstructionMistakes()));
                    }
                }
            }
        }

        if (wrongNodes.isEmpty()) {
            return new SerializablePair<>(wrongNodes, usedBoxes);
        }

        int i = 0;

        for (TableauNode child : node.getChildren()) {
            TreePath current = path.clone().child(i);
            pathQueue.add(current);
            i++;
        }

        if (pathQueue.isEmpty()) {
            return new SerializablePair<>(wrongNodes, usedBoxes);
        }

        SerializablePair<ListSet<TreePath>, ListSet<TreePath>> result =
                checkPath(source, pathQueue, wrongNodes, context);

        result.second().addAll(usedBoxes);

        return result;
    }

    private ListSet<TreePath> extractNodes(
            ListSet<
                            SerializablePair<
                                    SerializablePair<TableauNode, TreePath>,
                                    NodeConstructionMistake>>
                    constructionMistakes) {

        ListSet<TreePath> result = new ListSet<>();

        for (SerializablePair<SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>
                pair : constructionMistakes) {

            result.add(pair.first().second());
        }

        return result;
    }

    private ListSet<TreePath> extractPaths(ModalTableau context) {
        TreePath path = new TreePath();
        TableauNode current = context.getTree();

        return traverseChildren(path, current);
    }

    private ListSet<TreePath> traverseChildren(TreePath path, TableauNode current) {

        if (current.isLeaf()) {
            return new ListSet<TreePath>(path);
        }

        int i = 0;
        ListSet<TreePath> result = new ListSet<>();

        for (TableauNode node : current.getChildren()) {
            result.addAll(traverseChildren(path.clone().child(i), node));
            i++;
        }

        return result;
    }
}
