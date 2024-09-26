package de.tudortmund.cs.iltis.logiclib.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import org.junit.Test;

public class NormalFormsTest {

    @Test
    public void testNormalforms() {
        ModalFormulaReader reader =
                new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

        ModalFormula cnfComplete = reader.read("!C & (A | B) & !A");
        ModalFormula dnfComplete = reader.read("!C | (A & B) | !A");
        ModalFormula literal = reader.read("!A");
        ModalFormula constant = reader.read("1");
        ModalFormula cnfSingleClause = reader.read(" 0 & !A & B");
        ModalFormula dnfSingleClause = reader.read(" 1 | !A | B");
        ModalFormula withDoubleNegationCnf = reader.read("!!C  & (A | B) & !A");
        ModalFormula withDoubleNegationDnf = reader.read(" !!C  | (A & B) | !A");
        ModalFormula cnfCompleteExtraParentheses = reader.read("( !C | (A | B)) & (A | B) & !A");
        ModalFormula dnfCompleteExtraParentheses = reader.read("( !C & (A & B)) | (A & B) | !A");
        ModalFormula singleClause = reader.read("A | !B");
        ModalFormula withEquivalence = reader.read("A <-> (!B | C)");
        ModalFormula withImplication = reader.read("!A -> (B & !C)");

        // CNF and DNF
        assert cnfComplete.isInConjunctiveNormalform() : cnfComplete + " is not in CNF";
        assert dnfComplete.isInDisjunctiveNormalform() : dnfComplete + " is not in DNF";
        assert literal.isInConjunctiveNormalform() : literal + " is not in CNF";
        assert literal.isInDisjunctiveNormalform() : literal + " is not in DNF";
        assert constant.isInConjunctiveNormalform() : constant + " is not in CNF";
        assert constant.isInDisjunctiveNormalform() : constant + " is not in DNF";
        assert cnfSingleClause.isInConjunctiveNormalform() : cnfSingleClause + " is not in CNF";
        assert dnfSingleClause.isInDisjunctiveNormalform() : dnfSingleClause + " is not in DNF";

        assert !withDoubleNegationCnf.isInConjunctiveNormalform()
                : withDoubleNegationCnf + " is in CNF";
        assert !withDoubleNegationDnf.isInDisjunctiveNormalform()
                : withDoubleNegationDnf + " is in DNF";
        assert !cnfCompleteExtraParentheses.isInConjunctiveNormalform()
                : cnfCompleteExtraParentheses + " is in CNF";
        assert !dnfCompleteExtraParentheses.isInDisjunctiveNormalform()
                : dnfCompleteExtraParentheses + " is in DNF";

        assert !dnfComplete.isInConjunctiveNormalform() : dnfComplete + " is in CNF";
        assert !cnfComplete.isInDisjunctiveNormalform() : cnfComplete + " is in DNF";
        assert !cnfSingleClause.isInDisjunctiveNormalform() : cnfSingleClause + " is in DNF";
        assert !dnfSingleClause.isInConjunctiveNormalform() : dnfSingleClause + " is in CNF";

        assert !cnfCompleteExtraParentheses.isInDisjunctiveNormalform()
                : cnfCompleteExtraParentheses + " is in DNF";
        assert !dnfCompleteExtraParentheses.isInConjunctiveNormalform()
                : dnfCompleteExtraParentheses + " is in CNF";

        assert singleClause.isInConjunctiveNormalform() : singleClause + " is not in CNF";
        assert singleClause.isInDisjunctiveNormalform() : singleClause + " is not in DNF";

        // NNF
        assert cnfComplete.isInNegationNormalform() : cnfComplete + " is not in NNF";
        assert dnfComplete.isInNegationNormalform() : dnfComplete + " is not in NNF";
        assert literal.isInNegationNormalform() : literal + " is not in NNF";
        assert constant.isInNegationNormalform() : constant + " is not in NNF";
        assert cnfSingleClause.isInNegationNormalform() : cnfSingleClause + " is not in NNF";
        assert dnfSingleClause.isInNegationNormalform() : dnfSingleClause + " is not in NNF";
        assert cnfCompleteExtraParentheses.isInNegationNormalform()
                : cnfCompleteExtraParentheses + " is not in NNF";
        assert dnfCompleteExtraParentheses.isInNegationNormalform()
                : dnfCompleteExtraParentheses + " is not in NNF";

        assert !withDoubleNegationCnf.isInNegationNormalform()
                : withDoubleNegationCnf + " is in NNF";
        assert !withDoubleNegationDnf.isInNegationNormalform()
                : withDoubleNegationDnf + " is in NNF";

        assert !withEquivalence.isInNegationNormalform() : withEquivalence + " is in NNF";
        assert !withImplication.isInNegationNormalform() : withImplication + " is in NNF";
    }

    @Test
    public void testHornForms() {
        ModalFormulaReader reader =
                new ModalFormulaReader(ModalReaderProperties.createDefaultWithLatex());

        ModalFormula disjunctiveHorn = reader.read("(¬B ∨ A) ∧ B ∧ ¬A");
        ModalFormula disjunctiveHornTooManyPositiveLiterals = reader.read("(¬B ∨ A ∨ C) ∧ B ∧ ¬A");
        ModalFormula implicativeHorn = reader.read("(B → A) ∧ B ∧ (A → ⊥)");
        ModalFormula implicativeHornWithTopLeft = reader.read("(B → A) ∧ B ∧ (A → ⊥) ∧ (1 → C)");

        assert disjunctiveHorn.isDisjunctiveHornFormula()
                : disjunctiveHorn + " is not in disjunctive horn form";
        assert !disjunctiveHorn.isImplicativeHornFormula()
                : disjunctiveHorn + " is implicative horn formula";
        assert !disjunctiveHorn.isImplicativeHornFormulaWithTopLeft()
                : disjunctiveHorn + " is implicative horn formula with top left";

        assert !disjunctiveHornTooManyPositiveLiterals.isDisjunctiveHornFormula()
                : disjunctiveHornTooManyPositiveLiterals + " is in disjunctive horn form";

        assert implicativeHorn.isImplicativeHornFormula()
                : implicativeHorn + " is not implicative horn formula";
        assert !implicativeHorn.isDisjunctiveHornFormula()
                : implicativeHorn + " is disjunctive horn formula";

        assert implicativeHornWithTopLeft.isImplicativeHornFormulaWithTopLeft()
                : implicativeHornWithTopLeft + " is not implicative horn formula with top left";
        assert !implicativeHornWithTopLeft.isImplicativeHornFormula()
                : implicativeHornWithTopLeft + " is implicative horn formula";
    }
}
