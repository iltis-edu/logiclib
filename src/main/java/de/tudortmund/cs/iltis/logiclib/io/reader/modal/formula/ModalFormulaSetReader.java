package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaSetParser;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesChecker;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import org.antlr.v4.runtime.TokenStream;

public abstract class ModalFormulaSetReader<ParserOutputT, ReaderOutputT>
        extends GeneralModalReader<ParserOutputT, ReaderOutputT, ModalFormulaSetParser> {

    /** {@inheritDoc} */
    public ModalFormulaSetReader(ModalReaderProperties properties) {
        super(properties);
        init();
    }

    /** {@inheritDoc} */
    public ModalFormulaSetReader(
            ModalReaderProperties properties, ParenthesesChecker parenChecker) {
        super(properties, parenChecker);
        init();
    }

    private void init() {
        ((ModalReaderProperties) properties).allowParenthesesType(ParenthesesType.BRACES);
    }

    @Override
    protected ModalFormulaSetParser prepareParser(TokenStream tokenStream) {
        // create a parser that feeds off the tokens buffer
        return new ModalFormulaSetParser(tokenStream);
    }
}
