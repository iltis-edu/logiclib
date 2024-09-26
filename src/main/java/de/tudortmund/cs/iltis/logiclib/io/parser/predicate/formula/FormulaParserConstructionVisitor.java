package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.general.Data;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParenthesesMissingParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.general.*;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 * Used to construct a predicate formula from the {@link FormulaSetParser}. This visitor extends the
 * {@link FormulaSetParserBaseVisitor} (note the "Set") to allow usage of this visitor in the set
 * visitor {@link FormulaSetParserConstructionVisitor}. This is a bit messy, but otherwise it would
 * not work.
 */
public class FormulaParserConstructionVisitor extends FormulaSetParserBaseVisitor<ParsablyTyped> {

    private final PredicateReaderProperties properties;
    private final ParsingCreator creator;
    private final VisitorErrorHandler errorHandler;

    private final ISymbolConstructionVisitor iSymbolVisitor;

    public FormulaParserConstructionVisitor(
            PredicateReaderProperties properties,
            ParsingCreator creator,
            VisitorErrorHandler errorHandler) {
        this.properties = properties;
        this.creator = creator;
        this.errorHandler = errorHandler;

        iSymbolVisitor = new ISymbolConstructionVisitor();
    }

    @Override
    public ParsablyTyped visitInitFormula(FormulaSetParser.InitFormulaContext ctx) {
        return visit(ctx.formula());
    }

    @Override
    public ParsablyTyped visitInitTerm(FormulaSetParser.InitTermContext ctx) {
        return visit(ctx.term());
    }

    @Override
    public ParsablyTyped visitFormulaSub(FormulaSetParser.FormulaSubContext ctx) {
        return visit(ctx.subformula());
    }

    @Override
    public ParsablyTyped visitFormulaSuper(FormulaSetParser.FormulaSuperContext ctx) {
        return visit(ctx.superformula());
    }

    @Override
    public ParsablyTyped visitSuperformulaMultipe(FormulaSetParser.SuperformulaMultipeContext ctx) {
        List<ParsablyTyped> furtherConverted =
                ctx.further.stream().map(this::visit).collect(Collectors.toList());
        return creator.create(FormulaType.AND, Data.join(visit(ctx.first), furtherConverted));
    }

    @Override
    public ParsablyTyped visitSuperformulaSingle(FormulaSetParser.SuperformulaSingleContext ctx) {
        return visit(ctx.superformulaWithoutAnd());
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutOrMultiple(
            FormulaSetParser.SuperformulaWithoutOrMultipleContext ctx) {
        List<ParsablyTyped> furtherConverted =
                ctx.further.stream().map(this::visit).collect(Collectors.toList());
        return creator.create(FormulaType.AND, Data.join(visit(ctx.first), furtherConverted));
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutOrSingle(
            FormulaSetParser.SuperformulaWithoutOrSingleContext ctx) {
        return visit(ctx.superformulaWithoutAndOr());
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutAndMultiple(
            FormulaSetParser.SuperformulaWithoutAndMultipleContext ctx) {
        List<ParsablyTyped> furtherConverted =
                ctx.further.stream().map(this::visit).collect(Collectors.toList());
        return creator.create(FormulaType.OR, Data.join(visit(ctx.first), furtherConverted));
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutAndSingle(
            FormulaSetParser.SuperformulaWithoutAndSingleContext ctx) {
        return visit(ctx.superformulaWithoutAndOr());
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutAndOrImplies(
            FormulaSetParser.SuperformulaWithoutAndOrImpliesContext ctx) {
        return creator.create(FormulaType.IMPLIES, visit(ctx.first), visit(ctx.second));
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutAndOrEquiv(
            FormulaSetParser.SuperformulaWithoutAndOrEquivContext ctx) {
        return creator.create(FormulaType.EQUIV, visit(ctx.first), visit(ctx.second));
    }

    @Override
    public ParsablyTyped visitSuperformulaWithoutAndOrERROR(
            FormulaSetParser.SuperformulaWithoutAndOrERRORContext ctx) {
        return visit(ctx.superformulaError());
    }

    @Override
    public ParsablyTyped visitSuperformulaErrorAND(
            FormulaSetParser.SuperformulaErrorANDContext ctx) {
        ParsablyTyped firstSub = visit(ctx.firstsub);
        ParsablyTyped secondSuperWA = visit(ctx.secondsuperWA);

        if (secondSuperWA.getType()
                != FormulaType.AND) // secondSuperWA could already be an erroneous formula
        handleMissingParentheses(
                    ctx.AND().getSymbol(), FormulaType.AND, secondSuperWA.getType(), ctx.getText());

        return creator.create(FormulaType.AND, Data.join(firstSub, secondSuperWA));
    }

    @Override
    public ParsablyTyped visitSuperformulaErrorOR(FormulaSetParser.SuperformulaErrorORContext ctx) {
        ParsablyTyped firstSub = visit(ctx.firstsub);
        ParsablyTyped secondSuperWO = visit(ctx.secondsuperWO);

        if (secondSuperWO.getType()
                != FormulaType.OR) // secondSuperWO could already be an erroneous formula
        handleMissingParentheses(
                    ctx.OR().getSymbol(), FormulaType.OR, secondSuperWO.getType(), ctx.getText());

        return creator.create(FormulaType.AND, Data.join(firstSub, secondSuperWO));
    }

    @Override
    public ParsablyTyped visitSuperformulaErrorIMPLIES(
            FormulaSetParser.SuperformulaErrorIMPLIESContext ctx) {
        ParsablyTyped firstSub = visit(ctx.firstsub);
        ParsablyTyped secondSuper = visit(ctx.secondsuper);

        handleMissingParentheses(
                ctx.IMPLIES().getSymbol(),
                FormulaType.IMPLIES,
                secondSuper.getType(),
                ctx.getText());

        return creator.create(FormulaType.IMPLIES, firstSub, secondSuper);
    }

    @Override
    public ParsablyTyped visitSuperformulaErrorEQUIV(
            FormulaSetParser.SuperformulaErrorEQUIVContext ctx) {
        ParsablyTyped firstSub = visit(ctx.firstsub);
        ParsablyTyped secondSuper = visit(ctx.secondsuper);

        handleMissingParentheses(
                ctx.EQUIV().getSymbol(), FormulaType.EQUIV, secondSuper.getType(), ctx.getText());

        return creator.create(FormulaType.EQUIV, firstSub, secondSuper);
    }

    @Override
    public ParsablyTyped visitSubformulaNeg(FormulaSetParser.SubformulaNegContext ctx) {
        return creator.create(FormulaType.NEG, visit(ctx.sub));
    }

    @Override
    public ParsablyTyped visitSubformulaForAll(FormulaSetParser.SubformulaForAllContext ctx) {
        if (!ctx.symbols.isEmpty())
            handleMultipleVarsForQuantifier(ctx.FORALL().getSymbol(), ctx.getText());

        return creator.create(FormulaType.FORALL, iSymbolVisitor.visit(ctx.var), visit(ctx.sub));
    }

    @Override
    public ParsablyTyped visitSubformulaTrue(FormulaSetParser.SubformulaTrueContext ctx) {
        return creator.create(FormulaType.TRUE);
    }

    @Override
    public ParsablyTyped visitSubformulaFalse(FormulaSetParser.SubformulaFalseContext ctx) {
        return creator.create(FormulaType.FALSE);
    }

    @Override
    public ParsablyTyped visitSubformulaBracket(FormulaSetParser.SubformulaBracketContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundFormulae(ParenthesesType.BRACKETS))
            handleWrongParenthesesAroundFormulae(
                    ctx.OBRACKET().getSymbol(), ctx.getText(), ParenthesesType.BRACKETS);

        return visit(ctx.formula());
    }

    @Override
    public ParsablyTyped visitSubformulaExists(FormulaSetParser.SubformulaExistsContext ctx) {
        if (!ctx.symbols.isEmpty())
            handleMultipleVarsForQuantifier(ctx.EXISTS().getSymbol(), ctx.getText());

        return creator.create(FormulaType.EXISTS, iSymbolVisitor.visit(ctx.var), visit(ctx.sub));
    }

    @Override
    public ParsablyTyped visitSubformulaParen(FormulaSetParser.SubformulaParenContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundFormulae(ParenthesesType.PARENTHESES))
            handleWrongParenthesesAroundFormulae(
                    ctx.OPAREN().getSymbol(), ctx.getText(), ParenthesesType.PARENTHESES);

        return visit(ctx.formula());
    }

    @Override
    public ParsablyTyped visitSubformulaInfix(FormulaSetParser.SubformulaInfixContext ctx) {
        return creator.create(
                FormulaType.INFIX_RELATION_ATOM,
                ((SymbolToken) ctx.sym).getSymbol(),
                visit(ctx.firstPrefix),
                visit(ctx.secondPrefix));
    }

    @Override
    public ParsablyTyped visitSubformulaInfixUnrestricted(
            FormulaSetParser.SubformulaInfixUnrestrictedContext ctx) {
        return creator.create(
                FormulaType.INFIX_RELATION_ATOM,
                ((SymbolToken) ctx.sym).getSymbol(),
                visit(ctx.firstPrefix),
                visit(ctx.secondPrefix));
    }

    @Override
    public ParsablyTyped visitSubformulaPrefixParen(
            FormulaSetParser.SubformulaPrefixParenContext ctx) {
        IndexedSymbol iSymbolConverted = iSymbolVisitor.visit(ctx.iSymbol());

        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.PARENTHESES))
            handleWrongParenthesesAroundArguments(
                    ctx.OPAREN().getSymbol(), ctx.iSymbol().getText(), ParenthesesType.PARENTHESES);

        return prefixRelationHelper(iSymbolConverted, ctx.first, ctx.further);
    }

    @Override
    public ParsablyTyped visitSubformulaPrefixBracket(
            FormulaSetParser.SubformulaPrefixBracketContext ctx) {
        IndexedSymbol iSymbolConverted = iSymbolVisitor.visit(ctx.iSymbol());

        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.BRACKETS))
            handleWrongParenthesesAroundArguments(
                    ctx.OBRACKET().getSymbol(), ctx.iSymbol().getText(), ParenthesesType.BRACKETS);

        return prefixRelationHelper(iSymbolConverted, ctx.first, ctx.further);
    }

    @Override
    public ParsablyTyped visitSubformulaPrefix(FormulaSetParser.SubformulaPrefixContext ctx) {
        IndexedSymbol iSymbolConverted = iSymbolVisitor.visit(ctx.iSymbol());
        return creator.create(FormulaType.PREFIX_RELATION_ATOM, iSymbolConverted);
    }

    @Override
    public ParsablyTyped visitTermInfixUnrestricted(
            FormulaSetParser.TermInfixUnrestrictedContext ctx) {
        return creator.create(
                FormulaType.INFIX_FUNCTION_TERM,
                ((SymbolToken) ctx.sym).getSymbol(),
                visit(ctx.first),
                visit(ctx.second));
    }

    @Override
    public ParsablyTyped visitTermPrefix(FormulaSetParser.TermPrefixContext ctx) {
        return visit(ctx.prefixTerm());
    }

    @Override
    public ParsablyTyped visitTermInfix(FormulaSetParser.TermInfixContext ctx) {
        return creator.create(
                FormulaType.INFIX_FUNCTION_TERM,
                ((SymbolToken) ctx.sym).getSymbol(),
                visit(ctx.first),
                visit(ctx.second));
    }

    @Override
    public ParsablyTyped visitTermNEGError(FormulaSetParser.TermNEGErrorContext ctx) {
        handleNegInTerm(ctx.NEG().getSymbol(), ctx.child.getText());
        return visit(ctx.child);
    }

    @Override
    public ParsablyTyped visitPrefixTermParen(FormulaSetParser.PrefixTermParenContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundTerms(ParenthesesType.PARENTHESES))
            handleWrongParenthesesAroundTerms(
                    ctx.OPAREN().getSymbol(), ctx.term().getText(), ParenthesesType.PARENTHESES);

        return visit(ctx.term());
    }

    @Override
    public ParsablyTyped visitPrefixTermBrackets(FormulaSetParser.PrefixTermBracketsContext ctx) {
        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundTerms(ParenthesesType.BRACKETS))
            handleWrongParenthesesAroundTerms(
                    ctx.OBRACKET().getSymbol(), ctx.term().getText(), ParenthesesType.BRACKETS);

        return visit(ctx.term());
    }

    @Override
    public ParsablyTyped visitPrefixTermBracketMultiple(
            FormulaSetParser.PrefixTermBracketMultipleContext ctx) {
        IndexedSymbol iSymbolConverted = iSymbolVisitor.visit(ctx.iSymbol());

        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.BRACKETS))
            handleWrongParenthesesAroundArguments(
                    ctx.OBRACKET().getSymbol(), ctx.iSymbol().getText(), ParenthesesType.BRACKETS);

        return prefixTermHelper(iSymbolConverted, ctx.first, ctx.further);
    }

    @Override
    public ParsablyTyped visitPrefixTermParenMultiple(
            FormulaSetParser.PrefixTermParenMultipleContext ctx) {
        IndexedSymbol iSymbolConverted = iSymbolVisitor.visit(ctx.iSymbol());

        if (!properties
                .getParenthesesPredicateProperties()
                .isAllowedAroundArguments(ParenthesesType.PARENTHESES))
            handleWrongParenthesesAroundArguments(
                    ctx.OPAREN().getSymbol(), ctx.iSymbol().getText(), ParenthesesType.PARENTHESES);

        return prefixTermHelper(iSymbolConverted, ctx.first, ctx.further);
    }

    @Override
    public ParsablyTyped visitPrefixTermISymbol(FormulaSetParser.PrefixTermISymbolContext ctx) {
        // no parentheses/brackets found => possibly variable
        return creator.create(
                // !!! can be EITHER a variable OR a constant without parentheses
                FormulaType.VARIABLE, iSymbolVisitor.visit(ctx.iSymbol()));
    }

    // The iSymbol rule has a different return type. Thus, we need an inner class with a different
    // generics-parameter.
    private static class ISymbolConstructionVisitor
            extends FormulaSetParserBaseVisitor<IndexedSymbol> {
        @Override
        public IndexedSymbol visitISymbol(FormulaSetParser.ISymbolContext ctx) {
            return ((SymbolToken) ctx.sym).getSymbol();
        }
    }

    @Override
    public ParsablyTyped visitISymbol(FormulaSetParser.ISymbolContext ctx) {
        throw new RuntimeException("Use the inner class for ISymbol parsing!");
    }

    /*-----------------------------------------*\
     | Helper methods                          |
    \*-----------------------------------------*/

    private ParsablyTyped prefixRelationHelper(
            IndexedSymbol iSymbolConverted,
            ParserRuleContext first,
            List<FormulaSetParser.TermContext> further) {
        if (first == null || first.isEmpty()) {
            return creator.create(FormulaType.PREFIX_RELATION_ATOM, iSymbolConverted);
        } else {
            List<ParsablyTyped> furtherConverted =
                    further.stream().map(this::visit).collect(Collectors.toList());

            return creator.create(
                    FormulaType.PREFIX_RELATION_ATOM,
                    iSymbolConverted,
                    Data.join(visit(first), furtherConverted));
        }
    }

    private ParsablyTyped prefixTermHelper(
            IndexedSymbol iSymbolConverted,
            ParserRuleContext first,
            List<FormulaSetParser.TermContext> further) {
        if (first == null || first.isEmpty()) {
            // parentheses/brackets found => not a variable
            return creator.create(FormulaType.CONSTANT, iSymbolConverted);
        } else {
            List<ParsablyTyped> furtherConverted =
                    further.stream().map(this::visit).collect(Collectors.toList());

            return creator.create(
                    FormulaType.PREFIX_FUNCTION_TERM,
                    iSymbolConverted,
                    Data.join(visit(first), furtherConverted));
        }
    }

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

    private void handleMultipleVarsForQuantifier(Token quant, String wholeFormula) {
        errorHandler.reportFaultAndAlwaysBailOut(
                PredicateFormulaFaultReason.MULTIPLE_VARIABLES_IN_QUANTIFIER,
                quant,
                wholeFormula,
                "Quantifier with multiple variables");
    }

    private void handleWrongParenthesesAroundFormulae(
            Token bracket, String textIn, ParenthesesType type) {
        reportParenthesesParsingFault(
                PredicateFormulaFaultReason.PARENTHESES_AROUND_FORMULAE_NOT_ALLOWED,
                bracket,
                textIn,
                type);
    }

    private void handleWrongParenthesesAroundArguments(
            Token bracket, String textInFront, ParenthesesType type) {
        reportParenthesesParsingFault(
                PredicateFormulaFaultReason.PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
                bracket,
                textInFront,
                type);
    }

    private void handleWrongParenthesesAroundTerms(
            Token bracket, String textIn, ParenthesesType type) {
        reportParenthesesParsingFault(
                PredicateFormulaFaultReason.PARENTHESES_AROUND_TERMS_NOT_ALLOWED,
                bracket,
                textIn,
                type);
    }

    private void handleNegInTerm(Token neg, String termBehindNeg) {
        errorHandler.reportFaultAndAlwaysBailOut(
                PredicateFormulaFaultReason.NEGATION_IN_TERM,
                neg,
                termBehindNeg,
                "NEG in front of term");
    }

    private void reportParenthesesParsingFault(
            ParsingFaultReason reason, Token token, String faultText, ParenthesesType type) {
        ParsingFault fault =
                new ParenthesesParsingFault(
                        reason,
                        token.getLine() - 1,
                        token.getCharPositionInLine(),
                        token.getText(),
                        faultText,
                        type);

        errorHandler.reportFault(fault, "parentheses not allowed");
    }
}
