package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Negation;
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
 * Matches any modal logical formula which is a {@link Negation} on the outer level.
 *
 * @see TreePattern
 */
public class NegationPattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private NegationPattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isNegation());
    }

    public NegationPattern(@NotNull final TreePattern<ModalFormula> subPattern) {
        this(new ChildrenPattern<>(subPattern));
    }

    public NegationPattern(@NotNull final ChildrenPattern<ModalFormula> childrenPattern) {
        super(childrenPattern, createPredicatePattern());
    }

    public NegationPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public NegationPattern(
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
                Optional<ModalFormula> optChild =
                        childrenPattern.getChild(0).createTreeIfPossible(match);

                if (optChild.isPresent()) {
                    return new Negation(optChild.get());
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
        return ModalPatternType.NegationPattern;
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
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
