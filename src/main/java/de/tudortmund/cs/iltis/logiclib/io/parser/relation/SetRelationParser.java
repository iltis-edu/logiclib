// Generated from
// ../src/main/java/de/tudortmund/cs/iltis/logiclib/io/parser/relation/SetRelationParser.g4 by ANTLR
// 4.4
package de.tudortmund.cs.iltis.logiclib.io.parser.relation;

import de.tudortmund.cs.iltis.utils.io.parser.general.AbstractParser;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SetRelationParser extends AbstractParser {
    static {
        RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int OBRACE = 1,
            CBRACE = 2,
            OPAREN = 3,
            CPAREN = 4,
            COMMA = 5,
            WHITESPACE = 6,
            INDEXED_SYMBOL = 7;
    public static final String[] tokenNames = {
        "<INVALID>", "OBRACE", "CBRACE", "OPAREN", "CPAREN", "COMMA", "WHITESPACE", "INDEXED_SYMBOL"
    };
    public static final int RULE_initRelation = 0,
            RULE_relation = 1,
            RULE_content = 2,
            RULE_tuple = 3,
            RULE_tupleContent = 4,
            RULE_symbol = 5;
    public static final String[] ruleNames = {
        "initRelation", "relation", "content", "tuple", "tupleContent", "symbol"
    };

    @Override
    public String getGrammarFileName() {
        return "SetRelationParser.g4";
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

    public SetRelationParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class InitRelationContext extends ParserRuleContext {
        public RelationContext relation() {
            return getRuleContext(RelationContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(SetRelationParser.EOF, 0);
        }

        public InitRelationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_initRelation;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitInitRelation(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InitRelationContext initRelation() throws RecognitionException {
        InitRelationContext _localctx = new InitRelationContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_initRelation);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(12);
                relation();
                setState(13);
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

    public static class RelationContext extends ParserRuleContext {
        public TerminalNode OBRACE() {
            return getToken(SetRelationParser.OBRACE, 0);
        }

        public TerminalNode CBRACE() {
            return getToken(SetRelationParser.CBRACE, 0);
        }

        public ContentContext content() {
            return getRuleContext(ContentContext.class, 0);
        }

        public RelationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relation;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitRelation(this);
            else return visitor.visitChildren(this);
        }
    }

    public final RelationContext relation() throws RecognitionException {
        RelationContext _localctx = new RelationContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_relation);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(15);
                match(OBRACE);
                setState(16);
                content();
                setState(17);
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

    public static class ContentContext extends ParserRuleContext {
        public List<TerminalNode> COMMA() {
            return getTokens(SetRelationParser.COMMA);
        }

        public List<TupleContext> tuple() {
            return getRuleContexts(TupleContext.class);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SetRelationParser.COMMA, i);
        }

        public TupleContext tuple(int i) {
            return getRuleContext(TupleContext.class, i);
        }

        public ContentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_content;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitContent(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ContentContext content() throws RecognitionException {
        ContentContext _localctx = new ContentContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_content);
        try {
            int _alt;
            setState(29);
            switch (_input.LA(1)) {
                case OPAREN:
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(24);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                        while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(19);
                                        tuple();
                                        setState(20);
                                        match(COMMA);
                                    }
                                }
                            }
                            setState(26);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                        }
                        setState(27);
                        tuple();
                    }
                    break;
                case CBRACE:
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

    public static class TupleContext extends ParserRuleContext {
        public TupleContentContext tupleContent() {
            return getRuleContext(TupleContentContext.class, 0);
        }

        public TerminalNode OPAREN() {
            return getToken(SetRelationParser.OPAREN, 0);
        }

        public TerminalNode CPAREN() {
            return getToken(SetRelationParser.CPAREN, 0);
        }

        public TupleContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tuple;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitTuple(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TupleContext tuple() throws RecognitionException {
        TupleContext _localctx = new TupleContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_tuple);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(31);
                match(OPAREN);
                setState(32);
                tupleContent();
                setState(33);
                match(CPAREN);
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

    public static class TupleContentContext extends ParserRuleContext {
        public TupleContentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tupleContent;
        }

        public TupleContentContext() {}

        public void copyFrom(TupleContentContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SymbolWordContext extends TupleContentContext {
        public SymbolContext symbol(int i) {
            return getRuleContext(SymbolContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(SetRelationParser.COMMA);
        }

        public List<SymbolContext> symbol() {
            return getRuleContexts(SymbolContext.class);
        }

        public TerminalNode COMMA(int i) {
            return getToken(SetRelationParser.COMMA, i);
        }

        public SymbolWordContext(TupleContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitSymbolWord(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class EmptyWordContext extends TupleContentContext {
        public EmptyWordContext(TupleContentContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitEmptyWord(this);
            else return visitor.visitChildren(this);
        }
    }

    public final TupleContentContext tupleContent() throws RecognitionException {
        TupleContentContext _localctx = new TupleContentContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_tupleContent);
        try {
            int _alt;
            setState(45);
            switch (_input.LA(1)) {
                case INDEXED_SYMBOL:
                    _localctx = new SymbolWordContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                    {
                        setState(40);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                        while (_alt != 2
                                && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(35);
                                        symbol();
                                        setState(36);
                                        match(COMMA);
                                    }
                                }
                            }
                            setState(42);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                        }
                        setState(43);
                        symbol();
                    }
                    break;
                case CPAREN:
                    _localctx = new EmptyWordContext(_localctx);
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

    public static class SymbolContext extends ParserRuleContext {
        public Token symb;

        public TerminalNode INDEXED_SYMBOL() {
            return getToken(SetRelationParser.INDEXED_SYMBOL, 0);
        }

        public SymbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_symbol;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SetRelationParserVisitor)
                return ((SetRelationParserVisitor<? extends T>) visitor).visitSymbol(this);
            else return visitor.visitChildren(this);
        }
    }

    public final SymbolContext symbol() throws RecognitionException {
        SymbolContext _localctx = new SymbolContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_symbol);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(47);
                ((SymbolContext) _localctx).symb = match(INDEXED_SYMBOL);
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
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t\64\4\2\t\2\4\3"
                    + "\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3"
                    + "\4\3\4\7\4\31\n\4\f\4\16\4\34\13\4\3\4\3\4\5\4 \n\4\3\5\3\5\3\5\3\5\3"
                    + "\6\3\6\3\6\7\6)\n\6\f\6\16\6,\13\6\3\6\3\6\5\6\60\n\6\3\7\3\7\3\7\2\2"
                    + "\b\2\4\6\b\n\f\2\2\61\2\16\3\2\2\2\4\21\3\2\2\2\6\37\3\2\2\2\b!\3\2\2"
                    + "\2\n/\3\2\2\2\f\61\3\2\2\2\16\17\5\4\3\2\17\20\7\2\2\3\20\3\3\2\2\2\21"
                    + "\22\7\3\2\2\22\23\5\6\4\2\23\24\7\4\2\2\24\5\3\2\2\2\25\26\5\b\5\2\26"
                    + "\27\7\7\2\2\27\31\3\2\2\2\30\25\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2\2\32"
                    + "\33\3\2\2\2\33\35\3\2\2\2\34\32\3\2\2\2\35 \5\b\5\2\36 \3\2\2\2\37\32"
                    + "\3\2\2\2\37\36\3\2\2\2 \7\3\2\2\2!\"\7\5\2\2\"#\5\n\6\2#$\7\6\2\2$\t\3"
                    + "\2\2\2%&\5\f\7\2&\'\7\7\2\2\')\3\2\2\2(%\3\2\2\2),\3\2\2\2*(\3\2\2\2*"
                    + "+\3\2\2\2+-\3\2\2\2,*\3\2\2\2-\60\5\f\7\2.\60\3\2\2\2/*\3\2\2\2/.\3\2"
                    + "\2\2\60\13\3\2\2\2\61\62\7\t\2\2\62\r\3\2\2\2\6\32\37*/";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
