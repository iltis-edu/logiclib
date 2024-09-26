package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "assertNotApplicable")
public class XmlAssertNotApplicable extends XmlAssertApplicableBase {
    @Override
    public boolean expectApplicable() {
        return false;
    }
}
