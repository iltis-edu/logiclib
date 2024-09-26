package de.tudortmund.cs.iltis.logiclib.baselogic.transformations.feedback.xml;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import java.util.*;

public class FeedbackTransformationsList<T extends Tree<T>>
        extends ArrayList<TransformationWithFeedbackTextsInterface<T>> {
    public FeedbackTransformationsList(
            Collection<? extends List<TransformationWithFeedbackTextsInterface<T>>>
                    transformations) {
        this.addAll(this.toDirectList(transformations));
        this.removeAndUniteDuplicates();
    }

    // serialization
    public FeedbackTransformationsList() {}

    private List<TransformationWithFeedbackTextsInterface<T>> toDirectList(
            Collection<? extends List<TransformationWithFeedbackTextsInterface<T>>>
                    transformations) {
        ArrayList<TransformationWithFeedbackTextsInterface<T>> res = new ArrayList<>();
        transformations.forEach(list -> this.addAll(list));

        return res;
    }

    // when feedbacktexts are specified for a group AND for a single transformation from that group
    // separatly,
    // 2 separate feedbacktransformations are created, each having their one feedbacktextentry.
    // So these duplicate transformations are combined to one with all feedbacktexts here:
    private void removeAndUniteDuplicates() {
        ArrayList<TransformationWithFeedbackTextsInterface<T>> uniques = new ArrayList<>();
        forEach(
                tra -> {
                    if (!uniques.contains(tra)) {
                        uniques.add(tra);
                    } else {
                        Map<String, String> feedbackTextMap =
                                uniques.get(uniques.indexOf(tra)).getMap();
                        ;
                        tra.getMap()
                                .forEach(
                                        (key, text) -> {
                                            if (feedbackTextMap.containsKey(key)) {
                                                throw new IllegalArgumentException(
                                                        "Duplicate feedbacktype for different feedback texts for a transformation specified!");
                                            } else {
                                                feedbackTextMap.put(key, text);
                                            }
                                        });
                    }
                });

        this.clear();
        this.addAll(uniques);
    }
}
