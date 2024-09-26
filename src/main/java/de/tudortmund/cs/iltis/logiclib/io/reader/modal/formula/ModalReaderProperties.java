package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.ModalFormulaParserOperators;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.FormulaParserOperators;
import de.tudortmund.cs.iltis.utils.StringUtils;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingProperties;
import de.tudortmund.cs.iltis.utils.io.parser.customizable.CustomizableLexingPropertiesProvidable;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsableSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesType;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.RegularSymbolSplittingPolicy;
import java.io.Serializable;
import java.util.stream.Collectors;

/** A properties object used for the modal readers to define all tokens needed. */
public class ModalReaderProperties extends CustomizableLexingProperties implements Serializable {

    /** Instances of this class should only be made via the {@link #createDefault()}-methods. */
    private ModalReaderProperties() {}

    @Override
    public ParsableSymbol getSymbolForNonSeparatedText() {
        return ModalFormulaParserOperators.NOT_SEPARATED_VARIABLES;
    }

    @Override
    public ParsableSymbol getSymbolForISymbols() {
        return ModalFormulaParserOperators.VARIABLE;
    }

    @Override
    public ParsableSymbol getSymbolForSeparation() {
        return ModalFormulaParserOperators.WHITESPACE;
    }

    @Override
    public CustomizableLexingPropertiesProvidable clone() {
        ModalReaderProperties props = new ModalReaderProperties();
        props.addSeparationSymbols(separationSymbols);
        props.addSeparatingOperators(separatingOperators);
        props.addNonSeparatingOperators(nonSeparatingOperators);
        props.setSymbolSplittingPolicy(symbolSplittingPolicy);
        props.addParenthesesSymbols(parenthesesMap, allowedParenthesesTypes);
        return props;
    }

    private void addAsciiSymbols() {
        addSeparatingOperator("!", ModalFormulaParserOperators.NEG);
        addSeparatingOperator("&", ModalFormulaParserOperators.AND);
        addSeparatingOperator("|", ModalFormulaParserOperators.OR);
        addSeparatingOperator("<->", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("<=>", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("<\uFEFF-\uFEFF>", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("->", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("-\uFEFF>", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("<-", ModalFormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("<\uFEFF-", ModalFormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("1", ModalFormulaParserOperators.TRUE);
        addSeparatingOperator("0", ModalFormulaParserOperators.FALSE);
        addSeparatingOperator("{", ModalFormulaParserOperators.OBRACE);
        addSeparatingOperator("}", ModalFormulaParserOperators.CBRACE);
        addSeparatingOperator("[", ModalFormulaParserOperators.OBRACKET);
        addSeparatingOperator("]", ModalFormulaParserOperators.CBRACKET);
        addSeparatingOperator("(", ModalFormulaParserOperators.OPAREN);
        addSeparatingOperator(")", ModalFormulaParserOperators.CPAREN);
        addSeparatingOperator(",", ModalFormulaParserOperators.ARGUMENTSEP);

        addNonSeparatingOperator("not", ModalFormulaParserOperators.NEG);
        addNonSeparatingOperator("dia", ModalFormulaParserOperators.DIAMOND);
        addNonSeparatingOperator("box", ModalFormulaParserOperators.BOX);

        addSeparationSymbols(" ", "\t", "\n");
    }

    private void addUnicodeSymbols() {
        addSeparatingOperator("¬", ModalFormulaParserOperators.NEG);
        addSeparatingOperator("◇", ModalFormulaParserOperators.DIAMOND);
        addSeparatingOperator("\u25A1", ModalFormulaParserOperators.BOX);
        addSeparatingOperator("\u25FB", ModalFormulaParserOperators.BOX);
        addSeparatingOperator("\u2610", ModalFormulaParserOperators.BOX);
        addSeparatingOperator("∧", ModalFormulaParserOperators.AND);
        addSeparatingOperator("∨", ModalFormulaParserOperators.OR);
        addSeparatingOperator("↔", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("→", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("←", ModalFormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("⊤", ModalFormulaParserOperators.TRUE);
        addSeparatingOperator("⊥", ModalFormulaParserOperators.FALSE);
        addSeparatingOperator("{", ModalFormulaParserOperators.OBRACE);
        addSeparatingOperator("}", ModalFormulaParserOperators.CBRACE);
        addSeparatingOperator("[", ModalFormulaParserOperators.OBRACKET);
        addSeparatingOperator("]", ModalFormulaParserOperators.CBRACKET);
        addSeparatingOperator("(", ModalFormulaParserOperators.OPAREN);
        addSeparatingOperator(",", ModalFormulaParserOperators.ARGUMENTSEP);

        addSeparationSymbols(
                StringUtils.getUnicodeWhitespaces().stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()));
    }

    private void addLatexSymbols() {
        addSeparatingOperator("\\lnot", ModalFormulaParserOperators.NEG);
        addSeparatingOperator("\\neg", ModalFormulaParserOperators.NEG);

        addSeparatingOperator("\\diamond", ModalFormulaParserOperators.DIAMOND);
        addSeparatingOperator("\\Diamond", ModalFormulaParserOperators.DIAMOND);
        addSeparatingOperator("\\ldia", ModalFormulaParserOperators.DIAMOND);

        addSeparatingOperator("\\box", ModalFormulaParserOperators.BOX);
        addSeparatingOperator("\\Box", ModalFormulaParserOperators.BOX);
        addSeparatingOperator("\\lbox", ModalFormulaParserOperators.BOX);

        addSeparatingOperator("\\wedge", ModalFormulaParserOperators.AND);
        addSeparatingOperator("\\land", ModalFormulaParserOperators.AND);

        addSeparatingOperator("\\vee", ModalFormulaParserOperators.OR);
        addSeparatingOperator("\\lor", ModalFormulaParserOperators.OR);

        addSeparatingOperator("\\leftrightarrow", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\rightleftarrow", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\Leftrightarrow", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\Rightleftarrow", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\iff", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\equiv", ModalFormulaParserOperators.EQUIV);
        addSeparatingOperator("\\lequiv", ModalFormulaParserOperators.EQUIV);

        addSeparatingOperator("\\to", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\rightarrow", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\Rightarrow", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\implies", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\supset", ModalFormulaParserOperators.IMPLIES);
        addSeparatingOperator("\\limp", ModalFormulaParserOperators.IMPLIES);

        addSeparatingOperator("\\leftarrow", ModalFormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("\\Leftarrow", ModalFormulaParserOperators.REVERSE_IMPLIES);
        addSeparatingOperator("\\impliedby", ModalFormulaParserOperators.REVERSE_IMPLIES);

        addSeparatingOperator("\\top", ModalFormulaParserOperators.TRUE);
        addSeparatingOperator("\\bot", ModalFormulaParserOperators.FALSE);

        String[] openingParenModifiers = {
            "\\big", "\\Big", "\\bigg", "\\Bigg", "\\bigl", "\\Bigl", "\\biggl", "\\Biggl", "\\left"
        };
        String[] closingParenModifiers = {
            "\\big", "\\Big", "\\bigg", "\\Bigg", "\\bigr", "\\Bigr", "\\biggr", "\\Biggr",
            "\\right"
        };
        addModifiedParenthesesSymbols(openingParenModifiers, "(", FormulaParserOperators.OPAREN);
        addModifiedParenthesesSymbols(
                openingParenModifiers,
                new String[] {"\\lbrack", "["},
                FormulaParserOperators.OBRACKET);
        addModifiedParenthesesSymbols(
                openingParenModifiers,
                new String[] {"\\lbrace", "\\{"},
                FormulaParserOperators.OBRACE);
        addModifiedParenthesesSymbols(closingParenModifiers, ")", FormulaParserOperators.CPAREN);
        addModifiedParenthesesSymbols(
                closingParenModifiers,
                new String[] {"\\rbrack", "]"},
                FormulaParserOperators.CBRACKET);
        addModifiedParenthesesSymbols(
                closingParenModifiers,
                new String[] {"\\rbrace", "\\}"},
                FormulaParserOperators.CBRACE);

        addSeparationSymbols(
                // LaTeX space commands
                " ", "\t", "\n", "\\ ", "\\;", "\\,", "\\\\");
    }

    private void addModifiedParenthesesSymbols(
            String[] modifiers, String[] symbols, FormulaParserOperators op) {
        for (String symbol : symbols)
            for (String modifier : modifiers) addSeparatingOperator(modifier + symbol, op);
    }

    private void addModifiedParenthesesSymbols(
            String[] modifiers, String symbol, FormulaParserOperators op) {
        addModifiedParenthesesSymbols(modifiers, new String[] {symbol}, op);
    }

    public static ModalReaderProperties createDefault() {
        ModalReaderProperties props = new ModalReaderProperties();

        props.setSymbolSplittingPolicy(RegularSymbolSplittingPolicy.ALL_UNARY_SYMBOLS_POLICY);
        props.addAsciiSymbols();
        props.addUnicodeSymbols();

        props.addAllowedParenthesesSymbol(
                ParenthesesType.BRACES,
                ModalFormulaParserOperators.OBRACE,
                ModalFormulaParserOperators.CBRACE);
        props.addAllowedParenthesesSymbol(
                ParenthesesType.BRACKETS,
                ModalFormulaParserOperators.OBRACKET,
                ModalFormulaParserOperators.CBRACKET);
        props.addAllowedParenthesesSymbol(
                ParenthesesType.PARENTHESES,
                ModalFormulaParserOperators.OPAREN,
                ModalFormulaParserOperators.CPAREN);

        return props;
    }

    public static ModalReaderProperties createDefaultWithLatex() {
        ModalReaderProperties props = createDefault();

        props.addLatexSymbols();

        return props;
    }
}
