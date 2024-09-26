package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.CreateException;
import de.tudortmund.cs.iltis.utils.tree.pattern.MultiConstraintPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

/**
 * Matches any FO formula consisting of only a single {@link Variable}.
 *
 * @see TreePattern
 */
public class VariablePattern extends MultiConstraintPattern<TermOrFormula> {

    // needed for serialization
    private VariablePattern() {}

    private static PredicatePattern<TermOrFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isVariable());
    }

    public VariablePattern(@NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern) {
        super((TreePattern<TermOrFormula>) namePattern, createPredicatePattern());
    }

    public VariablePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern) {

        super(name, (TreePattern<TermOrFormula>) namePattern, createPredicatePattern());
    }

    public VariablePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern) {

        super(name, eqTester, (TreePattern<TermOrFormula>) namePattern, createPredicatePattern());
    }

    @Override
    public TermOrFormula createTree(@NotNull final Match<TermOrFormula> match) {
        try {
            // try to use this patterns name
            return createTreeByMatchAndName(match);
        } catch (CreateException e) {
            // try to use name pattern
            Optional<IndexedSymbol> optName =
                    ((NamePattern<TermOrFormula, IndexedSymbol>) this.getChild(0))
                            .createNameIfPossible(match);

            if (optName.isPresent()) {
                return new Variable(optName.get());
            }

            throw new CreateException(this, match);
        }
    }

    @Override
    public List<TermOrFormula> createForest(@NotNull final Match<TermOrFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return FOPatternType.VariablePattern;
    }

    @Override
    public String toString() {
        return "VariablePattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public VariablePattern clone() {
        return new VariablePattern(name, eqTester, getClonedChildren());
    }

    @Override
    public VariablePattern cloneWithIteratedName(int iteration) {

        return new VariablePattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private VariablePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<TermOrFormula>> children) {

        super(name, eqTester, children);
    }
}
