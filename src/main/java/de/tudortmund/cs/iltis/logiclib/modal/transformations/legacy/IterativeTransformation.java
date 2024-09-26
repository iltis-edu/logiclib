package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import java.util.*;
import java.util.stream.Collectors;

public class IterativeTransformation
        implements Transformation, Comparable<IterativeTransformation> {

    public IterativeTransformation() {
        this.path = new TreePath();
        this.transformations = new ArrayList<Transformation>();
    }

    public IterativeTransformation(IterativeTransformation... iterativeTransformations) {
        this(
                new TreePath(),
                Arrays.stream(iterativeTransformations)
                        .filter(Objects::nonNull)
                        .map(it -> it.transformations)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
    }

    public IterativeTransformation(Transformation... transformations) {
        this(new TreePath(), transformations);
    }

    public IterativeTransformation(TreePath path, Transformation... transformations) {
        this(path, Arrays.asList(transformations));
    }

    public IterativeTransformation(TreePath path, List<Transformation> transformations) {
        this.path = path;
        // TODO: clone()
        this.transformations =
                transformations.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public boolean isApplicable(ModalFormula formula) {
        ModalFormula currentFormula = formula;
        for (Transformation transformation : transformations) {
            if (!transformation.isApplicable(currentFormula)) {
                return false;
            }
            currentFormula = transformation.apply(currentFormula);
        }
        return true;
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        ModalFormula currentFormula = formula;
        for (Transformation transformation : transformations) {
            currentFormula = transformation.apply(currentFormula);
        }
        return currentFormula;
    }

    @Override
    public Transformation forPath(TreePath path) {
        return new IterativeTransformation(path, getTransformationArray());
    }

    public void addTransformation(Transformation transformation) {
        transformations.add(transformation);
    }

    public List<Transformation> getTransformations() {
        return transformations;
    }

    public int size() {
        return transformations.size();
    }

    @Override
    public String toString() {
        return transformations.toString();
    }

    @Override
    public int compareTo(IterativeTransformation other) {
        Objects.requireNonNull(other);
        return Integer.compare(transformations.size(), other.transformations.size());
    }

    public static IterativeTransformation getMin(
            IterativeTransformation first, IterativeTransformation second) {
        return getMin(first, second, Collections.emptySet());
    }

    public static IterativeTransformation getMin(
            IterativeTransformation first,
            IterativeTransformation second,
            Set<Transformation> notCountTransformations) {
        IterativeTransformation filteredFirst = getFiltered(first, notCountTransformations);
        IterativeTransformation filteredSecond = getFiltered(second, notCountTransformations);
        if (null == filteredFirst) {
            return second;
        }
        if (null == filteredSecond) {
            return first;
        }
        if (filteredSecond.compareTo(filteredFirst) < 0) {
            return second;
        }
        return first;
    }

    protected static IterativeTransformation getFiltered(
            IterativeTransformation transformation, Set<Transformation> removeTransformations) {
        if (null == transformation || removeTransformations.isEmpty()) {
            return transformation;
        }
        IterativeTransformation filteredTransformation = new IterativeTransformation();
        for (Transformation singleTransformation : transformation.getTransformations()) {
            if (removeTransformations.contains(singleTransformation)) {
                continue;
            }
            filteredTransformation =
                    new IterativeTransformation(
                            filteredTransformation,
                            new IterativeTransformation(singleTransformation));
        }
        return filteredTransformation;
    }

    protected Transformation[] getTransformationArray() {
        return transformations.toArray(new Transformation[0]);
    }

    protected TreePath path;
    protected List<Transformation> transformations;
}
