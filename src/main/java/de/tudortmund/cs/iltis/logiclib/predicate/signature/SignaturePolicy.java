package de.tudortmund.cs.iltis.logiclib.predicate.signature;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.function.SerializableBiPredicate;
import de.tudortmund.cs.iltis.utils.general.Data;
import java.util.Collection;
import java.util.Objects;

/**
 * A policy which symbols (with which arity and infix value) may appear as relation and function
 * symbols in a signature. This policy is modeled by one predicate for relation and one for function
 * symbols.
 */
public class SignaturePolicy implements SignatureCheckable {

    public static final SignaturePolicy NO_CONSTRAINT_POLICY =
            new SignaturePolicy((relSym, testArityInfix) -> true, (funSym, testArityInfix) -> true);

    public static final SignaturePolicy ONLY_PREFIX_POLICY =
            new SignaturePolicy(
                    (relSym, testArityInfix) -> !testArityInfix || !relSym.isInfix(),
                    (funSym, testArityInfix) -> !testArityInfix || !funSym.isInfix());

    /**
     * Signature policy used at chair 1 of Computer Science Department at TU Dortmund.
     *
     * <ul>
     *   <li>Symbols for constants are lower case letters from the beginning of the alphabet (a-e)
     *   <li>Symbols for relations are upper case letters
     *   <li>Symbols for functions are lower case letters from the middle of the alphabet (f-h)
     * </ul>
     *
     * Arbitrary sub- and superscripts may appear.
     */
    public static final SignaturePolicy TU_DORTMUND_LS1_WITH_INDEX_POLICY =
            ONLY_PREFIX_POLICY.tightenConstraints(
                    (relSym, testArityInfix) -> {
                        String name = relSym.getName();
                        if (name.length() != 1) return false;
                        char letter = name.charAt(0);
                        return 'A' <= letter && letter <= 'Z';
                    },
                    (funSym, testArityInfix) -> {
                        String name = funSym.getName();
                        if (name.length() != 1) return false;
                        char letter = name.charAt(0);
                        char firstAllowed, lastAllowed;
                        if (!testArityInfix) {
                            firstAllowed = 'a';
                            lastAllowed = 'h';
                        } else if (funSym.getArity() == 0) {
                            firstAllowed = 'a';
                            lastAllowed = 'e';
                        } else {
                            firstAllowed = 'f';
                            lastAllowed = 'h';
                        }
                        return firstAllowed <= letter && letter <= lastAllowed;
                    });

    /**
     * Like {@link SignaturePolicy#TU_DORTMUND_LS1_WITH_INDEX_POLICY} but does <b>not</b> allow sub-
     * and superscripts.
     */
    public static final SignaturePolicy TU_DORTMUND_LS1_LOGIK_POLICY =
            TU_DORTMUND_LS1_WITH_INDEX_POLICY.tightenConstraints(
                    (relSym, testArityInfix) -> !(relSym.hasSubscript() || relSym.hasSuperscript()),
                    (funSym, testArityInfix) ->
                            !(funSym.hasSubscript() || funSym.hasSuperscript()));

    /**
     * Like {@link SignaturePolicy#TU_DORTMUND_LS1_LOGIK_POLICY} but does allow infix notation "=".
     */
    public static final SignaturePolicy TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY =
            TU_DORTMUND_LS1_LOGIK_POLICY.weakenRelationConstraints(
                    new RelationSymbol("=", 2, true));

    private SerializableBiPredicate<RelationSymbol, Boolean> relConstraint;
    private SerializableBiPredicate<FunctionSymbol, Boolean> funConstraint;

    public SignaturePolicy(
            SerializableBiPredicate<RelationSymbol, Boolean> relConstraint,
            SerializableBiPredicate<FunctionSymbol, Boolean> funConstraint) {
        Objects.requireNonNull(relConstraint);
        Objects.requireNonNull(funConstraint);

        this.relConstraint = relConstraint;
        this.funConstraint = funConstraint;
    }

    @Override
    public boolean containsFunctionSymbol(FunctionSymbol sym) {
        Objects.requireNonNull(sym);
        return funConstraint.test(sym, true);
    }

    @Override
    public boolean containsSymbolAsFunction(IndexedSymbol sym) {
        Objects.requireNonNull(sym);
        return funConstraint.test(new FunctionSymbol(sym, 0, false), false);
    }

    @Override
    public boolean containsRelationSymbol(RelationSymbol sym) {
        Objects.requireNonNull(sym);
        return relConstraint.test(sym, true);
    }

    @Override
    public boolean containsSymbolAsRelation(IndexedSymbol sym) {
        Objects.requireNonNull(sym);
        return relConstraint.test(new RelationSymbol(sym, 0, false), false);
    }

    @Override
    public SignaturePolicy clone() {
        return new SignaturePolicy(relConstraint, funConstraint);
    }

    public SignaturePolicy tightenRelationConstraints(
            SerializableBiPredicate<RelationSymbol, Boolean> addRelConstraint) {
        Objects.requireNonNull(addRelConstraint);
        return tightenConstraints(addRelConstraint, (funSym, testArityInfix) -> true);
    }

    public SignaturePolicy tightenFunctionConstraints(
            SerializableBiPredicate<FunctionSymbol, Boolean> addFunConstraint) {
        Objects.requireNonNull(addFunConstraint);
        return tightenConstraints((relSym, testArityInfix) -> true, addFunConstraint);
    }

    public SignaturePolicy tightenConstraints(
            SerializableBiPredicate<RelationSymbol, Boolean> addRelConstraint,
            SerializableBiPredicate<FunctionSymbol, Boolean> addFunConstraint) {
        Objects.requireNonNull(addRelConstraint);
        Objects.requireNonNull(addFunConstraint);
        return new SignaturePolicy(
                relConstraint.and(addRelConstraint), funConstraint.and(addFunConstraint));
    }

    public SignaturePolicy weakenRelationConstraints(RelationSymbol... addRelSyms) {
        Objects.requireNonNull(addRelSyms);
        return weakenRelationConstraints(Data.newArrayList(addRelSyms));
    }

    public SignaturePolicy weakenRelationConstraints(Collection<RelationSymbol> addRelSyms) {
        Objects.requireNonNull(addRelSyms);
        return new SignaturePolicy(
                relConstraint.or(
                        (relSym, testArityInfix) ->
                                testArityInfix
                                        ? addRelSyms.contains(relSym)
                                        : addRelSyms.stream()
                                                .anyMatch(relSym::equalsWithoutArityAndInfix)),
                funConstraint);
    }

    public SignaturePolicy weakenFunctionConstraints(FunctionSymbol... addFunSyms) {
        Objects.requireNonNull(addFunSyms);
        return weakenFunctionConstraints(Data.newArrayList(addFunSyms));
    }

    public SignaturePolicy weakenFunctionConstraints(Collection<FunctionSymbol> addFunSyms) {
        Objects.requireNonNull(addFunSyms);
        return new SignaturePolicy(
                relConstraint,
                funConstraint.or(
                        (funSym, testArityInfix) ->
                                testArityInfix
                                        ? addFunSyms.contains(funSym)
                                        : addFunSyms.stream()
                                                .anyMatch(funSym::equalsWithoutArityAndInfix)));
    }

    public SignaturePolicy weakenConstraints(SignatureCheckable signature) {
        Objects.requireNonNull(signature);
        return new SignaturePolicy(
                relConstraint.or(
                        (relSym, testArityInfix) ->
                                testArityInfix
                                        ? signature.containsRelationSymbol(relSym)
                                        : signature.containsSymbolAsRelation(relSym)),
                funConstraint.or(
                        (funSym, testArityInfix) ->
                                testArityInfix
                                        ? signature.containsFunctionSymbol(funSym)
                                        : signature.containsSymbolAsFunction(funSym)));
    }

    /** For serialization */
    @SuppressWarnings("unused")
    private SignaturePolicy() {}
}
