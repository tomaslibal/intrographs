package eu.libal.intrographs.graphs.vertex;

import java.util.HashSet;
import java.util.Set;

public class Vertex<T> implements IVertex<T> {

    private T value;
    private Set<IVertex<T>> adjacent;
    private String id;

    public Vertex(T value, String id) {
        this.value = value;
        this.id = id;
        this.adjacent = new HashSet<>();
    }

    public Vertex(String id) {
        this.id = id;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Set<IVertex<T>> getAdjacentVertices() {
        return adjacent;
    }

    @Override
    public void addAdjacentVertex(IVertex<T> v) {
        adjacent.add(v);
    }
}
