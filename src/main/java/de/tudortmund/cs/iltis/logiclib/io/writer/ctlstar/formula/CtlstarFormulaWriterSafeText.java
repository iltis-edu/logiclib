package de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula;

import de.tudortmund.cs.iltis.utils.io.writer.general.SafeTextIndexedSymbolWriter;

/**
 * This traversal strategy returns a string representing the CTL Star formula using <b>plain
 * text</b> characters for operators and atoms. LTL and CTL operators are text, hence these methods
 * are not overridden here.
 *
 * @see CtlstarFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class CtlstarFormulaWriterSafeText extends CtlstarFormulaWriterText {

    public CtlstarFormulaWriterSafeText() {
        indexedSymbolWriter = new SafeTextIndexedSymbolWriter();
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // methods returning the plain text value for the respective CTL Star operators and atoms
    ///////////////////////////////////////////////////////////////////////////////////////

    // Atoms
    // ===========================================================================================================

    @Override
    protected String getFalse() {
        return "0";
    }

    @Override
    protected String getTrue() {
        return "1";
    }

    // Propositional Logic Operators
    // ===================================================================================

    @Override
    protected String getConjunction() {
        return "&";
    }

    @Override
    protected String getDisjunction() {
        return "|";
    }

    @Override
    protected String getEquivalence() {
        return "<->";
    }

    @Override
    protected String getImplication() {
        return "->";
    }

    @Override
    protected String getNegation() {
        return "!";
    }
}
