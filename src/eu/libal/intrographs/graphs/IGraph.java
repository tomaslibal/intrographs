package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This interface represents a graph type interface.
 *
 * @param <T> Type which each vertex uses for storing the vertex's value
 * @param <U> Type of the Edge (e.g. weighted edge or non-weighted edge, ...)
 */
public interface IGraph<T, U extends Edge<T>> {

    /**
     * Adds an unnamed vertex (no ID).
     *
     * @param v value of the vertex
     * @return returns the newly added vertex
     */
    Vertex<T> addVertex(T v);

    /**
     * Adds a vertex with the given ID, if it was not already present in the graph.
     *
     * @param v value of the vertex
     * @param id ID of the vertex
     * @return returns the newly added vertex
     */
    Vertex<T> addVertex(T v, String id);

    /**
     * Given an ID of a vertex, looks up the vertex in the graph.
     *
     * @param id ID of the vertex to be looked up
     * @return An optional of Vertex
     */
    Optional<Vertex<T>> lookupVertex(String id);

    /**
     * Adds the given Edge object to the graph and updates the adjacency list of the vertices to reflect the fact that
     * two vertices are now adjacent.
     *
     * @param e Edge object to be added
     * @return Returns back the Edge object
     */
    U addEdge(U e);

    /**
     * Creates a new Edge object and stores it for the given pair of source and target vertices, referenced to by their
     * IDs. Returns null at least one vertex ID is not found.
     *
     * @param sourceId Source vertex's ID
     * @param targetId Target vertex's ID
     * @return The newly created Edge object
     */
    U addEdge(String sourceId, String targetId);

    /**
     * Returns a set of Edges that are incident on a given vertex.
     *
     * @param v Vertex whose incident edges are being returned
     * @return Set of Edges incident on the given vertex
     */
    Set<U> incidentEdges(Vertex<T> v);

    /**
     * Returns the set of all Vertices in the graph.
     *
     * @return Set of Vertices
     */
    Set<Vertex<T>> vertexSet();

    /**
     * Returns the set of all Edges in the graph.
     *
     * @return Set of Edges
     */
    Set<U> edgeSet();

    /**
     * Removes the vertex by its ID.
     *
     * @param vertexId ID of the vertex to be removed
     * @return true if the deletion was successful, false otherwise
     */
    boolean removeVertex(String vertexId);

    /**
     * Removes the vertex by its reference
     * @param v Vertex to be removed from the graph
     * @return returns true if the deletion was successful, false otherwise
     */
    boolean removeVertex(Vertex<T> v);

    /**
     * Removes the edge by its reference
     * @param e Reference to the edge that should be removed
     * @return returns true if the deletion was successful, false otherwise
     */
    boolean removeEdge(U e);

    /**
     * Removes vertices that are in supplied list all at once from the graph.
     *
     * @param vertices A list of vertices to be removed
     * @return returns the result of Set.removeAll call
     */
    boolean removeVertices(List<Vertex<T>> vertices);

    /**
     * Removes edges that are in the supplied list all at once from the graph.
     *
     * @param edges A list of edges to be removed
     * @return returns the result of Set.removeAll call
     */
    boolean removeEdges(List<U> edges);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param v vertex whose degree will be returned
     * @return int
     */
    int degreeOf(Vertex<T> v);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param vertexId string id value of the vertex whose degree will be returned
     * @return int
     */
    int degreeOf(String vertexId);
}
