// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/predicate/formula/pattern/PatternParser.g4 by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

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
     * Visit a parse tree produced by {@link PatternParser#prefixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixTerm(@NotNull PatternParser.PrefixTermContext ctx);

    /**
     * Visit a parse tree produced by the {@code FixedName} labeled alternative in {@link
     * PatternParser#iSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFixedName(@NotNull PatternParser.FixedNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixRelationSymbolParentheses} labeled
     * alternative in {@link PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixRelationSymbolParentheses(
            @NotNull PatternParser.PrefixRelationSymbolParenthesesContext ctx);

    /**
     * Visit a parse tree produced by the {@code True} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTrue(@NotNull PatternParser.TrueContext ctx);

    /**
     * Visit a parse tree produced by the {@code False} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFalse(@NotNull PatternParser.FalseContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyStarTerms} labeled alternative in {@link
     * PatternParser#terms}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyStarTerms(@NotNull PatternParser.AnyStarTermsContext ctx);

    /**
     * Visit a parse tree produced by the {@code NamedSubTermStar} labeled alternative in {@link
     * PatternParser#terms}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamedSubTermStar(@NotNull PatternParser.NamedSubTermStarContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyName} labeled alternative in {@link
     * PatternParser#iSymbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyName(@NotNull PatternParser.AnyNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code Implies} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitImplies(@NotNull PatternParser.ImpliesContext ctx);

    /**
     * Visit a parse tree produced by the {@code UniversalQuantifier} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUniversalQuantifier(@NotNull PatternParser.UniversalQuantifierContext ctx);

    /**
     * Visit a parse tree produced by the {@code ContainsDescendant} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContainsDescendant(@NotNull PatternParser.ContainsDescendantContext ctx);

    /**
     * Visit a parse tree produced by the {@code Equiv} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEquiv(@NotNull PatternParser.EquivContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyNameStarTerms} labeled alternative in {@link
     * PatternParser#terms}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyNameStarTerms(@NotNull PatternParser.AnyNameStarTermsContext ctx);

    /**
     * Visit a parse tree produced by the {@code ReadNameAtomarTerm} labeled alternative in {@link
     * PatternParser#namedTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReadNameAtomarTerm(@NotNull PatternParser.ReadNameAtomarTermContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#initTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitTerm(@NotNull PatternParser.InitTermContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaInParen} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaInParen(@NotNull PatternParser.FormulaInParenContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixRelationSymbol} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixRelationSymbol(@NotNull PatternParser.PrefixRelationSymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code Alternative} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAlternative(@NotNull PatternParser.AlternativeContext ctx);

    /**
     * Visit a parse tree produced by the {@code ReadNameAtomarSub} labeled alternative in {@link
     * PatternParser#namedSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitReadNameAtomarSub(@NotNull PatternParser.ReadNameAtomarSubContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormulaWithNameAtomarTerm} labeled alternative
     * in {@link PatternParser#namedTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormulaWithNameAtomarTerm(
            @NotNull PatternParser.AnyFormulaWithNameAtomarTermContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubformulaStar} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaStar(@NotNull PatternParser.SubformulaStarContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormulaWithNameAtomarSub} labeled alternative in
     * {@link PatternParser#namedSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormulaWithNameAtomarSub(
            @NotNull PatternParser.AnyFormulaWithNameAtomarSubContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormulaAtomarTerm} labeled alternative in {@link
     * PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormulaAtomarTerm(@NotNull PatternParser.AnyFormulaAtomarTermContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformula(@NotNull PatternParser.SubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixFunctionSymbolBrackets} labeled alternative
     * in {@link PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixFunctionSymbolBrackets(
            @NotNull PatternParser.PrefixFunctionSymbolBracketsContext ctx);

    /**
     * Visit a parse tree produced by the {@code AndLeadingNoFormula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAndLeadingNoFormula(@NotNull PatternParser.AndLeadingNoFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithoutInfixFunction} labeled alternative in {@link
     * PatternParser#infixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithoutInfixFunction(@NotNull PatternParser.WithoutInfixFunctionContext ctx);

    /**
     * Visit a parse tree produced by the {@code InfixFunctionSymbol} labeled alternative in {@link
     * PatternParser#infixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInfixFunctionSymbol(@NotNull PatternParser.InfixFunctionSymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermInBrackets} labeled alternative in {@link
     * PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermInBrackets(@NotNull PatternParser.TermInBracketsContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#notInfixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNotInfixSubformula(@NotNull PatternParser.NotInfixSubformulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitName(@NotNull PatternParser.NameContext ctx);

    /**
     * Visit a parse tree produced by the {@code MultiConstraint} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMultiConstraint(@NotNull PatternParser.MultiConstraintContext ctx);

    /**
     * Visit a parse tree produced by the {@code OrLeadingSubformula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrLeadingSubformula(@NotNull PatternParser.OrLeadingSubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code Negation} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNegation(@NotNull PatternParser.NegationContext ctx);

    /**
     * Visit a parse tree produced by the {@code Complement} labeled alternative in {@link
     * PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComplement(@NotNull PatternParser.ComplementContext ctx);

    /**
     * Visit a parse tree produced by the {@code SubTermStar} labeled alternative in {@link
     * PatternParser#terms}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubTermStar(@NotNull PatternParser.SubTermStarContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformulaOrS}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaOrS(@NotNull PatternParser.SubformulaOrSContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyStarSubs} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyStarSubs(@NotNull PatternParser.AnyStarSubsContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyFormulaAtomarSub} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyFormulaAtomarSub(@NotNull PatternParser.AnyFormulaAtomarSubContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixFunctionSymbolParentheses} labeled
     * alternative in {@link PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixFunctionSymbolParentheses(
            @NotNull PatternParser.PrefixFunctionSymbolParenthesesContext ctx);

    /**
     * Visit a parse tree produced by the {@code InfixRelationSymbol} labeled alternative in {@link
     * PatternParser#infixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInfixRelationSymbol(@NotNull PatternParser.InfixRelationSymbolContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#term}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTerm(@NotNull PatternParser.TermContext ctx);

    /**
     * Visit a parse tree produced by the {@code AndLeadingSubformula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAndLeadingSubformula(@NotNull PatternParser.AndLeadingSubformulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code FormulaInBracket} labeled alternative in {@link
     * PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormulaInBracket(@NotNull PatternParser.FormulaInBracketContext ctx);

    /**
     * Visit a parse tree produced by the {@code OrLeadingNoFormula} labeled alternative in {@link
     * PatternParser#superformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrLeadingNoFormula(@NotNull PatternParser.OrLeadingNoFormulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#termOrS}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermOrS(@NotNull PatternParser.TermOrSContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#namedFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamedFormula(@NotNull PatternParser.NamedFormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixRelationSymbolBrackets} labeled alternative
     * in {@link PatternParser#atomarSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixRelationSymbolBrackets(
            @NotNull PatternParser.PrefixRelationSymbolBracketsContext ctx);

    /**
     * Visit a parse tree produced by the {@code WithoutInfixRelation} labeled alternative in {@link
     * PatternParser#infixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWithoutInfixRelation(@NotNull PatternParser.WithoutInfixRelationContext ctx);

    /**
     * Visit a parse tree produced by the {@code PrefixFunctionSymbol} labeled alternative in {@link
     * PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrefixFunctionSymbol(@NotNull PatternParser.PrefixFunctionSymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code NamedSubformulaStar} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNamedSubformulaStar(@NotNull PatternParser.NamedSubformulaStarContext ctx);

    /**
     * Visit a parse tree produced by the {@code ExistentialQuantifier} labeled alternative in
     * {@link PatternParser#prefixSubformula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExistentialQuantifier(@NotNull PatternParser.ExistentialQuantifierContext ctx);

    /**
     * Visit a parse tree produced by the {@code TermInParen} labeled alternative in {@link
     * PatternParser#atomarTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTermInParen(@NotNull PatternParser.TermInParenContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#formula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFormula(@NotNull PatternParser.FormulaContext ctx);

    /**
     * Visit a parse tree produced by the {@code AnyNameStarSubs} labeled alternative in {@link
     * PatternParser#subformulae}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnyNameStarSubs(@NotNull PatternParser.AnyNameStarSubsContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#initFormula}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitFormula(@NotNull PatternParser.InitFormulaContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#notInfixTerm}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNotInfixTerm(@NotNull PatternParser.NotInfixTermContext ctx);

    /**
     * Visit a parse tree produced by {@link PatternParser#subformulaWithName}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSubformulaWithName(@NotNull PatternParser.SubformulaWithNameContext ctx);
}
