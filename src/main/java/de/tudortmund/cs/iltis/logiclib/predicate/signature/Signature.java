package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFault;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection.VariableNamesFaultReason;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.StringUtils;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.SymbolSplittingPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Signature for predicate formulas consisting of relation and function symbols. Immutable class.
 */
public class Signature implements Cloneable, SignatureCheckable {

    /** For serialization */
    private static final long serialVersionUID = 1L;

    /** Never null; SortedSet is used for reliable output */
    private TreeSet<RelationSymbol> relSymbols;

    /** Never null; SortedSet is used for reliable output */
    private TreeSet<FunctionSymbol> funSymbols;

    /** Creates a new empty signature. */
    public Signature() {
        relSymbols = new TreeSet<>();
        funSymbols = new TreeSet<>();
    }

    /**
     * Creates a new signature.
     *
     * @param relSymbols the relation symbols to use
     * @param funSymbols the function symbols to use
     * @throws InvalidSignatureException if the combination of relation and function symbols is not
     *     valid according to {@link #isValid()}
     */
    public Signature(Set<RelationSymbol> relSymbols, Set<FunctionSymbol> funSymbols) {
        this.relSymbols = new TreeSet<>(relSymbols);
        this.funSymbols = new TreeSet<>(funSymbols);
        ValidityFaultCollection vfc = isValidElseGetFaults();
        if (vfc.containsAnyFault()) throw new InvalidSignatureException(vfc);
    }

    /**
     * Checks, whether the combination of relation and function symbols is valid. To be valid, the
     * relation and function symbols have to be disjunct and no symbol may appear twice with
     * different arities or infix values.
     *
     * <h6>Implementation notice:</h6>
     *
     * <p>This method should be (slightly) faster than #isValidElseException() and can be used if
     * the reason for failure is not needed. Currently, it is not used.
     */
    @SuppressWarnings("unused")
    private boolean isValid() {
        for (RelationSymbol relation : relSymbols)
            if (relSymbols.stream().anyMatch(rel -> rel.contradicts(relation))
                    || funSymbols.stream().anyMatch(f -> f.equalsWithoutArityAndInfix(relation)))
                return false;
        for (FunctionSymbol function : funSymbols)
            if (funSymbols.stream().anyMatch(f -> f.contradicts(function))) return false;
        return true;
    }

    /**
     * Checks, whether the combination of relation and function symbols is valid. To be valid, the
     * relation and function symbols have to be disjunct and no symbol may appear twice with
     * different arities or infix values.
     *
     * @return a fault collection object, never null
     */
    private ValidityFaultCollection isValidElseGetFaults() {
        List<ValidityFault> errors = new ArrayList<>();

        Set<RelationSymbol> freshRelSymbols = new TreeSet<>(relSymbols);
        for (RelationSymbol relation : relSymbols) {
            // remove already analyzed symbols from set to prevent creating each error message twice
            freshRelSymbols.remove(relation);

            // --- symbol is relation and function symbol ---
            funSymbols.stream()
                    .filter(fun -> fun.equalsWithoutArityAndInfix(relation))
                    .forEach(
                            fun ->
                                    errors.add(
                                            new ValidityFault(
                                                    ValidityFaultReason
                                                            .SAME_SYMBOL_FOR_RELATION_AND_FUNCTION,
                                                    relation,
                                                    fun)));

            // --- relation symbol has different arities ---
            freshRelSymbols.stream()
                    .filter(rel -> rel.contradictsArity(relation))
                    .forEach(
                            rel ->
                                    errors.add(
                                            new ValidityFault(
                                                    ValidityFaultReason
                                                            .SAME_RELATION_SYMBOL_WITH_DIFFERENT_ARITIES,
                                                    relation,
                                                    rel)));

            // --- relation symbol has different infix values ---
            freshRelSymbols.stream()
                    .filter(rel -> rel.contradictsInfix(relation))
                    .forEach(
                            rel ->
                                    errors.add(
                                            new ValidityFault(
                                                    ValidityFaultReason
                                                            .SAME_RELATION_SYMBOL_WITH_DIFFERENT_INFIX,
                                                    relation,
                                                    rel)));
        }

        Set<FunctionSymbol> freshFunSymbols = new TreeSet<>(funSymbols);
        for (FunctionSymbol function : funSymbols) {
            // remove already analyzed symbols from set to prevent creating each error message twice
            freshFunSymbols.remove(function);

            // --- function symbol has different arities ---
            freshFunSymbols.stream()
                    .filter(fun -> fun.contradictsArity(function))
                    .forEach(
                            fun ->
                                    errors.add(
                                            new ValidityFault(
                                                    ValidityFaultReason
                                                            .SAME_FUNCTION_SYMBOL_WITH_DIFFERENT_ARITIES,
                                                    function,
                                                    fun)));

            // --- function symbol has different infix values ---
            freshFunSymbols.stream()
                    .filter(fun -> fun.contradictsInfix(function))
                    .forEach(
                            fun ->
                                    errors.add(
                                            new ValidityFault(
                                                    ValidityFaultReason
                                                            .SAME_FUNCTION_SYMBOL_WITH_DIFFERENT_INFIX,
                                                    function,
                                                    fun)));
        }

        return new ValidityFaultCollection(errors, relSymbols, funSymbols);
    }

    @Override
    public boolean containsRelationSymbol(RelationSymbol rel) {
        return relSymbols.contains(rel);
    }

    @Override
    public boolean containsFunctionSymbol(FunctionSymbol f) {
        return funSymbols.contains(f);
    }

    //	public boolean containsRelationSymbol(IndexedSymbol sym) {
    //		return relSymbols.stream().anyMatch(rel -> rel.equalsWithoutArityAndInfix(sym));
    //	}
    //
    //	public boolean containsFunctionSymbol(IndexedSymbol sym) {
    //		return funSymbols.stream().anyMatch(f -> f.equalsWithoutArityAndInfix(sym));
    //	}
    //
    //	public boolean containsRelationSymbol(String sym) {
    //		return containsRelationSymbol(new IndexedSymbol(sym));
    //	}
    //
    //	public boolean containsFunctionSymbol(String sym) {
    //		return containsFunctionSymbol(new IndexedSymbol(sym));
    //	}

    @Override
    public boolean containsSymbolAsRelation(IndexedSymbol sym) {
        return relSymbols.stream().anyMatch(rel -> rel.equalsWithoutArityAndInfix(sym));
    }

    @Override
    public boolean containsSymbolAsFunction(IndexedSymbol sym) {
        return funSymbols.stream().anyMatch(f -> f.equalsWithoutArityAndInfix(sym));
    }

    /**
     * Checks, whether this signature is compatible with the given set of variable symbols.
     *
     * <h6>Implementation notice:</h6>
     *
     * <p>In contrast to {@link SignatureCheckable#isCompatibleToVariablesElseGetFaults(Set)} this
     * method does include the relation or function symbol, which conflicts with a variable symbol,
     * in the faults.
     *
     * @throws NullPointerException if {@code allVars} is {@code null}
     * @return a fault collection object, never null
     */
    @Override
    public VariableNamesFaultCollection isCompatibleToVariablesElseGetFaults(
            Set<VariableSymbol> allVars) {
        Objects.requireNonNull(allVars, "the variable set must not be null");

        List<VariableNamesFault> errors = new ArrayList<>();

        for (VariableSymbol var : allVars) {
            relSymbols.stream()
                    .filter(rel -> rel.equalsWithoutArityAndInfix(var))
                    .forEach(
                            rel ->
                                    errors.add(
                                            new VariableNamesFault(
                                                    VariableNamesFaultReason
                                                            .SAME_SYMBOL_FOR_VARIABLE_AND_RELATION,
                                                    var,
                                                    rel)));
            funSymbols.stream()
                    .filter(fun -> fun.equalsWithoutArityAndInfix(var))
                    .forEach(
                            fun ->
                                    errors.add(
                                            new VariableNamesFault(
                                                    VariableNamesFaultReason
                                                            .SAME_SYMBOL_FOR_VARIABLE_AND_FUNCTION,
                                                    var,
                                                    fun)));
        }

        return new VariableNamesFaultCollection(errors, allVars, this);
    }

    /**
     * Checks, whether this signature is a subset of {@code other}.
     *
     * <h6>Implementation notice:</h6>
     *
     * <p>This method should be faster than {@link #isSubsetOfElseGetFaults(SignatureCheckable)} and
     * can be used if the reason for failure is not needed.
     *
     * @throws NullPointerException if {@code other} is {@code null}
     */
    public boolean isSubsetOf(SignatureCheckable other) {
        Objects.requireNonNull(other, "other must not be null");
        return relSymbols.stream().allMatch(other::containsRelationSymbol)
                && funSymbols.stream().allMatch(other::containsFunctionSymbol);
    }

    /**
     * Checks, whether this signature is a subset of {@code other}.
     *
     * <p>If the given signature-like object is of type {@link Signature}, more detailed faults can
     * be given than for other classes. In particular, for class {@link Signature} as {@code other},
     * in the single {@link SubsetFault}s the exact symbol conflicting with a symbol of {@code this}
     * is given.
     *
     * @throws NullPointerException if {@code other} is {@code null}
     * @return a fault collection object, never null
     */
    public SubsetFaultCollection isSubsetOfElseGetFaults(SignatureCheckable other) {
        Objects.requireNonNull(other, "other must not be null");

        if (other instanceof Signature) return isSubsetOfElseGetFaultsInternal((Signature) other);
        else return isSubsetOfElseGetFaultsInternal(other);
    }

    private SubsetFaultCollection isSubsetOfElseGetFaultsInternal(SignatureCheckable other) {
        List<SubsetFault> errors = new ArrayList<>();

        relSymbols.forEach(
                thisRel ->
                        errors.addAll(
                                other.containsRelationSymbolElseGetFaults(thisRel).getFaults()));
        funSymbols.forEach(
                thisFun ->
                        errors.addAll(
                                other.containsFunctionSymbolElseGetFaults(thisFun).getFaults()));

        return new SubsetFaultCollection(errors, this, other);
    }

    private SubsetFaultCollection isSubsetOfElseGetFaultsInternal(Signature other) {
        List<SubsetFault> errors = new ArrayList<>();

        // --- symbol in this signature is neither function nor relation symbol in other ---
        relSymbols.stream()
                .filter(
                        thisRel ->
                                other.relSymbols.stream()
                                                .noneMatch(
                                                        otherRel ->
                                                                otherRel.equalsWithoutArityAndInfix(
                                                                        thisRel))
                                        && other.funSymbols.stream()
                                                .noneMatch(
                                                        otherFun ->
                                                                otherFun.equalsWithoutArityAndInfix(
                                                                        thisRel)))
                .forEach(
                        thisRel ->
                                errors.add(
                                        new SubsetFault(
                                                SubsetFaultReason.RELATION_SYMBOL_NOT_KNOWN,
                                                thisRel,
                                                null)));
        funSymbols.stream()
                .filter(
                        thisFun ->
                                other.relSymbols.stream()
                                                .noneMatch(
                                                        otherRel ->
                                                                otherRel.equalsWithoutArityAndInfix(
                                                                        thisFun))
                                        && other.funSymbols.stream()
                                                .noneMatch(
                                                        otherFun ->
                                                                otherFun.equalsWithoutArityAndInfix(
                                                                        thisFun)))
                .forEach(
                        thisFun ->
                                errors.add(
                                        new SubsetFault(
                                                SubsetFaultReason.FUNCTION_SYMBOL_NOT_KNOWN,
                                                thisFun,
                                                null)));

        for (RelationSymbol thisRel : relSymbols) {
            // --- relation symbol in this signature is function symbol in other ---
            other.funSymbols.stream()
                    .filter(otherFun -> otherFun.equalsWithoutArityAndInfix(thisRel))
                    .forEach(
                            otherFun ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .RELATION_SYMBOL_SHOULD_BE_FUNCTION,
                                                    thisRel,
                                                    otherFun)));

            // --- relation symbol in this signature has different arity in other ---
            other.relSymbols.stream()
                    .filter(thisRel::contradictsArity)
                    .forEach(
                            otherRel ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .ARITY_OF_RELATION_SYMBOL_WRONG,
                                                    thisRel,
                                                    otherRel)));

            // --- relation symbol in this signature has different infix value in other ---
            other.relSymbols.stream()
                    .filter(thisRel::contradictsInfix)
                    .forEach(
                            otherRel ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .INFIX_OF_RELATION_SYMBOL_WRONG,
                                                    thisRel,
                                                    otherRel)));
        }

        for (FunctionSymbol thisFun : funSymbols) {
            // --- function symbol in this signature is relation symbol in other ---
            other.relSymbols.stream()
                    .filter(otherRel -> otherRel.equalsWithoutArityAndInfix(thisFun))
                    .forEach(
                            otherRel ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .FUNCTION_SYMBOL_SHOULD_BE_RELATION,
                                                    thisFun,
                                                    otherRel)));

            // --- function symbol in this signature has different arity in other ---
            other.funSymbols.stream()
                    .filter(thisFun::contradictsArity)
                    .forEach(
                            otherFun ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .ARITY_OF_FUNCTION_SYMBOL_WRONG,
                                                    thisFun,
                                                    otherFun)));

            // --- function symbol in this signature has different infix value in other ---
            other.funSymbols.stream()
                    .filter(thisFun::contradictsInfix)
                    .forEach(
                            otherFun ->
                                    errors.add(
                                            new SubsetFault(
                                                    SubsetFaultReason
                                                            .INFIX_OF_FUNCTION_SYMBOL_WRONG,
                                                    thisFun,
                                                    otherFun)));
        }

        return new SubsetFaultCollection(errors, this, other);
    }

    public SortedSet<RelationSymbol> getRelSymbols() {
        return relSymbols;
    }

    public SortedSet<FunctionSymbol> getFunSymbols() {
        return funSymbols;
    }

    public RelationSymbol getRelationSymbol(IndexedSymbol sym) {
        for (RelationSymbol relationSymbol : relSymbols) {
            if (relationSymbol.equalsWithoutArityAndInfix(sym)) {
                return relationSymbol;
            }
        }
        return null;
    }

    public FunctionSymbol getFunctionSymbol(IndexedSymbol sym) {
        for (FunctionSymbol functionSymbol : funSymbols) {
            if (functionSymbol.equalsWithoutArityAndInfix(sym)) {
                return functionSymbol;
            }
        }
        return null;
    }

    public RelationSymbol getRelationSymbol(String sym) {
        return getRelationSymbol(new IndexedSymbol(sym));
    }

    public FunctionSymbol getFunctionSymbol(String sym) {
        return getFunctionSymbol(new IndexedSymbol(sym));
    }

    /**
     * Checks whether all relation and function symbols are expressible by the given symbol
     * splitting policy.
     */
    public boolean isCompatibleToSymbolSplittingPolicy(SymbolSplittingPolicy policy) {
        return relSymbols.stream().allMatch(policy::isExpressible)
                && funSymbols.stream().allMatch(policy::isExpressible);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + funSymbols.hashCode();
        result = prime * result + relSymbols.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Signature other = (Signature) o;
        return this.isSubsetOf(other) && other.isSubsetOf(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(StringUtils.concatenate(relSymbols));
        sb.append("; ");
        sb.append(StringUtils.concatenate(funSymbols));
        sb.append(")");
        return sb.toString();
    }

    public Signature clone() {
        SortedSet<RelationSymbol> newRelations = new TreeSet<>(relSymbols);
        SortedSet<FunctionSymbol> newFunctions = new TreeSet<>(funSymbols);
        return new Signature(newRelations, newFunctions);
    }
}
