package de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaSetParser;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import org.antlr.v4.runtime.TokenStream;

/** Reader for formula sets. Uses {@link FormulaSetParser}. */
public abstract class FormulaSetReader<ParserOutputT, ReaderOutputT>
        extends GeneralPredicateReader<ParserOutputT, ReaderOutputT, Formula, FormulaSetParser> {

    /**
     * Creates a new FormulaSetReader which lexes SafeText and Unicode and does not allow brackets
     * around terms or arguments.
     */
    public FormulaSetReader() {
        super();
    }

    /** Creates a new FormulaSetReader which lexes SafeText and Unicode. */
    public FormulaSetReader(
            boolean allowBracketsAroundArguments, boolean allowBracketsAroundTerms) {
        super(false, allowBracketsAroundArguments, allowBracketsAroundTerms);
    }

    /** Creates a new FormulaSetReader. */
    public FormulaSetReader(
            boolean lexSafeText,
            boolean lexUnicode,
            boolean allowBracketsAroundArguments,
            boolean allowBracketsAroundTerms) {
        super(
                lexSafeText,
                lexUnicode,
                false,
                allowBracketsAroundArguments,
                allowBracketsAroundTerms);
    }

    /** Creates a new DisjunctiveClauseReader. */
    public FormulaSetReader(PredicateReaderProperties props) {
        super(props);
        init();
    }

    private void init() {
        getProperties().allowParenthesesType(ParenthesesType.BRACES);
    }

    @Override
    protected FormulaSetParser prepareParser(TokenStream tokenStream) {
        // create a parser that feeds off the tokens buffer
        FormulaSetParser parser = new FormulaSetParser(tokenStream);
        parser.setProperties(getProperties());
        return parser;
    }
}
