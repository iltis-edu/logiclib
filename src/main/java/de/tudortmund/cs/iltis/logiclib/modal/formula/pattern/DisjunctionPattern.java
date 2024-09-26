package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Disjunction;
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
 * Matches any modal logical formula which is a {@link Disjunction} on the outer level.
 *
 * @see TreePattern
 */
public class DisjunctionPattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private DisjunctionPattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0).isDisjunction());
    }

    public DisjunctionPattern(@NotNull final TreePattern<ModalFormula>... patterns) {
        this(new ChildrenPattern<>(new FlexibleArityForestPattern(patterns)));
    }

    public DisjunctionPattern(@NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public DisjunctionPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<ModalFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public DisjunctionPattern(
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

                    return new Disjunction(subformulae);
                }

                Optional<List<ModalFormula>> optForest =
                        childrenPattern.getChild(0).createForestIfPossible(match);

                if (optForest.isPresent()) {
                    List<ModalFormula> children = new ArrayList<>();

                    for (ModalFormula formula : optForest.get()) {
                        children.add(formula);
                    }

                    return new Disjunction(children);
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
        return ModalPatternType.DisjunctionPattern;
    }

    @Override
    public String toString() {
        return "DisjunctionPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public DisjunctionPattern clone() {
        return new DisjunctionPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public DisjunctionPattern cloneWithIteratedName(int iteration) {

        return new DisjunctionPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private DisjunctionPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
