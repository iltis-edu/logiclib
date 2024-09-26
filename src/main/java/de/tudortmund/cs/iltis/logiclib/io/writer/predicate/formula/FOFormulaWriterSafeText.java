package de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula;

import de.tudortmund.cs.iltis.utils.io.writer.general.SafeTextIndexedSymbolWriter;

/**
 * This traversal strategy returns a string representing the formula using <b>plain text</b>
 * characters for logical symbols.
 *
 * @see FOFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class FOFormulaWriterSafeText extends FOFormulaWriterText {

    @Override
    protected String getExistentialQuantifier() {
        return "exists";
    }

    @Override
    protected String getUniversalQuantifier() {
        return "forall";
    }

    @Override
    protected String getConjunction() {
        return "&";
    }

    @Override
    protected String getDisjunction() {
        return "|";
    }

    @Override
    protected String getImplication() {
        return "->";
    }

    @Override
    protected String getEquivalence() {
        return "<->";
    }

    @Override
    protected String getNegation() {
        return "!";
    }

    @Override
    protected String getTrue() {
        return "1";
    }

    @Override
    protected String getFalse() {
        return "0";
    }

    // Constructor

    public FOFormulaWriterSafeText() {
        symbolWriter = new SafeTextIndexedSymbolWriter();
    }
}
