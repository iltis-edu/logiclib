package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultReason;

/** Collection of reasons why parsing can fail, specifically for predicate logical formulae. */
public enum PredicateFormulaFaultReason implements ParsingFaultReason {
    /** a negation symbol appears in a term (and not in a formula) */
    NEGATION_IN_TERM,
    /** parentheses (as opposed to brackets) are present in a term but not allowed */
    PARENTHESES_AROUND_TERMS_NOT_ALLOWED,
    /**
     * brackets (as opposed to parentheses) are present around arguments of a relation or function
     * symbol but not allowed; the attribute "text" of the fault contains the symbol whose
     * attributes are in brackets
     */
    PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED,
    /** brackets (as opposed to parentheses) are present around formulae but not allowed */
    PARENTHESES_AROUND_FORMULAE_NOT_ALLOWED,
    /** there are multiple variables after a quantifier */
    MULTIPLE_VARIABLES_IN_QUANTIFIER;

    /** {@inheritDoc} */
    @Override
    public int getGroup() {
        switch (this) {
            case PARENTHESES_AROUND_TERMS_NOT_ALLOWED:
            case PARENTHESES_AROUND_ARGUMENTS_NOT_ALLOWED:
            case PARENTHESES_AROUND_FORMULAE_NOT_ALLOWED:
                return 400;
            case NEGATION_IN_TERM:
                return 1100;
            case MULTIPLE_VARIABLES_IN_QUANTIFIER:
                return 1300;
            default:
                return 0;
        }
    }
}
