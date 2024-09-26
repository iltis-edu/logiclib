package de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers;

import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.*;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import java.util.*;

public abstract class SmtlibSolver extends FOSolver {

    protected FOSmtlibFormulaWriter writer;
    protected boolean useExtraZero;

    protected SmtlibSolver(boolean useExtraZero) {
        this.useExtraZero = useExtraZero;
        initConnection();
    }

    /** Initialize connection to the weblib. */
    protected abstract void initConnection();

    @Override
    public Answer solve(final Formula formula) {
        // since the smtlib input format is used to check for satisfiability
        // we need to check if the negation is unsatisfiable
        String smtlibInput =
                getConstantDefinitionPrefix()
                        + translateToSmtlibInput(formula, false)
                        + getConstantPostfix();

        return callWeblibWithInput(smtlibInput);
    }

    // overriding this method is not necessary, but it improves the performance of smtlib solvers a
    // bit
    @Override
    public Answer solveEquivalenceUnderConstraints(
            final Formula formula1, final Formula formula2, List<Formula> constraints) {
        if (constraints.isEmpty()) {
            return solveEquivalence(formula1, formula2);
        }

        String smtlibInput =
                getConstantDefinitionPrefix()
                        + translateToSmtlibInput(formula1, formula2, constraints, false)
                        + getConstantPostfix();

        return callWeblibWithInput(smtlibInput);
    }

    protected String translateToSmtlibInput(final Formula formula, boolean replaceLinebreak) {
        String smtlibInput =
                getSmtlibDefinitions(formula.getSignature())
                        + getSmtlibFreeVariableDefinitions(formula.getFreeVariables())
                        + formatFormulaToSmtlibAssertion(new Negation(formula))
                        + getOptionalCheckSatPostfix();

        if (replaceLinebreak) {
            return smtlibInput.replaceAll("\\\\n", "\n");
        } else {
            return smtlibInput;
        }
    }

    protected String translateToSmtlibInput(
            final Formula formula1,
            final Formula formula2,
            List<Formula> constraints,
            boolean replaceLinebreak) {

        Formula negatedEquivalence = new Negation(new Equivalence(formula1, formula2));

        // dummy formula to easily extract all freevariables and most general signature
        List<Formula> allFormulas = new LinkedList<>();
        allFormulas.add(negatedEquivalence);
        allFormulas.addAll(constraints);
        Formula dummy = new Conjunction(allFormulas);

        Set<Variable> freeVariables = dummy.getFreeVariables();
        Signature signature = dummy.getSignature();

        StringBuilder smtlibInput = new StringBuilder();

        smtlibInput.append(getSmtlibDefinitions(signature));
        smtlibInput.append(getSmtlibFreeVariableDefinitions(freeVariables));

        for (Formula constraint : constraints) {
            smtlibInput.append(formatFormulaToSmtlibAssertion(constraint));
        }
        smtlibInput.append(formatFormulaToSmtlibAssertion(negatedEquivalence));
        smtlibInput.append(getOptionalCheckSatPostfix());

        if (replaceLinebreak) {
            return smtlibInput.toString().replaceAll("\\\\n", "\n");
        } else {
            return smtlibInput.toString();
        }
    }

    public String translateToSmtlibInput(final Formula formula) {
        return translateToSmtlibInput(formula, true);
    }

    public String translateToSmtlibInput(
            final Formula formula1, final Formula formula2, List<Formula> constraints) {
        return translateToSmtlibInput(formula1, formula2, constraints, true);
    }

    protected Answer callWeblibWithInput(String smtlibInput) {
        function.call(smtlibInput, handler);

        if (!answer.isPresent()) {
            return new FailedSolving();
        }

        String answerString = answer.get().replaceAll(" ", "");

        // if the negation is satisfiable the formula is not valid
        if (answerString.startsWith("{\"result\":\"SATISFIABLE")) {
            // model interpretation is unique for each solver and may be done there
            return parseModel(answerString);
        }

        if (answerString.equals("{\"result\":\"UNSATISFIABLE\"}")) {
            return new ValidFormula();
        }

        if (answerString.equals("{\"result\":\"UNKNOWN\"}")) {
            return new FailedSolving();
        }

        // this case is only reached if the server experiences other errors while computing the
        // result
        return new ServerFailure();
    }

    protected abstract Answer parseModel(String modelOutput);

    protected String getSmtlibDefinitions(final Signature signature) {
        StringBuilder formattedInput = new StringBuilder();

        if (sort.equals("CustomSort")) {
            formattedInput.append("(declare-sort ");
            formattedInput.append(sort);
            if (useExtraZero) {
                // For some reason vampire needs a "0" here to understand the input while z3 does
                // not
                formattedInput.append(" 0");
            }
            formattedInput.append(")\\n");
        }

        Set<RelationSymbol> relationSymbols = signature.getRelSymbols();
        for (RelationSymbol relationSymbol : relationSymbols) {
            // use default "=" implementation
            if (relationSymbol.equals(new RelationSymbol("=", 2, true))) {
                continue;
            }

            formattedInput.append("(declare-fun ");
            formattedInput.append(relationSymbol.toTextString());
            int arity = relationSymbol.getArity();
            if (arity > 0) {
                formattedInput.append(" (");
                for (int i = 0; i < arity - 1; i++) {
                    formattedInput.append(sort);
                    formattedInput.append(" ");
                }
                formattedInput.append(sort);
                formattedInput.append(")");
            }
            formattedInput.append(" Bool)\\n");
        }

        Set<FunctionSymbol> functionSymbols = signature.getFunSymbols();
        for (FunctionSymbol functionSymbol : functionSymbols) {
            formattedInput.append("(declare-fun ");
            formattedInput.append(functionSymbol.toTextString());
            int arity = functionSymbol.getArity();
            if (arity > 0) {
                formattedInput.append(" (");
                for (int i = 0; i < arity - 1; i++) {
                    formattedInput.append(sort);
                    formattedInput.append(" ");
                }
                formattedInput.append(sort);
                formattedInput.append(")");
            } else {
                formattedInput.append(" ()");
            }
            formattedInput.append(" ");
            formattedInput.append(sort);
            formattedInput.append(")\\n");
        }

        return formattedInput.toString();
    }

    protected String getSmtlibFreeVariableDefinitions(final Set<Variable> freeVariables) {
        StringBuilder formattedInput = new StringBuilder();

        for (Variable variable : freeVariables) {
            formattedInput.append("(declare-const ");
            formattedInput.append(variable.toTextString());
            formattedInput.append(" ");
            formattedInput.append(sort);
            formattedInput.append(")\\n");
        }

        return formattedInput.toString();
    }

    protected String formatFormulaToSmtlibAssertion(final Formula formula) {

        return "(assert " + writer.write(formula) + ")\\n";
    }

    protected abstract String getConstantDefinitionPrefix();

    protected String getOptionalCheckSatPostfix() {
        if (useExtraZero) {
            // vampire needs the explicit call to check satisfiability
            return "(check-sat)\\n";
        }
        return "";
    }

    protected String getConstantPostfix() {
        return "\"\n}";
    }
}
