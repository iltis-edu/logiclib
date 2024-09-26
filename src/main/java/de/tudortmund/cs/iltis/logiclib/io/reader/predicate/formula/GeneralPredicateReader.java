package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaCreator;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaParserOperators;
import de.tudortmund.cs.iltis.logiclib.io.reader.general.GeneralFormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.VariableNamesFaultCollection;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import de.tudortmund.cs.iltis.utils.collections.TypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import java.util.Objects;
import java.util.Set;

/**
 * Reader for predicate logical formula-like objects.
 *
 * @param <ParserOutputT> the type the parsing process returns
 * @param <ReaderOutputT> the type the {@link #read(Object)}-method returns
 * @param <ParserT> the type of parser used for parsing
 * @param <FormulaT> the type of the underlying formula; e.g. <code>Formula</code> if ParserOutputT
 *     is <code>List&lt;Formula></code>
 */
public abstract class GeneralPredicateReader<
                ParserOutputT,
                ReaderOutputT,
                FormulaT extends TermOrFormula,
                ParserT extends AbstractParser>
        extends GeneralFormulaReader<ParserOutputT, ReaderOutputT, ParserT> {

    /**
     * Creates a new {@link GeneralPredicateReader} which lexes SafeText and Unicode and allows
     * brackets only around formulae.
     */
    public GeneralPredicateReader() {
        this(PredicateReaderProperties.createDefault());
    }

    /** Creates a new {@link GeneralPredicateReader} which lexes SafeText and Unicode. */
    public GeneralPredicateReader(
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        this(
                PredicateReaderProperties.createDefault(
                        ParenthesesPredicateReaderProperties.createDefault(
                                allowBracketsAroundFormulae,
                                allowBracketsAroundArguments,
                                allowBracketsAroundTerms)));
    }

    /** Creates a new {@link GeneralPredicateReader}. */
    public GeneralPredicateReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        this(
                PredicateReaderProperties.createDefault(
                        lexSafeText,
                        lexUnicode,
                        ParenthesesPredicateReaderProperties.createDefault(
                                allowBracketsAroundFormulae,
                                allowBracketsAroundArguments,
                                allowBracketsAroundTerms),
                        InfixPredicateReaderProperties.createDefault()));
    }

    /** Creates a new {@link GeneralPredicateReader}. */
    public GeneralPredicateReader(PredicateReaderProperties props) {
        super(props, new RepairingParenthesesChecker(props));
        init();
    }

    private void init() {
        registerPostLexConverter(
                token -> {
                    if (token.getType() == FormulaParserOperators.REVERSE_IMPLIES.getTokenType())
                        return convertReverseImplicationToken(token);
                    return null;
                });
        // infix symbols need to have SymbolTokens for the parser to work with
        registerPostLexConverter(
                token -> {
                    if (token.getType() == FormulaParserOperators.RELINFIXISYMBOL.getTokenType()
                            || token.getType()
                                    == FormulaParserOperators.FUNINFIXISYMBOL.getTokenType())
                        return splitIntoISYMBOLs(token, token.getType());
                    return null;
                });
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing using {@code parsingSig} (to differ between constant and variable
     * symbols), it is checked, whether the formula-like object is compatible to {@code targetSig}
     * and contains only free variables of the set {@code freeVarsAllowed}.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     incompatible to given signature or disallowed free variables appear. The following
     *     collection fault types may be set in the {@link ParsingFaultTypeMapping}-object contained
     *     in the thrown {@link IncorrectParseInputException}-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link SubsetFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *       <li>{@link FreeVariablesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    public ReaderOutputT read(
            Object input,
            SignatureCheckable parsingSig,
            SignatureCheckable targetSig,
            Set<VariableSymbol> freeVarsAllowed)
            throws IncorrectParseInputException {
        Objects.requireNonNull(input);
        Objects.requireNonNull(targetSig);
        Objects.requireNonNull(parsingSig);
        Objects.requireNonNull(freeVarsAllowed);

        ParsingFaultTypeMapping<ParserOutputT> mapping =
                parseInput(input, new FormulaCreator(parsingSig));
        mapping = checkForCompatibilityTo(targetSig, mapping);
        mapping = checkForFreeVariables(freeVarsAllowed, mapping);

        ParsingFaultTypeMapping<ReaderOutputT> outputMapping =
                convertParserOutputToReaderOutput(mapping);
        return returnResult(outputMapping);
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing, using {@code targetSig} (to differ between constant and variable
     * symbols), it is checked, whether the formula-like object is compatible to {@code targetSig}
     * and contains only free variables of the set {@code freeVarsAllowed}.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     incompatible to given signature or disallowed free variables appear. The following
     *     collection fault types may be set in the {@link ParsingFaultTypeMapping}-object contained
     *     in the thrown {@link IncorrectParseInputException}-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link SubsetFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *       <li>{@link FreeVariablesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    public ReaderOutputT read(
            Object input, SignatureCheckable targetSig, Set<VariableSymbol> freeVarsAllowed)
            throws IncorrectParseInputException {
        return read(input, targetSig, targetSig, freeVarsAllowed);
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing, using {@code parsingSig} (to differ between constant and variable
     * symbols), it is checked, whether the formula-like object is compatible to {@code targetSig}.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     incompatible to given signature. The following collection fault types may be set in the
     *     fault mapping contained in the thrown IncorrectParseInputException-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link SubsetFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    public ReaderOutputT read(
            Object input, SignatureCheckable parsingSig, SignatureCheckable targetSig)
            throws IncorrectParseInputException {
        Objects.requireNonNull(input);
        Objects.requireNonNull(targetSig);
        Objects.requireNonNull(parsingSig);

        ParsingFaultTypeMapping<ParserOutputT> mapping =
                parseInput(input, new FormulaCreator(parsingSig));
        mapping = checkForCompatibilityTo(targetSig, mapping);

        ParsingFaultTypeMapping<ReaderOutputT> outputMapping =
                convertParserOutputToReaderOutput(mapping);
        return returnResult(outputMapping);
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing, using {@code targetSig} (to differ between constant and variable
     * symbols), it is checked, whether the formula-like object is compatible to {@code targetSig}.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     incompatible to given signature. The following collection fault types may be set in the
     *     fault mapping contained in the thrown IncorrectParseInputException-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link SubsetFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    public ReaderOutputT read(Object input, SignatureCheckable targetSig)
            throws IncorrectParseInputException {
        return read(input, targetSig, targetSig);
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing, it is checked, whether the formula-like object is valid and contains
     * only free variables of the set {@code freeVarsAllowed}.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     not valid or disallowed free variables appear. The following collection fault types may
     *     be set in the fault mapping contained in the thrown IncorrectParseInputException-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *       <li>{@link FreeVariablesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    public ReaderOutputT read(Object input, Set<VariableSymbol> freeVarsAllowed)
            throws IncorrectParseInputException {
        Objects.requireNonNull(input);
        Objects.requireNonNull(freeVarsAllowed);

        ParsingFaultTypeMapping<ParserOutputT> mapping = parseInput(input, new FormulaCreator());
        mapping = checkForValidity(mapping);
        mapping = checkForFreeVariables(freeVarsAllowed, mapping);

        ParsingFaultTypeMapping<ReaderOutputT> outputMapping =
                convertParserOutputToReaderOutput(mapping);
        return returnResult(outputMapping);
    }

    /**
     * Tries to read the predicate logical formula-like object given in {@code input}. After
     * successfully parsing, it is checked, whether the formula-like object is valid.
     *
     * @return the formula-like object, iff parsing was successful
     * @throws IncorrectParseInputException if parsing was unsuccessful or formula-like object is
     *     not valid. The following collection fault types may be set in the fault mapping contained
     *     in the thrown IncorrectParseInputException-object.
     *     <ul>
     *       <li>{@link ParsingFaultCollection}
     *       <li>{@link ValidityFaultCollection}
     *       <li>{@link VariableNamesFaultCollection}
     *     </ul>
     *     Note: If an error is encountered during parsing, a fault is created and the attempt is
     *     made to "correct" the entered formula. Especially for long formulae however, these
     *     corrections might make more damage than they repair. In other words: if these corrections
     *     are not accurate, misleading faults concerning the signature or variables might be
     *     created. As a consequence, if a {@link ParsingFaultCollection}-object and a fault
     *     collection of one of the other types is mapped, you might prefer the {@link
     *     ParsingFaultCollection}-object, as the errors therein cannot be caused by (for this
     *     particular formula) misleading corrections.
     */
    @Override
    public ReaderOutputT read(Object input) throws IncorrectParseInputException {
        Objects.requireNonNull(input);

        ParsingFaultTypeMapping<ParserOutputT> mapping = parseInput(input, new FormulaCreator());
        mapping = checkForValidity(mapping);

        ParsingFaultTypeMapping<ReaderOutputT> outputMapping =
                convertParserOutputToReaderOutput(mapping);
        return returnResult(outputMapping);
    }

    /**
     * Checks, if the given formula object in the mapping object contains only the given variables
     * and add faults to the given fault mapping object otherwise.
     *
     * @param mapping the mapping, not null
     * @param vars the variable set, not null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForFreeVariables(
            Set<VariableSymbol> vars, ParsingFaultTypeMapping<ParserOutputT> mapping) {
        return checkForFreeVariables(vars, mapping, convertToFormula(mapping.getOutput()));
    }

    /**
     * Checks, if the given result contains only the given variables and add faults to the given
     * fault mapping object otherwise.
     *
     * @param mapping the mapping, not null
     * @param vars the variable set, not null
     * @param result the result, possibly null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForFreeVariables(
            Set<VariableSymbol> vars,
            ParsingFaultTypeMapping<ParserOutputT> mapping,
            FormulaT result) {
        if (result != null) {
            FreeVariablesFaultCollection freeVarsFaultCol =
                    result.areFreeVariablesOutOfElseGetFaults(vars);
            if (freeVarsFaultCol.containsAnyFault()) mapping = mapping.with(freeVarsFaultCol);
        }
        return mapping;
    }

    /**
     * Checks, if the given formula object in the mapping object is compatible to the given
     * signature (checkable) in the sense of {@link
     * TermOrFormula#isCompatibleTo(SignatureCheckable)} and add faults to the given fault mapping
     * object otherwise.
     *
     * @param mapping the mapping, not null
     * @param targetSig the signature (checkable), not null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForCompatibilityTo(
            SignatureCheckable targetSig, ParsingFaultTypeMapping<ParserOutputT> mapping) {
        return checkForCompatibilityTo(targetSig, mapping, convertToFormula(mapping.getOutput()));
    }

    /**
     * Checks, if the given result is compatible to the given signature (checkable) in the sense of
     * {@link TermOrFormula#isCompatibleTo(SignatureCheckable)} and add faults to the given fault
     * mapping object otherwise.
     *
     * @param mapping the mapping, not null
     * @param targetSig the signature (checkable), not null
     * @param result the result, possibly null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForCompatibilityTo(
            SignatureCheckable targetSig,
            ParsingFaultTypeMapping<ParserOutputT> mapping,
            FormulaT result) {
        if (result != null) {
            TypeMapping<FaultCollection<?, ?>> compMapping =
                    result.isCompatibleToElseGetFaults(targetSig);
            if (compMapping.containsAny()) mapping = mapping.with(compMapping);
        }
        return mapping;
    }

    /**
     * Checks, if the given formula object in the mapping object is valid in sense of {@link
     * TermOrFormula#isValid()} and add faults to the given fault mapping object otherwise.
     *
     * @param mapping the mapping, not null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForValidity(
            ParsingFaultTypeMapping<ParserOutputT> mapping) {
        return checkForValidity(mapping, convertToFormula(mapping.getOutput()));
    }

    /**
     * Checks, if the given result is valid in sense of {@link TermOrFormula#isValid()} and add
     * faults to the given fault mapping object otherwise.
     *
     * @param mapping the mapping, not null
     * @param result the result, possibly null
     * @return the possibly extended mapping, not null
     */
    protected ParsingFaultTypeMapping<ParserOutputT> checkForValidity(
            ParsingFaultTypeMapping<ParserOutputT> mapping, FormulaT result) {
        if (result != null) {
            TypeMapping<FaultCollection<?, ?>> validMapping = result.isValidElseGetFaults();
            if (validMapping.containsAny()) mapping = mapping.with(validMapping);
        }
        return mapping;
    }

    /** Converts the given output object to a formula for validity checks. */
    protected abstract FormulaT convertToFormula(ParserOutputT output);

    /** Converts the properties attribute to {@link PredicateReaderProperties}. */
    protected PredicateReaderProperties getProperties() {
        return (PredicateReaderProperties) properties;
    }

    /**
     * Checks whether a) the given properties object is consistent and b) whether all parentheses
     * types set in {@link PredicateReaderProperties#parenProps} are supported by this parser (i.e.
     * if they are {@link ParenthesesType#PARENTHESES} or {@link ParenthesesType#BRACKETS}).
     */
    public static boolean arePropertiesSupported(PredicateReaderProperties properties) {
        if (!properties.isConsistent()) return false;
        Set<ParenthesesType> inconSet =
                properties.getParenthesesPredicateProperties().getParensAllowedAroundArguments();
        inconSet.addAll(
                properties.getParenthesesPredicateProperties().getParensAllowedAroundFormulae());
        inconSet.addAll(
                properties.getParenthesesPredicateProperties().getParensAllowedAroundTerms());
        inconSet.remove(ParenthesesType.PARENTHESES);
        inconSet.remove(ParenthesesType.BRACKETS);
        return inconSet.isEmpty();
    }
}
