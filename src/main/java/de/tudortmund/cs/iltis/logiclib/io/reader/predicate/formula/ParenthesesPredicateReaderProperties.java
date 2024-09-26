package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import java.io.Serializable;
import java.util.Set;

/**
 * Properties for use in {@link PredicateReaderProperties} specifying which parentheses types are
 * allowed to be used around formulae, terms, and around arguments of function/relation symbols.
 */
public class ParenthesesPredicateReaderProperties implements Serializable {

    /** Instances of this class should only be made via the {@link #createDefault()}-methods. */
    private ParenthesesPredicateReaderProperties() {}

    /**
     * Determines, which precedence operators (parentheses, brackets, braces) are allowed to be used
     * around formulae. Default value is: none. Never null
     */
    protected Set<ParenthesesType> parensAllowedAroundFormulae = new ListSet<>();

    /**
     * Determines, which precedence operators (parentheses, brackets, braces) are allowed to be used
     * around arguments. Default value is: none. Never null
     */
    protected Set<ParenthesesType> parensAllowedAroundArguments = new ListSet<>();

    /**
     * Determines, which precedence operators (parentheses, brackets, braces) are allowed to be used
     * around terms. Default value is: none. Never null
     */
    protected Set<ParenthesesType> parensAllowedAroundTerms = new ListSet<>();

    /**
     * @see #parensAllowedAroundFormulae
     */
    public Set<ParenthesesType> getParensAllowedAroundFormulae() {
        return new ListSet<>(parensAllowedAroundFormulae);
    }

    /**
     * @see #parensAllowedAroundArguments
     */
    public Set<ParenthesesType> getParensAllowedAroundArguments() {
        return new ListSet<>(parensAllowedAroundArguments);
    }

    /**
     * @see #parensAllowedAroundTerms
     */
    public Set<ParenthesesType> getParensAllowedAroundTerms() {
        return new ListSet<>(parensAllowedAroundTerms);
    }

    /**
     * @see #parensAllowedAroundFormulae
     */
    public boolean isAllowedAroundFormulae(ParenthesesType type) {
        return parensAllowedAroundFormulae.contains(type);
    }

    /**
     * @see #parensAllowedAroundArguments
     */
    public boolean isAllowedAroundArguments(ParenthesesType type) {
        return parensAllowedAroundArguments.contains(type);
    }

    /**
     * @see #parensAllowedAroundTerms
     */
    public boolean isAllowedAroundTerms(ParenthesesType type) {
        return parensAllowedAroundTerms.contains(type);
    }

    /**
     * @see #parensAllowedAroundFormulae
     */
    public void clearParensAllowedAroundFormulae() {
        parensAllowedAroundFormulae.clear();
    }

    /**
     * @see #parensAllowedAroundFormulae
     */
    public void addParensAllowedAroundFormulae(ParenthesesType... types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) {
            if (type == null) throw new IllegalArgumentException("no argument may be null");
            parensAllowedAroundFormulae.add(type);
        }
    }

    /**
     * @see #parensAllowedAroundFormulae
     */
    public void addParensAllowedAroundFormulae(Iterable<ParenthesesType> types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) addParensAllowedAroundFormulae(type);
    }

    /**
     * @see #parensAllowedAroundArguments
     */
    public void clearParensAllowedAroundArguments() {
        parensAllowedAroundArguments.clear();
    }

    /**
     * @see #parensAllowedAroundArguments
     */
    public void addParensAllowedAroundArguments(ParenthesesType... types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) {
            if (type == null) throw new IllegalArgumentException("no argument may be null");
            parensAllowedAroundArguments.add(type);
        }
    }

    /**
     * @see #parensAllowedAroundArguments
     */
    public void addParensAllowedAroundArguments(Iterable<ParenthesesType> types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) addParensAllowedAroundArguments(type);
    }

    /**
     * @see #parensAllowedAroundTerms
     */
    public void clearParensAllowedAroundTerms() {
        parensAllowedAroundTerms.clear();
    }

    /**
     * @see #parensAllowedAroundTerms
     */
    public void addParensAllowedAroundTerms(ParenthesesType... types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) {
            if (type == null) throw new IllegalArgumentException("no argument may be null");
            parensAllowedAroundTerms.add(type);
        }
    }

    /**
     * @see #parensAllowedAroundTerms
     */
    public void addParensAllowedAroundTerms(Iterable<ParenthesesType> types) {
        if (types == null) throw new IllegalArgumentException("no argument may be null");
        for (ParenthesesType type : types) addParensAllowedAroundTerms(type);
    }

    @Override
    public String toString() {
        return "ParenthesesPredicateReaderProperties [parensAllowedAroundFormulae="
                + parensAllowedAroundFormulae
                + ", parensAllowedAroundArguments="
                + parensAllowedAroundArguments
                + ", parensAllowedAroundTerms="
                + parensAllowedAroundTerms
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + parensAllowedAroundArguments.hashCode();
        result = prime * result + parensAllowedAroundFormulae.hashCode();
        result = prime * result + parensAllowedAroundTerms.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ParenthesesPredicateReaderProperties other = (ParenthesesPredicateReaderProperties) obj;
        return parensAllowedAroundArguments.equals(other.parensAllowedAroundArguments)
                && parensAllowedAroundFormulae.equals(other.parensAllowedAroundFormulae)
                && parensAllowedAroundTerms.equals(other.parensAllowedAroundTerms);
    }

    // No @Override due to GWT
    public ParenthesesPredicateReaderProperties clone() {
        ParenthesesPredicateReaderProperties clone = new ParenthesesPredicateReaderProperties();
        clone.addParensAllowedAroundArguments(parensAllowedAroundArguments);
        clone.addParensAllowedAroundFormulae(parensAllowedAroundFormulae);
        clone.addParensAllowedAroundTerms(parensAllowedAroundTerms);
        return clone;
    }

    /**
     * Creates a properties object, allowing brackets only around formulae, and parentheses at any
     * spot.
     */
    public static ParenthesesPredicateReaderProperties createDefault() {
        return createDefault(true, false, false);
    }

    /** Creates a properties object, allowing brackets as given and parentheses at any spot. */
    public static ParenthesesPredicateReaderProperties createDefault(
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {

        ParenthesesPredicateReaderProperties props = new ParenthesesPredicateReaderProperties();
        props.addParensAllowedAroundFormulae(ParenthesesType.PARENTHESES);
        props.addParensAllowedAroundArguments(ParenthesesType.PARENTHESES);
        props.addParensAllowedAroundTerms(ParenthesesType.PARENTHESES);
        if (allowBracketsAroundFormulae)
            props.addParensAllowedAroundFormulae(ParenthesesType.BRACKETS);
        if (allowBracketsAroundArguments)
            props.addParensAllowedAroundArguments(ParenthesesType.BRACKETS);
        if (allowBracketsAroundTerms) props.addParensAllowedAroundTerms(ParenthesesType.BRACKETS);
        return props;
    }
}
