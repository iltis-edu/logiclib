package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser;

import java.io.Serializable;

public class TableauPolicy implements Serializable {

    private ProcessingOrder order;
    private boolean finishBranchBeforeClosing;
    private boolean addNewNodesInOrder;

    public TableauPolicy() {
        this.order = ProcessingOrder.TOP_TO_BOTTOM;
        this.finishBranchBeforeClosing = true;
        this.addNewNodesInOrder = true;
    }

    public TableauPolicy(
            ProcessingOrder order, boolean finishBranchBeforeClosing, boolean addNewNodesInOrder) {

        this.order = order;
        this.finishBranchBeforeClosing = finishBranchBeforeClosing;
        this.addNewNodesInOrder = addNewNodesInOrder;
    }

    public ProcessingOrder getProcessingOrder() {
        return this.order;
    }

    public boolean finishBranchBeforeClosing() {
        return this.finishBranchBeforeClosing;
    }

    public boolean addNewNodesInOrder() {
        return this.addNewNodesInOrder;
    }

    public enum ProcessingOrder {
        TOP_TO_BOTTOM,
        BOXES_AFTER_DIAMOND,
        ARBITRARY
    }
}
