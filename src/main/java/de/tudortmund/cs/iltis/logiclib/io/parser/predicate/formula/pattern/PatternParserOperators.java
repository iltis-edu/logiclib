package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;

/** A simple enumeration of all operators, operands, etc. that are used during parsing. */
public enum PatternParserOperators implements ParsableSymbol {
    COMPLEMENT,
    NOFORMULA,
    ANYFORMULA,
    READ,
    PLUS,
    STAR,
    AT,
    SQUOTE,
    TRUE,
    FALSE,
    NEG,
    AND,
    OR,
    EQUIV,
    IMPLIES,
    REVERSE_IMPLIES,
    FORALL,
    EXISTS,
    ARGUMENTSEP,
    OBRACKET,
    CBRACKET,
    OPAREN,
    CPAREN,
    OBRACE,
    CBRACE,
    RELINFIXISYMBOL,
    FUNINFIXISYMBOL,
    ISYMBOL,
    WS,
    OPERATOR_OR_ISYMBOL,
    MULTI,
    CONTAINS,
    DISTINCT;

    @Override
    public int getTokenType() {
        switch (this) {
            case COMPLEMENT:
                return PatternParser.COMPLEMENT;
            case NOFORMULA:
                return PatternParser.NOFORMULA;
            case ANYFORMULA:
                return PatternParser.ANYFORMULA;
            case READ:
                return PatternParser.READ;
            case PLUS:
                return PatternParser.PLUS;
            case STAR:
                return PatternParser.STAR;
            case AT:
                return PatternParser.AT;
            case SQUOTE:
                return PatternParser.SQUOTE;
            case TRUE:
                return PatternParser.TRUE;
            case FALSE:
                return PatternParser.FALSE;
            case NEG:
                return PatternParser.NEG;
            case AND:
                return PatternParser.AND;
            case OR:
                return PatternParser.OR;
            case EQUIV:
                return PatternParser.EQUIV;
            case IMPLIES:
                return PatternParser.IMPLIES;
            case REVERSE_IMPLIES:
                return PatternParser.REVERSE_IMPLIES;
            case FORALL:
                return PatternParser.FORALL;
            case EXISTS:
                return PatternParser.EXISTS;
            case ARGUMENTSEP:
                return PatternParser.ARGUMENTSEP;
            case OBRACKET:
                return PatternParser.OBRACKET;
            case CBRACKET:
                return PatternParser.CBRACKET;
            case OPAREN:
                return PatternParser.OPAREN;
            case CPAREN:
                return PatternParser.CPAREN;
            case OBRACE:
                return PatternParser.OBRACE;
            case CBRACE:
                return PatternParser.CBRACE;
            case RELINFIXISYMBOL:
                return PatternParser.RELINFIXISYMBOL;
            case FUNINFIXISYMBOL:
                return PatternParser.FUNINFIXISYMBOL;
            case ISYMBOL:
                return PatternParser.ISYMBOL;
            case WS:
                return PatternParser.WS;
            case OPERATOR_OR_ISYMBOL:
                return PatternParser.OPERATOR_OR_ISYMBOL;
            case MULTI:
                return PatternParser.MULTI;
            case CONTAINS:
                return PatternParser.CONTAINS;
            case DISTINCT:
                return PatternParser.DISTINCT;
            default:
                return 0;
        }
    }
}
