package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralPatternFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternCreator;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParser.InitFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern.PatternParserOperators;
import de.tudortmund.cs.iltis.logiclib.io.reader.general.GeneralPatternReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.InfixPredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.ParenthesesPredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Objects;
import java.util.Set;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical patterns. Uses {@link PatternParser}.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link ParenthesesParsingFaultReason} (all),
 * {@link PredicateFormulaFaultReason} (all), {@link GeneralPatternFaultReason} (all), {@link
 * GeneralFormulaFaultReason} (all)
 */
public class PatternReader
        extends GeneralPatternReader<
                TreePattern<TermOrFormula>, TreePattern<TermOrFormula>, PatternParser> {

    public PatternReader() {
        this(PatternReaderProperties.createDefault());
    }

    public PatternReader(CustomizableLexingPropertiesProvidable properties) {
        super(properties);
    }

    public PatternReader(
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {

        this(
                PatternReaderProperties.createDefault(
                        ParenthesesPredicateReaderProperties.createDefault(
                                allowBracketsAroundFormulae,
                                allowBracketsAroundArguments,
                                allowBracketsAroundTerms)));
    }

    public PatternReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean shortcutsAllowed,
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {

        this(
                PatternReaderProperties.createDefault(
                        lexSafeText,
                        lexUnicode,
                        shortcutsAllowed,
                        ParenthesesPredicateReaderProperties.createDefault(
                                allowBracketsAroundFormulae,
                                allowBracketsAroundArguments,
                                allowBracketsAroundTerms),
                        InfixPredicateReaderProperties.createDefault()));
    }

    public PatternReader(PatternReaderProperties props) {
        super(props, new RepairingParenthesesChecker(props));
        init();
    }

    private void init() {
        registerPostLexConverter(
                token -> {
                    if (token.getType() == PatternParserOperators.REVERSE_IMPLIES.getTokenType())
                        return convertReverseImplicationToken(token);
                    return null;
                });
        // infix symbols need to have SymbolTokens for the parser to work with
        registerPostLexConverter(
                token -> {
                    if (token.getType() == PatternParserOperators.RELINFIXISYMBOL.getTokenType()
                            || token.getType()
                                    == PatternParserOperators.FUNINFIXISYMBOL.getTokenType())
                        return splitIntoISYMBOLs(token, token.getType());
                    return null;
                });
    }

    public TreePattern<TermOrFormula> read(
            Object input, SignatureCheckable parsingSig, SignatureCheckable targetSig)
            throws IncorrectParseInputException {

        Objects.requireNonNull(input);
        Objects.requireNonNull(targetSig);
        Objects.requireNonNull(parsingSig);

        ParsingFaultTypeMapping<TreePattern<TermOrFormula>> mapping =
                parseInput(input, new PatternCreator(parsingSig));

        ParsingFaultTypeMapping<TreePattern<TermOrFormula>> outputMapping =
                convertParserOutputToReaderOutput(mapping);

        return returnResult(outputMapping);
    }

    public TreePattern<TermOrFormula> read(Object input, SignatureCheckable targetSig)
            throws IncorrectParseInputException {

        return read(input, targetSig, targetSig);
    }

    @Override
    public TreePattern<TermOrFormula> read(Object input) throws IncorrectParseInputException {

        Objects.requireNonNull(input);

        ParsingFaultTypeMapping<TreePattern<TermOrFormula>> mapping =
                parseInput(input, new PatternCreator());

        ParsingFaultTypeMapping<TreePattern<TermOrFormula>> outputMapping =
                convertParserOutputToReaderOutput(mapping);

        return returnResult(outputMapping);
    }

    protected PatternReaderProperties getProperties() {
        return (PatternReaderProperties) properties;
    }

    public static boolean arePropertiesSupported(PatternReaderProperties properties) {
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

    @Override
    protected PatternParser prepareParser(TokenStream tokenStream) {
        // create a parser that feeds off the tokens buffer
        PatternParser parser = new PatternParser(tokenStream);
        parser.setProperties(getProperties());
        return parser;
    }

    @Override
    protected TreePattern<TermOrFormula> executeParser(
            PatternParser parser, AbstractParseTreeVisitor<TreePattern<TermOrFormula>> visitor) {

        InitFormulaContext ctx = parser.initFormula();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected AbstractParseTreeVisitor<TreePattern<TermOrFormula>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {

        return new PatternParserConstructionVisitor(creator, getProperties(), errorHandler);
    }

    @Override
    protected ParsingFaultTypeMapping<TreePattern<TermOrFormula>> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<TreePattern<TermOrFormula>> mapping) {

        return mapping;
    }
}
