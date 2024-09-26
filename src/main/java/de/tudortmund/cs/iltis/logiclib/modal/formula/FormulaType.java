package de.tudortmund.cs.iltis.logiclib.modal.formula;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;

/**
 * An enum for types of modal logical formulae. Mainly used for differentiation of terms and
 * formulae in parsing.
 */
public enum FormulaType implements ParsableType {
    AND,
    OR,
    IMPLIES,
    EQUIV,
    BOX,
    DIAMOND,
    TRUE,
    FALSE,
    NEG,
    VARIABLE;
}
