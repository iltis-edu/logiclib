package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.general.Data;
import java.util.ArrayList;
import java.util.List;

/** Representation of a predicate logical relation symbol. */
public class RelationAtom extends Formula {

    public RelationAtom(RelationSymbol name) {
        super(true, name);
        if (name.getArity() != 0)
            throw new IllegalArgumentException(
                    "Symbol '" + name + "' cannot be used without subterms");
    }

    public RelationAtom(IndexedSymbol name) {
        this(new RelationSymbol(name, 0, false));
    }

    public RelationAtom(String name) {
        this(new RelationSymbol(name, 0, false));
    }

    public RelationAtom(RelationSymbol name, Iterable<Term> subterms) {
        super(true, name, subterms);
        if (name.getArity() != getArity())
            throw new IllegalArgumentException(
                    "Arity of relation symbol ("
                            + name.getArity()
                            + ") does not match number of subterms ("
                            + getArity()
                            + ")");
    }

    public RelationAtom(IndexedSymbol name, boolean infix, Iterable<Term> subterms) {
        super(true, new RelationSymbol(name, Data.getSize(subterms), infix), subterms);
    }

    public RelationAtom(String name, boolean infix, Iterable<Term> subterms) {
        this(new IndexedSymbol(name), infix, subterms);
    }

    public RelationAtom(String name, Iterable<Term> subterms) {
        this(name, false, subterms);
    }

    public RelationAtom(RelationSymbol name, Term... subterms) {
        super(true, name, subterms);
        if (name.getArity() != getArity())
            throw new IllegalArgumentException(
                    "Arity of relation symbol ("
                            + name.getArity()
                            + ") does not match number of subterms ("
                            + getArity()
                            + ")");
    }

    public RelationAtom(IndexedSymbol name, boolean infix, Term... subterms) {
        super(true, new RelationSymbol(name, subterms.length, infix), subterms);
    }

    public RelationAtom(String name, boolean infix, Term... subterms) {
        this(new IndexedSymbol(name), infix, subterms);
    }

    public RelationAtom(String name, Term... subterms) {
        this(name, false, subterms);
    }

    @Override
    public FormulaType getType() {
        if (isInfix()) return FormulaType.INFIX_RELATION_ATOM;
        else return FormulaType.PREFIX_RELATION_ATOM;
    }

    public boolean isInfix() {
        return ((RelationSymbol) name).isInfix();
    }

    @Override
    public RelationSymbol getName() {
        return (RelationSymbol) super.getName();
    }

    @Override
    public RelationAtom clone() {
        List<Term> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((Term) child.clone()));
        return new RelationAtom(getName().clone(), clonedChildren);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private RelationAtom() {}
}
