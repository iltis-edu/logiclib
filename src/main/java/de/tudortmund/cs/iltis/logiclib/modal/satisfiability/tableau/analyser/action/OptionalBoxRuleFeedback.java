package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public class OptionalBoxRuleFeedback implements TableauAnalyserFeedback {

    // needed for serialization
    private OptionalBoxRuleFeedback() {}

    private ListSet<TreePath> usedBoxes;

    public OptionalBoxRuleFeedback(ListSet<TreePath> usedBoxes) {
        this.usedBoxes = usedBoxes;
    }

    public ListSet<TreePath> getUsedBoxes() {
        return this.usedBoxes;
    }
}
