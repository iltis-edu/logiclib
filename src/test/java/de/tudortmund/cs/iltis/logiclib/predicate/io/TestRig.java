package de.tudortmund.cs.iltis.logiclib.predicate.io;

import static org.junit.Assert.fail;

import de.tudortmund.cs.iltis.logiclib.io.reader.predicate.formula.GeneralPredicateReader;
import de.tudortmund.cs.iltis.logiclib.predicate.VariableSymbol;
import de.tudortmund.cs.iltis.logiclib.predicate.signature.SignatureCheckable;
import de.tudortmund.cs.iltis.utils.collections.FaultCollection;
import de.tudortmund.cs.iltis.utils.io.parser.error.IncorrectParseInputException;
import de.tudortmund.cs.iltis.utils.io.parser.fault.ParsingFaultTypeMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 * Test rig for automated testing of readers
 *
 * @param <OutputT> output type of reader
 */
public abstract class TestRig<OutputT> {
    protected static GeneralPredicateReader<?, ?, ?, ?> reader;
    // elements should be: new Object[] {input : String,
    //                                   targetObject : ?,
    //                                   signature : SignatureCheckabel,
    //                                   allowedVarSet : Set<VariableSymbol>}
    protected static List<Object[]> positives;
    // elements should be: new Object[] {input : String,
    //                                   targetObject : ?
    //                                   signature : SignatureCheckable,
    //                                   allowedVarSet : Set<VariableSymbol>,
    //                                   faultCollectionClass : Class<? extends FaultCollection>,
    //                                   faultReason : Enum}
    protected static List<Object[]> neutrals;
    // elements should be: new Object[] {input : String,
    //                                   signature : SignatureCheckable,
    //                                   allowedVarSet : Set<VariableSymbol>}
    protected static List<Object[]> negatives;
    protected static boolean verbose = true;
    protected static boolean checkForAllowedVars = true;
    protected static boolean checkForNegativeTarget = true;

    @SuppressWarnings("unchecked")
    @Test
    public void testPositives() {
        if (positives == null) return;
        int no = 0;
        List<Integer> errorList = new ArrayList<>();
        for (Object[] entry : positives) {
            String testee = (String) entry[0];
            OutputT targetResult = (OutputT) entry[1];
            SignatureCheckable signature = (SignatureCheckable) entry[2];
            Set<VariableSymbol> allowedVars = (Set<VariableSymbol>) entry[3];
            if (verbose) System.out.println("Positive testee " + ++no + ": " + testee);
            try {
                OutputT actualResult;
                if (checkForAllowedVars)
                    actualResult = (OutputT) reader.read(testee, signature, allowedVars);
                else actualResult = (OutputT) reader.read(testee, signature);
                if (verbose) System.out.println("Result: " + actualResult);
                if (!targetResult.equals(actualResult)) {
                    if (verbose)
                        System.out.println(
                                ">>> Result not equal to expected result: " + targetResult);
                    errorList.add(no);
                }
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.out.println(">>> IncorrectParseInputException thrown");
                if (verbose) System.out.println(">>> Thrown term: " + mapping.getOutput());
                if (verbose) System.out.println(">>> Thrown mapping: " + mapping);
                errorList.add(no);
            } catch (Exception e) {
                if (verbose) e.printStackTrace(System.out);
                errorList.add(no);
            } finally {
                if (verbose) System.out.println();
            }
        }
        if (!errorList.isEmpty())
            fail("Errors occurred for the following positive test cases: " + errorList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNegatives() {
        if (negatives == null) return;
        int no = 0;
        List<Integer> errorList = new ArrayList<>();
        for (Object[] entry : negatives) {
            String testee = (String) entry[0];
            OutputT target = (OutputT) entry[1];
            SignatureCheckable signature = (SignatureCheckable) entry[2];
            Set<VariableSymbol> allowedVars = (Set<VariableSymbol>) entry[3];
            Class<? extends FaultCollection<?, ?>> supposedColType =
                    (Class<? extends FaultCollection<?, ?>>) entry[4];
            Enum<?> supposedReason = (Enum<?>) entry[5];
            if (verbose) System.out.println("Negative testee " + ++no + ": " + testee);
            try {
                OutputT actualResult;
                if (checkForAllowedVars)
                    actualResult = (OutputT) reader.read(testee, signature, allowedVars);
                else actualResult = (OutputT) reader.read(testee, signature);
                if (verbose) System.out.println(">>> No exception thrown for " + testee);
                if (verbose) System.out.println(">>> Instead output: " + actualResult);
                errorList.add(no);
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.out.println("Thrown term: " + mapping.getOutput());
                if (verbose) System.out.println("Thrown mapping: " + mapping);
                if (checkForNegativeTarget && target == null) {
                    if (mapping.getOutput() != null) {
                        if (verbose)
                            System.out.println(
                                    ">>> Thrown term unequal to expected term: " + target);
                        errorList.add(no);
                        continue;
                    }
                } else if (checkForNegativeTarget && !target.equals(mapping.getOutput())) {
                    if (verbose)
                        System.out.println(">>> Thrown term unequal to expected term: " + target);
                    errorList.add(no);
                    continue;
                }
                FaultCollection<?, ?> col = mapping.get(supposedColType);
                if (col == null) {
                    if (verbose)
                        System.out.println(
                                ">>> No mapping of type " + supposedColType + " present.");
                    errorList.add(no);
                    continue;
                }
                if (!col.getFaultMap().containsKey(supposedReason)) {
                    if (verbose)
                        System.out.println(">>> No reason " + supposedReason + " present.");
                    errorList.add(no);
                }
            } catch (Exception e) {
                if (verbose) e.printStackTrace(System.out);
                errorList.add(no);
            } finally {
                if (verbose) System.out.println();
            }
        }
        if (!errorList.isEmpty())
            fail("Errors occurred for the following negative test cases: " + errorList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNeutrals() {
        if (neutrals == null) return;
        int no = 0;
        for (Object[] entry : neutrals) {
            String testee = (String) entry[0];
            SignatureCheckable signature = (SignatureCheckable) entry[1];
            Set<VariableSymbol> allowedVars = (Set<VariableSymbol>) entry[2];
            if (verbose) System.out.println("Neutral testee " + ++no + ": " + testee);
            try {
                OutputT actualResult;
                if (checkForAllowedVars)
                    actualResult = (OutputT) reader.read(testee, signature, allowedVars);
                else actualResult = (OutputT) reader.read(testee, signature);
                if (verbose) System.out.println("Result: " + actualResult);
            } catch (IncorrectParseInputException e) {
                ParsingFaultTypeMapping<?> mapping = e.getFaultMapping();
                if (verbose) System.out.println(">>> IncorrectParseInputException thrown");
                if (verbose) System.out.println(">>> Thrown term: " + mapping.getOutput());
                if (verbose) System.out.println(">>> Thrown mapping: " + mapping);
            } catch (Exception e) {
                if (verbose) e.printStackTrace(System.out);
            } finally {
                if (verbose) System.out.println();
            }
        }
    }
}
