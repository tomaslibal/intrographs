package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This type represents a graph. It is an undirected graph, which does not have to be simple (edges having the same
 * vertex as the source and the target are allowed). This is not a multigraph, so two edges cannot be incident on the
 * same vertex tuple. Because the Edge class is a parameter on this generic class, it is possible to use weighted or
 * unweighted edges as needed.
 *
 * @param <T> The type of the value which each Vertex stores
 * @param <U> The type of the Edge
 */
public class Graph<T, U extends Edge<T>> extends BaseGraph<T, U> {

    public Graph() {
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

    @Override
    public Optional<Vertex<T>> lookupVertex(String id) {
        return getVertexById(id);
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
    /**
     *
     * A graph keeps vertices in a set and each vertex keeps a list of adjacent vertices. Thus, the edges are embedded in
     * this adjacency list. However, the graph also keeps a separate set of Edges, which is a set representing the same
     * state captured by the adjacency list. This is so that the edge set does not have to be computed every time it
     * is requested.
     *
     * @return Set&lt;U&gt;
     */
    public Set<U> edgeSet() {
        return edges;
    }

    @Override
    public boolean removeVertex(String vertexId) {

        List<Vertex<T>> lookup = vertices.stream().filter(v -> v.getId().equals(vertexId)).collect(Collectors.toList());

        return lookup.size() == 1 && removeVertex(lookup.get(0));
    }

    public boolean removeVertex(Vertex v) {
        dispatch("graph.vertex.remove", v.getId());
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
