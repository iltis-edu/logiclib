package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser.InitFormulaSetSetContext;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParserConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaSetReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.InvalidClauseException;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Disjunction;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Reader for predicate logical disjunctive clauses. Uses {@link FormulaSetParser}.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link PredicateFormulaFaultReason} (all), {@link
 * GeneralFormulaSetFaultReason} (all), {@link ClauseFaultCollection.ClauseFaultReason} (all)
 *
 * <p>The {@link ParsingFaultTypeMapping} can contain a {@link ClauseFaultCollection} with its
 * belonging {@link ClauseFaultCollection.ClauseFaultReason}s if the parsed formulas are not
 * applicable as a clause.
 */
public class DisjunctiveClauseSetReader
        extends FormulaSetReader<List<List<ParsablyTyped>>, DisjunctiveClauseSet> {

    /**
     * Creates a new DisjunctiveClauseReader which lexes SafeText and Unicode and does not allow
     * brackets around terms or arguments.
     */
    public DisjunctiveClauseSetReader() {
        super();
    }

    /** Creates a new DisjunctiveClauseReader which lexes SafeText and Unicode. */
    public DisjunctiveClauseSetReader(
            boolean allowBracketsAroundArguments, boolean allowBracketsAroundTerms) {
        super(allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new DisjunctiveClauseReader. */
    public DisjunctiveClauseSetReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(lexSafeText, lexUnicode, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new DisjunctiveClauseReader. */
    public DisjunctiveClauseSetReader(PredicateReaderProperties props) {
        super(props);
    }

    @Override
    protected AbstractParseTreeVisitor<List<List<ParsablyTyped>>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new FormulaSetParserConstructionVisitor(getProperties(), creator, errorHandler);
    }

    @Override
    protected List<List<ParsablyTyped>> executeParser(
            FormulaSetParser parser, AbstractParseTreeVisitor<List<List<ParsablyTyped>>> visitor) {
        InitFormulaSetSetContext ctx = parser.initFormulaSetSet();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    /**
     * The exact transformation does not matter (it could yield any formula which uses the same
     * symbols). Its only purpose is to be used for signature checks.
     */
    @Override
    protected Formula convertToFormula(List<List<ParsablyTyped>> formulaListList) {
        if (formulaListList != null
                && !formulaListList.contains(null)
                && formulaListList.stream().noneMatch(list -> list.contains(null)))
            if (formulaListList.isEmpty()) return new True();
            else {
                List<Formula> clauses = new ArrayList<>();
                for (List<ParsablyTyped> clauseParsablyTyped : formulaListList) {
                    List<Formula> clause =
                            clauseParsablyTyped.stream()
                                    .map(parsablyTyped -> (Formula) parsablyTyped)
                                    .collect(Collectors.toList());
                    if (clause.isEmpty()) clauses.add(new False());
                    else clauses.add(new Disjunction(clause));
                }
                return new Conjunction(clauses);
            }
        else return null;
    }

    @Override
    protected ParsingFaultTypeMapping<DisjunctiveClauseSet> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<List<List<ParsablyTyped>>> mapping) {
        List<List<ParsablyTyped>> formulaListList = mapping.getOutput();
        DisjunctiveClauseSet clauseSet = null;
        if (formulaListList != null
                && !formulaListList.contains(null)
                && formulaListList.stream().noneMatch(list -> list.contains(null))) {
            List<DisjunctiveClause> clauses = new ArrayList<>();
            for (List<ParsablyTyped> parsablyTypedList : formulaListList) {
                List<Formula> formulaList =
                        parsablyTypedList.stream()
                                .map(parsablyTyped -> (Formula) parsablyTyped)
                                .collect(Collectors.toList());
                try {
                    DisjunctiveClause clause = new DisjunctiveClause(formulaList);
                    clauses.add(clause);
                } catch (InvalidClauseException e) {
                    mapping = mapping.with(e.getFaultCollection());
                }
            }
            clauseSet = new DisjunctiveClauseSet(clauses);
        }

        return new ParsingFaultTypeMapping<>(mapping.getInput(), clauseSet, mapping.getAll());
    }
}
