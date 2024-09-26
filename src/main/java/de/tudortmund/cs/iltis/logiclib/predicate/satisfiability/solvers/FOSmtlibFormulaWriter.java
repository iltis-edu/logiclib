package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterText;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.utils.io.writer.general.LatexIndexedSymbolWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes a formula in the infix form which is useable for the smtlib format. This way it can be
 * used in an "asser" block. Note that this does NOT produce the necessary definitions (functions,
 * constants, etc.) for the smtlib format.
 */
public class FOSmtlibFormulaWriter extends FOFormulaWriterText {

    protected String getExistentialQuantifier() {
        return "exists";
    }

    protected String getUniversalQuantifier() {
        return "forall";
    }

    protected String getQuantifierSeparator() {
        return " ";
    }

    protected String getConjunction() {
        return "and";
    }

    protected String getDisjunction() {
        return "or";
    }

    protected String getImplication() {
        return "=>";
    }

    protected String getEquivalence() {
        return "=";
    }

    protected String getNegation() {
        return "not";
    }

    protected String getTrue() {
        return "true";
    }

    protected String getFalse() {
        return "false";
    }

    protected String inspect(RelationAtom item, List<String> subformulae) {
        RelationSymbol symbol = item.getName();
        String symbolText = symbolWriter.write(symbol);
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(symbolText);

        if (subformulae.size() > 0) {
            sb.append(" ");
            sb.append(String.join(" ", subformulae));
        }

        sb.append(")");

        return sb.toString();
    }

    protected String inspect(FunctionTerm item, List<String> subformulae) {
        FunctionSymbol symbol = item.getName();
        String symbolText = symbolWriter.write(symbol);

        if (symbol.getArity() == 0) {
            return symbolText;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        sb.append(symbolText);

        if (subformulae.size() > 0) {
            sb.append(" ");
            sb.append(String.join(" ", subformulae));
        }

        sb.append(")");

        return sb.toString();
    }

    protected String inspect(Negation item, List<String> subformulae) {
        if (subformulae.size() != 1)
            throw new IllegalStateException("Negation with operand count =/= 1");

        return "(" + getNegation() + " " + subformulae.get(0) + ")";
    }

    protected String inspect(Equivalence item, List<String> subformulae) {
        if (subformulae.size() != 2)
            throw new IllegalStateException("Equivalence with operand count =/= 2");

        return "(" + getEquivalence() + " " + subformulae.get(0) + " " + subformulae.get(1) + ")";
    }

    protected String inspect(Implication item, List<String> subformulae) {
        if (subformulae.size() != 2)
            throw new IllegalStateException("Implication with operand count =/= 2");

        return "(" + getImplication() + " " + subformulae.get(0) + " " + subformulae.get(1) + ")";
    }

    protected String inspect(Disjunction item, List<String> subformulae) {
        return "(" + getDisjunction() + " " + String.join(" ", subformulae) + ")";
    }

    protected String inspect(Conjunction item, List<String> subformulae) {
        return "(" + getConjunction() + " " + String.join(" ", subformulae) + ")";
    }

    protected String inspect(
            Quantifier item, List<String> subformulae, String type, String symbol) {
        // 2 operands because the variable being quantified also counts as a subformula.
        if (subformulae.size() != 2)
            throw new IllegalStateException(type + " with operand count =/= 2");

        String varText = "((" + inspect(item.getVariable(), new ArrayList<>()) + " " + sort + "))";

        return "(" + symbol + " " + varText + " " + subformulae.get(1) + ")";
    }

    private String sort;

    public FOSmtlibFormulaWriter() {
        this("CustomSort");
    }

    public FOSmtlibFormulaWriter(String sort) {
        this.sort = sort;
        symbolWriter = new LatexIndexedSymbolWriter();
    }
}
