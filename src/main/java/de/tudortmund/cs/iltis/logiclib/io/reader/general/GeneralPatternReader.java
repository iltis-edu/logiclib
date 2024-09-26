package de.tudortmund.cs.iltis.logiclib.io.reader.general;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingReader;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesChecker;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Triple;

/** A reader designated to encapsulate code used for reader for multiple kinds of patterns. */
public abstract class GeneralPatternReader<
                ParserOutputT, ReaderOutputT, ParserT extends AbstractParser>
        extends CustomizableLexingReader<ParserOutputT, ReaderOutputT, ParserT> {

    public GeneralPatternReader(CustomizableLexingPropertiesProvidable properties) {
        super(properties);
    }

    public GeneralPatternReader(
            CustomizableLexingPropertiesProvidable properties, ParenthesesChecker parenChecker) {

        super(properties, parenChecker);
    }

    protected Triple<List<Token>, List<ParsingFault>, Boolean> convertReverseImplicationToken(
            Token token) {
        List<Token> newTokenList = new ArrayList<>();
        List<ParsingFault> faultList = new ArrayList<>();
        newTokenList.add(token);
        faultList.add(
                new ParsingFault(
                        GeneralFormulaFaultReason.IMPLICATION_IN_WRONG_DIRECTION,
                        token.getLine() - 1,
                        token.getCharPositionInLine(),
                        token.getText()));
        return new Triple<>(newTokenList, faultList, true);
    }
}
