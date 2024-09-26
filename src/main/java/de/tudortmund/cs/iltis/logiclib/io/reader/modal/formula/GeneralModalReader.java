package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaParserOperators;
import de.tudortmund.cs.iltis.logiclib.io.reader.general.GeneralFormulaReader;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesChecker;

public abstract class GeneralModalReader<
                ParserOutputT, ReaderOutputT, ParserT extends AbstractParser>
        extends GeneralFormulaReader<ParserOutputT, ReaderOutputT, ParserT> {

    /** {@inheritDoc} */
    public GeneralModalReader(ModalReaderProperties properties) {
        super(properties);
        init();
    }

    /** {@inheritDoc} */
    public GeneralModalReader(ModalReaderProperties properties, ParenthesesChecker parenChecker) {
        super(properties, parenChecker);
        init();
    }

    private void init() {
        registerPostLexConverter(
                token -> {
                    if (token.getType()
                            == ModalFormulaParserOperators.REVERSE_IMPLIES.getTokenType())
                        return convertReverseImplicationToken(token);
                    return null;
                });
    }
}
