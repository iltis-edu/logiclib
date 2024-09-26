package de.tudortmund.cs.iltis.logiclib.modal.assimilation;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FormulaAssimilatorTestExamWiSe1617 {

    static ModalFormulaReader formulaReader =
            new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

    @Parameter(0)
    public ModalFormula mistake;

    @Parameter(1)
    public ModalFormula solution;

    @Test
    public void testSyntaxAssimilation() {
        FormulaAssimilatorTest.testSyntaxAssimilation(mistake, solution);
    }

    // @Test
    public void testSemanticAssimilation() {
        FormulaAssimilatorTest.testSemanticAssimilation(mistake, solution);
    }

    @Parameters(name = "{index}: {0} --> {1}")
    public static Collection<Object[]> data() throws Exception {
        ArrayList<Object[]> data = new ArrayList<>();
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesAL1()));
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesAL2()));
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesAL3()));
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesAL4()));

        data.addAll(Arrays.asList(getExam1WiSe1617MistakesML1()));
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesML2()));
        data.addAll(Arrays.asList(getExam1WiSe1617MistakesML3()));

        data.addAll(Arrays.asList(getExam2WiSe1617MistakesML1()));
        data.addAll(Arrays.asList(getExam2WiSe1617MistakesML2()));
        data.addAll(Arrays.asList(getExam2WiSe1617MistakesML3()));

        return data;
    }

    public static Object[][] getExam1WiSe1617MistakesAL1() throws Exception {
        // either ... or ...
        ModalFormula solution = formulaReader.read("K ↔ ¬T");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("K ↔ T"),
                    formulaReader.read("K∨T"),
                    formulaReader.read("K∧¬T")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam1WiSe1617MistakesAL2() throws Exception {
        // ... only if ...
        ModalFormula solution = formulaReader.read("B → (T∧L)");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("B ↔ (T∧L)"),
                    // formulaReader.read("(B ↔ T)∧L"),
                    // formulaReader.read("(T∧L) → B"),
                    formulaReader.read("B → T"),
                    formulaReader.read("B → (T∨L)"),
                    // formulaReader.read("(B → T)∨L"),

                    // not valid
                    // formulaReader.read("B → T∨L")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    // not
    public static Object[][] getExam1WiSe1617MistakesAL3() throws Exception {
        ModalFormula solution = formulaReader.read("¬M");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("M"),
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam1WiSe1617MistakesAL4() throws Exception {
        // if ... then ...
        ModalFormula solution = formulaReader.read("L → (A∨M)");
        ModalFormula[] mistakes = new ModalFormula[] {formulaReader.read("L → (A ↔ ¬M)")};
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam1WiSe1617MistakesML1() throws Exception {
        /**
         * Wenn es an einer Sehenswürdigkeit weder einen A noch einen I gibt, dann fährt von ihr aus
         * ein Bus zu einer A oder ein Bus zu einer I.
         */
        ModalFormula solution = formulaReader.read("(¬A∧¬I) → ◇(A∨I)");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("◇(¬A∧¬I) → ◇(A∨I)"),
                    formulaReader.read("(¬A∧¬I) → ☐(A∨I)"),
                    formulaReader.read("(A∨I) → ◇(A∨I)"),
                    formulaReader.read("(¬A∧¬I) ∧ ◇(A∨I)"),
                    formulaReader.read("(¬A∧¬I) → (☐A∨☐I)"),
                    formulaReader.read("(¬A∧¬I) → (◇A∨I)")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam1WiSe1617MistakesML2() throws Exception {
        /** Entweder gibt es I, oder alle Busse von dieser Sehenswürdigkeit fahren zu A. */
        ModalFormula solution = formulaReader.read("I ↔ ¬☐A");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("¬I → ☐A"),
                    formulaReader.read("¬◇I ↔ ☐A"),
                    formulaReader.read("I ∨ ☐A"),
                    formulaReader.read("(I∧¬A)∨☐A"),
                    formulaReader.read("I ↔ ◇A"),
                    formulaReader.read("☐(¬I∧☐A)"),
                    formulaReader.read("(I∧¬A)∨☐(¬I∧A)"),
                    formulaReader.read("(I∧¬A)∨☐A"),
                    formulaReader.read("◇(I ↔ ¬☐A)"),
                    formulaReader.read("◇(¬I → ☐A)"),
                    formulaReader.read("◇(¬◇I ↔ ☐A)"),
                    formulaReader.read("◇(I ∨ ☐A)"),
                    formulaReader.read("◇((I∧¬A)∨☐A)"),
                    formulaReader.read("◇(I ↔ ◇A)"),
                    formulaReader.read("◇(☐(¬I∧☐A))"),
                    formulaReader.read("◇((I∧¬A)∨☐(¬I∧A))"),
                    formulaReader.read("◇(I∧¬A)∨☐A")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam1WiSe1617MistakesML3() throws Exception {
        /**
         * Nur dann, wenn von einer Sehenswürdigkeit kein Bus zu einer Sehenswürdigkeit fährt, von
         * der aus alle Busse zu A fahren, gibt es an dieser Sehenswürdigkeit I.
         */
        ModalFormula solution = formulaReader.read("I → ¬◇☐A");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("I → ¬☐☐A"),
                    formulaReader.read("◇I → ¬◇☐A"),
                    formulaReader.read("I ↔ ¬◇☐A"),
                    formulaReader.read("¬◇☐A → I"),
                    formulaReader.read("¬◇(A∧☐A) → I"),
                    formulaReader.read("I → ¬☐A"),
                    formulaReader.read("☐◇A → I"),
                    formulaReader.read("◇☐A → ☐I"),
                    formulaReader.read("☐I → ◇☐¬A"),
                    formulaReader.read("¬◇☐A ↔ I"),
                    formulaReader.read("◇I → ◇☐A"),
                    formulaReader.read("◇☐A → I"),
                    formulaReader.read("☐◇A → ☐I"),
                    formulaReader.read("¬◇(☐A∧I)"),
                    formulaReader.read("I → (¬☐A∧☐☐A)"),
                    formulaReader.read("☐¬(A∨I) → (◇☐A∧◇☐I)"),
                    formulaReader.read("(☐¬(A∨I) → ◇☐A)∧◇☐I"),
                    formulaReader.read("I ↔ ¬☐☐A"),
                    formulaReader.read("I ↔ ☐¬◇☐A"),
                    formulaReader.read("¬☐☐A → I"),
                    formulaReader.read("((S∨A)→(◇(¬S∧¬A)∧◇((¬S∧☐A)∧☐(A∨I))))↔I"),
                    formulaReader.read("(((S∨A)→◇(¬S∧¬A))∧◇((¬S∧☐A)∧☐(A∨I)))↔I")

                    // not valid
                    // formulaReader.read("☐¬(A∨I) → ◇☐A∧◇☐I"),
                    // formulaReader.read("(¬◇I☐A) → I"),
                    // formulaReader.read("((S∨A)→◇(¬S∧¬A)∧◇((¬S∧☐A)∧☐(A∨I)))↔I")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam2WiSe1617MistakesML1() throws Exception {
        /**
         * Nur dann, wenn ein Nutzer einen VIP-Zugang hat, kann er Nutzern mit VIP-Zugang folgen.
         */
        ModalFormula solution = formulaReader.read("◇V → V");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("V → ☐V"),
                    formulaReader.read("☐V → V"),
                    formulaReader.read("V → ◇V"),
                    formulaReader.read("◇V → ☐V"),
                    formulaReader.read("☐V"),
                    formulaReader.read("(V → ◇V) ∧ (¬V → ¬☐V)"),
                    formulaReader.read("V ↔ ◇V"),
                    formulaReader.read("◇O → V"),
                    formulaReader.read("◇☐V → V")

                    // not valid
                    // formulaReader.read("V <- ◇O")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam2WiSe1617MistakesML2() throws Exception {
        /**
         * Wenn ein Nutzer ausschließlich Nutzern mit öffentlichem Profil folgt, dann folgt er min.
         * einem Nutzer, der einem Nutzer mit privatem Profil folgt.
         */
        ModalFormula solution = formulaReader.read("☐V → ◇◇¬O");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("☐O → ☐¬O"),
                    formulaReader.read("☐O → ☐◇¬O"),
                    formulaReader.read("☐O → ◇¬O"),
                    formulaReader.read("O → ◇¬O"),
                    formulaReader.read("◇◇¬O"),
                    formulaReader.read("◇O → (◇V ∧ ¬◇O)"),
                    formulaReader.read("O → ◇V"),
                    formulaReader.read("☐(O → ◇¬O)"),
                    formulaReader.read("☐O → ◇◇V"),
                    formulaReader.read("☐O → (◇O → ◇¬O)"),
                    formulaReader.read("◇O → ◇◇¬O"),
                    formulaReader.read("☐O → ◇◇ O"),
                    formulaReader.read("◇O → ◇¬O"),
                    formulaReader.read("☐O ∧ ☐◇¬O")
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] getExam2WiSe1617MistakesML3() throws Exception {
        /**
         * Ein Nutzer hat ein öffentliches Profil und keinen VIP-Zugang oder er hat genau dann ein
         * privates Profil, wenn alle Nutzer, denen er folgt, einen VIP-Zugang haben.
         */
        ModalFormula solution = formulaReader.read("(O ∧ ¬V) ∨ (¬O ↔ ☐V)");
        ModalFormula[] mistakes =
                new ModalFormula[] {
                    formulaReader.read("(O ∧ ¬V) ∨ (☐V → ¬O)"),
                    formulaReader.read("O ∨ (V → ☐V)"),
                    formulaReader.read("(O ∧ V) ∧ (¬O ↔ ☐V)"),
                    formulaReader.read("(O ∧ ¬V) ∨ (¬O ∧ ☐V)"),
                    formulaReader.read("(O ∧ ¬◇V) ∨ (¬◇O ↔ ☐V)"),
                    formulaReader.read("(O ∧ ¬V) ∧ (¬O ↔ ☐V)"),
                    formulaReader.read("(☐V ↔ ¬O) ∨ (☐V → (O ∧ ¬V))"),
                    formulaReader.read("☐◇V → (O ∧ ¬V)")

                    // not valid
                    // formulaReader.read("☐V → (PA¬◇V) ∨ ☐O"),
                };
        return createTuplesFromSolutionAndMistakes(solution, mistakes);
    }

    public static Object[][] createTuplesFromSolutionAndMistakes(
            ModalFormula solution, ModalFormula[] mistakes) {
        ModalFormula[][] tuples = new ModalFormula[mistakes.length][];
        for (int i = 0; i < mistakes.length; i++) {
            tuples[i] = new ModalFormula[] {mistakes[i], solution};
        }
        return tuples;
    }
}
