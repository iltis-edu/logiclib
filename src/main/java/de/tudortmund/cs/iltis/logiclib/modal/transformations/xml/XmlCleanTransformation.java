package de.tudortmund.cs.iltis.logiclib.modal.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * used in sequence before or after other transformations which cannot handle/ can create empty or
 * single-childed disjunctions/conjunctions
 */
@XmlRootElement(name = "clean")
public class XmlCleanTransformation extends XmlTransformation {

    @Override
    protected Transformation<ModalFormula> parseTransformation(
            Optional<TransformationRegistry<ModalFormula>> reg, TreePath path) {
        return new Transformation<ModalFormula>() {
            @Override
            public boolean isApplicable(ModalFormula formula) {
                return true;
            }

            @Override
            public ModalFormula apply(ModalFormula formula) {
                return formula.removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers();
            }

            @Override
            public Transformation<ModalFormula> forPath(TreePath path) {
                return this;
            }
        };
    }
}
