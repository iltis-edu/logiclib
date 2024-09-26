package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.feedback;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml.FeedbackTransformationsList;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.XmlParsableReader;
import java.util.Optional;

public class XmlPredicateFeedbackTransformationLoader {

    public static FeedbackTransformationsList<TermOrFormula> readFeedbackToTransformationsFromFile(
            String path, TransformationRegistry<TermOrFormula> registry) {
        return new XmlParsableReader<>(XmlPredicateFeedbackTransformationsList.class)
                .read(path, Optional.of(registry));
    }
}
