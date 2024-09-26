package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.BreadthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.DepthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.GeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.XmlModalTransformationLoader;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GeneralModalFormulaAssimilatorTest {

    @Test
    public void testMostPopularTransformations() throws IncorrectParseInputException {
        setUp();
        // These are some of the most popular transformations made by students over the whole time
        // of ILTISs existence

        // This is a slightly modified (commuted: original was (A∨C)∧(B∨¬A∨¬B)∧(B∨C)) example that
        // shows that prior satisfyability checks will still be necessary before assimilating
        assertTransformationMixed("(A∨C)∧(B∨C)∧(B∨¬A∨¬B)", "(A∨C)∧(B∨C)", 1, 0);
        assertTransformationMixed("(A∨C)∧(B∨C)∧(B∨¬A∨¬B)", "(A∨C)∧(B∨C)", 0, 2);

        assertTransformationMixed("(¬A∨B)∧((¬B∨C)∧(¬B∨A))", "(¬A∨B)∧(¬B∨C)∧(¬B∨A)", 0, 1);

        assertTransformationMixed("(¬A∨B)∧(¬B∨(C∧A)) ", "(¬A∨B)∧(¬B∨C)∧(¬B∨A)", 0, 2);

        assertTransformationMixed("¬(A∧¬B)∧¬(B∧¬(C∧A))", "(¬A∨B)∧(¬B∨(C∧A))", 0, 1);

        showResults();
    }

    // all tests pass with no problems, but they take a lot of time, so they are commented out to
    // save on such time when running maven install etc.
    // @Test
    public void testRealData() throws IncorrectParseInputException {
        setUp();
        // previously recognized
        assertTransformationMixed("C∨(A∧¬B)", "¬(C→¬(A∧¬B))", 1, 1);
        assertTransformationMixed("C∨(¬A∨B)", "¬(C→¬(¬A∨B))", 1, 1);
        assertTransformationMixed("¬C→¬¬(A∧¬B)", "¬(C→¬(A∧¬B))", 1, 2);
        assertTransformationMixed("¬(¬C∨(A∧¬B))", "¬(C→¬(A∧¬B))", 1, 1);
        assertTransformationMixed("C∧(¬¬A∧B)", "¬¬C∧¬(¬A∨B)", 1, 2);
        assertTransformationMixed("(B∨(C∧A))", "¬(B∧¬(C∧A))", 1, 1);
        assertTransformationMixed("(¬B∨(C∨A))", "¬(B∧¬(C∧A))", 1, 2);
        assertTransformationMixed("A∧¬C", "¬(¬A∧C)", 1, 1);
        assertTransformationMixed("(A∧B)∨(¬A∧B)", "A↔B", 1, 1);
        assertTransformationMixed("((¬B∧C)∨(¬B∧A))", "(¬B∨(C∧A))", 1, 1);

        showResults();
    }

    @Test
    public void testOnlyBuggyTransformations() throws IncorrectParseInputException {
        setUp();
        // 1 transformation
        assertTransformationOnlyBuggy("A→B", "B→A", 1);
        assertTransformationOnlyBuggy("¬A∧B", "¬(A∧B)", 1);
        assertTransformationOnlyBuggy("(¬T∧¬W)→K", "(¬T∧¬W)→¬K", 1);
        assertTransformationOnlyBuggy("A ∨ B ∨ C", "A ∨ B ∨ C ∨ D", 1);
        assertTransformationOnlyBuggy("A → B", "A ↔ B", 1);

        // 2 transformations
        assertTransformationOnlyBuggy("(A → B) ∧ ¬C", "(A ↔ B) ∧ C", 2);
        assertTransformationOnlyBuggy("◇(A ∧ ¬B)", "◇A ∧ ¬◇B", 2);
        assertTransformationOnlyBuggy("◇A ∧ ¬B", "A → ¬B", 2);
        assertTransformationOnlyBuggy("◇☐A", "☐◇A", 2);
        assertTransformationOnlyBuggy("◇A ∧ ¬B", "A → ¬B", 2);

        // 3 transformations

        showResults();
    }

    @Test
    public void testOnlyEquivalenceTransformations() throws IncorrectParseInputException {
        setUp();
        // 1 transformation
        assertTransformationOnlyEquivalent("A→B", "¬A∨B", 1);
        assertTransformationOnlyEquivalent("Z∨¬(X ∨ Y)", "Z∨(¬X ∧ ¬Y)", 1);
        assertTransformationOnlyEquivalent("☐⊥", "☐¬⊤", 1);

        // 2 transformations
        assertTransformationOnlyEquivalent("(K∧S)→P", "¬P→(¬K∨¬S)", 2);
        assertTransformationOnlyEquivalent("☐¬(X ∨ Y)", "(☐¬X ∧ ☐¬Y)", 2);
        assertTransformationOnlyEquivalent("¬(X ∨ (¬Y ∧ ◇Z))", "¬(X∨¬Y) ∨ ¬(X∨◇Z)", 2);

        // 3 transformations
        assertTransformationOnlyEquivalent("¬(A ∨ ¬B ∨ ◇C)", "¬A ∧ B ∧ ☐¬C", 3);
        assertTransformationOnlyEquivalent("¬☐(A ∧ ¬B ∧ ☐C)", "◇¬A ∨ ◇¬¬B ∨ ◇¬☐C", 3);
        assertTransformationOnlyEquivalent("◇(X ∨ ¬Y) ∧ Z", "(◇X ∧ Z) ∨ (¬☐Y ∧ Z)", 3);

        showResults();
    }

    // all tests pass with no problems, but they take a lot of time, so they are commented out to
    // save on such time when running maven install etc.
    // @Test
    public void testMixedTransformations() throws IncorrectParseInputException {
        setUp();
        // 1 buggy + 1 equivalence transformations
        assertTransformationMixed("(A→B) ∨ ¬¬C", "(B→A)∨ C", 1, 1);
        assertTransformationMixed("(A→B) ∨ ¬C", "(¬A∨B)∨ ¬¬C", 1, 1);
        assertTransformationMixed("◇¬(A ∨ B)", "◇¬A ∧ ◇¬B", 1, 1);

        // 2 buggy + 1 equivalence transformations
        assertTransformationMixed("◇(A ∨ B)", "☐A ∧ ☐B", 2, 1);
        assertTransformationMixed("A ∧ ◇¬B", "¬A → ¬☐B ", 2, 1);
        assertTransformationMixed("(☐A ∧ ☐B) ∨¬C", "(◇A ∨ ¬C ) ∧ (☐B ∧ ¬C)", 2, 1);

        showResults();
    }

    @Test
    public void testLimitNotReachedTransformations() throws IncorrectParseInputException {
        setUp();
        // 1 step needed
        assertTransformationMixed("Z∨¬(X ∨ Y)", "Z∨(¬X ∧ ¬Y)", 1, 3);
        assertTransformationMixed("¬A∧B", "¬(A∧B)", 1, 3);

        // 2 step needed
        assertTransformationMixed("(K∧S)→P", "¬P→(¬K∨¬S)", 1, 3);
        assertTransformationMixed("(A → B) ∧ ¬C", "(A ↔ B) ∧ C", 2, 3);
        assertTransformationMixed("◇A ∧ ¬B", "A → ¬B", 2, 3);

        // 3 step needed (2 when combing DeMorgan + double negation into one)
        assertTransformationMixed("¬(A ∨ ¬B ∨ ◇C)", "¬A ∧ B ∧ ☐¬C", 1, 3);

        showResults();
    }

    @Test
    public void testSemanticAssimilator() throws IncorrectParseInputException {
        // The semantic assimilation is incomparable to any syntactic approach,
        // since it cannot be used to determine complete sequences of transformations including
        // equivalence transformations.
        // However, if it can detect buggy transformations while conceptually allowing an arbitrary
        // number
        // of equivalence transformations to be applied after the buggy one.
        setUp();

        assertSemantic("(A→B) ∨ ¬¬C", "(B→A)∨ C", 1, 1, true);
        assertSemantic("(A→B) ∨ ¬C", "(¬A∨B)∨ ¬¬C", 1, 1, true);

        assertSemantic("◇A ∧ ¬B", "A → ¬B", 2, 2, true);
        assertSemantic("(A → B) ∧ ¬¬C", "(A ↔ B) ∧ ¬C", 2, 2, true);

        showResults();
    }

    private void assertTransformationOnlyBuggy(String source, String target, int maxSteps)
            throws IncorrectParseInputException {
        assertTransformation(source, target, maxSteps, 0, maxSteps, true);
    }

    private void assertTransformationOnlyEquivalent(String source, String target, int maxSteps)
            throws IncorrectParseInputException {
        assertTransformation(source, target, 0, maxSteps, maxSteps, true);
    }

    private void assertTransformationMixed(
            String source,
            String target,
            int maxTransformationCountBuggy,
            int maxTransformationCountEquivalent)
            throws IncorrectParseInputException {
        assertTransformation(
                source,
                target,
                maxTransformationCountBuggy,
                maxTransformationCountEquivalent,
                maxTransformationCountBuggy + maxTransformationCountEquivalent,
                true);
    }

    private void assertTransformation(
            GeneralFormulaAssimilator<ModalFormula> assimilator,
            ModalFormula sourceFormula,
            ModalFormula targetFormula,
            int maxTransformationCountBuggy,
            int maxTransformationCountEquivalent,
            int expectedTransformationCount,
            boolean expectedSuccess,
            String formattedInputData)
            throws IncorrectParseInputException {
        if (assimilator instanceof DepthFirstGeneralFormulaAssimilator) {
            formattedInputData = "DepthFirstAssimilation, " + formattedInputData;
        } else {
            formattedInputData = "BreadthFirstAssimilation, " + formattedInputData;
        }

        try {
            long start = System.currentTimeMillis();
            IterativeTransformation<ModalFormula> transformation =
                    assimilator.assimilate(
                            sourceFormula,
                            maxTransformationCountBuggy,
                            maxTransformationCountEquivalent);
            long timePassed = System.currentTimeMillis() - start;

            // test priority queue instead of FIFA
            if (assimilator instanceof BreadthFirstGeneralFormulaAssimilator) {
                start = System.currentTimeMillis();
                IterativeTransformation<ModalFormula> transformationPriority =
                        ((BreadthFirstGeneralFormulaAssimilator<ModalFormula>) assimilator)
                                .assimilateWithSizePriorityQueue(
                                        sourceFormula,
                                        maxTransformationCountBuggy,
                                        maxTransformationCountEquivalent);
                long timePassedPriority = System.currentTimeMillis() - start;

                if (transformationPriority != null) {
                    System.out.println(
                            ""
                                    + timePassedPriority
                                    + " ms passed on priority "
                                    + formattedInputData
                                    + ", found sequence of "
                                    + transformationPriority.size()
                                    + " transformations");
                    priorityBreadthManager.incrementAssimilation();
                    priorityBreadthManager.addTime(timePassedPriority);
                    priorityBreadthManager.addSteps(transformationPriority.size());
                } else {
                    System.out.println(
                            ""
                                    + timePassedPriority
                                    + " ms passed on "
                                    + formattedInputData
                                    + ", found sequence of 0 transformations");
                }
            }

            if (transformation != null) {
                System.out.println(
                        ""
                                + timePassed
                                + " ms passed on "
                                + formattedInputData
                                + ", found sequence of "
                                + transformation.size()
                                + " transformations");
                if (assimilator instanceof DepthFirstGeneralFormulaAssimilator) {
                    depthManager.incrementAssimilation();
                    depthManager.addTime(timePassed);
                    depthManager.addSteps(transformation.size());
                } else {
                    breadthManager.incrementAssimilation();
                    breadthManager.addTime(timePassed);
                    breadthManager.addSteps(transformation.size());
                }
            } else {
                System.out.println(
                        ""
                                + timePassed
                                + " ms passed on "
                                + formattedInputData
                                + ", found sequence of 0 transformations");
            }

            if (expectedSuccess) {
                assert transformation != null
                        : "Expected: not null, got: null, at: " + formattedInputData;
                assert transformation.size() <= expectedTransformationCount
                        : "Expected up to "
                                + expectedTransformationCount
                                + " Transformations, got: "
                                + transformation.size()
                                + ", at: "
                                + formattedInputData;
                assert transformation.isApplicable(sourceFormula)
                        : "Expected: applicable, got: not applicable, at: " + formattedInputData;
                assert transformation.apply(sourceFormula).equals(targetFormula)
                        : "Expected: equivalent formulas, got: not equivalent, at: "
                                + formattedInputData;
            } else {
                if (transformation == null) {
                    return;
                } else {
                    assert false : "Expected failure, got success at: " + formattedInputData;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception at " + formattedInputData);
            e.printStackTrace();
        }
    }

    private void assertTransformation(
            String source,
            String target,
            int maxTransformationCountBuggy,
            int maxTransformationCountEquivalent,
            int expectedTransformationCount,
            boolean expectedSuccess)
            throws IncorrectParseInputException {
        ModalFormula sourceFormula = ModalFormula.parse(source);
        ModalFormula targetFormula = ModalFormula.parse(target);

        String formattedInputData =
                "Source: "
                        + source
                        + ", Target: "
                        + target
                        + ", maxCountBuggy: "
                        + maxTransformationCountBuggy
                        + ", maxCountEquivalent: "
                        + maxTransformationCountEquivalent
                        + ", maxCount: "
                        + expectedTransformationCount
                        + ", expected Success: "
                        + expectedSuccess;

        BreadthFirstGeneralFormulaAssimilator<ModalFormula> breadthFirstGeneralFormulaAssimilator =
                new BreadthFirstModalAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations, false);

        DepthFirstGeneralFormulaAssimilator<ModalFormula> depthFirstGeneralFormulaAssimilator =
                new DepthFirstModalAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations);

        assertTransformation(
                breadthFirstGeneralFormulaAssimilator,
                sourceFormula,
                targetFormula,
                maxTransformationCountBuggy,
                maxTransformationCountEquivalent,
                expectedTransformationCount,
                expectedSuccess,
                formattedInputData);

        // Since we currently do not plan on using a depth first approach in the future and because
        // of the runtime of the tests, we do not test it here.
        // assertTransformation(depthFirstGeneralFormulaAssimilator, sourceFormula, targetFormula,
        // maxTransformationCountBuggy, maxTransformationCountEquivalent,
        // expectedTransformationCount, expectedSuccess, formattedInputData);
    }

    private void assertSemantic(
            String source,
            String target,
            int maxTransformationCountBuggy,
            int expectedTransformationCount,
            boolean expectedSuccess)
            throws IncorrectParseInputException {
        ModalFormula sourceFormula = ModalFormula.parse(source);
        ModalFormula targetFormula = ModalFormula.parse(target);

        String formattedInputData =
                "Semantic breadth first assimilation, Source: "
                        + source
                        + ", Target: "
                        + target
                        + ", maxCountBuggy: "
                        + maxTransformationCountBuggy
                        + ", expectedCount: "
                        + expectedTransformationCount
                        + ", expected Success: "
                        + expectedSuccess;

        BreadthFirstGeneralFormulaAssimilator<ModalFormula> assimilator =
                new BreadthFirstModalAssimilator(
                        targetFormula, new ArrayList<>(), buggyTransformations, true);

        try {
            long start = System.currentTimeMillis();
            IterativeTransformation<ModalFormula> transformation =
                    assimilator.assimilate(sourceFormula, maxTransformationCountBuggy, 0);
            long timePassed = System.currentTimeMillis() - start;

            if (transformation != null) {
                System.out.println(
                        ""
                                + timePassed
                                + " ms passed on priority "
                                + formattedInputData
                                + ", found sequence of "
                                + transformation.size()
                                + " transformations");
                semanticBreadthManager.incrementAssimilation();
                semanticBreadthManager.addTime(timePassed);
                semanticBreadthManager.addSteps(transformation.size());
            } else {
                System.out.println(
                        ""
                                + timePassed
                                + " ms passed on "
                                + formattedInputData
                                + ", found sequence of 0 transformations");
            }

            if (expectedSuccess) {
                assert transformation != null
                        : "Expected: not null, got: null, at: " + formattedInputData;
                assert transformation.size() <= expectedTransformationCount
                        : "Expected up to "
                                + expectedTransformationCount
                                + " Transformations, got: "
                                + transformation.size()
                                + ", at: "
                                + formattedInputData;
            } else {
                if (transformation == null) {
                    return;
                } else {
                    assert false : "Expected failure, got success at: " + formattedInputData;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception at " + formattedInputData);
            e.printStackTrace();
        }
    }

    private List<Transformation<ModalFormula>> equivalenceTransformations, buggyTransformations;

    private void getAllBuggyTransformations(TransformationRegistry<ModalFormula> registry) {
        buggyTransformations =
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/buggyTransformations.xml",
                                registry)
                        .getUnadornedTransformations();
    }

    private void getAllEquivalenceTransformations(TransformationRegistry<ModalFormula> registry) {
        equivalenceTransformations = new ArrayList<>();
        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                                registry)
                        .getUnadornedTransformations());
        // not needed when comparing formulae on with EqualityModuloCommutativityAndIdempotence
        // equalitytester
        /*
        equivalenceTransformations.addAll(
                XmlTransformationsLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",registry).getUnadornedTransformations()
        );*/

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                                registry)
                        .getUnadornedTransformations());

        equivalenceTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/fixedCombinations.xml",
                                registry)
                        .getUnadornedTransformations());
    }

    private EfficiencyStatisticsManager priorityBreadthManager,
            breadthManager,
            depthManager,
            semanticBreadthManager;

    private void setUp() {
        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<ModalFormula>();
        getAllEquivalenceTransformations(registry);
        getAllBuggyTransformations(registry);

        priorityBreadthManager = new EfficiencyStatisticsManager();
        breadthManager = new EfficiencyStatisticsManager();
        depthManager = new EfficiencyStatisticsManager();
        semanticBreadthManager = new EfficiencyStatisticsManager();
    }

    private void showResults() {
        System.out.println("Results:");
        System.out.println("------------------------------------------------------------");
        System.out.println("Priority breadth first assimilation:");
        System.out.println(priorityBreadthManager.toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Breadth first assimilation:");
        System.out.println(breadthManager.toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Depth first assimilation:");
        System.out.println(depthManager.toString());
        System.out.println("------------------------------------------------------------");
        System.out.println("Semantic Breadth first assimilation:");
        System.out.println(semanticBreadthManager.toString());
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
