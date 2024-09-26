package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.CreateException;
import de.tudortmund.cs.iltis.utils.tree.pattern.FixedArityForestPattern;
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
 * Matches any FO formula which is a {@link Implication} on the outer level.
 *
 * @see TreePattern
 */
public class ImplicationPattern extends MultiConstraintPattern<TermOrFormula> {

    // needed for serialization
    private ImplicationPattern() {}

    private static PredicatePattern<TermOrFormula> createPredicatePattern() {

        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0).isImplication());
    }

    public ImplicationPattern(
            @NotNull final TreePattern<TermOrFormula> first,
            @NotNull final TreePattern<TermOrFormula> second) {

        this(new ChildrenPattern<>(new FixedArityForestPattern<>(first, second)));
    }

    public ImplicationPattern(@NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public ImplicationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public ImplicationPattern(
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
                Optional<List<TermOrFormula>> optForest =
                        childrenPattern.getChild(0).createForestIfPossible(match);

                if (optForest.isPresent()) {
                    List<TermOrFormula> forest = optForest.get();

                    return new Implication((Formula) forest.get(0), (Formula) forest.get(1));
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
        return FOPatternType.ImplicationPattern;
    }

    @Override
    public String toString() {
        return "ImplicationPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public ImplicationPattern clone() {
        return new ImplicationPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public ImplicationPattern cloneWithIteratedName(int iteration) {

        return new ImplicationPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private ImplicationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<TermOrFormula>> children) {

        super(name, eqTester, children);
    }
}
