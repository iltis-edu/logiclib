package de.tudortmund.cs.iltis.logiclib.predicate.interpretation;

public class IntUniverse extends SetUniverse<Integer> {
    public IntUniverse(int from, int to) {
        for (int i = from; i <= to; i++) this.addElement(i);
    }

    public IntUniverse() {}
}
