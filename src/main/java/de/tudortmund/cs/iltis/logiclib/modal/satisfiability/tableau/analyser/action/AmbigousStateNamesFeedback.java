package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

public class AmbigousStateNamesFeedback implements TableauAnalyserFeedback {

    // needed for serialization
    private AmbigousStateNamesFeedback() {}

    private boolean hasNameConflict;

    public AmbigousStateNamesFeedback(boolean hasNameConflict) {
        this.hasNameConflict = hasNameConflict;
    }

    /**
     * @return true, if the state name is already used in the tableau false, if there are new nodes
     *     with different states
     */
    public boolean hasNameConflict() {
        return this.hasNameConflict;
    }
}
