package de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.BreadthFirstGeneralFormulaAssimilator;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class is currently not intended to be used and was only created for an internal project.
 * Please use the assmiliators {@link BreadthFirstGeneralFormulaAssimilator}
 *
 * @param <T> the kind of tree
 */
@Deprecated
public abstract class TransformationChooserAssimilation<T extends Tree<T>> {

    // serialization
    public TransformationChooserAssimilation() {}

    protected List<Transformation<T>> equivalenceTransformations, buggyTransformations;
    protected int stepsEquivalent, stepsBuggy;

    protected TransformationRegistry<T> registry;

    public TransformationChooserAssimilation(int stepsEquivalent, int stepsBuggy, String filePath) {
        this.stepsBuggy = stepsBuggy;
        this.stepsEquivalent = stepsEquivalent;

        registry = new TransformationRegistry<T>();

        parseSpecifiedBuggyTransformations(filePath);

        parseEquivalenceTransformations();

        registry = new TransformationRegistry<T>();
    }

    public TransformationChooserAssimilation(
            int stepsEquivalent, int stepsBuggy, boolean leaveOut, String... buggyIDs) {
        this.stepsBuggy = stepsBuggy;
        this.stepsEquivalent = stepsEquivalent;

        registry = new TransformationRegistry<T>();

        List<String> buggyIDsList = new ArrayList<>();
        Collections.addAll(buggyIDsList, buggyIDs);
        parseDefaultBuggyTransformations(leaveOut, buggyIDsList);

        parseEquivalenceTransformations();

        registry = new TransformationRegistry<T>();
    }

    public abstract IterativeTransformation<T> assimilateToTransformation(
            String source, String target, boolean priority);

    public abstract List<String> assimilateToStringDescription(
            String source, String target, boolean priority, boolean onlyBuggyOutput);

    protected IterativeTransformation<T> assimilateToTransformation(
            T source, boolean priority, BreadthFirstGeneralFormulaAssimilator<T> assimilator) {
        if (priority) {
            return assimilator.assimilateWithSizePriorityQueue(source, stepsBuggy, stepsEquivalent);
        } else {
            return assimilator.assimilate(source, stepsBuggy, stepsEquivalent);
        }
    }

    protected List<String> assimilateToStringDescription(
            T source,
            boolean priority,
            boolean onlyBuggyOutput,
            BreadthFirstGeneralFormulaAssimilator<T> assimilator) {
        IterativeTransformation<T> correction =
                assimilateToTransformation(source, priority, assimilator);

        if (correction == null) {
            return Arrays.asList("NO_TRANSFORMATION_FOUND");
        }

        if (correction.size() == 0) {
            return Arrays.asList("NO_TRANSFORMATION_NEEDED");
        }

        List<String> feedbackTexts = new ArrayList<>();
        for (Transformation<T> transformation : correction.getTransformations()) {
            String currentTransformationText =
                    ((FeedbackCarrierTransformationInterface<T>) transformation)
                            .getFeedbackText("name");
            if (!onlyBuggyOutput || currentTransformationText.startsWith("BUGGY_")) {
                feedbackTexts.add(currentTransformationText);
            }
        }

        return feedbackTexts;
    }

    public abstract List<String> getAllBuggyTransformationsIDs();

    protected abstract void parseSpecifiedBuggyTransformations(String filePath);

    protected abstract void parseDefaultBuggyTransformations(
            boolean leaveOut, List<String> buggyIDs);

    protected abstract void parseEquivalenceTransformations();
}
