package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public EdgeClass addEdge(String sourceId, String targetId) {
        Optional<Vertex<VertexType>> source = getVertexById(sourceId);
        Optional<Vertex<VertexType>> target = getVertexById(targetId);

        if (!source.isPresent()
            || !target.isPresent()) {
            // should throw
            return null;
        }

        EdgeClass e = (EdgeClass) new Edge<>(source.get(), target.get());
        if (edges.add(e)) {
            return e;
        } else {
            return null;
        }
    }

    @Override
    public EdgeClass addEdge(EdgeClass e) {
        if (edges.add(e)) {
            return e;
        } else {
            return null;
        }
    }

    @Override
    public Set<Vertex<VertexType>> vertexSet() {
        return vertices;
    }

    @Override
    public Set<EdgeClass> edgeSet() {
        return edges;
    }

    @Override
    public boolean removeVertex(String vertexId) {

        List<Vertex<VertexType>> lookup = vertices.stream().filter(v -> v.getId().equals(vertexId)).collect(Collectors.toList());

        return lookup.size() == 1 && removeVertex(lookup.get(0));
    }

    public boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    public boolean removeEdge(EdgeClass e) {
        return edges.remove(e);
    }

    public boolean removeEdges(List e) {
        return edges.removeAll(e);
    }

    public boolean removeVertices(List v) {
        return vertices.removeAll(v);
    }

}
