package eu.libal.intrographs;

public class Vertex<VertexType> implements IVertex<VertexType> {

    VertexType value;

    public Vertex(VertexType value) {
        this.value = value;
    }

    @Override
    public VertexType getValue() {
        return value;
    }
}
