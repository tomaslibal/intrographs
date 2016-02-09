package eu.libal.intrographs;

public class Edge<VertexType> implements IEdge {

    private IVertex<VertexType> source;
    private IVertex<VertexType> target;

    Edge(IVertex<VertexType> source, IVertex<VertexType> target)
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
