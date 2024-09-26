package de.tudortmund.cs.iltis.logiclib.io.reader.general;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexer;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingReader;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFault;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesChecker;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Triple;

/**
 * A reader designated to encapsulate code used for reader for multiple kinds of formulae.
 *
 * @param <ParserOutputT> the type the parsing process returns
 * @param <ReaderOutputT> the type the {@link #read(Object)}-method returns
 * @param <ParserT> the type of parser used for parsing
 */
public abstract class GeneralFormulaReader<
                ParserOutputT, ReaderOutputT, ParserT extends AbstractParser>
        extends CustomizableLexingReader<ParserOutputT, ReaderOutputT, ParserT> {

    /**
     * Creates a {@link GeneralFormulaReader} <b>without</b> testing for imbalanced or disallowed
     * parentheses.
     *
     * @param properties the properties for the {@link CustomizableLexer}; are cloned
     */
    public GeneralFormulaReader(CustomizableLexingPropertiesProvidable properties) {
        super(properties);
    }

    /**
     * Creates a {@link GeneralFormulaReader} with the given parentheses checker.
     *
     * @param properties the properties for the {@link CustomizableLexer}; are cloned
     * @param parenChecker the parentheses checker to use
     */
    public GeneralFormulaReader(
            CustomizableLexingPropertiesProvidable properties, ParenthesesChecker parenChecker) {
        super(properties, parenChecker);
    }

    /**
     * A fault for an inverse implication is raised. Is used for {@link
     * #registerPostLexConverter(java.util.function.Function)} in subclasses.
     */
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
