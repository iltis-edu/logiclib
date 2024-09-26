package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.FormulaReader;
import de.tudortmund.cs.iltis.logiclib.predicate.FunctionSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.RelationSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.formula.Formula;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.RandomModelSolver;
import de.tudortmund.cs.iltis.logiclib.predicate.satisfiability.solvers.answertypes.InvalidFormulaWithCounterExample;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.Signature;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignaturePolicy;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.Pair;
import de.tudortmund.cs.iltis.utils.collections.PairLike;
import de.tudortmund.cs.iltis.utils.collections.SerializablePair;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.Test;

public class InterpretationTypeTranslationTest {

    @Test
    public void testIntegerStringTranslation() {
        Interpretation<Integer> interpretation = getElaborateExample();

        Map<Integer, IndexedSymbol> map = new HashMap<>();
        map.put(1, new IndexedSymbol("Alice"));
        map.put(2, new IndexedSymbol("Beth"));
        map.put(3, new IndexedSymbol("Chris"));
        map.put(4, new IndexedSymbol("Dina"));

        Interpretation<IndexedSymbol> translatedInterpretation = interpretation.translateType(map);
    }

    @Test
    public void testConstraintTranslation() {
        Interpretation<Integer> interpretation = getElaborateExample();

        ElementCreationPolicy numberPolicy = new ElementCreationPolicy("Person %NUMBER");
        ElementCreationPolicy letterPolicy = new ElementCreationPolicy("Person %LETTER");
        ElementCreationPolicy appendingPolicy = new ElementCreationPolicy("");

        List<PairLike<IndexedSymbol, Formula>> namingConstraints = new ArrayList<>(4);
        FormulaReader reader = new FormulaReader();
        SignatureCheckable signatureCheckable =
                SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY;

        // can be assigned
        Formula formulaAlice = reader.read("f(x) = x & P(x)", signatureCheckable);
        Formula formulaBeth = reader.read("!(f(x)=x) & (R(x,x) | !P(x))", signatureCheckable);
        // cant be assigned
        Formula formulaChris = reader.read("!P(x)", signatureCheckable);

        namingConstraints.add(new Pair<>(new IndexedSymbol("Alice"), formulaAlice));
        namingConstraints.add(new Pair<>(new IndexedSymbol("Beth"), formulaBeth));
        namingConstraints.add(new Pair<>(new IndexedSymbol("Chris"), formulaChris));
        // no constraint
        namingConstraints.add(new Pair<>(new IndexedSymbol("Dina"), null));

        Interpretation<IndexedSymbol> numberInterpretation =
                numberPolicy.translateToConstrainedSymbols(interpretation, namingConstraints);

        Interpretation<IndexedSymbol> letterInterpretation =
                letterPolicy.translateToConstrainedSymbols(interpretation, namingConstraints);

        Interpretation<IndexedSymbol> appendingInterpretation =
                appendingPolicy.translateToConstrainedSymbols(interpretation, namingConstraints);
    }

    @Test
    public void testWithIncompatibleSignatures() {
        Set<Integer> universeSizes = new HashSet<>(10);
        for (int i = 1; i <= 10; i++) {
            universeSizes.add(i);
        }
        RandomModelSolver solver = new RandomModelSolver(universeSizes, 0.5, 100, 0);

        FormulaReader reader = new FormulaReader();
        Signature signature =
                reader.read(
                                "R(x) & G(x,x) & x = x & f(x) = x & a= a & b = b",
                                SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY)
                        .getSignature();

        Formula formula1 = reader.read("forall x G(x,b) →  R(b)", signature);
        Formula formula2 = reader.read("⊤", signature);

        InvalidFormulaWithCounterExample counterExample =
                (InvalidFormulaWithCounterExample)
                        solver.solveEquivalenceUnderConstraints(
                                formula1, formula2, new ArrayList<>());

        Interpretation<Integer> counterModel = counterExample.getExample().get();

        ElementCreationPolicy numberPolicy = new ElementCreationPolicy("Person %NUMBER");

        Formula formulaAlice = reader.read("R(x)", signature);
        Formula formulaBeth = reader.read("exists y (!(x=y) ∧ G(x,y))", signature);
        Formula formulaChris = null;

        List<PairLike<IndexedSymbol, Formula>> namingConstraints = new ArrayList<>(3);

        namingConstraints.add(new Pair<>(new IndexedSymbol("Alice"), formulaAlice));
        namingConstraints.add(new Pair<>(new IndexedSymbol("Beth"), formulaBeth));
        namingConstraints.add(new Pair<>(new IndexedSymbol("Chris"), formulaChris));

        List<SerializablePair<IndexedSymbol, Formula>> parameters = new ArrayList<>();
        parameters.add(new SerializablePair<>(new IndexedSymbol("Alice"), formulaAlice));
        parameters.add(new SerializablePair<>(new IndexedSymbol("Beth"), formulaBeth));
        parameters.add(new SerializablePair<>(new IndexedSymbol("Chris"), null));

        namingConstraints =
                parameters.stream()
                        .map(p -> new Pair<>(p.first(), p.second()))
                        .collect(Collectors.toList());

        Interpretation<IndexedSymbol> interpretation =
                numberPolicy.translateToConstrainedSymbols(counterModel, namingConstraints);
    }

    @Test
    public void testSignatureExtension() {
        Interpretation<Integer> interpretation = getElaborateExample();

        Structure<Integer> structure = interpretation.getStructure();

        RelationSymbol relationSymbolS = new RelationSymbol(new IndexedSymbol("S"), 1, false);
        RelationSymbol relationSymbolT = new RelationSymbol(new IndexedSymbol("T"), 2, false);
        RelationSymbol relationSymbolP = new RelationSymbol(new IndexedSymbol("P"), 1, false);
        RelationSymbol relationSymbolR = new RelationSymbol(new IndexedSymbol("R"), 2, false);
        RelationSymbol relationSymbolEq = new RelationSymbol(new IndexedSymbol("="), 2, true);
        FunctionSymbol functionSymbolConstant =
                new FunctionSymbol(new IndexedSymbol("a"), 0, false);
        FunctionSymbol functionSymbolG = new FunctionSymbol(new IndexedSymbol("g"), 1, false);
        FunctionSymbol functionSymbolH = new FunctionSymbol(new IndexedSymbol("h"), 2, false);
        FunctionSymbol functionSymbolF = new FunctionSymbol(new IndexedSymbol("f"), 1, false);

        Set<RelationSymbol> relationSymbols = new HashSet<>();
        relationSymbols.add(relationSymbolS);
        relationSymbols.add(relationSymbolT);
        relationSymbols.add(relationSymbolP);
        relationSymbols.add(relationSymbolR);
        relationSymbols.add(relationSymbolEq);

        Set<FunctionSymbol> functionSymbols = new HashSet<>();
        functionSymbols.add(functionSymbolConstant);
        functionSymbols.add(functionSymbolG);
        functionSymbols.add(functionSymbolH);
        functionSymbols.add(functionSymbolF);

        Signature extensionSignature = new Signature(relationSymbols, functionSymbols);

        long start = System.currentTimeMillis();
        Interpretation<Integer> extendedInterpretation =
                interpretation.extendToSignature(extensionSignature);
        System.out.println(System.currentTimeMillis() - start + " ms");

        Structure<Integer> extendedStructure = extendedInterpretation.getStructure();
    }

    private Interpretation<Integer> getElaborateExample() {
        // we use the RandomModelSolver here to create an elaborate example interpretation
        Set<Integer> universeSizes = new HashSet<>(10);
        for (int i = 1; i <= 10; i++) {
            universeSizes.add(i);
        }
        RandomModelSolver solver = new RandomModelSolver(universeSizes, 0.5, 100, 0);

        FormulaReader reader = new FormulaReader();
        SignatureCheckable signatureCheckable =
                SignaturePolicy.TU_DORTMUND_LS1_LOGIK_POLICY_WITH_EQUALITY;

        Formula formula1 =
                reader.read(
                        "!R(x,y) & forall x exists y exists z (! (y = z) & R(x,y) & R(x,z) & P(y) & P(z))",
                        signatureCheckable);
        Formula formula2 =
                reader.read(
                        "!R(x,y) & forall z (( exists x f(x) = z) -> exists x exists y (R(x,y) & !(x = y) & f(x)=z & f(y)=z & P(x) & P(y)))",
                        signatureCheckable);
        Formula constraint =
                reader.read(
                        "exists x exists y exists z exists t (!(x=y) & !(x=z) & !(y=z) & !(x=t) & !(y=t) & !(z=t)) ",
                        signatureCheckable);

        InvalidFormulaWithCounterExample counterExample =
                (InvalidFormulaWithCounterExample)
                        solver.solveEquivalenceUnderConstraints(
                                formula1, formula2, Arrays.asList(constraint));

        return counterExample.getExample().get();
    }
}
