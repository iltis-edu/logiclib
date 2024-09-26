package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "assertApplicable")
public class XmlAssertApplicable extends XmlAssertApplicableBase {
    @Override
    public boolean expectApplicable() {
        return true;
    }
}
