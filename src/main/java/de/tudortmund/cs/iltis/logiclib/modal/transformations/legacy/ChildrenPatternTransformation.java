package de.tudortmund.cs.iltis.logiclib.modal.transformations.legacy;

import de.tudortmund.cs.iltis.logiclib.io.reader.modal.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.modal.formula.ModalFormula;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.tree.TreePath;
import de.tudortmund.cs.iltis.utils.tree.pattern.TreePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.match.Match;
import java.util.Optional;

public class ChildrenPatternTransformation extends UnaryPatternTransformation
        implements InvertibleTransformation {

    private TreePattern<ModalFormula> outerPattern;

    // needed for serialization
    public ChildrenPatternTransformation() {}

    public ChildrenPatternTransformation(
            TreePattern<ModalFormula> outer,
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace) {

        this(new TreePath(), outer, match, replace);
    }

    public ChildrenPatternTransformation(String outer, String match, String replace) {
        this(new TreePath(), outer, match, replace);
    }

    public ChildrenPatternTransformation(
            final TreePath path,
            TreePattern<ModalFormula> outer,
            TreePattern<ModalFormula> match,
            TreePattern<ModalFormula> replace) {

        super(path, match, replace);
        this.outerPattern = outer;
    }

    public ChildrenPatternTransformation(
            final TreePath path, String outer, String match, String replace) {

        super(path, match, replace);

        try {
            this.outerPattern = new PatternReader().read(outer);
        } catch (IncorrectParseInputException e) {
            throw new RuntimeException("Pattern parse error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean isApplicable(final ModalFormula formula) {
        return outerPattern.matches(formula);
    }

    @Override
    public ModalFormula apply(ModalFormula formula) {
        ModalFormula result = formula;
        ModalFormula subformula = formula.retrieve(this.path);

        Optional<Match<ModalFormula>> optMatch = outerPattern.getFirstMatchIfAny(formula);

        if (!optMatch.isPresent()) {
            throw new TransformationUnapplicable(formula);
        }

        Match<ModalFormula> match = optMatch.get();

        int childrenIndex = 0;
        for (ModalFormula child : subformula.getSubformulae()) {

            Match<ModalFormula> childMatch =
                    matchPattern.getFirstMatchIfAny(child).get().withMatch(match).get();

            result =
                    replaceSubformula(
                            result,
                            this.path.clone().child(childrenIndex),
                            this.replacePattern.createTree(childMatch));

            childrenIndex++;
        }

        return result;
    }

    @Override
    public ChildrenPatternTransformation getInverse() {

        return new ChildrenPatternTransformation(
                path, this.outerPattern, this.replacePattern, this.matchPattern);
    }

    @Override
    public ChildrenPatternTransformation forPath(TreePath path) {

        return new ChildrenPatternTransformation(
                path, this.outerPattern, this.matchPattern, this.replacePattern);
    }
}
