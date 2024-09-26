package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Uses the {@link ModalFormulaSetParser} to construct a {@link ModalFormula} from the input.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all)
 */
public class ModalFormulaReader
        extends GeneralModalReader<ModalFormula, ModalFormula, ModalFormulaSetParser> {

    /** {@inheritDoc} */
    public ModalFormulaReader(ModalReaderProperties props) {
        super(props, new RepairingParenthesesChecker(props));
    }

    @Override
    protected ModalFormulaSetParser prepareParser(TokenStream tokenStream) {
        return new ModalFormulaSetParser(tokenStream);
    }

    @Override
    protected AbstractParseTreeVisitor<ModalFormula> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new ModalFormulaParserConstructionVisitor(errorHandler);
    }

    @Override
    protected ModalFormula executeParser(
            ModalFormulaSetParser parser, AbstractParseTreeVisitor<ModalFormula> visitor) {
        ModalFormulaSetParser.InitModalFormulaContext ctx = parser.initModalFormula();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected ParsingFaultTypeMapping<ModalFormula> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<ModalFormula> mapping) {
        return mapping;
    }
}
