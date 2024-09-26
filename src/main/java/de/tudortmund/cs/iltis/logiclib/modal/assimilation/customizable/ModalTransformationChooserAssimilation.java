package de.tudortmund.cs.iltis.logiclib.modal.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformationInterface;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.TransformationChooserAssimilation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationList;
import de.tudortmund.cs.iltis.logiclib.modal.assimilation.BreadthFirstModalAssimilator;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.xml.XmlModalTransformationLoader;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.TransformationWithNewTree;
import java.util.*;

public class ModalTransformationChooserAssimilation
        extends TransformationChooserAssimilation<ModalFormula> {

    public ModalTransformationChooserAssimilation(
            int stepsEquivalent, int stepsBuggy, String filePath) {
        super(stepsEquivalent, stepsBuggy, filePath);
    }

    // leaveOut defines if specified IDs of buggy transformations should be the ones to be used
    // (false) or the ones that are excluded from the list (true)
    public ModalTransformationChooserAssimilation(
            int stepsEquivalent, int stepsBuggy, boolean leaveOut, String... buggyIDs) {
        super(stepsEquivalent, stepsBuggy, leaveOut, buggyIDs);
    }

    // priority defines the way the assimilation should be performed; the results are always the
    // same but runtime might be better when using priority=true
    public IterativeTransformation<ModalFormula> assimilateToTransformation(
            String source, String target, boolean priority) {
        ModalFormula sourceFormula, targetFormula;
        try {
            sourceFormula = ModalFormula.parse(source);
            targetFormula = ModalFormula.parse(target);
        } catch (IncorrectParseInputException e) {
            throw new IllegalArgumentException("input sting is not a modalformula");
        }

        return assimilateToTransformation(
                sourceFormula,
                priority,
                new BreadthFirstModalAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations, false));
    }

    public List<String> assimilateToStringDescription(
            String source, String target, boolean priority, boolean onlyBuggyOutput) {
        ModalFormula sourceFormula, targetFormula;
        try {
            sourceFormula = ModalFormula.parse(source);
            targetFormula = ModalFormula.parse(target);
        } catch (IncorrectParseInputException e) {
            throw new IllegalArgumentException("input sting is not a modalformula");
        }

        return assimilateToStringDescription(
                sourceFormula,
                priority,
                onlyBuggyOutput,
                new BreadthFirstModalAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations, false));
    }

    // this is thought to be an overview over all different groups of buggy transformations
    public List<String> getAllBuggyTransformationsIDs() {
        List<String> iDs =
                Arrays.asList(
                        "BUGGY_IDEMPOTENCE_TO_TOP",
                        "BUGGY_FORMULA_TO_IMPLICATIONAL_TAUTOLOGY",
                        "BUGGY_REDUCE_CONJUNCTION_WITH_NEGATION",
                        "BUGGY_REDUCE_CONJUNCTION_WITH_NEGATION_TO_BOTTOM",
                        "BUGGY_REDUCE_DISJUNCTION_WITH_NEGATION",
                        "BUGGY_REDUCE_DISJUNCTION_WITH_NEGATION_TO_TOP",
                        "BUGGY_REDUCE_TOP_IN_DISJUNCTION",
                        "BUGGY_REDUCE_TOP_IN_CONJUNCTION",
                        "BUGGY_REDUCE_BOTTOM_IN_DISJUNCTION",
                        "BUGGY_REDUCE_BOTTOM_IN_CONJUNCTION",
                        "BUGGY_SWAP_PREMISE_AND_CONCLUSION",
                        "BUGGY_MOVE_PARENTHESIS_IN_MULTILAYERED_IMPLICATION",
                        "BUGGY_REDUCE_IMPLICATIONAL_TAUTOLOGY",
                        "BUGGY_REDUCE_EQUIVALENT_TAUTOLOGY",
                        "BUGGY_REMOVE_EQUIVALENCE",
                        "BUGGY_REMOVE_IMPLICATION",
                        "BUGGY_REDUCE_TO_IMPLICATION",
                        "BUGGY_PUSH_NEGATION",
                        "BUGGY_PULL_NEGATION",
                        "BUGGY_REMOVE_PARENTHESIS",
                        "BUGGY_MOVE_PARENTHESIS_INSTEAD_OF_DISTRIBUTION",
                        "BUGGY_REDUCE_TO_SUBFORMULA",
                        "BUGGY_PUSH_DISTRIBUTION_TO_WRONG_TARGET",
                        "BUGGY_PULL_DISTRIBUTION_TO_WRONG_TARGET",
                        "BUGGY_MOVE_PARENTHESIS",
                        "BUGGY_ADD_TO_IMPLICATION",
                        "BUGGY_REMOVE_NEGATION",
                        "BUGGY_ADD_NEGATION",
                        "BUGGY_REMOVE_SUBFORMULA",
                        "BUGGY_ADD_SUBFORMULA",
                        "BUGGY_REPLACE_OPERATOR",
                        "BUGGY_SWAP_MODAL_OPERATORS",
                        "BUGGY_ADD_MODAL_OPERATOR",
                        "BUGGY_REMOVE_MODAL_OPERATOR",
                        "BUGGY_MODAL_OPERATOR_DISTRIBUTION",
                        "BUGGY_MODAL_OPERATOR_UNDISTRIBUTION",
                        "BUGGY_NEGATION_MODAL_OPERATORS");
        return iDs;
    }

    protected void parseSpecifiedBuggyTransformations(String filePath) {
        List<AdornedTransformation<ModalFormula>> unfilteredBuggies =
                XmlModalTransformationLoader.readTransformationsFromFile(filePath, registry);

        buggyTransformations = new ArrayList<>();

        unfilteredBuggies.forEach(
                adornedTransformation -> {
                    if (!adornedTransformation.isOnlyPart()) {
                        Transformation<ModalFormula> transformation =
                                adornedTransformation.getTransformation();

                        FeedbackCarrierTransformationInterface<ModalFormula> carrierTransformation;
                        if (transformation instanceof TransformationWithNewTree) {
                            carrierTransformation =
                                    new TransformationWithNewFormulaAndFeedback(
                                            (TransformationWithNewTree<ModalFormula>)
                                                    transformation);
                        } else {
                            carrierTransformation =
                                    new FeedbackCarrierTransformation<ModalFormula>(transformation);
                        }

                        String name;
                        if (adornedTransformation.getGroupName() != null) {
                            name = adornedTransformation.getGroupName();
                        } else {
                            name = adornedTransformation.getName();
                        }

                        carrierTransformation.addFeedbackText("name", name);

                        buggyTransformations.add(carrierTransformation);
                    }
                });
    }

    protected void parseDefaultBuggyTransformations(boolean leaveOut, List<String> buggyIDs) {
        List<AdornedTransformation<ModalFormula>> unfilteredBuggies =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/buggyTransformations.xml",
                        registry);

        buggyTransformations = new ArrayList<>();

        unfilteredBuggies.forEach(
                adornedTransformation -> {
                    if (!adornedTransformation.isOnlyPart()
                            && !leaveOut
                                    == (buggyIDs.contains(adornedTransformation.getName())
                                            || buggyIDs.contains(
                                                    adornedTransformation.getGroupName()))) {

                        Transformation<ModalFormula> transformation =
                                adornedTransformation.getTransformation();

                        FeedbackCarrierTransformationInterface<ModalFormula> carrierTransformation;
                        if (transformation instanceof TransformationWithNewTree) {
                            carrierTransformation =
                                    new TransformationWithNewFormulaAndFeedback(
                                            (TransformationWithNewTree) transformation);
                        } else {
                            carrierTransformation =
                                    new FeedbackCarrierTransformation<ModalFormula>(transformation);
                        }

                        String name;
                        if (adornedTransformation.getGroupName() != null) {
                            name = adornedTransformation.getGroupName();
                        } else {
                            name = adornedTransformation.getName();
                        }

                        carrierTransformation.addFeedbackText("name", name);

                        buggyTransformations.add(carrierTransformation);
                    }
                });
    }

    protected void parseEquivalenceTransformations() {

        TransformationList<ModalFormula> adornedTransformations =
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                        registry);

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                        registry));

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                        registry));

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                        registry));

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                        registry));

        // not needed when comparing formulae on with EqualityModuloCommutativityAndIdempotence
        // equalitytester
        /*
        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",registry)
        );*/

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                        registry));

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                        registry));

        adornedTransformations.addAll(
                XmlModalTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/fixedCombinations.xml",
                        registry));

        equivalenceTransformations = new ArrayList<>();

        adornedTransformations.forEach(
                adornedTransformation -> {
                    if (adornedTransformation.isOnlyPart()) {
                        return;
                    }

                    Transformation<ModalFormula> transformation =
                            adornedTransformation.getTransformation();

                    FeedbackCarrierTransformationInterface<ModalFormula> carrierTransformation;
                    if (transformation instanceof TransformationWithNewTree) {
                        carrierTransformation =
                                new TransformationWithNewFormulaAndFeedback(
                                        (TransformationWithNewTree) transformation);
                    } else {
                        carrierTransformation =
                                new FeedbackCarrierTransformation<ModalFormula>(transformation);
                    }

                    String name;
                    if (adornedTransformation.getGroupName() != null) {
                        name = adornedTransformation.getGroupName();
                    } else {
                        name = adornedTransformation.getName();
                    }

                    carrierTransformation.addFeedbackText("name", name);

                    equivalenceTransformations.add(carrierTransformation);
                });
    }
}
