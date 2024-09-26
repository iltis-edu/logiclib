package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.List;

/**
 * This is the depth first approach to finding a sequence of modaltransformations from a source
 * formula to a target formula. It performs slower than the breadth first approach when the upper
 * limit of transformations is not needed to find such a sequence, but needs less memory.
 */
public abstract class DepthFirstGeneralFormulaAssimilator<T extends Tree<T>>
        extends GeneralFormulaAssimilator<T> {

    // serialization
    public DepthFirstGeneralFormulaAssimilator() {}

    public DepthFirstGeneralFormulaAssimilator(
            T target,
            List<Transformation<T>> equivalenceTransformations,
            List<Transformation<T>> buggyTransformations) {
        super(target, equivalenceTransformations, buggyTransformations);
    }

    public IterativeTransformation<T> assimilate(
            T source, int maxTransformationCountBuggy, int maxTransformationCountEquivalent) {

        // transformation found source.equals(target)
        if (eqTester.apply(source, target)) {
            return new IterativeTransformation<T>();
        }
        // no possible transformation steps left
        if (maxTransformationCountBuggy + maxTransformationCountEquivalent <= 0) {
            return null;
        }
        List<Transformation<T>> transformationsForSubformulae;
        IterativeTransformation<T> completeTransformation = null;
        IterativeTransformation<T> remainingTransformations = null;

        // apply equivalence transformation
        if (maxTransformationCountEquivalent > 0) {
            transformationsForSubformulae =
                    getTransformationsForSubformulae(equivalenceTransformations, source);

            for (Transformation<T> transformation : transformationsForSubformulae) {

                T transformedSource;

                try {
                    transformedSource = transformation.apply((T) source.clone());
                } catch (Exception e) {
                    // failsafe:
                    // In VERY rare instances a transformation might return true when checking for
                    // applicability
                    // even tough it is not applicable resulting in a runtime exception.
                    // We currently do not know why, when and for which transformations this is a
                    // possibility.
                    e.printStackTrace();
                    continue;
                }

                remainingTransformations =
                        assimilate(
                                transformedSource,
                                maxTransformationCountBuggy,
                                maxTransformationCountEquivalent - 1);

                if (remainingTransformations == null) {
                    continue;
                }

                completeTransformation =
                        new IterativeTransformation<T>(
                                new IterativeTransformation<T>(transformation),
                                remainingTransformations);

                if (0 >= completeTransformation.size() - 1) {
                    return completeTransformation;
                }
            }
        }

        // apply buggy transformation
        if (maxTransformationCountBuggy > 0) {
            transformationsForSubformulae =
                    getTransformationsForSubformulae(buggyTransformations, source);

            for (Transformation<T> correction : transformationsForSubformulae) {

                T transformedSource;

                try {
                    transformedSource = correction.apply((T) source.clone());
                } catch (Exception e) {
                    // failsafe:
                    // In VERY rare instances a transformation might return true when checking for
                    // applicability
                    // even tough it is not applicable resulting in a runtime exception.
                    // We currently do not know why, when and for which transformations this is a
                    // possibility.
                    e.printStackTrace();
                    continue;
                }

                remainingTransformations =
                        assimilate(
                                transformedSource,
                                maxTransformationCountBuggy - 1,
                                maxTransformationCountEquivalent);

                if (remainingTransformations == null) {
                    continue;
                }

                completeTransformation =
                        new IterativeTransformation<T>(
                                new IterativeTransformation<T>(correction),
                                remainingTransformations);

                if (0 >= completeTransformation.size() - 1) {
                    return completeTransformation;
                }
            }
        }

        return completeTransformation;
    }
}
