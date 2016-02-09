package eu.libal.intrographs;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Graph<VertexType, EdgeClass> extends BaseGraph implements IGraph<VertexType, EdgeClass> {

    Graph() {
        vertices = Collections.emptySet();
        edges = Collections.emptySet();
    }

    public void addVertex(VertexType v) {
        vertices.add(new Vertex<VertexType>(v));
    }

    public void addEdge(EdgeClass e) {
        edges.add(e);
    }

    @Override
    public Set<Vertex<VertexType>> vertexSet() {
        return vertices;
    }

    @Override
    public Set<EdgeClass> edgeSet() {
        return edges;
    }

    public void removeVertex(Vertex v) {

    }

    public void removeEdge(EdgeClass e) {

    }

    public void removeEdges(List edges) {

    }

    public void removeVertices(List vertices) {

    }

}
