package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class ClosePathAction implements TableauAction {

    // needed for serialization
    private ClosePathAction() {}

    private TreePath path;

    public ClosePathAction(TreePath path) {
        this.path = path;
    }

    public TreePath getSource() {
        return this.path;
    }

    public ListSet<TreePath> getNewNodes() {
        return new ListSet<>();
    }
}
