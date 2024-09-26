package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import java.util.Optional;

/** Basic class for a predicate logical formula. */
public abstract class Formula extends TermOrFormula {

    /**
     * Uses {@link SignaturePolicy#TU_DORTMUND_LS1_LOGIK_POLICY} as {@link SignaturePolicy}.
     *
     * @throws IncorrectParseInputException if text can not be parsed.
     */
    public static Formula parse(String text) throws IncorrectParseInputException {
        FormulaReader reader = new FormulaReader();
        return reader.read(text, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY);
    }

    public static Formula parse(String text, SignatureCheckable signature)
            throws IncorrectParseInputException {

        FormulaReader reader = new FormulaReader();
        return reader.read(text, signature);
    }

    public static Optional<Formula> tryToParse(final String formula) {
        try {
            return Optional.<Formula>of(Formula.parse(formula));
        } catch (IncorrectParseInputException e) {
            return Optional.<Formula>empty();
        }
    }

    public static Optional<Formula> tryToParse(final String formula, SignatureCheckable signature) {
        try {
            return Optional.<Formula>of(Formula.parse(formula, signature));
        } catch (IncorrectParseInputException e) {
            return Optional.<Formula>empty();
        }
    }

    public Formula(boolean arityFixed) {
        super(arityFixed);
    }

    public Formula(boolean arityFixed, final TermOrFormula... subterms) {
        super(arityFixed, subterms);
    }

    public Formula(boolean arityFixed, final Iterable<? extends TermOrFormula> subterms) {
        super(arityFixed, subterms);
    }

    public Formula(boolean arityFixed, final IndexedSymbol name) {
        super(arityFixed, name);
    }

    public Formula(boolean arityFixed, final IndexedSymbol name, final TermOrFormula... subterms) {
        super(arityFixed, name, subterms);
    }

    public Formula(
            boolean arityFixed,
            final IndexedSymbol name,
            final Iterable<? extends TermOrFormula> subterms) {
        super(arityFixed, name, subterms);
    }

    //////////////////////////////////////////////
    // FORMULA CONSTRUCTORS //////////////////////
    //////////////////////////////////////////////

    public Conjunction and(Formula... rightSubformulae) {
        Conjunction conj = new Conjunction(this);
        conj.addChildren(rightSubformulae);
        return conj;
    }

    public Conjunction and(Iterable<? extends Formula> rightSubformulae) {
        // here, the correct constructor for Conjunction is called,
        // because rightSubformulae implements Iterable<Formula> and
        // this implements Iterable<TermOrFormula>.
        Conjunction conj = new Conjunction(this);
        conj.addChildren(rightSubformulae);
        return conj;
    }

    public Disjunction or(Formula... rightSubformulae) {
        Disjunction disj = new Disjunction(this);
        disj.addChildren(rightSubformulae);
        return disj;
    }

    public Disjunction or(Iterable<? extends Formula> rightSubformulae) {
        // here, the correct constructor for Disjunction is called,
        // because rightSubformulae implements Iterable<Formula> and
        // this implements Iterable<TermOrFormula>.
        Disjunction disj = new Disjunction(this);
        disj.addChildren(rightSubformulae);
        return disj;
    }

    public Implication implies(Formula conclusion) {
        return new Implication(this, conclusion);
    }

    public Equivalence equivalentTo(Formula rightSubformula) {
        return new Equivalence(this, rightSubformula);
    }

    public Negation not() {
        return new Negation(this);
    }

    public ExistentialQuantifier exists(Variable variable) {
        return new ExistentialQuantifier(variable, this);
    }

    public UniversalQuantifier forAll(Variable variable) {
        return new UniversalQuantifier(variable, this);
    }

    @Override
    public abstract Formula clone();

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    protected Formula() {}
}
