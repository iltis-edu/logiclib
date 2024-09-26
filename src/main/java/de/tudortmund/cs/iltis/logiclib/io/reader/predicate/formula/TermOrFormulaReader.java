package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import org.antlr.v4.runtime.TokenStream;

/** Reader for predicate logical terms or formulae. */
public abstract class TermOrFormulaReader<ParserOutputT, FormulaT extends TermOrFormula>
        extends GeneralPredicateReader<ParserOutputT, FormulaT, FormulaT, FormulaSetParser> {

    /**
     * Creates a new TermOrFormulaReader which lexes SafeText and Unicode and allows brackets only
     * around formulae.
     */
    public TermOrFormulaReader() {
        super();
    }

    /** Creates a new TermOrFormulaReader which lexes SafeText and Unicode. */
    public TermOrFormulaReader(
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(allowBracketsAroundFormulae, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new TermOrFormulaReader. */
    public TermOrFormulaReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundFormulae,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(
                lexSafeText,
                lexUnicode,
                allowBracketsAroundFormulae,
                allowBracketsAroundArguments,
                allowBracketsAroundTerms);
    }

    /** Creates a new TermOrFormulaReader. */
    public TermOrFormulaReader(PredicateReaderProperties props) {
        super(props);
    }

    /** {@inheritDoc} */
    @Override
    protected FormulaSetParser prepareParser(TokenStream tokenStream) {
        // create a parser that feeds off the tokens buffer
        FormulaSetParser parser = new FormulaSetParser(tokenStream);
        parser.setProperties(getProperties());
        return parser;
    }
}
