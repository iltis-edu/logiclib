// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/predicate/formula/FormulaSetParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link
 * FormulaSetParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *     type.
 */
public interface FormulaSetParserVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by the {@code PrefixTermParen} labeled alternative in {@link
     * FormulaSetParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTermParen(@NotNull FormulaSetParser.PrefixTermParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentSingle} labeled alternative in
     * {@link FormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentSingle(@NotNull FormulaSetParser.FormulaSetContentSingleContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#formulaSetWithBraces}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithBraces(@NotNull FormulaSetParser.FormulaSetWithBracesContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#iSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitISymbol(@NotNull FormulaSetParser.ISymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetMissingBraces} labeled alternative in
     * {@link FormulaSetParser#formulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetMissingBraces(
            @NotNull FormulaSetParser.FormulaSetSetMissingBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaInfix} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaInfix(@NotNull FormulaSetParser.SubformulaInfixContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaBracket} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaBracket(@NotNull FormulaSetParser.SubformulaBracketContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeSingle} labeled alternative in
     * {@link FormulaSetParser#formulaSetsOrFormulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeSingle(
            @NotNull FormulaSetParser.FormulaSetsOrFormulaeSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermInfixUnrestricted} labeled alternative in
     * {@link FormulaSetParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermInfixUnrestricted(@NotNull FormulaSetParser.TermInfixUnrestrictedContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixTermBrackets} labeled alternative in {@link
     * FormulaSetParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTermBrackets(@NotNull FormulaSetParser.PrefixTermBracketsContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#initTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitTerm(@NotNull FormulaSetParser.InitTermContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutAndSingle} labeled alternative
     * in {@link FormulaSetParser#superformulaWithoutAnd}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutAndSingle(
            @NotNull FormulaSetParser.SuperformulaWithoutAndSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaExists} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaExists(@NotNull FormulaSetParser.SubformulaExistsContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentSetsOrFormulae} labeled
     * alternative in {@link FormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentSetsOrFormulae(
            @NotNull FormulaSetParser.FormulaSetSetContentSetsOrFormulaeContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaInfixUnrestricted} labeled alternative in
     * {@link FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaInfixUnrestricted(
            @NotNull FormulaSetParser.SubformulaInfixUnrestrictedContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeSingle} labeled alternative in {@link
     * FormulaSetParser#formulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeSingle(@NotNull FormulaSetParser.FormulaeSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetWithoutBraces} labeled alternative in
     * {@link FormulaSetParser#formulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithoutBraces(@NotNull FormulaSetParser.FormulaSetWithoutBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaErrorIMPLIES} labeled alternative in
     * {@link FormulaSetParser#superformulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaErrorIMPLIES(@NotNull FormulaSetParser.SuperformulaErrorIMPLIESContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermInfix} labeled alternative in {@link
     * FormulaSetParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermInfix(@NotNull FormulaSetParser.TermInfixContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaPrefixBracket} labeled alternative in
     * {@link FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaPrefixBracket(@NotNull FormulaSetParser.SubformulaPrefixBracketContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutAndMultiple} labeled alternative
     * in {@link FormulaSetParser#superformulaWithoutAnd}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutAndMultiple(
            @NotNull FormulaSetParser.SuperformulaWithoutAndMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetOrFormulaBraces} labeled alternative in
     * {@link FormulaSetParser#formulaSetOrFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetOrFormulaBraces(
            @NotNull FormulaSetParser.FormulaSetOrFormulaBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaMultipe} labeled alternative in {@link
     * FormulaSetParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaMultipe(@NotNull FormulaSetParser.SuperformulaMultipeContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermNEGError} labeled alternative in {@link
     * FormulaSetParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermNEGError(@NotNull FormulaSetParser.TermNEGErrorContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaPrefix} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaPrefix(@NotNull FormulaSetParser.SubformulaPrefixContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSuper} labeled alternative in {@link
     * FormulaSetParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSuper(@NotNull FormulaSetParser.FormulaSuperContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaErrorOR} labeled alternative in {@link
     * FormulaSetParser#superformulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaErrorOR(@NotNull FormulaSetParser.SuperformulaErrorORContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetWithBracesLINK} labeled alternative in
     * {@link FormulaSetParser#formulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithBracesLINK(@NotNull FormulaSetParser.FormulaSetWithBracesLINKContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixTermBracketMultiple} labeled alternative in
     * {@link FormulaSetParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTermBracketMultiple(
            @NotNull FormulaSetParser.PrefixTermBracketMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixTermISymbol} labeled alternative in {@link
     * FormulaSetParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTermISymbol(@NotNull FormulaSetParser.PrefixTermISymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentEmpty} labeled alternative in
     * {@link FormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentEmpty(@NotNull FormulaSetParser.FormulaSetContentEmptyContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaErrorEQUIV} labeled alternative in
     * {@link FormulaSetParser#superformulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaErrorEQUIV(@NotNull FormulaSetParser.SuperformulaErrorEQUIVContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentMultiple} labeled alternative
     * in {@link FormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentMultiple(
            @NotNull FormulaSetParser.FormulaSetSetContentMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaErrorAND} labeled alternative in {@link
     * FormulaSetParser#superformulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaErrorAND(@NotNull FormulaSetParser.SuperformulaErrorANDContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#initFormulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormulaSetSet(@NotNull FormulaSetParser.InitFormulaSetSetContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSub} labeled alternative in {@link
     * FormulaSetParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSub(@NotNull FormulaSetParser.FormulaSubContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeMultiple} labeled alternative
     * in {@link FormulaSetParser#formulaSetsOrFormulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeMultiple(
            @NotNull FormulaSetParser.FormulaSetsOrFormulaeMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutOrMultiple} labeled alternative
     * in {@link FormulaSetParser#superformulaWithoutOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutOrMultiple(
            @NotNull FormulaSetParser.SuperformulaWithoutOrMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaNeg} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaNeg(@NotNull FormulaSetParser.SubformulaNegContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetBraces} labeled alternative in {@link
     * FormulaSetParser#formulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetBraces(@NotNull FormulaSetParser.FormulaSetSetBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeOrEmptyNotPresent} labeled
     * alternative in {@link FormulaSetParser#formulaSetsOrFormulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeOrEmptyNotPresent(
            @NotNull FormulaSetParser.FormulaSetsOrFormulaeOrEmptyNotPresentContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#initFormulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormulaSet(@NotNull FormulaSetParser.InitFormulaSetContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeOrEmptyPresent} labeled
     * alternative in {@link FormulaSetParser#formulaSetsOrFormulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeOrEmptyPresent(
            @NotNull FormulaSetParser.FormulaSetsOrFormulaeOrEmptyPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutAndOrEquiv} labeled alternative
     * in {@link FormulaSetParser#superformulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutAndOrEquiv(
            @NotNull FormulaSetParser.SuperformulaWithoutAndOrEquivContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentMultiple} labeled alternative in
     * {@link FormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentMultiple(
            @NotNull FormulaSetParser.FormulaSetContentMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaSingle} labeled alternative in {@link
     * FormulaSetParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaSingle(@NotNull FormulaSetParser.SuperformulaSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutAndOrERROR} labeled alternative
     * in {@link FormulaSetParser#superformulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutAndOrERROR(
            @NotNull FormulaSetParser.SuperformulaWithoutAndOrERRORContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermPrefix} labeled alternative in {@link
     * FormulaSetParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermPrefix(@NotNull FormulaSetParser.TermPrefixContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaPrefixParen} labeled alternative in
     * {@link FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaPrefixParen(@NotNull FormulaSetParser.SubformulaPrefixParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetOrFormulaWithoutBraces} labeled
     * alternative in {@link FormulaSetParser#formulaSetOrFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetOrFormulaWithoutBraces(
            @NotNull FormulaSetParser.FormulaSetOrFormulaWithoutBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaForAll} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaForAll(@NotNull FormulaSetParser.SubformulaForAllContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeMultiple} labeled alternative in {@link
     * FormulaSetParser#formulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeMultiple(@NotNull FormulaSetParser.FormulaeMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaTrue} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaTrue(@NotNull FormulaSetParser.SubformulaTrueContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutAndOrImplies} labeled
     * alternative in {@link FormulaSetParser#superformulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutAndOrImplies(
            @NotNull FormulaSetParser.SuperformulaWithoutAndOrImpliesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaParen} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaParen(@NotNull FormulaSetParser.SubformulaParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeOrEmptyNotPresent} labeled alternative in
     * {@link FormulaSetParser#formulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeOrEmptyNotPresent(
            @NotNull FormulaSetParser.FormulaeOrEmptyNotPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaFalse} labeled alternative in {@link
     * FormulaSetParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaFalse(@NotNull FormulaSetParser.SubformulaFalseContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeOrEmptyPresent} labeled alternative in
     * {@link FormulaSetParser#formulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeOrEmptyPresent(@NotNull FormulaSetParser.FormulaeOrEmptyPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentEmpty} labeled alternative in
     * {@link FormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentEmpty(
            @NotNull FormulaSetParser.FormulaSetSetContentEmptyContext ctx);

    /**
     * Visit a parse tree produced by {@link FormulaSetParser#initFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormula(@NotNull FormulaSetParser.InitFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperformulaWithoutOrSingle} labeled alternative in
     * {@link FormulaSetParser#superformulaWithoutOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperformulaWithoutOrSingle(
            @NotNull FormulaSetParser.SuperformulaWithoutOrSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixTermParenMultiple} labeled alternative in
     * {@link FormulaSetParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTermParenMultiple(@NotNull FormulaSetParser.PrefixTermParenMultipleContext ctx);
}
