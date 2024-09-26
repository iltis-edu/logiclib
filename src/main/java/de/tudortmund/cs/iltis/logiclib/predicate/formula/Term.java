package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.TermReader;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;

/** Basic class for a predicate logical term. */
public abstract class Term extends TermOrFormula {

    /**
     * Uses {@link SignaturePolicy#TU_DORTMUND_LS1_LOGIK_POLICY} as {@link SignaturePolicy}.
     *
     * @throws IncorrectParseInputException if text can not be parsed.
     */
    public static Term parse(String text) throws IncorrectParseInputException {
        TermReader reader = new TermReader();
        return reader.read(text, SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY);
    }

    public static Term parse(String text, SignatureCheckable signature)
            throws IncorrectParseInputException {

        TermReader reader = new TermReader();
        return reader.read(text, signature);
    }

    public Term(final IndexedSymbol name) {
        super(true, name);
    }

    public Term(final IndexedSymbol name, final Iterable<Term> subterms) {
        super(true, name, subterms);
    }

    public Term(final IndexedSymbol name, final Term... subterms) {
        super(true, name, subterms);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;

    /** for GWT serialization */
    protected Term() {
        super();
    }
}
