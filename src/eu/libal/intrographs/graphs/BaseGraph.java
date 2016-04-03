package eu.libal.intrographs.graphs;

import eu.libal.intrographs.common.IListenable;
import eu.libal.intrographs.common.INotifiable;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

abstract public class BaseGraph<T, U extends Edge<T>> implements IGraph<T, U>, IListenable {

    protected final Set<Vertex<T>> vertices = new HashSet<>();
    protected final Set<U> edges = new HashSet<>();

    protected List<Pair<String, INotifiable>> callbacks = new LinkedList<>();

    public int degreeOf(Vertex<T> v) {
        int d = 0;

        for (U e : edges) {
            if (e.getSource().equals(v) || e.getTarget().equals(v)) {
                d++;
            }
        }

        return d;
    }

    public int degreeOf(String vertexId) {
        Optional<Vertex<T>> vertexById = getVertexById(vertexId);

        if (vertexById.isPresent()) {
            return degreeOf(vertexById.get());
        } else {
            return -1;
        }
    }

    public void subscribe(String eventName, INotifiable callback) {
        callbacks.add(new Pair<>(eventName, callback));
    }

    public void dispatch(String eventName, String message) {
        callbacks.stream()
            .filter(pair -> pair.getKey().equals(eventName))
            .forEach(pair -> {
                try {
                    pair.getValue().call(message);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            });
    }

    /**
     *
     * @return new generated String id
     */
    protected String getNewId() {
        return "id" + Date.from(Instant.now()).toString();
    }

    protected Optional<Vertex<T>> getVertexById(String id) {
        return vertices.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    @Override
    public Set<U> incidentEdges(Vertex<T> v) {
        return edges.stream()
                .filter(e -> e.getSource().getId().equals(v.getId()) || e.getTarget().getId().equals(v.getId()))
                .collect(Collectors.toSet());
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
    public Set<Vertex<T>> vertexSet() {
        return vertices;
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
    public boolean removeVertex(String vertexId) {

        List<Vertex<T>> lookup = vertices.stream().filter(v -> v.getId().equals(vertexId)).collect(Collectors.toList());

        return lookup.size() == 1 && removeVertex(lookup.get(0));
    }

    @Override
    public boolean removeVertex(Vertex<T> v) {
        /*
        remove the vertex from other vertices' adjacency lists
         */
        vertices.stream()
                .forEach(vertex -> vertex.removeAdjacentVertex(v));

        dispatch("graph.vertex.remove", v.getId());
        return vertices.remove(v);
    }

    /**
     * Removes the given edge from the graph, if it existed.
     *
     * @param e Reference to the edge that should be removed
     * @return bool
     */
    public boolean removeEdge(U e) {
        return edges.remove(e);
    }

    /**
     * Removes all given edges, should they exist in the graph.
     *
     * @param e list of edges to be removed
     * @return bool
     */
    public boolean removeEdges(List<U> e) {
        return edges.removeAll(e);
    }

    /**
     * Removes all given vertices, should they exist in the graph.
     *
     * @param v list of vertices to be removed
     * @return bool
     */
    public boolean removeVertices(List<Vertex<T>> v) {
        return vertices.removeAll(v);
    }
}
