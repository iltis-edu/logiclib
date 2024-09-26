package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import de.tudortmund.cs.iltis.utils.collections.ListSet;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class IsomorphismEvaluator implements Serializable {

    private KripkeStructure firstKripke;
    private KripkeStructure secondKripke;
    private Map<KripkeState, KripkeState> map;
    private Map<KripkeState, ListSet<KripkeState>> matchingStates;

    // needed for serialization
    private IsomorphismEvaluator() {}

    public IsomorphismEvaluator(KripkeStructure firstKripke, KripkeStructure secondKripke) {

        this.firstKripke = firstKripke;
        this.secondKripke = secondKripke;
        this.map = new HashMap<>();
        this.matchingStates = new HashMap<>();
    }

    /**
     * @return contains the bijective map between the first Kripke-Structure and the second, if the
     *     structures are isomorphic
     */
    public Optional<Map<KripkeState, KripkeState>> checkIsomorphy() {

        if (this.firstKripke.getVertexValues().size()
                != this.secondKripke.getVertexValues().size()) {

            return Optional.empty();
        }

        if (checkForNonMatchingState()) {
            return Optional.empty();
        }

        Iterator<KripkeState> it = this.firstKripke.getVertexValues().iterator();
        if (it.hasNext()) {
            if (checkIsomorphy(it.next())) {
                return Optional.of(this.map);
            }
        }
        return Optional.empty();
    }

    // is called by checkIsomorphy
    private boolean checkForNonMatchingState() {
        for (KripkeState firstState : this.firstKripke.getVertexValues()) {
            ListSet<KripkeState> matchingStates = new ListSet<>();

            for (KripkeState secondState : this.secondKripke.getVertexValues()) {
                if (secondState.getValuation().equals(firstState.getValuation())) {

                    if (this.secondKripke.getInDegreeOf(secondState)
                            == this.firstKripke.getInDegreeOf(firstState)) {

                        if (this.secondKripke.getOutDegreeOf(secondState)
                                == this.firstKripke.getOutDegreeOf(firstState)) {
                            matchingStates.add(secondState);
                        }
                    }
                }
            }
            if (matchingStates.size() == 0) {
                return true;
            }
            this.matchingStates.put(firstState, matchingStates);
        }

        return false;
    }

    // calcutes via backtracking if the first Kripke-structure and the second
    // are isomorphic
    private boolean checkIsomorphy(KripkeState firstState) {
        if (this.map.containsKey(firstState)) {
            return true;
        }
        for (KripkeState secondState : this.matchingStates.get(firstState)) {
            if (!this.map.containsValue(secondState)) {
                this.map.put(firstState, secondState);

                boolean mapIsBijective = true;

                for (KripkeState firstNeighbor :
                        this.firstKripke.getOutNeighborValues(firstState)) {

                    if (!this.checkIsomorphy(firstNeighbor)) {
                        mapIsBijective = false;
                        break;
                    }
                }

                if (mapIsBijective) {
                    return true;
                } else {
                    this.map.remove(firstState);
                }
            }
        }
        return false;
    }
}
