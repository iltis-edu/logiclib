package de.tudortmund.cs.iltis.logiclib.io.reader.relation;

import de.tudortmund.cs.iltis.logiclib.io.parser.relation.SetRelationConstructionVisitor;
import de.tudortmund.cs.iltis.logiclib.io.parser.relation.SetRelationParser;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.ConcreteRelation;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingReader;
import de.tudortmund.cs.iltis.utils.io.parser.error.visitor.VisitorErrorHandler;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsingCreator;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.RepairingParenthesesChecker;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/** Uses {@link SetRelationParser} to construct a {@link ConcreteRelation}. */
public class SetRelationReader
        extends CustomizableLexingReader<
                ConcreteRelation<IndexedSymbol>,
                ConcreteRelation<IndexedSymbol>,
                SetRelationParser> {

    /** Creates a new regex reader with the given properties. */
    public SetRelationReader(SetRelationReaderProperties props) {
        super(props, new RepairingParenthesesChecker(props));
    }

    @Override
    protected SetRelationParser prepareParser(TokenStream tokenStream) {
        return new SetRelationParser(tokenStream);
    }

    @Override
    protected ConcreteRelation<IndexedSymbol> executeParser(
            SetRelationParser parser,
            AbstractParseTreeVisitor<ConcreteRelation<IndexedSymbol>> visitor) {
        SetRelationParser.InitRelationContext ctx = parser.initRelation();
        return visitor.visit(ctx);
    }

    @Override
    protected ParsingFaultTypeMapping<ConcreteRelation<IndexedSymbol>>
            convertParserOutputToReaderOutput(
                    ParsingFaultTypeMapping<ConcreteRelation<IndexedSymbol>> mapping) {
        return mapping;
    }

    @Override
    protected AbstractParseTreeVisitor<ConcreteRelation<IndexedSymbol>> prepareParseTreeVisitor(
            ParsingCreator creator, VisitorErrorHandler errorHandler) {
        return new SetRelationConstructionVisitor();
    }
}
