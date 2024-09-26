package de.tudortmund.cs.iltis.logiclib.modal.formula;

import de.tudortmund.cs.iltis.logiclib.baselogic.utils.ConstrainedPathCollector;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalFormulaReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.ModalReaderProperties;
import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterHTML;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterSafeText;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.formula.ModalFormulaWriterText;
import de.tudortmund.cs.iltis.logiclib.modal.formula.utils.PathCollector;
import de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy.*;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.general.ParsablyTyped;
import de.tudortmund.cs.iltis.utils.term.ComparableTerm;
import de.tudortmund.cs.iltis.utils.tree.DefaultTraversalStrategy;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Basic representation for modal logic and propositional logic formulae. A formula can either be
 * constructed directly via their constructors:
 *
 * <pre>
 * {
 * 	&#64;code
 * 	Variable varA = new Variable("A");
 * 	Variable varB = new Variable("B");
 * 	Conjunction andAB = new Conjunction(varA, varB);
 * }
 * </pre>
 *
 * or in a more functional manner:
 *
 * <pre>
 * {
 * 	&#64;code
 * 	ModalFormula phi = varA.and(varB.not(), varA.implies(varB));
 * }
 * </pre>
 *
 * The latter idea comes from the <i>jLogical</i> project by Lucku (<a href=
 * "https://github.com/Lucku/jLogical">https://github.com/Lucku/jLogical</a>).
 */
public abstract class ModalFormula extends ComparableTerm<ModalFormula, IndexedSymbol>
        implements ParsablyTyped, Serializable {
    public static final True TOP = new True();
    public static final False BOTTOM = new False();

    // needed for serialization
    public ModalFormula() {}

    /////////////////////////////////////////////////////////////////
    // PUBLIC
    /////////////////////////////////////////////////////////////////

    public static ModalFormula parse(final String formula, ModalReaderProperties props)
            throws IncorrectParseInputException {
        return new ModalFormulaReader(props).read(formula);
    }

    public static ModalFormula parse(final String formula) throws IncorrectParseInputException {
        return parse(formula, ModalReaderProperties.createDefaultWithLatex());
    }

    public static Optional<ModalFormula> tryToParse(
            final String formula, ModalReaderProperties props) {
        try {
            return Optional.of(ModalFormula.parse(formula, props));
        } catch (IncorrectParseInputException e) {
            return Optional.empty();
        }
    }

    public static TreePattern<ModalFormula> readPattern(final String pattern) {
        return new PatternReader().read(pattern);
    }

    public ModalFormula(IndexedSymbol name) {
        super(true, name);
    }

    public ModalFormula(boolean arityFixed) {
        super(arityFixed);
    }

    public ModalFormula(boolean arityFixed, final ModalFormula... subterms) {
        super(arityFixed, subterms);
    }

    public ModalFormula(boolean arityFixed, final Iterable<? extends ModalFormula> subterms) {
        super(arityFixed, subterms);
    }

    public ModalFormula(boolean arityFixed, final IndexedSymbol name) {
        super(arityFixed, name);
    }

    public ModalFormula(
            boolean arityFixed, final IndexedSymbol name, final ModalFormula... subterms) {
        super(arityFixed, name, subterms);
    }

    public ModalFormula(
            boolean arityFixed,
            final IndexedSymbol name,
            final Iterable<? extends ModalFormula> subterms) {
        super(arityFixed, name, subterms);
    }

    public abstract ModalFormula clone();

    public ModalFormula sanitize() {
        Transformation sanitizer = new SanitizeTransformation();

        if (sanitizer.isApplicable(this)) {
            return sanitizer.apply(this);
        }

        return this;
    }

    /**
     * There are transformations (e.g. distributivity) that can result in formulas in which some
     * conjunctions/disjunctions have less than 2 children. This is problematic when applying other
     * transformations on this formula or checking for syntactic equivalence. This method returns a
     * cleaned formula.
     *
     * @return formula without conjunction/disjunctions with less than 2 children
     */
    public ModalFormula removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers() {
        ModalFormula transformedFormula = this.clone();

        // get paths of Conjunctions/Disjunctions with wrong number of children
        Set<TreePath> problemPaths =
                transformedFormula.traverse(
                        new ConstrainedPathCollector<ModalFormula>(
                                t ->
                                        (t.isConjunction() || t.isDisjunction())
                                                && t.getNumberOfChildren() <= 1));

        // when replacing subtrees by their child the paths of other possible problematic
        // subformulas in the child change
        // in order to prevent having to recalculate them, we sort the paths by descending size
        List<TreePath> sortedProblemPaths =
                problemPaths.stream()
                        .sorted((t1, t2) -> t2.size() - t1.size())
                        .collect(Collectors.toList());

        for (TreePath path : sortedProblemPaths) {
            ModalFormula toBeReplaced = transformedFormula.retrieve(path);
            if (toBeReplaced.getNumberOfChildren() == 1) {
                transformedFormula =
                        (ModalFormula) transformedFormula.transform(path, toBeReplaced.getChild(0));
            } else {
                transformedFormula = (ModalFormula) transformedFormula.transform(path, null);
            }
        }

        return transformedFormula;
    }

    /**
     * Transforms a horn formula that contains "(1 -> A)" subformulas into one that does not. If the
     * initial formula does not contain such subformulas nothing is changed.
     *
     * @return the horn formula
     */
    public ModalFormula removeTopLeftFromHornFormula() {
        Transformation fixpointTransformation =
                new FixpointAlgorithm(new UnaryInvertiblePatternTransformation("1 -> $X", "$X"));
        return fixpointTransformation
                .apply(this)
                .removeDisjunctionsAndConjunctionsWithWrongChildrenNumbers();
    }

    public String toString() {
        return this.toTextString();
    }

    public String toTextString() {
        ModalFormulaWriterText w = new ModalFormulaWriterText();
        return w.chop(this.traverse(w));
    }

    public String toSafeString() {
        ModalFormulaWriterSafeText w = new ModalFormulaWriterSafeText();
        return w.chop(this.traverse(w));
    }

    public String toHTMLString() {
        ModalFormulaWriterHTML w = new ModalFormulaWriterHTML();
        return w.chop(this.traverse(w));
    }

    // INFORMATIONAL ================================================
    public boolean isPropositional() {
        return getModalDepth() == 0;
    }

    public abstract FormulaType getType();

    public Set<Variable> getVariables() {
        return this.traverse(new VariableCollector());
    }

    public List<ModalFormula> getSubformulae() {
        return this.getChildren();
    }

    public Set<ModalFormula> getAllSubformulae() {
        return this.traverse(new SubformulaeCollector());
    }

    public int getSize() {
        return this.traverse(new SizeCounter());
    }

    public int getDepth() {
        return this.traverse(new DepthCounter());
    }

    public int getModalDepth() {
        return this.traverse(new ModalDepthCounter());
    }

    public Set<TreePath> getAllOccurrences(ModalFormula formula) {
        return this.traverse(new OccurrencesCollector(formula));
    }

    public Set<TreePath> getAllOccurrences(Set<ModalFormula> formulae) {
        return this.traverse(new OccurrencesCollector(formulae));
    }

    public Set<TreePath> getAllApplications(Transformation transformation) {
        return this.traverse(new ApplicationCollector(transformation));
    }

    public boolean isInConjunctiveNormalform() {
        if (isConstant() || isLiteral() || isSingleJunctionWithoutConstants()) {
            return true;
        }

        return isConjunction()
                && children.stream()
                        .allMatch(
                                formula ->
                                        formula.isFalse()
                                                || formula.isLiteral()
                                                || formula.isValidCNFDisjunction());
    }

    private boolean isValidCNFDisjunction() {
        return isDisjunction() && children.stream().allMatch(ModalFormula::isLiteral);
    }

    public boolean isInDisjunctiveNormalform() {
        if (isConstant() || isLiteral() || isSingleJunctionWithoutConstants()) {
            return true;
        }

        return isDisjunction()
                && children.stream()
                        .allMatch(
                                formula ->
                                        formula.isTrue()
                                                || formula.isLiteral()
                                                || formula.isValidDNFConjunction());
    }

    private boolean isValidDNFConjunction() {
        return isConjunction() && children.stream().allMatch(ModalFormula::isLiteral);
    }

    private boolean isSingleJunctionWithoutConstants() {
        return (isConjunction() || isDisjunction())
                && children.stream().allMatch(ModalFormula::isLiteral);
    }

    public boolean isInNegationNormalform() {
        Set<ModalFormula> subformulae = getAllSubformulae();
        return subformulae.stream()
                .allMatch(
                        formula ->
                                !formula.isImplication()
                                        && !formula.isEquivalence()
                                        && (!formula.isNegation()
                                                || formula.getChild(0).isVariable()));
    }

    public boolean isDisjunctiveHornFormula() {
        if (isLiteral()) {
            return true;
        }

        return isConjunction()
                && children.stream()
                        .allMatch(
                                formula ->
                                        formula.isLiteral() || formula.isDisjunctiveHornClause());
    }

    private boolean isDisjunctiveHornClause() {
        if (!isDisjunction()) {
            return false;
        }

        int positiveLiterals = 0;
        for (ModalFormula subformula : children) {
            if (!subformula.isLiteral()) {
                return false;
            }

            if (subformula.isVariable()) {
                positiveLiterals++;
            }
        }
        return positiveLiterals <= 1;
    }

    public boolean isImplicativeHornFormula() {
        if (isVariable()) {
            return true;
        }

        return isConjunction()
                && children.stream()
                        .allMatch(
                                formula ->
                                        formula.isVariable()
                                                || isImplicativeHornClause(formula, false));
    }

    public boolean isImplicativeHornFormulaWithTopLeft() {
        if (isVariable()) {
            return true;
        }

        return isConjunction()
                && children.stream()
                        .allMatch(
                                formula ->
                                        formula.isVariable()
                                                || isImplicativeHornClause(formula, true));
    }

    private boolean isImplicativeHornClause(ModalFormula formula, boolean allowTopLeft) {
        if (!formula.isImplication()) {
            return false;
        }

        ModalFormula premise = formula.getChild(0);
        ModalFormula conclusion = formula.getChild(1);

        if (!conclusion.isVariable() && !conclusion.isFalse()) {
            return false;
        }

        if (premise.isTrue()) {
            return allowTopLeft;
        }

        return premise.isVariable()
                || (premise.isConjunction()
                        && premise.children.stream().allMatch(ModalFormula::isVariable));
    }

    // FORMULA CONSTRUCTORS =========================================
    // Modal operators ----------------------------------------------
    public Diamond diamond() {
        return new Diamond(this);
    }

    public Box box() {
        return new Box(this);
    }

    // Propositional operators --------------------------------------
    public Conjunction and(final ModalFormula... rightSubformulae) {
        return new Conjunction(this, rightSubformulae);
    }

    public Conjunction and(final Collection<ModalFormula> subformulae) {
        return new Conjunction(this, subformulae);
    }

    public Disjunction or(final ModalFormula... rightSubformulae) {
        return new Disjunction(this, rightSubformulae);
    }

    public Disjunction or(final Collection<ModalFormula> subformulae) {
        return new Disjunction(this, subformulae);
    }

    public Implication implies(final ModalFormula rightSubformula) {
        return new Implication(this, rightSubformula);
    }

    public Equivalence equivalent(final ModalFormula rightSubformula) {
        return new Equivalence(this, rightSubformula);
    }

    public Negation not() {
        return new Negation(this);
    }

    // SUBFORMULAE ==================================================
    public Iterator<ModalFormula> getSubformulaIterator() {
        return this.getChildren().iterator();
    }

    public ModalFormula getSubformula(final int index) {
        return this.getChild(index);
    }

    public int getSubformulaCount() {
        return this.getNumberOfChildren();
    }

    public Set<TreePath> getSubformulaPaths() {
        return this.traverse(new ConstrainedPathCollector<ModalFormula>(f -> true));
    }

    // FORMULA DECONSTRUCTORS =======================================
    public boolean isDiamond() {
        return this.getType() == FormulaType.DIAMOND;
    }

    public boolean isBox() {
        return this.getType() == FormulaType.BOX;
    }

    public boolean isConjunction() {
        return this.getType() == FormulaType.AND;
    }

    public boolean isDisjunction() {
        return this.getType() == FormulaType.OR;
    }

    public boolean isImplication() {
        return this.getType() == FormulaType.IMPLIES;
    }

    public boolean isEquivalence() {
        return this.getType() == FormulaType.EQUIV;
    }

    public boolean isLiteral() {
        return isVariable() || (isNegation() && getSubformula(0).isVariable());
    }

    public boolean isNegation() {
        return this.getType() == FormulaType.NEG;
    }

    public boolean isVariable() {
        return this.getType() == FormulaType.VARIABLE;
    }

    public boolean isTrue() {
        return this.getType() == FormulaType.TRUE;
    }

    public boolean isFalse() {
        return this.getType() == FormulaType.FALSE;
    }

    public boolean isAtomic() {
        return isConstant() || isVariable();
    }

    public boolean isConstant() {
        return isTrue() || isFalse();
    }

    /////////////////////////////////////////////////////////////////
    // PROTECTED
    /////////////////////////////////////////////////////////////////

    // TODO new patterns may be used for substitution

    /////////////////////////////////////////////////////////////////
    // PRIVATE
    /////////////////////////////////////////////////////////////////

    // TRAVERSAL STRATEGIES =========================================
    // VariableCollector --------------------------------------------
    private static class VariableCollector
            extends DefaultTraversalStrategy<ModalFormula, Set<Variable>, Set<Variable>> {
        @Override
        protected Set<Variable> value(Set<Variable> collectedValue, ModalFormula item) {
            if (item.isVariable()) {
                Set<Variable> var = new TreeSet<>();
                var.add((Variable) item);
                return var;
            } else if (item.isTrue() || item.isFalse()) return new TreeSet<>();
            else return collectedValue;
        }

        @Override
        protected Set<Variable> collect(Set<Variable> collectedValue, Set<Variable> nextValue) {
            if (collectedValue == null) return nextValue;
            collectedValue.addAll(nextValue);
            return collectedValue;
        }
    }

    // SubformulaeCollector -----------------------------------------
    private static class SubformulaeCollector
            extends DefaultTraversalStrategy<ModalFormula, Set<ModalFormula>, Set<ModalFormula>> {
        @Override
        protected Set<ModalFormula> value(Set<ModalFormula> collectedValue, ModalFormula item) {
            collectedValue.add(item);
            return collectedValue;
        }

        @Override
        protected Set<ModalFormula> collect(
                Set<ModalFormula> collectedValue, Set<ModalFormula> nextValue) {
            if (collectedValue == null) return new ListSet<>();
            collectedValue.addAll(nextValue);
            return collectedValue;
        }
    }

    // ModalDepthCounter --------------------------------------------
    private static class ModalDepthCounter
            extends DefaultTraversalStrategy<ModalFormula, Integer, Integer> {
        @Override
        protected Integer value(Integer collectedValue, ModalFormula item) {
            if (item.isDiamond() || item.isBox()) collectedValue++;
            return collectedValue;
        }

        @Override
        protected Integer collect(Integer collectedValue, Integer nextValue) {
            return collectedValue == null ? 0 : Math.max(collectedValue, nextValue);
        }
    }

    // DepthCounter -------------------------------------------------
    private static class DepthCounter
            extends DefaultTraversalStrategy<ModalFormula, Integer, Integer> {
        @Override
        protected Integer value(Integer collectedValue, ModalFormula item) {
            if (!item.isVariable() && !item.isTrue() && !item.isFalse()) collectedValue++;
            return collectedValue;
        }

        @Override
        protected Integer collect(Integer collectedValue, Integer nextValue) {
            return collectedValue == null ? 0 : Math.max(collectedValue, nextValue);
        }
    }

    // SizeCounter --------------------------------------------------
    private static class SizeCounter
            extends DefaultTraversalStrategy<ModalFormula, Integer, Integer> {
        @Override
        protected Integer value(Integer collectedValue, ModalFormula item) {
            if (item.isVariable() || item.isTrue() || item.isFalse()) return 1;
            if (item.isDiamond() || item.isBox() || item.isNegation()) return collectedValue + 1;
            return collectedValue + 2;
        }

        @Override
        protected Integer collect(Integer collectedValue, Integer nextValue) {
            return collectedValue == null ? 0 : collectedValue + nextValue + 1;
        }
    }

    // OccurrencesCollector -----------------------------------------
    private static class OccurrencesCollector extends PathCollector {
        public OccurrencesCollector(final ModalFormula formula) {
            this.formulae = new TreeSet<>();
            this.formulae.add(formula);
        }

        public OccurrencesCollector(final Set<ModalFormula> formulae) {
            this.formulae = formulae;
        }

        @Override
        protected boolean isSatisfying(ModalFormula item) {
            return this.formulae.contains(item);
        }

        private final Set<ModalFormula> formulae;
    }

    // ApplicationCollector -----------------------------------------
    private static class ApplicationCollector extends PathCollector {
        public ApplicationCollector(final Transformation transformation) {
            this.transformation = transformation;
        }

        @Override
        protected boolean isSatisfying(ModalFormula item) {
            return this.transformation.isApplicable(item);
        }

        private final Transformation transformation;
    }
}
