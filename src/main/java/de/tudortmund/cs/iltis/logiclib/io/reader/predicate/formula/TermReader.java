package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser.InitTermContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical terms. Uses {@link FormulaSetParser}.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link PredicateFormulaFaultReason} (all)
 */
public class TermReader extends TermOrFormulaReader<ParsablyTyped, Term> {

    /**
     * Creates a new TermReader which lexes SafeText and Unicode and allows brackets around
     * formulae.
     */
    public TermReader() {
        super();
    }

    /** Creates a new TermReader which lexes SafeText and Unicode. */
    public TermReader(boolean allowBracketsAroundArguments, boolean allowBracketsAroundTerms) {
        super(false, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new TermReader. */
    public TermReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(
                lexSafeText,
                lexUnicode,
                false,
                allowBracketsAroundArguments,
                allowBracketsAroundTerms);
    }

    /** Creates a new TermReader. */
    public TermReader(PredicateReaderProperties props) {
        super(props);
    }

    @Override
    protected Term convertToFormula(ParsablyTyped output) {
        return (Term) output;
    }

    @Override
    protected AbstractParseTreeVisitor<ParsablyTyped> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new FormulaParserConstructionVisitor(getProperties(), creator, errorHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected Term executeParser(
            FormulaSetParser parser, AbstractParseTreeVisitor<ParsablyTyped> visitor) {
        InitTermContext ctx = parser.initTerm();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return (Term) visitor.visit(ctx);
    }

    @Override
    protected ParsingFaultTypeMapping<Term> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<ParsablyTyped> mapping) {
        return new ParsingFaultTypeMapping<>(
                mapping.getInput(), (Term) mapping.getOutput(), mapping.getAll());
    }
}
