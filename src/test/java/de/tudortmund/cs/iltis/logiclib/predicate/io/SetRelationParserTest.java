package de.tudortmund.cs.iltis.logiclib.predicate.io;

import static org.junit.Assert.*;

import de.tudortmund.cs.iltis.logiclib.io.reader.relation.SetRelationReader;
import de.tudortmund.cs.iltis.logiclib.io.reader.relation.SetRelationReaderProperties;
import de.tudortmund.cs.iltis.logiclib.predicate.interpretation.ConcreteRelation;
import de.tudortmund.cs.iltis.utils.IndexedSymbol;
import de.tudortmund.cs.iltis.utils.collections.Tuple;
import org.junit.Test;

public class SetRelationParserTest {

    @Test
    public void testSetRelationParser() {
        SetRelationReaderProperties props = SetRelationReaderProperties.createDefault();
        SetRelationReader setRelationReader = new SetRelationReader(props);

        ConcreteRelation<IndexedSymbol> output =
                setRelationReader.read("{(a,b), (b,d,e), (), (d_e^w, f)}");

        assertEquals(4, output.getSet().size());
        assertTrue(
                output.getSet()
                        .contains(new Tuple<>(new IndexedSymbol("a"), new IndexedSymbol("b"))));
        assertFalse(
                output.getSet()
                        .contains(new Tuple<>(new IndexedSymbol("b"), new IndexedSymbol("a"))));
        assertTrue(
                output.getSet()
                        .contains(
                                new Tuple<>(
                                        new IndexedSymbol("b"),
                                        new IndexedSymbol("d"),
                                        new IndexedSymbol("e"))));
        assertTrue(output.getSet().contains(new Tuple<>()));
        assertTrue(
                output.getSet()
                        .contains(new Tuple<>(new IndexedSymbol("d^w_e"), new IndexedSymbol("f"))));
    }
}
