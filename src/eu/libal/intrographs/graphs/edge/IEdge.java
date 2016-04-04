package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.Vertex;

public interface IEdge<T> {
    Vertex<T> getTarget();

    Vertex<T> getSource();

    void setSource(Vertex<T> source);

    void setTarget(Vertex<T> target);
}