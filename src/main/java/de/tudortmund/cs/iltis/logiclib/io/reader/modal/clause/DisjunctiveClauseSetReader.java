package de.tudortmund.cs.iltis.logiclib.io.reader.modal.clause;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaSetConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaSetReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.clause.ClauseFaultCollection;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.modal.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.modal.clause.InvalidClauseException;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Uses the {@link ModalFormulaSetParser} to construct a {@link DisjunctiveClauseSet} from the
 * input.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link GeneralFormulaSetFaultReason} (all), {@link
 * ClauseFaultReason} (all)
 *
 * <p>The {@link ParsingFaultTypeMapping} can contain a {@link ClauseFaultCollection} with its
 * belonging {@link ClauseFaultCollection.ClauseFaultReason}s if the parsed formulas are not
 * applicable as a clause.
 */
public class DisjunctiveClauseSetReader
        extends ModalFormulaSetReader<List<List<ModalFormula>>, DisjunctiveClauseSet> {

    public DisjunctiveClauseSetReader(ModalReaderProperties properties) {
        super(properties, new RepairingParenthesesChecker(properties));
    }

    @Override
    protected AbstractParseTreeVisitor<List<List<ModalFormula>>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new ModalFormulaSetConstructionVisitor(errorHandler);
    }

    @Override
    protected List<List<ModalFormula>> executeParser(
            ModalFormulaSetParser parser,
            AbstractParseTreeVisitor<List<List<ModalFormula>>> visitor) {
        ModalFormulaSetParser.InitFormulaSetSetContext ctx = parser.initFormulaSetSet();
        if (isVerbose()) System.out.println("tree: " + ctx.toStringTree());
        return visitor.visit(ctx);
    }

    @Override
    protected ParsingFaultTypeMapping<DisjunctiveClauseSet> convertParserOutputToReaderOutput(
            ParsingFaultTypeMapping<List<List<ModalFormula>>> mapping) {

        List<List<ModalFormula>> formulaListList = mapping.getOutput();
        DisjunctiveClauseSet clauseSet = null;
        if (formulaListList != null
                && !formulaListList.contains(null)
                && formulaListList.stream().noneMatch(list -> list.contains(null))) {
            List<DisjunctiveClause> clauses = new LinkedList<>();
            for (List<ModalFormula> formulaList : formulaListList) {
                try {
                    DisjunctiveClause clause = new DisjunctiveClause();
                    clause.addAll(formulaList);
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
