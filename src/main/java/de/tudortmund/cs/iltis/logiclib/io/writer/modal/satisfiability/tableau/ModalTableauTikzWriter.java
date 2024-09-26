package de.tudortmund.cs.iltis.logiclib.io.writer.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.ModalTableau;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;
import de.tudortmund.cs.iltis.utils.io.writer.tree.TikzTreeWriter;

public class ModalTableauTikzWriter implements Writer<ModalTableau> {

    private TikzTreeWriter<TableauNode> treeWriter;
    private TableauNodeWriter nodeWriter;

    static class NodeStyler implements Writer<TableauNode> {
        @Override
        public String write(TableauNode node) {
            if (node.isLeaf()) {
                if (node.isClosed()) return "label={below:$\\closedLeaf$}";
                else return "label={below:$\\openLeaf$}";
            }
            return "";
        }
    }

    public ModalTableauTikzWriter() {
        ModalFormulaWriterLaTeX formulaWriter = new ModalFormulaWriterLaTeX();
        this.nodeWriter = new TableauNodeWriter(formulaWriter, "$", "$");
        this.nodeWriter.setElementSymbol("\\in");
        this.treeWriter = new TikzTreeWriter<>(nodeWriter, new NodeStyler());
    }

    public String write(FormulaNode root) {
        ModalFormula formula = root.getFormula();
        if (formula.isPropositional()) this.nodeWriter.setPropositional();
        else this.nodeWriter.setModal();
        return this.treeWriter.write(root);
    }

    @Override
    public String write(ModalTableau tableau) {
        ModalFormula formula = ((FormulaNode) tableau.getTree()).getFormula();
        if (formula.isPropositional()) this.nodeWriter.setPropositional();
        else this.nodeWriter.setModal();
        return this.treeWriter.write(tableau.getTree());
    }
}
