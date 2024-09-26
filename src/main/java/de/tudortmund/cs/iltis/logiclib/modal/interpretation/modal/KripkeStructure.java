package de.tudortmund.cs.iltis.logiclib.modal.interpretation.modal;

import de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation.modal.KripkeStateNameWriter;
import de.tudortmund.cs.iltis.logiclib.io.writer.modal.interpretation.modal.KripkeStatePropositionWriter;
import de.tudortmund.cs.iltis.utils.collections.ListSet;
import de.tudortmund.cs.iltis.utils.graph.Edge;
import de.tudortmund.cs.iltis.utils.graph.EmptyEdgeLabel;
import de.tudortmund.cs.iltis.utils.graph.Vertex;
import de.tudortmund.cs.iltis.utils.graph.hashgraph.DefaultHashGraph;
import de.tudortmund.cs.iltis.utils.io.writer.collections.SetWriter;
import de.tudortmund.cs.iltis.utils.io.writer.general.EmptyWriter;
import de.tudortmund.cs.iltis.utils.io.writer.graph.EdgeWriter;
import de.tudortmund.cs.iltis.utils.io.writer.graph.VertexWriter;
import java.io.Serializable;

public class KripkeStructure extends DefaultHashGraph<KripkeState> implements Serializable {

    public KripkeStructure() {
        super();
        KripkeStateNameWriter stateWriter = new KripkeStateNameWriter();
        EmptyWriter<EmptyEdgeLabel> emptyWriter = new EmptyWriter<>();
        this.vertexWriter = new SetWriter<>(new VertexWriter<>(stateWriter));
        this.edgeWriter =
                new SetWriter<>(new EdgeWriter<>(new VertexWriter<>(stateWriter), emptyWriter));
    }

    public KripkeStructure(KripkeStructure right) {
        super(right);
    }

    @Override
    public Vertex<KripkeState, EmptyEdgeLabel> addVertex(
            Vertex<KripkeState, EmptyEdgeLabel> vertex) {
        return this.addVertex(vertex.get());
    }

    @Override
    public KripkeStructure clone() {
        // produce flat copy first, not deep-copying values:
        KripkeStructure copy = new KripkeStructure(this);

        // now, clone values:
        for (Vertex<KripkeState, EmptyEdgeLabel> vertex : copy.getVertices())
            vertex.set(vertex.get().clone());

        return copy;
    }

    @Override
    public String toString() {
        KripkeStateNameWriter stateNameWriter = new KripkeStateNameWriter();
        SetWriter<KripkeState> stateWriter = new SetWriter<>(stateNameWriter);
        VertexWriter<KripkeState, EmptyEdgeLabel> vertexWriter =
                new VertexWriter<>(stateNameWriter);
        EdgeWriter<KripkeState, EmptyEdgeLabel> edgeWriter = new EdgeWriter<>(vertexWriter);
        SetWriter<Edge<KripkeState, EmptyEdgeLabel>> edgeSetWriter = new SetWriter<>(edgeWriter);
        SetWriter<KripkeState> propWriter = new SetWriter<>(new KripkeStatePropositionWriter());

        ListSet<KripkeState> states = new ListSet<>(super.getVertexValues());
        ListSet<Edge<KripkeState, EmptyEdgeLabel>> edges = new ListSet<>(super.getEdges());
        // TODO: Sort sets.

        String text = "(";
        text += stateWriter.write(states) + ", ";
        text += edgeSetWriter.write(edges) + ", ";
        text += propWriter.write(super.getVertexValues());
        return text + ")";
    }
}
