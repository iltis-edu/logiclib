package de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
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
    protected Transformation<TermOrFormula> parseTransformation(
            Optional<TransformationRegistry<TermOrFormula>> registry, TreePath path) {
        return new Transformation<TermOrFormula>() {
            @Override
            public boolean isApplicable(TermOrFormula formula) {
                return true;
            }

            @Override
            public TermOrFormula apply(TermOrFormula formula) {
                return formula.removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers();
            }

            @Override
            public Transformation<TermOrFormula> forPath(TreePath path) {
                return this;
            }
        };
    }
}
