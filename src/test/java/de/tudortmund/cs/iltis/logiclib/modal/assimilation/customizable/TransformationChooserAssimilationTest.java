package de.tudortmund.cs.iltis.logiclib.modal.assimilation.customizable;

import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import org.junit.Test;

public class TransformationChooserAssimilationTest {

    @Test
    public void testTextOutput() throws IncorrectParseInputException {
        ModalTransformationChooserAssimilation assimilation =
                new ModalTransformationChooserAssimilation(1, 1, false, "BUGGY_IDEMPOTENCE_TO_TOP");

        assert "[BUGGY_IDEMPOTENCE_TO_TOP]"
                .equals(
                        assimilation
                                .assimilateToStringDescription("(X∧X)∨¬¬Y", "(⊤)∨Y", true, true)
                                .toString());
        assert "[BUGGY_IDEMPOTENCE_TO_TOP, REMOVE_DOUBLE_NEGATION]"
                .equals(
                        assimilation
                                .assimilateToStringDescription("(X∧X)∨¬¬Y", "(⊤)∨Y", true, false)
                                .toString());

        assimilation =
                new ModalTransformationChooserAssimilation(
                        1,
                        1,
                        true,
                        "BUGGY_PUSH_NEGATION_MODAL_OPERATORS",
                        "BUGGY_PULL_NEGATION_MODAL_OPERATORS",
                        "BUGGY_SWAP_BOX_FOR_DIAMOND",
                        "BUGGY_SWAP_DIAMOND_FOR_BOX");
        assert "[NO_TRANSFORMATION_FOUND]"
                .equals(
                        assimilation
                                .assimilateToStringDescription("¬◇X", "◇¬X", true, true)
                                .toString());

        assimilation = new ModalTransformationChooserAssimilation(1, 1, true);
        assert "[BUGGY_PUSH_NEGATION_MODAL_OPERATORS]"
                .equals(
                        assimilation
                                .assimilateToStringDescription("¬◇X", "◇¬X", true, true)
                                .toString());
        assert "[NO_TRANSFORMATION_NEEDED]"
                .equals(
                        assimilation
                                .assimilateToStringDescription("¬◇X", "¬◇X", true, true)
                                .toString());
    }
}
