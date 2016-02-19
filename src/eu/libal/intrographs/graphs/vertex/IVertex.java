package eu.libal.intrographs.graphs.vertex;

import java.util.Set;

/**
 * Vertex represents a node in a graph.
 *
 */
public interface IVertex<T>
{
    /**
     * This method should return the value member of the vertex
     *
     * @return T value
     */
    T getValue();

    /**
     *
     * @return String id
     */
    String getId();

    /**
     *
     * @return a set of vertices that are incident on the same edges as this vertex
     */
    Set<IVertex<T>> getAdjacentVertices();

    /**
     *
     * @param v adjacent vertex to be added to the set of adjacent vertices of {this}
     */
    void addAdjacentVertex(IVertex<T> v);
}
