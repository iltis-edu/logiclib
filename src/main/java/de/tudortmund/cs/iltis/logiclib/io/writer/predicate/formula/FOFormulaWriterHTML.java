package de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula;

import de.tudortmund.cs.iltis.utils.io.writer.general.HTMLIndexedSymbolWriter;

/**
 * This traversal strategy returns a string representing the formula using <b>HTML</b> characters
 * for logical symbols.
 *
 * @see FOFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class FOFormulaWriterHTML extends FOFormulaWriterText {

    @Override
    protected String getExistentialQuantifier() {
        return "&exist;";
    }

    @Override
    protected String getUniversalQuantifier() {
        return "&forall;";
    }

    @Override
    protected String getQuantifierSeparator() {
        return "&nbsp;";
    }

    @Override
    protected String getConjunction() {
        return "&and;";
    }

    @Override
    protected String getDisjunction() {
        return "&or;";
    }

    @Override
    protected String getImplication() {
        return "&rarr;";
    }

    @Override
    protected String getEquivalence() {
        return "&harr;";
    }

    @Override
    protected String getNegation() {
        return "&not;";
    }

    @Override
    protected String getTrue() {
        return "&top;";
    }

    @Override
    protected String getFalse() {
        return "&bottom;";
    }

    public FOFormulaWriterHTML() {
        symbolWriter = new HTMLIndexedSymbolWriter();
    }
}
