package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy.FormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.legacy.TypicalMistakesTransformations;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.Transformation;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TypicalMistakesTest {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Test
    public void testNegationMistakes() throws Exception {
        findMistakes("A", "¬A");
        findMistakes("¬A", "A");
    }

    @Test
    public void testConjunctionMistakes() throws Exception {
        findMistakes("A↔B", "A∧B");
        findMistakes("A→B", "A∧B");
        findMistakes("¬A∧¬B", "A∧B");
        findMistakes("A∧B", "¬A∧¬B");
        findMistakes("A∨B", "A∧B");
    }

    @Test
    public void testDisjunctionMistakes() throws Exception {
        findMistakes("A↔B", "A∨B");
        findMistakes("A→B", "A∨B");
        findMistakes("A∧B", "A∨B");

        // exclusive disjunction
        findMistakes("A↔¬B", "A∨B");
        findMistakes("¬A↔B", "A∨B");
        findMistakes("A∨B", "A↔¬B");
        findMistakes("A∨B", "¬A↔B");
    }

    @Test
    public void testImplicationMistakes() throws Exception {
        findMistakes("B→A", "A→B");
        findMistakes("A↔B", "A→B");
        findMistakes("A∧B", "A→B");
    }

    @Test
    public void testEquivalenceMistakes() throws Exception {
        findMistakes("A→B", "A↔B");
        findMistakes("A∧B", "A↔B");
    }

    public static void findMistakes(String buggyFormula, String correctFormula) throws Exception {
        findMistakes(formulaReader.read(buggyFormula), formulaReader.read(correctFormula));
    }

    public static void findMistakes(ModalFormula buggyFormula, ModalFormula correctFormula) {
        FormulaAssimilator syntaxAssimilator =
                FormulaAssimilator.syntax(
                        correctFormula, CORRECTIONS, MAX_EQUIVALENCE_TRANSFORMATIONS);
        FormulaAssimilatorTest.checkAssimilationResult(
                syntaxAssimilator,
                buggyFormula,
                correctFormula,
                MAX_MISTAKE_TRANSFORMATIONS,
                "Syntax");

        FormulaAssimilator semanticAssimilator =
                FormulaAssimilator.semantic(correctFormula, CORRECTIONS);
        FormulaAssimilatorTest.checkAssimilationResult(
                semanticAssimilator,
                buggyFormula,
                correctFormula,
                MAX_MISTAKE_TRANSFORMATIONS,
                "Semantic");
    }

    private static int MAX_MISTAKE_TRANSFORMATIONS = 1;
    private static int MAX_EQUIVALENCE_TRANSFORMATIONS = 2;

    private static List<Transformation> CORRECTIONS =
            Arrays.asList(
                    TypicalMistakesTransformations.getNegationMissing(),
                    TypicalMistakesTransformations.getNegationTooMuch(),
                    TypicalMistakesTransformations.getNeitherNorInsteadOfConjunction(),
                    TypicalMistakesTransformations.getConjunctionInsteadOfNeitherNor(),
                    TypicalMistakesTransformations.getDisjunctionInsteadOfConjunction(),
                    TypicalMistakesTransformations.getImplicationInsteadOfConjunction(),
                    TypicalMistakesTransformations.getEquivalenceInsteadOfConjunction(),
                    TypicalMistakesTransformations.getDisjunctionInsteadOfExclusiveDisjunction(),
                    TypicalMistakesTransformations.getExclusiveDisjunctionInsteadOfDisjunction(),
                    TypicalMistakesTransformations.getConjunctionInsteadOfDisjunction(),
                    TypicalMistakesTransformations.getImplicationInsteadOfDisjunction(),
                    TypicalMistakesTransformations.getEquivalenceInsteadOfDisjunction(),
                    TypicalMistakesTransformations.getImplicationWrongDirection(),
                    TypicalMistakesTransformations.getEquivalenceInsteadOfImplication(),
                    TypicalMistakesTransformations.getConjunctionInsteadOfImplication(),
                    TypicalMistakesTransformations.getDisjunctionInsteadOfImplication(),
                    TypicalMistakesTransformations.getImplicationInsteadOfEquivalence(),
                    TypicalMistakesTransformations.getConjunctionInsteadOfEquivalence(),
                    TypicalMistakesTransformations.getDisjunctionInsteadOfEquivalence());
}
