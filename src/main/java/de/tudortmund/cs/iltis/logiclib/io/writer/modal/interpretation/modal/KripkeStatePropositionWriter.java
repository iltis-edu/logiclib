package de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal.KripkeState;
import de.tudortmund.cs.iltis.utils.io.writer.collections.SetWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class KripkeStatePropositionWriter implements Writer<KripkeState> {
    @Override
    public String write(KripkeState state) {
        SetWriter<Variable> propWriter = new SetWriter<>();
        return state.writeName() + " ↦ " + propWriter.write(state.getValuation().getPreimage(true));
    }
}
