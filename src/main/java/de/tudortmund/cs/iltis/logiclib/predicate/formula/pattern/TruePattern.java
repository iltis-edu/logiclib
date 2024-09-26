package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
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
 * Matches the FO formula {@link True}.
 *
 * @see TreePattern
 */
public class TruePattern extends PredicatePattern<TermOrFormula> {

    private static SerializablePredicate<List<TermOrFormula>> PREDICATE =
            forest -> forest.size() == 1 && forest.get(0).isTrue();

    public TruePattern() {
        super(PREDICATE);
    }

    public TruePattern(@NotNull final IndexedSymbol name) {
        super(name, PREDICATE);
    }

    public TruePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester) {

        super(name, eqTester, PREDICATE);
    }

    @Override
    public TermOrFormula createTree(@NotNull final Match<TermOrFormula> match) {
        return new True();
    }

    @Override
    public List<TermOrFormula> createForest(@NotNull final Match<TermOrFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return FOPatternType.TruePattern;
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
