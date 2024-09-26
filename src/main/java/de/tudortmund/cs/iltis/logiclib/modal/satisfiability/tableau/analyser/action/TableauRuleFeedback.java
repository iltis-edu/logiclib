package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class TableauRuleFeedback implements TableauAnalyserFeedback {

    private SerializablePair<Boolean, RuleType> ruleEvaluation;

    private ListSet<
                    SerializablePair<
                            SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
            constructionMistakes;

    private ListSet<SerializablePair<TableauNode, TreePath>> missingNodes;
    private ListSet<SerializablePair<TableauNode, TreePath>> misplacedNodes;

    public TableauRuleFeedback() {
        this.ruleEvaluation = new SerializablePair<>(true, null);
        this.constructionMistakes = new ListSet<>();
        this.missingNodes = new ListSet<>();
        this.misplacedNodes = new ListSet<>();
    }

    public TableauRuleFeedback(RuleType type) {
        this.ruleEvaluation = new SerializablePair<>(false, type);
        this.constructionMistakes = new ListSet<>();
        this.missingNodes = new ListSet<>();
        this.misplacedNodes = new ListSet<>();
    }

    public TableauRuleFeedback(
            ListSet<
                            SerializablePair<
                                    SerializablePair<TableauNode, TreePath>,
                                    NodeConstructionMistake>>
                    constructionMistakes,
            ListSet<SerializablePair<TableauNode, TreePath>> missingNodes,
            ListSet<SerializablePair<TableauNode, TreePath>> misplacedNodes) {

        this.ruleEvaluation = new SerializablePair<>(true, null);
        this.constructionMistakes = constructionMistakes;
        this.missingNodes = missingNodes;
        this.misplacedNodes = misplacedNodes;
    }

    public boolean usesCorrectRule() {
        return this.ruleEvaluation.first();
    }

    public RuleType getNeededRuleType() {
        return this.ruleEvaluation.second();
    }

    public ListSet<
                    SerializablePair<
                            SerializablePair<TableauNode, TreePath>, NodeConstructionMistake>>
            getConstructionMistakes() {

        return this.constructionMistakes;
    }

    public ListSet<SerializablePair<TableauNode, TreePath>> getMissingNodes() {
        return this.missingNodes;
    }

    public ListSet<SerializablePair<TableauNode, TreePath>> getMisplacedNodes() {
        return this.misplacedNodes;
    }

    public enum RuleType {
        CONJUNCTION,
        DISJUNCTION,
        BOX,
        DIAMOND
    }

    public enum NodeConstructionMistake {
        WRONG_NODE_TYPE,
        WRONG_FORMULA,
        WRONG_STATE
    }
}
