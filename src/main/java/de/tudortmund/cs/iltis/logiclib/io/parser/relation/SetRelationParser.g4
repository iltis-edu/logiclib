parser grammar SetRelationParser;

options { superClass=AbstractParser; }
tokens {
    OBRACE, CBRACE,
    OPAREN, CPAREN,
    COMMA,
    WHITESPACE,
    INDEXED_SYMBOL
}

@header {
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
}

initRelation : relation EOF;

relation: OBRACE content CBRACE;

content: (tuple COMMA)* tuple | ;

tuple: OPAREN tupleContent CPAREN;

tupleContent: (symbol COMMA)* symbol #SymbolWord
    |           #EmptyWord
    ;

symbol: symb=INDEXED_SYMBOL;
