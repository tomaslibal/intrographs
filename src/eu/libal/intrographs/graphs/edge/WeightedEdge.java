package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.Vertex;

/**
 *
 */
public class WeightedEdge<T> extends Edge<T> {

    Double weight = 1.0;

    WeightedEdge(Vertex<T> source, Vertex<T> target) {
        super(source, target);
    }

    WeightedEdge(Vertex<T> source, Vertex<T> target, Double weight) {
        super(source, target);
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
