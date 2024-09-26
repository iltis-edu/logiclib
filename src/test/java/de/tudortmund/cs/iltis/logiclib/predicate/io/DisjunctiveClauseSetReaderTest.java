package de.tudortmund.cs.iltis.logiclib.predicate.io;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause.DisjunctiveClauseSetReader;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClauseSet;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.FunctionTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.utils.FreeVariablesFaultCollection.FreeVariablesFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SubsetFaultCollection.SubsetFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.ValidityFaultCollection.ValidityFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.junit.BeforeClass;

/** Test for DisjunctiveClauseSetReader */
public class DisjunctiveClauseSetReaderTest extends TestRig<DisjunctiveClauseSet> {
    @BeforeClass
    public static void initTestRig() {
        checkForAllowedVars = true;
        checkForNegativeTarget = true;

        reader = new DisjunctiveClauseSetReader();
        reader.setTesting(true);
        reader.setVerbose(verbose);

        // default SignaturePolicy
        SignaturePolicy sigPolicy = SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY;

        // #############
        // # positives #
        // #############

        positives = new ArrayList<>();
        DisjunctiveClauseSet target;
        DisjunctiveClause clause1 =
                new DisjunctiveClause(new RelationAtom("Q", new Variable("x")).not());
        DisjunctiveClause clause2 =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x"), new Variable("y")),
                        new RelationAtom("Q", new Variable("x")).not());
        DisjunctiveClause clause3 =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x"), new Variable("y")),
                        new RelationAtom("Q", new Variable("x")).not(),
                        new RelationAtom(
                                "R",
                                new FunctionTerm(
                                        "f",
                                        new Variable("x"),
                                        new FunctionTerm("g", new Variable("z"))),
                                new Variable("y")));
        Set<VariableSymbol> vars = new HashSet<>();
        vars.add(new VariableSymbol("x"));
        vars.add(new VariableSymbol("y"));
        vars.add(new VariableSymbol("z"));

        // empty
        target = new DisjunctiveClauseSet();
        positives.add(new Object[] {"{}", target, sigPolicy, vars});

        // empty of empty
        target = new DisjunctiveClauseSet(new DisjunctiveClause());
        positives.add(new Object[] {"{{}}", target, sigPolicy, vars});

        // one element
        target = new DisjunctiveClauseSet(clause2);
        positives.add(new Object[] {"{{R(x,y), ! Q(x)}}", target, sigPolicy, vars});

        // two elements
        target = new DisjunctiveClauseSet(clause2, clause3);
        positives.add(
                new Object[] {
                    "{ {R(x,y), ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)} }", target, sigPolicy, vars
                });

        // large
        target = new DisjunctiveClauseSet(clause1, clause2, clause3, new DisjunctiveClause());
        positives.add(
                new Object[] {
                    "{ {!Q(x)} , {R(x,y), ! Q(x)} , {R(x,y),!Q(x),R(f(x,g(z)),y)} , { } , {!Q(x)} }",
                    target,
                    sigPolicy,
                    vars
                });

        negatives = new ArrayList<>();

        // #######################
        // # signature erroneous #
        // #######################

        DisjunctiveClause clause2dash;
        // R with different arities
        // TODO Ambiguity
        clause2dash = new DisjunctiveClause(new RelationAtom("R", new FunctionTerm("a")).not());
        target = new DisjunctiveClauseSet(clause2, clause2dash);
        negatives.add(
                new Object[] {
                    "{{R(x,y), ! Q(x)},{! R(a)}}",
                    target,
                    sigPolicy,
                    vars,
                    ValidityFaultCollection.class,
                    ValidityFaultReason.SAME_RELATION_SYMBOL_WITH_DIFFERENT_ARITIES
                });

        // f with arity 0
        clause2dash =
                new DisjunctiveClause(
                        new RelationAtom("R", new FunctionTerm("f"), new FunctionTerm("a")).not());
        target = new DisjunctiveClauseSet(clause2, clause2dash);
        negatives.add(
                new Object[] {
                    "{{R(x,y), ! Q(x)},{! R(f,a)}}",
                    target,
                    sigPolicy,
                    vars,
                    SubsetFaultCollection.class,
                    SubsetFaultReason.ARITY_OF_FUNCTION_SYMBOL_WRONG
                });

        // ##########################
        // # parentheses imbalanced #
        // ##########################

        // a closing brace is missing
        target = new DisjunctiveClauseSet(clause2, clause3);
        negatives.add(
                new Object[] {
                    "{ {R(x,y), ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y) }",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });

        // a closing brace is missing
        negatives.add(
                new Object[] {
                    "{ {R(x,y), ! Q(x)}, R(x,y),!Q(x),R(f(x,g(z)),y) } }",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.OPENING_PAREN_MISSING
                });

        // everything more (especially with parentheses) is difficult to repair ...

        // #######################
        // # parentheses missing #
        // #######################

        // empty
        negatives.add(
                new Object[] {
                    "",
                    new DisjunctiveClauseSet(),
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_SETS_MISSING
                });
        // not empty, one element
        target = new DisjunctiveClauseSet(clause1);
        negatives.add(
                new Object[] {
                    " {! Q(x)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // not empty
        target = new DisjunctiveClauseSet(clause2, clause3);
        negatives.add(
                new Object[] {
                    " {R(x,y), ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_SETS_MISSING
                });
        // formula braces missing
        target = new DisjunctiveClauseSet(clause1, clause2, clause3);
        negatives.add(
                new Object[] {
                    " {!Q(x),{R(x,y), ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)}} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // formula braces missing
        target = new DisjunctiveClauseSet(clause2, clause1, clause3);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x)}, !Q(x),{R(x,y),!Q(x),R(f(x,g(z)),y)}} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // formula braces missing
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y) ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x)} {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {R(x,y), ! Q(x)} {R(x,y),!Q(x),R(f(x,g(z)),y)},{!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_SETS_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {R(x,y), ! Q(x)} {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x) ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_SETS_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {R(x,y), ! Q(x)} {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x) ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // smaller test
        target = new DisjunctiveClauseSet(clause2, clause1);
        negatives.add(
                new Object[] {
                    "{R(x,y) ! Q(x), } !Q(x),",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });

        // #########################
        // # various syntax errors #
        // #########################

        // neg in front of term
        negatives.add(
                new Object[] {
                    "{{R(! a)}}",
                    null,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.NEGATION_IN_TERM
                });
        // invalid character (- as symbol)
        // not valid for AbstractCustomizableLexer anymore
        // negatives.add(new Object[] {"{{R(x), Q(x - y, x)}}", null, sigPolicy, vars,
        //		ParsingFaultCollection.class,
        //		GeneralFormulaFaultReason.INVALID_SYMBOL});
        // illegal formula
        negatives.add(
                new Object[] {
                    "{{R(x), R(x) -> Q(x)}}",
                    new DisjunctiveClauseSet(),
                    sigPolicy,
                    vars,
                    ClauseFaultCollection.class,
                    ClauseFaultReason.CLAUSE_CONTAINS_INVALID_FORMULA
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y) ! Q(x)}, {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x)} {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_SETS_MISSING
                });
        // superfluous comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x)},, {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_SETS_SUPERFLUOUS
                });
        // superfluous comma and parentheses
        target = new DisjunctiveClauseSet(clause2, clause3, clause1);
        negatives.add(
                new Object[] {
                    " {{R(x,y), ! Q(x),}, {R(x,y),!Q(x),R(f(x,g(z)),y)},!Q(x)} ",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS
                });

        // ####################################
        // # allowed free variables erroneous #
        // ####################################

        // x_1 is not allowed
        target =
                new DisjunctiveClauseSet(
                        new DisjunctiveClause(new RelationAtom("R", new Variable("x_1"))));
        negatives.add(
                new Object[] {
                    "{{R(x_1)}}",
                    target,
                    sigPolicy,
                    vars,
                    FreeVariablesFaultCollection.class,
                    FreeVariablesFaultReason.SYMBOL_NOT_ALLOWED
                });
    }
}
