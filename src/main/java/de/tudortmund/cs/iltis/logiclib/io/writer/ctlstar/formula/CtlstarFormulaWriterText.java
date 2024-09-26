package de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.CtlStarFormula;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.False;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.Proposition;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.True;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional.*;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.writer.general.TextIndexedSymbolWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.List;

/**
 * This traversal strategy returns a string representing the CTL Star formula using unicode
 * characters in place of operators and atoms via method {@link #write(CtlStarFormula)}
 *
 * <p>NOTE:
 *
 * <ul>
 *   <li>CTL operators are built from All and Exists symbols along with LTL operators. eg. All
 *       Finally:= AF
 *   <li>Every binary CTL operator has parentheses as follows: eg. All Until:= A( _ U _ ) eg.
 *       Until:= ( _ U _ )
 *   <li>No unary CTL operator is surrounded by parentheses.
 *   <li>There are no line breaks inserted.
 *   <li>Every implication and equivalence is surrounded by parentheses.
 *   <li>Every conjunction and disjunction is surrounded by parentheses.
 *   <li>Unnecessary parentheses at the top of a formula, e.g. in (A∧¬B) remain. These can be
 *       omitted by using {@link #writeAndChop(CtlStarFormula)}.
 * </ul>
 */
public class CtlstarFormulaWriterText
        implements TraversalStrategy<CtlStarFormula, String>, Writer<CtlStarFormula> {

    public CtlstarFormulaWriterText() {
        indexedSymbolWriter = new TextIndexedSymbolWriter();
    }

    /**
     * the Indexed Symbol write method returns the input symbol as a string, when applicable with
     * "^" + superscript and "_" + subscript
     */
    protected Writer<IndexedSymbol> indexedSymbolWriter;

    //////////////////////////////////////////////////////////////////////////////////////
    // methods returning the unicode value for the respective CTL Star operators and atoms
    ////////////////////////////////////////////////////////////////////////////////////

    // Atoms
    // ===========================================================================================================

    protected String getFalse() {
        return "⊥";
    }

    protected String getTrue() {
        return "⊤";
    }

    // CTL Specific Operators
    // ==========================================================================================

    protected String getAll() {
        return "A";
    }

    protected String getExists() {
        return "E";
    }

    // LTL Specific Operators
    // ==========================================================================================

    protected String getFinally() {
        return "F";
    }

    protected String getGlobally() {
        return "G";
    }

    protected String getNext() {
        return "X";
    }

    protected String getRelease() {
        return "R";
    }

    protected String getUntil() {
        return "U";
    }

    protected String getWeakUntil() {
        return "W";
    }

    // Propositional Logic Operators
    // ===================================================================================

    protected String getConjunction() {
        return "∧";
    }

    protected String getDisjunction() {
        return "∨";
    }

    protected String getEquivalence() {
        return "↔";
    }

    protected String getImplication() {
        return "→";
    }

    protected String getNegation() {
        return "¬";
    }

    ///////////////////////////////////////////////////////////////////
    // inspect methods for the respective CTL Star operators and atoms
    //////////////////////////////////////////////////////////////////

    // Atoms
    // ===========================================================================================================

    protected String inspect(False item, List<String> subformulae) {
        return getFalse();
    }

    protected String inspect(True item, List<String> subformulae) {
        return getTrue();
    }

    protected String inspect(Proposition item, List<String> subformulae) {
        if (!subformulae.isEmpty())
            throw new IllegalStateException("Proposition shouldn't have children!");
        IndexedSymbol symbol = item.getName();
        return indexedSymbolWriter.write(symbol);
    }

    // CTL Specific Operators
    // ==========================================================================================

    protected String inspect(AllFinally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getAll() + getFinally() + subformulae.get(0);
    }

    protected String inspect(AllGlobally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getAll() + getGlobally() + subformulae.get(0);
    }

    protected String inspect(AllNext item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getAll() + getNext() + subformulae.get(0);
    }

    protected String inspect(AllRelease item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getAll() + "(" + subformulae.get(0) + getRelease() + subformulae.get(1) + ")";
    }

    protected String inspect(AllUntil item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getAll() + "(" + subformulae.get(0) + getUntil() + subformulae.get(1) + ")";
    }

    protected String inspect(AllWeakUntil item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getAll() + "(" + subformulae.get(0) + getWeakUntil() + subformulae.get(1) + ")";
    }

    protected String inspect(ExistsFinally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getExists() + getFinally() + subformulae.get(0);
    }

    protected String inspect(ExistsGlobally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getExists() + getGlobally() + subformulae.get(0);
    }

    protected String inspect(ExistsNext item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getExists() + getNext() + subformulae.get(0);
    }

    protected String inspect(ExistsRelease item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getExists() + "(" + subformulae.get(0) + getRelease() + subformulae.get(1) + ")";
    }

    protected String inspect(ExistsUntil item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getExists() + "(" + subformulae.get(0) + getUntil() + subformulae.get(1) + ")";
    }

    protected String inspect(ExistsWeakUntil item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return getExists() + "(" + subformulae.get(0) + getWeakUntil() + subformulae.get(1) + ")";
    }

    // LTL Specific Operators
    // ==========================================================================================

    protected String inspect(Finally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getFinally() + subformulae.get(0);
    }

    protected String inspect(Globally item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getGlobally() + subformulae.get(0);
    }

    protected String inspect(Next item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return getNext() + subformulae.get(0);
    }

    protected String inspect(Release item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return "(" + subformulae.get(0) + getRelease() + subformulae.get(1) + ")";
    }

    protected String inspect(Until item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return "(" + subformulae.get(0) + getUntil() + subformulae.get(1) + ")";
    }

    protected String inspect(WeakUntil item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return "(" + subformulae.get(0) + getWeakUntil() + subformulae.get(1) + ")";
    }

    // Propositional Logic Operators
    // ===================================================================================

    protected String inspect(Conjunction item, List<String> subformulae) {

        // conjunction symbol added between all formulae in subformulae:
        String textFormula = String.join(getConjunction(), subformulae);

        textFormula = "(" + textFormula + ")";
        return textFormula;
    }

    protected String inspect(Disjunction item, List<String> subformulae) {
        String textFormula = String.join(getDisjunction(), subformulae);
        textFormula = "(" + textFormula + ")";
        return textFormula;
    }

    protected String inspect(Equivalence item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return "(" + subformulae.get(0) + getEquivalence() + subformulae.get(1) + ")";
    }

    protected String inspect(Implication item, List<String> subformulae) {
        if (subformulae.size() != 2) throw new IllegalStateException("Operand count != 2");
        return "(" + subformulae.get(0) + getImplication() + subformulae.get(1) + ")";
    }

    protected String inspect(Negation item, List<String> subformulae) {
        if (subformulae.size() != 1) throw new IllegalStateException("Operand count != 1");
        return "(" + getNegation() + subformulae.get(0) + ")";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // main inspect method takes CtlStarFormula item and calls the inspect method corresponding to
    // item type
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String inspect(CtlStarFormula item, List<String> subformulae) {
        if (item.isFalse()) return this.inspect((False) item, subformulae);
        else if (item.isTrue()) return this.inspect((True) item, subformulae);
        else if (item.isProposition()) return this.inspect((Proposition) item, subformulae);
        else if (item.isAllFinally()) return this.inspect((AllFinally) item, subformulae);
        else if (item.isAllGlobally()) return this.inspect((AllGlobally) item, subformulae);
        else if (item.isAllNext()) return this.inspect((AllNext) item, subformulae);
        else if (item.isAllRelease()) return this.inspect((AllRelease) item, subformulae);
        else if (item.isAllUntil()) return this.inspect((AllUntil) item, subformulae);
        else if (item.isAllWeakUntil()) return this.inspect((AllWeakUntil) item, subformulae);
        else if (item.isExistsFinally()) return this.inspect((ExistsFinally) item, subformulae);
        else if (item.isExistsGlobally()) return this.inspect((ExistsGlobally) item, subformulae);
        else if (item.isExistsNext()) return this.inspect((ExistsNext) item, subformulae);
        else if (item.isExistsRelease()) return this.inspect((ExistsRelease) item, subformulae);
        else if (item.isExistsUntil()) return this.inspect((ExistsUntil) item, subformulae);
        else if (item.isExistsWeakUntil()) return this.inspect((ExistsWeakUntil) item, subformulae);
        else if (item.isFinally()) return this.inspect((Finally) item, subformulae);
        else if (item.isGlobally()) return this.inspect((Globally) item, subformulae);
        else if (item.isNext()) return this.inspect((Next) item, subformulae);
        else if (item.isRelease()) return this.inspect((Release) item, subformulae);
        else if (item.isUntil()) return this.inspect((Until) item, subformulae);
        else if (item.isWeakUntil()) return this.inspect((WeakUntil) item, subformulae);
        else if (item.isConjunction()) return this.inspect((Conjunction) item, subformulae);
        else if (item.isDisjunction()) return this.inspect((Disjunction) item, subformulae);
        else if (item.isEquivalence()) return this.inspect((Equivalence) item, subformulae);
        else if (item.isImplication()) return this.inspect((Implication) item, subformulae);
        else if (item.isNegation()) return this.inspect((Negation) item, subformulae);
        else throw new RuntimeException("Unknown operator or atom of CTL Star");
    }

    @Override
    public String write(CtlStarFormula object) {
        return object.traverse(this);
    }

    /**
     * @return input formula-string without surrounding "(" and ")"
     */
    private String chop(String output) {
        if (output.startsWith("(") && output.endsWith(")"))
            return output.substring(1, output.length() - 1);
        return output;
    }

    /**
     * @return string representation of a CTL Star formula object without unnecessary surrounding
     *     "(" and ")"
     */
    public String writeAndChop(CtlStarFormula object) {
        return chop(write(object));
    }
}
