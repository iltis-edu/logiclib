package de.tudortmund.cs.iltis.logiclib.predicate.assimilation;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.BreadthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.XmlPredicateTransformationLoader;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class PredicateAssimilatorTest {

    @Test
    public void test() {
        setUp();

        assertTransformation(
                "exists y (Y(y) & !forall x X(x,y))",
                "exists y (Y(y) & exists x !X(x,y))",
                1,
                1,
                1);
        assertTransformation(
                "forall x X(x,y) & forall x Y(x)", "forall x (X(x,y) & Y(x))", 1, 1, 1);
        assertTransformation(
                "forall x X(x,y) | forall x Y(x)", "forall x (X(x,y) | Y(x))", 1, 1, 1);
        assertTransformation("forall x X(x,y)", "exists x X(x,y)", 1, 1, 1);
        assertTransformation("(X(x,y) <-> Y(x,y))", "X(x,y)", 1, 0, 1);
        assertTransformation("X(x,y)", "X(x,y) -> Y(z)", 1, 1, 1);
        assertTransformation(
                "X(x,y) & (forall x (X(x,z) | Y(y) | Z(z)) )",
                "X(x,y) & (forall x X(x,z) | Y(y) | Z(z))",
                0,
                1,
                1);
        assertTransformation("X(x)", "forall x X(x)", 1, 0, 1);
        assertTransformation("X(x) & X(y)", "forall y X(x) & X(y)", 0, 1, 1);
        assertTransformation("X(x) & forall y Y(y,x)", "forall y (X(x) & Y(y,x))", 0, 2, 2);
        assertTransformation("(¬X∧Y)∨((¬Y∧Z)∨(¬Y∧X))", "(¬X∧Y)∨(¬Y∧Z)∨(¬Y∧X)", 0, 1, 1);

        showResults();
    }

    private void assertTransformation(
            String source, String target, int maxBuggy, int maxEquivalent, int expectedCount) {
        TermOrFormula sourceFormula = Formula.parse(source);
        TermOrFormula targetFormula = Formula.parse(target);

        String formattedInputData =
                "Source: "
                        + sourceFormula
                        + ", Target: "
                        + targetFormula
                        + ", maxBuggy: "
                        + maxBuggy
                        + ", maxEquivalent: "
                        + maxEquivalent
                        + ", expectedCount: "
                        + expectedCount;

        BreadthFirstGeneralFormulaAssimilator<TermOrFormula> assimilator =
                new BreadthFirstPredicateAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations);

        try {
            long start = System.currentTimeMillis();
            IterativeTransformation<TermOrFormula> transformation =
                    assimilator.assimilate(sourceFormula, maxBuggy, maxEquivalent);
            long timePassed = System.currentTimeMillis() - start;
            assert transformation != null
                    : "Expected: not null, got: null, at: " + formattedInputData;
            assert transformation.size() <= expectedCount
                    : "Expected up to "
                            + expectedCount
                            + " Transformations, got: "
                            + transformation.size()
                            + ", at: "
                            + formattedInputData;
            assert transformation.isApplicable(sourceFormula)
                    : "Expected: applicable, got: not applicable, at: " + formattedInputData;
            assert transformation.apply(sourceFormula).equals(targetFormula)
                    : "Expected: equivalent formulas, got: not equivalent, at: "
                            + formattedInputData;
            manager.incrementAssimilation();
            manager.addTime(timePassed);
            manager.addSteps(transformation.size());
            System.out.println(
                    ""
                            + timePassed
                            + " ms passed on default assimilation on ("
                            + formattedInputData
                            + "), found sequence of "
                            + transformation.size()
                            + " transformations");

            // priority
            start = System.currentTimeMillis();
            transformation =
                    assimilator.assimilateWithSizePriorityQueue(
                            sourceFormula, maxBuggy, maxEquivalent);
            timePassed = System.currentTimeMillis() - start;
            assert transformation != null
                    : "Expected: not null, got: null, at: " + formattedInputData;
            assert transformation.size() <= expectedCount
                    : "Expected up to "
                            + expectedCount
                            + " Transformations, got: "
                            + transformation.size()
                            + ", at: "
                            + formattedInputData;
            assert transformation.isApplicable(sourceFormula)
                    : "Expected: applicable, got: not applicable, at: " + formattedInputData;
            assert transformation.apply(sourceFormula).equals(targetFormula)
                    : "Expected: equivalent formulas, got: not equivalent, at: "
                            + formattedInputData;
            priorityManager.incrementAssimilation();
            priorityManager.addTime(timePassed);
            priorityManager.addSteps(transformation.size());
            System.out.println(
                    ""
                            + timePassed
                            + " ms passed on priority assimilation on ("
                            + formattedInputData
                            + "), found sequence of "
                            + transformation.size()
                            + " transformations");

        } catch (Exception e) {
            System.out.println("Exception at " + formattedInputData);
            e.printStackTrace();
        }
    }

    private List<Transformation<TermOrFormula>> equivalenceTransformations, buggyTransformations;

    private void getAllBuggyTransformations(TransformationRegistry<TermOrFormula> registry) {
        buggyTransformations =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateBuggyTransformations.xml",
                                registry)
                        .getUnadornedTransformations();
        System.out.println("size buggy: " + buggyTransformations.size());
    }

    private void getAllEquivalenceTransformations(TransformationRegistry<TermOrFormula> registry) {
        equivalenceTransformations = new ArrayList<>();
        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateEquivalence.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateConstants.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDistribution.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateFixedCombinations.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateQuantifiers.xml",
                                registry)
                        .getUnadornedTransformations());

        System.out.println("size equivalent: " + equivalenceTransformations.size());
    }

    private EfficiencyStatisticsManager manager, priorityManager;

    private void setUp() {
        TransformationRegistry<TermOrFormula> registry =
                new TransformationRegistry<TermOrFormula>();
        getAllBuggyTransformations(registry);
        getAllEquivalenceTransformations(registry);

        manager = new EfficiencyStatisticsManager();
        priorityManager = new EfficiencyStatisticsManager();
    }

    private void showResults() {
        System.out.println("Results:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Priority breadth first assimilation:");
        System.out.println(priorityManager.toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Breadth first assimilation:");
        System.out.println(manager.toString());
    }

    private class EfficiencyStatisticsManager {

        private long timeTaken;
        private int stepsTaken, assimilations;

        EfficiencyStatisticsManager() {
            this.stepsTaken = 0;
            this.timeTaken = 0;
            this.assimilations = 0;
        }

        public String toString() {
            if (assimilations == 0) {
                return "no assimilations";
            }

            return "assimilations done: "
                    + assimilations
                    + ", steps taken: "
                    + stepsTaken
                    + ", time taken: "
                    + timeTaken
                    + " ms, \naverage time per assimilation: "
                    + timeTaken / assimilations
                    + ", average time per step: "
                    + timeTaken / stepsTaken;
        }

        public void addTime(long timeTaken) {
            this.timeTaken += timeTaken;
        }

        public void addSteps(int stepsTaken) {
            this.stepsTaken += stepsTaken;
        }

        public void incrementAssimilation() {
            this.assimilations++;
        }
    }
}
