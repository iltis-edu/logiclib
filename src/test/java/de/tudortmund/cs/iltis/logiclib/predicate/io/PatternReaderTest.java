package de.tudortmund.cs.iltis.logiclib.predicate.io;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.InfixPredicateReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Term;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.ConjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.DisjunctionPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.EquivalencePattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.ExistentialQuantifierPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.FalsePattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.FunctionTermPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.ImplicationPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.NegationPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.RelationAtomPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.TruePattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.UniversalQuantifierPattern;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern.VariablePattern;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.ExactNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.NamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AlternativePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ComplementPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FlexibleArityForestPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.PredicatePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.BeforeClass;
import org.junit.Test;

public class PatternReaderTest {

    @SuppressWarnings("unchecked")
    @BeforeClass
    public static void initTests() {
        reader = new PatternReader();
        InfixPredicateReaderProperties infixProps = InfixPredicateReaderProperties.createDefault();
        infixProps.addInfixFunctionsSymbols("'+'");
        reader = new PatternReader(PatternReaderProperties.createDefault(infixProps));
        reader.setVerbose(true);
        reader.setTesting(true);
        positives = new ArrayList<>();
        verbose = true;

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
        SignaturePolicy extPolicy =
                SignaturePolicy.TU_DORTMUND_LS1_WITH_INDEX_POLICY
                        .weakenFunctionConstraints(new FunctionSymbol("g", 2, true))
                        .weakenRelationConstraints(new RelationSymbol("S", 2, true));

        positives.add(
                new Object[] {
                    "exists x_1 (R(x_1,x_z) -> 1)",
                    exists(
                            variable(anyName("x_1")),
                            implies(
                                    relation(
                                            anyName("R"),
                                            variable(anyName("x_1")),
                                            variable(anyName("x_z"))),
                                    top())),
                    signature1
                });

        // constant, and, universal quantifier, infix relation, negation
        positives.add(
                new Object[] {
                    "!\"'1t'\" = \"'t2'\" & forall x R(\"'1t'\",x)",
                    and(
                            not(
                                    relation(
                                            exactName("="),
                                            function(exactName("1t")),
                                            function(exactName("t2")))),
                            forall(
                                    variable(anyName("x")),
                                    relation(
                                            anyName("R"),
                                            function(exactName("1t")),
                                            variable(anyName("x"))))),
                    signature1
                });

        // more parentheses than necessary, brackets
        positives.add(
                new Object[] {
                    "(\"'1t'\" = \"'t2'\") & [forall x R(\"'1t'\",x)]",
                    and(
                            relation(
                                    exactName("="),
                                    function(exactName("1t")),
                                    function(exactName("t2"))),
                            forall(
                                    variable(anyName("x")),
                                    relation(
                                            anyName("R"),
                                            function(exactName("1t")),
                                            variable(anyName("x"))))),
                    signature1
                });

        // relation (arity 0), equivalence, bottom
        positives.add(
                new Object[] {"Q <-> 0", equivalent(relation(anyName("Q")), bottom()), signature2});

        // relation (arity 1), function (arity 2), or, unicode literal, superscript
        positives.add(
                new Object[] {
                    "R^1(\u03c0(0_a, 1_b)) | Q",
                    or(
                            relation(
                                    anyName("R^1"),
                                    function(
                                            anyName("\u03c0"),
                                            function(anyName("0_a")),
                                            function(anyName("1_b")))),
                            relation(anyName("Q"))),
                    signature2
                });

        // nested infix function
        positives.add(
                new Object[] {
                    "R^1((x_1 '+' x_2) '+' x)",
                    relation(
                            anyName("R^1"),
                            function(
                                    exactName("'+'"),
                                    function(
                                            exactName("'+'"),
                                            function(anyName("x_1")),
                                            function(anyName("x_2"))),
                                    function(anyName("x")))),
                    signature2
                });

        // mathematics with parentheses ...
        positives.add(
                new Object[] {
                    "(a '+' b) = c | (a '+' b) = d",
                    or(
                            relation(
                                    exactName("="),
                                    function(
                                            exactName("'+'"),
                                            function(anyName("a")),
                                            function(anyName("b"))),
                                    function(anyName("c"))),
                            relation(
                                    exactName("="),
                                    function(
                                            exactName("'+'"),
                                            function(anyName("a")),
                                            function(anyName("b"))),
                                    function(anyName("d")))),
                    signature2
                });

        // three ORs and ANDs
        positives.add(
                new Object[] {
                    "(Q&R^1(a)&R^1(b)) | R^1(c) | Q",
                    or(
                            and(
                                    relation(anyName("Q")),
                                    relation(anyName("R^1"), function(anyName("a"))),
                                    relation(anyName("R^1"), function(anyName("b")))),
                            relation(anyName("R^1"), function(anyName("c"))),
                            relation(anyName("Q"))),
                    signature2
                });

        // backslashes
        positives.add(
                new Object[] {
                    "(R^1('\\phi' '+' '\\psi'))",
                    relation(
                            anyName("R^1"),
                            function(
                                    exactName("'+'"),
                                    function(anyName("\\phi")),
                                    function(anyName("\\psi")))),
                    signature2
                });

        // supplementary unicode characters (Emoji ;-))
        String emoji1 = new String(new int[] {0x01F600}, 0, 1);
        String emoji2 = new String(new int[] {0x01F602}, 0, 1);
        positives.add(
                new Object[] {
                    "(R^1(" + emoji1 + " '+' " + emoji2 + "))",
                    relation(
                            anyName("R^1"),
                            function(
                                    exactName("'+'"),
                                    function(anyName(emoji1)),
                                    function(anyName(emoji2)))),
                    signature2
                });

        // unicode operators
        positives.add(
                new Object[] {
                    "∀y E(y)",
                    forall(variable(anyName("y")), relation(anyName("E"), variable(anyName("y")))),
                    sigPolicy
                });

        // missing space
        positives.add(
                new Object[] {
                    "exists xE(x,t^a)",
                    exists(
                            variable(anyName("x")),
                            relation(
                                    anyName("E"),
                                    variable(anyName("x")),
                                    variable(anyName("t^a")))),
                    sigPolicy
                });

        // missing space
        positives.add(
                new Object[] {
                    "exists xE(x,t^'all')",
                    exists(
                            variable(anyName("x")),
                            relation(
                                    anyName("E"),
                                    variable(anyName("x")),
                                    variable(anyName("t^all")))),
                    sigPolicy
                });

        // missing space
        positives.add(
                new Object[] {
                    "exists x^1_'a'R(x^1_'a')",
                    exists(
                            variable(anyName("x^1_a")),
                            relation(anyName("R"), variable(anyName("x^1_a")))),
                    sigPolicy
                });

        // test custom infix symbols
        positives.add(
                new Object[] {
                    "exists x^1_'12a' x^1_'12a' S (z_2 g b^1_'test')",
                    exists(
                            variable(anyName("x^1_12a")),
                            relation(
                                    exactName("S"),
                                    variable(anyName("x^1_12a")),
                                    function(
                                            exactName("g"),
                                            variable(anyName("z_2")),
                                            function(anyName("b^1_test"))))),
                    extPolicy
                });

        RelationAtomPattern relR = new RelationAtomPattern(anyName("R"));
        RelationAtomPattern relQ = new RelationAtomPattern(anyName("Q"));
        // test AlternativePattern
        positives.add(new Object[] {"R + Q", new AlternativePattern<>(relR, relQ), sigPolicy});

        // This test case currently does not work: the reason is that in utils we fixed the
        // implementation of equals
        // for Tree and Term. This includes a change to PredicatePattern, such that the lambda of a
        // PredicatePattern
        // is included in the equality test. This is necessary because otherwise it would be
        // impossible to add two or more
        // patterns with different predicates to a set. However, this test case (erroneously) relies
        // on the fact that
        // two PredicatePattern objects compare equal regardless of the actual predicate attribute.
        // test MultiConstraintPattern and ContainsDescendantPattern
        //		positives.add(new Object[] {"R($) || contains Q",
        //				new MultiConstraintPattern<>(relation(anyName("R"), anyTerm()),
        //						new ContainsDescendantPattern<>(relQ)), signature1});

        // test names
        positives.add(
                new Object[] {
                    "a@R",
                    new RelationAtomPattern(symbol("a"), anyName("R"), flexibleChildren()),
                    signature1
                });

        // This test case currently does not work: the reason is that in utils we fixed the
        // implementation of equals
        // for Tree and Term. This includes a change to PredicatePattern, such that the lambda of a
        // PredicatePattern
        // is included in the equality test. This is necessary because otherwise it would be
        // impossible to add two or more
        // patterns with different predicates to a set. However, this test case (erroneously) relies
        // on the fact that
        // two PredicatePattern objects compare equal regardless of the actual predicate attribute.
        // test star
        //		positives.add(new Object[] {"* & ...",
        //				and(new RepeatForestPattern<>(anyFormula())), signature1});

        // test read
        positives.add(new Object[] {"#a", new AnyPattern<>(symbol("a")), signature1});
        // test complement
        positives.add(new Object[] {"~R", new ComplementPattern<>(relR), signature1});
    }

    private static PredicatePattern<TermOrFormula> anyTerm() {
        return new PredicatePattern<>(
                forest -> forest.size() == 1 && forest.get(0) instanceof Term);
    }

    private static PredicatePattern<TermOrFormula> anyFormula() {
        return new PredicatePattern<>(forest -> forest.size() == 1 && forest.get(0) != null);
    }

    private static IndexedSymbol symbol(String name) {
        return new IndexedSymbol(name);
    }

    private static ChildrenPattern<TermOrFormula> flexibleChildren(
            TreePattern<TermOrFormula> first, TreePattern<TermOrFormula> second) {

        return new ChildrenPattern<>(new FlexibleArityForestPattern<>(first, second));
    }

    private static ChildrenPattern<TermOrFormula> flexibleChildren(
            TreePattern<TermOrFormula>... patterns) {

        return new ChildrenPattern<>(new FlexibleArityForestPattern<>(patterns));
    }

    private static AnyNamePattern<TermOrFormula, IndexedSymbol> anyName(String name) {
        return new AnyNamePattern<>(new IndexedSymbol(name));
    }

    private static ExactNamePattern<TermOrFormula, IndexedSymbol> exactName(String name) {
        return new ExactNamePattern<>(new IndexedSymbol(name));
    }

    private static ExistentialQuantifierPattern exists(
            VariablePattern var, TreePattern<TermOrFormula> subPattern) {

        return new ExistentialQuantifierPattern(var, subPattern);
    }

    private static UniversalQuantifierPattern forall(
            VariablePattern var, TreePattern<TermOrFormula> subPattern) {

        return new UniversalQuantifierPattern(var, subPattern);
    }

    private static VariablePattern variable(NamePattern<TermOrFormula, IndexedSymbol> name) {
        return new VariablePattern(name);
    }

    private static ImplicationPattern implies(
            TreePattern<TermOrFormula> premise, TreePattern<TermOrFormula> conclusion) {

        return new ImplicationPattern(premise, conclusion);
    }

    private static EquivalencePattern equivalent(
            TreePattern<TermOrFormula> left, TreePattern<TermOrFormula> right) {

        return new EquivalencePattern(left, right);
    }

    private static RelationAtomPattern relation(
            NamePattern<TermOrFormula, IndexedSymbol> name,
            TreePattern<TermOrFormula>... patterns) {

        return new RelationAtomPattern(name, patterns);
    }

    private static FunctionTermPattern function(
            NamePattern<TermOrFormula, IndexedSymbol> name,
            TreePattern<TermOrFormula>... patterns) {

        return new FunctionTermPattern(name, patterns);
    }

    private static TruePattern top() {
        return new TruePattern();
    }

    private static FalsePattern bottom() {
        return new FalsePattern();
    }

    private static ConjunctionPattern and(TreePattern<TermOrFormula>... patterns) {
        return new ConjunctionPattern(patterns);
    }

    private static DisjunctionPattern or(TreePattern<TermOrFormula>... patterns) {
        return new DisjunctionPattern(patterns);
    }

    private static NegationPattern not(TreePattern<TermOrFormula> subPattern) {
        return new NegationPattern(subPattern);
    }

    protected static PatternReader reader;
    // elements should be: new Object[] {input : String,
    //                                   targetObject : ?,
    //                                   signature : SignatureCheckabel,
    //                                   allowedVarSet : Set<VariableSymbol>}
    protected static List<Object[]> positives;
    // elements should be: new Object[] {input : String,
    //                                   targetObject : ?
    //                                   signature : SignatureCheckable,
    //                                   allowedVarSet : Set<VariableSymbol>,
    //                                   faultCollectionClass : Class<? extends FaultCollection>,
    //                                   faultReason : Enum}
    protected static List<Object[]> neutrals;
    // elements should be: new Object[] {input : String,
    //                                   signature : SignatureCheckable,
    //                                   allowedVarSet : Set<VariableSymbol>}
    protected static List<Object[]> negatives;
    protected static boolean verbose = true;
    protected static boolean checkForNegativeTarget = true;

    @SuppressWarnings("unchecked")
    @Test
    public void testPositives() {
        if (positives == null) return;
        int no = 0;
        for (Object[] entry : positives) {
            String testee = (String) entry[0];
            TreePattern<TermOrFormula> targetResult = (TreePattern<TermOrFormula>) entry[1];
            SignatureCheckable signature = (SignatureCheckable) entry[2];
            if (verbose) System.out.println("Positive testee " + ++no + ": " + testee);
            try {
                TreePattern<TermOrFormula> actualResult;
                actualResult = reader.read(testee, signature);
                if (verbose) System.out.println("Result: " + actualResult);
                if (!targetResult.equals(actualResult)) {
                    System.err.println("Result not equal to expected result: " + targetResult);
                    fail(
                            "Result '"
                                    + actualResult
                                    + "' not equal to target result '"
                                    + targetResult
                                    + "'");
                }
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.err.println("Thrown term: " + mapping.getOutput());
                if (verbose) System.err.println("Thrown mapping: " + mapping);
                fail("IncorrectParseInputException thrown for " + testee);
            } catch (Exception e) {
                if (verbose) e.printStackTrace();
                fail("Exception thrown for " + testee + ": " + e);
            } finally {
                if (verbose) System.out.println();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNegatives() {
        if (negatives == null) return;
        int no = 0;
        for (Object[] entry : negatives) {
            String testee = (String) entry[0];
            TreePattern<TermOrFormula> target = (TreePattern<TermOrFormula>) entry[1];
            SignatureCheckable signature = (SignatureCheckable) entry[2];
            if (verbose) System.out.println("Negative testee " + ++no + ": " + testee);
            try {
                TreePattern<TermOrFormula> actualResult;
                actualResult = reader.read(testee, signature);
                if (verbose)
                    System.err.println(
                            "No exception thrown for "
                                    + testee
                                    + "\nInstead output: "
                                    + actualResult);
                fail("No exception thrown for " + testee);
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.out.println("Thrown term: " + mapping.getOutput());
                if (verbose) System.out.println("Thrown mapping: " + mapping);
                if (checkForNegativeTarget && target == null) {
                    if (mapping.getOutput() != null) {
                        if (verbose)
                            System.err.println("Thrown term unequal to expected term: " + target);
                        fail(
                                "Thrown term "
                                        + mapping.getOutput()
                                        + " unequal to expected term: "
                                        + target
                                        + " for "
                                        + testee);
                    }
                } else if (checkForNegativeTarget && !target.equals(mapping.getOutput())) {
                    if (verbose)
                        System.err.println("Thrown term unequal to expected term: " + target);
                    fail(
                            "Thrown term "
                                    + mapping.getOutput()
                                    + " unequal to expected term: "
                                    + target
                                    + " for "
                                    + testee);
                }
            } catch (Exception e) {
                if (verbose) e.printStackTrace();
                fail("Unexpected exception thrown for testee " + testee + ": " + e);
            }
            if (verbose) System.out.println();
        }
    }

    @Test
    public void testNeutrals() {
        if (neutrals == null) return;
        int no = 0;
        for (Object[] entry : neutrals) {
            String testee = (String) entry[0];
            SignatureCheckable signature = (SignatureCheckable) entry[1];
            if (verbose) System.out.println("Neutral testee " + ++no + ": " + testee);
            try {
                TreePattern<TermOrFormula> actualResult;
                actualResult = reader.read(testee, signature);
                if (verbose) System.out.println("Result: " + actualResult);
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.err.println("Thrown term: " + mapping.getOutput());
                if (verbose) System.err.println("Thrown mapping: " + mapping);
            } catch (Exception e) {
                if (verbose) e.printStackTrace();
            } finally {
                if (verbose) System.out.println();
            }
        }
    }
}
