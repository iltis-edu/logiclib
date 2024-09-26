package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.InvalidSignatureException;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.collections.TupleIterator;
import de.tudortmund.cs.iltis.utils.function.SerializableFunction;
import de.tudortmund.cs.iltis.utils.function.SerializablePredicate;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A (predicate) logical structure consisting of a universe and relation and function
 * implementations. At all times (not thread-safe), the combination of relation and function symbols
 * of a structure is valid. Therefore, all methods (and constructors) check their arguments and
 * throw an exception if execution would induce a invalid combination of relation and function
 * symbols.
 *
 * @param <T> The type of the elements of the underlying universe
 */
public class Structure<T extends Comparable<T>> implements Serializable, Cloneable {

    /** Never null */
    private Map<RelationSymbol, RelationImplementation<T>> relations;

    /** Never null */
    private Map<FunctionSymbol, FunctionImplementation<T>> functions;

    /** Never null */
    private Universe<T> universe;

    /**
     * Creates a new Structure with the given universe and relation and function implementations.
     *
     * @param universe the universe to use. Is cloned.
     * @param relations the relations to use. A new map is created which uses the original symbols
     *     and implementations.
     * @param functions the function to use. A new map is created which uses the original symbols
     *     and implementations.
     * @throws IllegalArgumentException if any argument is null or combination of relation and
     *     function symbols is not valid
     */
    public Structure(
            Universe<T> universe,
            Map<RelationSymbol, RelationImplementation<T>> relations,
            Map<FunctionSymbol, FunctionImplementation<T>> functions) {

        if (universe == null || relations == null || functions == null)
            throw new IllegalArgumentException("No argument may be null");

        this.universe = universe.clone();
        this.relations = new HashMap<>(relations);
        this.functions = new HashMap<>(functions);

        if (!isValid())
            throw new IllegalArgumentException(
                    "The combination of specified functions and relations is not valid");
    }

    /**
     * Creates a new Structure with the given universe and no relations or functions.
     *
     * @param universe the universe to use. Is cloned.
     * @throws IllegalArgumentException if universe is null
     */
    public Structure(Universe<T> universe) {
        if (universe == null) throw new IllegalArgumentException("universe may not be null");
        this.universe = universe.clone();
        functions = new HashMap<>();
        relations = new HashMap<>();
    }

    /**
     * Adds a function implementation to this structure.
     *
     * @param symbol
     * @param implementation
     * @throws IllegalArgumentException if any argument is null, if a function with name symbol
     *     already exists or the combination of function and relations symbols after adding is not
     *     valid
     */
    public void addFunction(FunctionSymbol symbol, FunctionImplementation<T> implementation) {
        if (symbol == null || implementation == null)
            throw new IllegalArgumentException("Neither symbol nor implementation may be null");

        if (functions.containsKey(symbol))
            throw new IllegalArgumentException(
                    "FunctionTerm '" + symbol + "' is already contained in this structure");
        functions.put(symbol, implementation);

        if (!isValid()) {
            functions.remove(symbol);
            throw new IllegalArgumentException(
                    "FunctionTerm '"
                            + symbol
                            + "' contradicts the other elements of this structure");
        }
    }

    public void addFunction(String name, int arity, boolean infix, Function<Tuple<T>, T> function) {
        FunctionSymbol functionSymbol;
        RuleFunctionImplementation<T> functionImplementation;

        functionSymbol = new FunctionSymbol(new IndexedSymbol(name), arity, infix);
        SerializableFunction<Tuple<T>, T> functionSerializable;
        functionSerializable =
                new SerializableFunction<Tuple<T>, T>() {
                    @Override
                    public T apply(Tuple<T> ts) {
                        return function.apply(ts);
                    }
                };
        functionImplementation = new RuleFunctionImplementation<T>(functionSerializable);
        this.addFunction(functionSymbol, functionImplementation);
    }

    public void addFunction(String name, int arity, Function<Tuple<T>, T> function) {
        this.addFunction(name, arity, false, function);
    }

    public void addConstant(String name, T value) {
        this.addFunction(name, 0, tuple -> value);
    }

    /**
     * Adds a relation implementation to this structure.
     *
     * @param symbol
     * @param implementation
     * @throws IllegalArgumentException if any argument is null, if a relation with name symbol
     *     already exists or the combination of function and relations symbols after adding is not
     *     valid
     */
    public void addRelation(RelationSymbol symbol, RelationImplementation<T> implementation) {
        if (symbol == null || implementation == null)
            throw new IllegalArgumentException("Neither symbol nor implementation may be null");

        if (relations.containsKey(symbol))
            throw new IllegalArgumentException(
                    "RelationAtom '" + symbol + "' is already contained in this structure");
        relations.put(symbol, implementation);

        if (!isValid()) {
            relations.remove(symbol);
            throw new IllegalArgumentException(
                    "RelationAtom '"
                            + symbol
                            + "' contradicts the other elements of this structure");
        }
    }

    public void addRelation(String name, int arity, boolean infix, Predicate<Tuple<T>> predicate) {
        RelationSymbol relationSymbol;
        RuleRelationImplementation<T> relationImplementation;

        relationSymbol = new RelationSymbol(new IndexedSymbol(name), arity, infix);
        SerializablePredicate<Tuple<T>> predicateSerializable;
        predicateSerializable =
                new SerializablePredicate<Tuple<T>>() {
                    @Override
                    public boolean test(Tuple<T> ts) {
                        return predicate.test(ts);
                    }
                };
        relationImplementation = new RuleRelationImplementation<T>(predicateSerializable);
        this.addRelation(relationSymbol, relationImplementation);
    }

    public void addRelation(String name, int arity, Predicate<Tuple<T>> predicate) {
        this.addRelation(name, arity, false, predicate);
    }

    /**
     * Returns the signature consisting of exactly the relation and function symbols of this
     * structure. If this method is called <b>from the outside</b>, null may not be returned, since
     * at all times the signature have to be valid.
     *
     * @return the signature or null, if the combination of relation and function symbols used in
     *     this structure is not valid according to {@link Signature#isValid()}
     */
    public Signature getSignature() {
        try {
            return new Signature(relations.keySet(), functions.keySet());
        } catch (InvalidSignatureException e) {
            return null;
        }
    }

    public FunctionImplementation<T> getFunction(FunctionSymbol symbol) {
        return functions.get(symbol);
    }

    public RelationImplementation<T> getRelation(RelationSymbol symbol) {
        return relations.get(symbol);
    }

    public Universe<T> getUniverse() {
        return universe;
    }

    /**
     * Checks if the combination of relation and function symbols used for this structure is valid.
     * This method is to be called only from other methods of this class, since after their
     * execution true will be returned, at all times.
     *
     * @return true, iff the signature generated of this structure with {@link #getSignature()} is
     *     valid according to {@link Signature#isValid()}
     */
    private boolean isValid() {
        return getSignature() != null;
    }

    /**
     * Checks if this structure is compatible to the specified structure.
     *
     * @return true, iff the signature generated of this structure with {@link #getSignature()} is a
     *     subset of the specified signature
     */
    public boolean isCompatibleTo(Signature signature) {
        if (!isValid()) return false;
        return getSignature().isSubsetOf(signature);
    }

    public Iterator<Tuple<T>> getRelationIterator(RelationSymbol symbol) {
        return new RelationIterator(symbol);
    }

    public <S extends Comparable<S>> Structure<S> translateType(Map<T, S> map) {
        Map<RelationSymbol, RelationImplementation<S>> newRelations =
                relations.entrySet().stream()
                        .map(
                                entry ->
                                        new SerializablePair<>(
                                                entry.getKey(),
                                                entry.getValue().translateType(map)))
                        .collect(
                                Collectors.toMap(
                                        SerializablePair::first, SerializablePair::second));

        Map<FunctionSymbol, FunctionImplementation<S>> newFunctions =
                functions.entrySet().stream()
                        .map(
                                entry ->
                                        new SerializablePair<>(
                                                entry.getKey(),
                                                entry.getValue().translateType(map)))
                        .collect(
                                Collectors.toMap(
                                        SerializablePair::first, SerializablePair::second));

        return new Structure<>(universe.translateType(map), newRelations, newFunctions);
    }

    /**
     * Extends a structure to be compatible to a given signature. new relations are set to be empty
     * new functions map all elements to the first element of the universe new constants are the
     * first element of the universe
     *
     * @param signature the signature to be extended too
     * @return an extended structure
     */
    public Structure<T> extendToSignature(Signature signature) {
        Structure<T> extension = (Structure<T>) this.clone();

        for (RelationSymbol relationSymbol : signature.getRelSymbols()) {
            if (!extension.relations.containsKey(relationSymbol)) {
                extension.addRelation(relationSymbol, new SetRelationImplementation<>());
            }
        }

        T baseElement = extension.getUniverse().iterator().next();

        for (FunctionSymbol functionSymbol : signature.getFunSymbols()) {
            if (extension.functions.containsKey(functionSymbol)) {
                continue;
            }

            if (functionSymbol.getArity() == 0) {
                extension.addFunction(
                        functionSymbol, new ConstantFunctionImplementation<>(baseElement));
            } else {
                Map<Tuple<T>, T> functionMap = new HashMap<>();
                getAllTuples(extension.getUniverse(), functionSymbol.getArity())
                        .forEach(tuple -> functionMap.put(tuple, tuple.first()));
                extension.addFunction(functionSymbol, new MapFunctionImplementation<>(functionMap));
            }
        }

        return extension;
    }

    /**
     * Checks if all functions and relations have explicit (map or set) definitions
     *
     * @return true iff explicit
     */
    public boolean isExplicit() {
        for (Map.Entry<RelationSymbol, RelationImplementation<T>> entry : relations.entrySet()) {
            if (!(entry.getValue() instanceof SetRelationImplementation)) {
                return false;
            }
        }

        for (Map.Entry<FunctionSymbol, FunctionImplementation<T>> entry : functions.entrySet()) {
            if (!(entry.getValue() instanceof MapFunctionImplementation)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a set of all tuples of a given arity over a universe
     *
     * @param universe universe
     * @param arity arity (or tuplelength)
     * @return the set of all tuples
     */
    private Set<Tuple<T>> getAllTuples(Universe<T> universe, int arity) {
        if (arity < 1) {
            throw new IllegalArgumentException("Cannot generate tuples of non-positive size");
        }

        if (universe.isEmpty()) {
            throw new IllegalArgumentException("Cannot generate tuple over empty universe");
        }

        List<LinkedList<T>> currentTuples = new LinkedList<>();
        for (T element : universe) {
            LinkedList<T> baseTuple = new LinkedList<>();
            baseTuple.add(element);
            currentTuples.add(baseTuple);
        }

        List<LinkedList<T>> nextBiggerTuples = new LinkedList<>();
        for (int i = 1; i < arity; i++) {
            for (LinkedList<T> currentList : currentTuples) {
                for (T element : universe) {
                    LinkedList<T> nextTuple = (LinkedList<T>) currentList.clone();
                    nextTuple.add(element);
                    nextBiggerTuples.add(nextTuple);
                }
            }

            currentTuples = nextBiggerTuples;
            nextBiggerTuples = new LinkedList<>();
        }

        return currentTuples.stream().map(Tuple::new).collect(Collectors.toSet());
    }

    private class RelationIterator implements Iterator<Tuple<T>> {

        RelationImplementation<T> relation;
        TupleIterator<T> universeIterator;
        Tuple<T> nextTuple;

        RelationIterator(RelationSymbol symbol) {
            relation = getRelation(symbol);
            this.universeIterator = new TupleIterator<>(universe, symbol.getArity());
            next();
        }

        @Override
        public boolean hasNext() {
            return null != nextTuple;
        }

        @Override
        public Tuple<T> next() {
            Tuple<T> returnTuple = nextTuple;
            while (universeIterator.hasNext()) {
                nextTuple = universeIterator.next();
                if (relation.contains(nextTuple)) {
                    return returnTuple;
                }
            }
            nextTuple = null;
            return returnTuple;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((functions == null) ? 0 : functions.hashCode());
        result = prime * result + ((relations == null) ? 0 : relations.hashCode());
        result = prime * result + ((universe == null) ? 0 : universe.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Structure<T> other = (Structure<T>) obj;

        if (!functions.equals(other.functions)) return false;
        if (!relations.equals(other.relations)) return false;
        if (!universe.equals(other.universe)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Structure {universe: "
                + universe
                + "; relations: "
                + relations
                + "; functions: "
                + functions
                + "}";
    }

    protected Object clone() {
        return new Structure<>(universe, relations, functions);
    }

    /** For serialization */
    private static final long serialVersionUID = 1L;

    /** For GWT serialization */
    @SuppressWarnings("unused")
    private Structure() {}
}
