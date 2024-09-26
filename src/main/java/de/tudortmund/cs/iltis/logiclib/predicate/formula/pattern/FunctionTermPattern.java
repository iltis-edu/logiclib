package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
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
 * Matches any FO formula consisting of only a single {@link FunctionTerm}.
 *
 * @see TreePattern
 */
public class FunctionTermPattern extends MultiConstraintPattern<TermOrFormula> {

    // needed for serialization
    private FunctionTermPattern() {}

    private static PredicatePattern<TermOrFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isFunction());
    }

    public FunctionTermPattern(
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern) {

        this(namePattern, new ChildrenPattern<>(new FlexibleArityForestPattern<TermOrFormula>()));
    }

    public FunctionTermPattern(
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern,
            @NotNull final TreePattern<TermOrFormula>... subPatterns) {

        this(namePattern, new ChildrenPattern<>(new FlexibleArityForestPattern<>(subPatterns)));
    }

    public FunctionTermPattern(
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super((TreePattern<TermOrFormula>) namePattern, childrenPattern, createPredicatePattern());
    }

    public FunctionTermPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(
                name,
                (TreePattern<TermOrFormula>) namePattern,
                childrenPattern,
                createPredicatePattern());
    }

    public FunctionTermPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final NamePattern<TermOrFormula, IndexedSymbol> namePattern,
            @NotNull final ChildrenPattern<TermOrFormula> childrenPattern) {

        super(
                name,
                eqTester,
                (TreePattern<TermOrFormula>) namePattern,
                childrenPattern,
                createPredicatePattern());
    }

    @Override
    public TermOrFormula createTree(@NotNull final Match<TermOrFormula> match) {
        try {
            // try to use this patterns name
            return createTreeByMatchAndName(match);
        } catch (CreateException e) {
            // try to use the children pattern
            TreePattern<TermOrFormula> childrenPattern = this.getChild(1);

            Optional<TermOrFormula> optTree = childrenPattern.createTreeIfPossible(match);

            if (optTree.isPresent()) {
                return optTree.get();
            } else {
                // try to create children and construct tree with them
                Optional<IndexedSymbol> optName =
                        ((NamePattern<TermOrFormula, IndexedSymbol>) this.getChild(0))
                                .createNameIfPossible(match);

                if (!optName.isPresent()) {
                    throw new CreateException(this, match);
                }

                TreePattern<TermOrFormula> forestPattern = childrenPattern.getChild(0);

                List<Term> subterms = new ArrayList<>();

                // possible RepeatForestPattern occurrence needs to be handled
                // differently to provide a successful reconstruction if possible
                if (forestPattern instanceof FlexibleArityForestPattern) {

                    for (TreePattern<TermOrFormula> childPattern : forestPattern.getChildren()) {

                        Optional<List<TermOrFormula>> optSubterms =
                                childPattern.createForestIfPossible(match);

                        if (optSubterms.isPresent()) {
                            for (TermOrFormula subterm : optSubterms.get()) {
                                subterms.add((Term) subterm);
                            }
                        } else {
                            throw new CreateException(this, match);
                        }
                    }
                } else {

                    Optional<List<TermOrFormula>> optForest =
                            forestPattern.createForestIfPossible(match);

                    if (optForest.isPresent()) {
                        for (TermOrFormula subterm : optForest.get()) {
                            subterms.add((Term) subterm);
                        }
                    } else {
                        throw new CreateException(this, match);
                    }
                }

                return new FunctionTerm(optName.get().toString(), subterms);
            }
        }
    }

    @Override
    public List<TermOrFormula> createForest(@NotNull final Match<TermOrFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return FOPatternType.FunctionTermPattern;
    }

    @Override
    public String toString() {
        return "FunctionTermPattern [id=" + name + ", subpatterns=" + children + "]";
    }

    @Override
    public FunctionTermPattern clone() {
        return new FunctionTermPattern(name, eqTester, getClonedChildren());
    }

    @Override
    public FunctionTermPattern cloneWithIteratedName(int iteration) {

        return new FunctionTermPattern(
                iterateName(name, iteration),
                eqTester,
                getClonedChildrenWithIteratedNames(iteration));
    }

    private FunctionTermPattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<TermOrFormula>, List<TermOrFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<TermOrFormula>> children) {

        super(name, eqTester, children);
    }
}
