package de.tudortmund.cs.iltis.logiclib.predicate.formula.interpretation;

import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.*;
import org.junit.Test;

public class FormulaEvaluatorEfficiencyTest {

    @Test
    public void testIssue565() {
        final int NUMBER_OF_NODES = 7;
        Structure<Integer> A;
        A = new Structure<>(new IntUniverse(0, NUMBER_OF_NODES - 1));

        A.addRelation("E", 2, t -> (t.first() + 1) % NUMBER_OF_NODES == t.second());
        A.addRelation("Q", 2, t -> t.first() == t.second()); // failed to use infix '=' :- (
        A.addConstant("c", 0);
        A.addConstant("d", 6);

        Formula phi;
        phi =
                Formula.parse(
                        "∃x∀y∃u∃v((Q(y,c)→(E(y,u) ∧ E(u,v) ∧ E(v, x))) ∧ (Q(y,d)→(E(x, u) ∧ E(u,v) ∧ E(v, y))))");

        long timeBefore, timeAfter;

        // top-down ------------------------------------------------------------
        FormulaEvaluatorTopDown evalTD;
        evalTD = new FormulaEvaluatorTopDown(phi);
        System.out.println("TOP-DOWN\n\tselected nodes");
        timeBefore = System.currentTimeMillis();
        System.out.println("\t" + evalTD.evaluate(A));
        timeAfter = System.currentTimeMillis();
        System.out.println(
                "\t" + NUMBER_OF_NODES + " nodes in " + (timeAfter - timeBefore) + "msec");

        // bottom-up -----------------------------------------------------------
        FormulaEvaluator evalBU;
        evalBU = new FormulaEvaluator(phi);
        System.out.println("BOTTOM-UP\n\tselected nodes");
        timeBefore = System.currentTimeMillis();
        System.out.println("\t" + evalBU.evaluate(A));
        timeAfter = System.currentTimeMillis();
        System.out.println(
                "\t" + NUMBER_OF_NODES + " nodes in " + (timeAfter - timeBefore) + "msec");
    }
}
