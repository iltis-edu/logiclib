package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser.InitFormulaSetContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaSetReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.InvalidClauseException;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical disjunctive clauses. Uses {@link FormulaSetParser}.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link PredicateFormulaFaultReason} (all), {@link
 * GeneralFormulaSetFaultReason} (all), {@link ClauseFaultReason} (all)
 *
 * <p>The {@link ParsingFaultTypeMapping} can contain a {@link ClauseFaultCollection} with its
 * belonging {@link ClauseFaultReason}s if the parsed formulas are not applicable as a clause.
 */
public class DisjunctiveClauseReader
        extends FormulaSetReader<List<ParsablyTyped>, DisjunctiveClause> {

    /**
     * Creates a new DisjunctiveClauseReader which lexes SafeText and Unicode and does not allow
     * brackets around terms or arguments.
     */
    public DisjunctiveClauseReader() {
        super();
    }

    /** Creates a new DisjunctiveClauseReader which lexes SafeText and Unicode. */
    public DisjunctiveClauseReader(
            boolean allowBracketsAroundArguments, boolean allowBracketsAroundTerms) {
        super(allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new DisjunctiveClauseReader. */
    public DisjunctiveClauseReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(lexSafeText, lexUnicode, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new DisjunctiveClauseReader. */
    public DisjunctiveClauseReader(PredicateReaderProperties props) {
        super(props);
    }

    @Override
    protected AbstractParseTreeVisitor<List<ParsablyTyped>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new FormulaSetParserConstructionVisitor.SingleListConstructionVisitor(
                getProperties(), creator, errorHandler);
    }

    @Override
    protected List<ParsablyTyped> executeParser(
            FormulaSetParser parser, AbstractParseTreeVisitor<List<ParsablyTyped>> visitor) {
        InitFormulaSetContext ctx = parser.initFormulaSet();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected Formula convertToFormula(List<ParsablyTyped> parsablyTypedList) {
        if (parsablyTypedList == null || parsablyTypedList.contains(null)) return null;

        List<Formula> formulaList = new ArrayList<>();
        for (ParsablyTyped parsablyTyped : parsablyTypedList) {
            formulaList.add((Formula) parsablyTyped);
        }

        if (formulaList.isEmpty()) return new False();
        else return new Disjunction(formulaList);
    }

    @Override
    protected ParsingFaultTypeMapping<DisjunctiveClause> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<List<ParsablyTyped>> mapping) {

        DisjunctiveClause clause = null;
        if (mapping.getOutput() != null && !mapping.getOutput().contains(null)) {
            List<Formula> formulaList = new ArrayList<>();

            for (ParsablyTyped parsablyTyped : mapping.getOutput()) {
                formulaList.add((Formula) parsablyTyped);
            }

            try {
                clause = new DisjunctiveClause(formulaList);
            } catch (InvalidClauseException e) {
                mapping = mapping.with(e.getFaultCollection());
            }
        }
        return new ParsingFaultTypeMapping<>(mapping.getInput(), clause, mapping.getAll());
    }
}
