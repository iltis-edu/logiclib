package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationTest;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.parsable.Parsable;

public abstract class XmlTransformationTest
        extends Parsable<TransformationTest, TransformationRegistry<TermOrFormula>> {}
