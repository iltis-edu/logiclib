package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;
import java.util.Set;

/**
 * Properties for use in {@link PredicateReaderProperties} specifying which infix symbols are used
 * and whether other symbols may be used as infix operators.
 */
public class InfixPredicateReaderProperties implements Serializable {

    /** Instances of this class should only be made via the {@link #createDefault()}-methods. */
    private InfixPredicateReaderProperties() {}

    /**
     * flag, if only the symbols listed in {@link #infixRelationSymbols} shall be permitted as infix
     * relation symbols; default value is false
     */
    private boolean infixRelationsRestricted = false;

    /**
     * flag, if only the symbols listed in {@link #infixFunctionSymbols} shall be permitted as infix
     * function symbols; default value is false
     */
    private boolean infixFunctionsRestricted = false;

    /** See {@link PredicateReaderProperties#infixProps#infixRelationsRestricted} */
    public boolean areInfixRelationsRestricted() {
        return infixRelationsRestricted;
    }

    /** See {@link PredicateReaderProperties#infixProps#infixRelationsRestricted} */
    public void setInfixRelationsRestricted(boolean infixRelationsRestricted) {
        this.infixRelationsRestricted = infixRelationsRestricted;
    }

    /** See {@link PredicateReaderProperties#infixProps#infixFunctionsRestricted} */
    public boolean areInfixFunctionsRestricted() {
        return infixFunctionsRestricted;
    }

    /** See {@link PredicateReaderProperties#infixProps#infixRelationsRestricted} */
    public void setInfixFunctionsRestricted(boolean infixFunctionsRestricted) {
        this.infixFunctionsRestricted = infixFunctionsRestricted;
    }

    /**
     * set of all infix relation symbols; never null and no element is null; default value is none
     *
     * <p>1. these symbols are separating the input (it doesn't matter where they appear); 2. if
     * {@link #infixRelationsRestricted} is set, only these symbols are permitted as infix relation
     * symbols
     */
    private Set<String> infixRelationSymbols = new ListSet<>();

    /**
     * set of all infix function symbols; never null and no element is null; default value is none
     *
     * <p>1. these symbols are separating the input (it doesn't matter where they appear); 2. if
     * {@link #infixFunctionsRestricted} is set, only these symbols are permitted as infix function
     * symbols
     */
    private Set<String> infixFunctionSymbols = new ListSet<>();

    /**
     * @see #infixRelationSymbols
     */
    public Set<String> getInfixRelationSymbols() {
        return new ListSet<>(infixRelationSymbols);
    }

    /**
     * @see #infixRelationSymbols
     */
    public void clearInfixRelationSymbols() {
        infixRelationSymbols.clear();
    }

    /**
     * @see #infixRelationSymbols
     */
    public void addInfixRelationSymbols(String... symbols) {
        if (symbols == null) throw new IllegalArgumentException("no argument may be null");
        for (String symbol : symbols) {
            if (symbol == null) throw new IllegalArgumentException("no argument may be null");
            infixRelationSymbols.add(symbol);
        }
    }

    /**
     * @see #infixRelationSymbols
     */
    public void addInfixRelationSymbols(Iterable<String> symbols) {
        if (symbols == null) throw new IllegalArgumentException("no argument may be null");
        for (String symbol : symbols) {
            addInfixRelationSymbols(symbol);
        }
    }

    /**
     * @see #infixFunctionSymbols
     */
    public Set<String> getInfixFunctionSymbols() {
        return new ListSet<>(infixFunctionSymbols);
    }

    /**
     * @see #infixFunctionSymbols
     */
    public void clearInfixFunctionsSymbols() {
        infixFunctionSymbols.clear();
    }

    /**
     * @see #infixFunctionSymbols
     */
    public void addInfixFunctionsSymbols(String... symbols) {
        if (symbols == null) throw new IllegalArgumentException("no argument may be null");
        for (String symbol : symbols) {
            if (symbol == null) throw new IllegalArgumentException("no argument may be null");
            infixFunctionSymbols.add(symbol);
        }
    }

    /**
     * @see #infixFunctionSymbols
     */
    public void addInfixFunctionsSymbols(Iterable<String> symbols) {
        if (symbols == null) throw new IllegalArgumentException("no argument may be null");
        for (String symbol : symbols) {
            addInfixFunctionsSymbols(symbol);
        }
    }

    @Override
    public String toString() {
        return "InfixPredicateReaderProperties [infixRelationsRestricted="
                + infixRelationsRestricted
                + ", infixFunctionsRestricted="
                + infixFunctionsRestricted
                + ", infixRelationSymbols="
                + infixRelationSymbols
                + ", infixFunctionSymbols="
                + infixFunctionSymbols
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + infixFunctionSymbols.hashCode();
        result = prime * result + (infixFunctionsRestricted ? 1231 : 1237);
        result = prime * result + infixRelationSymbols.hashCode();
        result = prime * result + (infixRelationsRestricted ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        InfixPredicateReaderProperties other = (InfixPredicateReaderProperties) obj;
        return infixFunctionSymbols.equals(other.infixFunctionSymbols)
                && infixFunctionsRestricted == other.infixFunctionsRestricted
                && infixRelationSymbols.equals(other.infixRelationSymbols)
                && infixRelationsRestricted == other.infixRelationsRestricted;
    }

    // not @Override due to GWT
    public InfixPredicateReaderProperties clone() {
        InfixPredicateReaderProperties clone = new InfixPredicateReaderProperties();
        clone.infixFunctionsRestricted = infixFunctionsRestricted;
        clone.infixRelationsRestricted = infixRelationsRestricted;
        clone.addInfixFunctionsSymbols(infixFunctionSymbols);
        clone.addInfixRelationSymbols(infixRelationSymbols);
        return clone;
    }

    /**
     * Creates a properties object which specifies that infix symbols are not restricted and as
     * infix relation symbols "=" and as infix function symbols"-", "/", and "%" are set.
     */
    public static InfixPredicateReaderProperties createDefault() {
        return createDefault(false);
    }

    /**
     * Creates a properties object which specifies that infix symbols restricted as given and as
     * infix relation symbols "=" and as infix function symbols"-", "/", and "%" are set.
     */
    public static InfixPredicateReaderProperties createDefault(boolean infixRestricted) {
        InfixPredicateReaderProperties props = new InfixPredicateReaderProperties();
        props.addInfixRelationSymbols("=");
        props.addInfixFunctionsSymbols("-", "/", "%");
        if (infixRestricted) {
            props.infixFunctionsRestricted = true;
            props.infixRelationsRestricted = true;
        }
        return props;
    }
}
