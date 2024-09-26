package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;
import org.antlr.v4.runtime.Token;

/** A simple enumeration of all operators, operands, etc. that are used during parsing. */
public enum FormulaParserOperators implements ParsableSymbol {
    AND,
    OR,
    EQUIV,
    FORALL,
    EXISTS,
    TRUE,
    FALSE,
    NEG,
    IMPLIES,
    REVERSE_IMPLIES,
    ARGUMENTSEP,
    QUANTIFIERSEP,
    OPAREN,
    CPAREN,
    OBRACKET,
    CBRACKET,
    OBRACE,
    CBRACE,
    RELINFIXISYMBOL,
    FUNINFIXISYMBOL,
    OPERATOR_OR_ISYMBOL,
    ISYMBOL,
    WS;

    @Override
    public int getTokenType() {
        switch (this) {
            case AND:
                return FormulaSetParser.AND;
            case ARGUMENTSEP:
                return FormulaSetParser.ARGUMENTSEP;
            case CBRACE:
                return FormulaSetParser.CBRACE;
            case CBRACKET:
                return FormulaSetParser.CBRACKET;
            case CPAREN:
                return FormulaSetParser.CPAREN;
            case EQUIV:
                return FormulaSetParser.EQUIV;
            case EXISTS:
                return FormulaSetParser.EXISTS;
            case FALSE:
                return FormulaSetParser.FALSE;
            case FORALL:
                return FormulaSetParser.FORALL;
            case FUNINFIXISYMBOL:
                return FormulaSetParser.FUNINFIXISYMBOL;
            case IMPLIES:
                return FormulaSetParser.IMPLIES;
            case ISYMBOL:
                return FormulaSetParser.ISYMBOL;
            case NEG:
                return FormulaSetParser.NEG;
            case OBRACE:
                return FormulaSetParser.OBRACE;
            case OBRACKET:
                return FormulaSetParser.OBRACKET;
            case OPAREN:
                return FormulaSetParser.OPAREN;
            case OPERATOR_OR_ISYMBOL:
                return FormulaSetParser.OPERATOR_OR_ISYMBOL;
            case OR:
                return FormulaSetParser.OR;
            case QUANTIFIERSEP:
                return FormulaSetParser.QUANTIFIERSEP;
            case RELINFIXISYMBOL:
                return FormulaSetParser.RELINFIXISYMBOL;
            case REVERSE_IMPLIES:
                return FormulaSetParser.REVERSE_IMPLIES;
            case TRUE:
                return FormulaSetParser.TRUE;
            case WS:
                return FormulaSetParser.WS;
            default:
                return Token.INVALID_TYPE;
        }
    }
}
