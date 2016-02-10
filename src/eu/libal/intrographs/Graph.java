package eu.libal.intrographs;

import java.util.*;

public class Graph<VertexType, EdgeClass> extends BaseGraph<VertexType, EdgeClass> {

    Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    @Override
    public Vertex<VertexType> addVertex(VertexType v) {
        return addVertex(v, getNewId());
    }
    public Vertex<VertexType> addVertex(VertexType v, String id) {
        Vertex<VertexType> vertex = (Vertex<VertexType>) new Vertex<>(v, id);
        vertices.add(vertex);

        return vertex;
    }

    public void addEdge(String sourceId, String targetId) {
        Optional<Vertex<VertexType>> source = getVertexById(sourceId);
        Optional<Vertex<VertexType>> target = getVertexById(targetId);

        if (!source.isPresent()
            || !target.isPresent()) {
            return;
        }

        edges.add((EdgeClass) new Edge<VertexType>(source.get(), target.get()));
    }

    private Optional<Vertex<VertexType>> getVertexById(String id) {
        return vertices.stream().filter(v -> v.getId().equals(id)).findFirst();
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
