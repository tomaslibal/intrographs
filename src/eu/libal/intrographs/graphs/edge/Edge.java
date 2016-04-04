package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.Vertex;

/**
 * Represents and edge in a graph, on which two vertices are incident.
 *
 * @param <T> Type of the value in Vertex
 */
public class Edge<T> {

    private Vertex<T> source;
    private Vertex<T> target;

    public Edge(Vertex<T> source, Vertex<T> target)
    {
        this.source = source;
        this.target = target;
    }

    public Vertex<T> getTarget() {
        return target;
    }

    public Vertex<T> getSource() {
        return source;
    }

    public void setSource(Vertex<T> source) {
        this.source = source;
    }

    public void setTarget(Vertex<T> target) {
        this.target = target;
    }
}
