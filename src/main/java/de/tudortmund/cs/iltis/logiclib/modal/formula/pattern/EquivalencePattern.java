package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
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
 * Matches any modal logical formula which is a {@link Equivalence} on the outer level.
 *
 * @see TreePattern
 */
public class EquivalencePattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private EquivalencePattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0).isEquivalence());
    }

    public EquivalencePattern(
            @NotNull final TreePattern<ModalFormula> first,
            @NotNull final TreePattern<ModalFormula> second) {

        this(new ChildrenPattern<>(new FixedArityForestPattern<>(first, second)));
    }

    public EquivalencePattern(@NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public EquivalencePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public EquivalencePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, eqTester, childrenPattern, createPredicatePattern());
    }

    @Override
    public ModalFormula createTree(@NotNull final Match<ModalFormula> match) {
        try {
            // try to use this patterns name
            return createTreeByMatchAndName(match);
        } catch (CreateException e) {
            // try to use the children pattern
            TreePattern<ModalFormula> childrenPattern = this.getChild(0);

            Optional<ModalFormula> optTree = childrenPattern.createTreeIfPossible(match);

            if (optTree.isPresent()) {
                return optTree.get();
            } else {
                // try to create children and construct tree with them
                Optional<List<ModalFormula>> optForest =
                        childrenPattern.getChild(0).createForestIfPossible(match);

                if (optForest.isPresent()) {
                    List<ModalFormula> forest = optForest.get();

                    return new Equivalence(forest.get(0), forest.get(1));
                }
            }
        }

        throw new CreateException(this, match);
    }

    @Override
    public List<ModalFormula> createForest(@NotNull final Match<ModalFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return ModalPatternType.EquivalencePattern;
    }

    @Override
    public String toString() {
        return "EquivalencePattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public EquivalencePattern clone() {
        return new EquivalencePattern(name, eqTester, getClonedChildren());
    }

    @Override
    public EquivalencePattern cloneWithIteratedName(int iteration) {

        return new EquivalencePattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private EquivalencePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
