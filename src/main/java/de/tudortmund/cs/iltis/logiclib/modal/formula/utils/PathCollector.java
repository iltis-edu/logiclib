package de.tudortmund.cs.iltis.logiclib.modal.formula.utils;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.DefaultTraversalStrategy;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class PathCollector
        extends DefaultTraversalStrategy<ModalFormula, SortedSet<TreePath>, SortedSet<TreePath>> {
    public PathCollector() {
        this.path = new TreePath();
    }

    protected abstract boolean isSatisfying(ModalFormula item);

    @Override
    protected SortedSet<TreePath> value(SortedSet<TreePath> collectedValue, ModalFormula item) {
        if (isSatisfying(item)) collectedValue.add(this.path.clone());
        return collectedValue;
    }

    @Override
    protected SortedSet<TreePath> collect(
            SortedSet<TreePath> collectedValue, SortedSet<TreePath> nextValue) {
        if (collectedValue == null) collectedValue = new TreeSet<TreePath>();
        if (nextValue == null) nextValue = new TreeSet<TreePath>();
        collectedValue.addAll(nextValue);
        return collectedValue;
    }

    @Override
    public void nextLevel() {
        path.push(0);
    }

    @Override
    public void previousLevel() {
        path.pop();
    }

    @Override
    public void nextSibling() {
        final int last = path.pop();
        path.push(last + 1);
    }

    protected TreePath path;
}
