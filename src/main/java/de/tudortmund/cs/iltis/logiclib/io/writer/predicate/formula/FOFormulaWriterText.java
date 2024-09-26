package de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.ExistentialQuantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Quantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.UniversalQuantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.writer.general.TextIndexedSymbolWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * This traversal strategy returns a string representing the formula using unicode characters for
 * logical symbols by its {@link #write(TermOrFormula)} method.
 *
 * <p>NOTE:
 *
 * <ul>
 *   <li>There are no line breaks inserted.
 *   <li>Every implication and equivalence is surrounded by parentheses.
 *   <li>Every non-trivial conjunction and disjunction is surrounded by parentheses.
 *   <li>In particular, unnecessary parentheses at the top of a formula, e.g. in (A∧¬B) remain.
 *       These can be omitted by using {@link #writeAndChop(TermOrFormula)}.
 * </ul>
 */
public class FOFormulaWriterText
        implements TraversalStrategy<TermOrFormula, String>, Writer<TermOrFormula> {

    // Attribute

    protected Writer<IndexedSymbol> symbolWriter;

    // Fixed symbols

    protected String getExistentialQuantifier() {
        return "\u2203";
    }

    protected String getUniversalQuantifier() {
        return "\u2200";
    }

    protected String getQuantifierSeparator() {
        return " ";
    }

    protected String getConjunction() {
        return "\u2227";
    }

    protected String getDisjunction() {
        return "\u2228";
    }

    protected String getImplication() {
        return "\u2192";
    }

    protected String getEquivalence() {
        return "\u2194";
    }

    protected String getNegation() {
        return "\u00AC";
    }

    protected String getTrue() {
        return "\u22A4";
    }

    protected String getFalse() {
        return "\u22A5";
    }

    protected String getRelationArgumentSeparator() {
        return ",";
    }

    protected String getSurroundRelationBegin() {
        return "(";
    }

    protected String getSurroundRelationEnd() {
        return ")";
    }

    protected String getFunctionArgumentSeparator() {
        return ",";
    }

    protected String getSurroundFunctionBegin() {
        return "(";
    }

    protected String getSurroundFunctionEnd() {
        return ")";
    }

    protected String getSurroundSubformulaBegin() {
        return "(";
    }

    protected String getSurroundSubformulaEnd() {
        return ")";
    }

    // Constructor

    public FOFormulaWriterText() {
        symbolWriter = new TextIndexedSymbolWriter();
    }

    // Text creation -- Formulas

    protected String inspect(True item, List<String> subformulae) {
        return getTrue();
    }

    protected String inspect(False item, List<String> subformulae) {
        return getFalse();
    }

    protected String inspect(RelationAtom item, List<String> subformulae) {
        RelationSymbol symbol = item.getName();
        String symbolText = symbolWriter.write(symbol);
        StringBuilder sb = new StringBuilder();
        if (item.isInfix()) {
            if (subformulae.size() != 2)
                throw new IllegalStateException("Infix operator with operand count =/= 2");

            sb.append(getSurroundSubformulaBegin());
            sb.append(subformulae.get(0));
            sb.append(symbolText);
            sb.append(subformulae.get(1));
            sb.append(getSurroundSubformulaEnd());
        } else {
            sb.append(symbolText);
            if (subformulae.size() > 0) {
                sb.append(getSurroundRelationBegin());
                sb.append(String.join(getRelationArgumentSeparator(), subformulae));
                sb.append(getSurroundRelationEnd());
            }
        }
        return sb.toString();
    }

    // Text creation -- Quantifier

    protected String inspect(ExistentialQuantifier item, List<String> subformulae) {
        return inspect(item, subformulae, "ExistentialQuantifier", getExistentialQuantifier());
    }

    protected String inspect(UniversalQuantifier item, List<String> subformulae) {
        return inspect(item, subformulae, "UniversalQuantifier", getUniversalQuantifier());
    }

    protected String inspect(
            Quantifier item, List<String> subformulae, String type, String symbol) {
        // 2 operands because the variable being quantified also counts as a subformula.
        if (subformulae.size() != 2)
            throw new IllegalStateException(type + " with operand count =/= 2");

        String varText = inspect(item.getVariable(), new ArrayList<>());

        return symbol + varText + getQuantifierSeparator() + subformulae.get(1);
    }

    // Text creation -- Boolean connectors

    protected String inspect(Conjunction item, List<String> subformulae) {
        String textFormula = String.join(getConjunction(), subformulae);
        if (subformulae.size() > 1)
            textFormula = getSurroundSubformulaBegin() + textFormula + getSurroundSubformulaEnd();
        return textFormula;
    }

    protected String inspect(Disjunction item, List<String> subformulae) {
        String textFormula = String.join(getDisjunction(), subformulae);
        if (subformulae.size() > 1)
            textFormula = getSurroundSubformulaBegin() + textFormula + getSurroundSubformulaEnd();
        return textFormula;
    }

    protected String inspect(Implication item, List<String> subformulae) {
        if (subformulae.size() != 2)
            throw new IllegalStateException("Implication with operand count =/= 2");

        String textFormula = subformulae.get(0) + getImplication() + subformulae.get(1);
        return getSurroundSubformulaBegin() + textFormula + getSurroundSubformulaEnd();
    }

    protected String inspect(Equivalence item, List<String> subformulae) {
        if (subformulae.size() != 2)
            throw new IllegalStateException("Equivalence with operand count =/= 2");

        String textFormula = subformulae.get(0) + getEquivalence() + subformulae.get(1);
        return getSurroundSubformulaBegin() + textFormula + getSurroundSubformulaEnd();
    }

    protected String inspect(Negation item, List<String> subformulae) {
        if (subformulae.size() != 1)
            throw new IllegalStateException("Negation with operand count =/= 1");

        return getNegation() + subformulae.get(0);
    }

    // Text creation -- Terms

    protected String inspect(Variable item, List<String> subformulae) {
        if (subformulae.size() != 0) throw new IllegalStateException("Variable with subformulae");

        IndexedSymbol symbol = item.getName();
        return symbolWriter.write(symbol);
    }

    protected String inspect(FunctionTerm item, List<String> subformulae) {
        FunctionSymbol symbol = item.getName();
        String symbolText = symbolWriter.write(symbol);
        StringBuilder sb = new StringBuilder();
        if (item.isInfix()) {
            if (subformulae.size() != 2)
                throw new IllegalStateException("Infix operator with operand count =/= 2");

            sb.append(getSurroundSubformulaBegin());
            sb.append(subformulae.get(0));
            sb.append(symbolText);
            sb.append(subformulae.get(1));
            sb.append(getSurroundSubformulaEnd());
        } else {
            sb.append(symbolText);
            if (subformulae.size() > 0) {
                sb.append(getSurroundFunctionBegin());
                sb.append(String.join(getFunctionArgumentSeparator(), subformulae));
                sb.append(getSurroundFunctionEnd());
            }
        }
        return sb.toString();
    }

    // Public interface

    @Override
    public String inspect(TermOrFormula item, List<String> subformulae) {
        if (item.isTrue()) return this.inspect((True) item, subformulae);
        else if (item.isFalse()) return this.inspect((False) item, subformulae);
        else if (item.isRelation()) return this.inspect((RelationAtom) item, subformulae);
        else if (item.isExistentialQuantifier())
            return this.inspect((ExistentialQuantifier) item, subformulae);
        else if (item.isUniversalQuantifier())
            return this.inspect((UniversalQuantifier) item, subformulae);
        else if (item.isConjunction()) return this.inspect((Conjunction) item, subformulae);
        else if (item.isDisjunction()) return this.inspect((Disjunction) item, subformulae);
        else if (item.isImplication()) return this.inspect((Implication) item, subformulae);
        else if (item.isEquivalence()) return this.inspect((Equivalence) item, subformulae);
        else if (item.isNegation()) return this.inspect((Negation) item, subformulae);
        else if (item.isVariable()) return this.inspect((Variable) item, subformulae);
        else if (item.isFunction()) return this.inspect((FunctionTerm) item, subformulae);
        else {
            throw new RuntimeException(
                    "Literally Unreachable. Above if-else-chain exhaustively matches AST classes for FOFormulae");
        }
    }

    @Override
    public String write(TermOrFormula object) {
        return object.traverse(this);
    }

    public String writeAndChop(TermOrFormula object) {
        return chop(write(object));
    }

    private String chop(String output) {
        if (output.startsWith(getSurroundSubformulaBegin())
                && output.endsWith(getSurroundSubformulaEnd()))
            return output.substring(1, output.length() - 1);
        return output;
    }
}
