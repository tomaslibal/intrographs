package eu.libal.intrographs;

import java.util.List;

/**
 * Created by tlibal on 2/9/16.
 */
public interface IGraph<VertexType, EdgeType>
{

    void addVertex(Vertex<VertexType> v);
    void addEdge(EdgeType e);

    void removeVertex(Vertex<VertexType> v);
    void removeEdge(EdgeType e);
    void removeVertices();
    void removeEdges(List<EdgeType> edges);
}
