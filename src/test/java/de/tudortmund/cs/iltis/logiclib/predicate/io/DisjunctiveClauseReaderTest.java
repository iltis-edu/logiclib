package de.tudortmund.cs.iltis.logiclib.predicate.io;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaSetFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.parser.predicate.formula.PredicateFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.clause.DisjunctiveClauseReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.InfixPredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.PredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.ClauseFaultCollection.ClauseFaultReason;
import de.tudortmund.cs.iltis.logiclib.predicate.clause.DisjunctiveClause;
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

/** Test for DisjunctiveClauseReader */
public class DisjunctiveClauseReaderTest extends TestRig<DisjunctiveClause> {
    @SuppressWarnings("unused")
    @BeforeClass
    public static void initTestRig() {
        if (false) {
            // --- variant 1 ---
            checkForAllowedVars = true;
            checkForNegativeTarget = true;

            reader = new DisjunctiveClauseReader();
            reader.setTesting(true);
            reader.setVerbose(verbose);
            // ------
        } else {
            // --- variant 2 ---
            checkForAllowedVars = true;
            checkForNegativeTarget = false;

            reader =
                    new DisjunctiveClauseReader(
                            PredicateReaderProperties.createDefault(
                                    InfixPredicateReaderProperties.createDefault(true)));
            reader.setTesting(true);
            reader.setVerbose(verbose);
            // ------
        }

        // default SignaturePolicy
        SignaturePolicy sigPolicy = SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY;

        // #############
        // # positives #
        // #############

        positives = new ArrayList<>();
        DisjunctiveClause target;
        Set<VariableSymbol> vars = new HashSet<>();
        vars.add(new VariableSymbol("x"));
        vars.add(new VariableSymbol("y"));
        vars.add(new VariableSymbol("z"));

        // empty
        target = new DisjunctiveClause();
        positives.add(new Object[] {"{}", target, sigPolicy, vars});

        // one element
        target = new DisjunctiveClause(new RelationAtom("Q", new Variable("x")).not());
        positives.add(new Object[] {"{!Q(x)}", target, sigPolicy, vars});

        // two elements
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x"), new Variable("y")),
                        new RelationAtom("Q", new Variable("x")).not());
        positives.add(new Object[] {"{R(x,y), ! Q(x)}", target, sigPolicy, vars});

        // two identical elements
        target = new DisjunctiveClause(new RelationAtom("D", new FunctionTerm("a")));
        positives.add(new Object[] {"{D(a),D(a)}", target, sigPolicy, vars});

        // large
        target =
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
        positives.add(new Object[] {"{R(x,y),!Q(x),R(f(x,g(z)),y)}", target, sigPolicy, vars});

        negatives = new ArrayList<>();

        // #######################
        // # signature erroneous #
        // #######################

        // R with different arities
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("R", new Variable("y"), new Variable("x")));
        negatives.add(
                new Object[] {
                    "{R(x),R(y,x)}",
                    target,
                    sigPolicy,
                    vars,
                    ValidityFaultCollection.class,
                    ValidityFaultReason.SAME_RELATION_SYMBOL_WITH_DIFFERENT_ARITIES
                });

        // f with arity 0
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new FunctionTerm("f")),
                        new RelationAtom("Q", new Variable("y"), new Variable("x")));
        negatives.add(
                new Object[] {
                    "{R(f),Q(y,x)}",
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
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "{R(x), Q(y)",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    ParenthesesParsingFaultReason.CLOSING_PAREN_MISSING
                });

        // a closing brace is missing
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "R(x), Q(y)}",
                    target,
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
                    new DisjunctiveClause(),
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // not empty
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "R(x),Q(y)",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.BRACES_AROUND_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "R(x) Q(y)",
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
        target = new DisjunctiveClause(new RelationAtom("R", new FunctionTerm("a")));
        negatives.add(
                new Object[] {
                    "{R(! a)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    PredicateFormulaFaultReason.NEGATION_IN_TERM
                });
        // invalid character (- as symbol)
        // not valid for AbstractCustomizableLexer anymore
        // negatives.add(new Object[] {"{R(x), Q(x - y, x)}", null, sigPolicy, vars,
        //		ParsingFaultCollection.class,
        //		GeneralFormulaFaultReason.INVALID_SYMBOL});
        // illegal formula
        negatives.add(
                new Object[] {
                    "{R(x), R(x) -> Q(x)}",
                    null,
                    sigPolicy,
                    vars,
                    ClauseFaultCollection.class,
                    ClauseFaultReason.CLAUSE_CONTAINS_INVALID_FORMULA
                });
        // missing comma
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "{R(x) Q(y)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING
                });
        // superfluous comma
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "{,R(x), Q(y)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS
                });
        // superfluous comma
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "{,R(x) Q(y),}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_SUPERFLUOUS
                });
        // missing commas I
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")),
                        new RelationAtom("R", new Variable("y")),
                        new RelationAtom("Q", new Variable("x")));
        negatives.add(
                new Object[] {
                    "{R(x) Q(y) , R(y) Q(x)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING
                });
        // missing commas II
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")),
                        new RelationAtom("R", new Variable("y")),
                        new RelationAtom("Q", new Variable("x")));
        negatives.add(
                new Object[] {
                    "{R(x) Q(y) R(y) , Q(x)}",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING
                });
        // missing comma and parentheses
        target =
                new DisjunctiveClause(
                        new RelationAtom("R", new Variable("x")),
                        new RelationAtom("Q", new Variable("y")));
        negatives.add(
                new Object[] {
                    "R(x) Q(y)",
                    target,
                    sigPolicy,
                    vars,
                    ParsingFaultCollection.class,
                    GeneralFormulaSetFaultReason.SEPARATOR_BETWEEN_FORMULAE_MISSING
                });

        // ####################################
        // # allowed free variables erroneous #
        // ####################################

        // x_1 is not allowed
        target = new DisjunctiveClause(new RelationAtom("R", new Variable("x_1")));
        negatives.add(
                new Object[] {
                    "{R(x_1)}",
                    target,
                    sigPolicy,
                    vars,
                    FreeVariablesFaultCollection.class,
                    FreeVariablesFaultReason.SYMBOL_NOT_ALLOWED
                });
    }
}
