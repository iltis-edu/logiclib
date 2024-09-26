package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.XmlParsableReader;
import java.util.Optional;

public class XmlModalFeedbackTransformationLoader {

    public static FeedbackTransformationsList<ModalFormula> readFeedbackToTransformationsFromFile(
            String path, TransformationRegistry<ModalFormula> registry) {
        return new XmlParsableReader<>(XmlModalFeedbackTransformationsList.class)
                .read(path, Optional.of(registry));
    }
}
