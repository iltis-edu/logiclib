package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation;

import de.tudortmund.cs.iltis.utils.function.SerializableBiFunction;
import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GeneralFormulaAssimilator<T extends Tree<T>> implements Serializable {

    /* Need for serialization */
    @SuppressWarnings("unused")
    public GeneralFormulaAssimilator() {}

    protected List<Transformation<T>> buggyTransformations, equivalenceTransformations;

    protected T target;

    protected SerializableBiFunction<T, T, Boolean> eqTester;

    public GeneralFormulaAssimilator(
            T target,
            List<Transformation<T>> equivalenceTransformations,
            List<Transformation<T>> buggyTransformations) {
        this.target = (T) target.clone();
        this.buggyTransformations = adjustTransformationsToSubformula(buggyTransformations, target);
        this.equivalenceTransformations =
                adjustTransformationsToSubformula(equivalenceTransformations, target);
    }

    public abstract IterativeTransformation<T> assimilate(
            T source, int maxTransformationCountBuggy, int maxTransformationCountEquivalent);

    protected List<Transformation<T>> getTransformationsForSubformulae(
            List<Transformation<T>> transformations, T formula) {
        List<Transformation<T>> subfomulaeCorrections = new ArrayList<>();
        for (Transformation<T> transformation : transformations) {
            for (TreePath path : formula.getAllApplications(transformation)) {
                subfomulaeCorrections.add(transformation.forPath(path));
            }
        }
        return subfomulaeCorrections;
    }

    protected abstract List<Transformation<T>> adjustTransformationsToSubformula(
            List<Transformation<T>> transformations, T target);
}
