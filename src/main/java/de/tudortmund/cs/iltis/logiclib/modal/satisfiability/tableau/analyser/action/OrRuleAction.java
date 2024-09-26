package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class OrRuleAction implements TableauAction {

    // needed for serialization
    private OrRuleAction() {}

    private TreePath source;
    private ListSet<TreePath> newNodes;

    public OrRuleAction(TreePath source, ListSet<TreePath> nodes) {

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
