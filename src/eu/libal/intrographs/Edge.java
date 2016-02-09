package eu.libal.intrographs;

/**
 * Created by tlibal on 2/9/16.
 */
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
