package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.baselogic.utils.ConstrainedPathCollector;
import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterHTML;
import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterSafeText;
import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterText;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.*;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFault;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.InvalidSignatureException;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import de.tudortmund.cs.iltis.utils.collections.TypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.term.ComparableTerm;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Representation of a predicate logical term or formula. This is the basic class for predicate
 * logical constructs.
 */
public abstract class TermOrFormula extends ComparableTerm<TermOrFormula, IndexedSymbol>
        implements ParsablyTyped {

    public TermOrFormula(boolean arityFixed) {
        super(arityFixed);
    }

    public TermOrFormula(boolean arityFixed, final TermOrFormula... subterms) {
        super(arityFixed, subterms);
    }

    public TermOrFormula(boolean arityFixed, final Iterable<? extends TermOrFormula> subterms) {
        super(arityFixed, subterms);
    }

    public TermOrFormula(boolean arityFixed, final IndexedSymbol name) {
        super(arityFixed, name);
    }

    public TermOrFormula(
            boolean arityFixed, final IndexedSymbol name, final TermOrFormula... subterms) {
        super(arityFixed, name, subterms);
    }

    public TermOrFormula(
            boolean arityFixed,
            final IndexedSymbol name,
            final Iterable<? extends TermOrFormula> subterms) {
        super(arityFixed, name, subterms);
    }

    //////////////////////////////////////////////
    // INFORMATION ///////////////////////////////
    //////////////////////////////////////////////

    /**
     * @return true iff this is a term and does not use variables.
     */
    public boolean isBasicTerm() {
        return isTerm() && getVariables().isEmpty();
    }

    /**
     * @return true iff this is a relation symbol or true or false.
     */
    public boolean isAtomic() {
        return isRelation() || isTrueOrFalse();
    }

    /**
     * @return true iff this is a formula and does not contain quantifiers.
     */
    public boolean isQuantifierFree() {
        return isFormula() && getBoundVariables().isEmpty();
    }

    /**
     * @return true iff this is a formula and all variables are bound.
     */
    public boolean isSentence() {
        return isFormula() && getFreeVariables().isEmpty();
    }

    /**
     * @return A set of all {@link Term}s which are contained in this {@link TermOrFormula}.
     */
    public Set<Term> getAllSubterms() {
        return getDescendants().stream()
                .filter(TermOrFormula::isTerm)
                .map(t -> (Term) t)
                .collect(Collectors.toSet());
    }

    /**
     * @return A set of all {@link Formula} which are contained in this {@link TermOrFormula}.
     */
    public Set<Formula> getAllSubformulae() {
        return getDescendants().stream()
                .filter(TermOrFormula::isFormula)
                .map(t -> (Formula) t)
                .collect(Collectors.toSet());
    }

    public int getQuantifierCount() {
        long count = getDescendants().stream().filter(TermOrFormula::isQuantifier).count();
        return (int) count;
    }

    /**
     * @return A set of all variables which are free at least once.
     */
    public Set<Variable> getFreeVariables() {
        return traverse(new FreeVariableCollector());
    }

    /**
     * @return A set of all variables which are bound by at least one quantifier.
     */
    public Set<Variable> getBoundVariables() {
        return traverse(new BoundVariableCollector());
    }

    /**
     * @return A set of all variables.
     */
    public Set<Variable> getVariables() {
        return traverse(new VariableCollector());
    }

    /**
     * @return A set of all variable symbols which are free at least once.
     */
    public Set<VariableSymbol> getFreeVariableSymbols() {
        return getFreeVariables().stream().map(Variable::getName).collect(Collectors.toSet());
    }

    /**
     * @return A set of all variable symbols which are bound by at least one quantifier.
     */
    public Set<VariableSymbol> getBoundVariableSymbols() {
        return getBoundVariables().stream().map(Variable::getName).collect(Collectors.toSet());
    }

    /**
     * @return A set of all variables symbols.
     */
    public Set<VariableSymbol> getVariableSymbols() {
        return getVariables().stream().map(Variable::getName).collect(Collectors.toSet());
    }

    /**
     * @return A set of all relation symbols.
     */
    public Set<RelationSymbol> getRelationSymbols() {
        return getDescendants().stream()
                .filter(TermOrFormula::isRelation)
                .map(rel -> ((RelationAtom) rel).getName())
                .collect(Collectors.toSet());
    }

    /**
     * @return A set of all function symbols.
     */
    public Set<FunctionSymbol> getFunctionSymbols() {
        return getDescendants().stream()
                .filter(TermOrFormula::isFunction)
                .map(f -> ((FunctionTerm) f).getName())
                .collect(Collectors.toSet());
    }

    public FunctionSymbol getFunctionSymbol() {
        return this.getFunctionSymbols().iterator().next();
    }

    /**
     * Returns the signature consisting of exactly the relation and function symbols of this term or
     * formula.
     *
     * @return the signature or null, if the combination of relation and function symbols used in
     *     this formula is not valid according to {@link Signature#isValid()}
     */
    public Signature getSignature() {
        try {
            return new Signature(getRelationSymbols(), getFunctionSymbols());
        } catch (InvalidSignatureException e) {
            return null;
        }
    }

    /**
     * Checks if the combination of relation and function symbols and variable symbols used in this
     * term or formula is valid.
     *
     * @return true, iff the signature generated of this formula with {@link #getSignature()} is
     *     valid according to {@link Signature#isValid()} and is compatible to the used variable
     *     names according to {@link Signature#isCompatibleToVariables(Set)}
     */
    public boolean isValid() {
        Signature thisSignature = getSignature();
        if (thisSignature == null) return false;
        return thisSignature.isCompatibleToVariables(getVariableSymbols());
    }

    /**
     * Checks if the combination of relation and function symbols and variable symbols used in this
     * term or formula is valid.
     *
     * @return a type mapping, never null. The key {@code ValidityFaultCollection} is present, iff
     *     the signature of this term or formula is not valid. The key {@code
     *     VariableNamesFaultCollection} is present, iff the signature of this term or formula is
     *     not compatible to the variable names used in this term or formula
     */
    public TypeMapping<FaultCollection<?, ?>> isValidElseGetFaults() {
        TypeMapping<FaultCollection<?, ?>> faults = new TypeMapping<>();
        Signature thisSignature = null;
        try {
            thisSignature = new Signature(getRelationSymbols(), getFunctionSymbols());
        } catch (InvalidSignatureException e) {
            faults = faults.with(e.getFaultCollection());
        }
        if (thisSignature != null) {
            VariableNamesFaultCollection vnfc =
                    thisSignature.isCompatibleToVariablesElseGetFaults(getVariableSymbols());
            if (vnfc.containsAnyFault()) faults = faults.with(vnfc);
        }
        return faults;
    }

    /**
     * Checks if this term or formula is compatible to the specified signature-like object. That
     * includes, that this term or formula has a valid Signature, this signature is a subset of the
     * specified signature-like object, and the variable names used are compatible to the
     * <b>specified</b> signature-like object.
     *
     * @return true, iff this term or formula is compatible to {@code signature}
     * @throws NullPointerException if {@code signature} is {@code null}
     */
    public boolean isCompatibleTo(SignatureCheckable signature) {
        Objects.requireNonNull(signature, "the given signature must not be null");

        Signature thisSignature = getSignature();
        if (thisSignature == null) return false;
        return thisSignature.isSubsetOf(signature)
                && signature.isCompatibleToVariables(getVariableSymbols());
    }

    /**
     * Checks if this term or formula is compatible to the specified signature-like object. That
     * includes, that this term or formula has a valid Signature, this signature is a subset of the
     * specified signature-like object, and the variable names used are compatible to the
     * <b>specified</b> signature-like object.
     *
     * @throws NullPointerException if {@code signature} is {@code null}
     * @return a type mapping, never null. The key {@code ValidityFaultCollection} is present, iff
     *     the signature of this term or formula is not valid The key {@code SubsetFaultCollection}
     *     is present, iff the signature of this term or formula is not a subset of the specified
     *     signature-like object The key {@code VariableNamesFaultCollection} is present, iff the
     *     specified signature-like object is not compatible to the variable names used in this term
     *     or formula
     */
    public TypeMapping<FaultCollection<?, ?>> isCompatibleToElseGetFaults(
            SignatureCheckable signature) {
        Objects.requireNonNull(signature, "the given signature must not be null");

        TypeMapping<FaultCollection<?, ?>> faults = new TypeMapping<>();
        Signature thisSignature = null;
        try {
            thisSignature = new Signature(getRelationSymbols(), getFunctionSymbols());
        } catch (InvalidSignatureException e) {
            faults = faults.with(e.getFaultCollection());
        }

        if (thisSignature != null) {
            SubsetFaultCollection sfc = thisSignature.isSubsetOfElseGetFaults(signature);
            if (sfc.containsAnyFault()) faults = faults.with(sfc);
        }

        VariableNamesFaultCollection vnfc =
                signature.isCompatibleToVariablesElseGetFaults(getVariableSymbols());
        if (vnfc.containsAnyFault()) faults = faults.with(vnfc);

        return faults;
    }

    /**
     * Checks if this term or formula only contains free variables out of the given set.
     *
     * @return true, iff all of this term's or formula's free variables appear in {@code allowed}
     * @throws NullPointerException if {@code allowed} is {@code null}
     */
    public boolean areFreeVariablesOutOf(Set<VariableSymbol> allowed) {
        Objects.requireNonNull(allowed);
        Set<VariableSymbol> actual = getFreeVariableSymbols();
        return allowed.containsAll(actual);
    }

    /**
     * Checks if this term or formula only contains free variables out of the given set.
     *
     * @throws NullPointerException if {@code allowed} is {@code null}
     * @return a FreeVariablesFaultCollection-object, never null.
     */
    public FreeVariablesFaultCollection areFreeVariablesOutOfElseGetFaults(
            Set<VariableSymbol> allowed) {
        Objects.requireNonNull(allowed);
        Set<VariableSymbol> actual = getFreeVariableSymbols();
        List<FreeVariablesFault> faults = new ArrayList<>();
        actual.stream()
                .filter(var -> !allowed.contains(var))
                .forEach(
                        var ->
                                faults.add(
                                        new FreeVariablesFault(
                                                FreeVariablesFaultReason.SYMBOL_NOT_ALLOWED, var)));
        return new FreeVariablesFaultCollection(faults, allowed, actual);
    }

    //////////////////////////////////////////////
    // FORMULA DECONSTRUCTORS ////////////////////
    //////////////////////////////////////////////

    public abstract FormulaType getType();

    public boolean isVariable() {
        return getType() == FormulaType.VARIABLE;
    }

    /**
     * @return If this term or formula is a function with arity zero, i.e. a constant.
     */
    public boolean isConstant() {
        return getType() == FormulaType.CONSTANT;
    }

    /**
     * @return If this term or formula is a function with arity higher than zero.
     */
    public boolean isProperFunction() {
        return getType() == FormulaType.INFIX_FUNCTION_TERM
                || getType() == FormulaType.PREFIX_FUNCTION_TERM;
    }

    /**
     * @return If this term or formula is a function with arbitrary arity (including zero).
     */
    public boolean isFunction() {
        return isConstant() || isProperFunction();
    }

    public boolean isTerm() {
        return isFunction() || isVariable();
    }

    public boolean isRelation() {
        return getType() == FormulaType.INFIX_RELATION_ATOM
                || getType() == FormulaType.PREFIX_RELATION_ATOM;
    }

    public boolean isConjunction() {
        return getType() == FormulaType.AND;
    }

    public boolean isDisjunction() {
        return getType() == FormulaType.OR;
    }

    public boolean isImplication() {
        return getType() == FormulaType.IMPLIES;
    }

    public boolean isEquivalence() {
        return getType() == FormulaType.EQUIV;
    }

    public boolean isNegation() {
        return getType() == FormulaType.NEG;
    }

    public boolean isBooleanConnector() {
        return isConjunction()
                || isDisjunction()
                || isImplication()
                || isEquivalence()
                || isNegation();
    }

    public boolean isExistentialQuantifier() {
        return getType() == FormulaType.EXISTS;
    }

    public boolean isUniversalQuantifier() {
        return getType() == FormulaType.FORALL;
    }

    public boolean isQuantifier() {
        return isExistentialQuantifier() || isUniversalQuantifier();
    }

    public boolean isTrue() {
        return getType() == FormulaType.TRUE;
    }

    public boolean isFalse() {
        return getType() == FormulaType.FALSE;
    }

    public boolean isTrueOrFalse() {
        return isTrue() || isFalse();
    }

    public boolean isFormula() {
        return !isTerm();
    }

    @Override
    public String toString() {
        return toTextString();
    }

    public String toSafeTextString() {
        return safeTextWriter.writeAndChop(this);
    }

    public String toTextString() {
        return textWriter.writeAndChop(this);
    }

    public String toHTMLString() {
        return htmlWriter.writeAndChop(this);
    }

    public String toLatexString() {
        return latexWriter.writeAndChop(this);
    }

    /**
     * @return the formula without conjunction/disjunctions with less than 2 children
     */
    public TermOrFormula removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers() {
        TermOrFormula transformedFormula = this.clone();

        // get paths of Conjunctions/Disjunctions with wrong number of children
        Set<TreePath> problemPaths =
                transformedFormula.traverse(
                        new ConstrainedPathCollector<TermOrFormula>(
                                t ->
                                        (t.isConjunction() || t.isDisjunction())
                                                && t.getNumberOfChildren() <= 1));

        // when replacing subtrees by their child the paths of other possible problematic
        // subformulas in the child change
        // in order to prevent having to recalculate them, we sort the paths by descending size
        List<TreePath> sortedProblemPaths =
                problemPaths.stream()
                        .sorted((t1, t2) -> t2.size() - t1.size())
                        .collect(Collectors.toList());

        for (TreePath path : sortedProblemPaths) {
            TermOrFormula toBeReplaced = transformedFormula.retrieve(path);
            if (toBeReplaced.getNumberOfChildren() == 1) {
                transformedFormula =
                        (TermOrFormula)
                                transformedFormula.transform(path, toBeReplaced.getChild(0));
            } else {
                transformedFormula = (TermOrFormula) transformedFormula.transform(path, null);
            }
        }

        return transformedFormula;
    }

    public Set<TreePath> getSubformulaPaths() {
        return this.traverse(new ConstrainedPathCollector<TermOrFormula>(TermOrFormula::isFormula));
    }

    /**
     * checks if formula is in prenex normal form
     *
     * @return true if in prenex normal form
     */
    public boolean isInPrenexNormalForm() {
        if (isQuantifier()) {
            return getChild(1).isInPrenexNormalForm();
        }

        Set<Formula> subformulae = getAllSubformulae();
        for (Formula subformula : subformulae) {
            if (subformula.isQuantifier()) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if formula is in skolem normal form
     *
     * @return true if in skolem normal form
     */
    public boolean isInSkolemNormalForm() {
        if (isExistentialQuantifier()) {
            return false;
        }

        if (isUniversalQuantifier()) {
            return getChild(1).isInSkolemNormalForm();
        }

        Set<Formula> subformulae = getAllSubformulae();
        for (Formula subformula : subformulae) {
            if (subformula.isQuantifier()) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if formula is in negation normal form
     *
     * @return true if in negation normal form
     */
    public boolean isInNegationNormalForm() {
        Set<Formula> subformulae = getAllSubformulae();
        return subformulae.stream()
                .allMatch(
                        formula ->
                                !formula.isImplication()
                                        && !formula.isEquivalence()
                                        && (!formula.isNegation()
                                                || formula.getChild(0).isRelation()));
    }

    @Override
    public abstract TermOrFormula clone();

    /** for serialization */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("UnnecessaryModifier")
    private static final transient FOFormulaWriterText textWriter = new FOFormulaWriterText();

    @SuppressWarnings("UnnecessaryModifier")
    private static final transient FOFormulaWriterText safeTextWriter =
            new FOFormulaWriterSafeText();

    @SuppressWarnings("UnnecessaryModifier")
    private static final transient FOFormulaWriterText htmlWriter = new FOFormulaWriterHTML();

    @SuppressWarnings("UnnecessaryModifier")
    private static final transient FOFormulaWriterText latexWriter = new FOFormulaWriterLaTeX();

    /** For serialization */
    @SuppressWarnings("unused")
    protected TermOrFormula() {}
}
