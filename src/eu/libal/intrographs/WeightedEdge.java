package eu.libal.intrographs;

/**
 *
 */
public class WeightedEdge<VertexType> extends Edge<VertexType> {

    Double weight = 1.0;

    WeightedEdge(IVertex<VertexType> source, IVertex<VertexType> target) {
        super(source, target);
    }

    WeightedEdge(IVertex<VertexType> source, IVertex<VertexType> target, Double weight) {
        super(source, target);
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
