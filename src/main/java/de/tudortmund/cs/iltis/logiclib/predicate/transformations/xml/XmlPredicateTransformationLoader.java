package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.XmlTransformationLoader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.XmlParsableReader;
import java.util.Optional;

public class XmlPredicateTransformationLoader implements XmlTransformationLoader<TermOrFormula> {

    // serializatiion
    public XmlPredicateTransformationLoader() {}

    // only used in certain generic cases for procedures working for modal/ predicate formulae alike
    public TransformationList<TermOrFormula> parseTransformationsFromFile(
            String path, TransformationRegistry<TermOrFormula> registry) {
        return readTransformationsFromFile(path, registry);
    }

    public static TransformationList<TermOrFormula> readTransformationsFromFile(
            String path, TransformationRegistry<TermOrFormula> registry) {
        return new XmlParsableReader<>(XmlTransformationList.class)
                .read(path, Optional.of(registry));
    }
}
