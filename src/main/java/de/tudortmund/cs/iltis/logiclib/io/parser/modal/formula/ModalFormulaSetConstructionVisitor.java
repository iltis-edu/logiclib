package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 * Used to construct a predicate formula from the {@link ModalFormulaSetParser}.
 *
 * <p>This visitor uses the {@link ModalFormulaParserConstructionVisitor} for the base formulas.
 */
public class ModalFormulaSetConstructionVisitor
        extends ModalFormulaSetParserBaseVisitor<List<List<ModalFormula>>> {

    private final VisitorErrorHandler errorHandler;

    // An inner class visitor because of different return types
    private final SingleListConstructionVisitor singleListVisitor;

    public ModalFormulaSetConstructionVisitor(VisitorErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        this.singleListVisitor = new SingleListConstructionVisitor(errorHandler);
    }

    @Override
    public List<List<ModalFormula>> visitInitFormulaSetSet(
            ModalFormulaSetParser.InitFormulaSetSetContext ctx) {
        return visit(ctx.setSet);
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetSetMissingBraces(
            ModalFormulaSetParser.FormulaSetSetMissingBracesContext ctx) {
        handleMissingBracesAroundSetSet(ctx.contentNoBraces);
        return visit(ctx.contentNoBraces);
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetSetBraces(
            ModalFormulaSetParser.FormulaSetSetBracesContext ctx) {
        return visit(ctx.content);
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetSetContentSetsOrFormulae(
            ModalFormulaSetParser.FormulaSetSetContentSetsOrFormulaeContext ctx) {
        return visit(ctx.formulaSetsOrFormulae());
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetSetContentMultiple(
            ModalFormulaSetParser.FormulaSetSetContentMultipleContext ctx) {
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
    public List<List<ModalFormula>> visitFormulaSetSetContentEmpty(
            ModalFormulaSetParser.FormulaSetSetContentEmptyContext ctx) {
        return new ArrayList<>();
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetsOrFormulaeOrEmptyNotPresent(
            ModalFormulaSetParser.FormulaSetsOrFormulaeOrEmptyNotPresentContext ctx) {
        // is handled in parent
        return new ArrayList<>();
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetsOrFormulaeOrEmptyPresent(
            ModalFormulaSetParser.FormulaSetsOrFormulaeOrEmptyPresentContext ctx) {
        return visit(ctx.formulaSetsOrFormulae());
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetsOrFormulaeSingle(
            ModalFormulaSetParser.FormulaSetsOrFormulaeSingleContext ctx) {
        List<List<ModalFormula>> list = new ArrayList<>();
        list.add(singleListVisitor.visit(ctx.formulaSetOrFormula()));
        return list;
    }

    @Override
    public List<List<ModalFormula>> visitFormulaSetsOrFormulaeMultiple(
            ModalFormulaSetParser.FormulaSetsOrFormulaeMultipleContext ctx) {
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

    // Some rules have a different return type. Thus, we need an inner clas with a different
    // generics-parameter
    public static class SingleListConstructionVisitor
            extends ModalFormulaSetParserBaseVisitor<List<ModalFormula>> {

        private final VisitorErrorHandler errorHandler;

        // A visitor used for normal modal formula parsing
        private final ModalFormulaParserConstructionVisitor subVisitor;

        public SingleListConstructionVisitor(VisitorErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
            subVisitor = new ModalFormulaParserConstructionVisitor(errorHandler);
        }

        @Override
        public List<ModalFormula> visitFormulaSetOrFormulaBraces(
                ModalFormulaSetParser.FormulaSetOrFormulaBracesContext ctx) {
            return visit(ctx.formulaSetWithBraces());
        }

        @Override
        public List<ModalFormula> visitFormulaSetOrFormulaWithoutBraces(
                ModalFormulaSetParser.FormulaSetOrFormulaWithoutBracesContext ctx) {
            handleMissingBracesAroundFormulaeInSetSet(ctx.formula());
            return Data.newArrayList(subVisitor.visit(ctx.formula()));
        }

        @Override
        public List<ModalFormula> visitInitFormulaSet(
                ModalFormulaSetParser.InitFormulaSetContext ctx) {
            return visit(ctx.formulaSet());
        }

        @Override
        public List<ModalFormula> visitFormulaSetWithBraces(
                ModalFormulaSetParser.FormulaSetWithBracesContext ctx) {
            return visit(ctx.formulaSetContent());
        }

        @Override
        public List<ModalFormula> visitFormulaSetWithoutBraces(
                ModalFormulaSetParser.FormulaSetWithoutBracesContext ctx) {
            handleMissingBracesAroundFormulae(ctx.formulaSetContent());
            return visit(ctx.formulaSetContent());
        }

        @Override
        public List<ModalFormula> visitFormulaSetWithBracesLINK(
                ModalFormulaSetParser.FormulaSetWithBracesLINKContext ctx) {
            return visit(ctx.formulaSetWithBraces());
        }

        @Override
        public List<ModalFormula> visitFormulaSetContentSingle(
                ModalFormulaSetParser.FormulaSetContentSingleContext ctx) {
            return visit(ctx.formulae());
        }

        @Override
        public List<ModalFormula> visitFormulaSetContentEmpty(
                ModalFormulaSetParser.FormulaSetContentEmptyContext ctx) {
            return new ArrayList<>();
        }

        @Override
        public List<ModalFormula> visitFormulaSetContentMultiple(
                ModalFormulaSetParser.FormulaSetContentMultipleContext ctx) {
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
        public List<ModalFormula> visitFormulaeOrEmptyNotPresent(
                ModalFormulaSetParser.FormulaeOrEmptyNotPresentContext ctx) {
            // is handled in parent
            return new ArrayList<>();
        }

        @Override
        public List<ModalFormula> visitFormulaeOrEmptyPresent(
                ModalFormulaSetParser.FormulaeOrEmptyPresentContext ctx) {
            return visit(ctx.formulae());
        }

        @Override
        public List<ModalFormula> visitFormulaeSingle(
                ModalFormulaSetParser.FormulaeSingleContext ctx) {
            return Data.newArrayList(subVisitor.visit(ctx.formula()));
        }

        @Override
        public List<ModalFormula> visitFormulaeMultiple(
                ModalFormulaSetParser.FormulaeMultipleContext ctx) {
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
