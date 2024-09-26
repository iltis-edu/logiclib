package de.tudortmund.cs.iltis.logiclib.ctlstar.formula;

import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableType;

public enum FormulaType implements ParsableType {
    PROPOSITION,
    ALLFINALLY,
    ALLGLOBALLY,
    ALLNEXT,
    ALLRELEASE,
    ALLUNTIL,
    ALLWEAKUNTIL,
    EXISTSFINALLY,
    EXISTSGLOBALLY,
    EXISTSNEXT,
    EXISTSRELEASE,
    EXISTSUNTIL,
    EXISTSWEAKUNTIL,
    FINALLY,
    GLOBALLY,
    NEXT,
    RELEASE,
    UNTIL,
    WEAKUNTIL,
    CONJUNCTION,
    DISJUNCTION,
    EQUIVALENCE,
    IMPLICATION,
    NEGATION,
    TRUE,
    FALSE
}
