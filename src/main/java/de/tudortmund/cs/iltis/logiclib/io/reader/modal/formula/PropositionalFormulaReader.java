package de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula;

import de.tudortmund.cs.iltis.logiclib.io.parser.general.fault.GeneralFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.fault.PropositionalFormulaFault;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.fault.PropositionalFormulaFaultCollection;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.fault.PropositionalFormulaFaultReason;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import de.tudortmund.cs.iltis.utils.io.parser.general.GeneralParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.parser.parentheses.ParenthesesParsingFaultReason;
import de.tudortmund.cs.iltis.utils.io.reader.general.Reader;

/**
 * This reader uses the {@link ModalFormulaReader}. If a {@link ModalFormula} could be parsed
 * (regardless if faults occurred) the resulting {@link ModalFormula} will be checked if it is
 * propositional (see {@link ModalFormula#isPropositional()}). If the formula is not propositional a
 * {@link PropositionalFormulaFaultCollection} will be added to the {@link ParsingFaultTypeMapping}
 * which may also already contain parsing-faults. This type mapping will be thrown in a {@link
 * IncorrectParseInputException} if it contains any faults (parsing or propositional faults or
 * both). Otherwise, the parsed and propositional {@link ModalFormula} will be returned.
 *
 * <p>The following faults may occur in this reader: {@link GeneralParsingFaultReason#VARIOUS},
 * {@link GeneralParsingFaultReason#INVALID_SYMBOL}, {@link
 * GeneralParsingFaultReason#PARENTHESES_MISSING}, {@link ParenthesesParsingFaultReason} (all),
 * {@link GeneralFormulaFaultReason} (all), {@link PropositionalFormulaFaultReason} (all)
 *
 * <p>The {@link ParsingFaultTypeMapping} can contain a {@link PropositionalFormulaFaultCollection}
 * with its belonging {@link PropositionalFormulaFaultReason}s if the parsed formula is not
 * propositional.
 */
public class PropositionalFormulaReader implements Reader<ModalFormula> {

    private ModalReaderProperties props;

    public PropositionalFormulaReader(ModalReaderProperties props) {
        this.props = props;
    }

    // TODO: Add test case
    @Override
    public ModalFormula read(Object o) throws IncorrectParseInputException {
        ModalFormula output;
        ParsingFaultTypeMapping<?> mapping;
        try {
            output = new ModalFormulaReader(props).read(o);
            mapping = new ParsingFaultTypeMapping<>(o.toString(), output);
        } catch (IncorrectParseInputException e) {
            mapping = e.getFaultMapping();
            output = (ModalFormula) mapping.getOutput();
            if (output == null) throw e;
        }

        if (!output.isPropositional()) {
            PropositionalFormulaFaultCollection collection =
                    new PropositionalFormulaFaultCollection(
                            new PropositionalFormulaFault(
                                    PropositionalFormulaFaultReason.FORMULA_NOT_PROPOSITIONAL));
            mapping = mapping.with(collection);
        }

        if (mapping.containsAny()) throw new IncorrectParseInputException(mapping);
        else return output;
    }
}
