package de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula;

public class ModalFormulaWriterSafeText extends ModalFormulaWriterText {

    public String getDiamond() {
        return "dia";
    }

    public String getBox() {
        return "box";
    }

    public String getConjunction() {
        return "&";
    }

    public String getDisjunction() {
        return "|";
    }

    public String getImplication() {
        return "->";
    }

    public String getEquivalence() {
        return "<->";
    }

    public String getNegation() {
        return "!";
    }

    public String getTrue() {
        return "1";
    }

    public String getFalse() {
        return "0";
    }

    public ModalFormulaWriterSafeText() {
        super();
    }
}
