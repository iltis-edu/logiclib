package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeStructure;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class FormulaNode extends TableauNode {
    /** for serialization */
    private static final long serialVersionUID = 1L;

    private KripkeState state;
    private ModalFormula formula;
    private KripkeStructure structure;
    private boolean closed;

    // needed for serialization
    protected FormulaNode() {}

    public FormulaNode(KripkeState state, ModalFormula formula) {
        this.state = state;
        this.formula = formula;
        this.structure = null;
        this.closed = false;
    }

    public FormulaNode(KripkeState state, ModalFormula formula, TreePath path) {
        this(state, formula);
        this.path = path;
    }

    public KripkeState getState() {
        return this.state;
    }

    public void setState(KripkeState state) {
        this.state = state;
    }

    public ModalFormula getFormula() {
        return this.formula;
    }

    public KripkeStructure getStructure() {
        return this.structure;
    }

    public void setStructure(KripkeStructure structure) {
        this.structure = structure;
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    @Override
    public void open() {
        this.closed = false;
    }

    @Override
    public String toString() {
        return "(" + this.state + "," + this.formula + ":" + ")";
    }

    @Override
    public boolean equals(Object o) {
        // Important: this has to equality based (address identity),
        // otherwise the extendedByNodes attribute in EdgeNodes will
        // NOT work properly.
        return this == o;
    }

    @Override
    public int hashCode() {
        return 99 + (41 * this.state.hashCode() + 8 * this.formula.hashCode());
    }

    @Override
    public FormulaNode clone() {
        FormulaNode clone = new FormulaNode();

        List<TableauNode> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((TableauNode) child.clone()));
        clone.children = clonedChildren;
        clone.state = state.clone();
        clone.formula = formula.clone();
        if (structure != null) clone.structure = structure.clone();
        clone.closed = closed;
        return clone;
    }
}
