package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.*;

public class Graph<VertexType, EdgeClass> extends BaseGraph<VertexType, EdgeClass> {

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    @Override
    public Vertex<VertexType> addVertex(VertexType v) {
        return addVertex(v, getNewId());
    }

    @Override
    public Vertex<VertexType> addVertex(VertexType v, String id) {
        Vertex<VertexType> vertex = new Vertex<>(v, id);
        vertices.add(vertex);

        dispatch("graph.vertex.add", id);

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
