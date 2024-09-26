package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser.InitFormulaContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical formulae. Uses {@link FormulaSetParser}.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link PredicateFormulaFaultReason} (all)
 */
public class FormulaReader extends TermOrFormulaReader<ParsablyTyped, Formula> {

    /**
     * Creates a new FormulaReader which lexes SafeText and Unicode and allows brackets only around
     * formulae.
     */
    public FormulaReader() {
        super();
    }

    /** Creates a new FormulaReader which lexes SafeText and Unicode. */
    public FormulaReader(
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(allowBracketsAroundFormulae, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new FormulaReader. */
    public FormulaReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(
                lexSafeText,
                lexUnicode,
                allowBracketsAroundFormulae,
                allowBracketsAroundArguments,
                allowBracketsAroundTerms);
    }

    /** Creates a new FormulaReader. */
    public FormulaReader(PredicateReaderProperties props) {
        super(props);
    }

    @Override
    protected Formula convertToFormula(ParsablyTyped output) {
        return (Formula) output;
    }

    /** {@inheritDoc} */
    @Override
    protected AbstractParseTreeVisitor<ParsablyTyped> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new FormulaParserConstructionVisitor(getProperties(), creator, errorHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected ParsablyTyped executeParser(
            FormulaSetParser parser, AbstractParseTreeVisitor<ParsablyTyped> visitor) {
        InitFormulaContext ctx = parser.initFormula();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected ParsingFaultTypeMapping<Formula> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<ParsablyTyped> mapping) {
        return new ParsingFaultTypeMapping<>(
                mapping.getInput(), (Formula) mapping.getOutput(), mapping.getAll());
    }
}
