package de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import java.util.List;

/**
 * This writer returns a string representing the formula using <b>HTML</b> characters for logical
 * symbols.
 *
 * @see ModalFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class ModalFormulaWriterHTML extends ModalFormulaWriterText {

    @Override
    public String getDiamond() {
        return "&#x25C7;";
    }

    @Override
    public String getBox() {
        return "&#x2610;";
    }

    @Override
    public String getConjunction() {
        return "&and;";
    }

    @Override
    public String getDisjunction() {
        return "&or;";
    }

    @Override
    public String getImplication() {
        return "&rarr;";
    }

    @Override
    public String getEquivalence() {
        return "&harr;";
    }

    @Override
    public String getNegation() {
        return "&not;";
    }

    @Override
    public String getTrue() {
        return "&#8868;";
    }

    @Override
    public String getFalse() {
        return "&#8869;";
    }

    public String inspect(final Variable item, List<String> subformulae) {
        return item.getName().toHTMLString();
    }
}
