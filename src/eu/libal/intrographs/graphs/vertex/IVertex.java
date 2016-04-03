package eu.libal.intrographs.graphs.vertex;

import java.util.Set;

/**
 * Vertex represents a node in a graph.
 *
 */
public interface IVertex<T> extends Comparable<IVertex<T>> {
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

    /**
     *
     * @param v adjacent vertex to be removed the from the set of adj. vertices
     */
    void removeAdjacentVertex(IVertex<T> v);

    /**
     * See Comparable<T> for more info.
     *
     * @param v vertex being compared to {this} vertex
     * @return (String) {this}.id.compareTo((String) v.getId()), so 0 if string IDs identical
     */
    int compareTo(IVertex<T> v);

    /**
     * Returns the (undirected) degree of the vertex
     *
     * @return int Degree of the vertex
     */
    int degreeOf();

    /**
     * Returns true if vertex v is same as this. Otherwise, returns false.
     * @param v
     * @return
     */
    boolean equals(IVertex<T> v);

    void setId(String id);

    void setValue(T value);
}
