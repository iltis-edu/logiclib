package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFaultReason;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Describes a type which describes signature-like constraints, like allowed relation and function
 * symbols and their arities (and infix values). Currently, its implemented by {@link Signature} and
 * {@link SignaturePolicy}.
 */
public interface SignatureCheckable extends Cloneable, Serializable {

    // Abstract methods

    /**
     * Checks, whether the given function symbol is contained in this signature-like structure,
     * including checks for arity and infix value.
     */
    boolean containsFunctionSymbol(FunctionSymbol sym);

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a function
     * symbol, but does not check for arity or infix value.
     */
    boolean containsSymbolAsFunction(IndexedSymbol sym);

    /**
     * Checks, whether the given relation symbol is contained in this signature-like structure,
     * including checks for arity and infix value.
     */
    boolean containsRelationSymbol(RelationSymbol sym);

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a relation
     * symbol, but does not check for arity or infix value.
     */
    boolean containsSymbolAsRelation(IndexedSymbol sym);

    SignatureCheckable clone();

    // Default methods

    /**
     * Checks, whether the given function symbol is contained in this signature-like structure,
     * including checks for arity and infix value.
     *
     * @throws NullPointerException if {@code sym} is {@code null}
     * @return a fault collection object, never null
     */
    default SubsetFaultCollection containsFunctionSymbolElseGetFaults(FunctionSymbol sym) {
        Objects.requireNonNull(sym, "sym must not be null");

        List<SubsetFault> errors = new ArrayList<>();

        // --- a) given function symbol is contained in this signature-like structure ---
        if (containsFunctionSymbol(sym))
            ;
        // --- b) given function symbol is neither function nor relation symbol in this
        // signature-like structure ---
        else if (!containsSymbol(sym))
            errors.add(new SubsetFault(SubsetFaultReason.FUNCTION_SYMBOL_NOT_KNOWN, sym, null));
        // --- c) given function symbol is relation symbol in this signature-like structure ---
        else if (containsSymbolAsRelation(sym))
            errors.add(
                    new SubsetFault(
                            SubsetFaultReason.FUNCTION_SYMBOL_SHOULD_BE_RELATION, sym, null));
        // --- d) given function symbol has different infix value in this signature-like structure
        // ---
        else if (containsSymbolAsFunction(sym, sym.getArity()))
            errors.add(
                    new SubsetFault(SubsetFaultReason.INFIX_OF_FUNCTION_SYMBOL_WRONG, sym, null));
        // --- e) given function symbol has different arity in this signature-like structure ---
        else
            errors.add(
                    new SubsetFault(SubsetFaultReason.ARITY_OF_FUNCTION_SYMBOL_WRONG, sym, null));

        return new SubsetFaultCollection(errors, null, this);
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a function
     * symbol with the given arity. Does not check for infix value.
     */
    default boolean containsSymbolAsFunction(IndexedSymbol sym, int arity) {
        if (arity == 2)
            return containsFunctionSymbol(new FunctionSymbol(sym, arity, false))
                    || containsFunctionSymbol(new FunctionSymbol(sym, arity, true));
        return containsFunctionSymbol(new FunctionSymbol(sym, arity, false));
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a function
     * symbol, but does not check for arity or infix value.
     */
    default boolean containsSymbolAsFunction(String sym) {
        return containsSymbolAsFunction(new IndexedSymbol(sym));
    }

    /**
     * Checks, whether the given relation symbol is contained in this signature-like structure,
     * including checks for arity and infix value.
     *
     * @throws NullPointerException if {@code sym} is {@code null}
     * @return a fault collection object, never null
     */
    default SubsetFaultCollection containsRelationSymbolElseGetFaults(RelationSymbol sym) {
        Objects.requireNonNull(sym, "sym must not be null");

        List<SubsetFault> errors = new ArrayList<>();

        // --- a) given relation symbol is contained in this signature-like structure ---
        if (containsRelationSymbol(sym))
            ;
        // --- b) given relation symbol is neither function nor relation symbol in this
        // signature-like structure ---
        else if (!containsSymbol(sym))
            errors.add(new SubsetFault(SubsetFaultReason.RELATION_SYMBOL_NOT_KNOWN, sym, null));
        // --- c) given relation symbol is function symbol in this signature-like structure ---
        else if (containsSymbolAsFunction(sym))
            errors.add(
                    new SubsetFault(
                            SubsetFaultReason.RELATION_SYMBOL_SHOULD_BE_FUNCTION, sym, null));
        // --- d) given relation symbol has different infix value in this signature-like structure
        // ---
        else if (containsSymbolAsRelation(sym, sym.getArity()))
            errors.add(
                    new SubsetFault(SubsetFaultReason.INFIX_OF_RELATION_SYMBOL_WRONG, sym, null));
        // --- e) given relation symbol has different arity in this signature-like structure ---
        else
            errors.add(
                    new SubsetFault(SubsetFaultReason.ARITY_OF_RELATION_SYMBOL_WRONG, sym, null));

        return new SubsetFaultCollection(errors, null, this);
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a relation
     * symbol with the given arity. Does not check for infix value.
     */
    default boolean containsSymbolAsRelation(IndexedSymbol sym, int arity) {
        if (arity == 2)
            return containsRelationSymbol(new RelationSymbol(sym, arity, false))
                    || containsRelationSymbol(new RelationSymbol(sym, arity, true));
        return containsRelationSymbol(new RelationSymbol(sym, arity, false));
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a relation
     * symbol, but does not check for arity or infix value.
     */
    default boolean containsSymbolAsRelation(String sym) {
        return containsSymbolAsRelation(new IndexedSymbol(sym));
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a relation
     * or function symbol, but does not check for arity or infix value.
     */
    default boolean containsSymbol(IndexedSymbol sym) {
        return containsSymbolAsRelation(sym) || containsSymbolAsFunction(sym);
    }

    /**
     * Checks, whether the given symbol is contained in this signature-like structure as a relation
     * or function symbol, but does not check for arity or infix value.
     */
    default boolean containsSymbol(String sym) {
        return containsSymbol(new IndexedSymbol(sym));
    }

    /**
     * Checks, whether this signature-like object is compatible with the given set of variable
     * symbols.
     *
     * <h6>Implementation notice:</h6>
     *
     * <p>This method should be (slightly) faster than {@link
     * #isCompatibleToVariablesElseGetFaults(Set)} and can be used if the reason for failure is not
     * needed.
     *
     * @throws NullPointerException if {@code allVars} is {@code null}
     */
    default boolean isCompatibleToVariables(Set<VariableSymbol> allVars) {
        return allVars.stream().allMatch(var -> !containsSymbol(var));
    }

    /**
     * Checks, whether this signature-like object is compatible with the given set of variable
     * symbols.
     *
     * @throws NullPointerException if {@code allVars} is {@code null}
     * @return a fault collection object, never null
     */
    default VariableNamesFaultCollection isCompatibleToVariablesElseGetFaults(
            Set<VariableSymbol> allVars) {
        Objects.requireNonNull(allVars, "the variable set must not be null");

        List<VariableNamesFault> errors = new ArrayList<>();

        for (VariableSymbol var : allVars) {
            if (containsSymbolAsRelation(var))
                errors.add(
                        new VariableNamesFault(
                                VariableNamesFaultReason.SAME_SYMBOL_FOR_VARIABLE_AND_RELATION,
                                var,
                                null));
            if (containsSymbolAsFunction(var))
                errors.add(
                        new VariableNamesFault(
                                VariableNamesFaultReason.SAME_SYMBOL_FOR_VARIABLE_AND_FUNCTION,
                                var,
                                null));
        }

        return new VariableNamesFaultCollection(errors, allVars, this);
    }
}
