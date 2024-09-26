package de.tudortmund.cs.iltis.logiclib.predicate.formula;

import org.junit.Test;

public class FormulaNormalFormTest {

    @Test
    public void testNNF() {
        Formula formula;

        // formulae in negation normal form
        formula = Formula.parse("exists x (X(x) & !Y(x,y))");
        assert formula.isInNegationNormalForm()
                : formula + " is not recognized to be in negation normal form";

        formula = Formula.parse("forall y exists x !X(x,y)");
        assert formula.isInNegationNormalForm()
                : formula + " is not recognized to be in negation normal form";

        formula = Formula.parse("X(x) & Y(x,y)");
        assert formula.isInNegationNormalForm()
                : formula + " is not recognized to be in negation normal form";

        // formulae not in negation normal form
        formula = Formula.parse("!(exists x X(x) & Y(x,y))");
        assert !formula.isInNegationNormalForm()
                : formula + " is recognized to be in negation normal form";

        formula = Formula.parse("forall y !(exists x X(x) & Y(x,y))");
        assert !formula.isInNegationNormalForm()
                : formula + " is recognized to be in negation normal form";

        formula = Formula.parse("!(!X(x)) &  forall y Y(x,y)");
        assert !formula.isInNegationNormalForm()
                : formula + " is recognized to be in negation normal form";

        formula = Formula.parse("!X(x) -> forall y !Y(x,y)");
        assert !formula.isInNegationNormalForm()
                : formula + " is recognized to be in negation normal form";

        formula = Formula.parse("!X(x) <-> forall y !Y(x,y)");
        assert !formula.isInNegationNormalForm()
                : formula + " is recognized to be in negation normal form";
    }

    @Test
    public void testPrenexNormalForm() {
        Formula formula;

        // formulae in prenex normal form
        formula = Formula.parse("exists x (X(x) & Y(x,y))");
        assert formula.isInPrenexNormalForm()
                : formula + " is not recognized to be in prenex normal form";

        formula = Formula.parse("forall y exists x (X(x) & Y(x,y))");
        assert formula.isInPrenexNormalForm()
                : formula + " is not recognized to be in prenex normal form";

        formula = Formula.parse("X(x) & Y(x,y)");
        assert formula.isInPrenexNormalForm()
                : formula + " is not recognized to be in prenex normal form";

        // formulae not in prenex normal form
        formula = Formula.parse("exists x X(x) & Y(x,y)");
        assert !formula.isInPrenexNormalForm()
                : formula + " is recognized to be in prenex normal form";

        formula = Formula.parse("forall y (exists x X(x) & Y(x,y))");
        assert !formula.isInPrenexNormalForm()
                : formula + " is recognized to be in prenex normal form";

        formula = Formula.parse("X(x) &  forall y Y(x,y)");
        assert !formula.isInPrenexNormalForm()
                : formula + " is recognized to be in prenex normal form";
    }

    @Test
    public void testSkolemNormalForm() {
        Formula formula;

        // formulae in skolem normal form
        formula = Formula.parse("forall x (X(x) & Y(x,y))");
        assert formula.isInSkolemNormalForm()
                : formula + " is not recognized to be in skolem normal form";

        formula = Formula.parse("forall y forall x (X(x) & Y(x,y))");
        assert formula.isInSkolemNormalForm()
                : formula + " is not recognized to be in skolem normal form";

        formula = Formula.parse("X(x) & Y(x,y)");
        assert formula.isInSkolemNormalForm()
                : formula + " is not recognized to be in skolem normal form";

        // formulae not in skolem normal form
        formula = Formula.parse("exists x forall y (X(x) & Y(x,y))");
        assert !formula.isInSkolemNormalForm()
                : formula + " is recognized to be in skolem normal form";

        formula = Formula.parse("forall y (forall x X(x) & Y(x,y))");
        assert !formula.isInSkolemNormalForm()
                : formula + " is recognized to be in skolem normal form";

        formula = Formula.parse("X(x) &  forall y Y(x,y)");
        assert !formula.isInSkolemNormalForm()
                : formula + " is recognized to be in skolem normal form";
    }
}
