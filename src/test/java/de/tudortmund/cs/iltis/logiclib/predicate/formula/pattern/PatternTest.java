package de.tudortmund.cs.iltis.logiclib.predicate.formula.pattern;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.pattern.PatternReader;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.*;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.term.pattern.AnyNamePattern;
import de.tudortmund.cs.iltis.utils.term.pattern.ExactNamePattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.AnyPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.ChildrenPattern;
import de.tudortmund.cs.iltis.utils.tree.pattern.FlexibleArityForestPattern;
import org.junit.Test;

public class PatternTest {

    @Test
    public void test() {
        PatternReader reader = new PatternReader();
        TermOrFormula top = new True();
        TermOrFormula bottom = new False();

        TruePattern tPattern = new TruePattern();
        assert (tPattern.matches(top));
        assert (!tPattern.matches(bottom));

        FalsePattern fPattern = new FalsePattern();
        assert (fPattern.matches(bottom));
        assert (!fPattern.matches(top));

        AnyNamePattern anyName = new AnyNamePattern(new IndexedSymbol("x"));
        Variable aVar = new Variable("a");
        Variable bVar = new Variable("b");
        VariablePattern vPattern = new VariablePattern(anyName);
        assert (vPattern.matches(aVar));
        assert (vPattern.matches(bVar));
        assert (!vPattern.matches(top));

        vPattern = new VariablePattern(new ExactNamePattern(new IndexedSymbol("a")));
        assert (vPattern.matches(aVar));
        assert (!vPattern.matches(bVar));

        ChildrenPattern flexibleChildren =
                new ChildrenPattern(new FlexibleArityForestPattern(new AnyPattern()));

        FunctionTerm fFunction = new FunctionTerm(new IndexedSymbol("f"));
        FunctionTerm gFunction = new FunctionTerm("g", aVar, bVar);
        FunctionTermPattern ftPattern = new FunctionTermPattern(anyName, flexibleChildren);
        assert (ftPattern.matches(fFunction));
        assert (ftPattern.matches(gFunction));
        assert (!ftPattern.matches(aVar));

        RelationAtom SRelation = new RelationAtom("S");
        RelationAtom PRelation = new RelationAtom("P", gFunction);
        RelationAtomPattern rPattern = new RelationAtomPattern(anyName, flexibleChildren);
        assert (rPattern.matches(SRelation));
        assert (rPattern.matches(PRelation));
        assert (!rPattern.matches(gFunction));

        Negation neg = new Negation((Formula) top);
        NegationPattern nPattern = new NegationPattern(flexibleChildren);
        assert (nPattern.matches(neg));
        assert (!nPattern.matches(PRelation));

        Conjunction conj = new Conjunction(SRelation, PRelation);
        ConjunctionPattern cPattern = new ConjunctionPattern(flexibleChildren);
        assert (cPattern.matches(conj));
        assert (!cPattern.matches(neg));
    }
}
