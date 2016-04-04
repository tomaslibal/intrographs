package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.common.Notifiable;
import eu.libal.intrographs.common.ListenableField;
import eu.libal.intrographs.graphs.vertex.Vertex;

/**
 *
 */
public class WeightedEdge<T> extends Edge<T> {

    private final ListenableField<Double> weight;

    WeightedEdge(Vertex<T> source, Vertex<T> target) {
        this(source, target, 1.0);
    }

    WeightedEdge(Vertex<T> source, Vertex<T> target, Double weight) {
        super(source, target);
        this.weight = new ListenableField<>();
        this.weight.setValue(weight);
    }

    public void subscribe(Notifiable callback) {
        weight.subscribe("update", callback);
    }

    public Double getWeight() {
        return weight.getValue();
    }

    public void setWeight(Double weight) {
        this.weight.setValue(weight);
    }
}
