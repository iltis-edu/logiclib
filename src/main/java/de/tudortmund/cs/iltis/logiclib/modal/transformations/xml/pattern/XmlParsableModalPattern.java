package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.pattern;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.ParsableModalPattern;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;
import de.tudortmund.cs.iltis.utils.io.parsable.ParsableString;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.util.Optional;
import javax.xml.bind.annotation.XmlValue;

public abstract class XmlParsableModalPattern
        extends Parsable<TreePattern<ModalFormula>, TransformationRegistry<ModalFormula>> {

    @XmlValue ParsableString inputString = new ParsableString();

    protected TreePattern<ModalFormula> parse(
            Optional<TransformationRegistry<ModalFormula>> context) {
        return new ParsableModalPattern(inputString.required().value()).value();
    }
}
