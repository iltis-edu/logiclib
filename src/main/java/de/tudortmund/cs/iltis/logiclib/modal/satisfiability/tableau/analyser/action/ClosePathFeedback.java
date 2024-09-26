package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class ClosePathFeedback implements TableauAnalyserFeedback {

    private boolean isPathCloseable;
    private SerializablePair<TreePath, TreePath> contradiction;

    public ClosePathFeedback() {
        this.isPathCloseable = false;
        this.contradiction = null;
    }

    public ClosePathFeedback(SerializablePair<TreePath, TreePath> pair) {
        this.isPathCloseable = true;
        this.contradiction = pair;
    }

    public boolean isPathCloseable() {
        return this.isPathCloseable;
    }

    public SerializablePair<TreePath, TreePath> getContradiction() {
        return this.contradiction;
    }
}
