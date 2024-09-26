package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class CloseablePathsFeedback implements TableauAnalyserFeedback {

    private ListSet<TreePath> paths;

    public CloseablePathsFeedback() {
        this.paths = new ListSet<>();
    }

    public CloseablePathsFeedback(ListSet<TreePath> paths) {
        this.paths = paths;
    }

    public ListSet<TreePath> getCloseablePaths() {
        return this.paths;
    }
}
