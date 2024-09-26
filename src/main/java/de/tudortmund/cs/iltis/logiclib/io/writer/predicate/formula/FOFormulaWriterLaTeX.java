package de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.writer.general.LatexIndexedSymbolWriter;

/**
 * This traversal strategy creates a LaTeX string representing the term or formula. Therefore, the
 * {@link LatexIndexedSymbolWriter} is used. The result of {@link #write(TermOrFormula)} is designed
 * to be used in math mode; {@link #writeInline(TermOrFormula)} and {@link
 * #writeDisplay(TermOrFormula)} add designed to work in text mode because they add the respective
 * math mode delimiters.
 *
 * @see FOFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class FOFormulaWriterLaTeX extends FOFormulaWriterText {

    @Override
    protected String getExistentialQuantifier() {
        return "\\exists ";
    }

    @Override
    protected String getUniversalQuantifier() {
        return "\\forall ";
    }

    @Override
    protected String getQuantifierSeparator() {
        return "\\ ";
    }

    @Override
    protected String getConjunction() {
        return "\\wedge ";
    }

    @Override
    protected String getDisjunction() {
        return "\\vee ";
    }

    @Override
    protected String getImplication() {
        return "\\rightarrow ";
    }

    @Override
    protected String getEquivalence() {
        return "\\leftrightarrow ";
    }

    @Override
    protected String getNegation() {
        return "\\neg ";
    }

    @Override
    protected String getTrue() {
        return "\\top ";
    }

    @Override
    protected String getFalse() {
        return "\\bot ";
    }

    public FOFormulaWriterLaTeX() {
        symbolWriter = new LatexIndexedSymbolWriter();
    }

    /** Writes the given symbol with inline math delimiters. */
    public String writeInline(TermOrFormula symbol) {
        return "\\(" + write(symbol) + "\\)";
    }

    /** Writes the given symbol with display math delimiters. */
    public String writeDisplay(TermOrFormula symbol) {
        return "\\[" + write(symbol) + "\\]";
    }
}
