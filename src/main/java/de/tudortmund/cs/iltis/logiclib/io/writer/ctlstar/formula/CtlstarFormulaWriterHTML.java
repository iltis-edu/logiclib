package de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula;

import de.tudortmund.cs.iltis.utils.io.writer.general.HTMLIndexedSymbolWriter;

/**
 * This traversal strategy returns a string representing the CTL Star formula using <b>HTML</b>
 * characters for operators and atoms.
 *
 * @see CtlstarFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class CtlstarFormulaWriterHTML extends CtlstarFormulaWriterText {

    public CtlstarFormulaWriterHTML() {
        indexedSymbolWriter = new HTMLIndexedSymbolWriter();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // methods returning the HTML value for the respective CTL Star operators and atoms
    ////////////////////////////////////////////////////////////////////////////////////

    // Atoms
    // ===========================================================================================================

    @Override
    protected String getFalse() {
        return "&#8869;";
    }

    @Override
    protected String getTrue() {
        return "&#8868;";
    }

    // CTL Specific Operators
    // ==========================================================================================

    @Override
    protected String getAll() {
        return "&#65;";
    }

    @Override
    protected String getExists() {
        return "&#69;";
    }

    // LTL Specific Operators
    // ==========================================================================================

    @Override
    protected String getFinally() {
        return "&#70;";
    }

    @Override
    protected String getGlobally() {
        return "&#71;";
    }

    @Override
    protected String getNext() {
        return "&#88;";
    }

    @Override
    protected String getRelease() {
        return "&#82;";
    }

    @Override
    protected String getUntil() {
        return "&#85;";
    }

    @Override
    protected String getWeakUntil() {
        return "&#87;";
    }

    // Propositional Logic Operators
    // ===================================================================================

    @Override
    protected String getConjunction() {
        return "&and;";
    }

    @Override
    protected String getDisjunction() {
        return "&or;";
    }

    @Override
    protected String getEquivalence() {
        return "&harr;";
    }

    @Override
    protected String getImplication() {
        return "&rarr;";
    }

    @Override
    protected String getNegation() {
        return "&not;";
    }
}
