// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/predicate/formula/pattern/PatternParser.g4 by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.pattern;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReaderProperties;
import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PatternParser extends AbstractParser {
    static {
        RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int COMPLEMENT = 1,
            NOFORMULA = 2,
            ANYFORMULA = 3,
            READ = 4,
            PLUS = 5,
            STAR = 6,
            AT = 7,
            SQUOTE = 8,
            NEG = 9,
            AND = 10,
            OR = 11,
            EQUIV = 12,
            IMPLIES = 13,
            FORALL = 14,
            EXISTS = 15,
            TRUE = 16,
            FALSE = 17,
            OBRACKET = 18,
            CBRACKET = 19,
            OPAREN = 20,
            CPAREN = 21,
            ARGUMENTSEP = 22,
            ISYMBOL = 23,
            OBRACE = 24,
            CBRACE = 25,
            RELINFIXISYMBOL = 26,
            FUNINFIXISYMBOL = 27,
            REVERSE_IMPLIES = 28,
            WS = 29,
            OPERATOR_OR_ISYMBOL = 30,
            MULTI = 31,
            CONTAINS = 32,
            DISTINCT = 33;
    public static final String[] tokenNames = {
        "<INVALID>",
        "COMPLEMENT",
        "NOFORMULA",
        "ANYFORMULA",
        "READ",
        "PLUS",
        "STAR",
        "AT",
        "SQUOTE",
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
        "ISYMBOL",
        "OBRACE",
        "CBRACE",
        "RELINFIXISYMBOL",
        "FUNINFIXISYMBOL",
        "REVERSE_IMPLIES",
        "WS",
        "OPERATOR_OR_ISYMBOL",
        "MULTI",
        "CONTAINS",
        "DISTINCT"
    };
    public static final int RULE_initFormula = 0,
            RULE_initTerm = 1,
            RULE_formula = 2,
            RULE_superformula = 3,
            RULE_subformulaOrS = 4,
            RULE_subformula = 5,
            RULE_notInfixSubformula = 6,
            RULE_subformulae = 7,
            RULE_atomarSubformula = 8,
            RULE_namedFormula = 9,
            RULE_namedSubformula = 10,
            RULE_subformulaWithName = 11,
            RULE_prefixSubformula = 12,
            RULE_infixSubformula = 13,
            RULE_termOrS = 14,
            RULE_term = 15,
            RULE_notInfixTerm = 16,
            RULE_terms = 17,
            RULE_atomarTerm = 18,
            RULE_prefixTerm = 19,
            RULE_namedTerm = 20,
            RULE_infixTerm = 21,
            RULE_iSymbol = 22,
            RULE_name = 23;
    public static final String[] ruleNames = {
        "initFormula",
        "initTerm",
        "formula",
        "superformula",
        "subformulaOrS",
        "subformula",
        "notInfixSubformula",
        "subformulae",
        "atomarSubformula",
        "namedFormula",
        "namedSubformula",
        "subformulaWithName",
        "prefixSubformula",
        "infixSubformula",
        "termOrS",
        "term",
        "notInfixTerm",
        "terms",
        "atomarTerm",
        "prefixTerm",
        "namedTerm",
        "infixTerm",
        "iSymbol",
        "name"
    };

    @Override
    public String getGrammarFileName() {
        return "PatternParser.g4";
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

    private PatternReaderProperties properties = PatternReaderProperties.createDefault();

    public void setProperties(PatternReaderProperties props) {
        if (props == null) throw new IllegalArgumentException("props must not be null");
        properties = props;
    }

    public PatternReaderProperties getProperties() {
        return properties;
    }

    public PatternParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class InitFormulaContext extends ParserRuleContext {
        public TerminalNode EOF() {
            return getToken(PatternParser.EOF, 0);
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
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitInitFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitFormulaContext initFormula() throws RecognitionException {
        InitFormulaContext _localctx = new InitFormulaContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_initFormula);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(48);
                formula();
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

    public static class InitTermContext extends ParserRuleContext {
        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(PatternParser.EOF, 0);
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
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitInitTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitTermContext initTerm() throws RecognitionException {
        InitTermContext _localctx = new InitTermContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_initTerm);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(51);
                term();
                setState(52);
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
        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public SuperformulaContext superformula() {
            return getRuleContext(SuperformulaContext.class, 0);
        }

        public FormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_formula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final FormulaContext formula() throws RecognitionException {
        FormulaContext _localctx = new FormulaContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_formula);
        try {
            setState(56);
            switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(54);
                        superformula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(55);
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

    public static class OrLeadingNoFormulaContext extends SuperformulaContext {
        public SubformulaOrSContext firsts;
        public SubformulaOrSContext subformulaOrS;
        public List<SubformulaOrSContext> furthers = new ArrayList<SubformulaOrSContext>();

        public List<SubformulaOrSContext> subformulaOrS() {
            return getRuleContexts(SubformulaOrSContext.class);
        }

        public List<TerminalNode> OR() {
            return getTokens(PatternParser.OR);
        }

        public TerminalNode NOFORMULA(int i) {
            return getToken(PatternParser.NOFORMULA, i);
        }

        public TerminalNode OR(int i) {
            return getToken(PatternParser.OR, i);
        }

        public SubformulaOrSContext subformulaOrS(int i) {
            return getRuleContext(SubformulaOrSContext.class, i);
        }

        public List<TerminalNode> NOFORMULA() {
            return getTokens(PatternParser.NOFORMULA);
        }

        public OrLeadingNoFormulaContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitOrLeadingNoFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AlternativeContext extends SuperformulaContext {
        public SubformulaContext first;
        public SubformulaContext subformula;
        public List<SubformulaContext> further = new ArrayList<SubformulaContext>();

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public List<TerminalNode> PLUS() {
            return getTokens(PatternParser.PLUS);
        }

        public TerminalNode PLUS(int i) {
            return getToken(PatternParser.PLUS, i);
        }

        public AlternativeContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAlternative(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AndLeadingNoFormulaContext extends SuperformulaContext {
        public SubformulaOrSContext firsts;
        public SubformulaOrSContext subformulaOrS;
        public List<SubformulaOrSContext> furthers = new ArrayList<SubformulaOrSContext>();

        public List<SubformulaOrSContext> subformulaOrS() {
            return getRuleContexts(SubformulaOrSContext.class);
        }

        public TerminalNode AND(int i) {
            return getToken(PatternParser.AND, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(PatternParser.AND);
        }

        public TerminalNode NOFORMULA(int i) {
            return getToken(PatternParser.NOFORMULA, i);
        }

        public SubformulaOrSContext subformulaOrS(int i) {
            return getRuleContext(SubformulaOrSContext.class, i);
        }

        public List<TerminalNode> NOFORMULA() {
            return getTokens(PatternParser.NOFORMULA);
        }

        public AndLeadingNoFormulaContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAndLeadingNoFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class OrLeadingSubformulaContext extends SuperformulaContext {
        public SubformulaOrSContext firsts;
        public SubformulaOrSContext subformulaOrS;
        public List<SubformulaOrSContext> furthers = new ArrayList<SubformulaOrSContext>();

        public List<SubformulaOrSContext> subformulaOrS() {
            return getRuleContexts(SubformulaOrSContext.class);
        }

        public List<TerminalNode> OR() {
            return getTokens(PatternParser.OR);
        }

        public TerminalNode NOFORMULA(int i) {
            return getToken(PatternParser.NOFORMULA, i);
        }

        public TerminalNode OR(int i) {
            return getToken(PatternParser.OR, i);
        }

        public SubformulaOrSContext subformulaOrS(int i) {
            return getRuleContext(SubformulaOrSContext.class, i);
        }

        public List<TerminalNode> NOFORMULA() {
            return getTokens(PatternParser.NOFORMULA);
        }

        public OrLeadingSubformulaContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitOrLeadingSubformula(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ImpliesContext extends SuperformulaContext {
        public SubformulaContext first;
        public SubformulaContext second;

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public TerminalNode IMPLIES() {
            return getToken(PatternParser.IMPLIES, 0);
        }

        public ImpliesContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitImplies(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class EquivContext extends SuperformulaContext {
        public SubformulaContext first;
        public SubformulaContext second;

        public TerminalNode EQUIV() {
            return getToken(PatternParser.EQUIV, 0);
        }

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public EquivContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitEquiv(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AndLeadingSubformulaContext extends SuperformulaContext {
        public SubformulaOrSContext firsts;
        public SubformulaOrSContext subformulaOrS;
        public List<SubformulaOrSContext> furthers = new ArrayList<SubformulaOrSContext>();

        public List<SubformulaOrSContext> subformulaOrS() {
            return getRuleContexts(SubformulaOrSContext.class);
        }

        public TerminalNode AND(int i) {
            return getToken(PatternParser.AND, i);
        }

        public List<TerminalNode> AND() {
            return getTokens(PatternParser.AND);
        }

        public TerminalNode NOFORMULA(int i) {
            return getToken(PatternParser.NOFORMULA, i);
        }

        public SubformulaOrSContext subformulaOrS(int i) {
            return getRuleContext(SubformulaOrSContext.class, i);
        }

        public List<TerminalNode> NOFORMULA() {
            return getTokens(PatternParser.NOFORMULA);
        }

        public AndLeadingSubformulaContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitAndLeadingSubformula(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class MultiConstraintContext extends SuperformulaContext {
        public SubformulaContext first;
        public SubformulaContext subformula;
        public List<SubformulaContext> further = new ArrayList<SubformulaContext>();

        public List<TerminalNode> MULTI() {
            return getTokens(PatternParser.MULTI);
        }

        public List<SubformulaContext> subformula() {
            return getRuleContexts(SubformulaContext.class);
        }

        public SubformulaContext subformula(int i) {
            return getRuleContext(SubformulaContext.class, i);
        }

        public TerminalNode MULTI(int i) {
            return getToken(PatternParser.MULTI, i);
        }

        public MultiConstraintContext(SuperformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitMultiConstraint(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SuperformulaContext superformula() throws RecognitionException {
        SuperformulaContext _localctx = new SuperformulaContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_superformula);
        int _la;
        try {
            int _alt;
            setState(134);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    _localctx = new AlternativeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(58);
                        ((AlternativeContext) _localctx).first = subformula();
                        setState(61);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(59);
                                    match(PLUS);
                                    setState(60);
                                    ((AlternativeContext) _localctx).subformula = subformula();
                                    ((AlternativeContext) _localctx)
                                            .further.add(
                                                    ((AlternativeContext) _localctx).subformula);
                                }
                            }
                            setState(63);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == PLUS);
                    }
                    break;
                case 2:
                    _localctx = new MultiConstraintContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(65);
                        ((MultiConstraintContext) _localctx).first = subformula();
                        setState(68);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(66);
                                    match(MULTI);
                                    setState(67);
                                    ((MultiConstraintContext) _localctx).subformula = subformula();
                                    ((MultiConstraintContext) _localctx)
                                            .further.add(
                                                    ((MultiConstraintContext) _localctx)
                                                            .subformula);
                                }
                            }
                            setState(70);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == MULTI);
                    }
                    break;
                case 3:
                    _localctx = new AndLeadingSubformulaContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(72);
                        ((AndLeadingSubformulaContext) _localctx).firsts = subformulaOrS();
                        setState(78);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(73);
                                    match(AND);
                                    setState(76);
                                    switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                                        case 1:
                                            {
                                                setState(74);
                                                ((AndLeadingSubformulaContext) _localctx)
                                                                .subformulaOrS =
                                                        subformulaOrS();
                                                ((AndLeadingSubformulaContext) _localctx)
                                                        .furthers.add(
                                                                ((AndLeadingSubformulaContext)
                                                                                _localctx)
                                                                        .subformulaOrS);
                                            }
                                            break;
                                        case 2:
                                            {
                                                setState(75);
                                                match(NOFORMULA);
                                            }
                                            break;
                                    }
                                }
                            }
                            setState(80);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == AND);
                    }
                    break;
                case 4:
                    _localctx = new AndLeadingNoFormulaContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(84);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(82);
                                            match(NOFORMULA);
                                            setState(83);
                                            match(AND);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(86);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(88);
                        ((AndLeadingNoFormulaContext) _localctx).firsts = subformulaOrS();
                        setState(96);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == AND) {
                            {
                                {
                                    setState(89);
                                    match(AND);
                                    setState(92);
                                    switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                                        case 1:
                                            {
                                                setState(90);
                                                ((AndLeadingNoFormulaContext) _localctx)
                                                                .subformulaOrS =
                                                        subformulaOrS();
                                                ((AndLeadingNoFormulaContext) _localctx)
                                                        .furthers.add(
                                                                ((AndLeadingNoFormulaContext)
                                                                                _localctx)
                                                                        .subformulaOrS);
                                            }
                                            break;
                                        case 2:
                                            {
                                                setState(91);
                                                match(NOFORMULA);
                                            }
                                            break;
                                    }
                                }
                            }
                            setState(98);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                    break;
                case 5:
                    _localctx = new OrLeadingSubformulaContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(99);
                        ((OrLeadingSubformulaContext) _localctx).firsts = subformulaOrS();
                        setState(105);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(100);
                                    match(OR);
                                    setState(103);
                                    switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                                        case 1:
                                            {
                                                setState(101);
                                                ((OrLeadingSubformulaContext) _localctx)
                                                                .subformulaOrS =
                                                        subformulaOrS();
                                                ((OrLeadingSubformulaContext) _localctx)
                                                        .furthers.add(
                                                                ((OrLeadingSubformulaContext)
                                                                                _localctx)
                                                                        .subformulaOrS);
                                            }
                                            break;
                                        case 2:
                                            {
                                                setState(102);
                                                match(NOFORMULA);
                                            }
                                            break;
                                    }
                                }
                            }
                            setState(107);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == OR);
                    }
                    break;
                case 6:
                    _localctx = new OrLeadingNoFormulaContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(111);
                        _errHandler.sync(this);
                        _alt = 1;
                        do {
                            switch (_alt) {
                                case 1:
                                    {
                                        {
                                            setState(109);
                                            match(NOFORMULA);
                                            setState(110);
                                            match(OR);
                                        }
                                    }
                                    break;
                                default:
                                    throw new NoViableAltException(this);
                            }
                            setState(113);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                        } while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
                        setState(115);
                        ((OrLeadingNoFormulaContext) _localctx).firsts = subformulaOrS();
                        setState(123);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == OR) {
                            {
                                {
                                    setState(116);
                                    match(OR);
                                    setState(119);
                                    switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                                        case 1:
                                            {
                                                setState(117);
                                                ((OrLeadingNoFormulaContext) _localctx)
                                                                .subformulaOrS =
                                                        subformulaOrS();
                                                ((OrLeadingNoFormulaContext) _localctx)
                                                        .furthers.add(
                                                                ((OrLeadingNoFormulaContext)
                                                                                _localctx)
                                                                        .subformulaOrS);
                                            }
                                            break;
                                        case 2:
                                            {
                                                setState(118);
                                                match(NOFORMULA);
                                            }
                                            break;
                                    }
                                }
                            }
                            setState(125);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                    break;
                case 7:
                    _localctx = new ImpliesContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                    {
                        setState(126);
                        ((ImpliesContext) _localctx).first = subformula();
                        setState(127);
                        match(IMPLIES);
                        setState(128);
                        ((ImpliesContext) _localctx).second = subformula();
                    }
                    break;
                case 8:
                    _localctx = new EquivContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                    {
                        setState(130);
                        ((EquivContext) _localctx).first = subformula();
                        setState(131);
                        match(EQUIV);
                        setState(132);
                        ((EquivContext) _localctx).second = subformula();
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

    public static class SubformulaOrSContext extends ParserRuleContext {
        public SubformulaeContext subformulae() {
            return getRuleContext(SubformulaeContext.class, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public SubformulaOrSContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subformulaOrS;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitSubformulaOrS(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaOrSContext subformulaOrS() throws RecognitionException {
        SubformulaOrSContext _localctx = new SubformulaOrSContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_subformulaOrS);
        try {
            setState(138);
            switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(136);
                        subformula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(137);
                        subformulae();
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
        public NamedSubformulaContext namedSubformula() {
            return getRuleContext(NamedSubformulaContext.class, 0);
        }

        public InfixSubformulaContext infixSubformula() {
            return getRuleContext(InfixSubformulaContext.class, 0);
        }

        public PrefixSubformulaContext prefixSubformula() {
            return getRuleContext(PrefixSubformulaContext.class, 0);
        }

        public NamedFormulaContext namedFormula() {
            return getRuleContext(NamedFormulaContext.class, 0);
        }

        public AtomarSubformulaContext atomarSubformula() {
            return getRuleContext(AtomarSubformulaContext.class, 0);
        }

        public SubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subformula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitSubformula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaContext subformula() throws RecognitionException {
        SubformulaContext _localctx = new SubformulaContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_subformula);
        try {
            setState(145);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(140);
                        namedFormula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(141);
                        namedSubformula();
                    }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(142);
                        atomarSubformula();
                    }
                    break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(143);
                        prefixSubformula();
                    }
                    break;
                case 5:
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(144);
                        infixSubformula();
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

    public static class NotInfixSubformulaContext extends ParserRuleContext {
        public NamedSubformulaContext namedSubformula() {
            return getRuleContext(NamedSubformulaContext.class, 0);
        }

        public PrefixSubformulaContext prefixSubformula() {
            return getRuleContext(PrefixSubformulaContext.class, 0);
        }

        public NamedFormulaContext namedFormula() {
            return getRuleContext(NamedFormulaContext.class, 0);
        }

        public AtomarSubformulaContext atomarSubformula() {
            return getRuleContext(AtomarSubformulaContext.class, 0);
        }

        public NotInfixSubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_notInfixSubformula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNotInfixSubformula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NotInfixSubformulaContext notInfixSubformula() throws RecognitionException {
        NotInfixSubformulaContext _localctx = new NotInfixSubformulaContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_notInfixSubformula);
        try {
            setState(151);
            switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(147);
                        namedFormula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(148);
                        namedSubformula();
                    }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(149);
                        atomarSubformula();
                    }
                    break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(150);
                        prefixSubformula();
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

    public static class SubformulaeContext extends ParserRuleContext {
        public SubformulaeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subformulae;
        }

        public SubformulaeContext() {}

        public void copyFrom(SubformulaeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnyStarSubsContext extends SubformulaeContext {
        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyStarSubsContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyStarSubs(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NamedSubformulaStarContext extends SubformulaeContext {
        public NamedSubformulaContext sub;

        public NamedSubformulaContext namedSubformula() {
            return getRuleContext(NamedSubformulaContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public NamedSubformulaStarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNamedSubformulaStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubformulaStarContext extends SubformulaeContext {
        public AtomarSubformulaContext sub;

        public TerminalNode AT() {
            return getToken(PatternParser.AT, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AtomarSubformulaContext atomarSubformula() {
            return getRuleContext(AtomarSubformulaContext.class, 0);
        }

        public SubformulaStarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitSubformulaStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyNameStarSubsContext extends SubformulaeContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyNameStarSubsContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyNameStarSubs(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaeContext subformulae() throws RecognitionException {
        SubformulaeContext _localctx = new SubformulaeContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_subformulae);
        try {
            setState(168);
            switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
                case 1:
                    _localctx = new SubformulaStarContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(157);
                        switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                            case 1:
                                {
                                }
                                break;
                            case 2:
                                {
                                    setState(154);
                                    name();
                                    setState(155);
                                    match(AT);
                                }
                                break;
                        }
                        setState(159);
                        ((SubformulaStarContext) _localctx).sub = atomarSubformula();
                        setState(160);
                        match(STAR);
                    }
                    break;
                case 2:
                    _localctx = new NamedSubformulaStarContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(162);
                        ((NamedSubformulaStarContext) _localctx).sub = namedSubformula();
                        setState(163);
                        match(STAR);
                    }
                    break;
                case 3:
                    _localctx = new AnyStarSubsContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(165);
                        match(STAR);
                    }
                    break;
                case 4:
                    _localctx = new AnyNameStarSubsContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(166);
                        match(STAR);
                        setState(167);
                        name();
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

    public static class AtomarSubformulaContext extends ParserRuleContext {
        public AtomarSubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_atomarSubformula;
        }

        public AtomarSubformulaContext() {}

        public void copyFrom(AtomarSubformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnyFormulaAtomarSubContext extends AtomarSubformulaContext {
        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaAtomarSubContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyFormulaAtomarSub(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixRelationSymbolBracketsContext extends AtomarSubformulaContext {
        public TermOrSContext firsts;
        public TermOrSContext termOrS;
        public List<TermOrSContext> furthers = new ArrayList<TermOrSContext>();

        public TerminalNode OBRACKET() {
            return getToken(PatternParser.OBRACKET, 0);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(PatternParser.ARGUMENTSEP, i);
        }

        public TerminalNode CBRACKET() {
            return getToken(PatternParser.CBRACKET, 0);
        }

        public TermOrSContext termOrS(int i) {
            return getRuleContext(TermOrSContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(PatternParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public List<TermOrSContext> termOrS() {
            return getRuleContexts(TermOrSContext.class);
        }

        public PrefixRelationSymbolBracketsContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixRelationSymbolBrackets(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixRelationSymbolParenthesesContext extends AtomarSubformulaContext {
        public TermOrSContext firsts;
        public TermOrSContext termOrS;
        public List<TermOrSContext> furthers = new ArrayList<TermOrSContext>();

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(PatternParser.ARGUMENTSEP, i);
        }

        public TerminalNode OPAREN() {
            return getToken(PatternParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(PatternParser.CPAREN, 0);
        }

        public TermOrSContext termOrS(int i) {
            return getRuleContext(TermOrSContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(PatternParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public List<TermOrSContext> termOrS() {
            return getRuleContexts(TermOrSContext.class);
        }

        public PrefixRelationSymbolParenthesesContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixRelationSymbolParentheses(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TrueContext extends AtomarSubformulaContext {
        public TerminalNode TRUE() {
            return getToken(PatternParser.TRUE, 0);
        }

        public TrueContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitTrue(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FalseContext extends AtomarSubformulaContext {
        public TerminalNode FALSE() {
            return getToken(PatternParser.FALSE, 0);
        }

        public FalseContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitFalse(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaInBracketContext extends AtomarSubformulaContext {
        public TerminalNode OBRACKET() {
            return getToken(PatternParser.OBRACKET, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(PatternParser.CBRACKET, 0);
        }

        public FormulaInBracketContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitFormulaInBracket(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FormulaInParenContext extends AtomarSubformulaContext {
        public TerminalNode OPAREN() {
            return getToken(PatternParser.OPAREN, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(PatternParser.CPAREN, 0);
        }

        public FormulaInParenContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitFormulaInParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixRelationSymbolContext extends AtomarSubformulaContext {
        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public PrefixRelationSymbolContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixRelationSymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AtomarSubformulaContext atomarSubformula() throws RecognitionException {
        AtomarSubformulaContext _localctx = new AtomarSubformulaContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_atomarSubformula);
        int _la;
        try {
            setState(210);
            switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
                case 1:
                    _localctx = new TrueContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(170);
                        match(TRUE);
                    }
                    break;
                case 2:
                    _localctx = new FalseContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(171);
                        match(FALSE);
                    }
                    break;
                case 3:
                    _localctx = new FormulaInBracketContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(172);
                        match(OBRACKET);
                        setState(173);
                        formula();
                        setState(174);
                        match(CBRACKET);
                    }
                    break;
                case 4:
                    _localctx = new FormulaInParenContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(176);
                        match(OPAREN);
                        setState(177);
                        formula();
                        setState(178);
                        match(CPAREN);
                    }
                    break;
                case 5:
                    _localctx = new PrefixRelationSymbolContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(180);
                        iSymbol();
                    }
                    break;
                case 6:
                    _localctx = new PrefixRelationSymbolBracketsContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(181);
                        iSymbol();
                        setState(182);
                        match(OBRACKET);
                        setState(191);
                        switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                            case 1:
                                {
                                    setState(183);
                                    ((PrefixRelationSymbolBracketsContext) _localctx).firsts =
                                            termOrS();
                                    setState(188);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(184);
                                                match(ARGUMENTSEP);
                                                setState(185);
                                                ((PrefixRelationSymbolBracketsContext) _localctx)
                                                                .termOrS =
                                                        termOrS();
                                                ((PrefixRelationSymbolBracketsContext) _localctx)
                                                        .furthers.add(
                                                                ((PrefixRelationSymbolBracketsContext)
                                                                                _localctx)
                                                                        .termOrS);
                                            }
                                        }
                                        setState(190);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(193);
                        match(CBRACKET);
                    }
                    break;
                case 7:
                    _localctx = new PrefixRelationSymbolParenthesesContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                    {
                        setState(195);
                        iSymbol();
                        setState(196);
                        match(OPAREN);
                        setState(205);
                        switch (getInterpreter().adaptivePredict(_input, 22, _ctx)) {
                            case 1:
                                {
                                    setState(197);
                                    ((PrefixRelationSymbolParenthesesContext) _localctx).firsts =
                                            termOrS();
                                    setState(202);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(198);
                                                match(ARGUMENTSEP);
                                                setState(199);
                                                ((PrefixRelationSymbolParenthesesContext) _localctx)
                                                                .termOrS =
                                                        termOrS();
                                                ((PrefixRelationSymbolParenthesesContext) _localctx)
                                                        .furthers.add(
                                                                ((PrefixRelationSymbolParenthesesContext)
                                                                                _localctx)
                                                                        .termOrS);
                                            }
                                        }
                                        setState(204);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(207);
                        match(CPAREN);
                    }
                    break;
                case 8:
                    _localctx = new AnyFormulaAtomarSubContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                    {
                        setState(209);
                        match(ANYFORMULA);
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

    public static class NamedFormulaContext extends ParserRuleContext {
        public SubformulaWithNameContext subNI;

        public TerminalNode AT() {
            return getToken(PatternParser.AT, 0);
        }

        public SubformulaWithNameContext subformulaWithName() {
            return getRuleContext(SubformulaWithNameContext.class, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public NamedFormulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_namedFormula;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNamedFormula(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NamedFormulaContext namedFormula() throws RecognitionException {
        NamedFormulaContext _localctx = new NamedFormulaContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_namedFormula);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(212);
                name();
                setState(213);
                match(AT);
                setState(214);
                ((NamedFormulaContext) _localctx).subNI = subformulaWithName();
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

    public static class NamedSubformulaContext extends ParserRuleContext {
        public NamedSubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_namedSubformula;
        }

        public NamedSubformulaContext() {}

        public void copyFrom(NamedSubformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class ReadNameAtomarSubContext extends NamedSubformulaContext {
        public TerminalNode READ() {
            return getToken(PatternParser.READ, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public ReadNameAtomarSubContext(NamedSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitReadNameAtomarSub(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyFormulaWithNameAtomarSubContext extends NamedSubformulaContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaWithNameAtomarSubContext(NamedSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitAnyFormulaWithNameAtomarSub(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NamedSubformulaContext namedSubformula() throws RecognitionException {
        NamedSubformulaContext _localctx = new NamedSubformulaContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_namedSubformula);
        try {
            setState(220);
            switch (_input.LA(1)) {
                case ANYFORMULA:
                    _localctx = new AnyFormulaWithNameAtomarSubContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(216);
                        match(ANYFORMULA);
                        setState(217);
                        name();
                    }
                    break;
                case READ:
                    _localctx = new ReadNameAtomarSubContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(218);
                        match(READ);
                        setState(219);
                        name();
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

    public static class SubformulaWithNameContext extends ParserRuleContext {
        public PrefixSubformulaContext prefixSubformula() {
            return getRuleContext(PrefixSubformulaContext.class, 0);
        }

        public AtomarSubformulaContext atomarSubformula() {
            return getRuleContext(AtomarSubformulaContext.class, 0);
        }

        public SubformulaWithNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_subformulaWithName;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitSubformulaWithName(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaWithNameContext subformulaWithName() throws RecognitionException {
        SubformulaWithNameContext _localctx = new SubformulaWithNameContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_subformulaWithName);
        try {
            setState(224);
            switch (_input.LA(1)) {
                case ANYFORMULA:
                case SQUOTE:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case ISYMBOL:
                case RELINFIXISYMBOL:
                case FUNINFIXISYMBOL:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(222);
                        atomarSubformula();
                    }
                    break;
                case COMPLEMENT:
                case NEG:
                case FORALL:
                case EXISTS:
                case CONTAINS:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(223);
                        prefixSubformula();
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

    public static class PrefixSubformulaContext extends ParserRuleContext {
        public PrefixSubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prefixSubformula;
        }

        public PrefixSubformulaContext() {}

        public void copyFrom(PrefixSubformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class UniversalQuantifierContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode FORALL() {
            return getToken(PatternParser.FORALL, 0);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public UniversalQuantifierContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitUniversalQuantifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ContainsDescendantContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public TerminalNode CONTAINS() {
            return getToken(PatternParser.CONTAINS, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public ContainsDescendantContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitContainsDescendant(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NegationContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public TerminalNode NEG() {
            return getToken(PatternParser.NEG, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public NegationContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNegation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ExistentialQuantifierContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public TerminalNode EXISTS() {
            return getToken(PatternParser.EXISTS, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public ExistentialQuantifierContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitExistentialQuantifier(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ComplementContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode COMPLEMENT() {
            return getToken(PatternParser.COMPLEMENT, 0);
        }

        public ComplementContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitComplement(this);
            else return visitor.visitChildren(this);
        }
    }

    public final PrefixSubformulaContext prefixSubformula() throws RecognitionException {
        PrefixSubformulaContext _localctx = new PrefixSubformulaContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_prefixSubformula);
        try {
            setState(240);
            switch (_input.LA(1)) {
                case NEG:
                    _localctx = new NegationContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(226);
                        match(NEG);
                        setState(227);
                        ((NegationContext) _localctx).sub = subformula();
                    }
                    break;
                case FORALL:
                    _localctx = new UniversalQuantifierContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(228);
                        match(FORALL);
                        setState(229);
                        iSymbol();
                        setState(230);
                        ((UniversalQuantifierContext) _localctx).sub = subformula();
                    }
                    break;
                case EXISTS:
                    _localctx = new ExistentialQuantifierContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(232);
                        match(EXISTS);
                        setState(233);
                        iSymbol();
                        setState(234);
                        ((ExistentialQuantifierContext) _localctx).sub = subformula();
                    }
                    break;
                case COMPLEMENT:
                    _localctx = new ComplementContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(236);
                        match(COMPLEMENT);
                        setState(237);
                        ((ComplementContext) _localctx).sub = subformula();
                    }
                    break;
                case CONTAINS:
                    _localctx = new ContainsDescendantContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(238);
                        match(CONTAINS);
                        setState(239);
                        ((ContainsDescendantContext) _localctx).sub = subformula();
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

    public static class InfixSubformulaContext extends ParserRuleContext {
        public InfixSubformulaContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_infixSubformula;
        }

        public InfixSubformulaContext() {}

        public void copyFrom(InfixSubformulaContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class WithoutInfixRelationContext extends InfixSubformulaContext {
        public NotInfixTermContext firstPrefix;
        public Token sym;
        public NotInfixTermContext secondPrefix;

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(PatternParser.RELINFIXISYMBOL, 0);
        }

        public NotInfixTermContext notInfixTerm(int i) {
            return getRuleContext(NotInfixTermContext.class, i);
        }

        public List<NotInfixTermContext> notInfixTerm() {
            return getRuleContexts(NotInfixTermContext.class);
        }

        public WithoutInfixRelationContext(InfixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitWithoutInfixRelation(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class InfixRelationSymbolContext extends InfixSubformulaContext {
        public NotInfixTermContext firstPrefix;
        public Token sym;
        public NotInfixTermContext secondPrefix;

        public TerminalNode ISYMBOL() {
            return getToken(PatternParser.ISYMBOL, 0);
        }

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(PatternParser.FUNINFIXISYMBOL, 0);
        }

        public NotInfixTermContext notInfixTerm(int i) {
            return getRuleContext(NotInfixTermContext.class, i);
        }

        public List<NotInfixTermContext> notInfixTerm() {
            return getRuleContexts(NotInfixTermContext.class);
        }

        public InfixRelationSymbolContext(InfixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitInfixRelationSymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InfixSubformulaContext infixSubformula() throws RecognitionException {
        InfixSubformulaContext _localctx = new InfixSubformulaContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_infixSubformula);
        try {
            setState(254);
            switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
                case 1:
                    _localctx = new WithoutInfixRelationContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(242);
                        ((WithoutInfixRelationContext) _localctx).firstPrefix = notInfixTerm();
                        setState(243);
                        ((WithoutInfixRelationContext) _localctx).sym = match(RELINFIXISYMBOL);
                        setState(244);
                        ((WithoutInfixRelationContext) _localctx).secondPrefix = notInfixTerm();
                    }
                    break;
                case 2:
                    _localctx = new InfixRelationSymbolContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(246);
                        if (!(!properties.getInfixProperties().areInfixRelationsRestricted()))
                            throw new FailedPredicateException(
                                    this,
                                    "! properties.getInfixProperties().areInfixRelationsRestricted()");
                        setState(247);
                        ((InfixRelationSymbolContext) _localctx).firstPrefix = notInfixTerm();
                        setState(250);
                        switch (_input.LA(1)) {
                            case ISYMBOL:
                                {
                                    setState(248);
                                    ((InfixRelationSymbolContext) _localctx).sym = match(ISYMBOL);
                                }
                                break;
                            case FUNINFIXISYMBOL:
                                {
                                    setState(249);
                                    ((InfixRelationSymbolContext) _localctx).sym =
                                            match(FUNINFIXISYMBOL);
                                }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(252);
                        ((InfixRelationSymbolContext) _localctx).secondPrefix = notInfixTerm();
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

    public static class TermOrSContext extends ParserRuleContext {
        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TermsContext terms() {
            return getRuleContext(TermsContext.class, 0);
        }

        public TermOrSContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_termOrS;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitTermOrS(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TermOrSContext termOrS() throws RecognitionException {
        TermOrSContext _localctx = new TermOrSContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_termOrS);
        try {
            setState(258);
            switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(256);
                        term();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(257);
                        terms();
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
        public InfixTermContext infixTerm() {
            return getRuleContext(InfixTermContext.class, 0);
        }

        public AtomarTermContext atomarTerm() {
            return getRuleContext(AtomarTermContext.class, 0);
        }

        public NamedTermContext namedTerm() {
            return getRuleContext(NamedTermContext.class, 0);
        }

        public PrefixTermContext prefixTerm() {
            return getRuleContext(PrefixTermContext.class, 0);
        }

        public TermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_term;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TermContext term() throws RecognitionException {
        TermContext _localctx = new TermContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_term);
        try {
            setState(264);
            switch (getInterpreter().adaptivePredict(_input, 30, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(260);
                        namedTerm();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(261);
                        atomarTerm();
                    }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(262);
                        prefixTerm();
                    }
                    break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(263);
                        infixTerm();
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

    public static class NotInfixTermContext extends ParserRuleContext {
        public AtomarTermContext atomarTerm() {
            return getRuleContext(AtomarTermContext.class, 0);
        }

        public NamedTermContext namedTerm() {
            return getRuleContext(NamedTermContext.class, 0);
        }

        public PrefixTermContext prefixTerm() {
            return getRuleContext(PrefixTermContext.class, 0);
        }

        public NotInfixTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_notInfixTerm;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNotInfixTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NotInfixTermContext notInfixTerm() throws RecognitionException {
        NotInfixTermContext _localctx = new NotInfixTermContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_notInfixTerm);
        try {
            setState(269);
            switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(266);
                        namedTerm();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(267);
                        atomarTerm();
                    }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(268);
                        prefixTerm();
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

    public static class TermsContext extends ParserRuleContext {
        public TermsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_terms;
        }

        public TermsContext() {}

        public void copyFrom(TermsContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnyNameStarTermsContext extends TermsContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyNameStarTermsContext(TermsContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyNameStarTerms(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyStarTermsContext extends TermsContext {
        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyStarTermsContext(TermsContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyStarTerms(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NamedSubTermStarContext extends TermsContext {
        public NamedTermContext sub;

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public NamedTermContext namedTerm() {
            return getRuleContext(NamedTermContext.class, 0);
        }

        public NamedSubTermStarContext(TermsContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNamedSubTermStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class SubTermStarContext extends TermsContext {
        public AtomarTermContext sub;

        public TerminalNode AT() {
            return getToken(PatternParser.AT, 0);
        }

        public AtomarTermContext atomarTerm() {
            return getRuleContext(AtomarTermContext.class, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public SubTermStarContext(TermsContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitSubTermStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TermsContext terms() throws RecognitionException {
        TermsContext _localctx = new TermsContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_terms);
        try {
            setState(286);
            switch (getInterpreter().adaptivePredict(_input, 33, _ctx)) {
                case 1:
                    _localctx = new SubTermStarContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(275);
                        switch (getInterpreter().adaptivePredict(_input, 32, _ctx)) {
                            case 1:
                                {
                                }
                                break;
                            case 2:
                                {
                                    setState(272);
                                    name();
                                    setState(273);
                                    match(AT);
                                }
                                break;
                        }
                        setState(277);
                        ((SubTermStarContext) _localctx).sub = atomarTerm();
                        setState(278);
                        match(STAR);
                    }
                    break;
                case 2:
                    _localctx = new NamedSubTermStarContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(280);
                        ((NamedSubTermStarContext) _localctx).sub = namedTerm();
                        setState(281);
                        match(STAR);
                    }
                    break;
                case 3:
                    _localctx = new AnyStarTermsContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(283);
                        match(STAR);
                    }
                    break;
                case 4:
                    _localctx = new AnyNameStarTermsContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(284);
                        match(STAR);
                        setState(285);
                        name();
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

    public static class AtomarTermContext extends ParserRuleContext {
        public AtomarTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_atomarTerm;
        }

        public AtomarTermContext() {}

        public void copyFrom(AtomarTermContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnyFormulaAtomarTermContext extends AtomarTermContext {
        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaAtomarTermContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitAnyFormulaAtomarTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixFunctionSymbolBracketsContext extends AtomarTermContext {
        public TermOrSContext first;
        public TermOrSContext termOrS;
        public List<TermOrSContext> further = new ArrayList<TermOrSContext>();

        public TerminalNode OBRACKET() {
            return getToken(PatternParser.OBRACKET, 0);
        }

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(PatternParser.ARGUMENTSEP, i);
        }

        public TerminalNode CBRACKET() {
            return getToken(PatternParser.CBRACKET, 0);
        }

        public TermOrSContext termOrS(int i) {
            return getRuleContext(TermOrSContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(PatternParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public List<TermOrSContext> termOrS() {
            return getRuleContexts(TermOrSContext.class);
        }

        public PrefixFunctionSymbolBracketsContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixFunctionSymbolBrackets(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixFunctionSymbolContext extends AtomarTermContext {
        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public PrefixFunctionSymbolContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixFunctionSymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TermInBracketsContext extends AtomarTermContext {
        public TerminalNode OBRACKET() {
            return getToken(PatternParser.OBRACKET, 0);
        }

        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(PatternParser.CBRACKET, 0);
        }

        public TermInBracketsContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitTermInBrackets(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class TermInParenContext extends AtomarTermContext {
        public TermContext term() {
            return getRuleContext(TermContext.class, 0);
        }

        public TerminalNode OPAREN() {
            return getToken(PatternParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(PatternParser.CPAREN, 0);
        }

        public TermInParenContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitTermInParen(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class PrefixFunctionSymbolParenthesesContext extends AtomarTermContext {
        public TermOrSContext first;
        public TermOrSContext termOrS;
        public List<TermOrSContext> further = new ArrayList<TermOrSContext>();

        public TerminalNode ARGUMENTSEP(int i) {
            return getToken(PatternParser.ARGUMENTSEP, i);
        }

        public TerminalNode OPAREN() {
            return getToken(PatternParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(PatternParser.CPAREN, 0);
        }

        public TermOrSContext termOrS(int i) {
            return getRuleContext(TermOrSContext.class, i);
        }

        public List<TerminalNode> ARGUMENTSEP() {
            return getTokens(PatternParser.ARGUMENTSEP);
        }

        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public List<TermOrSContext> termOrS() {
            return getRuleContexts(TermOrSContext.class);
        }

        public PrefixFunctionSymbolParenthesesContext(AtomarTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitPrefixFunctionSymbolParentheses(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AtomarTermContext atomarTerm() throws RecognitionException {
        AtomarTermContext _localctx = new AtomarTermContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_atomarTerm);
        int _la;
        try {
            setState(326);
            switch (getInterpreter().adaptivePredict(_input, 38, _ctx)) {
                case 1:
                    _localctx = new PrefixFunctionSymbolContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(288);
                        iSymbol();
                    }
                    break;
                case 2:
                    _localctx = new PrefixFunctionSymbolBracketsContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(289);
                        iSymbol();
                        setState(290);
                        match(OBRACKET);
                        setState(299);
                        switch (getInterpreter().adaptivePredict(_input, 35, _ctx)) {
                            case 1:
                                {
                                    setState(291);
                                    ((PrefixFunctionSymbolBracketsContext) _localctx).first =
                                            termOrS();
                                    setState(296);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(292);
                                                match(ARGUMENTSEP);
                                                setState(293);
                                                ((PrefixFunctionSymbolBracketsContext) _localctx)
                                                                .termOrS =
                                                        termOrS();
                                                ((PrefixFunctionSymbolBracketsContext) _localctx)
                                                        .further.add(
                                                                ((PrefixFunctionSymbolBracketsContext)
                                                                                _localctx)
                                                                        .termOrS);
                                            }
                                        }
                                        setState(298);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(301);
                        match(CBRACKET);
                    }
                    break;
                case 3:
                    _localctx = new PrefixFunctionSymbolParenthesesContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(303);
                        iSymbol();
                        setState(304);
                        match(OPAREN);
                        setState(313);
                        switch (getInterpreter().adaptivePredict(_input, 37, _ctx)) {
                            case 1:
                                {
                                    setState(305);
                                    ((PrefixFunctionSymbolParenthesesContext) _localctx).first =
                                            termOrS();
                                    setState(310);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                    while (_la == ARGUMENTSEP) {
                                        {
                                            {
                                                setState(306);
                                                match(ARGUMENTSEP);
                                                setState(307);
                                                ((PrefixFunctionSymbolParenthesesContext) _localctx)
                                                                .termOrS =
                                                        termOrS();
                                                ((PrefixFunctionSymbolParenthesesContext) _localctx)
                                                        .further.add(
                                                                ((PrefixFunctionSymbolParenthesesContext)
                                                                                _localctx)
                                                                        .termOrS);
                                            }
                                        }
                                        setState(312);
                                        _errHandler.sync(this);
                                        _la = _input.LA(1);
                                    }
                                }
                                break;
                        }
                        setState(315);
                        match(CPAREN);
                    }
                    break;
                case 4:
                    _localctx = new TermInBracketsContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(317);
                        match(OBRACKET);
                        setState(318);
                        term();
                        setState(319);
                        match(CBRACKET);
                    }
                    break;
                case 5:
                    _localctx = new TermInParenContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(321);
                        match(OPAREN);
                        setState(322);
                        term();
                        setState(323);
                        match(CPAREN);
                    }
                    break;
                case 6:
                    _localctx = new AnyFormulaAtomarTermContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(325);
                        match(ANYFORMULA);
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
        public AtomarTermContext subNI;

        public TerminalNode AT() {
            return getToken(PatternParser.AT, 0);
        }

        public AtomarTermContext atomarTerm() {
            return getRuleContext(AtomarTermContext.class, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public PrefixTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prefixTerm;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitPrefixTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final PrefixTermContext prefixTerm() throws RecognitionException {
        PrefixTermContext _localctx = new PrefixTermContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_prefixTerm);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(328);
                name();
                setState(329);
                match(AT);
                setState(330);
                ((PrefixTermContext) _localctx).subNI = atomarTerm();
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

    public static class NamedTermContext extends ParserRuleContext {
        public NamedTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_namedTerm;
        }

        public NamedTermContext() {}

        public void copyFrom(NamedTermContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class AnyFormulaWithNameAtomarTermContext extends NamedTermContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaWithNameAtomarTermContext(NamedTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitAnyFormulaWithNameAtomarTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ReadNameAtomarTermContext extends NamedTermContext {
        public TerminalNode READ() {
            return getToken(PatternParser.READ, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public ReadNameAtomarTermContext(NamedTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitReadNameAtomarTerm(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NamedTermContext namedTerm() throws RecognitionException {
        NamedTermContext _localctx = new NamedTermContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_namedTerm);
        try {
            setState(336);
            switch (_input.LA(1)) {
                case ANYFORMULA:
                    _localctx = new AnyFormulaWithNameAtomarTermContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(332);
                        match(ANYFORMULA);
                        setState(333);
                        name();
                    }
                    break;
                case READ:
                    _localctx = new ReadNameAtomarTermContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(334);
                        match(READ);
                        setState(335);
                        name();
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

    public static class InfixTermContext extends ParserRuleContext {
        public InfixTermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_infixTerm;
        }

        public InfixTermContext() {}

        public void copyFrom(InfixTermContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class WithoutInfixFunctionContext extends InfixTermContext {
        public NotInfixTermContext first;
        public Token sym;
        public NotInfixTermContext second;

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(PatternParser.FUNINFIXISYMBOL, 0);
        }

        public NotInfixTermContext notInfixTerm(int i) {
            return getRuleContext(NotInfixTermContext.class, i);
        }

        public List<NotInfixTermContext> notInfixTerm() {
            return getRuleContexts(NotInfixTermContext.class);
        }

        public WithoutInfixFunctionContext(InfixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor)
                        .visitWithoutInfixFunction(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class InfixFunctionSymbolContext extends InfixTermContext {
        public NotInfixTermContext first;
        public Token sym;
        public NotInfixTermContext second;

        public TerminalNode ISYMBOL() {
            return getToken(PatternParser.ISYMBOL, 0);
        }

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(PatternParser.RELINFIXISYMBOL, 0);
        }

        public NotInfixTermContext notInfixTerm(int i) {
            return getRuleContext(NotInfixTermContext.class, i);
        }

        public List<NotInfixTermContext> notInfixTerm() {
            return getRuleContexts(NotInfixTermContext.class);
        }

        public InfixFunctionSymbolContext(InfixTermContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitInfixFunctionSymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InfixTermContext infixTerm() throws RecognitionException {
        InfixTermContext _localctx = new InfixTermContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_infixTerm);
        try {
            setState(350);
            switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
                case 1:
                    _localctx = new WithoutInfixFunctionContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(338);
                        ((WithoutInfixFunctionContext) _localctx).first = notInfixTerm();
                        setState(339);
                        ((WithoutInfixFunctionContext) _localctx).sym = match(FUNINFIXISYMBOL);
                        setState(340);
                        ((WithoutInfixFunctionContext) _localctx).second = notInfixTerm();
                    }
                    break;
                case 2:
                    _localctx = new InfixFunctionSymbolContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(342);
                        if (!(!properties.getInfixProperties().areInfixFunctionsRestricted()))
                            throw new FailedPredicateException(
                                    this,
                                    "! properties.getInfixProperties().areInfixFunctionsRestricted()");
                        setState(343);
                        ((InfixFunctionSymbolContext) _localctx).first = notInfixTerm();
                        setState(346);
                        switch (_input.LA(1)) {
                            case ISYMBOL:
                                {
                                    setState(344);
                                    ((InfixFunctionSymbolContext) _localctx).sym = match(ISYMBOL);
                                }
                                break;
                            case RELINFIXISYMBOL:
                                {
                                    setState(345);
                                    ((InfixFunctionSymbolContext) _localctx).sym =
                                            match(RELINFIXISYMBOL);
                                }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                        setState(348);
                        ((InfixFunctionSymbolContext) _localctx).second = notInfixTerm();
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
        public ISymbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_iSymbol;
        }

        public ISymbolContext() {}

        public void copyFrom(ISymbolContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FixedNameContext extends ISymbolContext {
        public TerminalNode SQUOTE(int i) {
            return getToken(PatternParser.SQUOTE, i);
        }

        public List<TerminalNode> SQUOTE() {
            return getTokens(PatternParser.SQUOTE);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public FixedNameContext(ISymbolContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitFixedName(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyNameContext extends ISymbolContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public AnyNameContext(ISymbolContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyName(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ISymbolContext iSymbol() throws RecognitionException {
        ISymbolContext _localctx = new ISymbolContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_iSymbol);
        try {
            setState(357);
            switch (_input.LA(1)) {
                case SQUOTE:
                    _localctx = new FixedNameContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(352);
                        match(SQUOTE);
                        setState(353);
                        name();
                        setState(354);
                        match(SQUOTE);
                    }
                    break;
                case ISYMBOL:
                case RELINFIXISYMBOL:
                case FUNINFIXISYMBOL:
                    _localctx = new AnyNameContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(356);
                        name();
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

    public static class NameContext extends ParserRuleContext {
        public Token sym;

        public TerminalNode ISYMBOL() {
            return getToken(PatternParser.ISYMBOL, 0);
        }

        public TerminalNode RELINFIXISYMBOL() {
            return getToken(PatternParser.RELINFIXISYMBOL, 0);
        }

        public TerminalNode DISTINCT(int i) {
            return getToken(PatternParser.DISTINCT, i);
        }

        public TerminalNode FUNINFIXISYMBOL() {
            return getToken(PatternParser.FUNINFIXISYMBOL, 0);
        }

        public List<TerminalNode> DISTINCT() {
            return getTokens(PatternParser.DISTINCT);
        }

        public NameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_name;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitName(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NameContext name() throws RecognitionException {
        NameContext _localctx = new NameContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_name);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(362);
                switch (_input.LA(1)) {
                    case ISYMBOL:
                        {
                            setState(359);
                            ((NameContext) _localctx).sym = match(ISYMBOL);
                        }
                        break;
                    case RELINFIXISYMBOL:
                        {
                            setState(360);
                            ((NameContext) _localctx).sym = match(RELINFIXISYMBOL);
                        }
                        break;
                    case FUNINFIXISYMBOL:
                        {
                            setState(361);
                            ((NameContext) _localctx).sym = match(FUNINFIXISYMBOL);
                        }
                        break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(367);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 44, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(364);
                                match(DISTINCT);
                            }
                        }
                    }
                    setState(369);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 44, _ctx);
                }
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
            case 13:
                return infixSubformula_sempred((InfixSubformulaContext) _localctx, predIndex);
            case 21:
                return infixTerm_sempred((InfixTermContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean infixTerm_sempred(InfixTermContext _localctx, int predIndex) {
        switch (predIndex) {
            case 1:
                return !properties.getInfixProperties().areInfixFunctionsRestricted();
        }
        return true;
    }

    private boolean infixSubformula_sempred(InfixSubformulaContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return !properties.getInfixProperties().areInfixRelationsRestricted();
        }
        return true;
    }

    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3#\u0175\4\2\t\2\4"
                    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
                    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
                    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
                    + "\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\5\4;\n\4\3\5\3\5\3\5\6\5@\n\5\r\5\16"
                    + "\5A\3\5\3\5\3\5\6\5G\n\5\r\5\16\5H\3\5\3\5\3\5\3\5\5\5O\n\5\6\5Q\n\5\r"
                    + "\5\16\5R\3\5\3\5\6\5W\n\5\r\5\16\5X\3\5\3\5\3\5\3\5\5\5_\n\5\7\5a\n\5"
                    + "\f\5\16\5d\13\5\3\5\3\5\3\5\3\5\5\5j\n\5\6\5l\n\5\r\5\16\5m\3\5\3\5\6"
                    + "\5r\n\5\r\5\16\5s\3\5\3\5\3\5\3\5\5\5z\n\5\7\5|\n\5\f\5\16\5\177\13\5"
                    + "\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0089\n\5\3\6\3\6\5\6\u008d\n\6\3"
                    + "\7\3\7\3\7\3\7\3\7\5\7\u0094\n\7\3\b\3\b\3\b\3\b\5\b\u009a\n\b\3\t\3\t"
                    + "\3\t\3\t\5\t\u00a0\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00ab\n"
                    + "\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n"
                    + "\u00bd\n\n\f\n\16\n\u00c0\13\n\5\n\u00c2\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3"
                    + "\n\7\n\u00cb\n\n\f\n\16\n\u00ce\13\n\5\n\u00d0\n\n\3\n\3\n\3\n\5\n\u00d5"
                    + "\n\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\5\f\u00df\n\f\3\r\3\r\5\r\u00e3"
                    + "\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"
                    + "\3\16\5\16\u00f3\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00fd"
                    + "\n\17\3\17\3\17\5\17\u0101\n\17\3\20\3\20\5\20\u0105\n\20\3\21\3\21\3"
                    + "\21\3\21\5\21\u010b\n\21\3\22\3\22\3\22\5\22\u0110\n\22\3\23\3\23\3\23"
                    + "\3\23\5\23\u0116\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"
                    + "\u0121\n\23\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u0129\n\24\f\24\16\24\u012c"
                    + "\13\24\5\24\u012e\n\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u0137\n"
                    + "\24\f\24\16\24\u013a\13\24\5\24\u013c\n\24\3\24\3\24\3\24\3\24\3\24\3"
                    + "\24\3\24\3\24\3\24\3\24\3\24\5\24\u0149\n\24\3\25\3\25\3\25\3\25\3\26"
                    + "\3\26\3\26\3\26\5\26\u0153\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"
                    + "\5\27\u015d\n\27\3\27\3\27\5\27\u0161\n\27\3\30\3\30\3\30\3\30\3\30\5"
                    + "\30\u0168\n\30\3\31\3\31\3\31\5\31\u016d\n\31\3\31\7\31\u0170\n\31\f\31"
                    + "\16\31\u0173\13\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"
                    + " \"$&(*,.\60\2\2\u01a9\2\62\3\2\2\2\4\65\3\2\2\2\6:\3\2\2\2\b\u0088\3"
                    + "\2\2\2\n\u008c\3\2\2\2\f\u0093\3\2\2\2\16\u0099\3\2\2\2\20\u00aa\3\2\2"
                    + "\2\22\u00d4\3\2\2\2\24\u00d6\3\2\2\2\26\u00de\3\2\2\2\30\u00e2\3\2\2\2"
                    + "\32\u00f2\3\2\2\2\34\u0100\3\2\2\2\36\u0104\3\2\2\2 \u010a\3\2\2\2\"\u010f"
                    + "\3\2\2\2$\u0120\3\2\2\2&\u0148\3\2\2\2(\u014a\3\2\2\2*\u0152\3\2\2\2,"
                    + "\u0160\3\2\2\2.\u0167\3\2\2\2\60\u016c\3\2\2\2\62\63\5\6\4\2\63\64\7\2"
                    + "\2\3\64\3\3\2\2\2\65\66\5 \21\2\66\67\7\2\2\3\67\5\3\2\2\28;\5\b\5\29"
                    + ";\5\f\7\2:8\3\2\2\2:9\3\2\2\2;\7\3\2\2\2<?\5\f\7\2=>\7\7\2\2>@\5\f\7\2"
                    + "?=\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\u0089\3\2\2\2CF\5\f\7\2DE\7"
                    + "!\2\2EG\5\f\7\2FD\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2I\u0089\3\2\2\2"
                    + "JP\5\n\6\2KN\7\f\2\2LO\5\n\6\2MO\7\4\2\2NL\3\2\2\2NM\3\2\2\2OQ\3\2\2\2"
                    + "PK\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2S\u0089\3\2\2\2TU\7\4\2\2UW\7"
                    + "\f\2\2VT\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Zb\5\n\6\2[^\7"
                    + "\f\2\2\\_\5\n\6\2]_\7\4\2\2^\\\3\2\2\2^]\3\2\2\2_a\3\2\2\2`[\3\2\2\2a"
                    + "d\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\u0089\3\2\2\2db\3\2\2\2ek\5\n\6\2fi\7\r"
                    + "\2\2gj\5\n\6\2hj\7\4\2\2ig\3\2\2\2ih\3\2\2\2jl\3\2\2\2kf\3\2\2\2lm\3\2"
                    + "\2\2mk\3\2\2\2mn\3\2\2\2n\u0089\3\2\2\2op\7\4\2\2pr\7\r\2\2qo\3\2\2\2"
                    + "rs\3\2\2\2sq\3\2\2\2st\3\2\2\2tu\3\2\2\2u}\5\n\6\2vy\7\r\2\2wz\5\n\6\2"
                    + "xz\7\4\2\2yw\3\2\2\2yx\3\2\2\2z|\3\2\2\2{v\3\2\2\2|\177\3\2\2\2}{\3\2"
                    + "\2\2}~\3\2\2\2~\u0089\3\2\2\2\177}\3\2\2\2\u0080\u0081\5\f\7\2\u0081\u0082"
                    + "\7\17\2\2\u0082\u0083\5\f\7\2\u0083\u0089\3\2\2\2\u0084\u0085\5\f\7\2"
                    + "\u0085\u0086\7\16\2\2\u0086\u0087\5\f\7\2\u0087\u0089\3\2\2\2\u0088<\3"
                    + "\2\2\2\u0088C\3\2\2\2\u0088J\3\2\2\2\u0088V\3\2\2\2\u0088e\3\2\2\2\u0088"
                    + "q\3\2\2\2\u0088\u0080\3\2\2\2\u0088\u0084\3\2\2\2\u0089\t\3\2\2\2\u008a"
                    + "\u008d\5\f\7\2\u008b\u008d\5\20\t\2\u008c\u008a\3\2\2\2\u008c\u008b\3"
                    + "\2\2\2\u008d\13\3\2\2\2\u008e\u0094\5\24\13\2\u008f\u0094\5\26\f\2\u0090"
                    + "\u0094\5\22\n\2\u0091\u0094\5\32\16\2\u0092\u0094\5\34\17\2\u0093\u008e"
                    + "\3\2\2\2\u0093\u008f\3\2\2\2\u0093\u0090\3\2\2\2\u0093\u0091\3\2\2\2\u0093"
                    + "\u0092\3\2\2\2\u0094\r\3\2\2\2\u0095\u009a\5\24\13\2\u0096\u009a\5\26"
                    + "\f\2\u0097\u009a\5\22\n\2\u0098\u009a\5\32\16\2\u0099\u0095\3\2\2\2\u0099"
                    + "\u0096\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u0098\3\2\2\2\u009a\17\3\2\2"
                    + "\2\u009b\u00a0\3\2\2\2\u009c\u009d\5\60\31\2\u009d\u009e\7\t\2\2\u009e"
                    + "\u00a0\3\2\2\2\u009f\u009b\3\2\2\2\u009f\u009c\3\2\2\2\u00a0\u00a1\3\2"
                    + "\2\2\u00a1\u00a2\5\22\n\2\u00a2\u00a3\7\b\2\2\u00a3\u00ab\3\2\2\2\u00a4"
                    + "\u00a5\5\26\f\2\u00a5\u00a6\7\b\2\2\u00a6\u00ab\3\2\2\2\u00a7\u00ab\7"
                    + "\b\2\2\u00a8\u00a9\7\b\2\2\u00a9\u00ab\5\60\31\2\u00aa\u009f\3\2\2\2\u00aa"
                    + "\u00a4\3\2\2\2\u00aa\u00a7\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\21\3\2\2"
                    + "\2\u00ac\u00d5\7\22\2\2\u00ad\u00d5\7\23\2\2\u00ae\u00af\7\24\2\2\u00af"
                    + "\u00b0\5\6\4\2\u00b0\u00b1\7\25\2\2\u00b1\u00d5\3\2\2\2\u00b2\u00b3\7"
                    + "\26\2\2\u00b3\u00b4\5\6\4\2\u00b4\u00b5\7\27\2\2\u00b5\u00d5\3\2\2\2\u00b6"
                    + "\u00d5\5.\30\2\u00b7\u00b8\5.\30\2\u00b8\u00c1\7\24\2\2\u00b9\u00be\5"
                    + "\36\20\2\u00ba\u00bb\7\30\2\2\u00bb\u00bd\5\36\20\2\u00bc\u00ba\3\2\2"
                    + "\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c2"
                    + "\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00b9\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"
                    + "\u00c3\3\2\2\2\u00c3\u00c4\7\25\2\2\u00c4\u00d5\3\2\2\2\u00c5\u00c6\5"
                    + ".\30\2\u00c6\u00cf\7\26\2\2\u00c7\u00cc\5\36\20\2\u00c8\u00c9\7\30\2\2"
                    + "\u00c9\u00cb\5\36\20\2\u00ca\u00c8\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca"
                    + "\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf"
                    + "\u00c7\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\7\27"
                    + "\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d5\7\5\2\2\u00d4\u00ac\3\2\2\2\u00d4"
                    + "\u00ad\3\2\2\2\u00d4\u00ae\3\2\2\2\u00d4\u00b2\3\2\2\2\u00d4\u00b6\3\2"
                    + "\2\2\u00d4\u00b7\3\2\2\2\u00d4\u00c5\3\2\2\2\u00d4\u00d3\3\2\2\2\u00d5"
                    + "\23\3\2\2\2\u00d6\u00d7\5\60\31\2\u00d7\u00d8\7\t\2\2\u00d8\u00d9\5\30"
                    + "\r\2\u00d9\25\3\2\2\2\u00da\u00db\7\5\2\2\u00db\u00df\5\60\31\2\u00dc"
                    + "\u00dd\7\6\2\2\u00dd\u00df\5\60\31\2\u00de\u00da\3\2\2\2\u00de\u00dc\3"
                    + "\2\2\2\u00df\27\3\2\2\2\u00e0\u00e3\5\22\n\2\u00e1\u00e3\5\32\16\2\u00e2"
                    + "\u00e0\3\2\2\2\u00e2\u00e1\3\2\2\2\u00e3\31\3\2\2\2\u00e4\u00e5\7\13\2"
                    + "\2\u00e5\u00f3\5\f\7\2\u00e6\u00e7\7\20\2\2\u00e7\u00e8\5.\30\2\u00e8"
                    + "\u00e9\5\f\7\2\u00e9\u00f3\3\2\2\2\u00ea\u00eb\7\21\2\2\u00eb\u00ec\5"
                    + ".\30\2\u00ec\u00ed\5\f\7\2\u00ed\u00f3\3\2\2\2\u00ee\u00ef\7\3\2\2\u00ef"
                    + "\u00f3\5\f\7\2\u00f0\u00f1\7\"\2\2\u00f1\u00f3\5\f\7\2\u00f2\u00e4\3\2"
                    + "\2\2\u00f2\u00e6\3\2\2\2\u00f2\u00ea\3\2\2\2\u00f2\u00ee\3\2\2\2\u00f2"
                    + "\u00f0\3\2\2\2\u00f3\33\3\2\2\2\u00f4\u00f5\5\"\22\2\u00f5\u00f6\7\34"
                    + "\2\2\u00f6\u00f7\5\"\22\2\u00f7\u0101\3\2\2\2\u00f8\u00f9\6\17\2\2\u00f9"
                    + "\u00fc\5\"\22\2\u00fa\u00fd\7\31\2\2\u00fb\u00fd\7\35\2\2\u00fc\u00fa"
                    + "\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\5\"\22\2"
                    + "\u00ff\u0101\3\2\2\2\u0100\u00f4\3\2\2\2\u0100\u00f8\3\2\2\2\u0101\35"
                    + "\3\2\2\2\u0102\u0105\5 \21\2\u0103\u0105\5$\23\2\u0104\u0102\3\2\2\2\u0104"
                    + "\u0103\3\2\2\2\u0105\37\3\2\2\2\u0106\u010b\5*\26\2\u0107\u010b\5&\24"
                    + "\2\u0108\u010b\5(\25\2\u0109\u010b\5,\27\2\u010a\u0106\3\2\2\2\u010a\u0107"
                    + "\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u0109\3\2\2\2\u010b!\3\2\2\2\u010c"
                    + "\u0110\5*\26\2\u010d\u0110\5&\24\2\u010e\u0110\5(\25\2\u010f\u010c\3\2"
                    + "\2\2\u010f\u010d\3\2\2\2\u010f\u010e\3\2\2\2\u0110#\3\2\2\2\u0111\u0116"
                    + "\3\2\2\2\u0112\u0113\5\60\31\2\u0113\u0114\7\t\2\2\u0114\u0116\3\2\2\2"
                    + "\u0115\u0111\3\2\2\2\u0115\u0112\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118"
                    + "\5&\24\2\u0118\u0119\7\b\2\2\u0119\u0121\3\2\2\2\u011a\u011b\5*\26\2\u011b"
                    + "\u011c\7\b\2\2\u011c\u0121\3\2\2\2\u011d\u0121\7\b\2\2\u011e\u011f\7\b"
                    + "\2\2\u011f\u0121\5\60\31\2\u0120\u0115\3\2\2\2\u0120\u011a\3\2\2\2\u0120"
                    + "\u011d\3\2\2\2\u0120\u011e\3\2\2\2\u0121%\3\2\2\2\u0122\u0149\5.\30\2"
                    + "\u0123\u0124\5.\30\2\u0124\u012d\7\24\2\2\u0125\u012a\5\36\20\2\u0126"
                    + "\u0127\7\30\2\2\u0127\u0129\5\36\20\2\u0128\u0126\3\2\2\2\u0129\u012c"
                    + "\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012e\3\2\2\2\u012c"
                    + "\u012a\3\2\2\2\u012d\u0125\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012f\3\2"
                    + "\2\2\u012f\u0130\7\25\2\2\u0130\u0149\3\2\2\2\u0131\u0132\5.\30\2\u0132"
                    + "\u013b\7\26\2\2\u0133\u0138\5\36\20\2\u0134\u0135\7\30\2\2\u0135\u0137"
                    + "\5\36\20\2\u0136\u0134\3\2\2\2\u0137\u013a\3\2\2\2\u0138\u0136\3\2\2\2"
                    + "\u0138\u0139\3\2\2\2\u0139\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013b\u0133"
                    + "\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u013e\7\27\2\2"
                    + "\u013e\u0149\3\2\2\2\u013f\u0140\7\24\2\2\u0140\u0141\5 \21\2\u0141\u0142"
                    + "\7\25\2\2\u0142\u0149\3\2\2\2\u0143\u0144\7\26\2\2\u0144\u0145\5 \21\2"
                    + "\u0145\u0146\7\27\2\2\u0146\u0149\3\2\2\2\u0147\u0149\7\5\2\2\u0148\u0122"
                    + "\3\2\2\2\u0148\u0123\3\2\2\2\u0148\u0131\3\2\2\2\u0148\u013f\3\2\2\2\u0148"
                    + "\u0143\3\2\2\2\u0148\u0147\3\2\2\2\u0149\'\3\2\2\2\u014a\u014b\5\60\31"
                    + "\2\u014b\u014c\7\t\2\2\u014c\u014d\5&\24\2\u014d)\3\2\2\2\u014e\u014f"
                    + "\7\5\2\2\u014f\u0153\5\60\31\2\u0150\u0151\7\6\2\2\u0151\u0153\5\60\31"
                    + "\2\u0152\u014e\3\2\2\2\u0152\u0150\3\2\2\2\u0153+\3\2\2\2\u0154\u0155"
                    + "\5\"\22\2\u0155\u0156\7\35\2\2\u0156\u0157\5\"\22\2\u0157\u0161\3\2\2"
                    + "\2\u0158\u0159\6\27\3\2\u0159\u015c\5\"\22\2\u015a\u015d\7\31\2\2\u015b"
                    + "\u015d\7\34\2\2\u015c\u015a\3\2\2\2\u015c\u015b\3\2\2\2\u015d\u015e\3"
                    + "\2\2\2\u015e\u015f\5\"\22\2\u015f\u0161\3\2\2\2\u0160\u0154\3\2\2\2\u0160"
                    + "\u0158\3\2\2\2\u0161-\3\2\2\2\u0162\u0163\7\n\2\2\u0163\u0164\5\60\31"
                    + "\2\u0164\u0165\7\n\2\2\u0165\u0168\3\2\2\2\u0166\u0168\5\60\31\2\u0167"
                    + "\u0162\3\2\2\2\u0167\u0166\3\2\2\2\u0168/\3\2\2\2\u0169\u016d\7\31\2\2"
                    + "\u016a\u016d\7\34\2\2\u016b\u016d\7\35\2\2\u016c\u0169\3\2\2\2\u016c\u016a"
                    + "\3\2\2\2\u016c\u016b\3\2\2\2\u016d\u0171\3\2\2\2\u016e\u0170\7#\2\2\u016f"
                    + "\u016e\3\2\2\2\u0170\u0173\3\2\2\2\u0171\u016f\3\2\2\2\u0171\u0172\3\2"
                    + "\2\2\u0172\61\3\2\2\2\u0173\u0171\3\2\2\2/:AHNRX^bimsy}\u0088\u008c\u0093"
                    + "\u0099\u009f\u00aa\u00be\u00c1\u00cc\u00cf\u00d4\u00de\u00e2\u00f2\u00fc"
                    + "\u0100\u0104\u010a\u010f\u0115\u0120\u012a\u012d\u0138\u013b\u0148\u0152"
                    + "\u015c\u0160\u0167\u016c\u0171";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
