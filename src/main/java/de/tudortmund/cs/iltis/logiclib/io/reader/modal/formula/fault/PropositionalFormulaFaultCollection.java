package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.fault;

import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import java.util.Arrays;
import java.util.List;

/**
 * Can hold multiple {@link PropositionalFormulaFault}s and can be added to a {@link
 * de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping}.
 */
public class PropositionalFormulaFaultCollection
        extends FaultCollection<PropositionalFormulaFaultReason, PropositionalFormulaFault> {

    public PropositionalFormulaFaultCollection() {
        super();
    }

    public PropositionalFormulaFaultCollection(List<PropositionalFormulaFault> faults) {
        super(faults);
    }

    public PropositionalFormulaFaultCollection(PropositionalFormulaFault... faults) {
        super(Arrays.asList(faults));
    }

    @Override
    public FaultCollection<PropositionalFormulaFaultReason, PropositionalFormulaFault> clone() {
        // cloning is done in constructor
        return new PropositionalFormulaFaultCollection(faults);
    }
}
