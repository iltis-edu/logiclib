package de.tudortmund.cs.iltis.logiclib.io.writer.modal.clause;

import de.tudortmund.cs.iltis.logiclib.modal.clause.Clause;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Literal;
import de.tudortmund.cs.iltis.utils.io.writer.general.Writer;

public class ClauseWriterAscii implements Writer<Clause> {

    public String write(Clause clause) {
        StringBuilder text = new StringBuilder();
        for (Literal literal : clause) {
            text.append("-");
            if (literal.isNegative()) text.append("not");
            text.append(literal.getVariable());
        }
        return text.substring(1);
    }
}
