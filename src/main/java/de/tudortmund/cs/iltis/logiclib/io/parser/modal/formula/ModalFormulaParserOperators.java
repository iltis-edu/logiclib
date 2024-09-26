package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;

/** A simple enumeration of all operators, operands, etc. that are used during parsing. */
public enum ModalFormulaParserOperators implements ParsableSymbol {
    NEG,
    DIAMOND,
    BOX,
    AND,
    OR,
    EQUIV,
    IMPLIES,
    REVERSE_IMPLIES,
    TRUE,
    FALSE,
    OBRACKET,
    CBRACKET,
    OPAREN,
    CPAREN,
    VARIABLE,
    WHITESPACE,
    NOT_SEPARATED_VARIABLES,
    OBRACE,
    CBRACE,
    ARGUMENTSEP;

    @Override
    public int getTokenType() {
        switch (this) {
            case NEG:
                return ModalFormulaSetParser.NEG;
            case DIAMOND:
                return ModalFormulaSetParser.DIAMOND;
            case BOX:
                return ModalFormulaSetParser.BOX;
            case AND:
                return ModalFormulaSetParser.AND;
            case OR:
                return ModalFormulaSetParser.OR;
            case EQUIV:
                return ModalFormulaSetParser.EQUIV;
            case IMPLIES:
                return ModalFormulaSetParser.IMPLIES;
            case REVERSE_IMPLIES:
                return ModalFormulaSetParser.REVERSE_IMPLIES;
            case TRUE:
                return ModalFormulaSetParser.TRUE;
            case FALSE:
                return ModalFormulaSetParser.FALSE;
            case OBRACKET:
                return ModalFormulaSetParser.OBRACKET;
            case CBRACKET:
                return ModalFormulaSetParser.CBRACKET;
            case OPAREN:
                return ModalFormulaSetParser.OPAREN;
            case CPAREN:
                return ModalFormulaSetParser.CPAREN;
            case OBRACE:
                return ModalFormulaSetParser.OBRACE;
            case CBRACE:
                return ModalFormulaSetParser.CBRACE;
            case ARGUMENTSEP:
                return ModalFormulaSetParser.ARGUMENTSEP;
            case VARIABLE:
                return ModalFormulaSetParser.VARIABLE;
            case WHITESPACE:
                return ModalFormulaSetParser.WHITESPACE;
            case NOT_SEPARATED_VARIABLES:
                return ModalFormulaSetParser.NOT_SEPARATED_VARIABLES;
        }
        throw new RuntimeException("unreachable");
    }
}
