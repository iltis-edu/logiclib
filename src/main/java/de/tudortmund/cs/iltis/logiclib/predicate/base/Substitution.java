package de.tudortmund.cs.iltis.logiclib.predicate.base;

import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.base.mgu.MGUComputationDifferentNonVariableTerms;
import de.tudortmund.cs.iltis.logiclib.predicate.base.mgu.MGUComputationDifferentRelationSymbols;
import de.tudortmund.cs.iltis.logiclib.predicate.base.mgu.MGUComputationVariableRecursion;
import de.tudortmund.cs.iltis.logiclib.predicate.base.mgu.MGUResult;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Negation;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationFailed;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationInProgress;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationLog;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationState;
import de.tudortmund.cs.iltis.utils.explainedresult.ComputationSuccess;
import de.tudortmund.cs.iltis.utils.explainedresult.ExplainedResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

public class Substitution extends TreeMap<Variable, Term> {

    public Substitution() {
        super();
    }

    public Substitution(Variable variable, Term term) {
        this();
        substitute(variable, term);
    }

    /**
     * @param oldFormula only works with RelationAtom
     */
    public TermOrFormula apply(TermOrFormula oldFormula) {
        if (oldFormula.isVariable()) {
            if (this.containsKey(oldFormula)) {
                return this.get(oldFormula);
            }
            return oldFormula;
        }
        if (oldFormula.isFunction()) {
            List<Term> newSubTerms = new ArrayList<>();

            for (TermOrFormula subTerm : oldFormula.getChildren()) {
                newSubTerms.add((Term) this.apply(subTerm));
            }
            return new FunctionTerm(((FunctionTerm) oldFormula).getName(), newSubTerms);
        }
        if (oldFormula.isRelation()) {
            List<Term> newSubTerms = new ArrayList<>();

            for (TermOrFormula subTerm : oldFormula.getChildren()) {
                newSubTerms.add((Term) this.apply(subTerm));
            }
            return new RelationAtom(((RelationAtom) oldFormula).getName(), newSubTerms);
        }

        // can only be a negation
        return new Negation((Formula) this.apply(oldFormula.getChild(0)));
    }

    /** Adds the mapping from variable to term to this substitution. */
    public void substitute(Variable variable, Term term) {
        this.put(variable, term);
    }

    public static ExplainedResult<
                    Optional<Substitution>, ComputationLog<ComputationState, MGUResult>>
            calculateMGU(Collection<RelationAtom> atoms) {

        ComputationLog<ComputationState, MGUResult> log = new ComputationLog<>();
        Substitution unifier = new Substitution();
        ListSet<RelationAtom> unifiedAtoms = new ListSet<>(atoms);
        boolean isFirstAtom = true;
        RelationAtom firstAtom = null;
        RelationSymbol symbol = null;

        // if there are different relation symbols, there is no unifier
        for (RelationAtom atom : atoms) {
            RelationSymbol otherSymbol = atom.getRelationSymbols().iterator().next();
            if (isFirstAtom) {
                firstAtom = atom;
                symbol = otherSymbol;
                isFirstAtom = false;
            } else {
                if (!otherSymbol.equals(symbol)) {

                    log.log(
                            new MGUComputationDifferentRelationSymbols(firstAtom, atom),
                            new MGUResult(unifiedAtoms, unifier, unifier));

                    return new ExplainedResult<>(Optional.empty(), log);
                }
            }
        }

        while (unifiedAtoms.size() > 1) {
            ListSet<RelationAtom> tempAtoms = new ListSet<>();
            firstAtom = null;
            Substitution substitution = new Substitution();

            // compare two different atoms
            for (RelationAtom atom : unifiedAtoms) {
                if (firstAtom == null) {
                    firstAtom = atom;
                } else {
                    if (substitution.isEmpty()) {

                        SerializablePair<ComputationState, Substitution> pair =
                                unify(firstAtom, atom);

                        ComputationState state = pair.first();

                        if (state instanceof ComputationFailed) {

                            log.log(
                                    state,
                                    new MGUResult(unifiedAtoms, unifier.clone(), substitution));

                            return new ExplainedResult<>(Optional.empty(), log);
                        } else {
                            substitution = pair.second();
                        }

                        tempAtoms.add((RelationAtom) substitution.apply(firstAtom));

                        tempAtoms.add((RelationAtom) substitution.apply(atom));
                    } else {
                        tempAtoms.add((RelationAtom) substitution.apply(atom));
                    }
                }
            }
            unifiedAtoms = tempAtoms;
            compose(substitution, unifier);

            log.log(
                    new ComputationInProgress(),
                    new MGUResult(unifiedAtoms, unifier.clone(), substitution));
        }

        log.log(new ComputationSuccess(), new MGUResult(unifiedAtoms, unifier, new Substitution()));
        return new ExplainedResult<>(Optional.of(unifier), log);
    }

    // TODO should be replaced by a proper Composition in utils later
    private static void compose(Substitution substitution, Substitution unifier) {
        Variable key = substitution.firstKey();
        Term value = substitution.get(key);

        if (!unifier.containsKey(key)) {
            unifier.put(key, value);
        }

        for (Entry<Variable, Term> entry : unifier.entrySet()) {
            if (entry.getValue().equals(key)) {
                unifier.put(entry.getKey(), value);
            }
        }
    }

    private static SerializablePair<ComputationState, Substitution> unify(
            RelationAtom firstAtom, RelationAtom secondAtom) {

        Iterator<TermOrFormula> firstIterator = firstAtom.getChildren().iterator();
        Iterator<TermOrFormula> secondIterator = secondAtom.getChildren().iterator();

        while (firstIterator.hasNext()) {
            TermOrFormula firstSubTerm = firstIterator.next();
            TermOrFormula secondSubTerm = secondIterator.next();

            if (!firstSubTerm.equals(secondSubTerm)) {
                Term firstTerm = (Term) firstSubTerm;
                Term secondTerm = (Term) secondSubTerm;

                if (firstTerm.isVariable()) {
                    Variable variable = (Variable) firstTerm;

                    // there is no unifier
                    if (secondTerm.getVariables().contains(variable)) {

                        return new SerializablePair<>(
                                new MGUComputationVariableRecursion(
                                        firstAtom, secondAtom, variable, secondTerm),
                                new Substitution());
                    }

                    return new SerializablePair<>(
                            new ComputationInProgress(), new Substitution(variable, secondTerm));
                }

                if (secondTerm.isVariable()) {
                    Variable variable = (Variable) secondTerm;

                    // there is no unifier
                    if (firstTerm.getVariables().contains(variable)) {

                        return new SerializablePair<>(
                                new MGUComputationVariableRecursion(
                                        secondAtom, firstAtom, variable, firstTerm),
                                new Substitution());
                    }

                    return new SerializablePair<>(
                            new ComputationInProgress(), new Substitution(variable, firstTerm));
                }

                // if both subterms are not variables, there is no unifier
                return new SerializablePair<>(
                        new MGUComputationDifferentNonVariableTerms(
                                firstAtom, secondAtom, firstTerm, secondTerm),
                        new Substitution());
            }
        }
        return null;
    }

    public Substitution clone() {
        Substitution result = new Substitution();

        for (Entry<Variable, Term> entry : this.entrySet()) {
            result.substitute(entry.getKey().clone(), (Term) entry.getValue().clone());
        }

        return result;
    }
}
