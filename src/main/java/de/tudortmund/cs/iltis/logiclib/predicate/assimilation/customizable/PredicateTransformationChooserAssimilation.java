package de.tudortmund.cs.iltis.logiclib.predicate.assimilation.customizable;

import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformation;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.FeedbackCarrierTransformationInterface;
import de.tudortmund.cs.iltis.logiclib.baselogic.assimilation.customizable.TransformationChooserAssimilation;
import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.AdornedTransformation;
import de.tudortmund.cs.iltis.logiclib.predicate.assimilation.BreadthFirstPredicateAssimilator;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewFormula;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.UnaryPatternTransformationWithNewTerm;
import de.tudortmund.cs.iltis.logiclib.predicate.transformations.xml.XmlPredicateTransformationLoader;
import de.tudortmund.cs.iltis.utils.tree.transformations.IterativeTransformation;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredicateTransformationChooserAssimilation
        extends TransformationChooserAssimilation<TermOrFormula> {

    public PredicateTransformationChooserAssimilation(
            int stepsEquivalent, int stepsBuggy, String filePath) {
        super(stepsEquivalent, stepsBuggy, filePath);
    }

    public PredicateTransformationChooserAssimilation(
            int stepsEquivalent, int stepsBuggy, boolean leaveOut, String... buggyIDs) {
        super(stepsEquivalent, stepsBuggy, leaveOut, buggyIDs);
    }

    public IterativeTransformation<TermOrFormula> assimilateToTransformation(
            String source, String target, boolean priority) {
        TermOrFormula sourceFormula = Formula.parse(source);
        TermOrFormula targetFormula = Formula.parse(target);

        return assimilateToTransformation(
                sourceFormula,
                priority,
                new BreadthFirstPredicateAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations));
    }

    public List<String> assimilateToStringDescription(
            String source, String target, boolean priority, boolean onlyBuggyOutput) {
        TermOrFormula sourceFormula = Formula.parse(source);
        TermOrFormula targetFormula = Formula.parse(target);

        return assimilateToStringDescription(
                sourceFormula,
                priority,
                onlyBuggyOutput,
                new BreadthFirstPredicateAssimilator(
                        targetFormula, equivalenceTransformations, buggyTransformations));
    }

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
                        "BUGGY_REMOVE_QUANTIFIER",
                        "BUGGY_ADD_QUANTIFIER",
                        "BUGGY_SWAP_QUANTIFIER",
                        "BUGGY_QUANTIFIER_PRECEDENCE",
                        "BUGGY_DISTRIBUTE_QUANTIFIERS",
                        "BUGGY_UNDISTRIBUTE_QUANTIFIERS",
                        "BUGGY_NEGATION_QUANTIFIER",
                        "BUGGY_IMPLICATION_QUANTIFIER",
                        "BUGGY_EQUIVALENCE_QUANTIFIER");

        return iDs;
    }

    protected void parseSpecifiedBuggyTransformations(String filePath) {
        List<AdornedTransformation<TermOrFormula>> unfilteredBuggies =
                XmlPredicateTransformationLoader.readTransformationsFromFile(filePath, registry);

        buggyTransformations = new ArrayList<>();

        unfilteredBuggies.forEach(
                adornedTransformation -> {
                    if (!adornedTransformation.isOnlyPart()) {

                        Transformation<TermOrFormula> transformation =
                                adornedTransformation.getTransformation();

                        FeedbackCarrierTransformationInterface<TermOrFormula> carrierTransformation;
                        if (transformation instanceof UnaryPatternTransformationWithNewFormula) {
                            UnaryPatternTransformationWithNewFormula tr =
                                    (UnaryPatternTransformationWithNewFormula) transformation;
                            carrierTransformation =
                                    new PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
                                            tr);
                        } else if (transformation
                                instanceof UnaryPatternTransformationWithNewTerm) {
                            UnaryPatternTransformationWithNewTerm tr =
                                    (UnaryPatternTransformationWithNewTerm) transformation;
                            carrierTransformation =
                                    new PredicateUnaryPatternTransformationWithNewTermAndFeedback(
                                            tr);
                        } else {
                            carrierTransformation =
                                    new FeedbackCarrierTransformation<TermOrFormula>(
                                            transformation);
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
        List<AdornedTransformation<TermOrFormula>> unfilteredBuggies =
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateBuggyTransformations.xml",
                        registry);

        buggyTransformations = new ArrayList<>();

        unfilteredBuggies.forEach(
                adornedTransformation -> {
                    if (!adornedTransformation.isOnlyPart()
                            && !leaveOut
                                    == (buggyIDs.contains(adornedTransformation.getName())
                                            || buggyIDs.contains(
                                                    adornedTransformation.getGroupName()))) {

                        Transformation<TermOrFormula> transformation =
                                adornedTransformation.getTransformation();

                        FeedbackCarrierTransformationInterface<TermOrFormula> carrierTransformation;
                        if (transformation instanceof UnaryPatternTransformationWithNewFormula) {
                            UnaryPatternTransformationWithNewFormula tr =
                                    (UnaryPatternTransformationWithNewFormula) transformation;
                            carrierTransformation =
                                    new PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
                                            tr);
                        } else if (transformation
                                instanceof UnaryPatternTransformationWithNewTerm) {
                            UnaryPatternTransformationWithNewTerm tr =
                                    (UnaryPatternTransformationWithNewTerm) transformation;
                            carrierTransformation =
                                    new PredicateUnaryPatternTransformationWithNewTermAndFeedback(
                                            tr);
                        } else {
                            carrierTransformation =
                                    new FeedbackCarrierTransformation<TermOrFormula>(
                                            transformation);
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
        List<AdornedTransformation<TermOrFormula>> equivalenceTransformationsRaw =
                new ArrayList<>();
        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateSingleInvertibleTransformations.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateImplication.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateEquivalence.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateConstants.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDeMorgan.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateAssociativity.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateDistribution.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateFixedCombinations.xml",
                        registry));

        equivalenceTransformationsRaw.addAll(
                XmlPredicateTransformationLoader.readTransformationsFromFile(
                        "de/tudortmund/cs/iltis/logiclib/predicate/transformations/xml/predicateQuantifiers.xml",
                        registry));

        equivalenceTransformations = new ArrayList<>();

        equivalenceTransformationsRaw.forEach(
                transformation -> {
                    if (transformation.isOnlyPart()) return;

                    FeedbackCarrierTransformationInterface<TermOrFormula> carrierTransformation;
                    if (transformation.getTransformation()
                            instanceof UnaryPatternTransformationWithNewFormula) {
                        UnaryPatternTransformationWithNewFormula tr =
                                (UnaryPatternTransformationWithNewFormula)
                                        transformation.getTransformation();
                        carrierTransformation =
                                new PredicateUnaryPatternTransformationWithNewFormulaAndFeedback(
                                        tr);
                    } else if (transformation.getTransformation()
                            instanceof UnaryPatternTransformationWithNewTerm) {
                        UnaryPatternTransformationWithNewTerm tr =
                                (UnaryPatternTransformationWithNewTerm)
                                        transformation.getTransformation();
                        carrierTransformation =
                                new PredicateUnaryPatternTransformationWithNewTermAndFeedback(tr);
                    } else {
                        carrierTransformation =
                                new FeedbackCarrierTransformation<TermOrFormula>(
                                        transformation.getTransformation());
                    }

                    String name;
                    if (transformation.getGroupName() != null) {
                        name = transformation.getGroupName();
                    } else {
                        name = transformation.getName();
                    }

                    carrierTransformation.addFeedbackText("name", name);

                    equivalenceTransformations.add(carrierTransformation);
                });
    }
}
