// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/relation/SetRelationParser.g4 by ANTLR
// 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.relation;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link
 * SetRelationParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *     type.
 */
public interface SetRelationParserVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link SetRelationParser#tuple}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTuple(@NotNull SetRelationParser.TupleContext ctx);

    /**
     * Visit a parse tree produced by {@link SetRelationParser#symbol}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSymbol(@NotNull SetRelationParser.SymbolContext ctx);

    /**
     * Visit a parse tree produced by the {@code SymbolWord} labeled alternative in {@link
     * SetRelationParser#tupleContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSymbolWord(@NotNull SetRelationParser.SymbolWordContext ctx);

    /**
     * Visit a parse tree produced by the {@code EmptyWord} labeled alternative in {@link
     * SetRelationParser#tupleContent}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitEmptyWord(@NotNull SetRelationParser.EmptyWordContext ctx);

    /**
     * Visit a parse tree produced by {@link SetRelationParser#initRelation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInitRelation(@NotNull SetRelationParser.InitRelationContext ctx);

    /**
     * Visit a parse tree produced by {@link SetRelationParser#content}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitContent(@NotNull SetRelationParser.ContentContext ctx);

    /**
     * Visit a parse tree produced by {@link SetRelationParser#relation}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRelation(@NotNull SetRelationParser.RelationContext ctx);
}
