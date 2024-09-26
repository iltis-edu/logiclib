package de.tudortmund.cs.iltis.logiclib.baselogic.transformations;

import de.tudortmund.cs.iltis.utils.tree.Tree;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;

public class AdornedTransformation<T extends Tree<T>> {

    private final String name;

    private String groupName;
    private final Transformation<T> transformation;
    private final List<TransformationTest<T>> tests;

    private boolean onlyPart;

    public AdornedTransformation(
            String name, Transformation<T> transformation, List<TransformationTest<T>> tests) {
        this.name = name;
        this.transformation = transformation;
        this.tests = tests;
        onlyPart = false;
    }

    public boolean isOnlyPart() {
        return onlyPart;
    }

    public void setOnlyPart(boolean onlyPart) {
        this.onlyPart = onlyPart;
    }

    public String getName() {
        return name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Transformation<T> getTransformation() {
        return transformation;
    }

    public TestResult runTests() {
        TestResult result = new TestResult();
        tests.forEach(test -> result.run(transformation, test));
        return result;
    }

    public static class TestResult<T extends Tree<T>> {
        private List<TransformationTest<T>> succeeded = new ArrayList<>();
        private List<TransformationTest<T>> failed = new ArrayList<>();

        public void run(Transformation<T> transformation, TransformationTest<T> test) {
            boolean result = false;
            try {
                result = test.run(transformation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            (result ? succeeded : failed).add(test);
        }

        public List<TransformationTest<T>> getSucceeded() {
            return this.succeeded;
        }

        public List<TransformationTest<T>> getFailed() {
            return this.failed;
        }

        public int failedNumber() {
            return failed.size();
        }

        public int succeededNumber() {
            return succeeded.size();
        }

        public int totalNumber() {
            return failedNumber() + succeededNumber();
        }

        public String toString() {
            int total = totalNumber();
            if (total == 0) return "No tests run.";

            int countFailed = failed.size();
            if (countFailed == 0) return "All " + total + " succeeded";
            else return countFailed + " of " + total + " failed";
        }
    }
}
