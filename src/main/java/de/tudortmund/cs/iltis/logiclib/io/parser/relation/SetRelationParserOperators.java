package de.tudortmund.cs.iltis.logiclib.io.parser.relation;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;

public enum SetRelationParserOperators implements ParsableSymbol {
    OBRACE,
    CBRACE,
    OPAREN,
    CPAREN,
    COMMA,
    WHITESPACE,
    INDEXED_SYMBOL;

    @Override
    public int getTokenType() {
        switch (this) {
            case OBRACE:
                return SetRelationParser.OBRACE;
            case CBRACE:
                return SetRelationParser.CBRACE;
            case OPAREN:
                return SetRelationParser.OPAREN;
            case CPAREN:
                return SetRelationParser.CPAREN;
            case COMMA:
                return SetRelationParser.COMMA;
            case WHITESPACE:
                return SetRelationParser.WHITESPACE;
            case INDEXED_SYMBOL:
                return SetRelationParser.INDEXED_SYMBOL;
        }
        throw new RuntimeException("unreachable");
    }
}
