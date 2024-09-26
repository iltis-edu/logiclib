package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
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
 * Matches any FO formula which is a {@link Negation} on the outer level.
 *
 * @see TreePattern
 */
public class NegationPattern extends MultiConstraintPattern<TermOrFormula> {

    // needed for serialization
    private NegationPattern() {}

    private static PredicatePattern<TermOrFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isNegation());
    }

    public NegationPattern(@NotNull final TreePattern<TermOrFormula> subPattern) {
        this(new ChildrenPattern<>(subPattern));
    }

    public NegationPattern(@NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {
        super(childrenPattern, createPredicatePattern());
    }

    public NegationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public NegationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(name, eqTester, childrenPattern, createPredicatePattern());
    }

    @Override
    public TermOrFormula createTree(@NotNull final Match<TermOrFormula> match) {
        try {
            // try to use this patterns name
            return createTreeByMatchAndName(match);
        } catch (CreateException e) {
            // try to use the children pattern
            TreePattern<TermOrFormula> childrenPattern = this.getChild(0);

            Optional<TermOrFormula> optTree = childrenPattern.createTreeIfPossible(match);

            if (optTree.isPresent()) {
                return optTree.get();
            } else {
                // try to create children and construct tree with them
                Optional<TermOrFormula> optChild =
                        childrenPattern.getChild(0).createTreeIfPossible(match);

                if (optChild.isPresent()) {
                    return new Negation((Formula) optChild.get());
                }
            }
        }

        throw new CreateException(this, match);
    }

    @Override
    public List<TermOrFormula> createForest(@NotNull final Match<TermOrFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return FOPatternType.NegationPattern;
    }

    @Override
    public String toString() {
        return "NegationPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public NegationPattern clone() {
        return new NegationPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public NegationPattern cloneWithIteratedName(int iteration) {

        return new NegationPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private NegationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<TermOrFormula>> children) {

        super(name, eqTester, children);
    }
}
