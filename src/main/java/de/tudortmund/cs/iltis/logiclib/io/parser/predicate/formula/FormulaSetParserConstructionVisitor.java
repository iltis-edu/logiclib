package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 * Used to construct a predicate formula from the {@link FormulaSetParser}.
 *
 * <p>This visitor uses the {@link FormulaParserConstructionVisitor} for the base formulas.
 */
public class FormulaSetParserConstructionVisitor
        extends FormulaSetParserBaseVisitor<List<List<ParsablyTyped>>> {

    private final VisitorErrorHandler errorHandler;

    // An inner class visitor because of different return types
    private final SingleListConstructionVisitor singleListVisitor;

    public FormulaSetParserConstructionVisitor(
            PredicateReaderProperties properties,
            ParsingCreator creator,
            VisitorErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        this.singleListVisitor =
                new SingleListConstructionVisitor(properties, creator, errorHandler);
    }

    @Override
    public List<List<ParsablyTyped>> visitInitFormulaSetSet(
            FormulaSetParser.InitFormulaSetSetContext ctx) {
        return visit(ctx.setSet);
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetSetMissingBraces(
            FormulaSetParser.FormulaSetSetMissingBracesContext ctx) {
        handleMissingBracesAroundSetSet(ctx.contentNoBraces);
        return visit(ctx.contentNoBraces);
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetSetBraces(
            FormulaSetParser.FormulaSetSetBracesContext ctx) {
        return visit(ctx.content);
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetSetContentSetsOrFormulae(
            FormulaSetParser.FormulaSetSetContentSetsOrFormulaeContext ctx) {
        return visit(ctx.formulaSetsOrFormulae());
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetSetContentMultiple(
            FormulaSetParser.FormulaSetSetContentMultipleContext ctx) {
        if (ctx.first.getChildCount() == 0) {
            // first is empty
            handleSuperfluousSeparatorBetweenSets(ctx.seps.get(0));
        }
        for (int i = 0; i < ctx.further.size() - 1; i++) {
            if (ctx.further.get(i).getChildCount() == 0) {
                // further.get(i) is empty
                handleSuperfluousSeparatorBetweenSets(ctx.seps.get(i + 1));
            }
        }
        if (ctx.further.get(ctx.further.size() - 1).getChildCount() == 0) {
            // if last formula is empty
            handleSuperfluousSeparatorBetweenSets(ctx.seps.get(ctx.seps.size() - 1));
        }

        return Data.join(
                visit(ctx.first),
                null,
                null,
                ctx.further.stream()
                        .map(this::visit)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetSetContentEmpty(
            FormulaSetParser.FormulaSetSetContentEmptyContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetsOrFormulaeOrEmptyNotPresent(
            FormulaSetParser.FormulaSetsOrFormulaeOrEmptyNotPresentContext ctx) {
        // is handled in parent
        return new ArrayList<>();
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetsOrFormulaeOrEmptyPresent(
            FormulaSetParser.FormulaSetsOrFormulaeOrEmptyPresentContext ctx) {
        return visit(ctx.formulaSetsOrFormulae());
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetsOrFormulaeSingle(
            FormulaSetParser.FormulaSetsOrFormulaeSingleContext ctx) {
        List<List<ParsablyTyped>> list = new ArrayList<>();
        list.add(singleListVisitor.visit(ctx.formulaSetOrFormula()));
        return list;
    }

    @Override
    public List<List<ParsablyTyped>> visitFormulaSetsOrFormulaeMultiple(
            FormulaSetParser.FormulaSetsOrFormulaeMultipleContext ctx) {
        handleMissingSeparatorBetweenSets(ctx.furtherSets.get(0));
        return Data.join(
                singleListVisitor.visit(ctx.firstSet),
                ctx.furtherSets.stream()
                        .map(singleListVisitor::visit)
                        .collect(Collectors.toList()));
    }

    /*-----------------------------------------*\
     | Helper methods of outer class           |
    \*-----------------------------------------*/

    private void handleMissingBracesAroundSetSet(ParserRuleContext ctx) {
        errorHandler.reportFault(
                GeneralFormulaSetFaultReason.BRACES_AROUND_SETS_MISSING,
                ctx.getStart(),
                ctx.getText(),
                "warning: braces around sets missing");
    }

    private void handleSuperfluousSeparatorBetweenSets(Token sep) {
        errorHandler.reportFault(
                GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_SETS_SUPERFLUOUS,
                sep,
                sep.getText(),
                "separator between sets superfluous");
    }

    private void handleMissingSeparatorBetweenSets(ParserRuleContext ctx) {
        errorHandler.reportFault(
                GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_SETS_MISSING,
                ctx.getStart(),
                ctx.getText(),
                "separator between formulae missing");
    }

    /*-----------------------------------------*\
     | INNER CLASS                             |
    \*-----------------------------------------*/

    // Some rules have a different return type. Thus, we need an inner class with a different
    // generics-parameter.
    public static class SingleListConstructionVisitor
            extends FormulaSetParserBaseVisitor<List<ParsablyTyped>> {

        private final VisitorErrorHandler errorHandler;

        // A visitor used for normal formula parsing
        private final FormulaParserConstructionVisitor subVisitor;

        public SingleListConstructionVisitor(
                PredicateReaderProperties properties,
                ParsingCreator creator,
                VisitorErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
            this.subVisitor =
                    new FormulaParserConstructionVisitor(properties, creator, errorHandler);
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetOrFormulaBraces(
                FormulaSetParser.FormulaSetOrFormulaBracesContext ctx) {
            return visit(ctx.formulaSetWithBraces());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetOrFormulaWithoutBraces(
                FormulaSetParser.FormulaSetOrFormulaWithoutBracesContext ctx) {
            handleMissingBracesAroundFormulaeInSetSet(ctx.formula());
            return Data.newArrayList(subVisitor.visit(ctx.formula()));
        }

        @Override
        public List<ParsablyTyped> visitInitFormulaSet(FormulaSetParser.InitFormulaSetContext ctx) {
            return visit(ctx.formulaSet());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetWithBraces(
                FormulaSetParser.FormulaSetWithBracesContext ctx) {
            return visit(ctx.formulaSetContent());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetWithoutBraces(
                FormulaSetParser.FormulaSetWithoutBracesContext ctx) {
            handleMissingBracesAroundFormulae(ctx.formulaSetContent());
            return visit(ctx.formulaSetContent());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetWithBracesLINK(
                FormulaSetParser.FormulaSetWithBracesLINKContext ctx) {
            return visit(ctx.formulaSetWithBraces());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetContentSingle(
                FormulaSetParser.FormulaSetContentSingleContext ctx) {
            return visit(ctx.formulae());
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetContentEmpty(
                FormulaSetParser.FormulaSetContentEmptyContext ctx) {
            return new ArrayList<>();
        }

        @Override
        public List<ParsablyTyped> visitFormulaSetContentMultiple(
                FormulaSetParser.FormulaSetContentMultipleContext ctx) {
            if (ctx.first.getChildCount() == 0) {
                // first is empty
                handleSuperfluousSeparatorBetweenFormulae(ctx.seps.get(0));
            }
            for (int i = 0; i < ctx.further.size() - 1; i++) {
                if (ctx.further.get(i).getChildCount() == 0) {
                    // further.get(i) is empty
                    handleSuperfluousSeparatorBetweenFormulae(ctx.seps.get(i + 1));
                }
            }
            if (ctx.further.get(ctx.further.size() - 1).getChildCount() == 0) {
                // if last formula is empty
                handleSuperfluousSeparatorBetweenFormulae(ctx.seps.get(ctx.seps.size() - 1));
            }

            return Data.join(
                    visit(ctx.first),
                    null,
                    null,
                    ctx.further.stream()
                            .map(this::visit)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()));
        }

        @Override
        public List<ParsablyTyped> visitFormulaeOrEmptyNotPresent(
                FormulaSetParser.FormulaeOrEmptyNotPresentContext ctx) {
            // is handled in parent
            return new ArrayList<>();
        }

        @Override
        public List<ParsablyTyped> visitFormulaeOrEmptyPresent(
                FormulaSetParser.FormulaeOrEmptyPresentContext ctx) {
            return visit(ctx.formulae());
        }

        @Override
        public List<ParsablyTyped> visitFormulaeSingle(FormulaSetParser.FormulaeSingleContext ctx) {
            return Data.newArrayList(subVisitor.visit(ctx.formula()));
        }

        @Override
        public List<ParsablyTyped> visitFormulaeMultiple(
                FormulaSetParser.FormulaeMultipleContext ctx) {
            handleMissingSeparatorBetweenFormulae(ctx.further.get(0));
            return Data.join(
                    subVisitor.visit(ctx.first),
                    ctx.further.stream().map(subVisitor::visit).collect(Collectors.toList()));
        }

        /*-----------------------------------------*\
         | Helper methods of inner class           |
        \*-----------------------------------------*/

        private void handleMissingBracesAroundFormulaeInSetSet(ParserRuleContext ctx) {
            errorHandler.reportFault(
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING,
                    ctx.getStart(),
                    ctx.getText(),
                    "warning: braces around formulae missing in set of sets");
        }

        private void handleSuperfluousSeparatorBetweenFormulae(Token sep) {
            errorHandler.reportFault(
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS,
                    sep,
                    sep.getText(),
                    "error: separator between formulae superfluous");
        }

        private void handleMissingSeparatorBetweenFormulae(ParserRuleContext ctx) {
            errorHandler.reportFault(
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING,
                    ctx.getStart(),
                    ctx.getText(),
                    "error: separator between formulae missing");
        }

        private void handleMissingBracesAroundFormulae(ParserRuleContext ctx) {
            errorHandler.reportFault(
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING,
                    ctx.getStart(),
                    ctx.getText(),
                    "warning: braces around formulae missing");
        }
    }
}
