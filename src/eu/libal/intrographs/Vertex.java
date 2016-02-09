package eu.libal.intrographs;

/**
 * Created by tlibal on 2/9/16.
 */
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
