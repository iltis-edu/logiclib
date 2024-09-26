package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.*;

/**
 * This is the breadth first approach to finding a sequence of modaltransformations from a source
 * formula to a target formula. It performs faster than the depth first approach when the upper
 * limit of transformations is not needed to find such a sequence, but needs more memory.
 */
public abstract class BreadthFirstGeneralFormulaAssimilator<T extends Tree<T>>
        extends GeneralFormulaAssimilator<T> {

    // serialization
    public BreadthFirstGeneralFormulaAssimilator() {}

    private int targetSize;

    public BreadthFirstGeneralFormulaAssimilator(
            T target,
            List<Transformation<T>> equivalenceTransformations,
            List<Transformation<T>> buggyTransformations) {
        super(target, equivalenceTransformations, buggyTransformations);
        // prevent redundant calculations
        this.targetSize = getSize(target);
    }

    protected abstract int getSize(T formula);

    @Override
    public IterativeTransformation<T> assimilate(
            T source, int maxTransformationCountBuggy, int maxTransformationCountEquivalent) {
        return assimilateWithQueue(
                source,
                maxTransformationCountBuggy,
                maxTransformationCountEquivalent,
                new LinkedList<PathToSearch>());
    }

    public IterativeTransformation<T> assimilateWithSizePriorityQueue(
            T source, int maxTransformationCountBuggy, int maxTransformationCountEquivalent) {
        return assimilateWithQueue(
                source,
                maxTransformationCountBuggy,
                maxTransformationCountEquivalent,
                new PriorityQueue<PathToSearch>());
    }

    protected IterativeTransformation<T> assimilateWithQueue(
            T source,
            int maxTransformationCountBuggy,
            int maxTransformationCountEquivalent,
            Queue<PathToSearch> queue) {
        if (eqTester.apply(source, target)) {
            return new IterativeTransformation<T>();
        }

        List<Transformation<T>> transformationsForSubformulae;

        Optional<IterativeTransformation<T>> targetTransformation;

        queue.add(
                new PathToSearch(
                        source,
                        null,
                        maxTransformationCountBuggy,
                        maxTransformationCountEquivalent));

        while (!queue.isEmpty()) {

            PathToSearch current = queue.remove();

            if (current.equivalenceStepsLeft > 0) {
                transformationsForSubformulae =
                        getTransformationsForSubformulae(
                                equivalenceTransformations, current.currentFormula);

                targetTransformation =
                        applyTransformations(transformationsForSubformulae, current, queue, false);

                if (targetTransformation.isPresent()) {
                    return targetTransformation.get();
                }
            }

            if (current.buggyStepsLeft > 0) {
                transformationsForSubformulae =
                        getTransformationsForSubformulae(
                                buggyTransformations, current.currentFormula);

                targetTransformation =
                        applyTransformations(transformationsForSubformulae, current, queue, true);

                if (targetTransformation.isPresent()) {
                    return targetTransformation.get();
                }
            }
        }
        return null;
    }

    /**
     * Applies the transformations to current formula. If the target formula is reached, the
     * resulting transformation is returned. Otherwise, the resulting formula is queued as a new
     * PathToSearch.
     *
     * @param transformations the applied transformations
     * @param current the PathToSearch specifying the current search path
     * @param queue the queue of unexplored paths
     * @param buggy if the transformations used are buggy
     * @return the transformation used to reach the target formula, if it was found.
     */
    private Optional<IterativeTransformation<T>> applyTransformations(
            List<Transformation<T>> transformations,
            PathToSearch current,
            Queue<PathToSearch> queue,
            boolean buggy) {
        for (Transformation<T> transformation : transformations) {
            try {
                T transformedSource = transformation.apply(current.currentFormula);
                IterativeTransformation<T> newTransformation =
                        new IterativeTransformation<T>(
                                current.transformation,
                                new IterativeTransformation<T>(transformation));

                if (eqTester.apply(transformedSource, target)) {
                    return Optional.of(newTransformation);
                }

                if (buggy) {
                    queue.add(
                            new PathToSearch(
                                    transformedSource,
                                    newTransformation,
                                    current.buggyStepsLeft - 1,
                                    current.equivalenceStepsLeft));
                } else {
                    queue.add(
                            new PathToSearch(
                                    transformedSource,
                                    newTransformation,
                                    current.buggyStepsLeft,
                                    current.equivalenceStepsLeft - 1));
                }
            } catch (Exception e) {
                // failsafe:
                // In VERY rare instances a transformation might return true when checking for
                // applicability
                // even tough it is not applicable resulting in a runtime exception.
                // We currently do not know why, when and for which transformations this is a
                // possibility.
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }

    protected class PathToSearch implements Comparable<PathToSearch> {
        int buggyStepsLeft, equivalenceStepsLeft;
        IterativeTransformation<T> transformation;
        T currentFormula;

        PathToSearch(
                T currentFormula,
                IterativeTransformation<T> transformation,
                int buggyStepsLeft,
                int equivalenceStepsLeft) {
            this.buggyStepsLeft = buggyStepsLeft;
            this.equivalenceStepsLeft = equivalenceStepsLeft;
            this.currentFormula = currentFormula;
            this.transformation = transformation;
        }

        public int compareTo(PathToSearch other) {
            if (other == null) {
                return 0;
            }

            // keep breadth first order
            int stepDifference =
                    other.equivalenceStepsLeft
                            + other.buggyStepsLeft
                            - this.buggyStepsLeft
                            - this.equivalenceStepsLeft;
            if (stepDifference != 0) {
                return stepDifference;
            }

            // apply size ordering
            int thisSizeDifference = Math.abs(targetSize - getSize(currentFormula));
            int otherSizeDifference = Math.abs(targetSize - getSize(other.currentFormula));
            return thisSizeDifference - otherSizeDifference;
        }
    }
}
