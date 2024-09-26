// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/modal/formula/ModalFormulaSetParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link
 * ModalFormulaSetParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *     type.
 */
public interface ModalFormulaSetParserVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by the {@code BRACKETS} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBRACKETS(@NotNull ModalFormulaSetParser.BRACKETSContext ctx);

    /**
     * Visit a parse tree produced by the {@code DIAMOND} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDIAMOND(@NotNull ModalFormulaSetParser.DIAMONDContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentMultiple} labeled alternative
     * in {@link ModalFormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentMultiple(
            @NotNull ModalFormulaSetParser.FormulaSetSetContentMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentSingle} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentSingle(
            @NotNull ModalFormulaSetParser.FormulaSetContentSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutAndOrImplies} labeled
     * alternative in {@link ModalFormulaSetParser#superFormulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutAndOrImplies(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutAndOrImpliesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutAndSingle} labeled alternative
     * in {@link ModalFormulaSetParser#superFormulaWithoutAnd}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutAndSingle(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutAndSingleContext ctx);

    /**
     * Visit a parse tree produced by {@link ModalFormulaSetParser#formulaSetWithBraces}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithBraces(@NotNull ModalFormulaSetParser.FormulaSetWithBracesContext ctx);

    /**
     * Visit a parse tree produced by {@link ModalFormulaSetParser#initFormulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormulaSetSet(@NotNull ModalFormulaSetParser.InitFormulaSetSetContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetMissingBraces} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetMissingBraces(
            @NotNull ModalFormulaSetParser.FormulaSetSetMissingBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeMultiple} labeled alternative
     * in {@link ModalFormulaSetParser#formulaSetsOrFormulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeMultiple(
            @NotNull ModalFormulaSetParser.FormulaSetsOrFormulaeMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaErrorEQUIV} labeled alternative in
     * {@link ModalFormulaSetParser#superFormulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaErrorEQUIV(@NotNull ModalFormulaSetParser.SuperFormulaErrorEQUIVContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaErrorAND} labeled alternative in {@link
     * ModalFormulaSetParser#superFormulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaErrorAND(@NotNull ModalFormulaSetParser.SuperFormulaErrorANDContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutOrSingle} labeled alternative in
     * {@link ModalFormulaSetParser#superFormulaWithoutOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutOrSingle(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutOrSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code PARENS} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPARENS(@NotNull ModalFormulaSetParser.PARENSContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeSingle} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetsOrFormulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeSingle(
            @NotNull ModalFormulaSetParser.FormulaSetsOrFormulaeSingleContext ctx);

    /**
     * Visit a parse tree produced by {@link ModalFormulaSetParser#initModalFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitModalFormula(@NotNull ModalFormulaSetParser.InitModalFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code VARIABLE} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVARIABLE(@NotNull ModalFormulaSetParser.VARIABLEContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetBraces} labeled alternative in {@link
     * ModalFormulaSetParser#formulaSetSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetBraces(@NotNull ModalFormulaSetParser.FormulaSetSetBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaSingle} labeled alternative in {@link
     * ModalFormulaSetParser#superFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaSingle(@NotNull ModalFormulaSetParser.SuperFormulaSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeOrEmptyNotPresent} labeled
     * alternative in {@link ModalFormulaSetParser#formulaSetsOrFormulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeOrEmptyNotPresent(
            @NotNull ModalFormulaSetParser.FormulaSetsOrFormulaeOrEmptyNotPresentContext ctx);

    /**
     * Visit a parse tree produced by {@link ModalFormulaSetParser#initFormulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormulaSet(@NotNull ModalFormulaSetParser.InitFormulaSetContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutAndMultiple} labeled alternative
     * in {@link ModalFormulaSetParser#superFormulaWithoutAnd}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutAndMultiple(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutAndMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutAndOrError} labeled alternative
     * in {@link ModalFormulaSetParser#superFormulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutAndOrError(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutAndOrErrorContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetsOrFormulaeOrEmptyPresent} labeled
     * alternative in {@link ModalFormulaSetParser#formulaSetsOrFormulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetsOrFormulaeOrEmptyPresent(
            @NotNull ModalFormulaSetParser.FormulaSetsOrFormulaeOrEmptyPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentMultiple} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentMultiple(
            @NotNull ModalFormulaSetParser.FormulaSetContentMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentSetsOrFormulae} labeled
     * alternative in {@link ModalFormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentSetsOrFormulae(
            @NotNull ModalFormulaSetParser.FormulaSetSetContentSetsOrFormulaeContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaErrorIMPLIES} labeled alternative in
     * {@link ModalFormulaSetParser#superFormulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaErrorIMPLIES(
            @NotNull ModalFormulaSetParser.SuperFormulaErrorIMPLIESContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaErrorOR} labeled alternative in {@link
     * ModalFormulaSetParser#superFormulaError}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaErrorOR(@NotNull ModalFormulaSetParser.SuperFormulaErrorORContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeSingle} labeled alternative in {@link
     * ModalFormulaSetParser#formulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeSingle(@NotNull ModalFormulaSetParser.FormulaeSingleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetOrFormulaWithoutBraces} labeled
     * alternative in {@link ModalFormulaSetParser#formulaSetOrFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetOrFormulaWithoutBraces(
            @NotNull ModalFormulaSetParser.FormulaSetOrFormulaWithoutBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaLINK} labeled alternative in {@link
     * ModalFormulaSetParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaLINK(@NotNull ModalFormulaSetParser.SuperFormulaLINKContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubFormulaLINK} labeled alternative in {@link
     * ModalFormulaSetParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubFormulaLINK(@NotNull ModalFormulaSetParser.SubFormulaLINKContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetWithoutBraces} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithoutBraces(
            @NotNull ModalFormulaSetParser.FormulaSetWithoutBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutAndOrEquiv} labeled alternative
     * in {@link ModalFormulaSetParser#superFormulaWithoutAndOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutAndOrEquiv(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutAndOrEquivContext ctx);

    /**
     * Visit a parse tree produced by the {@code TRUE} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTRUE(@NotNull ModalFormulaSetParser.TRUEContext ctx);

    /**
     * Visit a parse tree produced by the {@code NEGATION} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNEGATION(@NotNull ModalFormulaSetParser.NEGATIONContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeMultiple} labeled alternative in {@link
     * ModalFormulaSetParser#formulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeMultiple(@NotNull ModalFormulaSetParser.FormulaeMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code BOX} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBOX(@NotNull ModalFormulaSetParser.BOXContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaMultiple} labeled alternative in {@link
     * ModalFormulaSetParser#superFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaMultiple(@NotNull ModalFormulaSetParser.SuperFormulaMultipleContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetOrFormulaBraces} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetOrFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetOrFormulaBraces(
            @NotNull ModalFormulaSetParser.FormulaSetOrFormulaBracesContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeOrEmptyNotPresent} labeled alternative in
     * {@link ModalFormulaSetParser#formulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeOrEmptyNotPresent(
            @NotNull ModalFormulaSetParser.FormulaeOrEmptyNotPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaeOrEmptyPresent} labeled alternative in
     * {@link ModalFormulaSetParser#formulaeOrEmpty}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaeOrEmptyPresent(@NotNull ModalFormulaSetParser.FormulaeOrEmptyPresentContext ctx);

    /**
     * Visit a parse tree produced by the {@code FALSE} labeled alternative in {@link
     * ModalFormulaSetParser#subFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFALSE(@NotNull ModalFormulaSetParser.FALSEContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetSetContentEmpty} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetSetContentEmpty(
            @NotNull ModalFormulaSetParser.FormulaSetSetContentEmptyContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetWithBracesLINK} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSet}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetWithBracesLINK(
            @NotNull ModalFormulaSetParser.FormulaSetWithBracesLINKContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaSetContentEmpty} labeled alternative in
     * {@link ModalFormulaSetParser#formulaSetContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaSetContentEmpty(@NotNull ModalFormulaSetParser.FormulaSetContentEmptyContext ctx);

    /**
     * Visit a parse tree produced by the {@code SuperFormulaWithoutOrMultiple} labeled alternative
     * in {@link ModalFormulaSetParser#superFormulaWithoutOr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSuperFormulaWithoutOrMultiple(
            @NotNull ModalFormulaSetParser.SuperFormulaWithoutOrMultipleContext ctx);
}
