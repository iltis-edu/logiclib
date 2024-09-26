package de.tudortmund.cs.iltis.logiclib.io.reader.relation;

import de.tudortmund.cs.iltis.logiclib.io.parser.relation.SetRelationParserOperators;
import de.tudortmund.cs.iltis.utils.StringUtils;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingProperties;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.RegularSymbolSplittingPolicy;
import java.io.Serializable;
import java.util.stream.Collectors;

/** A properties object used for the {@link SetRelationReader} to define all tokens needed. */
public class SetRelationReaderProperties extends CustomizableLexingProperties
        implements Serializable {

    private SetRelationReaderProperties() {}

    @Override
    public ParsableSymbol getSymbolForNonSeparatedText() {
        return SetRelationParserOperators.INDEXED_SYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForISymbols() {
        return SetRelationParserOperators.INDEXED_SYMBOL;
    }

    @Override
    public ParsableSymbol getSymbolForSeparation() {
        return SetRelationParserOperators.WHITESPACE;
    }

    private void addAsciiSymbols(
            String openParentheses, String closedParentheses, String valueSeparator) {
        addSeparatingOperator("{", SetRelationParserOperators.OBRACE);
        addSeparatingOperator("}", SetRelationParserOperators.CBRACE);
        addSeparatingOperator(openParentheses, SetRelationParserOperators.OPAREN);
        addSeparatingOperator(closedParentheses, SetRelationParserOperators.CPAREN);
        addSeparatingOperator(valueSeparator, SetRelationParserOperators.COMMA);

        addSeparationSymbols(" ", "\t", "\n");
    }

    private void addUnicodeSymbols() {
        addSeparationSymbols(
                StringUtils.getUnicodeWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    public static SetRelationReaderProperties createDefault() {
        return createDefault("(", ")", ",");
    }

    public static SetRelationReaderProperties createDefault(
            String openParentheses, String closedParentheses, String valueSeparator) {
        SetRelationReaderProperties props = new SetRelationReaderProperties();

        props.setSymbolSplittingPolicy(RegularSymbolSplittingPolicy.UNARY_NAME_AND_INDEX_POLICY);
        props.addAsciiSymbols(openParentheses, closedParentheses, valueSeparator);
        props.addUnicodeSymbols();

        props.addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                SetRelationParserOperators.OBRACE,
                SetRelationParserOperators.CBRACE);
        props.addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                SetRelationParserOperators.OPAREN,
                SetRelationParserOperators.CPAREN);

        return props;
    }

    @Override
    public CustomizableLexingPropertiesProvidable clone() {
        SetRelationReaderProperties props = new SetRelationReaderProperties();
        props.addSeparationSymbols(separationSymbols);
        props.addSeparatingOperators(separatingOperators);
        props.addNonSeparatingOperators(nonSeparatingOperators);
        props.setSymbolSplittingPolicy(symbolSplittingPolicy);
        props.addParenthesesSymbols(parenthesesMap, allowedParenthesesTypes);
        return props;
    }
}
