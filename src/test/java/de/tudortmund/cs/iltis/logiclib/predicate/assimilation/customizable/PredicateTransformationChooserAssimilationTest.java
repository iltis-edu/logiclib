package de.tudortmund.cs.iltis.logiclib.predicate.assimilation.customizable;

import org.junit.Test;

public class PredicateTransformationChooserAssimilationTest {

    @Test
    public void testTextOutput() {
        PredicateTransformationChooserAssimilation assimilation =
                new PredicateTransformationChooserAssimilation(
                        1, 1, false, "BUGGY_IDEMPOTENCE_TO_TOP");

        assert "[BUGGY_IDEMPOTENCE_TO_TOP]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "(X(x) & X(x)) | !(!Y(y))", "(1) | Y(y)", true, true)
                                .toString());
        assert "[BUGGY_IDEMPOTENCE_TO_TOP, REMOVE_DOUBLE_NEGATION]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "(X(x) & X(x)) | !(!Y(y))", "(1) | Y(y)", true, false)
                                .toString());

        assimilation =
                new PredicateTransformationChooserAssimilation(
                        1, 1, true, "BUGGY_NEGATION_QUANTIFIER", "BUGGY_SWAP_QUANTIFIER");
        assert "[BUGGY_ADD_QUANTIFIER]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "forall x !X(x)", "!forall x X(x)", true, true)
                                .toString());

        assimilation = new PredicateTransformationChooserAssimilation(1, 1, true);
        assert "[BUGGY_NEGATION_QUANTIFIER]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "forall x !X(x)", "!forall x X(x)", true, true)
                                .toString());
        assert "[NO_TRANSFORMATION_NEEDED]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "forall x !X(x)", "forall x !X(x)", true, true)
                                .toString());

        assert "[BUGGY_ADD_SUBFORMULA]"
                .equals(
                        assimilation
                                .assimilateToStringDescription(
                                        "forall y E(x,y)",
                                        "forall y (E(x,y) -> exists z E(y,z))",
                                        true,
                                        true)
                                .toString());
    }
}
