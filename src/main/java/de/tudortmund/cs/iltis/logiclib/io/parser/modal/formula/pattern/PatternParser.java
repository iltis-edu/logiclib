// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/modal/formula/pattern/PatternParser.g4
// by ANTLR 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.modal.formula.pattern;

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
            BOX = 14,
            DIAMOND = 15,
            TRUE = 16,
            FALSE = 17,
            OBRACKET = 18,
            CBRACKET = 19,
            OPAREN = 20,
            CPAREN = 21,
            ISYMBOL = 22,
            OBRACE = 23,
            CBRACE = 24,
            REVERSE_IMPLIES = 25,
            WS = 26,
            OPERATOR_OR_ISYMBOL = 27,
            MULTI = 28,
            CONTAINS = 29,
            DISTINCT = 30;
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
        "BOX",
        "DIAMOND",
        "TRUE",
        "FALSE",
        "OBRACKET",
        "CBRACKET",
        "OPAREN",
        "CPAREN",
        "ISYMBOL",
        "OBRACE",
        "CBRACE",
        "REVERSE_IMPLIES",
        "WS",
        "OPERATOR_OR_ISYMBOL",
        "MULTI",
        "CONTAINS",
        "DISTINCT"
    };
    public static final int RULE_initFormula = 0,
            RULE_formula = 1,
            RULE_superformula = 2,
            RULE_subformulaOrS = 3,
            RULE_subformula = 4,
            RULE_subformulae = 5,
            RULE_atomarSubformula = 6,
            RULE_namedFormula = 7,
            RULE_namedSubformula = 8,
            RULE_subformulaWithName = 9,
            RULE_prefixSubformula = 10,
            RULE_iSymbol = 11,
            RULE_name = 12;
    public static final String[] ruleNames = {
        "initFormula",
        "formula",
        "superformula",
        "subformulaOrS",
        "subformula",
        "subformulae",
        "atomarSubformula",
        "namedFormula",
        "namedSubformula",
        "subformulaWithName",
        "prefixSubformula",
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
                setState(26);
                formula();
                setState(27);
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
        enterRule(_localctx, 2, RULE_formula);
        try {
            setState(31);
            switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(29);
                        superformula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(30);
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
        enterRule(_localctx, 4, RULE_superformula);
        int _la;
        try {
            setState(109);
            switch (getInterpreter().adaptivePredict(_input, 13, _ctx)) {
                case 1:
                    _localctx = new AlternativeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(33);
                        ((AlternativeContext) _localctx).first = subformula();
                        setState(36);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(34);
                                    match(PLUS);
                                    setState(35);
                                    ((AlternativeContext) _localctx).subformula = subformula();
                                    ((AlternativeContext) _localctx)
                                            .further.add(
                                                    ((AlternativeContext) _localctx).subformula);
                                }
                            }
                            setState(38);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == PLUS);
                    }
                    break;
                case 2:
                    _localctx = new MultiConstraintContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(40);
                        ((MultiConstraintContext) _localctx).first = subformula();
                        setState(43);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(41);
                                    match(MULTI);
                                    setState(42);
                                    ((MultiConstraintContext) _localctx).subformula = subformula();
                                    ((MultiConstraintContext) _localctx)
                                            .further.add(
                                                    ((MultiConstraintContext) _localctx)
                                                            .subformula);
                                }
                            }
                            setState(45);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == MULTI);
                    }
                    break;
                case 3:
                    _localctx = new AndLeadingSubformulaContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(47);
                        ((AndLeadingSubformulaContext) _localctx).firsts = subformulaOrS();
                        setState(53);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(48);
                                    match(AND);
                                    setState(51);
                                    switch (_input.LA(1)) {
                                        case COMPLEMENT:
                                        case ANYFORMULA:
                                        case READ:
                                        case STAR:
                                        case SQUOTE:
                                        case NEG:
                                        case BOX:
                                        case DIAMOND:
                                        case TRUE:
                                        case FALSE:
                                        case OBRACKET:
                                        case OPAREN:
                                        case ISYMBOL:
                                        case CONTAINS:
                                            {
                                                setState(49);
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
                                        case NOFORMULA:
                                            {
                                                setState(50);
                                                match(NOFORMULA);
                                            }
                                            break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                }
                            }
                            setState(55);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == AND);
                    }
                    break;
                case 4:
                    _localctx = new AndLeadingNoFormulaContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(59);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(57);
                                    match(NOFORMULA);
                                    setState(58);
                                    match(AND);
                                }
                            }
                            setState(61);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == NOFORMULA);
                        setState(63);
                        ((AndLeadingNoFormulaContext) _localctx).firsts = subformulaOrS();
                        setState(71);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == AND) {
                            {
                                {
                                    setState(64);
                                    match(AND);
                                    setState(67);
                                    switch (_input.LA(1)) {
                                        case COMPLEMENT:
                                        case ANYFORMULA:
                                        case READ:
                                        case STAR:
                                        case SQUOTE:
                                        case NEG:
                                        case BOX:
                                        case DIAMOND:
                                        case TRUE:
                                        case FALSE:
                                        case OBRACKET:
                                        case OPAREN:
                                        case ISYMBOL:
                                        case CONTAINS:
                                            {
                                                setState(65);
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
                                        case NOFORMULA:
                                            {
                                                setState(66);
                                                match(NOFORMULA);
                                            }
                                            break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                }
                            }
                            setState(73);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                    break;
                case 5:
                    _localctx = new OrLeadingSubformulaContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(74);
                        ((OrLeadingSubformulaContext) _localctx).firsts = subformulaOrS();
                        setState(80);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(75);
                                    match(OR);
                                    setState(78);
                                    switch (_input.LA(1)) {
                                        case COMPLEMENT:
                                        case ANYFORMULA:
                                        case READ:
                                        case STAR:
                                        case SQUOTE:
                                        case NEG:
                                        case BOX:
                                        case DIAMOND:
                                        case TRUE:
                                        case FALSE:
                                        case OBRACKET:
                                        case OPAREN:
                                        case ISYMBOL:
                                        case CONTAINS:
                                            {
                                                setState(76);
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
                                        case NOFORMULA:
                                            {
                                                setState(77);
                                                match(NOFORMULA);
                                            }
                                            break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                }
                            }
                            setState(82);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == OR);
                    }
                    break;
                case 6:
                    _localctx = new OrLeadingNoFormulaContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                    {
                        setState(86);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        do {
                            {
                                {
                                    setState(84);
                                    match(NOFORMULA);
                                    setState(85);
                                    match(OR);
                                }
                            }
                            setState(88);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        } while (_la == NOFORMULA);
                        setState(90);
                        ((OrLeadingNoFormulaContext) _localctx).firsts = subformulaOrS();
                        setState(98);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == OR) {
                            {
                                {
                                    setState(91);
                                    match(OR);
                                    setState(94);
                                    switch (_input.LA(1)) {
                                        case COMPLEMENT:
                                        case ANYFORMULA:
                                        case READ:
                                        case STAR:
                                        case SQUOTE:
                                        case NEG:
                                        case BOX:
                                        case DIAMOND:
                                        case TRUE:
                                        case FALSE:
                                        case OBRACKET:
                                        case OPAREN:
                                        case ISYMBOL:
                                        case CONTAINS:
                                            {
                                                setState(92);
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
                                        case NOFORMULA:
                                            {
                                                setState(93);
                                                match(NOFORMULA);
                                            }
                                            break;
                                        default:
                                            throw new NoViableAltException(this);
                                    }
                                }
                            }
                            setState(100);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                    break;
                case 7:
                    _localctx = new ImpliesContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                    {
                        setState(101);
                        ((ImpliesContext) _localctx).first = subformula();
                        setState(102);
                        match(IMPLIES);
                        setState(103);
                        ((ImpliesContext) _localctx).second = subformula();
                    }
                    break;
                case 8:
                    _localctx = new EquivContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                    {
                        setState(105);
                        ((EquivContext) _localctx).first = subformula();
                        setState(106);
                        match(EQUIV);
                        setState(107);
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
        enterRule(_localctx, 6, RULE_subformulaOrS);
        try {
            setState(113);
            switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(111);
                        subformula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(112);
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
        enterRule(_localctx, 8, RULE_subformula);
        try {
            setState(119);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(115);
                        namedFormula();
                    }
                    break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(116);
                        namedSubformula();
                    }
                    break;
                case 3:
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(117);
                        atomarSubformula();
                    }
                    break;
                case 4:
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(118);
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

    public static class AnyStarContext extends SubformulaeContext {
        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyStarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class StarContext extends SubformulaeContext {
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

        public StarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class NamedStarContext extends SubformulaeContext {
        public NamedSubformulaContext sub;

        public NamedSubformulaContext namedSubformula() {
            return getRuleContext(NamedSubformulaContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public NamedStarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitNamedStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyNameStarContext extends SubformulaeContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode STAR() {
            return getToken(PatternParser.STAR, 0);
        }

        public AnyNameStarContext(SubformulaeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyNameStar(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SubformulaeContext subformulae() throws RecognitionException {
        SubformulaeContext _localctx = new SubformulaeContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_subformulae);
        try {
            setState(136);
            switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
                case 1:
                    _localctx = new StarContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(125);
                        switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                            case 1:
                                {
                                }
                                break;
                            case 2:
                                {
                                    setState(122);
                                    name();
                                    setState(123);
                                    match(AT);
                                }
                                break;
                        }
                        setState(127);
                        ((StarContext) _localctx).sub = atomarSubformula();
                        setState(128);
                        match(STAR);
                    }
                    break;
                case 2:
                    _localctx = new NamedStarContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(130);
                        ((NamedStarContext) _localctx).sub = namedSubformula();
                        setState(131);
                        match(STAR);
                    }
                    break;
                case 3:
                    _localctx = new AnyStarContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(133);
                        match(STAR);
                    }
                    break;
                case 4:
                    _localctx = new AnyNameStarContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(134);
                        match(STAR);
                        setState(135);
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

    public static class VariableContext extends AtomarSubformulaContext {
        public ISymbolContext iSymbol() {
            return getRuleContext(ISymbolContext.class, 0);
        }

        public VariableContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitVariable(this);
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

    public static class AnyFormulaContext extends AtomarSubformulaContext {
        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaContext(AtomarSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyFormula(this);
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

    public static class FormulaInParenContext extends AtomarSubformulaContext {
        public TerminalNode OBRACKET() {
            return getToken(PatternParser.OBRACKET, 0);
        }

        public FormulaContext formula() {
            return getRuleContext(FormulaContext.class, 0);
        }

        public TerminalNode OPAREN() {
            return getToken(PatternParser.OPAREN, 0);
        }

        public TerminalNode CBRACKET() {
            return getToken(PatternParser.CBRACKET, 0);
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

    public final AtomarSubformulaContext atomarSubformula() throws RecognitionException {
        AtomarSubformulaContext _localctx = new AtomarSubformulaContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_atomarSubformula);
        try {
            setState(152);
            switch (_input.LA(1)) {
                case TRUE:
                    _localctx = new TrueContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(138);
                        match(TRUE);
                    }
                    break;
                case FALSE:
                    _localctx = new FalseContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(139);
                        match(FALSE);
                    }
                    break;
                case OBRACKET:
                case OPAREN:
                    _localctx = new FormulaInParenContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(148);
                        switch (_input.LA(1)) {
                            case OBRACKET:
                                {
                                    setState(140);
                                    match(OBRACKET);
                                    setState(141);
                                    formula();
                                    setState(142);
                                    match(CBRACKET);
                                }
                                break;
                            case OPAREN:
                                {
                                    setState(144);
                                    match(OPAREN);
                                    setState(145);
                                    formula();
                                    setState(146);
                                    match(CPAREN);
                                }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    break;
                case SQUOTE:
                case ISYMBOL:
                    _localctx = new VariableContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(150);
                        iSymbol();
                    }
                    break;
                case ANYFORMULA:
                    _localctx = new AnyFormulaContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(151);
                        match(ANYFORMULA);
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
        enterRule(_localctx, 14, RULE_namedFormula);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(154);
                name();
                setState(155);
                match(AT);
                setState(156);
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

    public static class ReadNameContext extends NamedSubformulaContext {
        public TerminalNode READ() {
            return getToken(PatternParser.READ, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public ReadNameContext(NamedSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitReadName(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class AnyFormulaWithNameContext extends NamedSubformulaContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode ANYFORMULA() {
            return getToken(PatternParser.ANYFORMULA, 0);
        }

        public AnyFormulaWithNameContext(NamedSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitAnyFormulaWithName(this);
            else return visitor.visitChildren(this);
        }
    }

    public final NamedSubformulaContext namedSubformula() throws RecognitionException {
        NamedSubformulaContext _localctx = new NamedSubformulaContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_namedSubformula);
        try {
            setState(162);
            switch (_input.LA(1)) {
                case ANYFORMULA:
                    _localctx = new AnyFormulaWithNameContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(158);
                        match(ANYFORMULA);
                        setState(159);
                        name();
                    }
                    break;
                case READ:
                    _localctx = new ReadNameContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(160);
                        match(READ);
                        setState(161);
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
        enterRule(_localctx, 18, RULE_subformulaWithName);
        try {
            setState(166);
            switch (_input.LA(1)) {
                case ANYFORMULA:
                case SQUOTE:
                case TRUE:
                case FALSE:
                case OBRACKET:
                case OPAREN:
                case ISYMBOL:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(164);
                        atomarSubformula();
                    }
                    break;
                case COMPLEMENT:
                case NEG:
                case BOX:
                case DIAMOND:
                case CONTAINS:
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(165);
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

    public static class BoxContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public TerminalNode BOX() {
            return getToken(PatternParser.BOX, 0);
        }

        public BoxContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitBox(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class DiamondContext extends PrefixSubformulaContext {
        public SubformulaContext sub;

        public TerminalNode DIAMOND() {
            return getToken(PatternParser.DIAMOND, 0);
        }

        public SubformulaContext subformula() {
            return getRuleContext(SubformulaContext.class, 0);
        }

        public DiamondContext(PrefixSubformulaContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof PatternParserVisitor)
                return ((PatternParserVisitor<? extends T>) visitor).visitDiamond(this);
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
        enterRule(_localctx, 20, RULE_prefixSubformula);
        try {
            setState(178);
            switch (_input.LA(1)) {
                case NEG:
                    _localctx = new NegationContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(168);
                        match(NEG);
                        setState(169);
                        ((NegationContext) _localctx).sub = subformula();
                    }
                    break;
                case BOX:
                    _localctx = new BoxContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(170);
                        match(BOX);
                        setState(171);
                        ((BoxContext) _localctx).sub = subformula();
                    }
                    break;
                case DIAMOND:
                    _localctx = new DiamondContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                    {
                        setState(172);
                        match(DIAMOND);
                        setState(173);
                        ((DiamondContext) _localctx).sub = subformula();
                    }
                    break;
                case COMPLEMENT:
                    _localctx = new ComplementContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                    {
                        setState(174);
                        match(COMPLEMENT);
                        setState(175);
                        ((ComplementContext) _localctx).sub = subformula();
                    }
                    break;
                case CONTAINS:
                    _localctx = new ContainsDescendantContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                    {
                        setState(176);
                        match(CONTAINS);
                        setState(177);
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
        enterRule(_localctx, 22, RULE_iSymbol);
        try {
            setState(185);
            switch (_input.LA(1)) {
                case SQUOTE:
                    _localctx = new FixedNameContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(180);
                        match(SQUOTE);
                        setState(181);
                        name();
                        setState(182);
                        match(SQUOTE);
                    }
                    break;
                case ISYMBOL:
                    _localctx = new AnyNameContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                    {
                        setState(184);
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

        public TerminalNode DISTINCT(int i) {
            return getToken(PatternParser.DISTINCT, i);
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
        enterRule(_localctx, 24, RULE_name);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(187);
                ((NameContext) _localctx).sym = match(ISYMBOL);
                setState(191);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == DISTINCT) {
                    {
                        {
                            setState(188);
                            match(DISTINCT);
                        }
                    }
                    setState(193);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
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

    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3 \u00c5\4\2\t\2\4"
                    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
                    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\3\3\3\5\3\"\n\3\3\4\3\4\3"
                    + "\4\6\4\'\n\4\r\4\16\4(\3\4\3\4\3\4\6\4.\n\4\r\4\16\4/\3\4\3\4\3\4\3\4"
                    + "\5\4\66\n\4\6\48\n\4\r\4\16\49\3\4\3\4\6\4>\n\4\r\4\16\4?\3\4\3\4\3\4"
                    + "\3\4\5\4F\n\4\7\4H\n\4\f\4\16\4K\13\4\3\4\3\4\3\4\3\4\5\4Q\n\4\6\4S\n"
                    + "\4\r\4\16\4T\3\4\3\4\6\4Y\n\4\r\4\16\4Z\3\4\3\4\3\4\3\4\5\4a\n\4\7\4c"
                    + "\n\4\f\4\16\4f\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4p\n\4\3\5\3\5\5"
                    + "\5t\n\5\3\6\3\6\3\6\3\6\5\6z\n\6\3\7\3\7\3\7\3\7\5\7\u0080\n\7\3\7\3\7"
                    + "\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u008b\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"
                    + "\3\b\3\b\3\b\5\b\u0097\n\b\3\b\3\b\5\b\u009b\n\b\3\t\3\t\3\t\3\t\3\n\3"
                    + "\n\3\n\3\n\5\n\u00a5\n\n\3\13\3\13\5\13\u00a9\n\13\3\f\3\f\3\f\3\f\3\f"
                    + "\3\f\3\f\3\f\3\f\3\f\5\f\u00b5\n\f\3\r\3\r\3\r\3\r\3\r\5\r\u00bc\n\r\3"
                    + "\16\3\16\7\16\u00c0\n\16\f\16\16\16\u00c3\13\16\3\16\2\2\17\2\4\6\b\n"
                    + "\f\16\20\22\24\26\30\32\2\2\u00e0\2\34\3\2\2\2\4!\3\2\2\2\6o\3\2\2\2\b"
                    + "s\3\2\2\2\ny\3\2\2\2\f\u008a\3\2\2\2\16\u009a\3\2\2\2\20\u009c\3\2\2\2"
                    + "\22\u00a4\3\2\2\2\24\u00a8\3\2\2\2\26\u00b4\3\2\2\2\30\u00bb\3\2\2\2\32"
                    + "\u00bd\3\2\2\2\34\35\5\4\3\2\35\36\7\2\2\3\36\3\3\2\2\2\37\"\5\6\4\2 "
                    + "\"\5\n\6\2!\37\3\2\2\2! \3\2\2\2\"\5\3\2\2\2#&\5\n\6\2$%\7\7\2\2%\'\5"
                    + "\n\6\2&$\3\2\2\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)p\3\2\2\2*-\5\n\6\2+,"
                    + "\7\36\2\2,.\5\n\6\2-+\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60p\3\2"
                    + "\2\2\61\67\5\b\5\2\62\65\7\f\2\2\63\66\5\b\5\2\64\66\7\4\2\2\65\63\3\2"
                    + "\2\2\65\64\3\2\2\2\668\3\2\2\2\67\62\3\2\2\289\3\2\2\29\67\3\2\2\29:\3"
                    + "\2\2\2:p\3\2\2\2;<\7\4\2\2<>\7\f\2\2=;\3\2\2\2>?\3\2\2\2?=\3\2\2\2?@\3"
                    + "\2\2\2@A\3\2\2\2AI\5\b\5\2BE\7\f\2\2CF\5\b\5\2DF\7\4\2\2EC\3\2\2\2ED\3"
                    + "\2\2\2FH\3\2\2\2GB\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2Jp\3\2\2\2KI\3"
                    + "\2\2\2LR\5\b\5\2MP\7\r\2\2NQ\5\b\5\2OQ\7\4\2\2PN\3\2\2\2PO\3\2\2\2QS\3"
                    + "\2\2\2RM\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2Up\3\2\2\2VW\7\4\2\2WY\7"
                    + "\r\2\2XV\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\d\5\b\5\2]"
                    + "`\7\r\2\2^a\5\b\5\2_a\7\4\2\2`^\3\2\2\2`_\3\2\2\2ac\3\2\2\2b]\3\2\2\2"
                    + "cf\3\2\2\2db\3\2\2\2de\3\2\2\2ep\3\2\2\2fd\3\2\2\2gh\5\n\6\2hi\7\17\2"
                    + "\2ij\5\n\6\2jp\3\2\2\2kl\5\n\6\2lm\7\16\2\2mn\5\n\6\2np\3\2\2\2o#\3\2"
                    + "\2\2o*\3\2\2\2o\61\3\2\2\2o=\3\2\2\2oL\3\2\2\2oX\3\2\2\2og\3\2\2\2ok\3"
                    + "\2\2\2p\7\3\2\2\2qt\5\n\6\2rt\5\f\7\2sq\3\2\2\2sr\3\2\2\2t\t\3\2\2\2u"
                    + "z\5\20\t\2vz\5\22\n\2wz\5\16\b\2xz\5\26\f\2yu\3\2\2\2yv\3\2\2\2yw\3\2"
                    + "\2\2yx\3\2\2\2z\13\3\2\2\2{\u0080\3\2\2\2|}\5\32\16\2}~\7\t\2\2~\u0080"
                    + "\3\2\2\2\177{\3\2\2\2\177|\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\5\16"
                    + "\b\2\u0082\u0083\7\b\2\2\u0083\u008b\3\2\2\2\u0084\u0085\5\22\n\2\u0085"
                    + "\u0086\7\b\2\2\u0086\u008b\3\2\2\2\u0087\u008b\7\b\2\2\u0088\u0089\7\b"
                    + "\2\2\u0089\u008b\5\32\16\2\u008a\177\3\2\2\2\u008a\u0084\3\2\2\2\u008a"
                    + "\u0087\3\2\2\2\u008a\u0088\3\2\2\2\u008b\r\3\2\2\2\u008c\u009b\7\22\2"
                    + "\2\u008d\u009b\7\23\2\2\u008e\u008f\7\24\2\2\u008f\u0090\5\4\3\2\u0090"
                    + "\u0091\7\25\2\2\u0091\u0097\3\2\2\2\u0092\u0093\7\26\2\2\u0093\u0094\5"
                    + "\4\3\2\u0094\u0095\7\27\2\2\u0095\u0097\3\2\2\2\u0096\u008e\3\2\2\2\u0096"
                    + "\u0092\3\2\2\2\u0097\u009b\3\2\2\2\u0098\u009b\5\30\r\2\u0099\u009b\7"
                    + "\5\2\2\u009a\u008c\3\2\2\2\u009a\u008d\3\2\2\2\u009a\u0096\3\2\2\2\u009a"
                    + "\u0098\3\2\2\2\u009a\u0099\3\2\2\2\u009b\17\3\2\2\2\u009c\u009d\5\32\16"
                    + "\2\u009d\u009e\7\t\2\2\u009e\u009f\5\24\13\2\u009f\21\3\2\2\2\u00a0\u00a1"
                    + "\7\5\2\2\u00a1\u00a5\5\32\16\2\u00a2\u00a3\7\6\2\2\u00a3\u00a5\5\32\16"
                    + "\2\u00a4\u00a0\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\23\3\2\2\2\u00a6\u00a9"
                    + "\5\16\b\2\u00a7\u00a9\5\26\f\2\u00a8\u00a6\3\2\2\2\u00a8\u00a7\3\2\2\2"
                    + "\u00a9\25\3\2\2\2\u00aa\u00ab\7\13\2\2\u00ab\u00b5\5\n\6\2\u00ac\u00ad"
                    + "\7\20\2\2\u00ad\u00b5\5\n\6\2\u00ae\u00af\7\21\2\2\u00af\u00b5\5\n\6\2"
                    + "\u00b0\u00b1\7\3\2\2\u00b1\u00b5\5\n\6\2\u00b2\u00b3\7\37\2\2\u00b3\u00b5"
                    + "\5\n\6\2\u00b4\u00aa\3\2\2\2\u00b4\u00ac\3\2\2\2\u00b4\u00ae\3\2\2\2\u00b4"
                    + "\u00b0\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\27\3\2\2\2\u00b6\u00b7\7\n\2"
                    + "\2\u00b7\u00b8\5\32\16\2\u00b8\u00b9\7\n\2\2\u00b9\u00bc\3\2\2\2\u00ba"
                    + "\u00bc\5\32\16\2\u00bb\u00b6\3\2\2\2\u00bb\u00ba\3\2\2\2\u00bc\31\3\2"
                    + "\2\2\u00bd\u00c1\7\30\2\2\u00be\u00c0\7 \2\2\u00bf\u00be\3\2\2\2\u00c0"
                    + "\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\33\3\2\2"
                    + "\2\u00c3\u00c1\3\2\2\2\33!(/\659?EIPTZ`dosy\177\u008a\u0096\u009a\u00a4"
                    + "\u00a8\u00b4\u00bb\u00c1";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
