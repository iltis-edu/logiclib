package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.ClosePathAction;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.ClosePathFeedback;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action.TableauAnalyserFeedback;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.HashMap;
import java.util.Map;

public class ClosePathAnalyser {

    private FormulaNode root;
    private TreePath path;

    public ClosePathAnalyser(ClosePathAction action, ModalTableau context) {
        this.root = (FormulaNode) context.getTree();
        this.path = action.getSource();
    }

    public TableauAnalyserFeedback apply() {
        // valuation is used to detect contradictions on path
        Map<SerializablePair<KripkeState, ModalFormula>, TreePath> valuation = new HashMap<>();
        TreePath current = new TreePath();
        TableauNode node = root;

        for (Integer index : path) {
            node = node.getChild(index);
            current = current.clone().child(index);

            if (node instanceof FormulaNode) {
                FormulaNode fNode = (FormulaNode) node;
                ModalFormula formula = fNode.getFormula();
                KripkeState state = fNode.getState();

                if (formula.isVariable()) {

                    SerializablePair<KripkeState, ModalFormula> configuration =
                            new SerializablePair<>(state, formula);

                    SerializablePair<KripkeState, ModalFormula> contradiction =
                            new SerializablePair<>(state, formula.not());

                    if (valuation.containsKey(contradiction)) {
                        // contradiction found, path should be closed

                        return new ClosePathFeedback(
                                new SerializablePair<>(current, valuation.get(contradiction)));
                    }

                    if (!valuation.containsKey(configuration)) {
                        valuation.put(configuration, current);
                    }
                } else if (formula.isFalse()) {
                    return new ClosePathFeedback(new SerializablePair<>(current, current));
                } else if (formula.isLiteral()) {

                    SerializablePair<KripkeState, ModalFormula> configuration =
                            new SerializablePair<>(state, formula);

                    SerializablePair<KripkeState, ModalFormula> contradiction =
                            new SerializablePair<>(state, formula.getSubformula(0));

                    if (valuation.containsKey(contradiction)) {
                        // contradiction found, path should be closed

                        return new ClosePathFeedback(
                                new SerializablePair<>(current, valuation.get(contradiction)));
                    }

                    if (!valuation.containsKey(configuration)) {
                        valuation.put(configuration, current);
                    }
                }
            }
        }

        // no contradiction found, path should not be closed
        return new ClosePathFeedback();
    }
}
