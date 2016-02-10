package eu.libal.intrographs;

import java.util.*;

public class Graph<VertexType, EdgeClass> extends BaseGraph<VertexType, EdgeClass> {

    Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    public void addVertex(VertexType v) {
        addVertex(v, getNewId());
    }
    public void addVertex(VertexType v, String id) {
        vertices.add( new Vertex<VertexType>(v, id) );
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
