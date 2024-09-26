// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/modal/formula/ModalFormulaSetParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula;

import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ModalFormulaSetParser extends AbstractParser {
    static {
        RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int IMPLIES = 7,
            OBRACKET = 10,
            OR = 5,
            EQUIV = 6,
            DIAMOND = 2,
            TRUE = 8,
            BOX = 3,
            CBRACKET = 11,
            OPAREN = 12,
            NEG = 1,
            VARIABLE = 14,
            WHITESPACE = 15,
            AND = 4,
            NOT_SEPARATED_VARIABLES = 16,
            FALSE = 9,
            CPAREN = 13,
            OBRACE = 17,
            CBRACE = 18,
            ARGUMENTSEP = 19,
            REVERSE_IMPLIES = 20;
    public static final String[] tokenNames = {
        "<INVALID>",
        "NEG",
        "DIAMOND",
        "BOX",
        "AND",
        "OR",
        "EQUIV",
        "IMPLIES",
        "TRUE",
        "FALSE",
        "OBRACKET",
        "CBRACKET",
        "OPAREN",
        "CPAREN",
        "VARIABLE",
        "WHITESPACE",
        "NOT_SEPARATED_VARIABLES",
        "OBRACE",
        "CBRACE",
        "ARGUMENTSEP",
        "REVERSE_IMPLIES"
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
            RULE_initModalFormula = 12,
            RULE_formula = 13,
            RULE_superFormula = 14,
            RULE_superFormulaWithoutOr = 15,
            RULE_superFormulaWithoutAnd = 16,
            RULE_superFormulaWithoutAndOr = 17,
            RULE_superFormulaError = 18,
            RULE_subFormula = 19;
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
        "initModalFormula",
        "formula",
        "superFormula",
        "superFormulaWithoutOr",
        "superFormulaWithoutAnd",
        "superFormulaWithoutAndOr",
        "superFormulaError",
        "subFormula"
    };

    @Override
    public String getGrammarFileName() {
        return "ModalFormulaSetParser.g4";
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

    public ModalFormulaSetParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class InitFormulaSetSetContext extends ParserRuleContext {
        public FormulaSetSetContext setSet;

        public FormulaSetSetContext formulaSetSet() {
            return getRuleContext(FormulaSetSetContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(ModalFormulaSetParser.EOF, 0);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
                setState(40);
                ((InitFormulaSetSetContext) _localctx).setSet = formulaSetSet();
                setState(41);
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
            return getToken(ModalFormulaSetParser.OBRACE, 0);
        }

        public FormulaSetSetContentContext formulaSetSetContent() {
            return getRuleContext(FormulaSetSetContentContext.class, 0);
        }

        public TerminalNode CBRACE() {
            return getToken(ModalFormulaSetParser.CBRACE, 0);
        }

        public FormulaSetSetBracesContext(FormulaSetSetContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetMissingBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetSetContext formulaSetSet() throws RecognitionException {
        FormulaSetSetContext _localctx = new FormulaSetSetContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_formulaSetSet);
        try {
            setState(48);
            switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                case 1:
                    _localctx = new FormulaSetSetBracesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(43);
                        match(OBRACE);
                        setState(44);
                        ((FormulaSetSetBracesContext) _localctx).content = formulaSetSetContent();
                        setState(45);
                        match(CBRACE);
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetSetMissingBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(47);
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
            return getToken(ModalFormulaSetParser.ARGUMENTSEP, i);
        }

        public List<FormulaSetsOrFormulaeOrEmptyContext> formulaSetsOrFormulaeOrEmpty() {
            return getRuleContexts(FormulaSetsOrFormulaeOrEmptyContext.class);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(ModalFormulaSetParser.ARGUMENTSEP);
        }

        public FormulaSetSetContentMultipleContext(FormulaSetSetContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetSetContentSetsOrFormulae(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetSetContentContext formulaSetSetContent() throws RecognitionException {
        FormulaSetSetContentContext _localctx = new FormulaSetSetContentContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_formulaSetSetContent);
        int _la;
        try {
            setState(59);
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
                        setState(51);
                        formulaSetsOrFormulae();
                    }
                    break;
                case 3:
                    _localctx = new FormulaSetSetContentMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(52);
                        ((FormulaSetSetContentMultipleContext) _localctx).first =
                                formulaSetsOrFormulaeOrEmpty();
                        setState(55);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(53);
                                    ((FormulaSetSetContentMultipleContext) _localctx).ARGUMENTSEP =
                                            match(ARGUMENTSEP);
                                    ((FormulaSetSetContentMultipleContext) _localctx)
                                            .seps.add(
                                                    ((FormulaSetSetContentMultipleContext)
                                                                    _localctx)
                                                            .ARGUMENTSEP);
                                    setState(54);
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
                            setState(57);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            setState(63);
            switch (_input.LA(1)) {
                case NEG:
                case DIAMOND:
                case BOX:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case VARIABLE:
                case OBRACE:
                    _localctx = new FormulaSetsOrFormulaeOrEmptyPresentContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(61);
                        formulaSetsOrFormulae();
                    }
                    break;
                case EOF:
                case CBRACE:
                case ARGUMENTSEP:
                    _localctx = new FormulaSetsOrFormulaeOrEmptyNotPresentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetsOrFormulaeMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetsOrFormulaeContext formulaSetsOrFormulae() throws RecognitionException {
        FormulaSetsOrFormulaeContext _localctx = new FormulaSetsOrFormulaeContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_formulaSetsOrFormulae);
        int _la;
        try {
            setState(72);
            switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
                case 1:
                    _localctx = new FormulaSetsOrFormulaeSingleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(65);
                        formulaSetOrFormula();
                    }
                    break;
                case 2:
                    _localctx = new FormulaSetsOrFormulaeMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(66);
                        ((FormulaSetsOrFormulaeMultipleContext) _localctx).firstSet =
                                formulaSetOrFormula();
                        setState(68);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(67);
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
                            setState(70);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while ((((_la) & ~0x3f) == 0
                                && ((1L << _la)
                                                & ((1L << NEG)
                                                        | (1L << DIAMOND)
                                                        | (1L << BOX)
                                                        | (1L << TRUE)
                                                        | (1L << FALSE)
                                                        | (1L << OBRACKET)
                                                        | (1L << OPAREN)
                                                        | (1L << VARIABLE)
                                                        | (1L << OBRACE)))
                                        != 0));
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetOrFormulaWithoutBraces(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetOrFormulaContext formulaSetOrFormula() throws RecognitionException {
        FormulaSetOrFormulaContext _localctx = new FormulaSetOrFormulaContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_formulaSetOrFormula);
        try {
            setState(76);
            switch (_input.LA(1)) {
                case OBRACE:
                    _localctx = new FormulaSetOrFormulaBracesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(74);
                        formulaSetWithBraces();
                    }
                    break;
                case NEG:
                case DIAMOND:
                case BOX:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case VARIABLE:
                    _localctx = new FormulaSetOrFormulaWithoutBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(75);
                        formula();
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

    public static class InitFormulaSetContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(ModalFormulaSetParser.EOF, 0);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitInitFormulaSet(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitFormulaSetContext initFormulaSet() throws RecognitionException {
        InitFormulaSetContext _localctx = new InitFormulaSetContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_initFormulaSet);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(78);
                formulaSet();
                setState(79);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetWithBracesLINK(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetContext formulaSet() throws RecognitionException {
        FormulaSetContext _localctx = new FormulaSetContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_formulaSet);
        try {
            setState(83);
            switch (_input.LA(1)) {
                case OBRACE:
                    _localctx = new FormulaSetWithBracesLINKContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(81);
                        formulaSetWithBraces();
                    }
                    break;
                case EOF:
                case NEG:
                case DIAMOND:
                case BOX:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case VARIABLE:
                case ARGUMENTSEP:
                    _localctx = new FormulaSetWithoutBracesContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(82);
                        formulaSetContent();
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

    public static class FormulaSetWithBracesContext extends ParserRuleContext {
        public TerminalNode OBRACE() {
            return getToken(ModalFormulaSetParser.OBRACE, 0);
        }

        public TerminalNode CBRACE() {
            return getToken(ModalFormulaSetParser.CBRACE, 0);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
                setState(85);
                match(OBRACE);
                setState(86);
                formulaSetContent();
                setState(87);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            return getToken(ModalFormulaSetParser.ARGUMENTSEP, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(ModalFormulaSetParser.ARGUMENTSEP);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaSetContentEmpty(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaSetContentContext formulaSetContent() throws RecognitionException {
        FormulaSetContentContext _localctx = new FormulaSetContentContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_formulaSetContent);
        int _la;
        try {
            setState(98);
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
                        setState(90);
                        formulae();
                    }
                    break;
                case 3:
                    _localctx = new FormulaSetContentMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(91);
                        ((FormulaSetContentMultipleContext) _localctx).first = formulaeOrEmpty();
                        setState(94);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(92);
                                    ((FormulaSetContentMultipleContext) _localctx).ARGUMENTSEP =
                                            match(ARGUMENTSEP);
                                    ((FormulaSetContentMultipleContext) _localctx)
                                            .seps.add(
                                                    ((FormulaSetContentMultipleContext) _localctx)
                                                            .ARGUMENTSEP);
                                    setState(93);
                                    ((FormulaSetContentMultipleContext) _localctx).formulaeOrEmpty =
                                            formulaeOrEmpty();
                                    ((FormulaSetContentMultipleContext) _localctx)
                                            .further.add(
                                                    ((FormulaSetContentMultipleContext) _localctx)
                                                            .formulaeOrEmpty);
                                }
                            }
                            setState(96);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaeOrEmptyPresent(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaeOrEmptyContext formulaeOrEmpty() throws RecognitionException {
        FormulaeOrEmptyContext _localctx = new FormulaeOrEmptyContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_formulaeOrEmpty);
        try {
            setState(102);
            switch (_input.LA(1)) {
                case NEG:
                case DIAMOND:
                case BOX:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case VARIABLE:
                    _localctx = new FormulaeOrEmptyPresentContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(100);
                        formulae();
                    }
                    break;
                case EOF:
                case CBRACE:
                case ARGUMENTSEP:
                    _localctx = new FormulaeOrEmptyNotPresentContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaeSingle(this);
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
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitFormulaeMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaeContext formulae() throws RecognitionException {
        FormulaeContext _localctx = new FormulaeContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_formulae);
        int _la;
        try {
            setState(111);
            switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                case 1:
                    _localctx = new FormulaeSingleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(104);
                        formula();
                    }
                    break;
                case 2:
                    _localctx = new FormulaeMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(105);
                        ((FormulaeMultipleContext) _localctx).first = formula();
                        setState(107);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(106);
                                    ((FormulaeMultipleContext) _localctx).formula = formula();
                                    ((FormulaeMultipleContext) _localctx)
                                            .further.add(
                                                    ((FormulaeMultipleContext) _localctx).formula);
                                }
                            }
                            setState(109);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while ((((_la) & ~0x3f) == 0
                                && ((1L << _la)
                                                & ((1L << NEG)
                                                        | (1L << DIAMOND)
                                                        | (1L << BOX)
                                                        | (1L << TRUE)
                                                        | (1L << FALSE)
                                                        | (1L << OBRACKET)
                                                        | (1L << OPAREN)
                                                        | (1L << VARIABLE)))
                                        != 0));
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

    public static class InitModalFormulaContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(ModalFormulaSetParser.EOF, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public InitModalFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initModalFormula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitInitModalFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitModalFormulaContext initModalFormula() throws RecognitionException {
        InitModalFormulaContext _localctx = new InitModalFormulaContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_initModalFormula);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(113);
                formula();
                setState(114);
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

    public static class SuperFormulaLINKContext extends FormulaContext {
        public SuperFormulaContext superFormula() {
            return getRuleContext(SuperFormulaContext.class, 0);
        }

        public SuperFormulaLINKContext(FormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaLINK(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubFormulaLINKContext extends FormulaContext {
        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public SubFormulaLINKContext(FormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSubFormulaLINK(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaContext formula() throws RecognitionException {
        FormulaContext _localctx = new FormulaContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_formula);
        try {
            setState(118);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaLINKContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(116);
                        superFormula();
                    }
                    break;
                case 2:
                    _localctx = new SubFormulaLINKContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(117);
                        subFormula();
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

    public static class SuperFormulaContext extends ParserRuleContext {
        public SuperFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superFormula;
        }

        public SuperFormulaContext() {}

        public void copyFrom(SuperFormulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperFormulaMultipleContext extends SuperFormulaContext {
        public SubFormulaContext first;
        public SubFormulaContext subFormula;
        public List<SubFormulaContext> further = new ArrayList<SubFormulaContext>();

        public TerminalNode AND(int i) {
            return getToken(ModalFormulaSetParser.AND, i);
        }

        public List<SubFormulaContext> subFormula() {
            return getRuleContexts(SubFormulaContext.class);
        }

        public SubFormulaContext subFormula(int i) {
            return getRuleContext(SubFormulaContext.class, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(ModalFormulaSetParser.AND);
        }

        public SuperFormulaMultipleContext(SuperFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaSingleContext extends SuperFormulaContext {
        public SuperFormulaWithoutAndContext superFormulaWithoutAnd() {
            return getRuleContext(SuperFormulaWithoutAndContext.class, 0);
        }

        public SuperFormulaSingleContext(SuperFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperFormulaContext superFormula() throws RecognitionException {
        SuperFormulaContext _localctx = new SuperFormulaContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_superFormula);
        int _la;
        try {
            setState(128);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(120);
                        ((SuperFormulaMultipleContext) _localctx).first = subFormula();
                        setState(123);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(121);
                                    match(AND);
                                    setState(122);
                                    ((SuperFormulaMultipleContext) _localctx).subFormula =
                                            subFormula();
                                    ((SuperFormulaMultipleContext) _localctx)
                                            .further.add(
                                                    ((SuperFormulaMultipleContext) _localctx)
                                                            .subFormula);
                                }
                            }
                            setState(125);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == AND);
                    }
                    break;
                case 2:
                    _localctx = new SuperFormulaSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(127);
                        superFormulaWithoutAnd();
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

    public static class SuperFormulaWithoutOrContext extends ParserRuleContext {
        public SuperFormulaWithoutOrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superFormulaWithoutOr;
        }

        public SuperFormulaWithoutOrContext() {}

        public void copyFrom(SuperFormulaWithoutOrContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperFormulaWithoutOrSingleContext extends SuperFormulaWithoutOrContext {
        public SuperFormulaWithoutAndOrContext superFormulaWithoutAndOr() {
            return getRuleContext(SuperFormulaWithoutAndOrContext.class, 0);
        }

        public SuperFormulaWithoutOrSingleContext(SuperFormulaWithoutOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutOrSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaWithoutOrMultipleContext extends SuperFormulaWithoutOrContext {
        public SubFormulaContext first;
        public SubFormulaContext subFormula;
        public List<SubFormulaContext> further = new ArrayList<SubFormulaContext>();

        public TerminalNode AND(int i) {
            return getToken(ModalFormulaSetParser.AND, i);
        }

        public List<SubFormulaContext> subFormula() {
            return getRuleContexts(SubFormulaContext.class);
        }

        public SubFormulaContext subFormula(int i) {
            return getRuleContext(SubFormulaContext.class, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(ModalFormulaSetParser.AND);
        }

        public SuperFormulaWithoutOrMultipleContext(SuperFormulaWithoutOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutOrMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperFormulaWithoutOrContext superFormulaWithoutOr() throws RecognitionException {
        SuperFormulaWithoutOrContext _localctx = new SuperFormulaWithoutOrContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_superFormulaWithoutOr);
        int _la;
        try {
            setState(138);
            switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaWithoutOrMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(130);
                        ((SuperFormulaWithoutOrMultipleContext) _localctx).first = subFormula();
                        setState(133);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(131);
                                    match(AND);
                                    setState(132);
                                    ((SuperFormulaWithoutOrMultipleContext) _localctx).subFormula =
                                            subFormula();
                                    ((SuperFormulaWithoutOrMultipleContext) _localctx)
                                            .further.add(
                                                    ((SuperFormulaWithoutOrMultipleContext)
                                                                    _localctx)
                                                            .subFormula);
                                }
                            }
                            setState(135);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == AND);
                    }
                    break;
                case 2:
                    _localctx = new SuperFormulaWithoutOrSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(137);
                        superFormulaWithoutAndOr();
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

    public static class SuperFormulaWithoutAndContext extends ParserRuleContext {
        public SuperFormulaWithoutAndContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superFormulaWithoutAnd;
        }

        public SuperFormulaWithoutAndContext() {}

        public void copyFrom(SuperFormulaWithoutAndContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperFormulaWithoutAndSingleContext extends SuperFormulaWithoutAndContext {
        public SuperFormulaWithoutAndOrContext superFormulaWithoutAndOr() {
            return getRuleContext(SuperFormulaWithoutAndOrContext.class, 0);
        }

        public SuperFormulaWithoutAndSingleContext(SuperFormulaWithoutAndContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutAndSingle(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaWithoutAndMultipleContext
            extends SuperFormulaWithoutAndContext {
        public SubFormulaContext first;
        public SubFormulaContext subFormula;
        public List<SubFormulaContext> further = new ArrayList<SubFormulaContext>();

        public List<SubFormulaContext> subFormula() {
            return getRuleContexts(SubFormulaContext.class);
        }

        public SubFormulaContext subFormula(int i) {
            return getRuleContext(SubFormulaContext.class, i);
        }

        public List<TerminalNode> OR() {
            return getTokens(ModalFormulaSetParser.OR);
        }

        public TerminalNode OR(int i) {
            return getToken(ModalFormulaSetParser.OR, i);
        }

        public SuperFormulaWithoutAndMultipleContext(SuperFormulaWithoutAndContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutAndMultiple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperFormulaWithoutAndContext superFormulaWithoutAnd()
            throws RecognitionException {
        SuperFormulaWithoutAndContext _localctx =
                new SuperFormulaWithoutAndContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_superFormulaWithoutAnd);
        int _la;
        try {
            setState(148);
            switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaWithoutAndMultipleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(140);
                        ((SuperFormulaWithoutAndMultipleContext) _localctx).first = subFormula();
                        setState(143);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(141);
                                    match(OR);
                                    setState(142);
                                    ((SuperFormulaWithoutAndMultipleContext) _localctx).subFormula =
                                            subFormula();
                                    ((SuperFormulaWithoutAndMultipleContext) _localctx)
                                            .further.add(
                                                    ((SuperFormulaWithoutAndMultipleContext)
                                                                    _localctx)
                                                            .subFormula);
                                }
                            }
                            setState(145);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == OR);
                    }
                    break;
                case 2:
                    _localctx = new SuperFormulaWithoutAndSingleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(147);
                        superFormulaWithoutAndOr();
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

    public static class SuperFormulaWithoutAndOrContext extends ParserRuleContext {
        public SuperFormulaWithoutAndOrContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superFormulaWithoutAndOr;
        }

        public SuperFormulaWithoutAndOrContext() {}

        public void copyFrom(SuperFormulaWithoutAndOrContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperFormulaWithoutAndOrImpliesContext
            extends SuperFormulaWithoutAndOrContext {
        public SubFormulaContext first;
        public SubFormulaContext second;

        public TerminalNode IMPLIES() {
            return getToken(ModalFormulaSetParser.IMPLIES, 0);
        }

        public List<SubFormulaContext> subFormula() {
            return getRuleContexts(SubFormulaContext.class);
        }

        public SubFormulaContext subFormula(int i) {
            return getRuleContext(SubFormulaContext.class, i);
        }

        public SuperFormulaWithoutAndOrImpliesContext(SuperFormulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutAndOrImplies(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaWithoutAndOrEquivContext
            extends SuperFormulaWithoutAndOrContext {
        public SubFormulaContext first;
        public SubFormulaContext second;

        public TerminalNode EQUIV() {
            return getToken(ModalFormulaSetParser.EQUIV, 0);
        }

        public List<SubFormulaContext> subFormula() {
            return getRuleContexts(SubFormulaContext.class);
        }

        public SubFormulaContext subFormula(int i) {
            return getRuleContext(SubFormulaContext.class, i);
        }

        public SuperFormulaWithoutAndOrEquivContext(SuperFormulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutAndOrEquiv(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaWithoutAndOrErrorContext
            extends SuperFormulaWithoutAndOrContext {
        public SuperFormulaErrorContext superFormulaError() {
            return getRuleContext(SuperFormulaErrorContext.class, 0);
        }

        public SuperFormulaWithoutAndOrErrorContext(SuperFormulaWithoutAndOrContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaWithoutAndOrError(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperFormulaWithoutAndOrContext superFormulaWithoutAndOr()
            throws RecognitionException {
        SuperFormulaWithoutAndOrContext _localctx =
                new SuperFormulaWithoutAndOrContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_superFormulaWithoutAndOr);
        try {
            setState(159);
            switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaWithoutAndOrImpliesContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(150);
                        ((SuperFormulaWithoutAndOrImpliesContext) _localctx).first = subFormula();
                        setState(151);
                        match(IMPLIES);
                        setState(152);
                        ((SuperFormulaWithoutAndOrImpliesContext) _localctx).second = subFormula();
                    }
                    break;
                case 2:
                    _localctx = new SuperFormulaWithoutAndOrEquivContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(154);
                        ((SuperFormulaWithoutAndOrEquivContext) _localctx).first = subFormula();
                        setState(155);
                        match(EQUIV);
                        setState(156);
                        ((SuperFormulaWithoutAndOrEquivContext) _localctx).second = subFormula();
                    }
                    break;
                case 3:
                    _localctx = new SuperFormulaWithoutAndOrErrorContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(158);
                        superFormulaError();
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

    public static class SuperFormulaErrorContext extends ParserRuleContext {
        public SuperFormulaErrorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_superFormulaError;
        }

        public SuperFormulaErrorContext() {}

        public void copyFrom(SuperFormulaErrorContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SuperFormulaErrorORContext extends SuperFormulaErrorContext {
        public SubFormulaContext firstSub;
        public SuperFormulaWithoutOrContext secondSuperWO;

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public TerminalNode OR() {
            return getToken(ModalFormulaSetParser.OR, 0);
        }

        public SuperFormulaWithoutOrContext superFormulaWithoutOr() {
            return getRuleContext(SuperFormulaWithoutOrContext.class, 0);
        }

        public SuperFormulaErrorORContext(SuperFormulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaErrorOR(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaErrorEQUIVContext extends SuperFormulaErrorContext {
        public SubFormulaContext firstSub;
        public SuperFormulaContext secondSuper;

        public TerminalNode EQUIV() {
            return getToken(ModalFormulaSetParser.EQUIV, 0);
        }

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public SuperFormulaContext superFormula() {
            return getRuleContext(SuperFormulaContext.class, 0);
        }

        public SuperFormulaErrorEQUIVContext(SuperFormulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaErrorEQUIV(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaErrorANDContext extends SuperFormulaErrorContext {
        public SubFormulaContext firstSub;
        public SuperFormulaWithoutAndContext secondSuperWA;

        public SuperFormulaWithoutAndContext superFormulaWithoutAnd() {
            return getRuleContext(SuperFormulaWithoutAndContext.class, 0);
        }

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public TerminalNode AND() {
            return getToken(ModalFormulaSetParser.AND, 0);
        }

        public SuperFormulaErrorANDContext(SuperFormulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaErrorAND(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SuperFormulaErrorIMPLIESContext extends SuperFormulaErrorContext {
        public SubFormulaContext firstSub;
        public SuperFormulaContext secondSuper;

        public TerminalNode IMPLIES() {
            return getToken(ModalFormulaSetParser.IMPLIES, 0);
        }

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public SuperFormulaContext superFormula() {
            return getRuleContext(SuperFormulaContext.class, 0);
        }

        public SuperFormulaErrorIMPLIESContext(SuperFormulaErrorContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor)
                        .visitSuperFormulaErrorIMPLIES(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperFormulaErrorContext superFormulaError() throws RecognitionException {
        SuperFormulaErrorContext _localctx = new SuperFormulaErrorContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_superFormulaError);
        try {
            setState(177);
            switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
                case 1:
                    _localctx = new SuperFormulaErrorANDContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(161);
                        ((SuperFormulaErrorANDContext) _localctx).firstSub = subFormula();
                        setState(162);
                        match(AND);
                        setState(163);
                        ((SuperFormulaErrorANDContext) _localctx).secondSuperWA =
                                superFormulaWithoutAnd();
                    }
                    break;
                case 2:
                    _localctx = new SuperFormulaErrorORContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(165);
                        ((SuperFormulaErrorORContext) _localctx).firstSub = subFormula();
                        setState(166);
                        match(OR);
                        setState(167);
                        ((SuperFormulaErrorORContext) _localctx).secondSuperWO =
                                superFormulaWithoutOr();
                    }
                    break;
                case 3:
                    _localctx = new SuperFormulaErrorIMPLIESContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(169);
                        ((SuperFormulaErrorIMPLIESContext) _localctx).firstSub = subFormula();
                        setState(170);
                        match(IMPLIES);
                        setState(171);
                        ((SuperFormulaErrorIMPLIESContext) _localctx).secondSuper = superFormula();
                    }
                    break;
                case 4:
                    _localctx = new SuperFormulaErrorEQUIVContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(173);
                        ((SuperFormulaErrorEQUIVContext) _localctx).firstSub = subFormula();
                        setState(174);
                        match(EQUIV);
                        setState(175);
                        ((SuperFormulaErrorEQUIVContext) _localctx).secondSuper = superFormula();
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

    public static class SubFormulaContext extends ParserRuleContext {
        public SubFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subFormula;
        }

        public SubFormulaContext() {}

        public void copyFrom(SubFormulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class BRACKETSContext extends SubFormulaContext {
        public TerminalNode OBRACKET() {
            return getToken(ModalFormulaSetParser.OBRACKET, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(ModalFormulaSetParser.CBRACKET, 0);
        }

        public BRACKETSContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitBRACKETS(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PARENSContext extends SubFormulaContext {
        public TerminalNode OPAREN() {
            return getToken(ModalFormulaSetParser.OPAREN, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(ModalFormulaSetParser.CPAREN, 0);
        }

        public PARENSContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitPARENS(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class DIAMONDContext extends SubFormulaContext {
        public TerminalNode DIAMOND() {
            return getToken(ModalFormulaSetParser.DIAMOND, 0);
        }

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public DIAMONDContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitDIAMOND(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class VARIABLEContext extends SubFormulaContext {
        public Token var;

        public TerminalNode VARIABLE() {
            return getToken(ModalFormulaSetParser.VARIABLE, 0);
        }

        public VARIABLEContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitVARIABLE(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TRUEContext extends SubFormulaContext {
        public TerminalNode TRUE() {
            return getToken(ModalFormulaSetParser.TRUE, 0);
        }

        public TRUEContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitTRUE(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NEGATIONContext extends SubFormulaContext {
        public TerminalNode NEG() {
            return getToken(ModalFormulaSetParser.NEG, 0);
        }

        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public NEGATIONContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitNEGATION(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FALSEContext extends SubFormulaContext {
        public TerminalNode FALSE() {
            return getToken(ModalFormulaSetParser.FALSE, 0);
        }

        public FALSEContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitFALSE(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BOXContext extends SubFormulaContext {
        public SubFormulaContext subFormula() {
            return getRuleContext(SubFormulaContext.class, 0);
        }

        public TerminalNode BOX() {
            return getToken(ModalFormulaSetParser.BOX, 0);
        }

        public BOXContext(SubFormulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof ModalFormulaSetParserVisitor)
                return ((ModalFormulaSetParserVisitor<? extends T>) visitor).visitBOX(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubFormulaContext subFormula() throws RecognitionException {
        SubFormulaContext _localctx = new SubFormulaContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_subFormula);
        try {
            setState(196);
            switch (_input.LA(1)) {
                case TRUE:
                    _localctx = new TRUEContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(179);
                        match(TRUE);
                    }
                    break;
                case FALSE:
                    _localctx = new FALSEContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(180);
                        match(FALSE);
                    }
                    break;
                case VARIABLE:
                    _localctx = new VARIABLEContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(181);
                        ((VARIABLEContext) _localctx).var = match(VARIABLE);
                    }
                    break;
                case NEG:
                    _localctx = new NEGATIONContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(182);
                        match(NEG);
                        setState(183);
                        subFormula();
                    }
                    break;
                case BOX:
                    _localctx = new BOXContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(184);
                        match(BOX);
                        setState(185);
                        subFormula();
                    }
                    break;
                case DIAMOND:
                    _localctx = new DIAMONDContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(186);
                        match(DIAMOND);
                        setState(187);
                        subFormula();
                    }
                    break;
                case OBRACKET:
                    _localctx = new BRACKETSContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                    {
                        setState(188);
                        match(OBRACKET);
                        setState(189);
                        formula();
                        setState(190);
                        match(CBRACKET);
                    }
                    break;
                case OPAREN:
                    _localctx = new PARENSContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                    {
                        setState(192);
                        match(OPAREN);
                        setState(193);
                        formula();
                        setState(194);
                        match(CPAREN);
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

    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26\u00c9\4\2\t\2"
                    + "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
                    + "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
                    + "\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3\63"
                    + "\n\3\3\4\3\4\3\4\3\4\3\4\6\4:\n\4\r\4\16\4;\5\4>\n\4\3\5\3\5\5\5B\n\5"
                    + "\3\6\3\6\3\6\6\6G\n\6\r\6\16\6H\5\6K\n\6\3\7\3\7\5\7O\n\7\3\b\3\b\3\b"
                    + "\3\t\3\t\5\tV\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\6\13a\n\13"
                    + "\r\13\16\13b\5\13e\n\13\3\f\3\f\5\fi\n\f\3\r\3\r\3\r\6\rn\n\r\r\r\16\r"
                    + "o\5\rr\n\r\3\16\3\16\3\16\3\17\3\17\5\17y\n\17\3\20\3\20\3\20\6\20~\n"
                    + "\20\r\20\16\20\177\3\20\5\20\u0083\n\20\3\21\3\21\3\21\6\21\u0088\n\21"
                    + "\r\21\16\21\u0089\3\21\5\21\u008d\n\21\3\22\3\22\3\22\6\22\u0092\n\22"
                    + "\r\22\16\22\u0093\3\22\5\22\u0097\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3"
                    + "\23\3\23\3\23\5\23\u00a2\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"
                    + "\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00b4\n\24\3\25\3\25\3\25"
                    + "\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"
                    + "\5\25\u00c7\n\25\3\25\2\2\26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""
                    + "$&(\2\2\u00d6\2*\3\2\2\2\4\62\3\2\2\2\6=\3\2\2\2\bA\3\2\2\2\nJ\3\2\2\2"
                    + "\fN\3\2\2\2\16P\3\2\2\2\20U\3\2\2\2\22W\3\2\2\2\24d\3\2\2\2\26h\3\2\2"
                    + "\2\30q\3\2\2\2\32s\3\2\2\2\34x\3\2\2\2\36\u0082\3\2\2\2 \u008c\3\2\2\2"
                    + "\"\u0096\3\2\2\2$\u00a1\3\2\2\2&\u00b3\3\2\2\2(\u00c6\3\2\2\2*+\5\4\3"
                    + "\2+,\7\2\2\3,\3\3\2\2\2-.\7\23\2\2./\5\6\4\2/\60\7\24\2\2\60\63\3\2\2"
                    + "\2\61\63\5\6\4\2\62-\3\2\2\2\62\61\3\2\2\2\63\5\3\2\2\2\64>\3\2\2\2\65"
                    + ">\5\n\6\2\669\5\b\5\2\678\7\25\2\28:\5\b\5\29\67\3\2\2\2:;\3\2\2\2;9\3"
                    + "\2\2\2;<\3\2\2\2<>\3\2\2\2=\64\3\2\2\2=\65\3\2\2\2=\66\3\2\2\2>\7\3\2"
                    + "\2\2?B\5\n\6\2@B\3\2\2\2A?\3\2\2\2A@\3\2\2\2B\t\3\2\2\2CK\5\f\7\2DF\5"
                    + "\f\7\2EG\5\f\7\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JC\3"
                    + "\2\2\2JD\3\2\2\2K\13\3\2\2\2LO\5\22\n\2MO\5\34\17\2NL\3\2\2\2NM\3\2\2"
                    + "\2O\r\3\2\2\2PQ\5\20\t\2QR\7\2\2\3R\17\3\2\2\2SV\5\22\n\2TV\5\24\13\2"
                    + "US\3\2\2\2UT\3\2\2\2V\21\3\2\2\2WX\7\23\2\2XY\5\24\13\2YZ\7\24\2\2Z\23"
                    + "\3\2\2\2[e\3\2\2\2\\e\5\30\r\2]`\5\26\f\2^_\7\25\2\2_a\5\26\f\2`^\3\2"
                    + "\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2d[\3\2\2\2d\\\3\2\2\2d]\3"
                    + "\2\2\2e\25\3\2\2\2fi\5\30\r\2gi\3\2\2\2hf\3\2\2\2hg\3\2\2\2i\27\3\2\2"
                    + "\2jr\5\34\17\2km\5\34\17\2ln\5\34\17\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2o"
                    + "p\3\2\2\2pr\3\2\2\2qj\3\2\2\2qk\3\2\2\2r\31\3\2\2\2st\5\34\17\2tu\7\2"
                    + "\2\3u\33\3\2\2\2vy\5\36\20\2wy\5(\25\2xv\3\2\2\2xw\3\2\2\2y\35\3\2\2\2"
                    + "z}\5(\25\2{|\7\6\2\2|~\5(\25\2}{\3\2\2\2~\177\3\2\2\2\177}\3\2\2\2\177"
                    + "\u0080\3\2\2\2\u0080\u0083\3\2\2\2\u0081\u0083\5\"\22\2\u0082z\3\2\2\2"
                    + "\u0082\u0081\3\2\2\2\u0083\37\3\2\2\2\u0084\u0087\5(\25\2\u0085\u0086"
                    + "\7\6\2\2\u0086\u0088\5(\25\2\u0087\u0085\3\2\2\2\u0088\u0089\3\2\2\2\u0089"
                    + "\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u008d\5$"
                    + "\23\2\u008c\u0084\3\2\2\2\u008c\u008b\3\2\2\2\u008d!\3\2\2\2\u008e\u0091"
                    + "\5(\25\2\u008f\u0090\7\7\2\2\u0090\u0092\5(\25\2\u0091\u008f\3\2\2\2\u0092"
                    + "\u0093\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0097\3\2"
                    + "\2\2\u0095\u0097\5$\23\2\u0096\u008e\3\2\2\2\u0096\u0095\3\2\2\2\u0097"
                    + "#\3\2\2\2\u0098\u0099\5(\25\2\u0099\u009a\7\t\2\2\u009a\u009b\5(\25\2"
                    + "\u009b\u00a2\3\2\2\2\u009c\u009d\5(\25\2\u009d\u009e\7\b\2\2\u009e\u009f"
                    + "\5(\25\2\u009f\u00a2\3\2\2\2\u00a0\u00a2\5&\24\2\u00a1\u0098\3\2\2\2\u00a1"
                    + "\u009c\3\2\2\2\u00a1\u00a0\3\2\2\2\u00a2%\3\2\2\2\u00a3\u00a4\5(\25\2"
                    + "\u00a4\u00a5\7\6\2\2\u00a5\u00a6\5\"\22\2\u00a6\u00b4\3\2\2\2\u00a7\u00a8"
                    + "\5(\25\2\u00a8\u00a9\7\7\2\2\u00a9\u00aa\5 \21\2\u00aa\u00b4\3\2\2\2\u00ab"
                    + "\u00ac\5(\25\2\u00ac\u00ad\7\t\2\2\u00ad\u00ae\5\36\20\2\u00ae\u00b4\3"
                    + "\2\2\2\u00af\u00b0\5(\25\2\u00b0\u00b1\7\b\2\2\u00b1\u00b2\5\36\20\2\u00b2"
                    + "\u00b4\3\2\2\2\u00b3\u00a3\3\2\2\2\u00b3\u00a7\3\2\2\2\u00b3\u00ab\3\2"
                    + "\2\2\u00b3\u00af\3\2\2\2\u00b4\'\3\2\2\2\u00b5\u00c7\7\n\2\2\u00b6\u00c7"
                    + "\7\13\2\2\u00b7\u00c7\7\20\2\2\u00b8\u00b9\7\3\2\2\u00b9\u00c7\5(\25\2"
                    + "\u00ba\u00bb\7\5\2\2\u00bb\u00c7\5(\25\2\u00bc\u00bd\7\4\2\2\u00bd\u00c7"
                    + "\5(\25\2\u00be\u00bf\7\f\2\2\u00bf\u00c0\5\34\17\2\u00c0\u00c1\7\r\2\2"
                    + "\u00c1\u00c7\3\2\2\2\u00c2\u00c3\7\16\2\2\u00c3\u00c4\5\34\17\2\u00c4"
                    + "\u00c5\7\17\2\2\u00c5\u00c7\3\2\2\2\u00c6\u00b5\3\2\2\2\u00c6\u00b6\3"
                    + "\2\2\2\u00c6\u00b7\3\2\2\2\u00c6\u00b8\3\2\2\2\u00c6\u00ba\3\2\2\2\u00c6"
                    + "\u00bc\3\2\2\2\u00c6\u00be\3\2\2\2\u00c6\u00c2\3\2\2\2\u00c7)\3\2\2\2"
                    + "\31\62;=AHJNUbdhoqx\177\u0082\u0089\u008c\u0093\u0096\u00a1\u00b3\u00c6";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
