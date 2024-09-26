package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class EdgeNode extends TableauNode {
    /** for serialization */
    private static final long serialVersionUID = 1L;

    private KripkeState parentState;
    private KripkeState childState;
    private ArrayList<FormulaNode> extendedByNodes;

    // needed for serialization
    protected EdgeNode() {}

    public EdgeNode(KripkeState parentState, KripkeState childState) {
        this.parentState = parentState;
        this.childState = childState;
        this.extendedByNodes = new ArrayList<>();
    }

    public EdgeNode(KripkeState parentState, KripkeState childState, TreePath path) {
        this(parentState, childState);
        this.path = path;
    }

    public KripkeState getParentState() {
        return this.parentState;
    }

    public KripkeState getChildState() {
        return this.childState;
    }

    public boolean hasBeenExtendedBy(FormulaNode node) {
        return hasBeenExtendedBy(node, true);
    }

    public boolean hasBeenExtendedBy(FormulaNode node, boolean addressIdentity) {
        if (addressIdentity) return this.extendedByNodes.contains(node);
        else
            return this.extendedByNodes.stream()
                            .filter(
                                    e ->
                                            e.getFormula().equals(node.getFormula())
                                                    && e.getState() != null
                                                    && e.getState().equals(node.getState()))
                            .count()
                    > 0;
    }

    public void extendBy(FormulaNode node) {
        this.extendedByNodes.add(node);
    }

    @Override
    public String toString() {
        return "(" + parentState.writeName() + "," + childState.writeName() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeNode)) return false;
        EdgeNode other = (EdgeNode) o;
        return this.parentState == other.parentState && this.childState == other.childState;
    }

    @Override
    public int hashCode() {
        return 615 + 17 * (this.parentState.hashCode() + 24 * this.childState.hashCode());
    }

    public EdgeNode clone() {
        EdgeNode clone = new EdgeNode();

        List<TableauNode> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((TableauNode) child.clone()));
        clone.children = clonedChildren;
        clone.parentState = parentState.clone();
        clone.childState = childState.clone();
        clone.extendedByNodes = (ArrayList<FormulaNode>) extendedByNodes.clone();
        return clone;
    }
}
