package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.io.writer.predicate.formula.FOFormulaWriterText;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.RelationAtom;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.TermOrFormula;
import de.tudortmund.cs.iltis.utils.io.writer.general.LatexIndexedSymbolWriter;
import java.util.List;

public class FOPrincessFormulaWriter extends FOFormulaWriterText {

    private String sort;

    @Override
    protected String getExistentialQuantifier() {
        return " \\\\exists " + sort + " ";
    }

    @Override
    protected String getUniversalQuantifier() {
        return " \\\\forall " + sort + " ";
    }

    @Override
    protected String getQuantifierSeparator() {
        return " ";
    }

    @Override
    protected String getConjunction() {
        return " & ";
    }

    @Override
    protected String getDisjunction() {
        return " | ";
    }

    @Override
    protected String getImplication() {
        return " -> ";
    }

    @Override
    protected String getEquivalence() {
        return " <-> ";
    }

    @Override
    protected String getNegation() {
        return "! ";
    }

    @Override
    protected String getTrue() {
        return " true ";
    }

    @Override
    protected String getFalse() {
        return " false ";
    }

    @Override
    protected String inspect(RelationAtom item, List<String> subformulae) {
        return " " + super.inspect(item, subformulae) + " ";
    }

    public FOPrincessFormulaWriter() {
        this("CustomSort");
    }

    public FOPrincessFormulaWriter(String sort) {
        this.sort = sort;
        symbolWriter = new LatexIndexedSymbolWriter();
    }

    @Override
    public String write(TermOrFormula formula) {
        String preComputation = super.write(formula);

        // add ";" after quantifiers
        String patternStringMatch = "(exists|forall)( " + sort + ")(\\s+)(\\S+)(\\s+)";
        String patternStringReplace = "$1$2$3$4$5; ";

        preComputation = preComputation.replaceAll(patternStringMatch, patternStringReplace);

        // compress spaces to single ones
        patternStringMatch = "\\s+";
        patternStringReplace = " ";

        return preComputation.replaceAll(patternStringMatch, patternStringReplace);
    }

    /** for serialization */
    private static final long serialVersionUID = 1L;
}
