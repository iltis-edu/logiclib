package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.CreateException;
import de.tudortmund.cs.iltis.utils.tree.pattern.FlexibleArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.MultiConstraintPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

/**
 * Matches any modal logical formula which is a {@link Conjunction} on the outer level.
 *
 * @see TreePattern
 */
public class ConjunctionPattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private ConjunctionPattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0).isConjunction());
    }

    public ConjunctionPattern(@NotNull final TreePattern<ModalFormula>... patterns) {
        this(new ChildrenPattern<>(new FlexibleArityForestPattern<>(patterns)));
    }

    public ConjunctionPattern(@NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public ConjunctionPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public ConjunctionPattern(
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
                TreePattern<ModalFormula> forestPattern = childrenPattern.getChild(0);

                // possible RepeatForestPattern occurrence needs to be handled
                // differently to provide a successful reconstruction if possible
                if (forestPattern instanceof FlexibleArityForestPattern) {
                    List<ModalFormula> subformulae = new ArrayList<>();

                    for (TreePattern<ModalFormula> childPattern : forestPattern.getChildren()) {

                        Optional<List<ModalFormula>> optSubformulae =
                                childPattern.createForestIfPossible(match);

                        if (optSubformulae.isPresent()) {
                            subformulae.addAll(optSubformulae.get());
                        } else {
                            throw new CreateException(this, match);
                        }
                    }

                    return new Conjunction(subformulae);
                }

                Optional<List<ModalFormula>> optForest =
                        forestPattern.createForestIfPossible(match);

                if (optForest.isPresent()) {
                    List<ModalFormula> children = new ArrayList<>();

                    for (ModalFormula formula : optForest.get()) {
                        children.add(formula);
                    }

                    return new Conjunction(children);
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
        return ModalPatternType.ConjunctionPattern;
    }

    @Override
    public String toString() {
        return "ConjunctionPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public ConjunctionPattern clone() {
        return new ConjunctionPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public ConjunctionPattern cloneWithIteratedName(int iteration) {

        return new ConjunctionPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private ConjunctionPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
