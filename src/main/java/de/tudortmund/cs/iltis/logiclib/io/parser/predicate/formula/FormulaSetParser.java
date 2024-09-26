// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/predicate/formula/FormulaSetParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FormulaSetParser extends AbstractParser {
    static {
        RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int NEG = 1,
            AND = 2,
            OR = 3,
            EQUIV = 4,
            IMPLIES = 5,
            FORALL = 6,
            EXISTS = 7,
            TRUE = 8,
            FALSE = 9,
            OBRACKET = 10,
            CBRACKET = 11,
            OPAREN = 12,
            CPAREN = 13,
            ARGUMENTSEP = 14,
            QUANTIFIERSEP = 15,
            ISYMBOL = 16,
            OBRACE = 17,
            CBRACE = 18,
            RELINFIXISYMBOL = 19,
            FUNINFIXISYMBOL = 20,
            OPERATOR_OR_ISYMBOL = 21,
            REVERSE_IMPLIES = 22,
            WS = 23;
    public static final String[] tokenNames = {
        "<INVALID>",
        "NEG",
        "AND",
        "OR",
        "EQUIV",
        "IMPLIES",
        "FORALL",
        "EXISTS",
        "TRUE",
        "FALSE",
        "OBRACKET",
        "CBRACKET",
        "OPAREN",
        "CPAREN",
        "ARGUMENTSEP",
        "QUANTIFIERSEP",
        "ISYMBOL",
        "OBRACE",
        "CBRACE",
        "RELINFIXISYMBOL",
        "FUNINFIXISYMBOL",
        "OPERATOR_OR_ISYMBOL",
        "REVERSE_IMPLIES",
        "WS"
    };
    public static final int RULE_initFormulaSetSet = 0,
            RULE_formulaSetSet = 1,
            RULE_formulaSetSetContent = 2,
            RULE_formulaSetsOrFormulaeOrEmpty = 3,
            RULE_formulaSetsOrFormulae = 4,
            RULE_formulaSetOrFormula = 5,
            RULE_initFormulaSet = 6,
            RULE_formulaSet = 7,
            RULE_formulaSetWithBraces = 8,
            RULE_formulaSetContent = 9,
            RULE_formulaeOrEmpty = 10,
            RULE_formulae = 11,
            RULE_initFormula = 12,
            RULE_initTerm = 13,
            RULE_formula = 14,
            RULE_superformula = 15,
            RULE_superformulaWithoutOr = 16,
            RULE_superformulaWithoutAnd = 17,
            RULE_superformulaWithoutAndOr = 18,
            RULE_superformulaError = 19,
            RULE_subformula = 20,
            RULE_term = 21,
            RULE_prefixTerm = 22,
            RULE_iSymbol = 23;
    public static final String[] ruleNames = {
        "initFormulaSetSet",
        "formulaSetSet",
        "formulaSetSetContent",
        "formulaSetsOrFormulaeOrEmpty",
        "formulaSetsOrFormulae",
        "formulaSetOrFormula",
        "initFormulaSet",
        "formulaSet",
        "formulaSetWithBraces",
        "formulaSetContent",
        "formulaeOrEmpty",
        "formulae",
        "initFormula",
        "initTerm",
        "formula",
        "superformula",
        "superformulaWithoutOr",
        "superformulaWithoutAnd",
        "superformulaWithoutAndOr",
        "superformulaError",
        "subformula",
        "term",
        "prefixTerm",
        "iSymbol"
    };

    @Override
    public String getGrammarFileName() {
        return "FormulaSetParser.g4";
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    private PredicateReaderProperties properties = PredicateReaderProperties.createDefault();

    public void setProperties(PredicateReaderProperties props) {
        if (props == null) throw new IllegalArgumentException("props must not be null");
        properties = props;
    }

    public PredicateReaderProperties getProperties() {
        return properties;
    }

    public FormulaSetParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class InitFormulaSetSetContext extends ParserRuleContext {
        public FormulaSetSetContext setSet;

        public FormulaSetSetContext formulaSetSet() {
            return getRuleContext(FormulaSetSetContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(FormulaSetParser.EOF, 0);
        }

        public InitFormulaSetSetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initFormulaSetSet;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitInitFormulaSetSet(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitFormulaSetSetContext initFormulaSetSet() throws RecognitionException {
        InitFormulaSetSetContext _localctx = new InitFormulaSetSetContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_initFormulaSetSet);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(48);
                ((InitFormulaSetSetContext) _localctx).setSet = formulaSetSet();
                setState(49);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetSetContext extends ParserRuleContext {
        public FormulaSetSetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetSet;
        }

        public FormulaSetSetContext() {}

        public void copyFrom(FormulaSetSetContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetSetBracesContext extends FormulaSetSetContext {
        public FormulaSetSetContentContext content;

        public TerminalNode OBRACE() {
            return getToken(FormulaSetParser.OBRACE, 0);
        }

        public FormulaSetSetContentContext formulaSetSetContent() {
            return getRuleContext(FormulaSetSetContentContext.class, 0);
        }

        public TerminalNode CBRACE() {
            return getToken(FormulaSetParser.CBRACE, 0);
        }

        public FormulaSetSetBracesContext(FormulaSetSetContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetSetMissingBracesContext extends FormulaSetSetContext {
        public FormulaSetSetContentContext contentNoBraces;

        public FormulaSetSetContentContext formulaSetSetContent() {
            return getRuleContext(FormulaSetSetContentContext.class, 0);
        }

        public FormulaSetSetMissingBracesContext(FormulaSetSetContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetMissingBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetSetContext formulaSetSet() throws RecognitionException {
        FormulaSetSetContext _localctx = new FormulaSetSetContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_formulaSetSet);
        try {
            setState(56);
            switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                case 1:
                    _localctx = new FormulaSetSetBracesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(51);
                        match(OBRACE);
                        setState(52);
                        ((FormulaSetSetBracesContext) _localctx).content = formulaSetSetContent();
                        setState(53);
                        match(CBRACE);
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetSetMissingBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(55);
                        ((FormulaSetSetMissingBracesContext) _localctx).contentNoBraces =
                                formulaSetSetContent();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetSetContentContext extends ParserRuleContext {
        public FormulaSetSetContentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetSetContent;
        }

        public FormulaSetSetContentContext() {}

        public void copyFrom(FormulaSetSetContentContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetSetContentMultipleContext extends FormulaSetSetContentContext {
        public FormulaSetsOrFormulaeOrEmptyContext first;
        public Token ARGUMENTSEP;
        public List<Token> seps = new ArrayList<Token>();
        public FormulaSetsOrFormulaeOrEmptyContext formulaSetsOrFormulaeOrEmpty;
        public List<FormulaSetsOrFormulaeOrEmptyContext> further =
                new ArrayList<FormulaSetsOrFormulaeOrEmptyContext>();

        public FormulaSetsOrFormulaeOrEmptyContext formulaSetsOrFormulaeOrEmpty(int i) {
            return getRuleContext(FormulaSetsOrFormulaeOrEmptyContext.class, i);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public List<FormulaSetsOrFormulaeOrEmptyContext> formulaSetsOrFormulaeOrEmpty() {
            return getRuleContexts(FormulaSetsOrFormulaeOrEmptyContext.class);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public FormulaSetSetContentMultipleContext(FormulaSetSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetContentMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetSetContentEmptyContext extends FormulaSetSetContentContext {
        public FormulaSetSetContentEmptyContext(FormulaSetSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetContentEmpty(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetSetContentSetsOrFormulaeContext
            extends FormulaSetSetContentContext {
        public FormulaSetsOrFormulaeContext formulaSetsOrFormulae() {
            return getRuleContext(FormulaSetsOrFormulaeContext.class, 0);
        }

        public FormulaSetSetContentSetsOrFormulaeContext(FormulaSetSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetContentSetsOrFormulae(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetSetContentContext formulaSetSetContent() throws RecognitionException {
        FormulaSetSetContentContext _localctx = new FormulaSetSetContentContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_formulaSetSetContent);
        int _la;
        try {
            setState(67);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1:
                    _localctx = new FormulaSetSetContentEmptyContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetSetContentSetsOrFormulaeContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(59);
                        formulaSetsOrFormulae();
                    }
                    break;
                case 3:
                    _localctx = new FormulaSetSetContentMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(60);
                        ((FormulaSetSetContentMultipleContext) _localctx).first =
                                formulaSetsOrFormulaeOrEmpty();
                        setState(63);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(61);
                                    ((FormulaSetSetContentMultipleContext) _localctx).ARGUMENTSEP =
                                            match(ARGUMENTSEP);
                                    ((FormulaSetSetContentMultipleContext) _localctx)
                                            .seps.add(
                                                    ((FormulaSetSetContentMultipleContext)
                                                                    _localctx)
                                                            .ARGUMENTSEP);
                                    setState(62);
                                    ((FormulaSetSetContentMultipleContext) _localctx)
                                                    .formulaSetsOrFormulaeOrEmpty =
                                            formulaSetsOrFormulaeOrEmpty();
                                    ((FormulaSetSetContentMultipleContext) _localctx)
                                            .further.add(
                                                    ((FormulaSetSetContentMultipleContext)
                                                                    _localctx)
                                                            .formulaSetsOrFormulaeOrEmpty);
                                }
                            }
                            setState(65);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == ARGUMENTSEP);
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetsOrFormulaeOrEmptyContext extends ParserRuleContext {
        public FormulaSetsOrFormulaeOrEmptyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetsOrFormulaeOrEmpty;
        }

        public FormulaSetsOrFormulaeOrEmptyContext() {}

        public void copyFrom(FormulaSetsOrFormulaeOrEmptyContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetsOrFormulaeOrEmptyNotPresentContext
            extends FormulaSetsOrFormulaeOrEmptyContext {
        public FormulaSetsOrFormulaeOrEmptyNotPresentContext(
                FormulaSetsOrFormulaeOrEmptyContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetsOrFormulaeOrEmptyNotPresent(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetsOrFormulaeOrEmptyPresentContext
            extends FormulaSetsOrFormulaeOrEmptyContext {
        public FormulaSetsOrFormulaeContext formulaSetsOrFormulae() {
            return getRuleContext(FormulaSetsOrFormulaeContext.class, 0);
        }

        public FormulaSetsOrFormulaeOrEmptyPresentContext(FormulaSetsOrFormulaeOrEmptyContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetsOrFormulaeOrEmptyPresent(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetsOrFormulaeOrEmptyContext formulaSetsOrFormulaeOrEmpty()
            throws RecognitionException {
        FormulaSetsOrFormulaeOrEmptyContext _localctx =
                new FormulaSetsOrFormulaeOrEmptyContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_formulaSetsOrFormulaeOrEmpty);
        try {
            setState(71);
            switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                case 1:
                    _localctx = new FormulaSetsOrFormulaeOrEmptyPresentContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(69);
                        formulaSetsOrFormulae();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetsOrFormulaeOrEmptyNotPresentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetsOrFormulaeContext extends ParserRuleContext {
        public FormulaSetsOrFormulaeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetsOrFormulae;
        }

        public FormulaSetsOrFormulaeContext() {}

        public void copyFrom(FormulaSetsOrFormulaeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetsOrFormulaeSingleContext extends FormulaSetsOrFormulaeContext {
        public FormulaSetOrFormulaContext formulaSetOrFormula() {
            return getRuleContext(FormulaSetOrFormulaContext.class, 0);
        }

        public FormulaSetsOrFormulaeSingleContext(FormulaSetsOrFormulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetsOrFormulaeSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetsOrFormulaeMultipleContext extends FormulaSetsOrFormulaeContext {
        public FormulaSetOrFormulaContext firstSet;
        public FormulaSetOrFormulaContext formulaSetOrFormula;
        public List<FormulaSetOrFormulaContext> furtherSets =
                new ArrayList<FormulaSetOrFormulaContext>();

        public FormulaSetOrFormulaContext formulaSetOrFormula(int i) {
            return getRuleContext(FormulaSetOrFormulaContext.class, i);
        }

        public List<FormulaSetOrFormulaContext> formulaSetOrFormula() {
            return getRuleContexts(FormulaSetOrFormulaContext.class);
        }

        public FormulaSetsOrFormulaeMultipleContext(FormulaSetsOrFormulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetsOrFormulaeMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetsOrFormulaeContext formulaSetsOrFormulae() throws RecognitionException {
        FormulaSetsOrFormulaeContext _localctx = new FormulaSetsOrFormulaeContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_formulaSetsOrFormulae);
        try {
            int _alt;
            setState(80);
            switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
                case 1:
                    _localctx = new FormulaSetsOrFormulaeSingleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(73);
                        formulaSetOrFormula();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetsOrFormulaeMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(74);
                        ((FormulaSetsOrFormulaeMultipleContext) _localctx).firstSet =
                                formulaSetOrFormula();
                        setState(76);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(75);
                                            ((FormulaSetsOrFormulaeMultipleContext) _localctx)
                                                            .formulaSetOrFormula =
                                                    formulaSetOrFormula();
                                            ((FormulaSetsOrFormulaeMultipleContext) _localctx)
                                                    .furtherSets.add(
                                                            ((FormulaSetsOrFormulaeMultipleContext)
                                                                            _localctx)
                                                                    .formulaSetOrFormula);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(78);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetOrFormulaContext extends ParserRuleContext {
        public FormulaSetOrFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetOrFormula;
        }

        public FormulaSetOrFormulaContext() {}

        public void copyFrom(FormulaSetOrFormulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetOrFormulaBracesContext extends FormulaSetOrFormulaContext {
        public FormulaSetWithBracesContext formulaSetWithBraces() {
            return getRuleContext(FormulaSetWithBracesContext.class, 0);
        }

        public FormulaSetOrFormulaBracesContext(FormulaSetOrFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetOrFormulaBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetOrFormulaWithoutBracesContext extends FormulaSetOrFormulaContext {
        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public FormulaSetOrFormulaWithoutBracesContext(FormulaSetOrFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetOrFormulaWithoutBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetOrFormulaContext formulaSetOrFormula() throws RecognitionException {
        FormulaSetOrFormulaContext _localctx = new FormulaSetOrFormulaContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_formulaSetOrFormula);
        try {
            setState(84);
            switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                case 1:
                    _localctx = new FormulaSetOrFormulaBracesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(82);
                        formulaSetWithBraces();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetOrFormulaWithoutBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(83);
                        formula();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class InitFormulaSetContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(FormulaSetParser.EOF, 0);
        }

        public FormulaSetContext formulaSet() {
            return getRuleContext(FormulaSetContext.class, 0);
        }

        public InitFormulaSetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initFormulaSet;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitInitFormulaSet(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitFormulaSetContext initFormulaSet() throws RecognitionException {
        InitFormulaSetContext _localctx = new InitFormulaSetContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_initFormulaSet);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(86);
                formulaSet();
                setState(87);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetContext extends ParserRuleContext {
        public FormulaSetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSet;
        }

        public FormulaSetContext() {}

        public void copyFrom(FormulaSetContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetWithoutBracesContext extends FormulaSetContext {
        public FormulaSetContentContext formulaSetContent() {
            return getRuleContext(FormulaSetContentContext.class, 0);
        }

        public FormulaSetWithoutBracesContext(FormulaSetContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetWithoutBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetWithBracesLINKContext extends FormulaSetContext {
        public FormulaSetWithBracesContext formulaSetWithBraces() {
            return getRuleContext(FormulaSetWithBracesContext.class, 0);
        }

        public FormulaSetWithBracesLINKContext(FormulaSetContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetWithBracesLINK(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetContext formulaSet() throws RecognitionException {
        FormulaSetContext _localctx = new FormulaSetContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_formulaSet);
        try {
            setState(91);
            switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
                case 1:
                    _localctx = new FormulaSetWithBracesLINKContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(89);
                        formulaSetWithBraces();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetWithoutBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(90);
                        formulaSetContent();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetWithBracesContext extends ParserRuleContext {
        public TerminalNode OBRACE() {
            return getToken(FormulaSetParser.OBRACE, 0);
        }

        public TerminalNode CBRACE() {
            return getToken(FormulaSetParser.CBRACE, 0);
        }

        public FormulaSetContentContext formulaSetContent() {
            return getRuleContext(FormulaSetContentContext.class, 0);
        }

        public FormulaSetWithBracesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetWithBraces;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetWithBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetWithBracesContext formulaSetWithBraces() throws RecognitionException {
        FormulaSetWithBracesContext _localctx = new FormulaSetWithBracesContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_formulaSetWithBraces);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(93);
                match(OBRACE);
                setState(94);
                formulaSetContent();
                setState(95);
                match(CBRACE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaSetContentContext extends ParserRuleContext {
        public FormulaSetContentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaSetContent;
        }

        public FormulaSetContentContext() {}

        public void copyFrom(FormulaSetContentContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSetContentSingleContext extends FormulaSetContentContext {
        public FormulaeContext formulae() {
            return getRuleContext(FormulaeContext.class, 0);
        }

        public FormulaSetContentSingleContext(FormulaSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetContentSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetContentMultipleContext extends FormulaSetContentContext {
        public FormulaeOrEmptyContext first;
        public Token ARGUMENTSEP;
        public List<Token> seps = new ArrayList<Token>();
        public FormulaeOrEmptyContext formulaeOrEmpty;
        public List<FormulaeOrEmptyContext> further = new ArrayList<FormulaeOrEmptyContext>();

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public FormulaeOrEmptyContext formulaeOrEmpty(int i) {
            return getRuleContext(FormulaeOrEmptyContext.class, i);
        }

        public List<FormulaeOrEmptyContext> formulaeOrEmpty() {
            return getRuleContexts(FormulaeOrEmptyContext.class);
        }

        public FormulaSetContentMultipleContext(FormulaSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetContentMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSetContentEmptyContext extends FormulaSetContentContext {
        public FormulaSetContentEmptyContext(FormulaSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetContentEmpty(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetContentContext formulaSetContent() throws RecognitionException {
        FormulaSetContentContext _localctx = new FormulaSetContentContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_formulaSetContent);
        int _la;
        try {
            setState(106);
            switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
                case 1:
                    _localctx = new FormulaSetContentEmptyContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetContentSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(98);
                        formulae();
                    }
                    break;
                case 3:
                    _localctx = new FormulaSetContentMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(99);
                        ((FormulaSetContentMultipleContext) _localctx).first = formulaeOrEmpty();
                        setState(102);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(100);
                                    ((FormulaSetContentMultipleContext) _localctx).ARGUMENTSEP =
                                            match(ARGUMENTSEP);
                                    ((FormulaSetContentMultipleContext) _localctx)
                                            .seps.add(
                                                    ((FormulaSetContentMultipleContext) _localctx)
                                                            .ARGUMENTSEP);
                                    setState(101);
                                    ((FormulaSetContentMultipleContext) _localctx).formulaeOrEmpty =
                                            formulaeOrEmpty();
                                    ((FormulaSetContentMultipleContext) _localctx)
                                            .further.add(
                                                    ((FormulaSetContentMultipleContext) _localctx)
                                                            .formulaeOrEmpty);
                                }
                            }
                            setState(104);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == ARGUMENTSEP);
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaeOrEmptyContext extends ParserRuleContext {
        public FormulaeOrEmptyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulaeOrEmpty;
        }

        public FormulaeOrEmptyContext() {}

        public void copyFrom(FormulaeOrEmptyContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaeOrEmptyNotPresentContext extends FormulaeOrEmptyContext {
        public FormulaeOrEmptyNotPresentContext(FormulaeOrEmptyContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaeOrEmptyNotPresent(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaeOrEmptyPresentContext extends FormulaeOrEmptyContext {
        public FormulaeContext formulae() {
            return getRuleContext(FormulaeContext.class, 0);
        }

        public FormulaeOrEmptyPresentContext(FormulaeOrEmptyContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaeOrEmptyPresent(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaeOrEmptyContext formulaeOrEmpty() throws RecognitionException {
        FormulaeOrEmptyContext _localctx = new FormulaeOrEmptyContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_formulaeOrEmpty);
        try {
            setState(110);
            switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
                case 1:
                    _localctx = new FormulaeOrEmptyPresentContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(108);
                        formulae();
                    }
                    break;
                case 2:
                    _localctx = new FormulaeOrEmptyNotPresentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaeContext extends ParserRuleContext {
        public FormulaeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formulae;
        }

        public FormulaeContext() {}

        public void copyFrom(FormulaeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaeSingleContext extends FormulaeContext {
        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public FormulaeSingleContext(FormulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitFormulaeSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaeMultipleContext extends FormulaeContext {
        public FormulaContext first;
        public FormulaContext formula;
        public List<FormulaContext> further = new ArrayList<FormulaContext>();

        public List<FormulaContext> formula() {
            return getRuleContexts(FormulaContext.class);
        }

        public FormulaContext formula(int i) {
            return getRuleContext(FormulaContext.class, i);
        }

        public FormulaeMultipleContext(FormulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitFormulaeMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaeContext formulae() throws RecognitionException {
        FormulaeContext _localctx = new FormulaeContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_formulae);
        try {
            int _alt;
            setState(119);
            switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                case 1:
                    _localctx = new FormulaeSingleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(112);
                        formula();
                    }
                    break;
                case 2:
                    _localctx = new FormulaeMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(113);
                        ((FormulaeMultipleContext) _localctx).first = formula();
                        setState(115);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(114);
                                            ((FormulaeMultipleContext) _localctx).formula =
                                                    formula();
                                            ((FormulaeMultipleContext) _localctx)
                                                    .further.add(
                                                            ((FormulaeMultipleContext) _localctx)
                                                                    .formula);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(117);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class InitFormulaContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(FormulaSetParser.EOF, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public InitFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initFormula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitInitFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitFormulaContext initFormula() throws RecognitionException {
        InitFormulaContext _localctx = new InitFormulaContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_initFormula);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(121);
                formula();
                setState(122);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class InitTermContext extends ParserRuleContext {
        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(FormulaSetParser.EOF, 0);
        }

        public InitTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initTerm;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitInitTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitTermContext initTerm() throws RecognitionException {
        InitTermContext _localctx = new InitTermContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_initTerm);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(124);
                term();
                setState(125);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FormulaContext extends ParserRuleContext {
        public FormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formula;
        }

        public FormulaContext() {}

        public void copyFrom(FormulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FormulaSuperContext extends FormulaContext {
        public SuperformulaContext superformula() {
            return getRuleContext(SuperformulaContext.class, 0);
        }

        public FormulaSuperContext(FormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitFormulaSuper(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaSubContext extends FormulaContext {
        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public FormulaSubContext(FormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitFormulaSub(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaContext formula() throws RecognitionException {
        FormulaContext _localctx = new FormulaContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_formula);
        try {
            setState(129);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    _localctx = new FormulaSuperContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(127);
                        superformula();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSubContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(128);
                        subformula();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SuperformulaContext extends ParserRuleContext {
        public SuperformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superformula;
        }

        public SuperformulaContext() {}

        public void copyFrom(SuperformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperformulaMultipeContext extends SuperformulaContext {
        public SubformulaContext first;
        public SubformulaContext subformula;
        public List<SubformulaContext> further = new ArrayList<SubformulaContext>();

        public TerminalNode AND(int i) {
            return getToken(FormulaSetParser.AND, i);
        }

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(FormulaSetParser.AND);
        }

        public SuperformulaMultipeContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaMultipe(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaSingleContext extends SuperformulaContext {
        public SuperformulaWithoutAndContext superformulaWithoutAnd() {
            return getRuleContext(SuperformulaWithoutAndContext.class, 0);
        }

        public SuperformulaSingleContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaContext superformula() throws RecognitionException {
        SuperformulaContext _localctx = new SuperformulaContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_superformula);
        try {
            int _alt;
            setState(139);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    _localctx = new SuperformulaMultipeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(131);
                        ((SuperformulaMultipeContext) _localctx).first = subformula();
                        setState(134);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(132);
                                            match(AND);
                                            setState(133);
                                            ((SuperformulaMultipeContext) _localctx).subformula =
                                                    subformula();
                                            ((SuperformulaMultipeContext) _localctx)
                                                    .further.add(
                                                            ((SuperformulaMultipeContext) _localctx)
                                                                    .subformula);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(136);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                    }
                    break;
                case 2:
                    _localctx = new SuperformulaSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(138);
                        superformulaWithoutAnd();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SuperformulaWithoutOrContext extends ParserRuleContext {
        public SuperformulaWithoutOrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superformulaWithoutOr;
        }

        public SuperformulaWithoutOrContext() {}

        public void copyFrom(SuperformulaWithoutOrContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperformulaWithoutOrMultipleContext extends SuperformulaWithoutOrContext {
        public SubformulaContext first;
        public SubformulaContext subformula;
        public List<SubformulaContext> further = new ArrayList<SubformulaContext>();

        public TerminalNode AND(int i) {
            return getToken(FormulaSetParser.AND, i);
        }

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(FormulaSetParser.AND);
        }

        public SuperformulaWithoutOrMultipleContext(SuperformulaWithoutOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutOrMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaWithoutOrSingleContext extends SuperformulaWithoutOrContext {
        public SuperformulaWithoutAndOrContext superformulaWithoutAndOr() {
            return getRuleContext(SuperformulaWithoutAndOrContext.class, 0);
        }

        public SuperformulaWithoutOrSingleContext(SuperformulaWithoutOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutOrSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaWithoutOrContext superformulaWithoutOr() throws RecognitionException {
        SuperformulaWithoutOrContext _localctx = new SuperformulaWithoutOrContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_superformulaWithoutOr);
        try {
            int _alt;
            setState(149);
            switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                case 1:
                    _localctx = new SuperformulaWithoutOrMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(141);
                        ((SuperformulaWithoutOrMultipleContext) _localctx).first = subformula();
                        setState(144);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(142);
                                            match(AND);
                                            setState(143);
                                            ((SuperformulaWithoutOrMultipleContext) _localctx)
                                                            .subformula =
                                                    subformula();
                                            ((SuperformulaWithoutOrMultipleContext) _localctx)
                                                    .further.add(
                                                            ((SuperformulaWithoutOrMultipleContext)
                                                                            _localctx)
                                                                    .subformula);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(146);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                    }
                    break;
                case 2:
                    _localctx = new SuperformulaWithoutOrSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(148);
                        superformulaWithoutAndOr();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SuperformulaWithoutAndContext extends ParserRuleContext {
        public SuperformulaWithoutAndContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superformulaWithoutAnd;
        }

        public SuperformulaWithoutAndContext() {}

        public void copyFrom(SuperformulaWithoutAndContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperformulaWithoutAndMultipleContext
            extends SuperformulaWithoutAndContext {
        public SubformulaContext first;
        public SubformulaContext subformula;
        public List<SubformulaContext> further = new ArrayList<SubformulaContext>();

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public List<TerminalNode> OR() {
            return getTokens(FormulaSetParser.OR);
        }

        public TerminalNode OR(int i) {
            return getToken(FormulaSetParser.OR, i);
        }

        public SuperformulaWithoutAndMultipleContext(SuperformulaWithoutAndContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutAndMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaWithoutAndSingleContext extends SuperformulaWithoutAndContext {
        public SuperformulaWithoutAndOrContext superformulaWithoutAndOr() {
            return getRuleContext(SuperformulaWithoutAndOrContext.class, 0);
        }

        public SuperformulaWithoutAndSingleContext(SuperformulaWithoutAndContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutAndSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaWithoutAndContext superformulaWithoutAnd()
            throws RecognitionException {
        SuperformulaWithoutAndContext _localctx =
                new SuperformulaWithoutAndContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_superformulaWithoutAnd);
        try {
            int _alt;
            setState(159);
            switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
                case 1:
                    _localctx = new SuperformulaWithoutAndMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(151);
                        ((SuperformulaWithoutAndMultipleContext) _localctx).first = subformula();
                        setState(154);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(152);
                                            match(OR);
                                            setState(153);
                                            ((SuperformulaWithoutAndMultipleContext) _localctx)
                                                            .subformula =
                                                    subformula();
                                            ((SuperformulaWithoutAndMultipleContext) _localctx)
                                                    .further.add(
                                                            ((SuperformulaWithoutAndMultipleContext)
                                                                            _localctx)
                                                                    .subformula);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(156);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                    }
                    break;
                case 2:
                    _localctx = new SuperformulaWithoutAndSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(158);
                        superformulaWithoutAndOr();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SuperformulaWithoutAndOrContext extends ParserRuleContext {
        public SuperformulaWithoutAndOrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superformulaWithoutAndOr;
        }

        public SuperformulaWithoutAndOrContext() {}

        public void copyFrom(SuperformulaWithoutAndOrContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperformulaWithoutAndOrImpliesContext
            extends SuperformulaWithoutAndOrContext {
        public SubformulaContext first;
        public SubformulaContext second;

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public TerminalNode IMPLIES() {
            return getToken(FormulaSetParser.IMPLIES, 0);
        }

        public SuperformulaWithoutAndOrImpliesContext(SuperformulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutAndOrImplies(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaWithoutAndOrEquivContext
            extends SuperformulaWithoutAndOrContext {
        public SubformulaContext first;
        public SubformulaContext second;

        public TerminalNode EQUIV() {
            return getToken(FormulaSetParser.EQUIV, 0);
        }

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public SuperformulaWithoutAndOrEquivContext(SuperformulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutAndOrEquiv(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaWithoutAndOrERRORContext
            extends SuperformulaWithoutAndOrContext {
        public SuperformulaErrorContext superformulaError() {
            return getRuleContext(SuperformulaErrorContext.class, 0);
        }

        public SuperformulaWithoutAndOrERRORContext(SuperformulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaWithoutAndOrERROR(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaWithoutAndOrContext superformulaWithoutAndOr()
            throws RecognitionException {
        SuperformulaWithoutAndOrContext _localctx =
                new SuperformulaWithoutAndOrContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_superformulaWithoutAndOr);
        try {
            setState(170);
            switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                case 1:
                    _localctx = new SuperformulaWithoutAndOrImpliesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(161);
                        ((SuperformulaWithoutAndOrImpliesContext) _localctx).first = subformula();
                        setState(162);
                        match(IMPLIES);
                        setState(163);
                        ((SuperformulaWithoutAndOrImpliesContext) _localctx).second = subformula();
                    }
                    break;
                case 2:
                    _localctx = new SuperformulaWithoutAndOrEquivContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(165);
                        ((SuperformulaWithoutAndOrEquivContext) _localctx).first = subformula();
                        setState(166);
                        match(EQUIV);
                        setState(167);
                        ((SuperformulaWithoutAndOrEquivContext) _localctx).second = subformula();
                    }
                    break;
                case 3:
                    _localctx = new SuperformulaWithoutAndOrERRORContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(169);
                        superformulaError();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SuperformulaErrorContext extends ParserRuleContext {
        public SuperformulaErrorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superformulaError;
        }

        public SuperformulaErrorContext() {}

        public void copyFrom(SuperformulaErrorContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperformulaErrorORContext extends SuperformulaErrorContext {
        public SubformulaContext firstsub;
        public SuperformulaWithoutOrContext secondsuperWO;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public SuperformulaWithoutOrContext superformulaWithoutOr() {
            return getRuleContext(SuperformulaWithoutOrContext.class, 0);
        }

        public TerminalNode OR() {
            return getToken(FormulaSetParser.OR, 0);
        }

        public SuperformulaErrorORContext(SuperformulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaErrorOR(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaErrorIMPLIESContext extends SuperformulaErrorContext {
        public SubformulaContext firstsub;
        public SuperformulaContext secondsuper;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode IMPLIES() {
            return getToken(FormulaSetParser.IMPLIES, 0);
        }

        public SuperformulaContext superformula() {
            return getRuleContext(SuperformulaContext.class, 0);
        }

        public SuperformulaErrorIMPLIESContext(SuperformulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaErrorIMPLIES(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaErrorANDContext extends SuperformulaErrorContext {
        public SubformulaContext firstsub;
        public SuperformulaWithoutAndContext secondsuperWA;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode AND() {
            return getToken(FormulaSetParser.AND, 0);
        }

        public SuperformulaWithoutAndContext superformulaWithoutAnd() {
            return getRuleContext(SuperformulaWithoutAndContext.class, 0);
        }

        public SuperformulaErrorANDContext(SuperformulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaErrorAND(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperformulaErrorEQUIVContext extends SuperformulaErrorContext {
        public SubformulaContext firstsub;
        public SuperformulaContext secondsuper;

        public TerminalNode EQUIV() {
            return getToken(FormulaSetParser.EQUIV, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public SuperformulaContext superformula() {
            return getRuleContext(SuperformulaContext.class, 0);
        }

        public SuperformulaErrorEQUIVContext(SuperformulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperformulaErrorEQUIV(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaErrorContext superformulaError() throws RecognitionException {
        SuperformulaErrorContext _localctx = new SuperformulaErrorContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_superformulaError);
        try {
            setState(188);
            switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                case 1:
                    _localctx = new SuperformulaErrorANDContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(172);
                        ((SuperformulaErrorANDContext) _localctx).firstsub = subformula();
                        setState(173);
                        match(AND);
                        setState(174);
                        ((SuperformulaErrorANDContext) _localctx).secondsuperWA =
                                superformulaWithoutAnd();
                    }
                    break;
                case 2:
                    _localctx = new SuperformulaErrorORContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(176);
                        ((SuperformulaErrorORContext) _localctx).firstsub = subformula();
                        setState(177);
                        match(OR);
                        setState(178);
                        ((SuperformulaErrorORContext) _localctx).secondsuperWO =
                                superformulaWithoutOr();
                    }
                    break;
                case 3:
                    _localctx = new SuperformulaErrorIMPLIESContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(180);
                        ((SuperformulaErrorIMPLIESContext) _localctx).firstsub = subformula();
                        setState(181);
                        match(IMPLIES);
                        setState(182);
                        ((SuperformulaErrorIMPLIESContext) _localctx).secondsuper = superformula();
                    }
                    break;
                case 4:
                    _localctx = new SuperformulaErrorEQUIVContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(184);
                        ((SuperformulaErrorEQUIVContext) _localctx).firstsub = subformula();
                        setState(185);
                        match(EQUIV);
                        setState(186);
                        ((SuperformulaErrorEQUIVContext) _localctx).secondsuper = superformula();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SubformulaContext extends ParserRuleContext {
        public SubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subformula;
        }

        public SubformulaContext() {}

        public void copyFrom(SubformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SubformulaNegContext extends SubformulaContext {
        public SubformulaContext sub;

        public TerminalNode NEG() {
            return getToken(FormulaSetParser.NEG, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public SubformulaNegContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaNeg(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaInfixContext extends SubformulaContext {
        public PrefixTermContext firstPrefix;
        public Token sym;
        public PrefixTermContext secondPrefix;

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(FormulaSetParser.RELINFIXISYMBOL, 0);
        }

        public PrefixTermContext prefixTerm(int i) {
            return getRuleContext(PrefixTermContext.class, i);
        }

        public List<PrefixTermContext> prefixTerm() {
            return getRuleContexts(PrefixTermContext.class);
        }

        public SubformulaInfixContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaInfix(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaBracketContext extends SubformulaContext {
        public TerminalNode OBRACKET() {
            return getToken(FormulaSetParser.OBRACKET, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(FormulaSetParser.CBRACKET, 0);
        }

        public SubformulaBracketContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSubformulaBracket(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaPrefixParenContext extends SubformulaContext {
        public TermContext first;
        public TermContext term;
        public List<TermContext> further = new ArrayList<TermContext>();

        public List<TermContext> term() {
            return getRuleContexts(TermContext.class);
        }

        public TermContext term(int i) {
            return getRuleContext(TermContext.class, i);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public TerminalNode OPAREN() {
            return getToken(FormulaSetParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(FormulaSetParser.CPAREN, 0);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public SubformulaPrefixParenContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSubformulaPrefixParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaParenContext extends SubformulaContext {
        public TerminalNode OPAREN() {
            return getToken(FormulaSetParser.OPAREN, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(FormulaSetParser.CPAREN, 0);
        }

        public SubformulaParenContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaPrefixContext extends SubformulaContext {
        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public SubformulaPrefixContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaPrefix(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaForAllContext extends SubformulaContext {
        public ISymbolContext var;
        public ISymbolContext iSymbol;
        public List<ISymbolContext> symbols = new ArrayList<ISymbolContext>();
        public SubformulaContext sub;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public ISymbolContext iSymbol(int i) {
            return getRuleContext(ISymbolContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public TerminalNode FORALL() {
            return getToken(FormulaSetParser.FORALL, 0);
        }

        public List<ISymbolContext> iSymbol() {
            return getRuleContexts(ISymbolContext.class);
        }

        public SubformulaForAllContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaForAll(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaFalseContext extends SubformulaContext {
        public TerminalNode FALSE() {
            return getToken(FormulaSetParser.FALSE, 0);
        }

        public SubformulaFalseContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaFalse(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaTrueContext extends SubformulaContext {
        public TerminalNode TRUE() {
            return getToken(FormulaSetParser.TRUE, 0);
        }

        public SubformulaTrueContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaTrue(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaExistsContext extends SubformulaContext {
        public ISymbolContext var;
        public ISymbolContext iSymbol;
        public List<ISymbolContext> symbols = new ArrayList<ISymbolContext>();
        public SubformulaContext sub;

        public TerminalNode EXISTS() {
            return getToken(FormulaSetParser.EXISTS, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public ISymbolContext iSymbol(int i) {
            return getRuleContext(ISymbolContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public List<ISymbolContext> iSymbol() {
            return getRuleContexts(ISymbolContext.class);
        }

        public SubformulaExistsContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitSubformulaExists(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaInfixUnrestrictedContext extends SubformulaContext {
        public PrefixTermContext firstPrefix;
        public Token sym;
        public PrefixTermContext secondPrefix;

        public TerminalNode ISYMBOL() {
            return getToken(FormulaSetParser.ISYMBOL, 0);
        }

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(FormulaSetParser.FUNINFIXISYMBOL, 0);
        }

        public PrefixTermContext prefixTerm(int i) {
            return getRuleContext(PrefixTermContext.class, i);
        }

        public List<PrefixTermContext> prefixTerm() {
            return getRuleContexts(PrefixTermContext.class);
        }

        public SubformulaInfixUnrestrictedContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSubformulaInfixUnrestricted(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaPrefixBracketContext extends SubformulaContext {
        public TermContext first;
        public TermContext term;
        public List<TermContext> further = new ArrayList<TermContext>();

        public TerminalNode OBRACKET() {
            return getToken(FormulaSetParser.OBRACKET, 0);
        }

        public List<TermContext> term() {
            return getRuleContexts(TermContext.class);
        }

        public TermContext term(int i) {
            return getRuleContext(TermContext.class, i);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public TerminalNode CBRACKET() {
            return getToken(FormulaSetParser.CBRACKET, 0);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public SubformulaPrefixBracketContext(SubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitSubformulaPrefixBracket(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaContext subformula() throws RecognitionException {
        SubformulaContext _localctx = new SubformulaContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_subformula);
        int _la;
        try {
            int _alt;
            setState(265);
            switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                case 1:
                    _localctx = new SubformulaTrueContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(190);
                        match(TRUE);
                    }
                    break;
                case 2:
                    _localctx = new SubformulaFalseContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(191);
                        match(FALSE);
                    }
                    break;
                case 3:
                    _localctx = new SubformulaNegContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(192);
                        match(NEG);
                        setState(193);
                        ((SubformulaNegContext) _localctx).sub = subformula();
                    }
                    break;
                case 4:
                    _localctx = new SubformulaForAllContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(194);
                        match(FORALL);
                        setState(195);
                        ((SubformulaForAllContext) _localctx).var = iSymbol();
                        setState(200);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                        while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(196);
                                        match(ARGUMENTSEP);
                                        setState(197);
                                        ((SubformulaForAllContext) _localctx).iSymbol = iSymbol();
                                        ((SubformulaForAllContext) _localctx)
                                                .symbols.add(
                                                        ((SubformulaForAllContext) _localctx)
                                                                .iSymbol);
                                    }
                                }
                            }
                            setState(202);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                        }
                        setState(203);
                        ((SubformulaForAllContext) _localctx).sub = subformula();
                    }
                    break;
                case 5:
                    _localctx = new SubformulaExistsContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(205);
                        match(EXISTS);
                        setState(206);
                        ((SubformulaExistsContext) _localctx).var = iSymbol();
                        setState(211);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 23, _ctx);
                        while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(207);
                                        match(ARGUMENTSEP);
                                        setState(208);
                                        ((SubformulaExistsContext) _localctx).iSymbol = iSymbol();
                                        ((SubformulaExistsContext) _localctx)
                                                .symbols.add(
                                                        ((SubformulaExistsContext) _localctx)
                                                                .iSymbol);
                                    }
                                }
                            }
                            setState(213);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 23, _ctx);
                        }
                        setState(214);
                        ((SubformulaExistsContext) _localctx).sub = subformula();
                    }
                    break;
                case 6:
                    _localctx = new SubformulaBracketContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(216);
                        match(OBRACKET);
                        setState(217);
                        formula();
                        setState(218);
                        match(CBRACKET);
                    }
                    break;
                case 7:
                    _localctx = new SubformulaParenContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                    {
                        setState(220);
                        match(OPAREN);
                        setState(221);
                        formula();
                        setState(222);
                        match(CPAREN);
                    }
                    break;
                case 8:
                    _localctx = new SubformulaInfixContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                    {
                        setState(224);
                        ((SubformulaInfixContext) _localctx).firstPrefix = prefixTerm();
                        setState(225);
                        ((SubformulaInfixContext) _localctx).sym = match(RELINFIXISYMBOL);
                        setState(226);
                        ((SubformulaInfixContext) _localctx).secondPrefix = prefixTerm();
                    }
                    break;
                case 9:
                    _localctx = new SubformulaInfixUnrestrictedContext(_localctx);
                    enterOuterAlt(_localctx, 9);
                    {
                        setState(228);
                        if (!(!properties.getInfixProperties().areInfixRelationsRestricted()))
                            throw new FailedPredicateException(
                                    this,
                                    "! properties.getInfixProperties().areInfixRelationsRestricted()");
                        setState(229);
                        ((SubformulaInfixUnrestrictedContext) _localctx).firstPrefix = prefixTerm();
                        setState(232);
                        switch (_input.LA(1)) {
                            case ISYMBOL:
                                {
                                    setState(230);
                                    ((SubformulaInfixUnrestrictedContext) _localctx).sym =
                                            match(ISYMBOL);
                                }
                                break;
                            case FUNINFIXISYMBOL:
                                {
                                    setState(231);
                                    ((SubformulaInfixUnrestrictedContext) _localctx).sym =
                                            match(FUNINFIXISYMBOL);
                                }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(234);
                        ((SubformulaInfixUnrestrictedContext) _localctx).secondPrefix =
                                prefixTerm();
                    }
                    break;
                case 10:
                    _localctx = new SubformulaPrefixBracketContext(_localctx);
                    enterOuterAlt(_localctx, 10);
                    {
                        setState(236);
                        iSymbol();
                        setState(237);
                        match(OBRACKET);
                        setState(246);
                        switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
                            case 1:
                                {
                                    setState(238);
                                    ((SubformulaPrefixBracketContext) _localctx).first = term();
                                    setState(243);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(239);
                                                match(ARGUMENTSEP);
                                                setState(240);
                                                ((SubformulaPrefixBracketContext) _localctx).term =
                                                        term();
                                                ((SubformulaPrefixBracketContext) _localctx)
                                                        .further.add(
                                                                ((SubformulaPrefixBracketContext)
                                                                                _localctx)
                                                                        .term);
                                            }
                                        }
                                        setState(245);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(248);
                        match(CBRACKET);
                    }
                    break;
                case 11:
                    _localctx = new SubformulaPrefixParenContext(_localctx);
                    enterOuterAlt(_localctx, 11);
                    {
                        setState(250);
                        iSymbol();
                        setState(251);
                        match(OPAREN);
                        setState(260);
                        switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
                            case 1:
                                {
                                    setState(252);
                                    ((SubformulaPrefixParenContext) _localctx).first = term();
                                    setState(257);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(253);
                                                match(ARGUMENTSEP);
                                                setState(254);
                                                ((SubformulaPrefixParenContext) _localctx).term =
                                                        term();
                                                ((SubformulaPrefixParenContext) _localctx)
                                                        .further.add(
                                                                ((SubformulaPrefixParenContext)
                                                                                _localctx)
                                                                        .term);
                                            }
                                        }
                                        setState(259);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(262);
                        match(CPAREN);
                    }
                    break;
                case 12:
                    _localctx = new SubformulaPrefixContext(_localctx);
                    enterOuterAlt(_localctx, 12);
                    {
                        setState(264);
                        iSymbol();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class TermContext extends ParserRuleContext {
        public TermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_term;
        }

        public TermContext() {}

        public void copyFrom(TermContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class TermPrefixContext extends TermContext {
        public PrefixTermContext prefixTerm() {
            return getRuleContext(PrefixTermContext.class, 0);
        }

        public TermPrefixContext(TermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitTermPrefix(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TermNEGErrorContext extends TermContext {
        public TermContext child;

        public TerminalNode NEG() {
            return getToken(FormulaSetParser.NEG, 0);
        }

        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TermNEGErrorContext(TermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitTermNEGError(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TermInfixUnrestrictedContext extends TermContext {
        public PrefixTermContext first;
        public Token sym;
        public PrefixTermContext second;

        public TerminalNode ISYMBOL() {
            return getToken(FormulaSetParser.ISYMBOL, 0);
        }

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(FormulaSetParser.RELINFIXISYMBOL, 0);
        }

        public PrefixTermContext prefixTerm(int i) {
            return getRuleContext(PrefixTermContext.class, i);
        }

        public List<PrefixTermContext> prefixTerm() {
            return getRuleContexts(PrefixTermContext.class);
        }

        public TermInfixUnrestrictedContext(TermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitTermInfixUnrestricted(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TermInfixContext extends TermContext {
        public PrefixTermContext first;
        public Token sym;
        public PrefixTermContext second;

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(FormulaSetParser.FUNINFIXISYMBOL, 0);
        }

        public PrefixTermContext prefixTerm(int i) {
            return getRuleContext(PrefixTermContext.class, i);
        }

        public List<PrefixTermContext> prefixTerm() {
            return getRuleContexts(PrefixTermContext.class);
        }

        public TermInfixContext(TermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitTermInfix(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TermContext term() throws RecognitionException {
        TermContext _localctx = new TermContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_term);
        try {
            setState(282);
            switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
                case 1:
                    _localctx = new TermInfixContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(267);
                        ((TermInfixContext) _localctx).first = prefixTerm();
                        setState(268);
                        ((TermInfixContext) _localctx).sym = match(FUNINFIXISYMBOL);
                        setState(269);
                        ((TermInfixContext) _localctx).second = prefixTerm();
                    }
                    break;
                case 2:
                    _localctx = new TermInfixUnrestrictedContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(271);
                        if (!(!properties.getInfixProperties().areInfixFunctionsRestricted()))
                            throw new FailedPredicateException(
                                    this,
                                    "! properties.getInfixProperties().areInfixFunctionsRestricted()");
                        setState(272);
                        ((TermInfixUnrestrictedContext) _localctx).first = prefixTerm();
                        setState(275);
                        switch (_input.LA(1)) {
                            case ISYMBOL:
                                {
                                    setState(273);
                                    ((TermInfixUnrestrictedContext) _localctx).sym = match(ISYMBOL);
                                }
                                break;
                            case RELINFIXISYMBOL:
                                {
                                    setState(274);
                                    ((TermInfixUnrestrictedContext) _localctx).sym =
                                            match(RELINFIXISYMBOL);
                                }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(277);
                        ((TermInfixUnrestrictedContext) _localctx).second = prefixTerm();
                    }
                    break;
                case 3:
                    _localctx = new TermPrefixContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(279);
                        prefixTerm();
                    }
                    break;
                case 4:
                    _localctx = new TermNEGErrorContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(280);
                        match(NEG);
                        setState(281);
                        ((TermNEGErrorContext) _localctx).child = term();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class PrefixTermContext extends ParserRuleContext {
        public PrefixTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prefixTerm;
        }

        public PrefixTermContext() {}

        public void copyFrom(PrefixTermContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class PrefixTermParenContext extends PrefixTermContext {
        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode OPAREN() {
            return getToken(FormulaSetParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(FormulaSetParser.CPAREN, 0);
        }

        public PrefixTermParenContext(PrefixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitPrefixTermParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixTermBracketsContext extends PrefixTermContext {
        public TerminalNode OBRACKET() {
            return getToken(FormulaSetParser.OBRACKET, 0);
        }

        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(FormulaSetParser.CBRACKET, 0);
        }

        public PrefixTermBracketsContext(PrefixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitPrefixTermBrackets(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixTermBracketMultipleContext extends PrefixTermContext {
        public TermContext first;
        public TermContext term;
        public List<TermContext> further = new ArrayList<TermContext>();

        public TerminalNode OBRACKET() {
            return getToken(FormulaSetParser.OBRACKET, 0);
        }

        public List<TermContext> term() {
            return getRuleContexts(TermContext.class);
        }

        public TermContext term(int i) {
            return getRuleContext(TermContext.class, i);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public TerminalNode CBRACKET() {
            return getToken(FormulaSetParser.CBRACKET, 0);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public PrefixTermBracketMultipleContext(PrefixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitPrefixTermBracketMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixTermParenMultipleContext extends PrefixTermContext {
        public TermContext first;
        public TermContext term;
        public List<TermContext> further = new ArrayList<TermContext>();

        public List<TermContext> term() {
            return getRuleContexts(TermContext.class);
        }

        public TermContext term(int i) {
            return getRuleContext(TermContext.class, i);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(FormulaSetParser.ARGUMENTSEP, i);
        }

        public TerminalNode OPAREN() {
            return getToken(FormulaSetParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(FormulaSetParser.CPAREN, 0);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(FormulaSetParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public PrefixTermParenMultipleContext(PrefixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitPrefixTermParenMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixTermISymbolContext extends PrefixTermContext {
        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public PrefixTermISymbolContext(PrefixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor)
                        .visitPrefixTermISymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final PrefixTermContext prefixTerm() throws RecognitionException {
        PrefixTermContext _localctx = new PrefixTermContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_prefixTerm);
        int _la;
        try {
            setState(321);
            switch (getInterpreter().adaptivePredict(_input, 36, _ctx)) {
                case 1:
                    _localctx = new PrefixTermBracketMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(284);
                        iSymbol();
                        setState(285);
                        match(OBRACKET);
                        setState(294);
                        switch (getInterpreter().adaptivePredict(_input, 33, _ctx)) {
                            case 1:
                                {
                                    setState(286);
                                    ((PrefixTermBracketMultipleContext) _localctx).first = term();
                                    setState(291);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(287);
                                                match(ARGUMENTSEP);
                                                setState(288);
                                                ((PrefixTermBracketMultipleContext) _localctx)
                                                                .term =
                                                        term();
                                                ((PrefixTermBracketMultipleContext) _localctx)
                                                        .further.add(
                                                                ((PrefixTermBracketMultipleContext)
                                                                                _localctx)
                                                                        .term);
                                            }
                                        }
                                        setState(293);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(296);
                        match(CBRACKET);
                    }
                    break;
                case 2:
                    _localctx = new PrefixTermParenMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(298);
                        iSymbol();
                        setState(299);
                        match(OPAREN);
                        setState(308);
                        switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
                            case 1:
                                {
                                    setState(300);
                                    ((PrefixTermParenMultipleContext) _localctx).first = term();
                                    setState(305);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(301);
                                                match(ARGUMENTSEP);
                                                setState(302);
                                                ((PrefixTermParenMultipleContext) _localctx).term =
                                                        term();
                                                ((PrefixTermParenMultipleContext) _localctx)
                                                        .further.add(
                                                                ((PrefixTermParenMultipleContext)
                                                                                _localctx)
                                                                        .term);
                                            }
                                        }
                                        setState(307);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(310);
                        match(CPAREN);
                    }
                    break;
                case 3:
                    _localctx = new PrefixTermISymbolContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(312);
                        iSymbol();
                    }
                    break;
                case 4:
                    _localctx = new PrefixTermBracketsContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(313);
                        match(OBRACKET);
                        setState(314);
                        term();
                        setState(315);
                        match(CBRACKET);
                    }
                    break;
                case 5:
                    _localctx = new PrefixTermParenContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(317);
                        match(OPAREN);
                        setState(318);
                        term();
                        setState(319);
                        match(CPAREN);
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ISymbolContext extends ParserRuleContext {
        public Token sym;

        public TerminalNode ISYMBOL() {
            return getToken(FormulaSetParser.ISYMBOL, 0);
        }

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(FormulaSetParser.RELINFIXISYMBOL, 0);
        }

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(FormulaSetParser.FUNINFIXISYMBOL, 0);
        }

        public ISymbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_iSymbol;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FormulaSetParserVisitor)
                return ((FormulaSetParserVisitor<? extends T>) visitor).visitISymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ISymbolContext iSymbol() throws RecognitionException {
        ISymbolContext _localctx = new ISymbolContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_iSymbol);
        try {
            setState(326);
            switch (_input.LA(1)) {
                case ISYMBOL:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(323);
                        ((ISymbolContext) _localctx).sym = match(ISYMBOL);
                    }
                    break;
                case RELINFIXISYMBOL:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(324);
                        ((ISymbolContext) _localctx).sym = match(RELINFIXISYMBOL);
                    }
                    break;
                case FUNINFIXISYMBOL:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(325);
                        ((ISymbolContext) _localctx).sym = match(FUNINFIXISYMBOL);
                    }
                    break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 20:
                return subformula_sempred((SubformulaContext) _localctx, predIndex);
            case 21:
                return term_sempred((TermContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean subformula_sempred(SubformulaContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return !properties.getInfixProperties().areInfixRelationsRestricted();
        }
        return true;
    }

    private boolean term_sempred(TermContext _localctx, int predIndex) {
        switch (predIndex) {
            case 1:
                return !properties.getInfixProperties().areInfixFunctionsRestricted();
        }
        return true;
    }

    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31\u014b\4\2\t\2"
                    + "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
                    + "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
                    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
                    + "\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3;\n\3\3\4\3\4\3\4\3\4\3\4\6\4B\n\4"
                    + "\r\4\16\4C\5\4F\n\4\3\5\3\5\5\5J\n\5\3\6\3\6\3\6\6\6O\n\6\r\6\16\6P\5"
                    + "\6S\n\6\3\7\3\7\5\7W\n\7\3\b\3\b\3\b\3\t\3\t\5\t^\n\t\3\n\3\n\3\n\3\n"
                    + "\3\13\3\13\3\13\3\13\3\13\6\13i\n\13\r\13\16\13j\5\13m\n\13\3\f\3\f\5"
                    + "\fq\n\f\3\r\3\r\3\r\6\rv\n\r\r\r\16\rw\5\rz\n\r\3\16\3\16\3\16\3\17\3"
                    + "\17\3\17\3\20\3\20\5\20\u0084\n\20\3\21\3\21\3\21\6\21\u0089\n\21\r\21"
                    + "\16\21\u008a\3\21\5\21\u008e\n\21\3\22\3\22\3\22\6\22\u0093\n\22\r\22"
                    + "\16\22\u0094\3\22\5\22\u0098\n\22\3\23\3\23\3\23\6\23\u009d\n\23\r\23"
                    + "\16\23\u009e\3\23\5\23\u00a2\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"
                    + "\24\3\24\5\24\u00ad\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"
                    + "\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u00bf\n\25\3\26\3\26\3\26\3\26"
                    + "\3\26\3\26\3\26\3\26\7\26\u00c9\n\26\f\26\16\26\u00cc\13\26\3\26\3\26"
                    + "\3\26\3\26\3\26\3\26\7\26\u00d4\n\26\f\26\16\26\u00d7\13\26\3\26\3\26"
                    + "\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"
                    + "\3\26\3\26\5\26\u00eb\n\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\7\26\u00f4"
                    + "\n\26\f\26\16\26\u00f7\13\26\5\26\u00f9\n\26\3\26\3\26\3\26\3\26\3\26"
                    + "\3\26\3\26\7\26\u0102\n\26\f\26\16\26\u0105\13\26\5\26\u0107\n\26\3\26"
                    + "\3\26\3\26\5\26\u010c\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27"
                    + "\u0116\n\27\3\27\3\27\3\27\3\27\3\27\5\27\u011d\n\27\3\30\3\30\3\30\3"
                    + "\30\3\30\7\30\u0124\n\30\f\30\16\30\u0127\13\30\5\30\u0129\n\30\3\30\3"
                    + "\30\3\30\3\30\3\30\3\30\3\30\7\30\u0132\n\30\f\30\16\30\u0135\13\30\5"
                    + "\30\u0137\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"
                    + "\5\30\u0144\n\30\3\31\3\31\3\31\5\31\u0149\n\31\3\31\2\2\32\2\4\6\b\n"
                    + "\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\2\u016d\2\62\3\2\2\2\4:\3"
                    + "\2\2\2\6E\3\2\2\2\bI\3\2\2\2\nR\3\2\2\2\fV\3\2\2\2\16X\3\2\2\2\20]\3\2"
                    + "\2\2\22_\3\2\2\2\24l\3\2\2\2\26p\3\2\2\2\30y\3\2\2\2\32{\3\2\2\2\34~\3"
                    + "\2\2\2\36\u0083\3\2\2\2 \u008d\3\2\2\2\"\u0097\3\2\2\2$\u00a1\3\2\2\2"
                    + "&\u00ac\3\2\2\2(\u00be\3\2\2\2*\u010b\3\2\2\2,\u011c\3\2\2\2.\u0143\3"
                    + "\2\2\2\60\u0148\3\2\2\2\62\63\5\4\3\2\63\64\7\2\2\3\64\3\3\2\2\2\65\66"
                    + "\7\23\2\2\66\67\5\6\4\2\678\7\24\2\28;\3\2\2\29;\5\6\4\2:\65\3\2\2\2:"
                    + "9\3\2\2\2;\5\3\2\2\2<F\3\2\2\2=F\5\n\6\2>A\5\b\5\2?@\7\20\2\2@B\5\b\5"
                    + "\2A?\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2E<\3\2\2\2E=\3\2\2"
                    + "\2E>\3\2\2\2F\7\3\2\2\2GJ\5\n\6\2HJ\3\2\2\2IG\3\2\2\2IH\3\2\2\2J\t\3\2"
                    + "\2\2KS\5\f\7\2LN\5\f\7\2MO\5\f\7\2NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2"
                    + "\2\2QS\3\2\2\2RK\3\2\2\2RL\3\2\2\2S\13\3\2\2\2TW\5\22\n\2UW\5\36\20\2"
                    + "VT\3\2\2\2VU\3\2\2\2W\r\3\2\2\2XY\5\20\t\2YZ\7\2\2\3Z\17\3\2\2\2[^\5\22"
                    + "\n\2\\^\5\24\13\2][\3\2\2\2]\\\3\2\2\2^\21\3\2\2\2_`\7\23\2\2`a\5\24\13"
                    + "\2ab\7\24\2\2b\23\3\2\2\2cm\3\2\2\2dm\5\30\r\2eh\5\26\f\2fg\7\20\2\2g"
                    + "i\5\26\f\2hf\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2\2\2km\3\2\2\2lc\3\2\2\2"
                    + "ld\3\2\2\2le\3\2\2\2m\25\3\2\2\2nq\5\30\r\2oq\3\2\2\2pn\3\2\2\2po\3\2"
                    + "\2\2q\27\3\2\2\2rz\5\36\20\2su\5\36\20\2tv\5\36\20\2ut\3\2\2\2vw\3\2\2"
                    + "\2wu\3\2\2\2wx\3\2\2\2xz\3\2\2\2yr\3\2\2\2ys\3\2\2\2z\31\3\2\2\2{|\5\36"
                    + "\20\2|}\7\2\2\3}\33\3\2\2\2~\177\5,\27\2\177\u0080\7\2\2\3\u0080\35\3"
                    + "\2\2\2\u0081\u0084\5 \21\2\u0082\u0084\5*\26\2\u0083\u0081\3\2\2\2\u0083"
                    + "\u0082\3\2\2\2\u0084\37\3\2\2\2\u0085\u0088\5*\26\2\u0086\u0087\7\4\2"
                    + "\2\u0087\u0089\5*\26\2\u0088\u0086\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u0088"
                    + "\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008e\5$\23\2\u008d"
                    + "\u0085\3\2\2\2\u008d\u008c\3\2\2\2\u008e!\3\2\2\2\u008f\u0092\5*\26\2"
                    + "\u0090\u0091\7\4\2\2\u0091\u0093\5*\26\2\u0092\u0090\3\2\2\2\u0093\u0094"
                    + "\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0098\3\2\2\2\u0096"
                    + "\u0098\5&\24\2\u0097\u008f\3\2\2\2\u0097\u0096\3\2\2\2\u0098#\3\2\2\2"
                    + "\u0099\u009c\5*\26\2\u009a\u009b\7\5\2\2\u009b\u009d\5*\26\2\u009c\u009a"
                    + "\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f"
                    + "\u00a2\3\2\2\2\u00a0\u00a2\5&\24\2\u00a1\u0099\3\2\2\2\u00a1\u00a0\3\2"
                    + "\2\2\u00a2%\3\2\2\2\u00a3\u00a4\5*\26\2\u00a4\u00a5\7\7\2\2\u00a5\u00a6"
                    + "\5*\26\2\u00a6\u00ad\3\2\2\2\u00a7\u00a8\5*\26\2\u00a8\u00a9\7\6\2\2\u00a9"
                    + "\u00aa\5*\26\2\u00aa\u00ad\3\2\2\2\u00ab\u00ad\5(\25\2\u00ac\u00a3\3\2"
                    + "\2\2\u00ac\u00a7\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad\'\3\2\2\2\u00ae\u00af"
                    + "\5*\26\2\u00af\u00b0\7\4\2\2\u00b0\u00b1\5$\23\2\u00b1\u00bf\3\2\2\2\u00b2"
                    + "\u00b3\5*\26\2\u00b3\u00b4\7\5\2\2\u00b4\u00b5\5\"\22\2\u00b5\u00bf\3"
                    + "\2\2\2\u00b6\u00b7\5*\26\2\u00b7\u00b8\7\7\2\2\u00b8\u00b9\5 \21\2\u00b9"
                    + "\u00bf\3\2\2\2\u00ba\u00bb\5*\26\2\u00bb\u00bc\7\6\2\2\u00bc\u00bd\5 "
                    + "\21\2\u00bd\u00bf\3\2\2\2\u00be\u00ae\3\2\2\2\u00be\u00b2\3\2\2\2\u00be"
                    + "\u00b6\3\2\2\2\u00be\u00ba\3\2\2\2\u00bf)\3\2\2\2\u00c0\u010c\7\n\2\2"
                    + "\u00c1\u010c\7\13\2\2\u00c2\u00c3\7\3\2\2\u00c3\u010c\5*\26\2\u00c4\u00c5"
                    + "\7\b\2\2\u00c5\u00ca\5\60\31\2\u00c6\u00c7\7\20\2\2\u00c7\u00c9\5\60\31"
                    + "\2\u00c8\u00c6\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb"
                    + "\3\2\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00ce\5*\26\2\u00ce"
                    + "\u010c\3\2\2\2\u00cf\u00d0\7\t\2\2\u00d0\u00d5\5\60\31\2\u00d1\u00d2\7"
                    + "\20\2\2\u00d2\u00d4\5\60\31\2\u00d3\u00d1\3\2\2\2\u00d4\u00d7\3\2\2\2"
                    + "\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d8\3\2\2\2\u00d7\u00d5"
                    + "\3\2\2\2\u00d8\u00d9\5*\26\2\u00d9\u010c\3\2\2\2\u00da\u00db\7\f\2\2\u00db"
                    + "\u00dc\5\36\20\2\u00dc\u00dd\7\r\2\2\u00dd\u010c\3\2\2\2\u00de\u00df\7"
                    + "\16\2\2\u00df\u00e0\5\36\20\2\u00e0\u00e1\7\17\2\2\u00e1\u010c\3\2\2\2"
                    + "\u00e2\u00e3\5.\30\2\u00e3\u00e4\7\25\2\2\u00e4\u00e5\5.\30\2\u00e5\u010c"
                    + "\3\2\2\2\u00e6\u00e7\6\26\2\2\u00e7\u00ea\5.\30\2\u00e8\u00eb\7\22\2\2"
                    + "\u00e9\u00eb\7\26\2\2\u00ea\u00e8\3\2\2\2\u00ea\u00e9\3\2\2\2\u00eb\u00ec"
                    + "\3\2\2\2\u00ec\u00ed\5.\30\2\u00ed\u010c\3\2\2\2\u00ee\u00ef\5\60\31\2"
                    + "\u00ef\u00f8\7\f\2\2\u00f0\u00f5\5,\27\2\u00f1\u00f2\7\20\2\2\u00f2\u00f4"
                    + "\5,\27\2\u00f3\u00f1\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5"
                    + "\u00f6\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u00f0\3\2"
                    + "\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00fb\7\r\2\2\u00fb"
                    + "\u010c\3\2\2\2\u00fc\u00fd\5\60\31\2\u00fd\u0106\7\16\2\2\u00fe\u0103"
                    + "\5,\27\2\u00ff\u0100\7\20\2\2\u0100\u0102\5,\27\2\u0101\u00ff\3\2\2\2"
                    + "\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0107"
                    + "\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u00fe\3\2\2\2\u0106\u0107\3\2\2\2\u0107"
                    + "\u0108\3\2\2\2\u0108\u0109\7\17\2\2\u0109\u010c\3\2\2\2\u010a\u010c\5"
                    + "\60\31\2\u010b\u00c0\3\2\2\2\u010b\u00c1\3\2\2\2\u010b\u00c2\3\2\2\2\u010b"
                    + "\u00c4\3\2\2\2\u010b\u00cf\3\2\2\2\u010b\u00da\3\2\2\2\u010b\u00de\3\2"
                    + "\2\2\u010b\u00e2\3\2\2\2\u010b\u00e6\3\2\2\2\u010b\u00ee\3\2\2\2\u010b"
                    + "\u00fc\3\2\2\2\u010b\u010a\3\2\2\2\u010c+\3\2\2\2\u010d\u010e\5.\30\2"
                    + "\u010e\u010f\7\26\2\2\u010f\u0110\5.\30\2\u0110\u011d\3\2\2\2\u0111\u0112"
                    + "\6\27\3\2\u0112\u0115\5.\30\2\u0113\u0116\7\22\2\2\u0114\u0116\7\25\2"
                    + "\2\u0115\u0113\3\2\2\2\u0115\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118"
                    + "\5.\30\2\u0118\u011d\3\2\2\2\u0119\u011d\5.\30\2\u011a\u011b\7\3\2\2\u011b"
                    + "\u011d\5,\27\2\u011c\u010d\3\2\2\2\u011c\u0111\3\2\2\2\u011c\u0119\3\2"
                    + "\2\2\u011c\u011a\3\2\2\2\u011d-\3\2\2\2\u011e\u011f\5\60\31\2\u011f\u0128"
                    + "\7\f\2\2\u0120\u0125\5,\27\2\u0121\u0122\7\20\2\2\u0122\u0124\5,\27\2"
                    + "\u0123\u0121\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126"
                    + "\3\2\2\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0128\u0120\3\2\2\2\u0128"
                    + "\u0129\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\7\r\2\2\u012b\u0144\3\2"
                    + "\2\2\u012c\u012d\5\60\31\2\u012d\u0136\7\16\2\2\u012e\u0133\5,\27\2\u012f"
                    + "\u0130\7\20\2\2\u0130\u0132\5,\27\2\u0131\u012f\3\2\2\2\u0132\u0135\3"
                    + "\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0137\3\2\2\2\u0135"
                    + "\u0133\3\2\2\2\u0136\u012e\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\3\2"
                    + "\2\2\u0138\u0139\7\17\2\2\u0139\u0144\3\2\2\2\u013a\u0144\5\60\31\2\u013b"
                    + "\u013c\7\f\2\2\u013c\u013d\5,\27\2\u013d\u013e\7\r\2\2\u013e\u0144\3\2"
                    + "\2\2\u013f\u0140\7\16\2\2\u0140\u0141\5,\27\2\u0141\u0142\7\17\2\2\u0142"
                    + "\u0144\3\2\2\2\u0143\u011e\3\2\2\2\u0143\u012c\3\2\2\2\u0143\u013a\3\2"
                    + "\2\2\u0143\u013b\3\2\2\2\u0143\u013f\3\2\2\2\u0144/\3\2\2\2\u0145\u0149"
                    + "\7\22\2\2\u0146\u0149\7\25\2\2\u0147\u0149\7\26\2\2\u0148\u0145\3\2\2"
                    + "\2\u0148\u0146\3\2\2\2\u0148\u0147\3\2\2\2\u0149\61\3\2\2\2(:CEIPRV]j"
                    + "lpwy\u0083\u008a\u008d\u0094\u0097\u009e\u00a1\u00ac\u00be\u00ca\u00d5"
                    + "\u00ea\u00f5\u00f8\u0103\u0106\u010b\u0115\u011c\u0125\u0128\u0133\u0136"
                    + "\u0143\u0148";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
