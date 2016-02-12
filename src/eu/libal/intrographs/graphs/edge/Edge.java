package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.IVertex;

public class Edge<VertexType> implements IEdge {

    private IVertex<VertexType> source;
    private IVertex<VertexType> target;

    public Edge(IVertex<VertexType> source, IVertex<VertexType> target)
    {
        this.source = source;
        this.target = target;
    }

    @Override
    public IVertex getTarget() {
        return target;
    }

    @Override
    public IVertex getSource() {
        return source;
    }
}
