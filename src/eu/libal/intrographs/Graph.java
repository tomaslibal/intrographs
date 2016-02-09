package eu.libal.intrographs;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tlibal on 2/9/16.
 */
public class Graph<VertexType, EdgeClass> extends BaseGraph implements IGraph<VertexType, EdgeClass> {

    Graph() {
        vertices = new LinkedList<>();
        edges = new LinkedList<>();
    }

    @Override
    public void addVertex(VertexType v) {
        vertices.add(new Vertex<VertexType>(v));
    }

    @Override
    public void addEdge(EdgeClass e) {
        edges.add(e);
    }

    @Override
    public void removeVertex(Vertex v) {

    }

    @Override
    public void removeEdge(EdgeClass e) {

    }

    @Override
    public void removeEdges(List edges) {

    }

    @Override
    public void removeVertices(List vertices) {

    }

}
