package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.XmlTransformationLoader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.XmlParsableReader;
import java.util.Optional;

public class XmlModalTransformationLoader implements XmlTransformationLoader<ModalFormula> {

    // serialization
    public XmlModalTransformationLoader() {}

    // only used in certain generic cases for procedures working for modal/ predicate formulae alike
    public TransformationList<ModalFormula> parseTransformationsFromFile(
            String path, TransformationRegistry<ModalFormula> registry) {
        return readTransformationsFromFile(path, registry);
    }

    public static TransformationList<ModalFormula> readTransformationsFromFile(
            String path, TransformationRegistry<ModalFormula> registry) {
        return new XmlParsableReader<>(XmlTransformationList.class)
                .read(path, Optional.of(registry));
    }
}
