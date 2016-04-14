package eu.libal.intrographs.graphs;

import eu.libal.intrographs.common.Listenable;
import eu.libal.intrographs.common.Notifiable;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This abstract class represents a graph type.
 *
 * @param <T> Type which each vertex uses for storing the vertex's value
 * @param <U> Type of the Edge (e.g. weighted edge or non-weighted edge, ...)
 */
abstract public class BaseGraph<T, U extends Edge> implements Listenable {

    protected final Set<Vertex<T>> vertices = new HashSet<>();
    protected final Set<U> edges = new HashSet<>();

    private List<Pair<String, Notifiable>> callbacks = new LinkedList<>();

    /**
     * Returns the degree of a node (undirected)
     *
     * @param v vertex whose degree will be returned
     * @return int
     */
    public int degreeOf(Vertex<T> v) {
        int d = 0;

        for (U e : edges) {
            Vertex<?> source = e.getSource();
            Vertex<?> target = e.getTarget();

            if (source.equals(v) || target.equals(v)) {
                d++;
            }
        }

        return d;
    }

    /**
     * Returns the degree of a node (undirected)
     *
     * @param vertexId id of the vertex whose degree will be returned
     * @return int
     */
    public int degreeOf(String vertexId) {
        Optional<Vertex<T>> vertexById = getVertexById(vertexId);

        if (vertexById.isPresent()) {
            return degreeOf(vertexById.get());
        } else {
            return -1;
        }
    }

    public void subscribe(String eventName, Notifiable callback) {
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

    /**
     * Returns a set of Edges that are incident on a given vertex.
     *
     * @param v Vertex whose incident edges are being returned
     * @return Set of Edges incident on the given vertex
     */
    public Set<U> incidentEdges(Vertex<T> v) {
        return edges.stream()
                .filter(e -> e.getSource().getId().equals(v.getId()) || e.getTarget().getId().equals(v.getId()))
                .collect(Collectors.toSet());
    }

    /**
     *
     * A graph keeps vertices in a set and each vertex keeps a list of adjacent vertices. Thus, the edges are embedded in
     * this adjacency list. However, the graph also keeps a separate set of Edges, which is a set representing the same
     * state captured by the adjacency list. This is so that the edge set does not have to be computed every time it
     * is requested.
     *
     * @return Set of all edges in the graph
     */
    public Set<U> edgeSet() {
        return edges;
    }

    /**
     * Returns the set of all Vertices in the graph.
     *
     * @return Set of Vertices
     */
    public Set<Vertex<T>> vertexSet() {
        return vertices;
    }

    /**
     * Adds an unnamed vertex (no ID).
     *
     * @param v value of the vertex
     * @return returns the newly added vertex
     */
    public Vertex<T> addVertex(T v) {
        return addVertex(v, getNewId());
    }

    /**
     * Adds a vertex with the given ID, if it was not already present in the graph.
     *
     * @param v value of the vertex
     * @param id ID of the vertex
     * @return returns the newly added vertex
     */
    @Deprecated
    public Vertex<T> addVertex(T v, String id) {
        Vertex<T> vertex = new Vertex<>(v, id);
        addVertex(vertex);

        return vertex;
    }

    public void addVertex(Vertex<T> v) {
        vertices.add(v);

        dispatch("graph.vertex.add", v.getId());
    }

    /**
     * Given an ID of a vertex, looks up the vertex in the graph.
     *
     * @param id ID of the vertex to be looked up
     * @return An optional of Vertex
     */
    public Optional<Vertex<T>> lookupVertex(String id) {
        return getVertexById(id);
    }

    /**
     * Creates a new Edge object and stores it for the given pair of source and target vertices, referenced to by their
     * IDs. Returns null at least one vertex ID is not found.
     *
     * @param sourceId Source vertex's ID
     * @param targetId Target vertex's ID
     * @return The newly created Edge object
     */
    public U addEdge(String sourceId, String targetId) {
        Optional<Vertex<T>> source = getVertexById(sourceId);
        Optional<Vertex<T>> target = getVertexById(targetId);

        if (!source.isPresent()
            || !target.isPresent()) {
            // should throw
            return null;
        }

        Edge<T> e = new Edge<>(source.get(), target.get());
        return addEdge((U) e);
    }

    /**
     * Adds the given Edge object to the graph and updates the adjacency list of the vertices to reflect the fact that
     * two vertices are now adjacent.
     *
     * @param e Edge object to be added
     * @return Returns back the Edge object
     */
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

    /**
     * Removes the vertex by its ID.
     *
     * @param vertexId ID of the vertex to be removed
     * @return true if the deletion was successful, false otherwise
     */
    public boolean removeVertex(String vertexId) {

        List<Vertex<T>> lookup = vertices.stream().filter(v -> v.getId().equals(vertexId)).collect(Collectors.toList());

        return lookup.size() == 1 && removeVertex(lookup.get(0));
    }

    /**
     * Removes the vertex by its reference
     * @param v Vertex to be removed from the graph
     * @return returns true if the deletion was successful, false otherwise
     */
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
     * @return returns the result of Set.removeAll call
     */
    public boolean removeEdges(List<U> e) {
        return edges.removeAll(e);
    }

    /**
     * Removes all given vertices, should they exist in the graph.
     *
     * @param v list of vertices to be removed
     * @return returns the result of Set.removeAll call
     */
    public boolean removeVertices(List<Vertex<T>> v) {
        return vertices.removeAll(v);
    }

    public void addAllVertices(List<Vertex<T>> vertices) {
        vertexSet().addAll(vertices);
    }
}
