package de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation;

import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.logiclib.modal.interpretation.Valuation;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class ValuationWriterLaTeX implements Writer<Valuation> {

    public String write(Valuation valuation) {
        StringBuilder latex = new StringBuilder();

        boolean first = true;
        for (Variable variable : valuation.getDomain()) {
            if (!first) latex.append(",");
            first = false;
            latex.append(variable).append(" \\mapsto ").append(valuation.map(variable) ? "1" : "0");
        }
        return "\\{" + latex + "\\}";
    }
}
