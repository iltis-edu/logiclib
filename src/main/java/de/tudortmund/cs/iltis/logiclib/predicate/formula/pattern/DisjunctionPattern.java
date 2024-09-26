package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
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
 * Matches any FO formula which is a {@link Disjunction} on the outer level.
 *
 * @see TreePattern
 */
public class DisjunctionPattern extends MultiConstraintPattern<TermOrFormula> {

    // needed for serialization
    private DisjunctionPattern() {}

    private static PredicatePattern<TermOrFormula> createPredicatePattern() {

        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0).isDisjunction());
    }

    public DisjunctionPattern(@NotNull final TreePattern<TermOrFormula>... patterns) {
        this(new ChildrenPattern<>(new FlexibleArityForestPattern(patterns)));
    }

    public DisjunctionPattern(@NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(childrenPattern, createPredicatePattern());
    }

    public DisjunctionPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(name, childrenPattern, createPredicatePattern());
    }

    public DisjunctionPattern(
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
                TreePattern<TermOrFormula> forestPattern = childrenPattern.getChild(0);

                // possible RepeatForestPattern occurrence needs to be handled
                // differently to provide a successful reconstruction if possible
                if (forestPattern instanceof FlexibleArityForestPattern) {
                    List<Formula> subformulae = new ArrayList<>();

                    for (TreePattern<TermOrFormula> childPattern : forestPattern.getChildren()) {

                        Optional<List<TermOrFormula>> optSubformulae =
                                childPattern.createForestIfPossible(match);

                        if (optSubformulae.isPresent()) {
                            for (TermOrFormula subformula : optSubformulae.get()) {
                                subformulae.add((Formula) subformula);
                            }
                        } else {
                            throw new CreateException(this, match);
                        }
                    }

                    return new Disjunction(subformulae);
                }

                Optional<List<TermOrFormula>> optForest =
                        forestPattern.createForestIfPossible(match);

                if (optForest.isPresent()) {
                    List<Formula> children = new ArrayList<>();

                    for (TermOrFormula formula : optForest.get()) {
                        children.add((Formula) formula);
                    }

                    return new Disjunction(children);
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
        return FOPatternType.DisjunctionPattern;
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
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<TermOrFormula>> children) {

        super(name, eqTester, children);
    }
}
