package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.False;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * Matches the modal logical formula {@link False}.
 *
 * @see TreePattern
 */
public class FalsePattern extends PredicatePattern<ModalFormula> {

    private static SerializablePredicate<List<ModalFormula>> PREDICATE =
            forest -> forest.size() == 1 && forest.get(0).isFalse();

    public FalsePattern() {
        super(PREDICATE);
    }

    public FalsePattern(@NotNull final IndexedSymbol name) {
        super(name, PREDICATE);
    }

    public FalsePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester) {

        super(name, eqTester, PREDICATE);
    }

    @Override
    public ModalFormula createTree(@NotNull final Match<ModalFormula> match) {
        return new False();
    }

    @Override
    public List<ModalFormula> createForest(@NotNull final Match<ModalFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return ModalPatternType.FalsePattern;
    }

    @Override
    public String toString() {
        return "FalsePattern [id=" + name + "]";
    }

    @Override
    public FalsePattern clone() {
        return new FalsePattern(name, eqTester);
    }

    @Override
    public FalsePattern cloneWithIteratedName(int iteration) {
        return new FalsePattern(iterateName(name, iteration), eqTester);
    }
}
