package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;

/** An enum for types of predicate logical patterns. */
public enum PatternCreatorType implements ParsableType {
    TRUE,
    FALSE,
    VARIABLE,
    FUNCTION,
    RELATION,
    NEGATION,
    CONJUNCTION,
    DISJUNCTION,
    IMPLICATION,
    EQUIVALENCE,
    EXISTENTIAL_QUANTIFIER,
    UNIVERSAL_QUANTIFIER,
    ALTERNATIVE,
    ANY,
    ANY_FORMULA,
    ANY_TERM,
    COMPLEMENT,
    REPEAT_FOREST,
    EXACT_NAME,
    ANY_NAME,
    MULTI_CONSTRAINT,
    CONTAINS_DESCENDANT,
    QUANTIFIED_CONSTANT
}
