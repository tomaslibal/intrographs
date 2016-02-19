package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class Graph<T, U extends Edge<T>> extends BaseGraph<T, U> {

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    @Override
    public Vertex<T> addVertex(T v) {
        return addVertex(v, getNewId());
    }

    @Override
    public Vertex<T> addVertex(T v, String id) {
        Vertex<T> vertex = new Vertex<>(v, id);
        vertices.add(vertex);

        dispatch("graph.vertex.add", id);

        return vertex;
    }

    public Vertex<T> addVertex(String id, double x, double y) {
        Vertex<T> vertex = new Vertex<>(id);
        vertices.add(vertex);

        dispatch("graph.vertex.add", id.concat(";x:").concat(String.valueOf(x)).concat(";y:").concat(String.valueOf(y)));

        return vertex;
    }

    @Override
    public U addEdge(String sourceId, String targetId) {
        Optional<Vertex<T>> source = getVertexById(sourceId);
        Optional<Vertex<T>> target = getVertexById(targetId);

        if (!source.isPresent()
            || !target.isPresent()) {
            // should throw
            return null;
        }

        U e = (U) new Edge<>(source.get(), target.get());
        return addEdge(e);
    }

    @Override
    public U addEdge(U e) {
        if (edges.add(e)) {

            dispatch("graph.edge.add", "source:".concat(e.getSource().getId()).concat(";target:").concat(e.getTarget().getId()));

            /*
             * Each vertex keeps a set of adjacent nodes. Here we reciprocally add the adjacent vertices.
             */
            e.getSource().addAdjacentVertex(e.getTarget());
            e.getTarget().addAdjacentVertex(e.getSource());

            return e;
        } else {
            return null;
        }
    }

    @Override
    public Set<Vertex<T>> vertexSet() {
        return vertices;
    }

    @Override
    public Set<U> edgeSet() {
        return edges;
    }

    @Override
    public boolean removeVertex(String vertexId) {

        List<Vertex<T>> lookup = vertices.stream().filter(v -> v.getId().equals(vertexId)).collect(Collectors.toList());

        return lookup.size() == 1 && removeVertex(lookup.get(0));
    }

    public boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    public boolean removeEdge(U e) {
        return edges.remove(e);
    }

    public boolean removeEdges(List<U> e) {
        return edges.removeAll(e);
    }

    public boolean removeVertices(List<Vertex<T>> v) {
        return vertices.removeAll(v);
    }

}
