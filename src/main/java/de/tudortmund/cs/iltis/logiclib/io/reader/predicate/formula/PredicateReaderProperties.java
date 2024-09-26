package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaParserOperators;
import de.tudortmund.cs.iltis.utils.StringUtils;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingProperties;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.RegularSymbolSplittingPolicy;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Properties for Predicate logical formula (set) reader. For the moment, only parentheses and
 * brackets are supported around formulae, terms, and arguments of relation/function symbols (though
 * others can be set here, as well).
 *
 * <p>Uses {@link InfixPredicateReaderProperties} and {@link ParenthesesPredicateReaderProperties}.
 *
 * <p>The information in this object and the linked objects have to be consistent to allow for
 * well-defined parsing behaviour. You can use {@link #isConsistent()} to check whether the
 * information stored in this property object are consistent.
 */
public class PredicateReaderProperties extends CustomizableLexingProperties {

    /** Instances of this class should only be made via the {@link #createDefault()}-methods. */
    protected PredicateReaderProperties() {}

    // -----------
    // PARENTHESES
    // -----------

    /**
     * object storing all information of allowed parentheses around formulae, terms, and
     * relation/function symbols
     */
    protected ParenthesesPredicateReaderProperties parenProps =
            ParenthesesPredicateReaderProperties.createDefault();

    /**
     * @see #parenProps
     */
    public ParenthesesPredicateReaderProperties getParenthesesPredicateProperties() {
        return parenProps;
    }

    /**
     * @see #parenProps
     */
    public void setParenthesesPredicateProperties(ParenthesesPredicateReaderProperties parenProps) {
        if (parenProps == null) throw new IllegalArgumentException("argument may not be null");
        this.parenProps = parenProps;
    }

    // -------------
    // INFIX SYMBOLS
    // -------------

    /** object storing all information of allowed infix relation/function symbols */
    protected InfixPredicateReaderProperties infixProps =
            InfixPredicateReaderProperties.createDefault();

    /**
     * @see #infixProps
     */
    public InfixPredicateReaderProperties getInfixProperties() {
        return infixProps;
    }

    /**
     * @see #infixProps
     */
    public void setInfixProperties(InfixPredicateReaderProperties infixProps) {
        if (infixProps == null) throw new IllegalArgumentException("argument may not be null");
        this.infixProps = infixProps;
    }

    @Override
    public ParsableSymbol getSymbolForNonSeparatedText() {
        return FormulaParserOperators.OPERATOR_OR_ISYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForISymbols() {
        return FormulaParserOperators.ISYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForSeparation() {
        return FormulaParserOperators.WS;
    }

    /**
     * Returns a new map which is the result of merging the separation symbols, the separating
     * operators and the infix symbols, ordered descending according to symbol length.
     */
    @Override
    public Map<String, ParsableSymbol> getSeparatingAndSeparationSymbols() {
        Map<String, ParsableSymbol> result = new TreeMap<>(lengthComparator);
        result.putAll(
                infixProps.getInfixRelationSymbols().stream()
                        .collect(Collectors.toMap(s -> s, s -> getRelInfixSymbol())));
        result.putAll(
                infixProps.getInfixFunctionSymbols().stream()
                        .collect(Collectors.toMap(s -> s, s -> getFunInfixSymbol())));
        result.putAll(separatingOperators);
        result.putAll(
                separationSymbols.stream()
                        .collect(Collectors.toMap(s -> s, s -> getSymbolForSeparation())));
        return result;
    }

    protected ParsableSymbol getRelInfixSymbol() {
        return FormulaParserOperators.RELINFIXISYMBOL;
    }

    protected ParsableSymbol getFunInfixSymbol() {
        return FormulaParserOperators.FUNINFIXISYMBOL;
    }

    /**
     * Returns the set of infix symbols which are not recognizable by the set symbol splitting
     * policy.
     *
     * <p>You can adapt {@link #symbolSplittingPolicy} with {@link
     * RegularSymbolSplittingPolicy#or(RegularSymbolSplittingPolicy)} and {@link
     * RegularSymbolSplittingPolicy#CREATE_OF_SYMBOLS(java.util.Collection)} to support the returned
     * symbols.
     */
    public Set<String> calcInfixSymbolsInconsistentWithPolicy() {
        Set<String> infixSet = infixProps.getInfixRelationSymbols();
        infixSet.addAll(infixProps.getInfixFunctionSymbols());
        return infixSet.stream()
                .filter(infixSym -> !symbolSplittingPolicy.isSingleSymbol(infixSym))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the set of infix symbols which appear as separation symbols, separating or
     * non-separating operator as well.
     */
    public Set<String> calcInfixSymbolsInconsistentWithOperatorsAndSeparation() {
        Set<String> infixSet = infixProps.getInfixRelationSymbols();
        infixSet.addAll(infixProps.getInfixFunctionSymbols());
        Set<String> inconSet = getSeparationSymbols();
        inconSet.addAll(getSeparatingOperators().keySet());
        inconSet.addAll(getNonSeparatingOperators().keySet());
        inconSet.retainAll(infixSet);
        return inconSet;
    }

    /**
     * Returns the set of parentheses types that are allowed in {@link #parenProps} but not in
     * {@link #parenthesesMap}.
     */
    public Set<ParenthesesType> calcPredicateParenTypesInconsistentWithAllowedParenTypes() {
        Set<ParenthesesType> inconSet = parenProps.getParensAllowedAroundArguments();
        inconSet.addAll(parenProps.getParensAllowedAroundFormulae());
        inconSet.addAll(parenProps.getParensAllowedAroundTerms());
        inconSet.removeAll(allowedParenthesesTypes);
        return inconSet;
    }

    /** Checks whether this property object is consistent and therefore useful for parsing. */
    public boolean isConsistent() {
        return calcInfixSymbolsInconsistentWithOperatorsAndSeparation().isEmpty()
                && calcInfixSymbolsInconsistentWithPolicy().isEmpty()
                && calcPredicateParenTypesInconsistentWithAllowedParenTypes().isEmpty();
    }

    @Override
    public PredicateReaderProperties clone() {
        PredicateReaderProperties props = new PredicateReaderProperties();
        props.setInfixProperties(infixProps.clone());
        props.setParenthesesPredicateProperties(parenProps.clone());
        props.addSeparationSymbols(separationSymbols);
        props.addSeparatingOperators(separatingOperators);
        props.addNonSeparatingOperators(nonSeparatingOperators);
        props.setSymbolSplittingPolicy(symbolSplittingPolicy);
        props.addParenthesesSymbols(parenthesesMap, allowedParenthesesTypes);
        return props;
    }

    private void initOperatorsSafeText() {
        addSeparatingOperator("!", FormulaParserOperators.NEG);
        addSeparatingOperator("&", FormulaParserOperators.AND);
        addSeparatingOperator("|", FormulaParserOperators.OR);
        addSeparatingOperator("<->", FormulaParserOperators.EQUIV);
        addSeparatingOperator("<\uFEFF-\uFEFF>", FormulaParserOperators.EQUIV);
        addSeparatingOperator("->", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("-\uFEFF>", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("<-", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("<\uFEFF-", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator(",", FormulaParserOperators.ARGUMENTSEP);
        addSeparatingOperator("(", FormulaParserOperators.OPAREN);
        addSeparatingOperator(")", FormulaParserOperators.CPAREN);
        addSeparatingOperator("[", FormulaParserOperators.OBRACKET);
        addSeparatingOperator("]", FormulaParserOperators.CBRACKET);
        addSeparatingOperator("{", FormulaParserOperators.OBRACE);
        addSeparatingOperator("}", FormulaParserOperators.CBRACE);
        addNonSeparatingOperator("forall", FormulaParserOperators.FORALL);
        addNonSeparatingOperator("exists", FormulaParserOperators.EXISTS);
        addNonSeparatingOperator("1", FormulaParserOperators.TRUE);
        addNonSeparatingOperator("0", FormulaParserOperators.FALSE);
        addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                FormulaParserOperators.OPAREN,
                FormulaParserOperators.CPAREN);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                FormulaParserOperators.OBRACKET,
                FormulaParserOperators.CBRACKET);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                FormulaParserOperators.OBRACE,
                FormulaParserOperators.CBRACE);
        addSeparationSymbols(
                StringUtils.getUnicodeWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    private void initOperatorsUnicode() {
        addSeparatingOperator("\u00AC", FormulaParserOperators.NEG);
        addSeparatingOperator("\u2227", FormulaParserOperators.AND);
        addSeparatingOperator("\u2228", FormulaParserOperators.OR);
        addSeparatingOperator("\u2194", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\u2192", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("<-", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("<\uFEFF-", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator(",", FormulaParserOperators.ARGUMENTSEP);
        addSeparatingOperator("(", FormulaParserOperators.OPAREN);
        addSeparatingOperator(")", FormulaParserOperators.CPAREN);
        addSeparatingOperator("[", FormulaParserOperators.OBRACKET);
        addSeparatingOperator("]", FormulaParserOperators.CBRACKET);
        addSeparatingOperator("{", FormulaParserOperators.OBRACE);
        addSeparatingOperator("}", FormulaParserOperators.CBRACE);
        addSeparatingOperator("\u2200", FormulaParserOperators.FORALL);
        addSeparatingOperator("\u2203", FormulaParserOperators.EXISTS);
        addSeparatingOperator("\u22A4", FormulaParserOperators.TRUE);
        addSeparatingOperator("\u22A5", FormulaParserOperators.FALSE);
        addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                FormulaParserOperators.OPAREN,
                FormulaParserOperators.CPAREN);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                FormulaParserOperators.OBRACKET,
                FormulaParserOperators.CBRACKET);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                FormulaParserOperators.OBRACE,
                FormulaParserOperators.CBRACE);
        addSeparationSymbols(
                StringUtils.getUnicodeWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    private void initOperatorsLatex() {
        addSeparatingOperator("\\neg", FormulaParserOperators.NEG);
        addSeparatingOperator("\\lnot", FormulaParserOperators.NEG);
        addSeparatingOperator("\\wedge", FormulaParserOperators.AND);
        addSeparatingOperator("\\land", FormulaParserOperators.AND);
        addSeparatingOperator("\\vee", FormulaParserOperators.OR);
        addSeparatingOperator("\\lor", FormulaParserOperators.OR);
        addSeparatingOperator("\\leftrightarrow", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\rightleftarrow", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\Leftrightleftarrow", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\Rightleftarrow", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\equiv", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\iff", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\lequiv", FormulaParserOperators.EQUIV);
        addSeparatingOperator("\\to", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\rightarrow", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\Rightarrow", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\supset", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\implies", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\limp", FormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\leftarrow", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("\\subset", FormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator(",", FormulaParserOperators.ARGUMENTSEP);
        String[] openingParenModifiers = {
            "\\big", "\\Big", "\\bigg", "\\Bigg", "\\bigl", "\\Bigl", "\\biggl", "\\Biggl", "\\left"
        };
        String[] closingParenModifiers = {
            "\\big", "\\Big", "\\bigg", "\\Bigg", "\\bigr", "\\Bigr", "\\biggr", "\\Biggr",
            "\\right"
        };
        addModifiedParenthesesSymbols(openingParenModifiers, "(", FormulaParserOperators.OPAREN);
        addModifiedParenthesesSymbols(
                openingParenModifiers,
                new String[] {"\\lbrack", "["},
                FormulaParserOperators.OBRACKET);
        addModifiedParenthesesSymbols(
                openingParenModifiers,
                new String[] {"\\lbrace", "\\{"},
                FormulaParserOperators.OBRACE);
        addModifiedParenthesesSymbols(closingParenModifiers, ")", FormulaParserOperators.CPAREN);
        addModifiedParenthesesSymbols(
                closingParenModifiers,
                new String[] {"\\rbrack", "]"},
                FormulaParserOperators.CBRACKET);
        addModifiedParenthesesSymbols(
                closingParenModifiers,
                new String[] {"\\rbrace", "\\}"},
                FormulaParserOperators.CBRACE);
        addSeparatingOperator("\\forall", FormulaParserOperators.FORALL);
        addSeparatingOperator("\\exists", FormulaParserOperators.EXISTS);
        addSeparatingOperator("\\top", FormulaParserOperators.TRUE);
        addSeparatingOperator("\\bot", FormulaParserOperators.FALSE);
        addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                FormulaParserOperators.OPAREN,
                FormulaParserOperators.CPAREN);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                FormulaParserOperators.OBRACKET,
                FormulaParserOperators.CBRACKET);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                FormulaParserOperators.OBRACE,
                FormulaParserOperators.CBRACE);
        addSeparationSymbols(
                // LaTeX space commands
                "\\ ", "\\;", "\\,", "\\\\");
        addSeparationSymbols(
                StringUtils.getUnicodeWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    private void addModifiedParenthesesSymbols(
            String[] modifiers, String[] symbols, FormulaParserOperators op) {
        for (String symbol : symbols)
            for (String modifier : modifiers) addSeparatingOperator(modifier + symbol, op);
    }

    private void addModifiedParenthesesSymbols(
            String[] modifiers, String symbol, FormulaParserOperators op) {
        addModifiedParenthesesSymbols(modifiers, new String[] {symbol}, op);
    }

    /**
     * Creates a properties object with the "usual" operators as LaTeX symbols, unicode characters
     * and as (safe) text characters. Infix symbols are not restricted and typical infix relation
     * and function symbols are set. Brackets are only allowed around formulae. {@link
     * RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PredicateReaderProperties createDefault() {
        return createDefault(true, true);
    }

    /**
     * Creates a properties object with the "usual" operators as LaTeX symbols and optionally as
     * unicode characters and/or as (safe) text characters. Infix symbols are not restricted and
     * typical infix relation and function symbols are set. Brackets are only allowed around
     * formulae. {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PredicateReaderProperties createDefault(boolean lexSafeText, boolean lexUnicode) {
        return createDefault(
                lexSafeText,
                lexUnicode,
                ParenthesesPredicateReaderProperties.createDefault(),
                InfixPredicateReaderProperties.createDefault());
    }

    /**
     * Creates a properties object with the "usual" operators as LaTeX symbols, unicode characters
     * and as (safe) text characters. Infix symbols are not restricted and typical infix relation
     * and function symbols are set. Brackets are allowed as given. {@link
     * RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PredicateReaderProperties createDefault(
            ParenthesesPredicateReaderProperties parenProps) {
        return createDefault(
                true, true, parenProps, InfixPredicateReaderProperties.createDefault());
    }

    /**
     * Creates a properties object with the "usual" operators as LaTeX symbols, unicode characters
     * and as (safe) text characters. Infix symbols are set as given. Brackets are only allowed
     * around formulae. {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PredicateReaderProperties createDefault(
            InfixPredicateReaderProperties infixProps) {
        return createDefault(
                true, true, ParenthesesPredicateReaderProperties.createDefault(), infixProps);
    }

    /**
     * Creates a properties object with the "usual" operators as LaTeX symbols and optionally as
     * unicode characters and/or as (safe) text characters. Infix symbols are set as given. Brackets
     * are allowed as given. {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is
     * used.
     */
    public static PredicateReaderProperties createDefault(
            boolean lexSafeText,
            boolean lexUnicode,
            ParenthesesPredicateReaderProperties parenProps,
            InfixPredicateReaderProperties infixProps) {
        return createDefault(lexSafeText, lexUnicode, true, parenProps, infixProps);
    }

    /**
     * Creates a properties object with the "usual" operators as unicode characters and/or as (safe)
     * text characters and/or LaTeX symbols, as given. Infix symbols are set as given. Brackets are
     * allowed as given. {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PredicateReaderProperties createDefault(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean lexLatex,
            ParenthesesPredicateReaderProperties parenProps,
            InfixPredicateReaderProperties infixProps) {
        PredicateReaderProperties props = new PredicateReaderProperties();
        props.setInfixProperties(infixProps);
        props.setParenthesesPredicateProperties(parenProps);
        if (lexSafeText) props.initOperatorsSafeText();
        if (lexUnicode) props.initOperatorsUnicode();
        if (lexLatex) props.initOperatorsLatex();
        props.setSymbolSplittingPolicy(RegularSymbolSplittingPolicy.UNARY_NAME_AND_INDEX_POLICY);
        return props;
    }
}
