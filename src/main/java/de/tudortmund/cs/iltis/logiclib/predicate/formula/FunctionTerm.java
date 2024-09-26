package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FormulaType;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.general.Data;
import java.util.ArrayList;
import java.util.List;

/** Representation of a predicate logical function symbol. */
public class FunctionTerm extends Term {

    public FunctionTerm(FunctionSymbol name) {
        super(name);
        if (name.getArity() != 0)
            throw new IllegalArgumentException(
                    "Symbol '" + name + "' cannot be used without subterms");
    }

    public FunctionTerm(IndexedSymbol name) {
        this(new FunctionSymbol(name, 0, false));
    }

    public FunctionTerm(String name) {
        this(new FunctionSymbol(name, 0, false));
    }

    public FunctionTerm(FunctionSymbol name, Iterable<Term> subterms) {
        super(name, subterms);
        if (name.getArity() != getArity())
            throw new IllegalArgumentException(
                    "Arity of function symbol ("
                            + name.getArity()
                            + ") does not match number of subterms ("
                            + getArity()
                            + ")");
    }

    public FunctionTerm(IndexedSymbol name, boolean infix, Iterable<Term> subterms) {
        super(new FunctionSymbol(name, Data.getSize(subterms), infix), subterms);
    }

    public FunctionTerm(String name, boolean infix, Iterable<Term> subterms) {
        this(new IndexedSymbol(name), infix, subterms);
    }

    public FunctionTerm(String name, Iterable<Term> subterms) {
        this(name, false, subterms);
    }

    public FunctionTerm(FunctionSymbol name, Term... subterms) {
        super(name, subterms);
        if (name.getArity() != getArity())
            throw new IllegalArgumentException(
                    "Arity of function symbol ("
                            + name.getArity()
                            + ") does not match number of subterms ("
                            + getArity()
                            + ")");
    }

    public FunctionTerm(IndexedSymbol name, boolean infix, Term... subterms) {
        super(new FunctionSymbol(name, subterms.length, infix), subterms);
    }

    public FunctionTerm(String name, boolean infix, Term... subterms) {
        this(new IndexedSymbol(name), infix, subterms);
    }

    public FunctionTerm(String name, Term... subterms) {
        this(name, false, subterms);
    }

    @Override
    public FormulaType getType() {
        if (getArity() == 0) return FormulaType.CONSTANT;
        else if (isInfix()) return FormulaType.INFIX_FUNCTION_TERM;
        else return FormulaType.PREFIX_FUNCTION_TERM;
    }

    public boolean isInfix() {
        return ((FunctionSymbol) name).isInfix();
    }

    @Override
    public FunctionSymbol getName() {
        return (FunctionSymbol) super.getName();
    }

    @Override
    public FunctionTerm clone() {
        List<Term> clonedChildren = new ArrayList<>();
        children.forEach(child -> clonedChildren.add((Term) child.clone()));
        return new FunctionTerm(getName().clone(), clonedChildren);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    @SuppressWarnings("unused")
    private FunctionTerm() {}
}
