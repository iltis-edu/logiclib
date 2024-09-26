package de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;
import java.util.List;

public class ModalFormulaWriterLaTeX extends ModalFormulaWriterText
        implements Writer<ModalFormula> {

    @Override
    public String getDiamond() {
        return "\\Diamond ";
    }

    @Override
    public String getBox() {
        return "\\Box ";
    }

    @Override
    public String getConjunction() {
        return " \\land ";
    }

    @Override
    public String getDisjunction() {
        return " \\lor ";
    }

    @Override
    public String getImplication() {
        return " \\to ";
    }

    @Override
    public String getEquivalence() {
        return " \\leftrightarrow ";
    }

    @Override
    public String getNegation() {
        return "\\lnot ";
    }

    @Override
    public String getTrue() {
        return " \\top ";
    }

    @Override
    public String getFalse() {
        return " \\bot ";
    }

    public String inspect(final Variable item, List<String> subformulae) {
        return item.toString();
    }

    public String write(ModalFormula formula) {
        return super.chop(formula.traverse(this));
    }
}
