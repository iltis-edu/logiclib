package de.tudortmund.cs.iltis.logiclib.modal.formula;

import java.io.Serializable;

public class Literal implements Comparable<Literal>, Serializable {
    @SuppressWarnings("unused")
    protected Literal() { // Serialization
    }

    public Literal(boolean positive, Variable variable) {
        this.negative = !positive;
        this.variable = variable;
    }

    public Literal(boolean positive, String name) {
        this.negative = !positive;
        this.variable = new Variable(name);
    }

    public Literal(Literal right) {
        this.negative = right.negative;
        this.variable = right.variable;
    }

    public Literal(ModalFormula literal) {
        if (!isLiteral(literal))
            throw new IllegalArgumentException("Literal required. Got: '" + literal + "'");

        if (literal.isVariable()) {
            this.negative = false;
            this.variable = (Variable) literal;
        } else { // otherwise negated variable
            this.negative = true;
            this.variable = (Variable) literal.getSubformula(0);
        }
    }

    public static boolean isLiteral(ModalFormula formula) {
        if (formula.isVariable()) return true;

        if (formula.isNegation()) {
            Variable variable = (Variable) formula.getSubformula(0);
            return variable.isVariable();
        }

        return false;
    }

    public boolean isPositive() {
        return !this.negative;
    }

    public boolean isNegative() {
        return this.negative;
    }

    public Variable getVariable() {
        return this.variable;
    }

    public ModalFormula toFormula() {
        if (this.negative) return this.variable.not();
        return this.variable;
    }

    public Literal negated() {
        return new Literal(this.negative, this.variable.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Literal)) return false;
        Literal other = (Literal) o;
        return this.negative == other.negative && this.variable.equals(other.variable);
    }

    @Override
    public int hashCode() {
        int hash = this.getVariable().hashCode();
        return this.isPositive() ? hash : -hash;
    }

    @Override
    public int compareTo(Literal other) {
        final int variableCompare = this.variable.compareTo(other.variable);
        if (variableCompare == 0) {
            if (this.negative && !other.negative) return -1;
            if (!this.negative && other.negative) return +1;
            return 0;
        }
        return variableCompare;
    }

    @Override
    public String toString() {
        return this.toFormula().toString();
    }

    public Literal clone() {
        return new Literal(!this.negative, this.variable.clone());
    }

    private boolean negative;
    private Variable variable;
}
