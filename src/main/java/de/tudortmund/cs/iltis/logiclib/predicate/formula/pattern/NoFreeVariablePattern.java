package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.List;

/**
 * This patterns limits matches of a subpattern to those which do not have a free instance of a
 * specific Variable in any specified matched subtree. This pattern is NOT intended to be used for
 * actual tree creation but rather produces the match used for tree creation by other patterns.
 */
public class NoFreeVariablePattern extends VariableBoundingPattern {

    // serialization
    public NoFreeVariablePattern() {}

    @Override
    public PatternType getType() {
        return FOPatternType.NoFreeVariablePattern;
    }

    public NoFreeVariablePattern(
            TreePattern<TermOrFormula> pattern,
            List<IndexedSymbol> treesToBeChecked,
            IndexedSymbol freeVariableId) {
        super(pattern, treesToBeChecked, freeVariableId);
    }

    @Override
    public NoFreeVariablePattern clone() {
        return new NoFreeVariablePattern(pattern.clone(), treesToBeChecked, freeVariableId.clone());
    }

    @Override
    public NoFreeVariablePattern cloneWithIteratedName(int iteration) {
        return new NoFreeVariablePattern(pattern.clone(), treesToBeChecked, freeVariableId.clone());
    }

    @Override
    protected boolean checksForFreeVariable() {
        return false;
    }

    @Override
    public String toString() {
        return "NoFreeVariablePattern [subpattern="
                + pattern
                + ", treesToBeChecked="
                + treesToBeChecked
                + ", freeVariableId="
                + freeVariableId
                + "]";
    }
}
