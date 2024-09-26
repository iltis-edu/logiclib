package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents an interpretation of a predicate formula consisting of a structure and a mapping of
 * variable symbols.
 *
 * @param <T> type of elements the structure contains
 */
public class Interpretation<T extends Comparable<T>> implements Serializable {
    private Structure<T> structure;
    private Valuation<T> valuation;

    public Interpretation(Structure<T> structure, Valuation<T> valuation) {
        this.structure = structure;
        this.valuation = valuation;
    }

    public Structure<T> getStructure() {
        return structure;
    }

    public Valuation<T> getValuation() {
        return valuation;
    }

    public <S extends Comparable<S>> Interpretation<S> translateType(Map<T, S> map) {
        return new Interpretation<>(structure.translateType(map), valuation.translateType(map));
    }

    public Interpretation<T> extendToSignature(Signature signature) {
        return new Interpretation<>(structure.extendToSignature(signature), valuation);
    }

    /** For serialization only. */
    private Interpretation() {}

    /**
     * Returns a JSON String if possible (e.g. for tasks with explicit interpretations)
     *
     * @return json string
     */
    public String toJsonString() {
        if (!structure.isExplicit()) {
            throw new IllegalStateException("Cannot convert non explicit interpretation to json");
        }

        StringBuilder json = new StringBuilder();

        json.append("{ Structure: { ");
        appendUniverse(json);
        json.append(", ");
        appendRelations(json);
        json.append(", ");
        appendFunctions(json);
        json.append("}, ");

        appendValuation(json);

        json.append("}");

        return json.toString();
    }

    private void appendUniverse(StringBuilder json) {
        json.append("Universe: [");
        Universe<T> universe = structure.getUniverse();
        Iterator<T> universeIterator = universe.iterator();
        json.append(universeIterator.next());
        while (universeIterator.hasNext()) {
            json.append(" ,");
            json.append(universeIterator.next());
        }
        json.append("]");
    }

    private void appendRelations(StringBuilder json) {
        json.append("Relations:[");
        Iterator<RelationSymbol> relationSymbolIterator =
                structure.getSignature().getRelSymbols().iterator();
        while (relationSymbolIterator.hasNext()) {
            RelationSymbol relationSymbol = relationSymbolIterator.next();
            appendSingleRelation(
                    relationSymbol,
                    (SetRelationImplementation<T>) structure.getRelation(relationSymbol),
                    json);
            if (relationSymbolIterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]");
    }

    private void appendSingleRelation(
            RelationSymbol relationSymbol,
            SetRelationImplementation<T> relationImplementation,
            StringBuilder json) {
        json.append("{Name: ");
        json.append(relationSymbol.getName());
        json.append(", Values: [");
        Iterator<Tuple<T>> tupleIterator = relationImplementation.getClonedSet().iterator();
        while (tupleIterator.hasNext()) {
            json.append("(");
            Iterator<T> elementIterator = tupleIterator.next().iterator();
            while (elementIterator.hasNext()) {
                json.append(elementIterator.next());
                if (elementIterator.hasNext()) {
                    json.append(",");
                }
            }
            json.append(")");
            if (tupleIterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]}");
    }

    private void appendFunctions(StringBuilder json) {
        json.append("Functions:[");
        Iterator<FunctionSymbol> functionSymbolIterator =
                structure.getSignature().getFunSymbols().iterator();
        while (functionSymbolIterator.hasNext()) {
            FunctionSymbol functionSymbol = functionSymbolIterator.next();
            appendSingleFunction(
                    functionSymbol,
                    (MapFunctionImplementation<T>) structure.getFunction(functionSymbol),
                    json);
            if (functionSymbolIterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]");
    }

    private void appendSingleFunction(
            FunctionSymbol functionSymbol,
            MapFunctionImplementation<T> functionImplementation,
            StringBuilder json) {
        json.append("{ Name: ");
        json.append(functionSymbol.getName());
        json.append(", Values: [");
        Iterator<Map.Entry<Tuple<T>, T>> mappingIterator =
                functionImplementation.getClonedMap().entrySet().iterator();
        while (mappingIterator.hasNext()) {
            json.append("(");
            Map.Entry<Tuple<T>, T> entry = mappingIterator.next();
            Iterator<T> elementIterator = entry.getKey().iterator();
            json.append("(");
            while (elementIterator.hasNext()) {
                json.append(elementIterator.next());
                if (elementIterator.hasNext()) {
                    json.append(",");
                }
            }
            json.append("),");
            json.append(entry.getValue());
            json.append(")");
            if (mappingIterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]}");
    }

    private void appendValuation(StringBuilder json) {
        json.append("Valuation: [");
        Iterator<VariableSymbol> variableSymbolIterator = valuation.getDomain().iterator();
        while (variableSymbolIterator.hasNext()) {
            VariableSymbol variableSymbol = variableSymbolIterator.next();
            json.append("(");
            json.append(variableSymbol.toString());
            json.append(",");
            json.append(valuation.map(variableSymbol));
            json.append(")");
            if (variableSymbolIterator.hasNext()) {
                json.append(", ");
            }
        }
        json.append("]");
    }
}
