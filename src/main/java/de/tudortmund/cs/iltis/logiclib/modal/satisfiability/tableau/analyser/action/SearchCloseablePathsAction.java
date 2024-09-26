package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class SearchCloseablePathsAction implements TableauAction {

    @Override
    public TreePath getSource() {
        return null;
    }

    @Override
    public ListSet<TreePath> getNewNodes() {
        return new ListSet<>();
    }
}
