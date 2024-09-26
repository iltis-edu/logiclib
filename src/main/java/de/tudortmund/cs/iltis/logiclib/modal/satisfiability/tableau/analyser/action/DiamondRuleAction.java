package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class DiamondRuleAction implements TableauAction {

    // needed for serialization
    private DiamondRuleAction() {}

    private TreePath source;
    private ListSet<TreePath> newNodes;

    public DiamondRuleAction(TreePath source, ListSet<TreePath> nodes) {

        this.source = source;
        this.newNodes = nodes;
    }

    public TreePath getSource() {
        return this.source;
    }

    public ListSet<TreePath> getNewNodes() {
        return this.newNodes;
    }
}
