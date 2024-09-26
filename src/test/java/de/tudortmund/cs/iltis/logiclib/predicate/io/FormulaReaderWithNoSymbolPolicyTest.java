package de.tudortmund.cs.iltis.logiclib.predicate.io;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.InfixPredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.False;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.True;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.symbol.BaseSymbolSplittingPolicy;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import org.junit.BeforeClass;

/** Test for FormulaReader */
public class FormulaReaderWithNoSymbolPolicyTest extends TestRig<Formula> {
    @BeforeClass
    public static void initTestRig() {
        checkForAllowedVars = false;
        checkForNegativeTarget = false;

        InfixPredicateReaderProperties infixProps = InfixPredicateReaderProperties.createDefault();
        infixProps.addInfixFunctionsSymbols("+");
        PredicateReaderProperties props = PredicateReaderProperties.createDefault(infixProps);
        props.setSymbolSplittingPolicy(new BaseSymbolSplittingPolicy());
        reader = new FormulaReader(props);
        reader.setVerbose(true);
        reader.setTesting(true);

        Set<VariableSymbol> vars = null;

        // signature 1: (R/2, =/2, 1t/0, t1/0)
        Set<RelationSymbol> rels1 = new TreeSet<>();
        rels1.add(new RelationSymbol("R", 2, false));
        rels1.add(new RelationSymbol("=", 2, true));
        Set<FunctionSymbol> funs1 = new TreeSet<>();
        funs1.add(new FunctionSymbol("1t", 0, false));
        funs1.add(new FunctionSymbol("t2", 0, false));
        Signature signature1 = new Signature(rels1, funs1);

        // signature 2: (R^1/1, Q/0, =/2, pi/2, +/2)
        Set<RelationSymbol> rels2 = new TreeSet<>();
        rels2.add(new RelationSymbol("R^1", 1, false));
        rels2.add(new RelationSymbol("Q", 0, false));
        rels2.add(new RelationSymbol("=", 2, true));
        Set<FunctionSymbol> funs2 = new TreeSet<>();
        funs2.add(new FunctionSymbol("\u03c0", 2, false));
        funs2.add(new FunctionSymbol("+", 2, true));
        Signature signature2 = new Signature(rels2, funs2);

        SignaturePolicy sigPolicy = SignaturePolicy.TU_DORTMUND_LS1_WITH_INDEX_POLICY;

        positives = new ArrayList<>();
        // relation (arity 2), implication, top, variable, subscripts, existential quantifier
        positives.add(
                new Object[] {
                    "exists x_1 (R(x_1,x_z) -> 1)",
                    new RelationAtom("R", new Variable("x_1"), new Variable("x_z"))
                            .implies(new True())
                            .exists(new Variable("x_1")),
                    signature1,
                    vars
                });
        // constant, and, universal quantifier, infix relation, negation
        positives.add(
                new Object[] {
                    "!1t = t2 & forall x R(1t,x)",
                    new RelationAtom("=", true, new FunctionTerm("1t"), new FunctionTerm("t2"))
                            .not()
                            .and(
                                    new RelationAtom("R", new FunctionTerm("1t"), new Variable("x"))
                                            .forAll(new Variable("x"))),
                    signature1,
                    vars
                });
        // more parentheses than necessary, brackets
        positives.add(
                new Object[] {
                    "(1t = t2) & [forall x R(1t,x)]",
                    new RelationAtom("=", true, new FunctionTerm("1t"), new FunctionTerm("t2"))
                            .and(
                                    new RelationAtom("R", new FunctionTerm("1t"), new Variable("x"))
                                            .forAll(new Variable("x"))),
                    signature1,
                    vars
                });
        // relation (arity 0), equivalence, bottom
        positives.add(
                new Object[] {
                    "Q <-> 0", new RelationAtom("Q").equivalentTo(new False()), signature2, vars
                });
        // relation (artiy 1), function (arity 2), or, unicode literal, superscript
        positives.add(
                new Object[] {
                    "R^1(\u03c0(0_a, 1_b)) | Q",
                    new RelationAtom(
                                    "R^1",
                                    new FunctionTerm(
                                            "\u03c0", new Variable("0_a"), new Variable("1_b")))
                            .or(new RelationAtom("Q")),
                    signature2,
                    vars
                });
        // nested infix function
        positives.add(
                new Object[] {
                    "R^1((x_1+x_2) + x)",
                    new RelationAtom(
                            "R^1",
                            new FunctionTerm(
                                    "+",
                                    true,
                                    new FunctionTerm(
                                            "+", true, new Variable("x_1"), new Variable("x_2")),
                                    new Variable("x"))),
                    signature2,
                    vars
                });
        // mathematics with parentheses ...
        positives.add(
                new Object[] {
                    "(a + b) = c | (a + b) = d",
                    new RelationAtom(
                                    "=",
                                    true,
                                    new FunctionTerm(
                                            "+", true, new Variable("a"), new Variable("b")),
                                    new Variable("c"))
                            .or(
                                    new RelationAtom(
                                            "=",
                                            true,
                                            new FunctionTerm(
                                                    "+",
                                                    true,
                                                    new Variable("a"),
                                                    new Variable("b")),
                                            new Variable("d"))),
                    signature2,
                    vars
                });
        // three ORs and ANDs
        positives.add(
                new Object[] {
                    "(Q&R^1(a)&R^1(b)) | R^1(c) | Q",
                    new RelationAtom("Q")
                            .and(
                                    new RelationAtom("R^1", new Variable("a")),
                                    new RelationAtom("R^1", new Variable("b")))
                            .or(new RelationAtom("R^1", new Variable("c")), new RelationAtom("Q")),
                    signature2,
                    vars
                });
        // backslashes
        positives.add(
                new Object[] {
                    "(R^1(\\phi + \\psi))",
                    new RelationAtom(
                            "R^1",
                            new FunctionTerm(
                                    "+", true, new Variable("\\phi"), new Variable("\\psi"))),
                    signature2,
                    vars
                });
        // supplementary unicode characters (Emoji ;-))
        String emoji1 = new String(new int[] {0x01F600}, 0, 1);
        String emoji2 = new String(new int[] {0x01F602}, 0, 1);
        positives.add(
                new Object[] {
                    "(R^1(" + emoji1 + " + " + emoji2 + "))",
                    new RelationAtom(
                            "R^1",
                            new FunctionTerm(
                                    "+", true, new Variable(emoji1), new Variable(emoji2))),
                    signature2,
                    vars
                });

        // erroneously: multiple variables for quantifier
        positives.add(
                new Object[] {
                    "∀y E(y)",
                    new RelationAtom("E", new Variable("y")).forAll(new Variable("y")),
                    sigPolicy,
                    vars
                });

        // keep insertion order in map
        negatives = new ArrayList<>();

        // #######################
        // # signature erroneous #
        // #######################

        // + is relation and function
        negatives.add(
                new Object[] {
                    "R^1(x_1 + x_2) + x",
                    null,
                    signature2,
                    vars,
                    ValidityFaultCollection.class,
                    ValidityFaultReason.SAME_SYMBOL_FOR_RELATION_AND_FUNCTION
                });
        // * not known as function
        negatives.add(
                new Object[] {
                    "R^1((x_1 + x_2) * x)",
                    null,
                    signature2,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.FUNCTION_SYMBOL_NOT_KNOWN
                });
        // * not known as relation
        negatives.add(
                new Object[] {
                    "R^1(x_1 + x_2) * x",
                    null,
                    signature2,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.RELATION_SYMBOL_NOT_KNOWN
                });

        // TODO add more

        // many nesting layers with an error in the most inner one
        negatives.add(
                new Object[] {
                    "Q & [[R^1(a)    R^1(b)] | R^1(c) | Q]",
                    null,
                    signature2,
                    vars,
                    ValidityFaultCollection.class,
                    ValidityFaultReason.SAME_SYMBOL_FOR_RELATION_AND_FUNCTION
                });

        // ##########################
        // # parentheses imbalanced #
        // ##########################

        // a closing parentheses is missing in term (NoViableAltException)
        negatives.add(
                new Object[] {
                    "R^1((x_1 + x_2 + x)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // a closing parentheses is missing in term (NoViableAltException)
        negatives.add(
                new Object[] {
                    "R^1[[x_1 + x_2 + x]",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // an opening parentheses is missing in term (msg: extraneous input ')' expecting <EOF>;
        // line 1, position 18)
        negatives.add(
                new Object[] {
                    "R^1[x_1 + x_2] + x]",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // an opening parentheses is missing in term (msg: extraneous input ')' expecting <EOF>;
        // line 1, position 18)
        negatives.add(
                new Object[] {
                    "R^1(x_1 + x_2) + x)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // three ORs and ANDs with 2 missing closing parentheses
        negatives.add(
                new Object[] {
                    "((Q&R^1(a)&R^1(b) | R^1(c) | Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // 2 + 1 missing closing parentheses
        negatives.add(
                new Object[] {
                    "(Q&R^1(a)&(R^1(b) | R^1(c) | (Q -> R^1(d)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // three ORs and ANDs with 2 });ing opening parentheses
        negatives.add(
                new Object[] {
                    "Q&R^1(a)&R^1(b)) | R^1(c) | Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // 2 + 1 missing opening parentheses
        negatives.add(
                new Object[] {
                    "Q&R^1(a)&R^1(b))) | R^1(c)) | Q -> R^1(d))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // two times too many closing parens
        negatives.add(
                new Object[] {
                    "(Q & (R^1(a) | R^1(b) | (R^1(c)))))))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // more than two times too many closing brackets
        negatives.add(
                new Object[] {
                    "(Q & (R^1(a) | R^1(b) | R^1(c)))]]]]]]",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // less than two times too many closing parens
        negatives.add(
                new Object[] {
                    "(Q & (R^1(a) | R^1(b) | (R^1(c)))))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        // missing closing parentheses, missing opening parentheses and missing closing bracket
        negatives.add(
                new Object[] {
                    "((((((Q) & R^1(a)) | R^1(b) | (R^1(c)))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // missing closing parentheses
        negatives.add(
                new Object[] {
                    "(((((Q & R^1(a)) | R^1(b) | R^1(c))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // missing opening and closing parentheses
        negatives.add(
                new Object[] {
                    "(Q & R^1(a))))) | ((((R^1(b) | R^1(c))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        negatives.add(
                new Object[] {
                    "(Q & R^1(a))))) | ((((R^1(b) | R^1(c))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });
        // opening parentheses is closed by bracket
        negatives.add(
                new Object[] {
                    "(Q & R^1(a)] | R^1(c) | Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.PARENTHESES_MISMATCH
                });
        // opening bracket is closed by parentheses
        negatives.add(
                new Object[] {
                    "R^1(c) | Q | [Q & R^1(a))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.PARENTHESES_MISMATCH
                });

        // #######################
        // # parentheses missing #
        // #######################

        // parentheses are missing in term (NoViableAltException)
        // TODO improve
        negatives.add(
                new Object[] {
                    "R^1(x_1 + x_2 + x)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });
        // mathematics without parentheses ...
        // TODO improve
        negatives.add(
                new Object[] {
                    "a + b = c | a + b = d",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });
        // parentheses are missing and/or-combination (msg: extraneous input '&' expecting {<EOF>,
        // OR}; line 1, position 11)
        negatives.add(
                new Object[] {
                    "Q | R^1(a) & Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });
        // parentheses are missing and/or-combination (msg: extraneous input '&' expecting {<EOF>,
        // OR}; line 1, position 11)
        negatives.add(
                new Object[] {
                    "Q -> R^1(a) & Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });
        // parentheses are missing and/or-combination (msg: extraneous input '&' expecting {<EOF>,
        // OR}; line 1, position 11)
        negatives.add(
                new Object[] {
                    "Q | R^1(a) <-> Q & R^1(b)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });
        // multiple symbols without parentheses
        negatives.add(
                new Object[] {
                    "Q & R^1(a) & (R^1(b)|Q) -> R^1(b) | R^1(c) | Q",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });
        // simple case
        negatives.add(
                new Object[] {
                    "Q & R^1(a) -> R^1(b)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });
        // inside parentheses
        negatives.add(
                new Object[] {
                    "(Q & R^1(a) | R^1(b) | R^1(c))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.PARENTHESES_MISSING
                });

        // #########################
        // # various syntax errors #
        // #########################

        // neg in front of term (NoViableAltException)
        negatives.add(
                new Object[] {
                    "R^1(!a)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.NEGATION_IN_TERM
                });
        // neg in front of term: infix
        negatives.add(
                new Object[] {
                    "R^1(!a + b)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.NEGATION_IN_TERM
                });
        // implication in wrong way
        negatives.add(
                new Object[] {
                    "Q <- (R^1(a) & Q)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaFaultReason.IMPLICATION_IN_WRONG_DIRECTION
                });
        // doubled operator
        // TODO improve
        negatives.add(
                new Object[] {
                    "(Q || R^1(a)) <-> (Q && R^1(b))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });
        // many nesting layers with an error in the most inner one
        // TODO improve
        negatives.add(
                new Object[] {
                    "Q & [[R^1(a) && R^1(b)] | R^1(c) | Q]",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });

        // invalid character (- as symbol)
        // not possible with CustomizedLexer anymore
        // negatives.add(new Object[] {"R^1((x_1 + x_2) - x)",
        //		null, signature2, vars,
        //		ParsingFaultCollection.class,
        //		GeneralParsingFaultReason.INVALID_SYMBOL});
        // multiple variables for quantifier

        // invalid indexed symbol (x_)
        // only possible with CustomizedLexer anymore
        negatives.add(
                new Object[] {
                    "R^1(x_ + x_2)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.INVALID_SYMBOL
                });
        // invalid indexed symbol (x _1)
        // only possible with CustomizedLexer anymore
        negatives.add(
                new Object[] {
                    "R^1(x _1 + x_2)",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.INVALID_SYMBOL
                });

        // multiple variables for quantifier
        negatives.add(
                new Object[] {
                    "forall x, y, z (R^1(x) -> R^1(y))",
                    null,
                    signature2,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.MULTIPLE_VARIABLES_IN_QUANTIFIER
                });
        //		// multiple variables for quantifier
        //		negatives.add(new Object[] {"1 | exists x y z (R^1(x) -> R^1(y))",
        //				null, signature2, vars,
        //				ParsingFaultCollection.class,
        //				PredicateFormulaFaultReason.MULTIPLE_VARIABLES_IN_QUANTIFIER});
        // missing space
        negatives.add(
                new Object[] {
                    "exists xE(x,t)",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });
        // missing space
        negatives.add(
                new Object[] {
                    "exists xR(x)",
                    null,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.RELATION_SYMBOL_NOT_KNOWN
                });
    }
}
