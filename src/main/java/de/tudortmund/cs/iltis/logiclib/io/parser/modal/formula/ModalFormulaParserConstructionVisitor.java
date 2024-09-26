package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula;

import de.tudortmund.cs.iltis.logiclib.modal.formula.*;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParenthesesMissingParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;
import de.tudortmund.cs.iltis.utils.io.parser.general.SymbolToken;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.Token;

/**
 * Used to construct a modal formula from the {@link ModalFormulaSetParser} parser. This visitor
 * extends the {@link ModalFormulaSetParserBaseVisitor} (note the "Set") to allow usage of this
 * visitor in the set visitor {@link ModalFormulaSetConstructionVisitor}. This is a bit messy, but
 * otherwise it would not work.
 */
public class ModalFormulaParserConstructionVisitor
        extends ModalFormulaSetParserBaseVisitor<ModalFormula> {

    private final VisitorErrorHandler errorHandler;

    public ModalFormulaParserConstructionVisitor(VisitorErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public ModalFormula visitInitModalFormula(ModalFormulaSetParser.InitModalFormulaContext ctx) {
        return visit(ctx.formula());
    }

    @Override
    public ModalFormula visitSuperFormulaLINK(ModalFormulaSetParser.SuperFormulaLINKContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSubFormulaLINK(ModalFormulaSetParser.SubFormulaLINKContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSuperFormulaMultiple(
            ModalFormulaSetParser.SuperFormulaMultipleContext ctx) {
        List<ModalFormulaSetParser.SubFormulaContext> subFormulas = new ArrayList<>();
        subFormulas.add(ctx.first);
        subFormulas.addAll(ctx.further);

        return new Conjunction(subFormulas.stream().map(this::visit).collect(Collectors.toList()));
    }

    @Override
    public ModalFormula visitSuperFormulaSingle(
            ModalFormulaSetParser.SuperFormulaSingleContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutOrMultiple(
            ModalFormulaSetParser.SuperFormulaWithoutOrMultipleContext ctx) {
        List<ModalFormulaSetParser.SubFormulaContext> subFormulas = new ArrayList<>();
        subFormulas.add(ctx.first);
        subFormulas.addAll(ctx.further);

        return new Conjunction(subFormulas.stream().map(this::visit).collect(Collectors.toList()));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutOrSingle(
            ModalFormulaSetParser.SuperFormulaWithoutOrSingleContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutAndMultiple(
            ModalFormulaSetParser.SuperFormulaWithoutAndMultipleContext ctx) {
        List<ModalFormulaSetParser.SubFormulaContext> subFormulas = new ArrayList<>();
        subFormulas.add(ctx.first);
        subFormulas.addAll(ctx.further);

        return new Disjunction(subFormulas.stream().map(this::visit).collect(Collectors.toList()));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutAndSingle(
            ModalFormulaSetParser.SuperFormulaWithoutAndSingleContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutAndOrImplies(
            ModalFormulaSetParser.SuperFormulaWithoutAndOrImpliesContext ctx) {
        return visit(ctx.first).implies(visit(ctx.second));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutAndOrEquiv(
            ModalFormulaSetParser.SuperFormulaWithoutAndOrEquivContext ctx) {
        return visit(ctx.first).equivalent(visit(ctx.second));
    }

    @Override
    public ModalFormula visitSuperFormulaWithoutAndOrError(
            ModalFormulaSetParser.SuperFormulaWithoutAndOrErrorContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public ModalFormula visitSuperFormulaErrorAND(
            ModalFormulaSetParser.SuperFormulaErrorANDContext ctx) {
        ModalFormula firstSub = visit(ctx.firstSub);
        ModalFormula secondSuperWA = visit(ctx.secondSuperWA);

        if (secondSuperWA.getType()
                != FormulaType.AND) // secondSuperWA could already be an erroneous formula
        handleMissingParentheses(
                    ctx.AND().getSymbol(), FormulaType.AND, secondSuperWA.getType(), ctx.getText());

        return new Conjunction(Data.join(firstSub, secondSuperWA));
    }

    @Override
    public ModalFormula visitSuperFormulaErrorOR(
            ModalFormulaSetParser.SuperFormulaErrorORContext ctx) {
        ModalFormula firstSub = visit(ctx.firstSub);
        ModalFormula secondSuperWO = visit(ctx.secondSuperWO);

        if (secondSuperWO.getType()
                != FormulaType.OR) // secondSuperWO could already be an erroneous formula
        handleMissingParentheses(
                    ctx.OR().getSymbol(), FormulaType.AND, secondSuperWO.getType(), ctx.getText());

        return new Disjunction(Data.join(firstSub, secondSuperWO));
    }

    @Override
    public ModalFormula visitSuperFormulaErrorIMPLIES(
            ModalFormulaSetParser.SuperFormulaErrorIMPLIESContext ctx) {
        ModalFormula firstSub = visit(ctx.firstSub);
        ModalFormula secondSuper = visit(ctx.secondSuper);

        handleMissingParentheses(
                ctx.IMPLIES().getSymbol(),
                FormulaType.IMPLIES,
                secondSuper.getType(),
                ctx.getText());

        return firstSub.implies(secondSuper);
    }

    @Override
    public ModalFormula visitSuperFormulaErrorEQUIV(
            ModalFormulaSetParser.SuperFormulaErrorEQUIVContext ctx) {
        ModalFormula firstSub = visit(ctx.firstSub);
        ModalFormula secondSuper = visit(ctx.secondSuper);

        handleMissingParentheses(
                ctx.EQUIV().getSymbol(), FormulaType.EQUIV, secondSuper.getType(), ctx.getText());

        return firstSub.equivalent(secondSuper);
    }

    @Override
    public ModalFormula visitVARIABLE(ModalFormulaSetParser.VARIABLEContext ctx) {
        return new Variable(((SymbolToken) ctx.var).getSymbol());
    }

    @Override
    public ModalFormula visitTRUE(ModalFormulaSetParser.TRUEContext ctx) {
        return ModalFormula.TOP;
    }

    @Override
    public ModalFormula visitFALSE(ModalFormulaSetParser.FALSEContext ctx) {
        return ModalFormula.BOTTOM;
    }

    public ModalFormula visitBRACKETS(ModalFormulaSetParser.BRACKETSContext ctx) {
        return visit(ctx.getChild(1));
    }

    public ModalFormula visitPARENS(ModalFormulaSetParser.PARENSContext ctx) {
        return visit(ctx.getChild(1));
    }

    @Override
    public ModalFormula visitNEGATION(ModalFormulaSetParser.NEGATIONContext ctx) {
        return new Negation(visit(ctx.getChild(1)));
    }

    @Override
    public ModalFormula visitBOX(ModalFormulaSetParser.BOXContext ctx) {
        return new Box(visit(ctx.getChild(1)));
    }

    @Override
    public ModalFormula visitDIAMOND(ModalFormulaSetParser.DIAMONDContext ctx) {
        return new Diamond(visit(ctx.getChild(1)));
    }

    /*-----------------------------------------*\
     | Helper methods                          |
    \*-----------------------------------------*/

    private void handleMissingParentheses(
            Token firstToken, ParsableType firstType, ParsableType secondType, String partOfInput) {
        ParenthesesMissingParsingFault fault =
                new ParenthesesMissingParsingFault(
                        firstToken.getLine() - 1,
                        firstToken.getCharPositionInLine(),
                        firstToken.getText(),
                        partOfInput,
                        firstType,
                        secondType);

        errorHandler.reportFault(fault, "operators at same level");
    }
}
