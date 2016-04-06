package eu.libal.intrographs.graphs.vertex;

import java.util.HashSet;
import java.util.Set;

/**
 * Vertex represents a node in a graph.
 *
 */
public class Vertex<T> implements Comparable<Vertex<?>> {

    private T value;
    private String id;
    private final Set<Vertex<T>> adjacent;

    /**
     * Constructs a vertex with a given ID and null value.
     *
     * @param id id of the vertex (should be unique)
     */
    public Vertex(String id) {
        this(null, id);
    }

    public Vertex(T value, String id) {
        this.value = value;
        this.id = id;
        this.adjacent = new HashSet<>();
    }

    /**
     * This method should return the value member of the vertex
     *
     * @return T value
     */
    public T getValue() {
        return value;
    }

    /**
     *
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return a set of vertices that are incident on the same edges as this vertex
     */
    public Set<Vertex<T>> getAdjacentVertices() {
        return adjacent;
    }

    /**
     *
     * @param v adjacent vertex to be added to the set of adjacent vertices of {this}
     */
    public void addAdjacentVertex(Vertex<T> v) {
        adjacent.add(v);
    }

    /**
     *
     * @param v adjacent vertex to be removed the from the set of adj. vertices
     */
    public void removeAdjacentVertex(Vertex<T> v) {
        adjacent.remove(v);
    }

    /**
     * See Comparable<T> for more info.
     *
     * @param v vertex being compared to {this} vertex
     * @return (String) {this}.id.compareTo((String) v.getId()), so 0 if string IDs identical
     */
    public int compareTo(Vertex<?> v) {
        return id.compareTo(v.getId());
    }

    /**
     * Returns the (undirected) degree of the vertex
     *
     * @return int Degree of the vertex
     */
    public int degreeOf() {
        return adjacent.size();
    }

    /**
     * Returns true if vertex v is same as this one. Otherwise, returns false.
     *
     * @param v the second vertex being compared
     * @return true if vertices are equal, false otherwise
     */
    public boolean equals(Vertex<T> v) {
        return v != null && v.getId().equals(id);

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
