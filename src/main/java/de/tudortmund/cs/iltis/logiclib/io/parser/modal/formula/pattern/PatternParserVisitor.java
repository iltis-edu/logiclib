// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/modal/formula/pattern/PatternParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link
 * PatternParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *     type.
 */
public interface PatternParserVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by the {@code ReadName} labeled alternative in {@link
     * PatternParser#namedSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReadName(@NotNull PatternParser.ReadNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code FixedName} labeled alternative in {@link
     * PatternParser#iSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFixedName(@NotNull PatternParser.FixedNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code OrLeadingSubformula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrLeadingSubformula(@NotNull PatternParser.OrLeadingSubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code Variable} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVariable(@NotNull PatternParser.VariableContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyStar} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyStar(@NotNull PatternParser.AnyStarContext ctx);

    /**
     * Visit a parse tree produced by the {@code Negation} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNegation(@NotNull PatternParser.NegationContext ctx);

    /**
     * Visit a parse tree produced by the {@code True} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTrue(@NotNull PatternParser.TrueContext ctx);

    /**
     * Visit a parse tree produced by the {@code Complement} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComplement(@NotNull PatternParser.ComplementContext ctx);

    /**
     * Visit a parse tree produced by the {@code False} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFalse(@NotNull PatternParser.FalseContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyName} labeled alternative in {@link
     * PatternParser#iSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyName(@NotNull PatternParser.AnyNameContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformulaOrS}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaOrS(@NotNull PatternParser.SubformulaOrSContext ctx);

    /**
     * Visit a parse tree produced by the {@code Implies} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitImplies(@NotNull PatternParser.ImpliesContext ctx);

    /**
     * Visit a parse tree produced by the {@code ContainsDescendant} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContainsDescendant(@NotNull PatternParser.ContainsDescendantContext ctx);

    /**
     * Visit a parse tree produced by the {@code Star} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStar(@NotNull PatternParser.StarContext ctx);

    /**
     * Visit a parse tree produced by the {@code Equiv} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEquiv(@NotNull PatternParser.EquivContext ctx);

    /**
     * Visit a parse tree produced by the {@code AndLeadingSubformula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAndLeadingSubformula(@NotNull PatternParser.AndLeadingSubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaInParen} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaInParen(@NotNull PatternParser.FormulaInParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormulaWithName} labeled alternative in {@link
     * PatternParser#namedSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormulaWithName(@NotNull PatternParser.AnyFormulaWithNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code OrLeadingNoFormula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrLeadingNoFormula(@NotNull PatternParser.OrLeadingNoFormulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#namedFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamedFormula(@NotNull PatternParser.NamedFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code Alternative} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAlternative(@NotNull PatternParser.AlternativeContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormula} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormula(@NotNull PatternParser.AnyFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code Box} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBox(@NotNull PatternParser.BoxContext ctx);

    /**
     * Visit a parse tree produced by the {@code Diamond} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDiamond(@NotNull PatternParser.DiamondContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyNameStar} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyNameStar(@NotNull PatternParser.AnyNameStarContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformula(@NotNull PatternParser.SubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code AndLeadingNoFormula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAndLeadingNoFormula(@NotNull PatternParser.AndLeadingNoFormulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitName(@NotNull PatternParser.NameContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormula(@NotNull PatternParser.FormulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#initFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormula(@NotNull PatternParser.InitFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code MultiConstraint} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultiConstraint(@NotNull PatternParser.MultiConstraintContext ctx);

    /**
     * Visit a parse tree produced by the {@code NamedStar} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamedStar(@NotNull PatternParser.NamedStarContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformulaWithName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaWithName(@NotNull PatternParser.SubformulaWithNameContext ctx);
}
