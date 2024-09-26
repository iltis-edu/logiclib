package de.tudortmund.cs.iltis.logiclib.modal.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterText;
import de.tudortmund.cs.iltis.logiclib.modal.formula.*;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import java.util.List;
import java.util.Set;

public class PLSmtlibFormulaWriter extends ModalFormulaWriterText {

    public String getConjunction() {
        return "and";
    }

    public String getDisjunction() {
        return "or";
    }

    public String getImplication() {
        return "=>";
    }

    public String getEquivalence() {
        return "=";
    }

    public String getNegation() {
        return "not";
    }

    public String getTrue() {
        return "true";
    }

    public String getFalse() {
        return "false";
    }

    public String inspect(final Conjunction item, List<String> subformulae) {
        return "(" + getConjunction() + " " + String.join(" ", subformulae) + ")";
    }

    public String inspect(final Disjunction item, List<String> subformulae) {
        return "(" + getDisjunction() + " " + String.join(" ", subformulae) + ")";
    }

    public String inspect(final Implication item, List<String> subformulae) {
        return "(" + getImplication() + " " + subformulae.get(0) + " " + subformulae.get(1) + ")";
    }

    public String inspect(final Equivalence item, List<String> subformulae) {
        return "(" + getEquivalence() + " " + subformulae.get(0) + " " + subformulae.get(1) + ")";
    }

    public String inspect(final Negation item, List<String> subformulae) {
        return "(" + getNegation() + " " + subformulae.get(0) + ")";
    }

    public String inspect(final Variable item, List<String> subformulae) {
        IndexedSymbol symbol = item.getName();
        String text = symbol.getName();
        if (symbol.hasSuperscript()) text += "^" + symbol.getSuperscript();
        if (symbol.hasSubscript()) text += "_" + symbol.getSubscript();
        return text;
    }

    public String inspect(final True item, List<String> subformulae) {
        return getTrue();
    }

    public String inspect(final False item, List<String> subformulae) {
        return getFalse();
    }

    public static String translateFormulaToSmt(ModalFormula formula) {
        PLSmtlibFormulaWriter writer = new PLSmtlibFormulaWriter();
        return writer.getDefinitions(formula) + "(assert " + formula.traverse(writer) + ")";
    }

    private String getDefinitions(ModalFormula formula) {
        Set<Variable> variables = formula.getVariables();

        StringBuilder text = new StringBuilder();
        for (Variable variable : variables) {
            text.append("(declare-const ");
            text.append(inspect(variable, null));
            text.append(" Bool)\\n");
        }

        return text.toString();
    }
}
