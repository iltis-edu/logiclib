package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;

public abstract class TableauNode extends Tree<TableauNode> {

    /** for serialization */
    private static final long serialVersionUID = 1L;

    protected TreePath path;

    public TreePath getPath() {
        return this.path.clone();
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {}

    public void open() {}

    @Override
    public void addChild(TableauNode subtree) {
        super.addChild(subtree);
    }

    @Override
    public void addChildren(Iterable<? extends TableauNode> subtrees) {
        super.addChildren(subtrees);
    }

    public void removeChild(TableauNode subtree) {
        children.remove(subtree);
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
