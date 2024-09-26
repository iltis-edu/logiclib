package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralPatternFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternCreator;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParser.InitFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern.PatternParserOperators;
import de.tudortmund.cs.iltis.logiclib.io.reader.general.GeneralPatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Objects;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical patterns.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralPatternFaultReason} (all), {@link GeneralFormulaFaultReason} (all)
 */
public class PatternReader
        extends GeneralPatternReader<
                TreePattern<ModalFormula>, TreePattern<ModalFormula>, PatternParser> {

    public PatternReader() {
        this(PatternReaderProperties.createDefault());
    }

    public PatternReader(CustomizableLexingPropertiesProvidable properties) {
        super(properties);
    }

    public PatternReader(boolean lexSafeText, boolean lexUnicode, boolean shortcutsAllowed) {

        this(PatternReaderProperties.createDefault(lexSafeText, lexUnicode, shortcutsAllowed));
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
    }

    protected PatternReaderProperties getProperties() {
        return (PatternReaderProperties) properties;
    }

    @Override
    protected PatternParser prepareParser(TokenStream tokenStream) {
        // create a parser that feeds off the tokens buffer
        return new PatternParser(tokenStream);
    }

    @Override
    protected TreePattern<ModalFormula> executeParser(
            PatternParser parser, AbstractParseTreeVisitor<TreePattern<ModalFormula>> visitor) {

        InitFormulaContext ctx = parser.initFormula();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected AbstractParseTreeVisitor<TreePattern<ModalFormula>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {

        return new PatternParserConstructionVisitor(creator, getProperties(), errorHandler);
    }

    @Override
    protected ParsingFaultTypeMapping<TreePattern<ModalFormula>> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<TreePattern<ModalFormula>> mapping) {

        return mapping;
    }

    @Override
    public TreePattern<ModalFormula> read(Object input) throws IncorrectParseInputException {

        Objects.requireNonNull(input);

        ParsingFaultTypeMapping<TreePattern<ModalFormula>> mapping =
                parseInput(input, new PatternCreator());

        ParsingFaultTypeMapping<TreePattern<ModalFormula>> outputMapping =
                convertParserOutputToReaderOutput(mapping);

        return returnResult(outputMapping);
    }
}
