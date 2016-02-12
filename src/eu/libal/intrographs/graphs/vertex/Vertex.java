package eu.libal.intrographs.graphs.vertex;

public class Vertex<VertexType> implements IVertex<VertexType> {

    VertexType value;
    String id;

    public Vertex(VertexType value, String id) {
        this.value = value;
        this.id = id;
    }

    @Override
    public VertexType getValue() {
        return value;
    }

    @Override
    public String getId() {
        return id;
    }
}
