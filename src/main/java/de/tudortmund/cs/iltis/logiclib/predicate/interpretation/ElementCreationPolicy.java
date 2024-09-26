package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.PairLike;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.io.Serializable;
import java.util.*;

/**
 * Describes a policy for creating (distinguishable) indexed symbols from (distinguishable)
 * integers.
 */
public class ElementCreationPolicy implements Serializable {

    private String policyDefinition;
    private Policies policy;

    public ElementCreationPolicy(String policyDefinition) {
        this.policyDefinition = policyDefinition;

        if (policyDefinition.contains("%NUMBER")) {
            policy = Policies.NUMBER;
            return;
        }

        if (policyDefinition.contains("%LETTER")) {
            policy = Policies.LETTER;
            return;
        }

        policy = Policies.APPEND;
    }

    public IndexedSymbol getElement(int sourceElement) {
        switch (policy) {
            case NUMBER:
                return new IndexedSymbol(
                        policyDefinition.replace("%NUMBER", String.valueOf(sourceElement)));
            case LETTER:
                final int RADIX = 10;
                return new IndexedSymbol(
                        policyDefinition.replace(
                                "%LETTER", String.valueOf((char) (sourceElement + 64))));
            case APPEND:
                return new IndexedSymbol(policyDefinition + sourceElement);
            default:
                throw new IllegalStateException("No sufficient naming policy is set!");
        }
    }

    /**
     * Translates an integer interpretation into an indexed symbol interpretation. This makes use of
     * specified indexed symbols obeying constraint and used the creation policy for creating new
     * elements.
     *
     * @param interpretation the interpretation to be used
     * @param namingConstraints a list of pairs of names and constraint formulas which exactly 1
     *     free variable and no quantifiers. If any formula is null, the name is used arbitrarily.
     *     The assigning priority is decreasing.
     * @return Interpretation with elements mapped according to constraints
     */
    public Interpretation<IndexedSymbol> translateToConstrainedSymbols(
            Interpretation<Integer> interpretation,
            List<PairLike<IndexedSymbol, Formula>> namingConstraints) {
        Structure<Integer> structure =
                extendSignature(
                        (Structure<Integer>) interpretation.getStructure().clone(),
                        namingConstraints);

        Set<Integer> unassignedNumbers = new HashSet<>();
        for (Integer entry : structure.getUniverse()) {
            unassignedNumbers.add(entry);
        }

        Map<Integer, IndexedSymbol> namingMap = new HashMap<>();

        List<IndexedSymbol> arbitraryNames = new LinkedList<>();

        // map integer entries to fitting symbols
        for (PairLike<IndexedSymbol, Formula> pair : namingConstraints) {
            if (pair.second() == null) {
                arbitraryNames.add(pair.first());
                continue;
            }

            Set<Valuation<Integer>> valuations =
                    new FormulaEvaluatorTopDown<Integer>(pair.second()).evaluate(structure);

            VariableSymbol variableSymbol =
                    pair.second().getFreeVariableSymbols().iterator().next();

            for (Valuation<Integer> valuation : valuations) {
                int assignedEntry = valuation.map(variableSymbol);

                if (unassignedNumbers.contains(assignedEntry)) {
                    namingMap.put(assignedEntry, pair.first());
                    unassignedNumbers.remove(assignedEntry);
                    break;
                }
            }
        }

        // arbitrary names
        Iterator<Integer> unassignedNumbersIterator = unassignedNumbers.iterator();
        Iterator<IndexedSymbol> arbitraryNamesIterator = arbitraryNames.iterator();
        while (unassignedNumbersIterator.hasNext() && arbitraryNamesIterator.hasNext()) {
            namingMap.put(unassignedNumbersIterator.next(), arbitraryNamesIterator.next());
        }

        // map open integer entries using policy
        int i = 1;
        while (unassignedNumbersIterator.hasNext()) {
            namingMap.put(unassignedNumbersIterator.next(), getElement(i));
            i++;
        }

        return new Interpretation<Integer>(structure, interpretation.getValuation())
                .translateType(namingMap);
    }

    /**
     * extends a structure to a minimal structure over all naming constraint signatures
     *
     * @param structure the structure to be extended
     * @param namingConstraints the constraints determining the signatures
     * @return the extended structure
     */
    private Structure<Integer> extendSignature(
            Structure<Integer> structure,
            List<PairLike<IndexedSymbol, Formula>> namingConstraints) {
        for (PairLike<IndexedSymbol, Formula> pair : namingConstraints) {
            if (pair.second() != null
                    && !pair.second().getSignature().isSubsetOf(structure.getSignature())) {
                Signature signature = pair.second().getSignature();

                for (RelationSymbol relationSymbol : signature.getRelSymbols()) {
                    if (!structure.getSignature().containsRelationSymbol(relationSymbol)) {
                        RelationImplementation<Integer> relationImplementation =
                                getIntegerRelationImplementation(
                                        relationSymbol, structure.getUniverse());

                        structure.addRelation(relationSymbol, relationImplementation);
                    }
                }

                for (FunctionSymbol functionSymbol : signature.getFunSymbols()) {
                    if (!structure.getSignature().containsFunctionSymbol(functionSymbol)) {
                        FunctionImplementation<Integer> functionImplementation =
                                new ConstantFunctionImplementation<>(
                                        structure.getUniverse().iterator().next());

                        structure.addFunction(functionSymbol, functionImplementation);
                    }
                }
            }
        }

        return structure;
    }

    private RelationImplementation<Integer> getIntegerRelationImplementation(
            RelationSymbol relationSymbol, Universe<Integer> universe) {
        SetRelationImplementation<Integer> relationImplementation =
                new SetRelationImplementation<>();

        if (relationSymbol.equals(new RelationSymbol("=", 2, true))) {
            for (Integer element : universe) {
                relationImplementation.add(new Tuple<>(element, element));
            }
        }

        return relationImplementation;
    }

    // serialization
    public ElementCreationPolicy() {}

    private enum Policies {
        NUMBER,
        LETTER,
        APPEND
    }
}
