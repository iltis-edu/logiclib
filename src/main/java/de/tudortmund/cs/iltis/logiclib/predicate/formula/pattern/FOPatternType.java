package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;

/** Types for FOPatterns */
public enum FOPatternType implements PatternType {
    TruePattern,
    FalsePattern,
    VariablePattern,
    FunctionTermPattern,
    RelationAtomPattern,
    NegationPattern,
    ConjunctionPattern,
    DisjunctionPattern,
    ImplicationPattern,
    EquivalencePattern,
    ExistentialQuantifierPattern,
    UniversalQuantifierPattern,
    NoFreeVariablePattern,
    FreeVariablePattern;
}
