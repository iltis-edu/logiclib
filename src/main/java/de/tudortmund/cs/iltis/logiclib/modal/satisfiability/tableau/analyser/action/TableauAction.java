package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.analyser.action;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.io.Serializable;

public interface TableauAction extends Serializable {

    TreePath getSource();

    ListSet<TreePath> getNewNodes();
}
