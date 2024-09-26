package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Diamond;
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
 * Matches any modal logical formula which has an {@link Diamond} on the outer level.
 *
 * @see TreePattern
 */
@SuppressWarnings("serial")
public class DiamondPattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private DiamondPattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isDiamond());
    }

    public DiamondPattern(@NotNull final TreePattern<ModalFormula> subPattern) {

        this(new ChildrenPattern<>(new FixedArityForestPattern<>(subPattern)));
    }

    public DiamondPattern(@NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public DiamondPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public DiamondPattern(
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

                    return new Diamond(forest.get(0));
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
        return ModalPatternType.DiamondPattern;
    }

    @Override
    public String toString() {
        return "DiamondPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public DiamondPattern clone() {
        return new DiamondPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public DiamondPattern cloneWithIteratedName(int iteration) {

        return new DiamondPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private DiamondPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
