package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;

public interface IEdge<T> {
    IVertex<T> getTarget();

    IVertex<T> getSource();

    void setSource(Vertex<T> source);

    void setTarget(Vertex<T> target);
}