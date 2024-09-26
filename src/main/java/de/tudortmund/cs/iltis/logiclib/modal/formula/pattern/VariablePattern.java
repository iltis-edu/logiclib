package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
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
 * Matches any modal logical formula consisting of only a single {@link Variable}.
 *
 * @see TreePattern
 */
public class VariablePattern extends MultiConstraintPattern<ModalFormula> {

    // needed for serialization
    private VariablePattern() {}

    private static PredicatePattern<ModalFormula> createPredicatePattern() {

        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0).isVariable());
    }

    public VariablePattern(@NotNull final NamePattern<ModalFormula, IndexedSymbol> namePattern) {
        super((TreePattern<ModalFormula>) namePattern, createPredicatePattern());
    }

    public VariablePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final NamePattern<ModalFormula, IndexedSymbol> namePattern) {

        super(name, (TreePattern<ModalFormula>) namePattern, createPredicatePattern());
    }

    public VariablePattern(
            @NotNull final IndexedSymbol name,
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final NamePattern<ModalFormula, IndexedSymbol> namePattern) {

        super(name, eqTester, (TreePattern<ModalFormula>) namePattern, createPredicatePattern());
    }

    @Override
    public ModalFormula createTree(@NotNull final Match<ModalFormula> match) {
        try {
            // try to use this patterns name
            return createTreeByMatchAndName(match);
        } catch (CreateException e) {
            // try to use name pattern
            Optional<IndexedSymbol> optName =
                    ((NamePattern<ModalFormula, IndexedSymbol>) this.getChild(0))
                            .createNameIfPossible(match);

            if (optName.isPresent()) {
                return new Variable(optName.get());
            }

            throw new CreateException(this, match);
        }
    }

    @Override
    public List<ModalFormula> createForest(@NotNull final Match<ModalFormula> match) {
        return Arrays.asList(createTree(match));
    }

    @Override
    public PatternType getType() {
        return ModalPatternType.VariablePattern;
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
            @NotNull final SerializableBiFunction<List<ModalFormula>, List<ModalFormula>, Boolean>
                            eqTester,
            @NotNull final List<TreePattern<ModalFormula>> children) {

        super(name, eqTester, children);
    }
}
