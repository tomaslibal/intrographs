package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.Vertex;

public class Edge<T> implements IEdge<T> {

    private Vertex<T> source;
    private Vertex<T> target;

    public Edge(Vertex<T> source, Vertex<T> target)
    {
        this.source = source;
        this.target = target;
    }

    @Override
    public Vertex<T> getTarget() {
        return target;
    }

    @Override
    public Vertex<T> getSource() {
        return source;
    }

    @Override
    public void setSource(Vertex<T> source) {
        this.source = source;
    }

    @Override
    public void setTarget(Vertex<T> target) {
        this.target = target;
    }
}
