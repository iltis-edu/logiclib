package de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Box;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Diamond;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import java.util.List;

/**
 * This traversal strategy returns a string representing the formula using unicode characters for
 * logical symbols.
 *
 * <p>NOTE: - There are no line breaks inserted. - Every implication and equivalence is surrounded
 * by parentheses. - Every non-trivial conjunction and disjunction is surrounded by parentheses. -
 * In particular, unnecessary parentheses at the top of a formula, e.g. in (A∧¬B) remain. These can
 * be removed safely by calling {@link #chop(String)} <b>once</b>.
 */
public class ModalFormulaWriterText implements TraversalStrategy<ModalFormula, String> {

    public String getDiamond() {
        return "\u25C7";
    }

    public String getBox() {
        return "\u2610";
    }

    public String getConjunction() {
        return "\u2227";
    }

    public String getDisjunction() {
        return "\u2228";
    }

    public String getImplication() {
        return "\u2192";
    }

    public String getEquivalence() {
        return "\u2194";
    }

    public String getNegation() {
        return "\u00AC";
    }

    public String getTrue() {
        return "\u22A4";
    }

    public String getFalse() {
        return "\u22A5";
    }

    public ModalFormulaWriterText() {}

    @Override
    public void nextLevel() {}

    @Override
    public void previousLevel() {}

    @Override
    public void nextSibling() {}

    public String inspect(final Diamond item, List<String> subformulae) {
        return getDiamond() + subformulae.get(0);
    }

    public String inspect(final Box item, List<String> subformulae) {
        return getBox() + subformulae.get(0);
    }

    public String inspect(final Conjunction item, List<String> subformulae) {
        String textFormula = String.join(getConjunction(), subformulae);
        if (subformulae.size() > 1) textFormula = "(" + textFormula + ")";
        return textFormula;
    }

    public String inspect(final Disjunction item, List<String> subformulae) {
        String textFormula = String.join(getDisjunction(), subformulae);
        if (subformulae.size() > 1) textFormula = "(" + textFormula + ")";
        return textFormula;
    }

    public String inspect(final Implication item, List<String> subformulae) {
        return "(" + subformulae.get(0) + getImplication() + subformulae.get(1) + ")";
    }

    public String inspect(final Equivalence item, List<String> subformulae) {
        return "(" + subformulae.get(0) + getEquivalence() + subformulae.get(1) + ")";
    }

    public String inspect(final Negation item, List<String> subformulae) {
        return getNegation() + subformulae.get(0);
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

    @Override
    public String inspect(final ModalFormula item, List<String> subformulae) {
        if (item instanceof Diamond) return this.inspect((Diamond) item, subformulae);
        else if (item instanceof Box) return this.inspect((Box) item, subformulae);
        else if (item instanceof Conjunction) return this.inspect((Conjunction) item, subformulae);
        else if (item instanceof Disjunction) return this.inspect((Disjunction) item, subformulae);
        else if (item instanceof Implication) return this.inspect((Implication) item, subformulae);
        else if (item instanceof Equivalence) return this.inspect((Equivalence) item, subformulae);
        else if (item instanceof Negation) return this.inspect((Negation) item, subformulae);
        else if (item instanceof Variable) return this.inspect((Variable) item, subformulae);
        else if (item instanceof True) return this.inspect((True) item, subformulae);
        else if (item instanceof False) return this.inspect((False) item, subformulae);
        else throw new RuntimeException("Unexpected subtype of 'ModalFormula'.");
    }

    public String chop(final String output) {
        if (output.startsWith("(") && output.endsWith(")"))
            return output.substring(1, output.length() - 1);
        return output;
    }
}
