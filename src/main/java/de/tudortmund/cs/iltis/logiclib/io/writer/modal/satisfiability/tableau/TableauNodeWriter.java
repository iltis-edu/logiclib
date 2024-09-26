package de.tudortmund.cs.iltis.logiclib.io.writer.modal.satisfiability.tableau;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.EdgeNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.FormulaNode;
import de.tudortmund.cs.iltis.logiclib.modal.satisfiability.tableau.TableauNode;
import de.tudortmund.cs.iltis.utils.io.writer.general.DefaultWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class TableauNodeWriter implements Writer<TableauNode> {
    private Writer<ModalFormula> formulaWriter;
    private boolean propositional;
    private String elem;
    private String pre;
    private String post;

    public TableauNodeWriter() {
        this(new DefaultWriter<>());
    }

    public TableauNodeWriter(Writer<ModalFormula> formulaWriter) {
        this(formulaWriter, "", "");
    }

    public TableauNodeWriter(Writer<ModalFormula> formulaWriter, String pre, String post) {
        this.formulaWriter = formulaWriter;
        this.pre = pre;
        this.post = post;
        this.elem = "∈";
        this.propositional = false;
    }

    public void setElementSymbol(String elem) {
        this.elem = elem;
    }

    public void setPropositional() {
        this.propositional = true;
    }

    public void setModal() {
        this.propositional = false;
    }

    @Override
    public String write(TableauNode node) {
        if (node instanceof EdgeNode) return this.pre + this.write((EdgeNode) node) + this.post;
        else return this.pre + this.write((FormulaNode) node) + this.post;
    }

    private String write(EdgeNode node) {
        String output = "(";
        output += node.getParentState().writeName();
        output += ",";
        output += node.getChildState().writeName();
        output += ") " + this.elem + " E";
        return output;
    }

    private String write(FormulaNode node) {
        String output = "";
        if (!this.propositional) output += node.getState().writeName() + ", ";
        output += this.formulaWriter.write(node.getFormula());
        return output;
    }
}
