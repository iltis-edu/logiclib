package de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class KripkeStateNameWriter implements Writer<KripkeState> {
    @Override
    public String write(KripkeState state) {
        return state.writeName();
    }
}
