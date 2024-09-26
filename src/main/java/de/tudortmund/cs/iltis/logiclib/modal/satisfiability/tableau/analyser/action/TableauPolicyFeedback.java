package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class TableauPolicyFeedback implements TableauAnalyserFeedback {

    private boolean isPolicyBreached;
    private ListSet<TreePath> unfinishedNodes;
    private ListSet<SerializablePair<TreePath, TreePath>> nodesAddedInWrongOrder;

    public TableauPolicyFeedback() {
        this.isPolicyBreached = false;
        this.unfinishedNodes = new ListSet<>();
        this.nodesAddedInWrongOrder = new ListSet<>();
    }

    public TableauPolicyFeedback(
            ListSet<TreePath> unfinishedNodes,
            ListSet<SerializablePair<TreePath, TreePath>> nodesAddedInWrongOrder) {

        this.isPolicyBreached = false;
        this.unfinishedNodes = unfinishedNodes;
        this.nodesAddedInWrongOrder = new ListSet<>();
    }

    public boolean isPolicyBreached() {
        return this.isPolicyBreached;
    }

    public ListSet<TreePath> getUnfinishedNodes() {
        return this.unfinishedNodes;
    }

    public ListSet<SerializablePair<TreePath, TreePath>> getNodesAddedInWrongOrder() {
        return this.nodesAddedInWrongOrder;
    }
}
