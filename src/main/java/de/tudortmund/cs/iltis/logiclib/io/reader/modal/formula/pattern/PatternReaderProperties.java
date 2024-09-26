package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParserOperators;
import de.tudortmund.cs.iltis.utils.StringUtils;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingProperties;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.RegularSymbolSplittingPolicy;
import java.util.stream.Collectors;

/**
 * Properties for modal logical pattern reader.
 *
 * <p>The information in this object and the linked objects have to be consistent to allow for
 * well-defined parsing behaviour.
 */
public class PatternReaderProperties extends CustomizableLexingProperties {

    /** Instances of this class should only be made via the {@link #createDefault()}-methods. */
    private PatternReaderProperties() {}

    private boolean shortcutsAllowed;

    public boolean areShortcutsAllowed() {
        return this.shortcutsAllowed;
    }

    public void setShortcutsAllowed(boolean allowed) {
        this.shortcutsAllowed = allowed;
    }

    @Override
    public ParsableSymbol getSymbolForNonSeparatedText() {
        return PatternParserOperators.OPERATOR_OR_ISYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForISymbols() {
        return PatternParserOperators.ISYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForSeparation() {
        return PatternParserOperators.WS;
    }

    @Override
    public PatternReaderProperties clone() {
        PatternReaderProperties props = new PatternReaderProperties();
        props.addSeparationSymbols(separationSymbols);
        props.addSeparatingOperators(separatingOperators);
        props.addNonSeparatingOperators(nonSeparatingOperators);
        props.setSymbolSplittingPolicy(symbolSplittingPolicy);
        props.addParenthesesSymbols(parenthesesMap, allowedParenthesesTypes);
        props.setShortcutsAllowed(shortcutsAllowed);
        return props;
    }

    private void initOperatorsSafeText() {
        addSeparatingOperator("!", PatternParserOperators.NEG);
        addSeparatingOperator("&", PatternParserOperators.AND);
        addSeparatingOperator("|", PatternParserOperators.OR);
        addSeparatingOperator("<->", PatternParserOperators.EQUIV);
        addSeparatingOperator("<\uFEFF-\uFEFF>", PatternParserOperators.EQUIV);
        addSeparatingOperator("->", PatternParserOperators.IMPLIES);
        addSeparatingOperator("-\uFEFF>", PatternParserOperators.IMPLIES);
        addSeparatingOperator("<-", PatternParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("<\uFEFF-", PatternParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("(", PatternParserOperators.OPAREN);
        addSeparatingOperator(")", PatternParserOperators.CPAREN);
        addSeparatingOperator("[", PatternParserOperators.OBRACKET);
        addSeparatingOperator("]", PatternParserOperators.CBRACKET);
        addSeparatingOperator("{", PatternParserOperators.OBRACE);
        addSeparatingOperator("}", PatternParserOperators.CBRACE);
        addSeparatingOperator("~", PatternParserOperators.COMPLEMENT);
        addSeparatingOperator("...", PatternParserOperators.NOFORMULA);
        addSeparatingOperator("$", PatternParserOperators.ANYFORMULA);
        addSeparatingOperator("#", PatternParserOperators.READ);
        addSeparatingOperator("+", PatternParserOperators.PLUS);
        addSeparatingOperator("*", PatternParserOperators.STAR);
        addSeparatingOperator("@", PatternParserOperators.AT);
        addSeparatingOperator("\"", PatternParserOperators.SQUOTE);
        addSeparatingOperator("[]", PatternParserOperators.DISTINCT);
        addSeparatingOperator("||", PatternParserOperators.MULTI);
        addNonSeparatingOperator("box", PatternParserOperators.BOX);
        addNonSeparatingOperator("diamond", PatternParserOperators.DIAMOND);
        addNonSeparatingOperator("contains", PatternParserOperators.CONTAINS);
        addNonSeparatingOperator("1", PatternParserOperators.TRUE);
        addNonSeparatingOperator("0", PatternParserOperators.FALSE);
        addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                PatternParserOperators.OPAREN,
                PatternParserOperators.CPAREN);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                PatternParserOperators.OBRACKET,
                PatternParserOperators.CBRACKET);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                PatternParserOperators.OBRACE,
                PatternParserOperators.CBRACE);
        addSeparationSymbols(
                StringUtils.getUnicodeNonLineBreakingWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    private void initOperatorsUnicode() {
        addSeparatingOperator("\u00AC", PatternParserOperators.NEG);
        addSeparatingOperator("\u2227", PatternParserOperators.AND);
        addSeparatingOperator("\u2228", PatternParserOperators.OR);
        addSeparatingOperator("\u2194", PatternParserOperators.EQUIV);
        addSeparatingOperator("\u2192", PatternParserOperators.IMPLIES);
        addSeparatingOperator("<-", PatternParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("<\uFEFF-", PatternParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("(", PatternParserOperators.OPAREN);
        addSeparatingOperator(")", PatternParserOperators.CPAREN);
        addSeparatingOperator("[", PatternParserOperators.OBRACKET);
        addSeparatingOperator("]", PatternParserOperators.CBRACKET);
        addSeparatingOperator("{", PatternParserOperators.OBRACE);
        addSeparatingOperator("}", PatternParserOperators.CBRACE);
        addSeparatingOperator("~", PatternParserOperators.COMPLEMENT);
        addSeparatingOperator("...", PatternParserOperators.NOFORMULA);
        addSeparatingOperator("$", PatternParserOperators.ANYFORMULA);
        addSeparatingOperator("#", PatternParserOperators.READ);
        addSeparatingOperator("+", PatternParserOperators.PLUS);
        addSeparatingOperator("*", PatternParserOperators.STAR);
        addSeparatingOperator("@", PatternParserOperators.AT);
        addSeparatingOperator("[]", PatternParserOperators.DISTINCT);
        addSeparatingOperator("\"", PatternParserOperators.SQUOTE);
        addSeparatingOperator("\u01C1", PatternParserOperators.MULTI);
        addSeparatingOperator("\u2610", PatternParserOperators.BOX);
        addSeparatingOperator("\u25A1", PatternParserOperators.BOX);
        addSeparatingOperator("\u25FB", PatternParserOperators.BOX);
        addSeparatingOperator("\u25C7", PatternParserOperators.DIAMOND);
        addSeparatingOperator("\u2282", PatternParserOperators.CONTAINS);
        addSeparatingOperator("\u22A4", PatternParserOperators.TRUE);
        addSeparatingOperator("\u22A5", PatternParserOperators.FALSE);
        addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                PatternParserOperators.OPAREN,
                PatternParserOperators.CPAREN);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                PatternParserOperators.OBRACKET,
                PatternParserOperators.CBRACKET);
        addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                PatternParserOperators.OBRACE,
                PatternParserOperators.CBRACE);
        addSeparationSymbols(
                StringUtils.getUnicodeNonLineBreakingWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    /**
     * Creates a properties object with the "usual" operators as unicode characters and as (safe)
     * text characters. {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PatternReaderProperties createDefault() {
        return createDefault(true, true, true);
    }

    /**
     * Creates a properties object with the "usual" operators as unicode characters and/or as (safe)
     * text characters, as given. Infix symbols are set as given. Brackets are allowed as given.
     * {@link RegularSymbolSplittingPolicy#UNARY_NAME_AND_INDEX_POLICY} is used.
     */
    public static PatternReaderProperties createDefault(
            boolean lexSafeText, boolean lexUnicode, boolean shortcutsAllowed) {

        PatternReaderProperties props = new PatternReaderProperties();
        props.setShortcutsAllowed(shortcutsAllowed);

        if (lexSafeText) props.initOperatorsSafeText();

        if (lexUnicode) props.initOperatorsUnicode();

        props.setSymbolSplittingPolicy(RegularSymbolSplittingPolicy.UNARY_NAME_AND_INDEX_POLICY);

        return props;
    }
}
