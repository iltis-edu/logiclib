package de.tudortmund.cs.iltis.logiclib.io.parser.relation;

import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.ConcreteRelation;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import de.tudortmund.cs.iltis.utils.io.parser.general.SymbolToken;
import java.util.ArrayList;

public class SetRelationConstructionVisitor
        extends SetRelationParserBaseVisitor<ConcreteRelation<IndexedSymbol>>
        implements SetRelationParserVisitor<ConcreteRelation<IndexedSymbol>> {

    @Override
    public ConcreteRelation<IndexedSymbol> visitInitRelation(
            SetRelationParser.InitRelationContext ctx) {
        return visit(ctx.relation());
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitRelation(SetRelationParser.RelationContext ctx) {
        return visit(ctx.content());
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitContent(SetRelationParser.ContentContext ctx) {
        ConcreteRelation<IndexedSymbol> relation = new ConcreteRelation<>();
        for (SetRelationParser.TupleContext tupleContext : ctx.tuple()) {
            ConcreteRelation<IndexedSymbol> relationWithOneTuple = visit(tupleContext);
            relation.add(relationWithOneTuple);
        }
        return relation;
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitTuple(SetRelationParser.TupleContext ctx) {
        return visit(ctx.tupleContent());
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitSymbolWord(
            SetRelationParser.SymbolWordContext ctx) {
        ISymbolConstructionVisitor visitor = new ISymbolConstructionVisitor();
        ArrayList<IndexedSymbol> tuple = new ArrayList<>();
        for (SetRelationParser.SymbolContext symbolContext : ctx.symbol()) {
            IndexedSymbol indexedSymbol = visitor.visit(symbolContext);
            tuple.add(indexedSymbol);
        }
        ConcreteRelation<IndexedSymbol> relation = new ConcreteRelation<>();
        relation.add(new Tuple<IndexedSymbol>(tuple));
        return relation;
    }

    private static class ISymbolConstructionVisitor
            extends SetRelationParserBaseVisitor<IndexedSymbol> {
        @Override
        public IndexedSymbol visitSymbol(SetRelationParser.SymbolContext ctx) {
            return ((SymbolToken) ctx.symb).getSymbol();
        }
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitSymbol(SetRelationParser.SymbolContext ctx) {
        throw new RuntimeException("Use the inner class for ISymbol parsing!");
    }

    @Override
    public ConcreteRelation<IndexedSymbol> visitEmptyWord(SetRelationParser.EmptyWordContext ctx) {
        ConcreteRelation<IndexedSymbol> relation = new ConcreteRelation<>();
        relation.add(new Tuple<>());
        return relation;
    }
}
