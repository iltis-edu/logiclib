package de.tudortmund.cs.iltis.logiclib.predicate.formula.utils;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;

/**
 * An enum for types of predicate logical formulae. Mainly used for differentiation of terms and
 * formulae in parsing.
 */
public enum FormulaType implements ParsableType {
    AND,
    OR,
    IMPLIES,
    EQUIV,
    FORALL,
    EXISTS,
    TRUE,
    FALSE,
    NEG,
    PREFIX_RELATION_ATOM,
    PREFIX_FUNCTION_TERM,
    INFIX_RELATION_ATOM,
    INFIX_FUNCTION_TERM,
    VARIABLE,
    CONSTANT;
}
