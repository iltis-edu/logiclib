package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.ExistentialQuantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Implication;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.UniversalQuantifier;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import java.util.ArrayList;
import java.util.List;

/** Creator for predicate logical formulae, using {@link FormulaType}. Used for parsing. */
public class FormulaCreator extends ParsingCreator {

    /** Registers a creator function for each type of {@link FormulaType}. */
    public FormulaCreator(SignatureCheckable signature) {
        registerConstant(FormulaType.TRUE, True::new);
        registerConstant(FormulaType.FALSE, False::new);
        registerUnaryFunction(FormulaType.NEG, formula -> new Negation(toNonNullFormula(formula)));
        registerUnaryFunctionWithName(
                FormulaType.EXISTS,
                (var, formula) ->
                        new ExistentialQuantifier(new Variable(var), toNonNullFormula(formula)));
        registerUnaryFunctionWithName(
                FormulaType.FORALL,
                (var, formula) ->
                        new UniversalQuantifier(new Variable(var), toNonNullFormula(formula)));
        registerBinaryFunction(
                FormulaType.IMPLIES,
                (premise, conclusion) ->
                        new Implication(toNonNullFormula(premise), toNonNullFormula(conclusion)));
        registerBinaryFunction(
                FormulaType.EQUIV,
                (left, right) -> new Equivalence(toNonNullFormula(left), toNonNullFormula(right)));
        registerVararyFunction(
                FormulaType.AND, formulae -> new Conjunction(toNonNullFormulae(formulae)));
        registerVararyFunction(
                FormulaType.OR, formulae -> new Disjunction(toNonNullFormulae(formulae)));
        registerConstantWithName(FormulaType.PREFIX_RELATION_ATOM, RelationAtom::new);
        registerConstantWithName(FormulaType.CONSTANT, FunctionTerm::new);
        registerConstantWithName(
                FormulaType.VARIABLE,
                // !!! Can be EITHER a variable OR a constant !!!
                // For all ambiguous symbols (which are present in the signature)
                // decide for usage of function term (as constant)
                name ->
                        signature.containsSymbolAsFunction(name)
                                        || signature.containsSymbolAsRelation(name)
                                ? new FunctionTerm(name)
                                : new Variable(name));
        registerVararyFunctionWithName(
                FormulaType.PREFIX_RELATION_ATOM,
                (name, terms) -> new RelationAtom(name, false, toNonNullTerms(terms)));
        registerVararyFunctionWithName(
                FormulaType.PREFIX_FUNCTION_TERM,
                (name, terms) -> new FunctionTerm(name, false, toNonNullTerms(terms)));
        registerBinaryFunctionWithName(
                FormulaType.INFIX_RELATION_ATOM,
                (name, left, right) ->
                        new RelationAtom(name, true, toNonNullTerm(left), toNonNullTerm(right)));
        registerBinaryFunctionWithName(
                FormulaType.INFIX_FUNCTION_TERM,
                (name, left, right) ->
                        new FunctionTerm(name, true, toNonNullTerm(left), toNonNullTerm(right)));
    }

    /**
     * If error recovery is happening, some parts of rules can be skipped. Therefore, null values
     * are possible. If error recovery is not happening (bailing out), no null values are possible.
     */
    private Formula toNonNullFormula(Object o) {
        return o == null ? new RelationAtom("< unknown formula >") : (Formula) o;
    }

    /**
     * If error recovery is happening, some parts of rules can be skipped. Therefore, null values
     * are possible. If error recovery is not happening (bailing out), no null values are possible.
     */
    private Term toNonNullTerm(Object o) {
        return o == null ? new FunctionTerm("< unknown term >") : (Term) o;
    }

    /**
     * If error recovery is happening, some parts of rules can be skipped. Therefore, null values
     * are possible. If error recovery is not happening (bailing out), no null values are possible.
     */
    @SuppressWarnings("unchecked")
    private Iterable<Formula> toNonNullFormulae(Object o) {
        List<Formula> result = (List<Formula>) o;

        if (o == null) {
            result = new ArrayList<>(1);
            result.add(toNonNullFormula(null));
        } else {
            result.replaceAll(el -> el != null ? el : toNonNullFormula(el));
        }

        return result;
    }

    /**
     * If error recovery is happening, some parts of rules can be skipped. Therefore, null values
     * are possible. If error recovery is not happening (bailing out), no null values are possible.
     */
    @SuppressWarnings("unchecked")
    private Iterable<Term> toNonNullTerms(Object o) {
        List<Term> result = (List<Term>) o;

        if (o == null) {
            result = new ArrayList<>(1);
            result.add(toNonNullTerm(null));
        } else {
            result.replaceAll(el -> el != null ? el : toNonNullTerm(el));
        }

        return result;
    }

    /**
     * Registers a creator function for each type of {@link FormulaType}. Due to the lack of a
     * signature, all constant symbols will be interpreted as variables (because constants and
     * variables cannot be differentiated without a signature).
     */
    public FormulaCreator() {
        this(new Signature());
    }
}
