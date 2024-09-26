package de.tudortmund.cs.iltis.logiclib.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.ModalDefaultEvaluator;

/** Default implementation. */
public class Comparator {

    public static TrueComparator TRUE;
    protected Evaluator evaluator;

    static {
        Comparator.TRUE = new TrueComparator();
    }

    public Comparator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Comparator is the satisifiability comparator.
     *
     * @return true if formula is satisfiable.
     */
    public boolean test() {
        return this.evaluator.isSatisfiable();
    }

    public static Comparator satisfiable(ModalFormula phi) {
        return new Comparator(getEvaluator(phi));
    }

    public static Comparator tautology(ModalFormula phi) {
        return new TautologyComparator(phi);
    }

    public static Comparator equivalent(ModalFormula phi, ModalFormula psi) {
        if (phi.equals(psi)) return Comparator.TRUE;
        else return new EquivalenceComparator(phi, psi);
    }

    public static Comparator implies(ModalFormula phi, ModalFormula psi) {
        if (phi.equals(psi)) return Comparator.TRUE;
        else return new ImplicationComparator(phi, psi);
    }

    public Interpretation getLastModel() {
        return this.evaluator.getLastModel();
    }

    protected static Evaluator getEvaluator(ModalFormula phi) {
        if (phi.isPropositional()) return new PropositionalDefaultEvaluator(phi);
        else return new ModalDefaultEvaluator(phi);
    }
}

class TrueComparator extends Comparator {
    public TrueComparator() {
        super(null);
    }

    /**
     * Comparator is the satisifiability comparator.
     *
     * @return always true
     */
    @Override
    public boolean test() {
        return true;
    }
}

class EquivalenceComparator extends Comparator {
    public EquivalenceComparator(ModalFormula phi, ModalFormula psi) {
        super(getEvaluator(phi.equivalent(psi).not()));
    }

    /**
     * Comparator is the satisifiability comparator.
     *
     * @return true phi <-> psi is a tautology, that is, if its negation is not satisfiable.
     */
    @Override
    public boolean test() {
        return !super.test();
    }
}

class ImplicationComparator extends Comparator {
    public ImplicationComparator(ModalFormula phi, ModalFormula psi) {
        super(getEvaluator(phi.implies(psi).not()));
    }

    /**
     * Comparator is the satisifiability comparator.
     *
     * @return true phi -> psi is a tautology, that is, if its negation is not satisfiable.
     */
    @Override
    public boolean test() {
        return !super.test();
    }
}

class TautologyComparator extends Comparator {
    public TautologyComparator(ModalFormula phi) {
        super(getEvaluator(phi.not()));
    }

    /**
     * Comparator is the satisifiability comparator.:w
     *
     * @return true phi is a tautology, that is, if its negation is not satisfiable.
     */
    @Override
    public boolean test() {
        return !super.test();
    }
}
