package de.tudortmund.cs.iltis.logiclib.modal.formula.pattern;

import de.tudortmund.cs.iltis.utils.tree.pattern.PatternType;

/** Types for modal logical patterns */
public enum ModalPatternType implements PatternType {
    BoxPattern,
    DiamondPattern,
    ConjunctionPattern,
    DisjunctionPattern,
    ImplicationPattern,
    EquivalencePattern,
    NegationPattern,
    VariablePattern,
    FalsePattern,
    TruePattern;
}
