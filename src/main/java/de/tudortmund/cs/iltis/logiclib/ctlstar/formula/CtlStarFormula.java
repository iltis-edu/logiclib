package de.tudortmund.cs.iltis.logiclib.ctlstar.formula;

import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.atoms.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ctlspecific.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.ltlspecific.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.operators.propositional.*;
import de.tudortmund.cs.iltis.logiclib.ctlstar.formula.utils.PropositionCollector;
import de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula.CtlstarFormulaWriterHTML;
import de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula.CtlstarFormulaWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula.CtlstarFormulaWriterSafeText;
import de.tudortmund.cs.iltis.logiclib.io.writer.ctlstar.formula.CtlstarFormulaWriterText;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.term.ComparableTerm;
import java.util.HashSet;
import java.util.Set;

/**
 * Representation of CTL Star formulae. A CTL Star formula is CTL / LTL / Propositional if the
 * respective method {@link #isCtl()} / {@link #isLtl()} / {@link #isPropositional()} == True
 */
public abstract class CtlStarFormula extends ComparableTerm<CtlStarFormula, IndexedSymbol> {

    public static final True TOP = new True();

    public static final False BOTTOM = new False();

    /** needed for serialization */
    protected CtlStarFormula() {}

    public CtlStarFormula(boolean arityFixed, final IndexedSymbol name) {
        super(arityFixed, name);
    }

    public CtlStarFormula(boolean arityFixed, final CtlStarFormula... subFormulae) {
        super(arityFixed, subFormulae);
    }

    // FORMULA CONSTRUCTORS

    // CTL Specific Operators
    // ==========================================================================================

    public AllFinally AF() {
        return new AllFinally(this);
    }

    public AllGlobally AG() {
        return new AllGlobally(this);
    }

    public AllNext AX() {
        return new AllNext(this);
    }

    public AllRelease AR(CtlStarFormula rightSubformula) {
        return new AllRelease(this, rightSubformula);
    }

    public AllUntil AU(CtlStarFormula rightSubformula) {
        return new AllUntil(this, rightSubformula);
    }

    public AllWeakUntil AW(CtlStarFormula rightSubformula) {
        return new AllWeakUntil(this, rightSubformula);
    }

    public ExistsFinally EF() {
        return new ExistsFinally(this);
    }

    public ExistsGlobally EG() {
        return new ExistsGlobally(this);
    }

    public ExistsNext EX() {
        return new ExistsNext(this);
    }

    public ExistsRelease ER(CtlStarFormula rightSubformula) {
        return new ExistsRelease(this, rightSubformula);
    }

    public ExistsUntil EU(CtlStarFormula rightSubformula) {
        return new ExistsUntil(this, rightSubformula);
    }

    public ExistsWeakUntil EW(CtlStarFormula rightSubformula) {
        return new ExistsWeakUntil(this, rightSubformula);
    }

    // LTL Specific Operators
    // ==========================================================================================

    public Finally F() {
        return new Finally(this);
    }

    public Globally G() {
        return new Globally(this);
    }

    public Next X() {
        return new Next(this);
    }

    public Release R(CtlStarFormula rightSubformula) {
        return new Release(this, rightSubformula);
    }

    public Until U(CtlStarFormula rightSubformula) {
        return new Until(this, rightSubformula);
    }

    public WeakUntil W(CtlStarFormula rightSubformula) {
        return new WeakUntil(this, rightSubformula);
    }

    // Propositional Logic Operators
    // ===================================================================================

    public Conjunction and(CtlStarFormula... rightSubformulae) {
        // add this (left subformula) as a child:
        Conjunction c = new Conjunction(this);

        // add all right subformulae as children (possible because conjunction implementation does
        // not have fixed arity)
        c.addChildren(rightSubformulae);
        return c;
    }

    public Disjunction or(CtlStarFormula... rightSubformulae) {
        Disjunction d = new Disjunction(this);
        d.addChildren(rightSubformulae);
        return d;
    }

    public Equivalence equivalent(CtlStarFormula rightSubformula) {
        return new Equivalence(this, rightSubformula);
    }

    public Implication implies(CtlStarFormula rightSubformula) {
        return new Implication(this, rightSubformula);
    }

    public Negation not() {
        return new Negation(this);
    }

    // FORMULA DECONSTRUCTORS

    // CTL Specific Operators
    // ==========================================================================================

    public boolean isAllFinally() {
        return this.getType() == FormulaType.ALLFINALLY;
    }

    public boolean isAllGlobally() {
        return this.getType() == FormulaType.ALLGLOBALLY;
    }

    public boolean isAllNext() {
        return this.getType() == FormulaType.ALLNEXT;
    }

    public boolean isAllRelease() {
        return this.getType() == FormulaType.ALLRELEASE;
    }

    public boolean isAllUntil() {
        return this.getType() == FormulaType.ALLUNTIL;
    }

    public boolean isAllWeakUntil() {
        return this.getType() == FormulaType.ALLWEAKUNTIL;
    }

    public boolean isExistsFinally() {
        return this.getType() == FormulaType.EXISTSFINALLY;
    }

    public boolean isExistsGlobally() {
        return this.getType() == FormulaType.EXISTSGLOBALLY;
    }

    public boolean isExistsNext() {
        return this.getType() == FormulaType.EXISTSNEXT;
    }

    public boolean isExistsRelease() {
        return this.getType() == FormulaType.EXISTSRELEASE;
    }

    public boolean isExistsUntil() {
        return this.getType() == FormulaType.EXISTSUNTIL;
    }

    public boolean isExistsWeakUntil() {
        return this.getType() == FormulaType.EXISTSWEAKUNTIL;
    }

    // LTL Specific Operators
    // ==========================================================================================

    public boolean isFinally() {
        return this.getType() == FormulaType.FINALLY;
    }

    public boolean isGlobally() {
        return this.getType() == FormulaType.GLOBALLY;
    }

    public boolean isNext() {
        return this.getType() == FormulaType.NEXT;
    }

    public boolean isRelease() {
        return this.getType() == FormulaType.RELEASE;
    }

    public boolean isUntil() {
        return this.getType() == FormulaType.UNTIL;
    }

    public boolean isWeakUntil() {
        return this.getType() == FormulaType.WEAKUNTIL;
    }

    // Propositional Logic Operators
    // ===================================================================================

    public boolean isConjunction() {
        return this.getType() == FormulaType.CONJUNCTION;
    }

    public boolean isDisjunction() {
        return this.getType() == FormulaType.DISJUNCTION;
    }

    public boolean isEquivalence() {
        return this.getType() == FormulaType.EQUIVALENCE;
    }

    public boolean isImplication() {
        return this.getType() == FormulaType.IMPLICATION;
    }

    public boolean isNegation() {
        return this.getType() == FormulaType.NEGATION;
    }

    // Atoms
    // ===========================================================================================================

    public boolean isProposition() {
        return this.getType() == FormulaType.PROPOSITION;
    }

    public boolean isTrue() {
        return this.getType() == FormulaType.TRUE;
    }

    public boolean isFalse() {
        return this.getType() == FormulaType.FALSE;
    }

    /////////////////////////
    //     INFORMATION
    ///////////////////////

    public abstract FormulaType getType();

    /** is the type of this current formula specific to CTL? */
    private boolean isCtlSpecific() {
        switch (this.getType()) {
            case ALLWEAKUNTIL:
            case ALLRELEASE:
            case EXISTSWEAKUNTIL:
            case EXISTSRELEASE:
            case EXISTSNEXT:
            case EXISTSGLOBALLY:
            case EXISTSUNTIL:
            case EXISTSFINALLY:
            case ALLUNTIL:
            case ALLNEXT:
            case ALLGLOBALLY:
            case ALLFINALLY:
                return true;
            default:
                return false;
        }
    }

    /** is the type of this current formula specific to LTL? */
    private boolean isLtlSpecific() {
        switch (this.getType()) {
            case WEAKUNTIL:
            case RELEASE:
            case UNTIL:
            case NEXT:
            case GLOBALLY:
            case FINALLY:
                return true;
            default:
                return false;
        }
    }

    /**
     * @return a set of all propositions found in the formula
     */
    public Set<Proposition> getPropositions() {
        return this.traverse(new PropositionCollector());
    }

    /**
     * @return a set of all subformulae
     */
    public Set<CtlStarFormula> getAllSubformulae() {
        return new HashSet<>(getDescendants());
    }

    /** is this a CTL formula? ie. there are no LTL-specific operators in use */
    public boolean isCtl() {
        return getAllSubformulae().stream().noneMatch(CtlStarFormula::isLtlSpecific);
    }

    /** is this an LTL formula? ie. there are no CTL-specific operators in use */
    public boolean isLtl() {
        return getAllSubformulae().stream().noneMatch(CtlStarFormula::isCtlSpecific);
    }

    /**
     * is this a propositional formula? ie. there are neither CTL-specific nor LTL-specific
     * operators in use
     */
    public boolean isPropositional() {
        return getAllSubformulae().stream()
                .noneMatch(subformula -> isCtlSpecific() || isLtlSpecific());
    }

    ////////////////////////////////
    //        WRITERS
    //////////////////////////////

    @Override
    public String toString() {
        return toText();
    }

    public String toHTML() {
        CtlstarFormulaWriterHTML htmlWriter = new CtlstarFormulaWriterHTML();
        return htmlWriter.writeAndChop(this);
    }

    /**
     * @return LaTeX string WITHOUT a surrounding maths environment
     */
    public String toLaTeXWithoutMathsEnvironment() {
        CtlstarFormulaWriterLaTeX laTeXWriter = new CtlstarFormulaWriterLaTeX();
        return laTeXWriter.writeAndChop(this);
    }

    /**
     * @return LaTeX string WITH a surrounding inline maths environment
     */
    public String toLaTeXWithMathsEnvironment() {
        CtlstarFormulaWriterLaTeX laTeXWriter = new CtlstarFormulaWriterLaTeX();
        return laTeXWriter.writeInline(this);
    }

    public String toSafeText() {
        CtlstarFormulaWriterSafeText safeTextWriter = new CtlstarFormulaWriterSafeText();
        return safeTextWriter.writeAndChop(this);
    }

    public String toText() {
        CtlstarFormulaWriterText textWriter = new CtlstarFormulaWriterText();
        return textWriter.writeAndChop(this);
    }
}
