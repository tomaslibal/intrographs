package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.IVertex;

public class Edge<T> implements IEdge<T> {

    private IVertex<T> source;
    private IVertex<T> target;

    public Edge(IVertex<T> source, IVertex<T> target)
    {
        this.source = source;
        this.target = target;
    }

    @Override
    public IVertex<T> getTarget() {
        return target;
    }

    @Override
    public IVertex<T> getSource() {
        return source;
    }
}
