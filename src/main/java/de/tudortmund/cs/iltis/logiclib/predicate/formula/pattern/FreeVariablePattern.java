package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.List;

/**
 * This patterns limits matches of a subpattern to those which have a free instance of a Variable in
 * any specified matched subtree. This pattern is NOT intended to be used for actual tree creation
 * but rather produces the match used for tree creation by other patterns.
 */
public class FreeVariablePattern extends VariableBoundingPattern {

    // serialization
    public FreeVariablePattern() {}

    @Override
    public PatternType getType() {
        return FOPatternType.FreeVariablePattern;
    }

    public FreeVariablePattern(
            TreePattern<TermOrFormula> pattern,
            List<IndexedSymbol> treesToBeChecked,
            IndexedSymbol freeVariableId) {
        super(pattern, treesToBeChecked, freeVariableId);
    }

    @Override
    public FreeVariablePattern clone() {
        return new FreeVariablePattern(pattern.clone(), treesToBeChecked, freeVariableId.clone());
    }

    @Override
    public FreeVariablePattern cloneWithIteratedName(int iteration) {
        return new FreeVariablePattern(pattern.clone(), treesToBeChecked, freeVariableId.clone());
    }

    @Override
    protected boolean checksForFreeVariable() {
        return true;
    }

    @Override
    public String toString() {
        return "FreeVariablePattern [subpattern="
                + pattern
                + ", treesToBeChecked="
                + treesToBeChecked
                + ", freeVariableId="
                + freeVariableId
                + "]";
    }
}
