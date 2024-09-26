package de.tudortmund.cs.iltis.logiclib.predicate.io;

import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.TermReader;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.BeforeClass;

/** Test for TermReader */
public class TermReaderTest extends TestRig<Term> {
    @BeforeClass
    public static void initTestRig() {
        checkForNegativeTarget = false;
        checkForAllowedVars = true;

        reader = new TermReader();
        reader.setTesting(true);
        reader.setVerbose(verbose);

        // custom SignaturePolicy
        List<String> rels = new ArrayList<>();
        rels.add("R");
        rels.add("Q");
        List<String> funs = new ArrayList<>();
        funs.add("f");
        funs.add("g");
        funs.add("h");
        funs.add("e");
        SignaturePolicy customSigPolicy =
                new SignaturePolicy(
                        (rel, checkInfixArity) -> rels.contains(rel.getName()),
                        (fun, checkInfixArity) -> funs.contains(fun.getName()));

        // default SignaturePolicy
        SignaturePolicy sigPolicy = SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY;

        // signature: (R/2, =/2, 1t/0, t1/0)
        Set<RelationSymbol> rels1 = new TreeSet<>();
        rels1.add(new RelationSymbol("R", 2, false));
        rels1.add(new RelationSymbol("=", 2, true));
        Set<FunctionSymbol> funs1 = new TreeSet<>();
        funs1.add(new FunctionSymbol("a", 0, false));
        funs1.add(new FunctionSymbol("f", 1, false));
        funs1.add(new FunctionSymbol("+", 2, true));
        Signature signature = new Signature(rels1, funs1);

        // #############
        // # positives #
        // #############

        positives = new ArrayList<>();
        Term term;
        Set<VariableSymbol> vars = new HashSet<VariableSymbol>();
        vars.add(new VariableSymbol("x"));
        vars.add(new VariableSymbol("y"));

        // simple
        // TODO spaces are important
        term =
                new FunctionTerm(
                        new FunctionSymbol("+", 2, true),
                        new FunctionTerm("f", new FunctionTerm("a")),
                        new FunctionTerm("f", new Variable("x")));
        positives.add(new Object[] {"f(a) + f(x)", term, signature, vars});

        // medium
        term =
                new FunctionTerm(
                        "f",
                        new FunctionTerm("f", new Variable("x"), new Variable("y")),
                        new FunctionTerm("g", new FunctionTerm("h", new FunctionTerm("e"))));
        positives.add(new Object[] {"f(f(x,y),g(h(e)))", term, sigPolicy, vars});

        // with superflous parentheses
        term = new FunctionTerm("f", new Variable("x"), new Variable("y"));
        positives.add(new Object[] {"(f(x,y))", term, customSigPolicy, vars});

        negatives = new ArrayList<>();

        // #######################
        // # signature erroneous #
        // #######################

        // f with different arities
        negatives.add(
                new Object[] {
                    "f(f(x),y)",
                    null,
                    sigPolicy,
                    vars,
                    ValidityFaultCollection.class,
                    ValidityFaultReason.SAME_FUNCTION_SYMBOL_WITH_DIFFERENT_ARITIES
                });

        // R as function symbol
        negatives.add(
                new Object[] {
                    "R(x, y)",
                    null,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.FUNCTION_SYMBOL_SHOULD_BE_RELATION
                });

        // + as function symbol
        negatives.add(
                new Object[] {
                    "x + y",
                    null,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.FUNCTION_SYMBOL_NOT_KNOWN
                });

        // f with wrong infix value
        negatives.add(
                new Object[] {
                    "x f y",
                    null,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.INFIX_OF_FUNCTION_SYMBOL_WRONG
                });

        // f with arity 0
        negatives.add(
                new Object[] {
                    "f",
                    null,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.ARITY_OF_FUNCTION_SYMBOL_WRONG
                });

        // ##########################
        // # parentheses imbalanced #
        // ##########################

        // a closing parentheses is missing
        negatives.add(
                new Object[] {
                    "f(g(x,y)",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });

        // an opening parentheses is missing
        negatives.add(
                new Object[] {
                    "f(g x),y)",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });
        negatives.add(
                new Object[] {
                    "f(x),y)",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });

        // #######################
        // # parentheses missing #
        // #######################

        // parentheses are missing in term (NoViableAltException)
        // TODO improve
        negatives.add(
                new Object[] {
                    "x + y + x",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });

        // #########################
        // # various syntax errors #
        // #########################

        // empty
        negatives.add(
                new Object[] {
                    "",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });
        // neg in front of term
        negatives.add(
                new Object[] {
                    "! a",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.NEGATION_IN_TERM
                });
        // invalid character (- as symbol)
        // not possible with CustomizedLexer
        // negatives.add(new Object[] {"(x + y) - x", null, sigPolicy, vars,
        // 		ParsingFaultCollection.class,
        // 		GeneralParsingFaultReason.INVALID_SYMBOL});
        // formula instead of term
        negatives.add(
                new Object[] {
                    "R(x) -> Q(x)",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralParsingFaultReason.VARIOUS
                });

        // ####################################
        // # allowed free variables erroneous #
        // ####################################

        // z is not allowed
        negatives.add(
                new Object[] {
                    "f(x,y,z)",
                    null,
                    sigPolicy,
                    vars,
                    FreeVariablesFaultCollection.class,
                    FreeVariablesFaultReason.SYMBOL_NOT_ALLOWED
                });
    }
}
