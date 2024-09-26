package de.tudortmund.cs.iltis.logiclib.modal.transformations;

import de.tudortmund.cs.iltis.logiclib.baselogic.transformations.TransformationRegistry;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Conjunction;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Equivalence;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.logiclib.modal.formula.Variable;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.transformations.Transformation;
import java.util.*;

public class TseitinCNFTransformation implements Transformation<ModalFormula> {

    private Transformation<ModalFormula> removeParenthesesTransformation, tseitinCNF;
    private String variableNames;

    // serialization
    public TseitinCNFTransformation() {}

    public TseitinCNFTransformation(TransformationRegistry<ModalFormula> registry) {
        this("Tseitin", registry);
    }

    public TseitinCNFTransformation(
            String variableNames, TransformationRegistry<ModalFormula> registry) {
        this.variableNames = variableNames;
        setUpTransformations(registry);
    }

    @Override
    public boolean isApplicable(final ModalFormula formula) {
        return true;
    }

    @Override
    public ModalFormula apply(final ModalFormula formula) {
        Set<ModalFormula> subformulaeWithVariables = formula.clone().getAllSubformulae();

        List<SizeSortedFormula> sortedFormulas = new ArrayList<>();
        subformulaeWithVariables.forEach(
                subformula -> {
                    if (!subformula.isVariable()) {
                        sortedFormulas.add(new SizeSortedFormula(subformula));
                    }
                });

        Collections.sort(sortedFormulas);

        int newVariableCounter = 1;
        for (SizeSortedFormula sizeSortedFormula : sortedFormulas) {
            sizeSortedFormula.setReplacement(
                    new Variable(new IndexedSymbol(variableNames, "" + newVariableCounter, null)));
            newVariableCounter++;
        }
        newVariableCounter--;

        for (int outerIndex = sortedFormulas.size() - 1; outerIndex >= 0; outerIndex--) {
            for (int innerIndex = outerIndex - 1; innerIndex >= 0; innerIndex--) {
                ModalFormula outer = sortedFormulas.get(outerIndex).getFormula();
                ModalFormula inner = sortedFormulas.get(innerIndex).getFormula();

                Set<TreePath> occurencesOfInnerInOuter = outer.getAllOccurrences(inner);
                Variable replacement = sortedFormulas.get(innerIndex).getReplacement();
                for (TreePath path : occurencesOfInnerInOuter) {
                    outer = (ModalFormula) outer.transform(path, replacement);
                }
                sortedFormulas.get(outerIndex).setFormula(outer);
            }
        }

        List<ModalFormula> partialFormulas = new ArrayList<>(sortedFormulas.size());

        sortedFormulas.forEach(
                subformula -> {
                    partialFormulas.add(
                            tseitinCNF.apply(
                                    new Equivalence(
                                            subformula.getReplacement(), subformula.getFormula())));
                });

        partialFormulas.add(
                new Variable(new IndexedSymbol(variableNames, "" + newVariableCounter, null)));

        ModalFormula resultFormula =
                removeParenthesesTransformation.apply(new Conjunction(partialFormulas));

        return resultFormula;
    }

    private class SizeSortedFormula implements Comparable<SizeSortedFormula> {
        int size;
        ModalFormula formula;

        Variable replacement;

        public SizeSortedFormula(ModalFormula formula) {
            this.size = formula.getSize();
            this.formula = formula;
        }

        public ModalFormula getFormula() {
            return formula;
        }

        public void setFormula(ModalFormula formula) {
            this.formula = formula;
        }

        public int compareTo(SizeSortedFormula other) {
            return this.size - other.size;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof SizeSortedFormula)) {
                return false;
            }

            return ((SizeSortedFormula) other).formula.equals(this.formula);
        }

        public void setReplacement(Variable replacement) {
            this.replacement = replacement;
        }

        public Variable getReplacement() {
            return replacement;
        }
    }

    @Override
    public Transformation<ModalFormula> forPath(final TreePath path) {
        throw new UnsupportedOperationException(
                "Tseitin transformations are not intended to be used on subformulae");
    }

    /**
     * registry must have needed transformations registered
     *
     * @param registry
     */
    private void setUpTransformations(TransformationRegistry<ModalFormula> registry) {
        /*
        reason this is outcommented:
        Eventough the Tseitin transformation is currently not used in the tool anywhere gwt still considers the
        possibility of it being used as client code. That means gwt also considers the possibility of client code making use
        of the XmlParsers being used on the client. This however is not supported.
        For the future: The error thrown when trying to build the tool just says "IOException: Stream closed"

        TransformationRegistry<ModalFormula> registry = new TransformationRegistry<>();
        TransformationList<ModalFormula> trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/implication.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/equivalence.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/commutation.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/associativity.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/singleInvertibleTrafo.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/constants.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/deMorgan.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/distribution.xml",
                registry
        );
        trafos = XmlModalTransformationLoader.readTransformationsFromFile(
                "de/tudortmund/cs/iltis/logiclib/modal/transformations/xml/algorithms.xml",
                registry
        );*/

        removeParenthesesTransformation = registry.get("fixpoint:REMOVE_PARENTHESES");
        tseitinCNF = registry.get("CONJUNCTIVE_NORMAL_FORM_TSEITIN");
    }
}
