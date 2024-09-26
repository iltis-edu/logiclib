package de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.pattern.*;
import de.tudortmund.cs.iltis.utils.tree.TraversalStrategy;
import de.tudortmund.cs.iltis.utils.tree.pattern.AlternativePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.RepeatForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.List;

/**
 * This traversal strategy returns a string representing the formula pattern using unicode
 * characters for logical symbols. It is basically an extended copy of the output strategy for
 * formulae.
 *
 * <p>NOTE: - There are no linebreaks inserted. - Every implication and equivalence is surrounded by
 * parentheses. - Every non-trivial conjunction and disjunction is surrounded by parentheses. - In
 * particular, unnecessary parentheses at the top of a formula, e.g. in (A∧¬B) remain. These can be
 * removed safely by the @m chop.
 */
public class ModalFormulaPatternWriterText
        implements TraversalStrategy<TreePattern<ModalFormula>, String> {
    public static final String DIAMOND = "\u25C7";
    public static final String BOX = "\u2610";
    public static final String CONJUNCTION = "\u2227";
    public static final String DISJUNCTION = "\u2228";
    public static final String IMPLICATION = "\u2192";
    public static final String EQUIVALENCE = "\u2194";
    public static final String NEGATION = "\u00AC";
    public static final String TRUE = "\u22A4";
    public static final String FALSE = "\u22A5";

    @Override
    public void nextLevel() {}

    @Override
    public void previousLevel() {}

    @Override
    public void nextSibling() {}

    public String inspect(final DiamondPattern item, List<String> subformulae) {
        return DIAMOND + subformulae.get(0);
    }

    public String inspect(final BoxPattern item, List<String> subformulae) {
        return BOX + subformulae.get(0);
    }

    public String inspect(final ConjunctionPattern item, List<String> subformulae) {
        String textFormula = String.join(CONJUNCTION, subformulae);
        if (subformulae.size() > 1) textFormula = "(" + textFormula + ")";
        else textFormula += CONJUNCTION + "...";
        return textFormula;
    }

    public String inspect(final DisjunctionPattern item, List<String> subformulae) {
        String textFormula = String.join(DISJUNCTION, subformulae);
        if (subformulae.size() > 1) textFormula = "(" + textFormula + ")";
        else textFormula += DISJUNCTION + "...";
        return textFormula;
    }

    public String inspect(final ImplicationPattern item, List<String> subformulae) {
        return "(" + subformulae.get(0) + IMPLICATION + subformulae.get(1) + ")";
    }

    public String inspect(final EquivalencePattern item, List<String> subformulae) {
        return "(" + subformulae.get(0) + EQUIVALENCE + subformulae.get(1) + ")";
    }

    public String inspect(final NegationPattern item, List<String> subformulae) {
        return NEGATION + subformulae.get(0);
    }

    public String inspect(final VariablePattern item, List<String> subformulae) {
        if (item.isNamed()) return "'" + item.getName() + "'";
        else return "_";
    }

    public String inspect(final TruePattern item, List<String> subformulae) {
        return TRUE;
    }

    public String inspect(final FalsePattern item, List<String> subformulae) {
        return FALSE;
    }

    public String inspect(final AnyPattern<ModalFormula> item, List<String> subformulae) {
        return "$" + item.getName();
    }

    public String inspect(final AlternativePattern<ModalFormula> item, List<String> subformulae) {
        return String.join("/", subformulae);
    }

    public String inspect(final RepeatForestPattern<ModalFormula> item, List<String> subformulae) {
        String subtext = subformulae.isEmpty() ? "" : subformulae.get(0);
        return "*" + item.getName() + subtext;
    }

    @Override
    public String inspect(final TreePattern<ModalFormula> item, List<String> subformulae) {
        if (item instanceof DiamondPattern) return this.inspect((DiamondPattern) item, subformulae);
        else if (item instanceof BoxPattern) return this.inspect((BoxPattern) item, subformulae);
        else if (item instanceof ConjunctionPattern)
            return this.inspect((ConjunctionPattern) item, subformulae);
        else if (item instanceof DisjunctionPattern)
            return this.inspect((DisjunctionPattern) item, subformulae);
        else if (item instanceof ImplicationPattern)
            return this.inspect((ImplicationPattern) item, subformulae);
        else if (item instanceof EquivalencePattern)
            return this.inspect((EquivalencePattern) item, subformulae);
        else if (item instanceof NegationPattern)
            return this.inspect((NegationPattern) item, subformulae);
        else if (item instanceof VariablePattern)
            return this.inspect((VariablePattern) item, subformulae);
        else if (item instanceof TruePattern) return this.inspect((TruePattern) item, subformulae);
        else if (item instanceof FalsePattern)
            return this.inspect((FalsePattern) item, subformulae);
        else if (item instanceof AnyPattern)
            return this.inspect((AnyPattern<ModalFormula>) item, subformulae);
        else if (item instanceof AlternativePattern)
            return this.inspect((AlternativePattern<ModalFormula>) item, subformulae);
        else if (item instanceof RepeatForestPattern)
            return this.inspect((RepeatForestPattern<ModalFormula>) item, subformulae);
        else throw new RuntimeException("Unexpected subtype of 'ModalFormula'.");
    }

    public String chop(final String output) {
        if (output.startsWith("(") && output.endsWith(")"))
            return output.substring(1, output.length() - 1);
        return output;
    }
}
