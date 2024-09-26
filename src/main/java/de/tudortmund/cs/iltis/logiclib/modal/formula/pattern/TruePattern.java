package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.True;
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
 * Matches the modal logical formula {@link True}.
 *
 * @see TreePattern
 */
public class TruePattern extends PredicatePattern<ModalFormula> {

    private static SerializablePredicate<List<ModalFormula>> PREDICATE =
            forest -> forest.size() == 1 && forest.get(0).isTrue();

    public TruePattern() {
        super(PREDICATE);
    }

    public TruePattern(@NotNull final IndexedSymbol name) {
        super(name, PREDICATE);
    }

    public TruePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester) {

        super(name, eqTester, PREDICATE);
    }

    @Override
    public ModalFormula createTree(@NotNull final Match<ModalFormula> match) {
        return new True();
    }

    @Override
    public List<ModalFormula> createForest(@NotNull final Match<ModalFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return ModalPatternType.TruePattern;
    }

    @Override
    public String toString() {
        return "TruePattern [id=" + name + "]";
    }

    @Override
    public TruePattern clone() {
        return new TruePattern(name, eqTester);
    }

    @Override
    public TruePattern cloneWithIteratedName(int iteration) {
        return new TruePattern(iterateName(name, iteration), eqTester);
    }
}
