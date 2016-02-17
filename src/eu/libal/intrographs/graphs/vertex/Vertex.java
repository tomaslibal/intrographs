package eu.libal.intrographs.graphs.vertex;

public class Vertex<T> implements IVertex<T> {

    T value;
    String id;

    public Vertex(T value, String id) {
        this.value = value;
        this.id = id;
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
}
