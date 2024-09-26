package de.tudortmund.cs.iltis.logiclib.io.writer.modal.clause;

import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterLaTeX;
import de.tudortmund.cs.iltis.logiclib.modal.clause.Clause;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Literal;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class ClauseWriterLaTeX implements Writer<Clause> {

    public String write(Clause clause) {
        if (clause.isEmpty()) return "\\Box";

        ModalFormulaWriterLaTeX formulaWriter = new ModalFormulaWriterLaTeX();
        StringBuilder text = new StringBuilder();
        for (Literal literal : clause)
            text.append(",\\,").append(formulaWriter.write(literal.toFormula()));
        return "\\{" + text.substring(1) + "\\}";
    }
}
