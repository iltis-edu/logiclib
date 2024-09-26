package de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.utils.io.writer.general.LatexIndexedSymbolWriter;

/**
 * This traversal strategy returns a string representing the CTL Star formula using <b>LaTeX</b>
 * commands for operators and atoms. LTL and CTL operators are plain text in LaTeX, hence these
 * methods are not overridden here.
 *
 * @see CtlstarFormulaWriterText TextFormulaWriter: for further information on formatting.
 */
public class CtlstarFormulaWriterLaTeX extends CtlstarFormulaWriterText {

    public CtlstarFormulaWriterLaTeX() {
        indexedSymbolWriter = new LatexIndexedSymbolWriter();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // methods returning the LaTeX value for the respective CTL Star operators and atoms
    ////////////////////////////////////////////////////////////////////////////////////

    // Atoms
    // ===========================================================================================================

    @Override
    protected String getFalse() {
        return "\\bot ";
    }

    @Override
    protected String getTrue() {
        return "\\top ";
    }

    // Propositional Logic Operators
    // ===================================================================================

    @Override
    protected String getConjunction() {
        return "\\wedge ";
    }

    @Override
    protected String getDisjunction() {
        return "\\vee ";
    }

    @Override
    protected String getEquivalence() {
        return "\\leftrightarrow ";
    }

    @Override
    protected String getImplication() {
        return "\\rightarrow ";
    }

    @Override
    protected String getNegation() {
        return "\\neg ";
    }

    ////////////////////////////////////////////////////////////////////////
    // write methods which simultaneously create a LaTeX maths environment
    //////////////////////////////////////////////////////////////////////

    /** Writes the given CTL Star formula in an inline maths environment. */
    public String writeInline(CtlStarFormula formula) {
        return "\\(" + writeAndChop(formula) + "\\)";
    }

    /**
     * Writes the given CTL Star formula in an equation environment (offset from surrounding text).
     */
    public String writeEquation(CtlStarFormula formula) {
        return "\\[" + writeAndChop(formula) + "\\]";
    }
}
