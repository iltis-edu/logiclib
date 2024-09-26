package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;

/** An enum for types of modal logical patterns. */
public enum PatternCreatorType implements ParsableType {
    TRUE,
    FALSE,
    VARIABLE,
    NEGATION,
    CONJUNCTION,
    DISJUNCTION,
    IMPLICATION,
    EQUIVALENCE,
    DIAMOND,
    BOX,
    ALTERNATIVE,
    ANY,
    ANY_FORMULA,
    COMPLEMENT,
    REPEAT_FOREST,
    EXACT_NAME,
    ANY_NAME,
    MULTI_CONSTRAINT,
    CONTAINS_DESCENDANT
}
