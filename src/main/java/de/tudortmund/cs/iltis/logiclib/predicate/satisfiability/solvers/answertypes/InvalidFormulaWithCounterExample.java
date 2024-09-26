package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InvalidFormulaWithCounterExample extends InvalidFormula {

    // serialization
    public InvalidFormulaWithCounterExample() {}

    private Interpretation<Integer> counterExample;

    public InvalidFormulaWithCounterExample(Interpretation<Integer> counterExample) {
        this.counterExample = counterExample;
    }

    @Override
    public Optional<Interpretation<Integer>> getExample() {
        if (counterExample == null) {
            return Optional.empty();
        }
        return Optional.of(counterExample);
    }

    private void processStringInput(String input, boolean usedCustomSort, Signature signature) {
        SetUniverse<Integer> universe = new IntUniverse();
        Map<FunctionSymbol, FunctionImplementation<Integer>> functions = new HashMap<>();
        Map<RelationSymbol, RelationImplementation<Integer>> relations = new HashMap<>();
        Valuation<Integer> valuation = new Valuation<>();

        // model is in smtlib format and can be created line by line
        String[] parts = input.split("\n");

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            // ignore comments and cardinality constraint for "="
            if (part.startsWith(";;")
                    || part.startsWith("(forall")
                    || part.startsWith("(or")
                    || part.startsWith("(=")) {
                continue;
            }

            // declare universe elements
            if (part.startsWith("(declare-fun ")) {
                // remove all non-numbers from line
                String numberElement = part.replaceAll("[^0-9.]", "");
                universe.addElement(Integer.parseInt(numberElement));
                continue;
            }

            // define functions, relations and constants
            // definitions can span over multiple lines
            String completeDefinition = part;
            i++;
            while (parts[i] != null && !parts[i].startsWith("(define-fun")) {
                completeDefinition = completeDefinition + parts[i];
                i++;
            }
            i--;

            // extract content
            String[] definitionParts = completeDefinition.split(" ");
            String content = definitionParts[1];

            // handle constants/free variables
            if (definitionParts[2].equals("()")) {
                valuation.define(
                        new VariableSymbol(content),
                        Integer.parseInt(definitionParts[4].replaceAll("[^0-9.]", "")));
                continue;
            }

            // determine if function or relation is defined
            int typeIndex = 2;
            while (definitionParts[typeIndex].startsWith("(")
                    || definitionParts[typeIndex].endsWith(")")) {
                typeIndex++;
            }
            int arity = (typeIndex - 2) / 2;

            // handle relations
            if (definitionParts[typeIndex].equals("Bool")) {

                continue;
            }

            // handle functions

        }
    }
}
